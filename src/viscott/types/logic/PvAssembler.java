package viscott.types.logic;

import arc.func.Prov;
import arc.struct.Seq;
import mindustry.logic.LAssembler;
import mindustry.logic.LExecutor;
import mindustry.logic.LParser;
import mindustry.logic.LStatement;

public class PvAssembler extends LAssembler {
    public static Seq<Prov<LStatement>> allStatement;
    public static Seq<LStatement> readLogic(String text, Seq<Prov<LStatement>> allStatements){
        //don't waste time parsing null/empty text
        allStatement = allStatements;
        if(text == null || text.isEmpty()) return new Seq<>();
        return new PvParser(text, allStatements).parse();
    }
    public static PvAssembler pvAssemble(String data, boolean privileged){
        PvAssembler asm = new PvAssembler();

        Seq<LStatement> st = new PvParser(data,allStatement).parse();

        asm.instructions = st.map(
                l -> l.build(asm))
                .retainAll(l -> l != null).toArray(LExecutor.LInstruction.class);
        return asm;
    }

    public static Seq<LStatement> pvRead(String text, boolean privileged){
        //don't waste time parsing null/empty text
        if(text == null || text.isEmpty()) return new Seq<>();
        return new PvParser(text, allStatement).parse();
    }
}
