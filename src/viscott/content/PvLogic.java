package viscott.content;

import arc.scene.ui.layout.Table;
import mindustry.gen.Unit;
import mindustry.logic.LAssembler;
import mindustry.logic.LCategory;
import mindustry.logic.LExecutor;
import mindustry.logic.LStatement;
import mindustry.ui.Styles;
import viscott.types.logic.PvParser;

import static mindustry.Vars.content;
import static mindustry.Vars.net;

public class PvLogic {
    public static void load()
    {
        PvParser.addLoad("com",1,new CommentStatement());
        PvParser.addLoad("hurt",2,new HurtStatement());
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
    public static class HurtStatement extends LStatement
    {
        public String damage = "10", unit = "@unit";

        public HurtStatement() {
            super();
        }
        public HurtStatement(String damage,String unit) {
            super();
            this.damage = damage;
            this.unit = unit;
        }

        public LCategory category(){
            return LCategory.unit;
        }
        @Override
        public void build(Table table){
            table.add(" unit :");
            fields(table, unit, str -> unit = str);
            table.add(" damage :");
            fields(table, damage, str -> damage = str);
        }

        @Override
        public LStatement copy()
        {
            return new HurtStatement(damage,unit);
        }

        @Override
        public void write(StringBuilder builder){
            builder.append("hurt " + damage + " " + unit);
        }
        @Override
        public void afterRead()
        {
            damage = PvParser.allToken[1];
            unit = PvParser.allToken[2];
        }

        @Override
        public LExecutor.LInstruction build(LAssembler builder) {
            return new HurtI(builder.var(damage),builder.var(unit));
        }

        public static class HurtI implements LExecutor.LInstruction {
            public int unit, damage;

            public HurtI(int damage, int unit){
                this.unit = unit;
                this.damage = damage;
            }

            public HurtI(){
            }

            @Override
            public void run(LExecutor exec) {
                if (net.client()) return;

                if (exec.obj(unit) instanceof Unit unit)
                    unit.health -= exec.numf(damage);
            }
        }
    }
}
