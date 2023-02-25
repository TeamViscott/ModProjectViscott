package viscott.content;

import arc.scene.ui.layout.Table;
import arc.util.Time;
import mindustry.gen.Unit;
import mindustry.logic.LAssembler;
import mindustry.logic.LCategory;
import mindustry.logic.LExecutor;
import mindustry.logic.LStatement;
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
        PvParser.addLoad("hurt",2,new HealStatement());
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
            builder.append("hurt " + heal + " " + unit);
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
                    if (net.client()) return;

                    if (exec.obj(unit) instanceof Unit unit && unit.team == exec.team)
                        unit.health += exec.numf(healing);
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
}
