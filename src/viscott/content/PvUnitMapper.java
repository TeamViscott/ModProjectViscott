package viscott.content;

import mindustry.gen.EntityMapping;
import viscott.gen.FrogUnit;
import viscott.gen.GridUnit;

public class PvUnitMapper {
    public static void load() {
        EntityMapping.idMap[150] = FrogUnit::new;
        EntityMapping.idMap[151] = GridUnit::new;
    }
}
