package viscott.content;

import arc.func.Prov;
import mindustry.gen.EntityMapping;
import mindustry.gen.Unit;
import viscott.gen.FrogUnit;
import viscott.gen.GridUnit;
import viscott.gen.MwBossUnit;

public class PvUnitMapper {
    public static int
            FrogId,GridUnitId,MwBossId;
    public static void load() {
        EntityMapping.register("Frog",FrogUnit::new);
        EntityMapping.register("Grid",FrogUnit::new);
        EntityMapping.register("Manu",MwBossUnit::new);
    }
}
