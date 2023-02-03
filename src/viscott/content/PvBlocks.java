package viscott.content;

import arc.struct.Seq;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.DuctRouter;
import mindustry.world.blocks.distribution.Junction;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.blocks.environment.TallBlock;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.blocks.power.SolarGenerator;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawMulti;
import mindustry.world.meta.BuildVisibility;
import viscott.world.block.PvBlock;
import viscott.world.block.drill.Grinder;
import viscott.world.block.environment.DepositWall;
import viscott.world.block.power.ConstGenerator;

import static mindustry.type.ItemStack.with;

public class PvBlocks {
    public static Block
            /*Floor's*/
            densePlate1,densePlate2,densePlate3,densePlate4,
            densePlate5,densePlate6,densePlate7,densePlate8,
            damagedDensePlate,denseMetalWall,bariumWall,bariumPowder,

            /*Ore's*/
            erbiumOre,lithiumOre,zirconiumOre,platinumOre,

            /*Deposit's*/
            erbiumDepositLarge,lithiumDepositLarge,platinumDepositLarge,zirconiumDepositLarge,
                    erbiumDeposit,lithiumDeposit,platinumDeposit,zirconiumDeposit,

                    /*Buildings*/

                    /*Conveyors*/micromassConveyor,massJunction,massRouter,

                    /*Drills*/harvestGrinder,behemothGrinder,harvestDrill,

                    /*Power*/opticalNode,auditoryNode,
                    /*Power Production*/smallCarbonPanel,
                    /*Production*/siliconMassForge,particalAccelerator,
                    /*Liquids*/concentratedConduit,
                    /*Pressure related*/ pressureSource,
                    /**/nueroSpawnPad,
                    /*Core's*/coreHover
                            ;
            public static void load()
            {
                /*Floor's Start*/
                densePlate1 = new Floor("dense-panel-1",1){{localizedName = "Dense Panel 1";}};
                densePlate2 = new Floor("dense-panel-2",1){{localizedName = "Dense Panel 2";}};
                densePlate3 = new Floor("dense-panel-3",1){{localizedName = "Dense Panel 3";}};
                densePlate4 = new Floor("dense-panel-4",1){{localizedName = "Dense Panel 4";}};
                densePlate5 = new Floor("dense-panel-5",1){{localizedName = "Dense Panel 5";}};
                densePlate6 = new Floor("dense-panel-6",1){{localizedName = "Dense Panel 6";}};
                densePlate7 = new Floor("dense-panel-7",1){{localizedName = "Dense Panel 7";}};
                densePlate8 = new Floor("dense-panel-8",1){{localizedName = "Dense Panel 8";}};
                damagedDensePlate = new Floor("damaged-dense-plate",3);
                denseMetalWall = new StaticWall("dense-metal-wall"){{variants = 2; localizedName = "Dense Metal Wall";}};
                bariumWall = new StaticWall("barium-wall"){{variants = 2; localizedName = "Barium Wall";}};
                bariumPowder = new Floor("barium-powder",3)
                {{
                    localizedName = "Barium Powder";
                    itemDrop = PvItems.barium;
                    playerUnmineable = true;

                }};
                /*Floor's End*/
                /*Ore's Start*/
                erbiumOre = new OreBlock("erbium-ore",PvItems.erbium)
                {{
                    variants = 3;
                    localizedName = "Erbium Ore";
                }};
                lithiumOre = new OreBlock("lithium-ore",PvItems.lithium)
                {{
                    variants = 3;
                    localizedName = "Lithium Ore";
                }};
                platinumOre = new OreBlock("platinum-ore",PvItems.platinum)
                {{
                    variants = 3;
                    localizedName = "Platinum Ore";
                }};
                zirconiumOre = new OreBlock("zirconium-ore",PvItems.zirconium)
                {{
                    variants = 3;
                    localizedName = "Zirconium Ore";
                }};
                /*Ore's End*/
                /*Deposit's Start*/
                erbiumDeposit = new DepositWall("erbium-deposit") //Todo
                {{
                    variants = 0;
                    size = 1;
                    clipSize = 256;
                    localizedName = "Erbium Deposit";
                    itemDrop = PvItems.erbium;
                }};
                lithiumDeposit = new DepositWall("lithium-deposit") //Todo
                {{
                    variants = 0;
                    size = 1;
                    clipSize = 256;
                    localizedName = "Lithium Deposit";
                    itemDrop = PvItems.lithium;
                }};
                platinumDeposit = new DepositWall("platinum-deposit") //Todo
                {{
                    variants = 0;
                    size = 1;
                    clipSize = 256;
                    localizedName = "Platinum Deposit";
                    itemDrop = PvItems.platinum;
                    tier = 2;
                    hardness = 0.06f;
                }};
                zirconiumDeposit = new DepositWall("zirconium-deposit") //Todo
                {{
                    variants = 0;
                    size = 1;
                    clipSize = 256;
                    localizedName = "Zirconium Deposit";
                    itemDrop = PvItems.zirconium;
                    hardness = 0.02f;
                }};
                /*Deposit's End*/
                /*Building start*/
                micromassConveyor = new StackConveyor("micromass-conveyor")
                {{
                    requirements(Category.distribution, with(PvItems.lithium,1)); //Todo
                    localizedName = "Micromass Conveyor";
                    health = 50;
                    itemCapacity = 5;
                    speed = 20f/(60f*itemCapacity);
                }};
                massJunction = new Junction("mass-junction")
                {{
                    requirements(Category.distribution, with(PvItems.lithium,5)); //Todo
                    localizedName = "Mass Junction";
                    health = 90;
                    capacity = 20;
                    speed = 40;
                }};
                massRouter = new DuctRouter("mass-router")
                {{
                    requirements(Category.distribution, with(PvItems.lithium,4)); //Todo
                    localizedName = "Mass Router";
                    health = 85;
                    itemCapacity = 20;
                    speed = 0;
                }};

                harvestGrinder = new Grinder("harvest-grinder")
                {{
                    requirements(Category.production, with(PvItems.lithium,50)); //Todo
                    localizedName = "Harvest Grinder";
                    health = 200;
                    liquidCapacity = 20;
                    size = 2;
                    range = 2;
                    speedPerOre = 0.1f;
                    itemCapacity = 20;
                    updateEffect = Fx.smokeCloud;
                }};
                behemothGrinder = new Grinder("behemoth-grinder")
                {{
                    requirements(Category.production, with(PvItems.lithium,200,PvItems.erbium,100,PvItems.zirconium,25)); //Todo
                    localizedName = "Harvest Grinder";
                    health = 570;
                    liquidCapacity = 20;
                    consumePower(50f/60f);
                    size = 3;
                    tier = 2;
                    range = 4;
                    speedPerOre = 0.2f;
                    itemCapacity = 40;
                    updateEffect = Fx.smeltsmoke;
                }};
                harvestDrill = new Drill("harvest-drill")
                {{
                    requirements(Category.production, with(PvItems.lithium,10,PvItems.platinum,5)); //Todo
                    localizedName = "Harvest Drill";
                    size = 2;
                    drillTime = 500;
                    liquidBoostIntensity = 1;
                    tier = 1;
                }};
                opticalNode = new PowerNode("optical-node")
                {{
                    requirements(Category.power, with(PvItems.platinum,5)); //Todo
                    localizedName = "Optical node";
                    size = 1;
                    maxNodes = 4;
                    laserRange = 18;
                    health = 80;
                }};
                auditoryNode = new PowerNode("auditory-node")
                {{
                    requirements(Category.power, with(PvItems.platinum,100,PvItems.barium,10)); //Todo
                    localizedName = "Auditory node";
                    size = 2;
                    maxNodes = 6;
                    laserRange = 35;
                    health = 220;
                }};
                smallCarbonPanel = new ConstGenerator("small-carbon-panel")
                {{
                    requirements(Category.power, with(PvItems.lithium,20,PvItems.barium,50)); //Todo
                    localizedName = "Carbon panel";
                    size = 2;
                    powerProduction = 32f/60f;
                    health = 275;
                }};
                siliconMassForge = new GenericCrafter("silicon-mass-forge")
                {{
                    requirements(Category.crafting, with(PvItems.lithium,140)); //Todo
                    localizedName = "Silicon Mass Forge";
                    health = 175;
                    size = 2;
                    consumePower(45f/60f);
                    itemCapacity = 20;
                    craftTime = 3.3f*60f;
                    consumeItems(with(PvItems.barium,3,Items.coal,5));
                    outputItem = new ItemStack(Items.silicon,5);
                }};
                particalAccelerator = new HeatCrafter("partical-accelerator")
                {{
                    requirements(Category.crafting, with(PvItems.lithium,320, Items.silicon,20)); //Todo
                    localizedName = "Partical Accelerator";
                    health = 230;
                    size = 2;
                    consumePower(60f/60f);
                    itemCapacity = 10;
                    craftTime = 5.8f*60f;
                    heatRequirement = 6;
                    maxEfficiency = 5;
                    consumeItems(with(PvItems.zirconium,5));
                    outputItem = new ItemStack(PvItems.nobelium,3);
                }};
                concentratedConduit = new Conduit("concentrated-conduit")
                {{
                    requirements(Category.liquid, with(PvItems.lithium,4,PvItems.nobelium,2)); //Todo
                    localizedName = "Concentrated conduit";
                    health = 60;
                    liquidCapacity = 30;
                }};
                nueroSpawnPad = new UnitFactory("nuero-spawn-pad")
                {{
                    requirements(Category.units,with(PvItems.lithium,100,PvItems.zirconium,50));
                    localizedName = "Nuero Spawn Pad";
                    health = 1600;
                    size = 5;
                    consumePower(130f/60f);
                    itemCapacity = 3000;
                    liquidCapacity = 200;
                    plans = new Seq<>().with(
                        new UnitPlan(PvUnits.particle,15*60f,with(PvItems.lithium,20))
                    );
                }};
                coreHover = new CoreBlock("core-hover")
                {{
                    requirements(Category.effect, with(PvItems.lithium,500,PvItems.platinum,2000,PvItems.barium,1000)); //Todo
                    localizedName = "Core Hover";
                    unitType = PvUnits.micro;
                    health = 1350;
                    size = 3;
                    unitCapModifier = 10;
                    itemCapacity = 6000;
                }};
            }
}
