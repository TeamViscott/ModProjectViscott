package viscott.content;

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
import viscott.world.block.PvBlock;
import viscott.world.block.drill.Grinder;
import viscott.world.block.environment.DepositWall;
import viscott.world.block.power.ConstGenerator;

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

                    /*Drills*/harvestGrinder,harvestDrill,

                    /*Power*/opticalNode,auditoryNode,
                    /*Power Production*/smallCarbonPanel,

                    /*Liquids*/concentratedConduit
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
                }};
                zirconiumDeposit = new DepositWall("zirconium-deposit") //Todo
                {{
                    variants = 0;
                    size = 1;
                    clipSize = 256;
                    localizedName = "Zirconium Deposit";
                    itemDrop = PvItems.zirconium;

                }};
                /*Deposit's End*/
                /*Building start*/
                micromassConveyor = new StackConveyor("micromass-conveyor")
                {{
                    requirements(Category.distribution, ItemStack.with(PvItems.barium,1)); //Todo
                    localizedName = "Micromass Conveyor";
                    health = 50;
                    itemCapacity = 5;
                    speed = 20f/(60f*itemCapacity);
                }};
                massJunction = new Junction("mass-junction")
                {{
                    requirements(Category.distribution, ItemStack.with(PvItems.barium,1)); //Todo
                    localizedName = "Mass Junction";
                    health = 90;
                    capacity = 20;
                    speed = 40;
                }};
                massRouter = new DuctRouter("mass-router")
                {{
                    requirements(Category.distribution, ItemStack.with(PvItems.barium,1)); //Todo
                    localizedName = "Mass Router";
                    health = 85;
                    itemCapacity = 20;
                    speed = 0;
                }};

                harvestGrinder = new Grinder("harvest-grinder")
                {{
                    requirements(Category.production, ItemStack.with(PvItems.barium,10)); //Todo
                    localizedName = "Harvest Grinder";
                    size = 2;
                }};
                harvestDrill = new Drill("harvest-drill")
                {{
                    requirements(Category.production, ItemStack.with(PvItems.platinum,10)); //Todo
                    localizedName = "Harvest Drill";
                    size = 2;
                    drillTime = 500;
                    liquidBoostIntensity = 1;
                    tier = 1;
                }};
                opticalNode = new PowerNode("optical-node")
                {{
                    requirements(Category.power, ItemStack.with(PvItems.barium,50)); //Todo
                    localizedName = "Optical node";
                    size = 1;
                    maxNodes = 4;
                    laserRange = 18;
                    health = 80;
                }};
                auditoryNode = new PowerNode("auditory-node")
                {{
                    requirements(Category.power, ItemStack.with(PvItems.barium,50)); //Todo
                    localizedName = "Auditory node";
                    size = 2;
                    maxNodes = 6;
                    laserRange = 35;
                    health = 220;
                }};
                smallCarbonPanel = new ConstGenerator("small-carbon-panel")
                {{
                    requirements(Category.power, ItemStack.with(PvItems.barium,50)); //Todo
                    localizedName = "Carbon panel";
                    size = 2;
                    powerProduction = 32f/60f;
                    health = 275;
                }};
                concentratedConduit = new Conduit("concentrated-conduit")
                {{
                    requirements(Category.liquid, ItemStack.with(PvItems.nobelium,50)); //Todo
                    localizedName = "Concentrated conduit";
                    health = 60;
                    liquidCapacity = 30;
                }};
            }
}
