package viscott.content;

import mindustry.content.Fx;
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
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.blocks.power.SolarGenerator;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.storage.CoreBlock;
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

                    /*Liquids*/concentratedConduit,
                    /*Pressure related*/ pressureSource,
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
                bariumPowder = new Floor("barium-powder",3){{localizedName = "Barium Powder";}};
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
                }};
                zirconiumDeposit = new DepositWall("zirconium-deposit") //Todo
                {{
                    variants = 0;
                    size = 1;
                    clipSize = 256;
                    localizedName = "Zirconium Deposit";
                    itemDrop = PvItems.zirconium;
                    tier = 2;
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
                    requirements(Category.distribution, with(PvItems.barium,1)); //Todo
                    localizedName = "Mass Junction";
                    health = 90;
                    capacity = 20;
                    speed = 40;
                }};
                massRouter = new DuctRouter("mass-router")
                {{
                    requirements(Category.distribution, with(PvItems.barium,1)); //Todo
                    localizedName = "Mass Router";
                    health = 85;
                    itemCapacity = 20;
                    speed = 0;
                }};

                harvestGrinder = new Grinder("harvest-grinder")
                {{
                    requirements(Category.production, with(PvItems.lithium,50)); //Todo
                    localizedName = "Harvest Grinder";
                    size = 2;
                    updateEffect = Fx.smokeCloud;
                }};
                behemothGrinder = new Grinder("behemoth-grinder")
                {{
                    requirements(Category.production, with(PvItems.lithium,200,PvItems.erbium,100,PvItems.zirconium,25)); //Todo
                    localizedName = "Harvest Grinder";
                    size = 3;
                    tier = 2;
                    range = 2;
                    itemCapacity = 25;
                    updateEffect = Fx.smeltsmoke;
                }};
                harvestDrill = new Drill("harvest-drill")
                {{
                    requirements(Category.production, with(PvItems.platinum,10)); //Todo
                    localizedName = "Harvest Drill";
                    size = 2;
                    drillTime = 500;
                    liquidBoostIntensity = 1;
                    tier = 1;
                }};
                opticalNode = new PowerNode("optical-node")
                {{
                    requirements(Category.power, with(PvItems.barium,50)); //Todo
                    localizedName = "Optical node";
                    size = 1;
                    maxNodes = 4;
                    laserRange = 18;
                    health = 80;
                }};
                auditoryNode = new PowerNode("auditory-node")
                {{
                    requirements(Category.power, with(PvItems.barium,50)); //Todo
                    localizedName = "Auditory node";
                    size = 2;
                    maxNodes = 6;
                    laserRange = 35;
                    health = 220;
                }};
                smallCarbonPanel = new ConstGenerator("small-carbon-panel")
                {{
                    requirements(Category.power, with(PvItems.barium,50)); //Todo
                    localizedName = "Carbon panel";
                    size = 2;
                    powerProduction = 32f/60f;
                    health = 275;
                }};
                concentratedConduit = new Conduit("concentrated-conduit")
                {{
                    requirements(Category.liquid, with(PvItems.nobelium,50)); //Todo
                    localizedName = "Concentrated conduit";
                    health = 60;
                    liquidCapacity = 30;
                }};
                coreHover = new CoreBlock("core-hover")
                {{
                    requirements(Category.effect, with(PvItems.lithium,200)); //Todo
                    localizedName = "Core Hover";
                    unitType = PvUnits.micro;
                    health = 1350;
                    size = 3;
                    unitCapModifier = 10;
                    itemCapacity = 6000;
                }};
            }
}
