package viscott.content;

import arc.scene.ui.layout.Table;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.gen.LogicIO;
import mindustry.logic.*;
import mindustry.ui.Styles;
import mindustry.world.blocks.heat.HeatProducer;
import viscott.world.block.logic.PvLogicBlock;

public class PvVanillaChanges {
    public static void load()
    {
        UnitTypes.latum.hidden = false;
        UnitTypes.renale.hidden = false;
        ((HeatProducer)Blocks.heatSource).heatOutput = 100000;
    }
}
