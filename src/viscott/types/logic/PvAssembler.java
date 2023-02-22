package viscott.types.logic;

import arc.func.Prov;
import arc.struct.Seq;
import mindustry.logic.LAssembler;
import mindustry.logic.LParser;
import mindustry.logic.LStatement;

public class PvAssembler extends LAssembler {

    public static Seq<LStatement> readLogic(String text, Seq<Prov<LStatement>> allStatements){
        //don't waste time parsing null/empty text
        if(text == null || text.isEmpty()) return new Seq<>();
        return new PvParser(text, allStatements).parse();
    }
}
