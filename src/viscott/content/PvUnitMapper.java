package viscott.content;

import mindustry.gen.EntityMapping;
import viscott.gen.DodgeUnit;
import viscott.gen.FrogUnit;
import viscott.gen.WorldUnit;
import viscott.gen.MwBossUnit;

public class PvUnitMapper {
    public static int
            FrogId,GridUnitId,MwBossId,DodgeUnit;
    public static void load() {
        FrogId = EntityMapping.register("Frog",FrogUnit::new);
        GridUnitId = EntityMapping.register("Grid", WorldUnit::new);
        MwBossId = EntityMapping.register("Manu",MwBossUnit::new);
        DodgeUnit = EntityMapping.register("DodgeUnit",DodgeUnit::new);
    }
}
