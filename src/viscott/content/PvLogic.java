package viscott.content;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.util.Time;
import mindustry.gen.Unit;
import mindustry.logic.*;
import mindustry.ui.Styles;
import viscott.types.logic.PvParser;
import viscott.world.block.logic.PvLogicBlock;

import static mindustry.Vars.*;
import static mindustry.Vars.state;
import static mindustry.logic.LExecutor.varCounter;

public class PvLogic {
    public static void load()
    {
        PvParser.addLoad("com",1,new CommentStatement());
        PvParser.addLoad("heal",2,new HealStatement());
        PvParser.addLoad("shield",3,new ShieldStatement());
        PvParser.addLoad("dj",1,new DynamicJumpStatement());
    }
    public static String tokens(String... token)
    {
        String tokenList = "";
            for (String r : token)
                tokenList += r + " ";
        return tokenList;
    }
    public static class CommentStatement extends LStatement
    {
        public String comment = "";

        public CommentStatement() {
            super();
        }
        public CommentStatement(String comment) {
            super();
            this.comment = comment;
        }

        @Override
        public void build(Table table){
            table.area(comment, Styles.nodeArea, v -> comment = v).growX().height(90f).padLeft(2).padRight(6).color(table.color);
        }

        @Override
        public LStatement copy()
        {
            return new CommentStatement(comment);
        }

        @Override
        public void write(StringBuilder builder){
            builder.append("com "+comment.replace(' ','_').replace('\n','@'));
        }
        @Override
        public void afterRead()
        {
            comment = PvParser.allToken[1].replace('_',' ').replace('@','\n');
        }

        @Override
        public LExecutor.LInstruction build(LAssembler builder) {
            return null;
        }
    }
    public static class HealStatement extends LStatement
    {
        public String heal = "10", unit = "@unit";

        public HealStatement() {
            super();
        }
        public HealStatement(String damage, String unit) {
            super();
            this.heal = damage;
            this.unit = unit;
        }

        public LCategory category(){
            return LCategory.unit;
        }
        @Override
        public void build(Table table){
            table.add(" unit");
            fields(table, unit, str -> unit = str);
            table.add(" healing");
            fields(table, heal, str -> heal = str);
        }

        @Override
        public LStatement copy()
        {
            return new HealStatement(heal,unit);
        }

        @Override
        public void write(StringBuilder builder){
            builder.append("heal " + tokens(heal,unit));
        }
        @Override
        public void afterRead()
        {
            heal = PvParser.allToken[1];
            unit = PvParser.allToken[2];
        }

        @Override
        public LExecutor.LInstruction build(LAssembler builder) {
            return new HealI(builder.var(heal),builder.var(unit));
        }

        public static class HealI implements LExecutor.LInstruction {
            public int unit, healing;

            public float curTime;
            public long frameId;

            public HealI(int damage, int unit){
                this.unit = unit;
                this.healing = damage;
            }

            public HealI(){
            }

            @Override
            public void run(LExecutor exec) {
                if(curTime >= exec.num(healing)/5f){
                    curTime = 0f;

                    if (exec.obj(unit) instanceof Unit unit && unit.team == exec.team) {
                        float x = exec.build.x;
                        float y = exec.build.y;
                        if (Mathf.len(x-unit.x,y-unit.y) <= (exec.build.range()) && exec.numf(healing) > 0)
                            unit.health += exec.numf(healing);
                            if (unit.health > unit.maxHealth)
                                unit.health = unit.maxHealth;
                    }
                }else{
                    //skip back to self.
                    exec.var(varCounter).numval --;
                }



                if(state.updateId != frameId){
                    curTime += Time.delta / 60f * ((PvLogicBlock)exec.build.block).instructionsPerTick;
                    frameId = state.updateId;
                }
            }
        }
    }
    public static class ShieldStatement extends LStatement
    {
        public String shield = "10", unit = "@unit", output = "result";

        public ShieldStatement() {
            super();
        }
        public ShieldStatement(String damage, String unit,String output) {
            super();
            this.shield = damage;
            this.unit = unit;
            this.output = output;
        }

        public LCategory category(){
            return LCategory.unit;
        }
        @Override
        public void build(Table table){
            table.add(" unit");
            fields(table, unit, str -> unit = str);
            table.add(" shield");
            fields(table, shield, str -> shield = str);
            table.add(" max shield = 10% of hp");
            table.row();
            table.add(" result");
            fields(table, output, str -> output = str);
        }

        @Override
        public LStatement copy()
        {
            return new ShieldStatement(shield,unit,output);
        }

        @Override
        public void write(StringBuilder builder){
            builder.append("shield " + tokens(shield,unit,output));
        }
        @Override
        public void afterRead()
        {
            shield = PvParser.allToken[1];
            unit = PvParser.allToken[2];
            output = PvParser.allToken[3];
        }

        @Override
        public LExecutor.LInstruction build(LAssembler builder) {
            return new ShieldI(builder.var(shield),builder.var(unit),builder.var(output));
        }

        public static class ShieldI implements LExecutor.LInstruction {
            public int unit, shieldGiven,result;

            public float curTime;
            public long frameId;

            public ShieldI(int damage, int unit,int result){
                this.unit = unit;
                this.shieldGiven = damage;
                this.result = result;
            }

            public ShieldI(){
            }

            @Override
            public void run(LExecutor exec) {
                if(curTime >= exec.num(shieldGiven)/5f){
                    curTime = 0f;

                    if (exec.obj(unit) instanceof Unit unit && unit.team == exec.team) {
                        float x = exec.build.x;
                        float y = exec.build.y;
                        if (Mathf.len(x-unit.x,y-unit.y) <= (exec.build.range()))
                            unit.shield += exec.numf(shieldGiven);
                        if (unit.shield > unit.health * 0.1f)
                            unit.shield = unit.health * 0.1f;
                        exec.setnum(result,unit.shield);
                    }
                }else{
                    //skip back to self.
                    exec.var(varCounter).numval --;
                }



                if(state.updateId != frameId){
                    curTime += Time.delta / 60f * ((PvLogicBlock)exec.build.block).instructionsPerTick;
                    frameId = state.updateId;
                }
            }
        }
    }
    public static class DynamicJumpStatement extends LStatement
    {
        public String jump = "0";

        public DynamicJumpStatement() {
            super();
        }
        public DynamicJumpStatement(String jump) {
            super();
            this.jump = jump;
        }

        public LCategory category(){
            return LCategory.control;
        }
        @Override
        public void build(Table table){
            table.add("Jump ");
            fields(table, jump, str -> jump = str);
            table.add(" Blocks relative to This");
        }

        @Override
        public LStatement copy()
        {
            return new DynamicJumpStatement(jump);
        }

        @Override
        public void write(StringBuilder builder){
            builder.append("dj " + tokens(jump));
        }
        @Override
        public void afterRead()
        {
            jump = PvParser.allToken[1];
        }

        @Override
        public LExecutor.LInstruction build(LAssembler builder) {
            return new DJumpI(builder.var(jump));
        }

        public static class DJumpI implements LExecutor.LInstruction {
            public int jump;

            public float curTime;
            public long frameId;

            public DJumpI(int jump){
                this.jump = jump;
            }

            public DJumpI(){
            }

            @Override
            public void run(LExecutor exec) {
                exec.var(varCounter).numval += exec.numi(jump);
            }
        }
    }
}
