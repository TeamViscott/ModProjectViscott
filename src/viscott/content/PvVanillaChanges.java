package viscott.content;

import arc.scene.ui.layout.Table;
import arc.util.Timer;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.gen.LogicIO;
import mindustry.gen.Musics;
import mindustry.logic.*;
import mindustry.ui.Styles;
import mindustry.world.Build;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.world.meta.BuildVisibility;
import viscott.world.block.logic.PvLogicBlock;

public class PvVanillaChanges {
    public static void load()
    {
        UnitTypes.latum.hidden = false;
        UnitTypes.renale.hidden = false;
        ((HeatProducer)Blocks.heatSource).heatOutput = 100000;

        
    }
}
