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
        FrogId = EntityMapping.register("Frog",FrogUnit::new);
        GridUnitId = EntityMapping.register("Grid",GridUnit::new);
        MwBossId = EntityMapping.register("Manu",MwBossUnit::new);
    }
}
