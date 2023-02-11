package viscott.content;

import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.type.Category;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.Router;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.sandbox.ItemSource;
import mindustry.world.blocks.sandbox.LiquidSource;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BuildVisibility;

import javax.xml.transform.Source;

import static mindustry.type.ItemStack.with;

public class PvMaptools {
    public static Block
    basicsource, normalsource, advancedsource, ultrasource, ultrarouter, ultraliquidrouter, omegarouter, omegaliquidrouter, ultraconveyor,
    ultraconduit, basicliquidsource, advancedliquidsource, ultraliquidsource, normalliquidsource, coreIntrusion;
    public static void load()
    {
        basicsource = new ItemSource("basicsource")
        {{
            size = 1;
            localizedName = "Basic Source";
            itemsPerSecond = 2;
            health = 600;

        }};
        normalsource = new ItemSource("normalsource")
        {{
            size = 2;
            localizedName = "Medium Source";
            itemsPerSecond = 5;
            health = 1000;

        }};
        advancedsource = new ItemSource("advancedsource")
        {{
            size = 3;
            localizedName = "Advanced Source";
            itemsPerSecond = 7;
            health = 4000;

        }};
        ultrasource = new ItemSource("ultrasource")
        {{
            size = 5;
            localizedName = "Indestructible Source";
            itemsPerSecond = 10;
            health = 2147483647;

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

        }};
        normalliquidsource = new LiquidSource("normalliquidsource")
        {{
            size = 2;
            localizedName = "Medium Liquid Source";
            health = 1000;

        }};
        advancedliquidsource = new LiquidSource("advancedliquidsource")
        {{
            size = 3;
            localizedName = "Advanced Liquid Source";
            health = 4000;

        }};
        ultraliquidsource = new LiquidSource("ultraliquidsource")
        {{
            size = 5;
            localizedName = "Indestructible Liquid Source";
            health = 2147483647;

        }};
        ultraliquidrouter = new LiquidRouter("ultraliquidrouter")
        {{
            size = 1;
            localizedName = "Indestructible Liquid Router";
            health = 2147483647;

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
            }};
        coreIntrusion = new CoreBlock("core-intrusion")
        {{
            localizedName = "Core: Intrusion";
            alwaysUnlocked = true;
            unitType = UnitTypes.emanate; //todo emanate like unit for invasion mode
            health = 5350;
            size = 5;
            unitCapModifier = 100;
            itemCapacity = 10000;
        }};
        }
}
