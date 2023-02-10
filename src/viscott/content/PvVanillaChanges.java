package viscott.content;

import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.world.blocks.heat.HeatProducer;

public class PvVanillaChanges {
    public static void load()
    {
        UnitTypes.latum.hidden = false;
        UnitTypes.renale.hidden = false;
        ((HeatProducer)Blocks.heatSource).heatOutput = 100000;
    }
}
