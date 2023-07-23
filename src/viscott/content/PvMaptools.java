package viscott.content;

import arc.func.Prov;
import arc.struct.Seq;
import mindustry.content.UnitTypes;
import mindustry.logic.LStatement;
import mindustry.logic.LStatements;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.OverdriveProjector;
import mindustry.world.blocks.sandbox.PowerSource;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BuildVisibility;
import viscott.world.block.liquids.SpecialSource;
import viscott.world.block.logic.PvLogicBlock;
import viscott.world.block.sandbox.PvItemSource;
import viscott.world.block.sandbox.PvLiquidSource;

import javax.xml.transform.Source;

import static mindustry.type.ItemStack.with;

public class PvMaptools {
    public static Block
    basicsource, normalsource, advancedsource, ultrasource,
    basicliquidsource, advancedliquidsource, ultraliquidsource, normalliquidsource, coreIntrusion, ultrapowersource,
    voidsource,sourcesource,universalProcessor,
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
        voidsource = new SpecialSource("void-source") {
            {
                requirements(Category.liquid,BuildVisibility.sandboxOnly,with());
                health = 1400;
                localizedName = "Void Source";
                description = "Emitts Void and does not get damaged by Void.";
                liquidAmount = 10;
            }
        };
        sourcesource = new SpecialSource("source-source") {
            {
                requirements(Category.liquid,BuildVisibility.sandboxOnly,with());
                health = 1400;
                localizedName = "Source Source";
                description = "Emitts Source";
                liquid = PvLiquids.concentratedSource;
                liquidAmount = 10;
            }
        };
        universalProcessor = new PvLogicBlock("universal-processor") {{
            requirements(Category.logic,BuildVisibility.editorOnly,with());
            health = Integer.MAX_VALUE;
            privileged = true;
            instructionsPerTick = 4;
            maxInstructionsPerTick = 50;
            maxInstructionScale = 100;
            localizedName = "Universal Processor";
            description = "The Everything Processor basicaly.";
            allStatements.addAll(
                    PvLogic.ShieldStatement::new,
                    PvLogic.CommentStatement::new,
                    PvLogic.DynamicJumpStatement::new,
                    PvLogic.HealStatement::new,
                    PvLogic.TransmitIptStatement::new
            );
        }};
    }
}
