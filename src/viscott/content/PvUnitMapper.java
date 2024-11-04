package viscott.content;

import arc.func.Prov;
import mindustry.gen.EntityMapping;
import mindustry.gen.Unit;
import viscott.gen.DodgeUnit;
import viscott.gen.FrogUnit;
import viscott.gen.GridUnit;
import viscott.gen.MwBossUnit;

public class PvUnitMapper {
    public static int
            FrogId,GridUnitId,MwBossId,DodgeUnit;
    public static void load() {
        FrogId = EntityMapping.register("Frog",FrogUnit::new);
        GridUnitId = EntityMapping.register("Grid",GridUnit::new);
        MwBossId = EntityMapping.register("Manu",MwBossUnit::new);
        DodgeUnit = EntityMapping.register("DodgeUnit",DodgeUnit::new);
    }
}
