package viscott.content;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.type.Category;
import mindustry.world.blocks.defense.DirectionalForceProjector;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.with;

public class PvVanillaChanges {
    public static void load()
    {
        UnitTypes.latum.hidden = false;
        UnitTypes.renale.hidden = false;
        ((HeatProducer)Blocks.heatSource).heatOutput = 100000;
    }
}
