package viscott.content;

import arc.scene.ui.layout.Table;
import mindustry.logic.LAssembler;
import mindustry.logic.LExecutor;
import mindustry.logic.LStatement;
import mindustry.ui.Styles;
import viscott.types.logic.PvParser;

public class PvLogic {
    public static void load()
    {
        PvParser.addLoad("com",1,new CommentStatement());
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
}
