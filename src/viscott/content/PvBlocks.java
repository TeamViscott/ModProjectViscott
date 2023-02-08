package viscott.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.distribution.DuctRouter;
import mindustry.world.blocks.distribution.Junction;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.power.Battery;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.world.blocks.production.Pump;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import mindustry.world.draw.DrawWarmupRegion;
import viscott.world.block.defense.PvWall;
import viscott.world.block.drill.Grinder;
import viscott.world.block.drill.LiquidGrinder;
import viscott.world.block.environment.DepositWall;
import viscott.world.block.power.ConstGenerator;
import viscott.world.block.unit.BulkUnitFactory;

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

                    /*Drills*/harvestDrill,tetraDrill,spectrumDrill,
                    /*Grinders*/harvestGrinder,behemothGrinder,oilGrinder,

                    /*Power*/opticalNode,auditoryNode,compressedBattery,
                    /*Power Production*/smallCarbonPanel,largeCarbonPanel,lithiumDegenerator,
                                        keroseneGenerator,
                    /*Production*/siliconMassForge,particalAccelerator,
                    /*Liquids*/concentratedConduit,micropulsePump,
                    /*Pressure related*/ pressureSource,
                    /*Unit Creation*/nueroSpawnPad,
                    /*Core's*/coreHover,
                            /*Walls*/
                            zirconWall,zirconWallLarge,
                            siliconeWall,siliconeWallLarge,
                            platinumWall,platinumWallLarge,
                            erbiumWall,erbiumWallLarge,
                            carbonWall,carbonWallLarge
                            /*Testing*/
                            ;
            public static void load()
            {
                /*Floor's Start*/
                densePlate1 = new Floor("dense-panel1",0){{localizedName = "Dense Panel 1";}};
                densePlate2 = new Floor("dense-panel2",0){{localizedName = "Dense Panel 2";}};
                densePlate3 = new Floor("dense-panel3",0){{localizedName = "Dense Panel 3";}};
                densePlate4 = new Floor("dense-panel4",0){{localizedName = "Dense Panel 4";}};
                densePlate5 = new Floor("dense-panel5",0){{localizedName = "Dense Panel 5";}};
                densePlate6 = new Floor("dense-panel6",0){{localizedName = "Dense Panel 6";}};
                densePlate7 = new Floor("dense-panel7",0){{localizedName = "Dense Panel 7";}};
                densePlate8 = new Floor("dense-panel8",0){{localizedName = "Dense Panel 8";}};
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
                    tier = 2;
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
                    requirements(Category.distribution, with(PvItems.zirconium,1));
                    localizedName = "Micromass Conveyor";
                    health = 50;
                    itemCapacity = 5;
                    speed = 20f/(60f*itemCapacity);
                }};
                massJunction = new Junction("mass-junction")
                {{
                    requirements(Category.distribution, with(PvItems.zirconium,10));
                    localizedName = "Mass Junction";
                    health = 90;
                    capacity = 20;
                    speed = 20;
                }};
                massRouter = new DuctRouter("mass-router")
                {{
                    requirements(Category.distribution, with(PvItems.zirconium,10));
                    localizedName = "Mass Router";
                    health = 85;
                    speed = 0;
                }};

                harvestGrinder = new Grinder("harvest-grinder")
                {{
                    requirements(Category.production, with(PvItems.zirconium,35));
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
                    requirements(Category.production, with(PvItems.zirconium,100,PvItems.platinum,100)); //Todo
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
                    requirements(Category.production, with(PvItems.zirconium,15));
                    localizedName = "Harvest Drill";
                    size = 2;
                    drillTime = 500;
                    tier = 1;
                    liquidBoostIntensity = 2.5f;
                    health = 225;
                    liquidCapacity = 5;
                    consumeLiquid(Liquids.water,2.5f/60f).boost();
                }};
                tetraDrill = new Drill("tetra-drill")
                {{
                    requirements(Category.production, with(PvItems.zirconium,60,PvItems.platinum,25)); //Todo
                    localizedName = "Harvest Drill";
                    size = 3;
                    drillTime = 200;
                    tier = 2;
                    liquidBoostIntensity = 2.5f;
                    health = 400;
                    liquidCapacity = 20;
                    consumePower(70f/60f);
                    consumeLiquid(PvLiquids.kerosene,2.5f/60f).boost();
                }};
                spectrumDrill = new Drill("spectrum-drill")
                {{
                    requirements(Category.production, with(PvItems.zirconium,160,PvItems.platinum,65,PvItems.nobelium,40)); //Todo
                    localizedName = "Harvest Drill";
                    size = 4;
                    drillTime = 160;
                    tier = 3;
                    liquidBoostIntensity = 1f;
                    health = 790;
                    liquidCapacity = 5;
                    consumePower(195f/60f);
                }};
                opticalNode = new PowerNode("optical-node")
                {{
                    requirements(Category.power, with(PvItems.zirconium,10,PvItems.lithium,5));
                    localizedName = "Optical node";
                    size = 1;
                    maxNodes = 4;
                    laserRange = 18;
                    health = 80;
                }};
                auditoryNode = new PowerNode("auditory-node")
                {{
                    requirements(Category.power, with(PvItems.zirconium,40,Items.silicon,10,PvItems.platinum,15));
                    localizedName = "Auditory node";
                    size = 2;
                    maxNodes = 6;
                    laserRange = 35;
                    health = 220;
                }};
                compressedBattery = new Battery("compressed-battery")
                {{
                    requirements(Category.power, with(PvItems.lithium,50,PvItems.platinum,25,Items.silicon,25));
                    localizedName = "Compressed Battery";
                    consumePowerBuffered(75000);
                    size = 2;
                    health = 410;
                    emptyLightColor = Color.black;
                    fullLightColor = Color.valueOf("8fcaf2");
                }};

                smallCarbonPanel = new ConstGenerator("small-carbon-panel")
                {{
                    requirements(Category.power, with(Items.silicon,15,PvItems.carbonFiber,5));
                    localizedName = "Carbon panel";
                    size = 2;
                    powerProduction = 24f/60f;
                    health = 275;
                }};
                largeCarbonPanel = new ConstGenerator("large-carbon-panel")
                {{
                    requirements(Category.power, with(Items.silicon,65,PvItems.carbonFiber,30));
                    localizedName = "Large Carbon panel";
                    size = 4;
                    powerProduction = 130f/60f;
                    health = 780;
                }};
                lithiumDegenerator = new ConsumeGenerator("lithium-degenerator")
                {{
                    requirements(Category.power, with(PvItems.zirconium,110,PvItems.barium,65,PvItems.nobelium,25)); //Todo
                    localizedName = "Lithium Degenerator";
                    size = 2;
                    powerProduction = 120/60f;
                    health = 250;
                    consumeLiquid(Liquids.water,4f/60f);
                    consumeItem(PvItems.lithium);
                    liquidCapacity = 10;
                    itemCapacity = 5;
                    itemDuration = 1.8f*60f;
                    outputLiquid = new LiquidStack(Liquids.nitrogen,5f/60f);
                    generateEffect = Fx.generate;
                }};
                keroseneGenerator = new ConsumeGenerator("kerosene-generator")
                {{
                    requirements(Category.power, with(PvItems.zirconium,140,PvItems.nobelium,60,PvItems.lithium,100)); //Todo
                    localizedName = "Kerosene Generator";
                    health = 270;
                    size = 2;
                    consumeLiquid(PvLiquids.kerosene,8f/60f);
                    liquidCapacity = 30;
                    powerProduction = 430f/60f;
                    itemDuration = 60f;
                    generateEffect = Fx.smokeCloud;
                    drawer = new DrawMulti(
                        new DrawDefault(),
                        new DrawWarmupRegion(),
                            new DrawRegion("-rotator")
                            {{
                                rotateSpeed = 2;
                                spinSprite = true;

                            }},
                            new DrawRegion("-top")
                    );
                }};
                siliconMassForge = new GenericCrafter("silicon-mass-forge")
                {{
                    requirements(Category.crafting, with(PvItems.zirconium,35,PvItems.lithium,20));
                    localizedName = "Silicon Mass Forge";
                    health = 175;
                    size = 2;
                    consumeItems(with(PvItems.barium,3));
                    consumeLiquid(Liquids.oil,15f/60f);
                    consumePower(45f/60f);
                    itemCapacity = 20;
                    craftTime = 3.3f*60f;
                    outputItem = new ItemStack(Items.silicon,5);
                }};
                particalAccelerator = new HeatCrafter("partical-accelerator")
                {{
                    requirements(Category.crafting, with(PvItems.zirconium,50,PvItems.platinum,30,Items.silicon,50));
                    localizedName = "Particle Accelerator";
                    health = 230;
                    size = 2;
                    consumeItems(with(PvItems.zirconium,5));
                    consumePower(60f/60f);
                    itemCapacity = 10;
                    craftTime = 5.8f*60f;
                    heatRequirement = 6;
                    maxEfficiency = 5;
                    outputItem = new ItemStack(PvItems.nobelium,3);
                }};
                concentratedConduit = new Conduit("concentrated-conduit")
                {{
                    requirements(Category.liquid, with(PvItems.lithium,4,PvItems.zirconium,2));
                    localizedName = "Concentrated conduit";
                    health = 60;
                    liquidCapacity = 30;
                }};
                nueroSpawnPad = new BulkUnitFactory("nuero-spawn-pad")
                {{
                    requirements(Category.units,with(PvItems.zirconium,250,Items.silicon,75,PvItems.platinum,40)); //Todo
                    localizedName = "Nuero Spawn Pad";
                    health = 1600;
                    size = 5;
                    consumePower(130f/60f);
                    itemCapacity = 3000;
                    liquidCapacity = 200;
                    plans = new Seq<>().with(
                        new UnitPlan(PvUnits.particle,15*60f,with(PvItems.lithium,20,PvItems.platinum,10))
                    );
                }};
                coreHover = new CoreBlock("core-hover")
                {{
                    requirements(Category.effect, with(PvItems.zirconium,1000,PvItems.lithium,1500,Items.silicon,300));
                    localizedName = "Core Hover";
                    alwaysUnlocked = true;
                    unitType = PvUnits.micro;
                    health = 1350;
                    size = 3;
                    unitCapModifier = 10;
                    itemCapacity = 6000;
                }};
                oilGrinder = new LiquidGrinder("oil-grinder")
                {{
                    requirements(Category.production, with(PvItems.zirconium,35,PvItems.lithium,5,Items.silicon,25)); //Todo
                    localizedName = "Oil Grinder";
                    extractedLiquid = new LiquidStack(Liquids.oil,4f);
                    range = 3;
                    tier = 2;
                    size = 2;
                    health = 190;
                    liquidCapacity = 40;
                }};
                zirconWall = new Wall("zircon-wall")
                {{
                    requirements(Category.defense, with(PvItems.zirconium,4));
                    localizedName = "Zircon Wall";
                    health = 400;
                }};
                zirconWallLarge = new Wall("zircon-wall-large")
                {{
                    requirements(Category.defense, with(PvItems.zirconium,24));
                    localizedName = "Large Zircon Wall";
                    health = 1680;
                    size = 2;
                }};
                siliconeWall = new Battery("silicone-wall")
                {{
                    requirements(Category.defense, with(Items.silicon,4));
                    localizedName = "Silicone Wall";
                    health = 260;
                    consumePowerBuffered(500);
                }};
                siliconeWallLarge = new Battery("silicone-wall-large")
                {{
                    requirements(Category.defense, with(Items.silicon,24));
                    localizedName = "Large Silicone Wall";
                    health = 1175;
                    size = 2;
                    consumePowerBuffered(2000);
                }};
                platinumWall = new PvWall("platinum-wall")
                {{
                    requirements(Category.defense, with(PvItems.platinum,4));
                    localizedName = "Platinum Wall";
                    health = 575;
                    pierceReduction = 1;
                }};
                platinumWallLarge = new PvWall("platinum-wall-large")
                {{
                    requirements(Category.defense, with(PvItems.platinum,24));
                    localizedName = "Large Platinum Wall";
                    health = 2240;
                    pierceReduction = 2;
                    size = 2;
                }};
                erbiumWall = new PvWall("erbium-wall")
                {{
                    requirements(Category.defense, with(PvItems.erbium,4));
                    localizedName = "Erbium Wall";
                    health = 930;
                    absorbLasers = true;
                }};
                erbiumWallLarge = new PvWall("erbium-wall-large")
                {{
                    requirements(Category.defense, with(PvItems.erbium,24));
                    localizedName = "Large Erbium Wall";
                    health = 3800;
                    size = 2;
                    absorbLasers = true;
                }};
                carbonWall = new PvWall("carbon-wall")
                {{
                    requirements(Category.defense, with(PvItems.carbonFiber,6));
                    localizedName = "Carbon Wall";
                    health = 1120;
                    pierceReduction = 1;
                }};
                carbonWallLarge = new PvWall("carbon-wall-large")
                {{
                    requirements(Category.defense, with(PvItems.carbonFiber,30));
                    localizedName = "Large Carbon Wall";
                    health = 5250;
                    size = 2;
                    pierceReduction = 2;
                }};
                micropulsePump = new Pump("micropulse-pump")
                {{
                    requirements(Category.liquid, with(PvItems.zirconium,40,PvItems.lithium,60,PvItems.erbium,10));
                    localizedName = "MicroPulse Pump";
                    size = 2;
                    pumpAmount = 0.1f;
                    liquidCapacity = 40;
                }};
            }
}
