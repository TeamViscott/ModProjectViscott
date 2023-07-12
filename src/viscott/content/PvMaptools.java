package viscott.content;

import arc.util.Strings;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.type.Category;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.blocks.defense.OverdriveProjector;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.Router;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.sandbox.ItemSource;
import mindustry.world.blocks.sandbox.LiquidSource;
import mindustry.world.blocks.sandbox.PowerSource;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawHeatOutput;
import mindustry.world.draw.DrawMulti;
import mindustry.world.meta.BuildVisibility;
import viscott.world.block.liquids.VoidSource;
import viscott.world.block.sandbox.PvItemSource;
import viscott.world.block.sandbox.PvLiquidSource;

import javax.xml.transform.Source;

import static mindustry.type.ItemStack.with;

public class PvMaptools {
    public static Block
    basicsource, normalsource, advancedsource, ultrasource, ultrarouter, ultraliquidrouter, omegarouter, omegaliquidrouter, ultraconveyor,
    ultraconduit, basicliquidsource, advancedliquidsource, ultraliquidsource, normalliquidsource, coreIntrusion, ultraheatsource, ultrapowersource,
    voidsource,
    tier1overdrive, tier2overdrive, tier3overdrive, tier4overdrive;
    public static void load()
    {
        basicsource = new PvItemSource("basicsource")
        {{
            size = 1;
            localizedName = "Basic Source";
            itemsPerSecond = 10;
            health = 600;
            buildVisibility = BuildVisibility.sandboxOnly;
            sandboxEditOnly = true;

        }};
        normalsource = new PvItemSource("normalsource")
        {{
            size = 2;
            localizedName = "Medium Source";

            itemsPerSecond = 20;
            health = 1000;
            buildVisibility = BuildVisibility.sandboxOnly;
            sandboxEditOnly = true;

        }};
        advancedsource = new PvItemSource("advancedsource")
        {{
            size = 3;
            localizedName = "Advanced Source";
            itemsPerSecond = 25;
            health = 4000;
            buildVisibility = BuildVisibility.sandboxOnly;
            sandboxEditOnly = true;

        }};
        ultrasource = new PvItemSource("ultrasource")
        {{
            size = 5;
            localizedName = "Indestructible Source";
            itemsPerSecond = 30;
            health = Integer.MAX_VALUE;
            buildVisibility = BuildVisibility.sandboxOnly;
            sandboxEditOnly = true;

        }};
        basicliquidsource = new PvLiquidSource("basicliquidsource")
        {{
            requirements(Category.liquid, BuildVisibility.sandboxOnly,with());
            size = 1;
            localizedName = "Basic Liquid Source";
            health = 600;
            sandboxEditOnly = true;
        }};
        normalliquidsource = new PvLiquidSource("normalliquidsource")
        {{
            requirements(Category.liquid, BuildVisibility.sandboxOnly,with());
            size = 2;
            localizedName = "Medium Liquid Source";
            health = 1000;
            sandboxEditOnly = true;
        }};
        advancedliquidsource = new PvLiquidSource("advancedliquidsource")
        {{
            requirements(Category.liquid, BuildVisibility.sandboxOnly,with());
            size = 3;
            localizedName = "Advanced Liquid Source";
            health = 4000;
            sandboxEditOnly = true;
        }};
        ultraliquidsource = new PvLiquidSource("ultraliquidsource")
        {{
            requirements(Category.liquid, BuildVisibility.sandboxOnly,with());
            size = 5;
            localizedName = "Indestructible Liquid Source";
            health = Integer.MAX_VALUE;
            sandboxEditOnly = true;
        }};
        if (false)
            coreIntrusion = new CoreBlock("core-intrusion")
            {{
                requirements(Category.effect,BuildVisibility.sandboxOnly,with());
                localizedName = "Core: Intrusion";
                alwaysUnlocked = true;
                unitType = UnitTypes.emanate; //todo emanate like unit for invasion mode
                health = 5350;
                size = 6;
                unitCapModifier = 196;
                itemCapacity = 240000;
            }};
        ultrapowersource = new PowerSource("ultrapowersource")
        {{
            requirements(Category.power,BuildVisibility.sandboxOnly,with());
            size = 1;
            localizedName = "Indestructible Power Source";
            health = 2147483647;
            powerProduction = 10000000;
            laserRange = 1;
        }};
        tier1overdrive = new OverdriveProjector("tier1overdrive"){{
            requirements(Category.effect,BuildVisibility.sandboxOnly, with());
            consumePower(10f);
            size = 3;
            localizedName = "Tier 1 Overdrive";
            range = 200f;
            speedBoost = 3.25f;
            useTime = 300f;
            hasBoost = false;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};
        tier2overdrive = new OverdriveProjector("tier2overdrive"){{
            requirements(Category.effect,BuildVisibility.sandboxOnly, with());
            consumePower(10f);
            size = 3;
            localizedName = "Tier 2 Overdrive";
            range = 200f;
            speedBoost = 4.5f;
            useTime = 300f;
            hasBoost = false;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};
        tier3overdrive = new OverdriveProjector("tier3overdrive"){{
            requirements(Category.effect,BuildVisibility.sandboxOnly, with());
            consumePower(10f);
            size = 3;
            localizedName = "Tier 3 Overdrive";
            range = 200f;
            speedBoost = 7.25f;
            useTime = 300f;
            hasBoost = false;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};
        tier4overdrive = new OverdriveProjector("tier4overdrive"){{
            requirements(Category.effect,BuildVisibility.sandboxOnly, with());
            consumePower(10f);
            size = 3;
            localizedName = "Tier 4 Overdrive";
            range = 200f;
            speedBoost = 10f;
            useTime = 300f;
            hasBoost = false;
        }};
        voidsource = new VoidSource("void-source") {
            {
                requirements(Category.liquid,BuildVisibility.sandboxOnly,with());
                health = 1400;
                localizedName = "Void Source";
                description = "Emitts Void and does not get damaged by Void.";
                voidAmount = 4;
            }
        };
    }
}
