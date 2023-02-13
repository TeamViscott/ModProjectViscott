package viscott.content;

import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.type.Category;
import mindustry.type.UnitType;
import mindustry.world.Block;
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
import viscott.world.block.sandbox.PvItemSource;

import javax.xml.transform.Source;

import static mindustry.type.ItemStack.with;

public class PvMaptools {
    public static Block
    basicsource, normalsource, advancedsource, ultrasource, ultrarouter, ultraliquidrouter, omegarouter, omegaliquidrouter, ultraconveyor,
    ultraconduit, basicliquidsource, advancedliquidsource, ultraliquidsource, normalliquidsource, coreIntrusion, ultraheatsource, ultrapowersource,
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

        }};
        normalsource = new PvItemSource("normalsource")
        {{
            size = 2;
            localizedName = "Medium Source";

            itemsPerSecond = 20;
            health = 1000;
            buildVisibility = BuildVisibility.sandboxOnly;

        }};
        advancedsource = new PvItemSource("advancedsource")
        {{
            size = 3;
            localizedName = "Advanced Source";
            itemsPerSecond = 25;
            health = 4000;
            buildVisibility = BuildVisibility.sandboxOnly;

        }};
        ultrasource = new PvItemSource("ultrasource")
        {{
            size = 5;
            localizedName = "Indestructible Source";
            itemsPerSecond = 30;
            health = 2147483647;
            buildVisibility = BuildVisibility.sandboxOnly;

        }};
        ultraconveyor = new Conveyor("ultraconveyor")
        {{
            localizedName = "Indestructible Conveyor Belt";
            size = 1;
            speed = 10/60f;

            displayedSpeed = 11f;
            health = 2147483647;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};
        ultraconduit = new Conduit("ultraconduit")
        {{
            localizedName = "Indestructible Conduit";
            size = 1;
            liquidCapacity = 1f;
            health = 2147483647;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};
        basicliquidsource = new LiquidSource("basicliquidsource")
        {{
            size = 1;
            localizedName = "Basic Liquid Source";
            health = 600;
            buildVisibility = BuildVisibility.sandboxOnly;

        }};
        normalliquidsource = new LiquidSource("normalliquidsource")
        {{
            size = 2;
            localizedName = "Medium Liquid Source";
            health = 1000;
            buildVisibility = BuildVisibility.sandboxOnly;

        }};
        advancedliquidsource = new LiquidSource("advancedliquidsource")
        {{
            size = 3;
            localizedName = "Advanced Liquid Source";
            health = 4000;
            buildVisibility = BuildVisibility.sandboxOnly;

        }};
        ultraliquidsource = new LiquidSource("ultraliquidsource")
        {{
            size = 5;
            localizedName = "Indestructible Liquid Source";
            health = 2147483647;
            buildVisibility = BuildVisibility.sandboxOnly;

        }};
        ultraliquidrouter = new LiquidRouter("ultraliquidrouter")
        {{
            size = 1;
            localizedName = "Indestructible Liquid Router";
            health = 2147483647;
            buildVisibility = BuildVisibility.sandboxOnly;

        }};
        ultrarouter = new Router("ultrarouter")
        {
            {
                localizedName = "Indestructible router";
                size = 1;
                speed = 10 / 60f;
                health = 2147483647;
                buildVisibility = BuildVisibility.sandboxOnly;
            }};
        omegarouter = new Router("omegarouter")
        {
            {
                localizedName = "Indestructible Distributor";
                size = 2;
                speed = 10 / 60f;
                health = 2147483647;
                buildVisibility = BuildVisibility.sandboxOnly;
            }};
        omegaliquidrouter = new LiquidRouter("omegaliquidrouter")
        {
            {
                size = 2;
                localizedName = "Indestructible Liquid Distributor";
                health = 2147483647;
                buildVisibility = BuildVisibility.sandboxOnly;
            }};
        coreIntrusion = new CoreBlock("core-intrusion")
        {{
            localizedName = "Core: Intrusion";
            alwaysUnlocked = true;
            unitType = UnitTypes.emanate; //todo emanate like unit for invasion mode
            health = 5350;
            size = 6;
            unitCapModifier = 100;
            itemCapacity = 10000;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};
        ultraheatsource = new HeatProducer("ultraheatsource")
        {{
            size = 1;
            localizedName = "Indestructible Heat Source";
            health = 2147483647;
            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput());
            heatOutput = 1000;
            buildVisibility = BuildVisibility.sandboxOnly;
            category = Category.production;
            warmupRate = 100;

        }};
        ultrapowersource = new PowerSource("ultrapowersource")
        {{
            size = 1;
            localizedName = "Indestructible Power Source";
            health = 2147483647;
            powerProduction = 10000000;
            laserRange = 1;
            buildVisibility = BuildVisibility.sandboxOnly;
            category = Category.power;

        }};
        tier1overdrive = new OverdriveProjector("tier1overdrive"){{
            requirements(Category.effect, with());
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
            requirements(Category.effect, with());
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
            requirements(Category.effect, with());
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
            requirements(Category.effect, with());
            consumePower(10f);
            size = 3;
            localizedName = "Tier 4 Overdrive";
            range = 200f;
            speedBoost = 10f;
            useTime = 300f;
            hasBoost = false;
            buildVisibility = BuildVisibility.sandboxOnly;
        }};
        }
}
