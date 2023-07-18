package viscott.content;

import mindustry.content.Blocks;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.world.blocks.heat.HeatProducer;

public class PvVanillaChanges {
    public static void load()
    {
        UnitTypes.latum.hidden = false;
        UnitTypes.renale.hidden = false;
        ((HeatProducer)Blocks.heatSource).heatOutput = 10000;
        StatusEffects.corroded.localizedName = "Corroded";
        StatusEffects.corroded.show = true;

        
    }
}
