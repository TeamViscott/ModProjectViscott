package viscott.types.logic;

import arc.func.Prov;
import arc.struct.Seq;
import mindustry.logic.LAssembler;
import mindustry.logic.LCanvas;
import mindustry.logic.LExecutor;
import mindustry.logic.LStatement;

public class PvCanvas extends LCanvas {
    public Seq<Prov<LStatement>> allStatements;
    @Override
    public void load(String asm){
        jumps.clear();

        Seq<LStatement> statements = PvAssembler.readLogic(asm,allStatements);
        statements.truncate(LExecutor.maxInstructions);
        this.statements.clearChildren();
        for(LStatement st : statements){
            add(st);
        }

        for(LStatement st : statements){
            st.setupUI();
        }

        this.statements.layout();
    }
}
