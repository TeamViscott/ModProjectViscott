package viscott.content;

import arc.func.Prov;
import arc.graphics.Color;
import arc.math.Interp;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.content.*;
import mindustry.entities.bullet.MassDriverBolt;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.logic.LStatements;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.draw.*;
import mindustry.world.meta.BuildVisibility;
import viscott.types.*;
import viscott.types.drawer.PvDrawPulse;
import viscott.world.block.defense.VoidWall;
import viscott.world.block.effect.VoidBlock;
import viscott.world.block.defense.PvWall;
import viscott.world.block.distribution.*;
import viscott.world.block.drill.*;
import viscott.world.block.effect.*;
import viscott.world.block.environment.DepositWall;
import viscott.world.block.logic.*;
import viscott.world.block.power.*;
import viscott.world.block.production.*;
import viscott.world.block.unit.*;
import viscott.world.draw.*;

import static mindustry.type.ItemStack.with;

public class PvBlocks {
    public static Block
            /*Floor's*/
            densePlate,densePlate2,densePlate3,densePlate4,
            damagedDensePlate, patternedDensePlate, denseMetalWall, bariumWall, bariumPowder, tenebrousStone, tenebrousWall,
            testingRed,testingBlue,testingGrey,testingGreen,

            /*Ore's*/
            erbiumOre,lithiumOre,zirconiumOre,platinumOre,exposedRoot,

            /*Deposit's*/
                    erbiumDeposit,lithiumDeposit,platinumDeposit,zirconiumDeposit,

                    /*Buildings*/

                    /*Conveyors*/micromassConveyor,massJunction,massRouter, microTransportGate,yggdrasilsRoot,
                            megaTransportGate, megaLiquidTransportGate, microLiquidTransportGate,
                            megaMassConveyor,megaMassJunction,megaMassRouter,
                            nueromassConveyor,

                    /*Drills*/harvestDrill,tetraDrill,spectrumDrill,rootHarvester,
                    /*Grinders*/harvestGrinder,behemothGrinder,oilGrinder,
                        /* Xeal */eradicarialGrinder,
                        /* Unix */zirconiumRegrinder,

                    /*Power*/opticalNode,auditoryNode,compressedBattery,lifeForceConverter,oakDengerizer,
                    /*Power Production*/smallCarbonPanel,largeCarbonPanel,lithiumDegenerator,
                                        keroseneGenerator,radiator,blastReactor,subzeroReactor,feverReactor,
                            /*Nullis*/harvestor,
                    /*Production*/siliconMassForge,particalAccelerator, keroseneMixer, carbonWeaver,oakCrystalizer,darkMatterAccelerator,
                            fractionIonizer,nitrogenDistiller,quadRushForge,
                            /*Xeal*/cascadeForge,
                            /*Mortikai*/uberbulkForge,
                            /*Nullis*/voidPressurizer,

                    /*Heaters*/keroseneHeater,blastHeater, hybridHeater, xeroPointHeater,
                                heatPathfinder,
                    /*Liquids*/concentratedJunction,concentratedRouter,concentratedConduit,
                    smallConcentratedTank,largeConcentratedTank,
                            micropulsePump,effluxPump,
                    /*Pressure related*/pressureSource,
                    /*Unit Creation*/templateMolder,massTemplateMolder,
                            nueroSpawnPad,
                            neuroRemolder,grandRemolder,etheriumRemolder,mythicalRemolder,
                            forceModifier,
                            minimalHousingUnit,regularHousingUnit,
                            /*Yggdrasil*/branchMolder,

                    /*Nullis*/
                        packer,

                    /*Templates*/airTempT1,airTempT2,airTempT3,airTempT4, airTempT5,

                    /*Payload*/densePayloadConveyor,densePayloadRouter,
                            denseConstructor,denseDeconstructor,denseUnloader,denseLoader,
                    /*Core's*/coreHover,coreElevate,coreUpraise,yggdrasilsHeartwood,

                            bulkUnloader,
                            /*Nullis*/coreNull, coreVoid, coreAbyss,
                            /*Xeal*/coreSpark,coreCharge,coreSurge,

                            /*Psy*/coreWarden,coreGuardian,coreSentinel,
                            /*Mortikai*/coreBunker,
                    /*Effects*/utilityProjector,pocketContainer,hydroProjector,liquidProjector,
                            /*Nullis*/voidNode,voidBeacon,voidExpander,
                                    voidWall,voidWallLarge,

                            /*Walls*/
                            zirconWall,zirconWallLarge,
                            siliconWall,siliconWallLarge,
                            platinumWall,platinumWallLarge,
                            erbiumWall,erbiumWallLarge,
                            carbonWall,carbonWallLarge,
                            yggdrasilsBark,
                         /* Xeal*/ zirconWallHuge,siliconWallHuge,platinumWallHuge,erbiumWallHuge,carbonWallHuge,tideWall,tideWallLarge,
                          /*Logic*/
                            piscoProcessor,memoryByte,statusSelector,labelHandler,
                            /*Nullis*/voidReprocessingUnit,
                            /*Testing*/
                                    sus
                            ;
            public static void load()
            {
                /*Floor's Start*/
                densePlate = new Floor("dense-plate",0){{mapColor = Color.valueOf("2D2F3F"); localizedName = "Dense Plate";}};
                densePlate2 = new Floor("dense-metal-2",0){{mapColor = Color.valueOf("323544"); localizedName = "Dense Metal 2";}};
                densePlate3 = new Floor("dense-metal-3",0){{mapColor = Color.valueOf("363948"); localizedName = "Dense Metal 3";}};
                densePlate4 = new Floor("dense-metal-4",0){{mapColor = Color.valueOf("202033"); localizedName = "Dense Metal 4";}};
              //densePlate5 = new Floor("dense-panel5",0){{localizedName = "Dense Panel 5";}};
              //densePlate6 = new Floor("dense-panel6",0){{localizedName = "Dense Panel 6";}};
              //densePlate7 = new Floor("dense-panel7",0){{localizedName = "Dense Panel 7";}};
              //densePlate8 = new Floor("dense-panel8",0){{localizedName = "Dense Panel 8";}};
                damagedDensePlate = new Floor("damaged-dense-plate",3){{mapColor = Color.valueOf("2B2D3D"); localizedName = "Damaged Dense Plate";}};
                patternedDensePlate = new Floor("patterned-dense-plate",3){{mapColor = Color.valueOf("303241"); localizedName = "Patterned Dense Plate";}};
                denseMetalWall = new StaticWall("dense-metal-wall"){{variants = 3;mapColor = Color.valueOf("545864"); localizedName = "Dense Metal Wall";}};
                tenebrousStone = new Floor("tenebrous-stone", 4){{mapColor = Color.valueOf("3A3553"); localizedName = "Tenebrous Stone";}};
                tenebrousWall = new StaticWall("tenebrous-wall"){{variants = 3;mapColor = Color.valueOf("67607A"); localizedName = "Tenebrous Wall";}};
                testingRed = new Floor("testing-red",0){{mapColor = Color.valueOf("dbc5c5");localizedName = "Testing Floor [red]Red";}};
                testingBlue = new Floor("testing-blue",0){{mapColor = Color.valueOf("b1bac1");localizedName = "Testing Floor [cyan]Blue";}};
                testingGreen = new Floor("testing-green",0){{mapColor = Color.valueOf("dcffd1");localizedName = "Testing Floor [green]Green";}};
                testingGrey = new Floor("testing-grey",0){{mapColor = Color.valueOf("646567");localizedName = "Testing Floor [grey]Grey";}};
                bariumWall = new StaticWall("barium-wall"){{variants = 2;mapColor = Color.valueOf("666558"); localizedName = "Barium Wall";}};
                bariumPowder = new Floor("barium-powder",3){{mapColor = Color.valueOf("47463D"); localizedName = "Barium Powder"; itemDrop = PvItems.barium; playerUnmineable = true;}};
                /*Floor's End*/
                /*Ore's Start*/
                erbiumOre = new OreBlock("erbium-ore",PvItems.erbium){{variants = 3; localizedName = "Erbium Ore";}};
                lithiumOre = new OreBlock("lithium-ore",PvItems.lithium){{variants = 3; localizedName = "Lithium Ore";}};
                platinumOre = new OreBlock("platinum-ore",PvItems.platinum){{variants = 3; localizedName = "Platinum Ore";}};
                zirconiumOre = new OreBlock("zirconium-ore",PvItems.zirconium){{variants = 3; localizedName = "Zirconium Ore";}};
                exposedRoot = new OreBlock("exposed-root",PvItems.hardenedOak){{variants = 2; localizedName = "[#766e4d]Exposed Root";}};
                /*Ore's End*/
                /*Deposit's Start*/
                erbiumDeposit = new DepositWall("erbium-deposit") //Todo
                {{
                    variants = 0;
                    tier = 2;
                    clipSize = 256;
                    localizedName = "Erbium Deposit";
                    itemDrop = PvItems.erbium;
                    attributes.set(PvAttributes.power,0.8f);
                }};
                lithiumDeposit = new DepositWall("lithium-deposit") //Todo
                {{
                    variants = 0;
                    clipSize = 256;
                    localizedName = "Lithium Deposit";
                    itemDrop = PvItems.lithium;
                    attributes.set(PvAttributes.power,0.3f);
                }};
                platinumDeposit = new DepositWall("platinum-deposit") //Todo
                {{
                    variants = 2;
                    clipSize = 256;
                    localizedName = "Platinum Deposit";
                    itemDrop = PvItems.platinum;
                    tier = 2;
                    attributes.set(PvAttributes.power,0.5f);
                }};
                zirconiumDeposit = new DepositWall("zirconium-deposit") //Todo
                {{
                    variants = 2;
                    clipSize = 256;
                    localizedName = "Zirconium Deposit";
                    itemDrop = PvItems.zirconium;
                    attributes.set(PvAttributes.power,0.1f);
                }};
                /*Deposit's End*/
                /*Building start*/
                micromassConveyor = new MassConveyor("micromass-conveyor")
                {{
                    requirements(Category.distribution, with(PvItems.zirconium,1));
                    localizedName = "Micromass Conveyor";
                    health = 60;
                    itemCapacity = 5;
                    speed = 20f/(300);
                }};
                nueromassConveyor = new MassConveyor("nueromass-conveyor")
                {{
                    requirements(Category.distribution, with(Items.silicon,2,PvItems.platinum,1));
                    localizedName = "Neuromass Conveyor";
                    health = 100;
                    itemCapacity = 10;
                    speed = 40f/(300);
                }};

                megaMassConveyor = new MassConveyor("megamass-conveyor")
                {{
                    requirements(Category.distribution, with(Items.silicon, 3, PvItems.carbonFiber,1));
                    localizedName = "Megamass Conveyor";
                    health = 200;
                    itemCapacity = 20;
                    speed = 50f/(300);
                }};
                massJunction = new Junction("mass-junction")
                {{
                    requirements(Category.distribution, with(PvItems.zirconium,10));
                    localizedName = "Mass Junction";
                    health = 90;
                    capacity = 20;
                    speed = 20;
                    ((MassConveyor)micromassConveyor).junction = this;
                }};
                massRouter = new DuctRouter("mass-router")
                {{
                    requirements(Category.distribution, with(PvItems.zirconium,10));
                    localizedName = "Mass Router";
                    health = 85;
                    speed = 0;
                }};
                megaMassJunction = new Junction("megamass-junction")
                {{
                    requirements(Category.distribution, with(Items.silicon,7,PvItems.zirconium, 5, PvItems.carbonFiber, 3));
                    localizedName = "MegaMass Junction";
                    health = 200;
                    capacity = 50;
                    speed = 20f/(3);
                    ((MassConveyor)megaMassConveyor).junction = this;
                }};
                megaMassRouter = new DuctRouter("megamass-router")
                {{
                    requirements(Category.distribution, with(Items.silicon,7,PvItems.zirconium, 5, PvItems.carbonFiber, 3));
                    localizedName = "MegaMass Router";
                    health = 200;
                    itemCapacity = 50;
                    speed = 0;
                }};
                microTransportGate = new MassDriver("micro-transport-gate"){{
                    requirements(Category.distribution, with(PvItems.zirconium, 20));
                    size = 1;
                    localizedName = "Micro transport gate";
                    health = 180;
                    itemCapacity = 10;
                    reload = 0f;
                    range = 10*8f;
                    rotateSpeed = 360;
                    bullet = new MassDriverBolt(){{
                        lightOpacity = 0;
                        shake = 0;
                        hasColor = false;
                        trailWidth = 0;
                        trailLength = 0;
                        drawDisabled = true;
                        speed = 100;
                    }};
                    consumePower(5/60f);
                    shootSound = Sounds.none;
                    shake = 0;
                }};
                megaTransportGate = new MassDriver("mega-transport-gate"){{
                    requirements(Category.distribution, with(PvItems.zirconium, 50, PvItems.nobelium, 25, PvItems.lithium, 10));
                    size = 3;
                    localizedName = "Mega transport gate";
                    health = 180;
                    itemCapacity = 20;
                    rotateSpeed = 360;
                    reload = 0f;
                    range = 120*8f;
                    consumePower(330/60f);
                    shootSound = Sounds.none;
                    shake = 0;
                    bullet = new MassDriverBolt(){{
                        lightOpacity = 0;
                        shake = 0;
                        hasColor = false;
                        trailWidth = 0;
                        trailLength = 0;
                        drawDisabled = true;
                        speed = 100;
                    }};
                }};
                harvestGrinder = new Grinder("harvest-grinder")
                {{
                    requirements(Category.production, with(PvItems.zirconium,35));
                    localizedName = "Harvest Grinder";
                    squareSprite = false;
                    health = 200;
                    liquidCapacity = 20;
                    consumeLiquid(PvLiquids.xenon,2f/60f).boost();
                    boostMult = 4.6f;
                    size = 2;
                    tierMultiplier = 0.8f;
                    range = 2;
                    speedPerOre = 0.15f/60f;
                    itemCapacity = 20;
                    updateEffect = Fx.smokeCloud;
                    drawer = new DrawMulti(
                            new DrawRegion("-bottom"),
                            new DrawLiquidRegion() {{ this.suffix = "-liquid"; }},
                            new DrawDefault()
                    );
                }};
                behemothGrinder = new Grinder("behemoth-grinder")
                {{
                    requirements(Category.production, with(PvItems.zirconium,100,Items.silicon,20));
                    localizedName = "Behemoth Grinder";
                    squareSprite = false;
                    health = 570;
                    liquidCapacity = 20;
                    consumePower(50f/60f);
                    consumeLiquid(PvLiquids.xenon,4f/60f).boost();
                    boostMult = 4.6f;
                    size = 3;
                    tier = 2;
                    tierMultiplier = 0.7f;
                    range = 4;
                    speedPerOre = 0.18f/60f;
                    itemCapacity = 40;
                    updateEffect = Fx.smeltsmoke;
                    drawer = new DrawMulti(
                            new DrawRegion("-bottom"),
                            new DrawLiquidRegion() {{ this.suffix = "-liquid"; }},
                            new DrawDefault()
                    );
                }};
                eradicarialGrinder = new Grinder("eradicarial-grinder") {{
                    requirements(Category.production, with(PvItems.erbium,600,Items.silicon,200,PvItems.tideAlloy,10));
                    localizedName = "Eradicarial Grinder";
                    health = 820;
                    liquidCapacity = 20;
                    consumeLiquid(PvLiquids.xenon,8f/60f).boost();
                    boostMult = 4.6f;
                    consumePower(175f/60f);
                    faction.add(PvFactions.Xeal);
                    size = 4;
                    tier = 3;
                    tierMultiplier = 0.6f;
                    range = 6;
                    speedPerOre = 0.2f/60f;
                    itemCapacity = 100;
                    updateEffect = Fx.smeltsmoke;
                    squareSprite = false;
                    drawer = new DrawMulti(
                        new DrawRegion("-bottom"),
                        new DrawLiquidRegion() {{ this.suffix = "-liquid"; }},
                        new DrawDefault()
                    );
                }};
                zirconiumRegrinder = new SpaceGrinder("zirconium-regrinder") {{
                    requirements(Category.production, with(PvItems.zirconium,35));
                    localizedName = "Zirconium Regrinder";
                    description = "Takes in Zirconium from the atmosphere, solid blocks hinder this grinder from working";
                    squareSprite = false;
                    health = 200;
                    liquidCapacity = 20;
                    consumeLiquid(PvLiquids.xenon,2f/60f).boost();
                    boostMult = 4.6f;
                    size = 2;
                    range = 1;
                    speedPerOre = 0.4f/60f/12f;
                    faction.add(PvFactions.Unix);
                    itemCapacity = 20;
                    outputItem = new ItemStack(PvItems.zirconium,5);
                    updateEffect = Fx.smokeCloud;
                    drawer = new DrawMulti(
                            new DrawRegion("-bottom"),
                            new DrawLiquidRegion() {{ this.suffix = "-liquid"; }},
                            new DrawDefault()
                    );
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
                    requirements(Category.production, with(PvItems.zirconium,60,Items.silicon,25));
                    localizedName = "Tetra Drill";
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
                    localizedName = "Spectrum Drill";
                    size = 4;
                    drillTime = 160;
                    tier = 3;
                    liquidBoostIntensity = 1f;
                    health = 790;
                    liquidCapacity = 5;
                    consumePower(195f/60f);
                }};
                rootHarvester = new PvDrill("root-harvester")
                {{
                    requirements(Category.production, with(Items.copper,15,Items.sand,15));
                    localizedName = "[gold]Root Harvester";
                    faction.add(PvFactions.Yggdrasil);
                    size = 2;
                    drillTime = 800;
                    tier = 4;
                    liquidBoostIntensity = 6f;
                    health = 625;
                    liquidCapacity = 10;
                    consumeLiquid(Liquids.cryofluid,2.5f/60f).boost();
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
                radiator = new PowerGrinder("radiator")
                {{
                    requirements(Category.power, with(PvItems.zirconium,110,PvItems.barium,65)); //Todo
                    localizedName = "Radiator";
                    powerProduction = 5/60f;
                    health = 195;
                    size = 2;
                    range = 2;
                    tier = 10;
                }};
                lithiumDegenerator = new ConsumeGenerator("lithium-degenerator")
                {{
                    requirements(Category.power, with(PvItems.zirconium,110,PvItems.barium,65)); //Todo
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
                    requirements(Category.power, with(PvItems.zirconium,140,PvItems.platinum,25,PvItems.lithium,100)); //Todo
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
                blastReactor = new ConsumeGenerator("blast-reactor")
                {{
                    requirements(Category.power, with(PvItems.zirconium,130,PvItems.nobelium,70)); //todo
                    localizedName = "Blast Reactor";
                    health = 780;
                    size = 3;
                    powerProduction = 940f/60f;
                    itemCapacity = 20;
                    consumeItem(PvItems.nobelium,2);
                    itemDuration = 4.2f*60f;
                    generateEffect = new MultiEffect(Fx.fire,Fx.fireSmoke);
                    generateEffectRange = 2;
                    effectChance = 0.3f;
                    consumeEffect = Fx.smokeCloud;
                }};
                subzeroReactor = new ConsumeGenerator("subzero-reactor")
                {{
                    requirements(Category.power, with(PvItems.lithium,300,PvItems.nobelium,100,PvItems.erbium,70)); //todo
                    localizedName = "SubZero Reactor";
                    health = 980;
                    size = 4;
                    powerProduction = 5200f/60f;
                    liquidCapacity = 50;

                    consumeLiquid(PvLiquids.liquidNitrogen,10f/60f);
                    consumeLiquid(Liquids.nitrogen,25f/60f);
                    itemDuration = 10.4f*60f;
                    drawer = new DrawMulti(
                            new DrawRegion("-bottom"),
                            new DrawLiquidTile(Liquids.nitrogen, 4f),
                            new DrawLiquidTile(PvLiquids.liquidNitrogen, 4f),
                            new DrawBubbles(Liquids.nitrogen.color.add(-0.2f,-0.2f,-0.2f)){{
                                sides = 10;
                                recurrence = 3f;
                                spread = 12;
                                radius = 2f;
                                amount = 40;
                            }},
                            new DrawRegion()
                    );
                    effectChance = 0.2f;
                    generateEffect = Fx.smoke;
                    consumeEffect = Fx.smokeCloud;
                    warmupSpeed = 0.0001f;
                }};
                feverReactor = new ItemVariableReactor("fever-reactor")
                {{
                    requirements(Category.power, with(PvItems.zirconium,375,Items.silicon,125,PvItems.lithium,50,PvItems.erbium,30)); //todo
                    localizedName = "Fever Reactor";
                    maxHeat = 40;
                    health = 3050;
                    size = 5;
                    powerProduction = 12700f/60f;
                    itemCapacity = 30;
                    liquidCapacity = 50;
                    consumeItem(PvItems.erbium,5);
                    consumeLiquid(PvLiquids.liquidNitrogen,25f/60f);
                    explosionMinWarmup = 0.5f;
                    explosionRadius = 8*20;
                    explosionDamage = 3050;
                    itemDuration = 2.2f*60f;
                    consumeEffect = Fx.smokeCloud;
                    drawer = new DrawMulti(
                            new DrawRegion("-bottom"),
                            new DrawLiquidTile(PvLiquids.liquidNitrogen, 4f),
                            new DrawRegion()
                    );
                }};
                siliconMassForge = new GenericCrafter("silicon-mass-forge")
                {{
                    requirements(Category.crafting, with(PvItems.zirconium,35,PvItems.lithium,20)); //todo
                    localizedName = "Silicon Mass Forge";
                    health = 175;
                    size = 2;
                    consumeItems(with(PvItems.barium,3));
                    consumeLiquid(Liquids.oil,15f/60f);
                    consumePower(45f/60f);
                    itemCapacity = 20;
                    craftTime = 3.3f*60f;
                    craftEffect = Fx.absorb;
                    outputItem = new ItemStack(Items.silicon,5);
                    drawer = new DrawMulti(
                        new DrawDefault(),
                        new DrawArcSmelt() {{
                            this.flameColor = Pal.darkMetal;
                            this.flameRad = 0.8f;
                        }}
                    );
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
                    craftEffect = Fx.fire;
                    heatRequirement = 6;
                    maxEfficiency = 5;
                    outputItem = new ItemStack(PvItems.nobelium,5);
                    drawer = new DrawMulti(
                            new DrawFlame(Pal.darkFlame) {{
                                this.flameRadius = 4;
                                this.flameRadiusIn = 2;
                            }},
                            new DrawDefault()
                    );
                }};
                nitrogenDistiller = new MultiCrafter("nitrogen-distiller")
                {{
                    requirements(Category.crafting, with(PvItems.zirconium,50,PvItems.platinum,30,Items.silicon,50)); //Todo
                    localizedName = "Nitrogen Distiller";
                    size = 2;
                    newConsumer(Liquids.nitrogen);
                    outputLiquid(Liquids.nitrogen,10f/60f);
                    consumeLiquid(Liquids.water,20f/60f);
                    newConsumer(PvLiquids.liquidNitrogen);
                    consumeLiquid(Liquids.water,10f/60f);
                    consumeLiquid(Liquids.nitrogen,10f/60f);
                    outputLiquid(PvLiquids.liquidNitrogen,10f/60f);
                    craftEffect = Fx.bubble;
                }};
                keroseneMixer = new GenericCrafter("kerosene-mixer")
                {
                    {
                        requirements(Category.crafting, with(PvItems.zirconium, 50, PvItems.platinum, 30, PvItems.lithium, 30));
                        localizedName = "Kerosene mixer";
                        health = 185;
                        size = 2;
                        hasLiquids = true;
                        consumeItems(with(PvItems.zirconium, 2));
                        consumeLiquid(Liquids.oil, 20 / (60 * 2f));
                        consumePower(20f / 60f);
                        itemCapacity = 10;
                        liquidCapacity = 10;
                        craftTime = 2f * 60f;
                        outputLiquid = new LiquidStack(PvLiquids.kerosene, 15 / (60 * 2f));
                        craftEffect = Fx.breakProp;
                        drawer = new DrawMulti(
                                new DrawLiquidRegion(PvLiquids.kerosene),
                                new DrawRegion("-rot") {{
                                    rotateSpeed = 3;
                                    spinSprite = true;
                                }},
                                new DrawDefault()
                        );
                    }};
                    carbonWeaver = new GenericCrafter("carbon-weaver")
                    {{
                        requirements(Category.crafting, with(PvItems.zirconium,150,PvItems.platinum,80,Items.silicon,50));
                        localizedName = "Carbon Weaver";
                        health = 1300;
                        size = 3;
                        hasLiquids = true;
                        consumeItems(with(PvItems.zirconium,7, Items.silicon, 5, PvItems.platinum, 5));
                        consumeLiquid(PvLiquids.kerosene, 25/(60*6.7f));
                        consumePower(280f/60f);
                        itemCapacity = 14;
                        liquidCapacity = 10;
                        craftTime = 6.7f*60f;
                        outputItem = new ItemStack(PvItems.carbonFiber, 3);
                        craftEffect = Fx.fallSmoke;
                        drawer = new DrawMulti(
                                new DrawRegion("-bottom"),
                                new DrawLiquidRegion(PvLiquids.kerosene),
                                new DrawDefault(),
                                new DrawArcSmelt(){{
                                    flameRad = 4;
                                }}
                        );
                }};
                    fractionIonizer = new HeatCrafter("fraction-ionizer")
                    {{
                        requirements(Category.crafting, with(PvItems.zirconium,50,PvItems.platinum,30,Items.silicon,50)); //TODO
                        localizedName = "Fraction Ionizer";
                        health = 775;
                        size = 3;
                        consumePower(145f/60f);
                        liquidCapacity = 50;
                        heatRequirement = 8;
                        maxEfficiency = 4;
                        hasLiquids = true;
                        consumeLiquid(Liquids.nitrogen,25f/60f);
                        outputLiquid = new LiquidStack(PvLiquids.xenon,15f/60f);
                        craftTime = 8f*60f;
                        craftEffect = Fx.explosion;
                        drawer = new DrawMulti(
                                new DrawRegion("-bottom"),
                                new DrawLiquidRegion(PvLiquids.xenon),
                                new DrawDefault()
                        );
                    }};
                quadRushForge = new HeatCrafter("quadrush-forge")
                {{
                    requirements(Category.crafting,with(PvItems.zirconium,1000));
                    localizedName = "Quadrush Forge";
                    health = 985;
                    size = 4;
                    consumePower(380f/60f);
                    itemCapacity = 20;
                    heatRequirement = 15;
                    maxEfficiency = 2;
                    craftTime = 60*2.5f;
                    loopSound = Sounds.spellLoop;
                    craftEffect = new MultiEffect(PvEffects.quadRushCraft,Fx.smokeCloud);
                    consumeItems(with(PvItems.carbonFiber,1,Items.silicon,5));
                    outputItem = new ItemStack(PvItems.rushAlloy,1);
                }};
                cascadeForge = new PvGenericCrafter("cascade-forge") {{
                    requirements(Category.crafting,with(PvItems.zirconium,10000));
                    localizedName = "Cascade Forge";
                    faction.add(PvFactions.Xeal);
                    health = 920;
                    size = 4;
                    loopSound = Sounds.lasercharge2;
                    consumePower(250f/60f);
                    itemCapacity = 20;
                    liquidCapacity = 50;
                    craftTime = 60*7.1f;
                    craftEffect = new MultiEffect(PvEffects.cascadeCraft,Fx.freezing,Fx.wet);
                    updateEffect = Fx.smoke;
                    consumeLiquid(PvLiquids.xenon,15f/60f);
                    consumeItems(with(PvItems.lithium,10,PvItems.erbium,5));
                    outputItem = new ItemStack(PvItems.tideAlloy,2);
                }};
                uberbulkForge = new PvGenericCrafter("uberbulk-forge") {{
                    requirements(Category.crafting,with(PvItems.zirconium,40000));
                    localizedName = "Uberbulk Forge";
                    faction.add(PvFactions.Mortikai);
                    health = 1420;
                    size = 4;
                    loopSound = Sounds.lasercharge2;
                    consumePower(1200f/60f);
                    itemCapacity = 200;
                    liquidCapacity = 50;
                    craftTime = 60*9.8f;
                    craftEffect = new MultiEffect(Fx.smokeCloud,Fx.explosion,PvEffects.uberbulkCraft);
                    updateEffect = Fx.smoke;
                    consumeItems(with(PvItems.zirconium,100,PvItems.lithium,40,PvItems.nobelium,20,PvItems.carbonFiber,10));
                    outputItem = new ItemStack(PvItems.bulkAlloy,5);
                    drawer = new DrawMulti(
                        new DrawDefault(),
                        new DrawFlame(Pal.redSpark)
                    );
                }};
                voidPressurizer = new PvGenericCrafter("void-pressurizer") {{
                    requirements(Category.crafting,with(PvItems.rushAlloy,75,PvItems.carbonFiber,190,PvItems.zirconium,2500));
                    localizedName = "Void Pressurizer";
                    faction.add(PvFactions.Nullis);
                    health = 1400;
                    size = 4;
                    loopSound = Sounds.pulse;
                    consumePower(1260f/60f);
                    squareSprite = false;
                    itemCapacity = 50;
                    liquidCapacity = 50;
                    craftTime = 60*2f;
                    craftEffect = new MultiEffect(PvEffects.nullisDeath.get(0),Fx.explosion);
                    updateEffect = Fx.wet;
                    consumeItems(with(PvItems.rushAlloy,1,PvItems.erbium,20));
                    outputLiquid = new LiquidStack(PvLiquids.concentratedVoid,8/60f);
                    hasLiquids = true;
                    drawer = new DrawMulti(
                            new DrawRegion("-liquid"),
                            new DrawLiquidRegion(PvLiquids.concentratedVoid),
                            new DrawDefault()
                    );
                }};
                keroseneHeater = new HeatProducer("kerosene-heater"){{
                    requirements(Category.crafting, with(PvItems.zirconium, 20, PvItems.platinum, 30)); //Todo

                    drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput());
                    rotateDraw = false;
                    size = 2;
                    localizedName = "Kerosene Heater";
                    heatOutput = 4f;
                    regionRotated1 = 1;
                    ambientSound = Sounds.hum;
                    liquidCapacity = 10;
                    craftTime = 5.8f*60;
                    consumeLiquid(PvLiquids.kerosene, 5/(60*5.8f));
                }};
                blastHeater = new HeatProducer("blast-heater")
                {{
                    requirements(Category.crafting, with(PvItems.zirconium, 80, PvItems.platinum, 150)); //Todo
                    localizedName = "Blast Heater";
                    health = 400;
                    size = 2;
                    liquidCapacity = 10;
                    consumeItem(PvItems.nobelium,1);
                    heatOutput = 8;
                    craftTime = 3.8f * 60f;

                }};
                xeroPointHeater = new HeatProducer("xero-point-heater")
                {{
                    requirements(Category.crafting, with(PvItems.zirconium,200,PvItems.nobelium,150,PvItems.platinum,50)); //Todo
                    localizedName = "Xero Point Heater";
                    health = 1120;
                    heatOutput = 15;
                    consumeLiquid(PvLiquids.xenon,10f/60f);
                    craftTime = 60*8.8f;
                    liquidCapacity = 30;
                    size = 3;
                }};

                hybridHeater = new HeatProducer("hybrid-heater")
                {{
                    requirements(Category.crafting, with(PvItems.zirconium, 50, PvItems.platinum, 100,PvItems.barium,60)); //Todo
                    localizedName = "Hybrid Heater";
                    health = 1085;
                    size = 3;
                    itemCapacity = 10;
                    liquidCapacity = 10;
                    consumeLiquid(PvLiquids.kerosene,10f/60f);
                    consumeItem(PvItems.nobelium,1);
                    heatOutput = 18;
                    craftTime = 6.5f * 60f;
                }};
                heatPathfinder = new HeatConductor("heat-pathfinder"){{
                    requirements(Category.crafting, with(PvItems.zirconium, 100));
                    localizedName = "Heat Pathfinder";
                    size = 2;
                    drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput(), new DrawHeatInput("-heat"));
                    regionRotated1 = 1;
                }};
                airTempT1 = new PvTemplate("aerial-t1") {{
                    requirements = with(PvItems.zirconium,100); //Todo
                    localizedName = "Tier 1 Aerial Template";
                    health = 70;
                    size = 1;
                }};
                airTempT2 = new PvTemplate("aerial-t2") {{
                    requirements = with(PvItems.zirconium,250); //Todo
                    localizedName = "Tier 2 Aerial Template";
                    health = 180;
                    size = 2;
                }};
                airTempT3 = new PvTemplate("aerial-t3") {{
                    requirements = with(PvItems.zirconium,800); //Todo
                    localizedName = "Tier 3 Aerial Template";
                    health = 780;
                    size = 3;
                }};
                airTempT4 = new PvTemplate("aerial-t4") {{
                    requirements = with(PvItems.zirconium,1200); //Todo
                    localizedName = "Tier 4 Aerial Template";
                    health = 1280;
                    size = 5;
                }};
                airTempT5 = new PvTemplate("aerial-t5") {{
                    requirements = with(PvItems.zirconium,1800); //Todo
                    localizedName = "Tier 5 Aerial Template";
                    health = 1780;
                    size = 7;
                }};
                templateMolder = new PvSelectiveConstructor("template-molder") {{
                    requirements(Category.units,with(PvItems.zirconium,250,Items.silicon,75)); //Todo
                    localizedName = "Template Molder";
                    health = 500;
                    size = 3;
                    minBlockSize = 1;
                    maxBlockSize = 3;
                    consumePower(120/60);
                    constructions.addAll(
                            airTempT1, airTempT2
                    );
                }};
                massTemplateMolder = new PvSelectiveConstructor("mass-template-molder") {{
                    requirements(Category.units,with(PvItems.zirconium,250,Items.silicon,75,PvItems.platinum,40)); //Todo
                    localizedName = "Mass Template Molder";
                    health = 1700;
                    size = 5;
                    minBlockSize = 1;
                    maxBlockSize = 4;
                    consumePower(300/60);
                    constructions.addAll(
                            airTempT1, airTempT2,airTempT3
                    );
                }};
                nueroSpawnPad = new BulkUnitFactory("nuero-spawn-pad")
                {{
                    requirements(Category.units,with(PvItems.zirconium,250,Items.silicon,75)); //Todo
                    localizedName = "Nuero Spawn Pad";
                    health = 1600;
                    size = 5;
                    consumePower(130f/60f);
                    itemCapacity = 3000;
                    liquidCapacity = 200;
                    selectionColumns = 2;
                    plans = new Seq<>().with(
                        //Particle Path
                        new PvUnitPlan(PvUnits.particle,10*60f,with(PvItems.lithium,20,Items.silicon,10),airTempT1),
                        //Hover Path
                            new PvUnitPlan(PvUnits.milli,10*60f,with(PvItems.zirconium,160,Items.silicon,60)),
                        //Container Path
                        new PvUnitPlan(PvUnits.pocket,10*60f,with(PvItems.zirconium,40,PvItems.lithium,20)),
                        //Naval Path
                        new PvUnitPlan(PvUnits.rivulet,10*60f,with(PvItems.zirconium,50,Items.silicon,30))
                    );
                }};
                branchMolder = new BulkUnitFactory("branch-molder")
                {{
                    requirements(Category.units,with(Items.copper,250,Items.silicon,75,PvItems.hardenedOak,40));
                    localizedName = "[#766e4d]Branch Molder";
                    faction.add(PvFactions.Yggdrasil);
                    health = 1600;
                    size = 5;
                    consumePower(120f/60f);
                    itemCapacity = 3000;
                    liquidCapacity = 200;
                    selectionColumns = 1;
                    plans = new Seq<>().with(

                            new PvUnitPlan(PvUnits.root,20*60f,with(PvItems.hardenedOak,20,Items.silicon,10))
                    );
                }};
                neuroRemolder = new RemolderFactory("neuro-remolder") {{
                    requirements(Category.units,with(PvItems.zirconium,500,PvItems.platinum,100,Items.silicon,40)); //Todo
                    localizedName = "Neuro Remolder";
                    addUpgrade(PvUnits.particle,PvUnits.snippet, (PvTemplate) airTempT2);
                    addUpgrade(PvUnits.milli,PvUnits.centi);
                    addUpgrade(PvUnits.pocket,PvUnits.container);
                    addUpgrade(PvUnits.rivulet,PvUnits.bourn);
                    addUpgrade(PvUnits.chime,PvUnits.carillon);
                    templateUseCount = 15;
                    health = 1200;
                    size = 3;
                    itemCapacity = 200;
                    constructTime = 60*20;
                    consumePower(200f/60f);
                    consumeItems(with(PvItems.barium,100,Items.silicon,80,PvItems.lithium,40));
                }};
                grandRemolder = new RemolderFactory("grand-remolder") {{
                    requirements(Category.units,with(PvItems.zirconium,1000,PvItems.platinum,700,PvItems.erbium,300,Items.silicon,200,PvItems.nobelium,80)); //Todo
                    localizedName = "Grand Remolder";
                    addUpgrade(PvUnits.snippet,PvUnits.fragment, (PvTemplate) airTempT3);
                    addUpgrade(PvUnits.centi,PvUnits.deci);
                    addUpgrade(PvUnits.container,PvUnits.capsule);
                    addUpgrade(PvUnits.bourn,PvUnits.tributary);
                    addUpgrade(PvUnits.carillon,PvUnits.knell);
                    templateUseCount = 10;
                    health = 3150;
                    size = 5;
                    itemCapacity = 1000;
                    constructTime = 60*40;
                    consumePower(1200f/60f);
                    consumeItems(with(PvItems.barium,600, PvItems.erbium,450,PvItems.platinum,200,PvItems.nobelium,100));
                }};
                etheriumRemolder = new RemolderFactory("etherium-remolder") {{
                    requirements(Category.units,with(PvItems.zirconium,1000,PvItems.platinum,700,PvItems.erbium,300,Items.silicon,200,PvItems.nobelium,80)); //Todo
                    localizedName = "Etherium Remolder";
                    addUpgrade(PvUnits.fragment,PvUnits.excerpt, (PvTemplate) airTempT4);
                    addUpgrade(PvUnits.deci,PvUnits.hecto);
                    addUpgrade(PvUnits.capsule,PvUnits.vault);
                    addUpgrade(PvUnits.tributary,PvUnits.loch);
                    addUpgrade(PvUnits.knell,PvUnits.peal);
                    templateUseCount = 5;
                    health = 3150;
                    size = 7;
                    itemCapacity = 1000;
                    constructTime = 60*60;
                    consumePower(1200f/60f);
                    consumeItems(with(PvItems.barium,2000,PvItems.nobelium,200,PvItems.platinum,500,Items.silicon,200,PvItems.carbonFiber,60));
                }};
                mythicalRemolder = new RemolderFactory("mythical-remolder") {{
                    requirements(Category.units,with(PvItems.zirconium,1000,PvItems.platinum,700,PvItems.erbium,300,Items.silicon,200,PvItems.nobelium,80)); //Todo
                    localizedName = "Mythical Remolder";
                    addUpgrade(PvUnits.excerpt,PvUnits.pericope, (PvTemplate) airTempT5);
                    addUpgrade(PvUnits.hecto,PvUnits.kilo);
                    addUpgrade(PvUnits.loch,PvUnits.atlantic);
                    templateUseCount = 2;
                    health = 3150;
                    size = 9;
                    itemCapacity = 1000;
                    constructTime = 60*60;
                    consumePower(1200f/60f);
                    consumeItems(with(PvItems.barium,5000,PvItems.erbium,1400,PvItems.rushAlloy,80,PvItems.carbonFiber,150));
                }};
                forceModifier = new EffectRefabricator("force-modifier") {{
                    requirements(Category.units,BuildVisibility.sandboxOnly,with(PvItems.zirconium,1000,PvItems.platinum,700,PvItems.erbium,300,Items.silicon,200,PvItems.nobelium,80)); //Todo
                    localizedName = "Force Modifier";
                    health = 3000;
                    size = 5;
                    statusEffect = PvStatusEffects.forceApply;
                    statusDuration = 60*60;
                    maxSize = 8*16;
                    constructTime = 60*25;
                    consumePower(210f/60f);
                    consumeItems(with(PvItems.erbium,25));
                }};
                minimalHousingUnit = new HousingUnitBlock("minimal-housing-unit") {{
                    requirements(Category.units,with(PvItems.zirconium,400,PvItems.lithium,250,Items.silicon,50)); //Todo
                    localizedName = "Minimal Housing Unit";
                    health = 540;
                    size = 3;
                    payloadSize = 2;
                    itemCapacity = 100;
                    consumePower(160);
                    consumeItems(with(PvItems.zirconium,40,Items.silicon,20));
                    constructTime = 60*45;
                }};
                regularHousingUnit = new HousingUnitBlock("regular-housing-unit") {{
                    requirements(Category.units,with(PvItems.zirconium,600,PvItems.lithium,400,Items.silicon,120,PvItems.nobelium,60)); //Todo
                    localizedName = "Regular Housing Unit";
                    health = 1400;
                    size = 5;
                    payloadSize = 4;
                    itemCapacity = 200;
                    consumePower(520);
                    consumeItems(with(PvItems.barium,160,Items.silicon,100,PvItems.nobelium,40));
                    constructTime = 60*70;
                }};
                densePayloadConveyor = new PayloadConveyor("dense-payload-conveyor")
                {{
                    requirements(Category.units,with(PvItems.carbonFiber,20)); //Todo
                    localizedName = "Dense Payload Conveyor";
                    payloadLimit = 5;
                    size = 3;
                }};
                densePayloadRouter = new PayloadRouter("dense-payload-router")
                {{
                    requirements(Category.units,with(PvItems.carbonFiber,20)); //Todo
                    localizedName = "Dense Payload Router";
                    payloadLimit = 5;
                    size = 3;
                }};
                denseConstructor = new Constructor("dense-constructor")
                {{
                    requirements(Category.units,with(PvItems.carbonFiber,20)); //Todo
                    localizedName = "Dense Constructor";
                    size = 3;
                }};
                denseDeconstructor = new PayloadDeconstructor("dense-deconstructor")
                {{
                    requirements(Category.units,with(PvItems.carbonFiber,20)); //Todo
                    localizedName = "Dense Deconstructor";
                    size = 3;
                }};
                denseLoader = new PayloadLoader("dense-loader")
                {{
                    requirements(Category.units,with(PvItems.carbonFiber,20)); //Todo
                    localizedName = "Dense Loader";
                    size = 3;
                }};
                denseUnloader = new PayloadUnloader("dense-unloader")
                {{
                    requirements(Category.units,with(PvItems.carbonFiber,20)); //Todo
                    localizedName = "Dense Unloader";
                    size = 3;
                    consumePower(100f/60f);
                }};
                bulkUnloader = new Unloader("bulk-unloader")
                {{
                    requirements(Category.effect, with(PvItems.zirconium,100,PvItems.lithium,150,Items.silicon,30));
                    localizedName = "Bulk Unloader";
                    health = 100;
                    size = 1;
                    itemCapacity = 5;
                }};
                coreHover = new PvCore("core-hover")
                {{
                    requirements(Category.effect, with(PvItems.zirconium,1000,PvItems.lithium,1500,Items.silicon,300));
                    localizedName = "Core Hover";
                    alwaysUnlocked = true;
                    unitType = PvUnits.micro;
                    health = 1350;
                    size = 3;
                    unitCapModifier = 30;
                    itemCapacity = 6000;
                    blackListFactions = true;
                    faction.addAll(PvFactions.Xeal,PvFactions.Nullis,PvFactions.Psy);
                }};
                coreElevate = new PvCore("core-elevate")
                {{
                    requirements(Category.effect, with(PvItems.zirconium,5000,PvItems.lithium,4000,Items.silicon,900,PvItems.nobelium,500));
                    localizedName = "Core Elevate";
                    unitType = PvUnits.infrared;
                    health = 3700;
                    size = 4;
                    unitCapModifier = 50;
                    itemCapacity = 11000;
                    blackListFactions = true;
                    faction.addAll(PvFactions.Xeal,PvFactions.Nullis,PvFactions.Psy);
                }};
                coreUpraise = new PvCore("core-upraise")
                {{
                    requirements(Category.effect, with(PvItems.zirconium,10000,PvItems.lithium,7000,Items.silicon,1500,PvItems.carbonFiber,1200,PvItems.nobelium,400));
                    localizedName = "Core Upraise";
                    unitType = PvUnits.spectrum;
                    health = 7300;
                    size = 5;
                    unitCapModifier = 84;
                    itemCapacity = 16000;
                    blackListFactions = true;
                    faction.addAll(PvFactions.Xeal,PvFactions.Nullis,PvFactions.Psy);
                }};
                coreSpark = new PvCore("core-spark")
                {{
                    requirements(Category.effect, with(PvItems.zirconium,5000,PvItems.lithium,4000,Items.silicon,900));
                    localizedName = "Core Spark";
                    alwaysUnlocked = true;
                    unitType = PvUnits.amp;
                    faction.add(PvFactions.Xeal);
                    health = 3000;
                    size = 4;
                    healTime = 40;
                    healPowerDrain = 200;
                    minHealCharge = 200;
                    unitCapModifier = 32;
                    itemCapacity = 20000;
                    conductivePower = true;
                    outputsPower = true;
                    consumePowerBuffered(30000);
                }};
                coreCharge = new PvCore("core-charge")
                {{
                    requirements(Category.effect, with(PvItems.zirconium,10000,PvItems.lithium,8000,Items.silicon,3000,PvItems.nobelium,1500,PvItems.platinum,500));
                    localizedName = "Core Charge";
                    unitType = PvUnits.volt;
                    faction.add(PvFactions.Xeal);
                    health = 5500;
                    size = 5;
                    unitCapModifier = 48;
                    healTime = 30;
                    healPowerDrain = 100;
                    minHealCharge = 500;
                    itemCapacity = 30000;
                    conductivePower = true;
                    outputsPower = true;
                    consumePowerBuffered(75000);
                }};
                coreSurge = new PvCore("core-surge")
                {{
                    requirements(Category.effect, with(PvItems.zirconium,20000,PvItems.lithium,15000,Items.silicon,8000,PvItems.nobelium,5000,PvItems.platinum,3000,PvItems.carbonFiber,100));
                    localizedName = "Core Surge";
                    unitType = PvUnits.watt;
                    faction.add(PvFactions.Xeal);
                    health = 7000;
                    size = 6;
                    unitCapModifier = 65;
                    itemCapacity = 45000;
                    healTime = 25;
                    warmupEffect = PvEffects.surgeSpawn;
                    spawnEffect = Fx.greenBomb;
                    conductivePower = true;
                    outputsPower = true;
                    consumePowerBuffered(160000);
                }};
                yggdrasilsHeartwood = new PvCore("yggdrasils-heartwood")
                {{
                    requirements(Category.effect, with(PvItems.hardenedOak,15000));
                    localizedName = "[#766e4d]Yggdrasil's Heartwood";
                    faction.add(PvFactions.Yggdrasil);
                    alwaysUnlocked = true;
                    unitType = PvUnits.wood;
                    health = 68000;
                    size = 6;
                    unitCapModifier = 16;
                    itemCapacity = 6000;
                }};

                coreWarden = new PvCore("core-warden") {{
                    requirements(Category.effect, with(PvItems.zirconium,5000,PvItems.lithium,4000,Items.silicon,900));
                    localizedName = "Core Warden";
                    faction.add(PvFactions.Psy);
                    alwaysUnlocked = true;
                    unitType = PvUnits.warden; //TODO : makae an actual coreUnit.
                    health = 6000;
                    size = 3;
                    unitCapModifier = 20;
                    itemCapacity = 15000;
                }};

                coreBunker = new PvCore("core-bunker") {{
                    requirements(Category.effect, with(PvItems.zirconium,5000,PvItems.lithium,4000,Items.silicon,900)); //TODO : COST
                    localizedName = "Core Warden";
                    faction.add(PvFactions.Mortikai);
                    alwaysUnlocked = true;
                    unitType = PvUnits.watt; //TODO : makae an actual coreUnit.
                    health = 7500;
                    size = 4;
                    unitCapModifier = 15;
                    itemCapacity = 8000;
                }};

                coreNull = new NullisCore("core-null")
                {{
                    requirements(Category.effect,with(PvItems.zirconium,500,PvItems.lithium,200,PvItems.platinum,100));
                    localizedName = "Core Null";
                    unitType = PvUnits.vessel;
                    defaultMiner = PvUnits.shadow;
                    alwaysUnlocked = true;
                    size = 3;
                    health = 2100;
                    unitCapModifier = 60;
                    itemCapacity = 100;
                    voidRadius = 22;
                    drawer = new DrawMulti(
                        new DrawLiquidStaticRegion(PvLiquids.concentratedVoid) {{
                            padding = 2;
                        }},
                        new DrawDefault()
                    );
                }};
                coreVoid = new NullisCore("core-void")
                {{
                    requirements(Category.effect,with(PvItems.zirconium,1400,PvItems.lithium,800,PvItems.platinum,400,PvItems.erbium,100,Items.silicon,100,PvItems.nobelium,100));
                    localizedName = "Core Void";
                    unitType = PvUnits.shell;
                    defaultMiner = PvUnits.shadow;
                    size = 4;
                    health = 4600;
                    armor = 4;
                    miners = 3;
                    unitCapModifier = 100;
                    itemCapacity = 200;
                    voidRadius = 32;
                    drawer = new DrawMulti(
                            new DrawLiquidStaticRegion(PvLiquids.concentratedVoid) {{
                                padding = 2;
                            }},
                            new DrawDefault()
                    );
                }};
                coreAbyss = new NullisCore("core-abyss") {{
                    requirements(Category.effect,with(PvItems.zirconium,6000,PvItems.lithium,4000,PvItems.platinum,3200,PvItems.erbium,2400,Items.silicon,2000,PvItems.nobelium,1800,PvItems.rushAlloy,1000));
                    localizedName = "Core Abyss";
                    unitType = PvUnits.puppet;
                    defaultMiner = PvUnits.shadow;
                    size = 5;
                    health = 8200;
                    armor = 5;
                    miners = 6;
                    unitCapModifier = 140;
                    itemCapacity = 500;
                    voidRadius = 44;
                    drawer = new DrawMulti(
                            new DrawLiquidStaticRegion(PvLiquids.concentratedVoid) {{
                                padding = 2;
                            }},
                            new DrawDefault()
                    );
                }};
                voidNode = new VoidBlock("void-node")
                {{
                    requirements(Category.effect,with(PvItems.zirconium,30));
                    localizedName = "Void Node";
                    size = 1;
                    health = 100;
                    voidRadius = 10;

                }};
                voidBeacon = new VoidBlock("void-bacon")
                {{
                    requirements(Category.effect,with(PvItems.zirconium,1500,PvItems.lithium,1000,PvItems.nobelium,300,PvItems.carbonFiber,100));
                    localizedName = "Void Beacon";
                    size = 4;
                    health = 1400;
                    voidRadius = 26;
                    drawer = new DrawMulti(
                            new DrawDefault(),
                            new PvDrawPulse("-black")
                    );
                }};
                voidExpander = new VoidExpand("void-expander") {{
                    requirements(Category.effect,with(PvItems.zirconium,3000,PvItems.lithium,1800,PvItems.nobelium,1000,PvItems.carbonFiber,500,PvItems.rushAlloy,250));
                    localizedName = "Void Expander";
                    description = "Grows. the More you feed it.";
                    size = 5;
                    health = 4500;
                    voidRadius = 30;
                    squareSprite = false;
                    consumeItem(Items.silicon,1);
                    itemDuration = 180;
                    voidGrowAmount = 1;
                    itemCapacity = 100;
                    drawer = new DrawMulti(
                        new DrawLiquidStaticRegion(PvLiquids.concentratedVoid){{
                                padding = 10;
                        }},
                        new DrawDefault(),
                        new DrawMoveRegion("-l"){{
                            moveY = 4;
                            progress = (b) -> Mathf.sin(Time.time/60);
                        }},
                        new DrawMoveRegion("-r"){{
                            moveY = -4;
                            progress = (b) -> Mathf.sin(Time.time/60);
                        }},
                        new DrawMoveRegion("-u"){{
                            moveX = -4;
                            progress = (b) -> Mathf.cos(Time.time/60);
                        }},
                        new DrawMoveRegion("-d") {{
                            moveX = 4;
                            progress = (b) -> Mathf.cos(Time.time/60);
                        }}
                    );
                }};
                harvestor = new PowerGrinder("harvestor")
                {{
                    requirements(Category.power,with(PvItems.zirconium,20,PvItems.lithium,10));
                    localizedName = "Harvestor";
                    faction.add(PvFactions.Nullis);
                    size = 1;
                    range = 6;
                    tier = 10;
                    powerProduction = 1f/60f;
                }};
                packer = new UnitPackerBlock("packer")
                {{
                    requirements(Category.units,with(PvItems.zirconium,50));
                    localizedName = "Packer";
                    faction.add(PvFactions.Nullis);
                    range = 8*4.5f;
                    size = 1;
                    itemCapacity = 20;
                    quickCalledUnits = Seq.with(
                            PvUnits.pocket,
                            PvUnits.container,
                            PvUnits.capsule
                    );
                }};
                oilGrinder = new LiquidGrinder("oil-grinder")
                {{
                    requirements(Category.production, with(PvItems.zirconium,35,PvItems.lithium,50,PvItems.barium,10));
                    localizedName = "Oil Grinder";
                    extractedLiquid = new LiquidStack(Liquids.oil,4f);
                    range = 3;
                    tier = 2;
                    size = 2;
                    speedPerOre = 0.15f/60f;
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
                zirconWallHuge = new PvWall("zircon-wall-huge") {{
                    requirements(Category.defense, with(PvItems.zirconium,128,PvItems.tideAlloy,2));
                    localizedName = "Huge Zircon Wall";
                    health = 7000;
                    faction.add(PvFactions.Xeal);
                    size = 3;
                }};
                siliconWall = new PvBattery("silicon-wall")
                {{
                    requirements(Category.defense, with(Items.silicon,4));
                    localizedName = "Silicon Wall";
                    health = 260;
                    damageMult = (power) -> Interp.circle.apply(power*-1+1)*0.5f+0.5f; /* 50% reduction */
                    consumePowerBuffered(500);
                }};
                siliconWallLarge = new PvBattery("silicon-wall-large")
                {{
                    requirements(Category.defense, with(Items.silicon,24));
                    localizedName = "Large Silicon Wall";
                    health = 1175;
                    size = 2;
                    damageMult = (power) -> Interp.circle.apply(power*-1+1)*(1f/3f)+(2f/3f); /* 33% reduction */
                    consumePowerBuffered(2000);
                }};
                siliconWallHuge = new PvBattery("silicon-wall-huge") {{
                    requirements(Category.defense, with(Items.silicon,128,PvItems.tideAlloy,2));
                    localizedName = "Huge Silicon Wall";
                    health = 5025;
                    faction.add(PvFactions.Xeal);
                    size = 3;
                    damageMult = (power) -> Interp.circle.apply(power*-1+1)*0.2f+0.8f; /* 20% reduction */
                    consumePowerBuffered(8000); /*e*/
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
                platinumWallHuge = new PvWall("platinum-wall-huge") {{
                    requirements(Category.defense, with(PvItems.platinum,128,PvItems.tideAlloy,4));
                    localizedName = "Huge Platinum Wall";
                    health = 9200;
                    faction.add(PvFactions.Xeal);
                    pierceReduction = 3;
                    size = 3;
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
                erbiumWallHuge = new PvWall("erbium-wall-huge") {{
                    requirements(Category.defense, with(PvItems.erbium,128,PvItems.tideAlloy,4));
                    localizedName = "Huge Erbium Wall";
                    health = 14600;
                    faction.add(PvFactions.Xeal);
                    absorbLasers = true;
                    size = 3;
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
                carbonWallHuge = new PvWall("carbon-wall-huge") {{
                    requirements(Category.defense, with(PvItems.carbonFiber,128,PvItems.tideAlloy,6));
                    localizedName = "Huge Carbon Wall";
                    health = 21250;
                    faction.add(PvFactions.Xeal);
                    pierceReduction = 3;
                    size = 3;
                }};
                voidWall = new VoidWall("void-wall") {{
                    requirements(Category.defense, with(PvItems.singularityAlloy,6));
                    faction.add(PvFactions.Nullis);
                    localizedName = "Void Wall";
                    description = "A wall concentrating Dark matter. its fragile, but absorbs anything it can.";
                    size = 1;
                    pierceReduction = 99;
                    absorbLasers = true;
                    health = 920;
                    regenRate = 10;
                }};
                voidWallLarge = new VoidWall("void-wall-large") {{
                    requirements(Category.defense, with(PvItems.singularityAlloy,30));
                    faction.add(PvFactions.Nullis);
                    localizedName = "Large Void Wall";
                    description = "A larger wall concentrating Dark matter. its more sturdy but still fragile. Can absorbs anything.";
                    size = 2;
                    pierceReduction = 99;
                    absorbLasers = true;
                    health = 2400;
                    regenRate = 20;
                }};
                tideWall = new PvWall("tide-wall") {{
                    requirements(Category.defense, with(PvItems.tideAlloy,24));
                    faction.add(PvFactions.Xeal);
                    localizedName = "Tide Wall";
                    description = "The Sturdiest of walls. its only drawback? its costly. Absorbs lasers.";
                    size = 1;
                    absorbLasers = true;
                    health = 4125;
                }};
                tideWallLarge = new PvWall("tide-wall-large") {{
                    requirements(Category.defense, with(PvItems.tideAlloy,120));
                    faction.add(PvFactions.Xeal);
                    localizedName = "Large Tide Wall";
                    description = "The Sturdiest of walls. its only drawback? its costly. Absorbs lasers.";
                    size = 2;
                    absorbLasers = true;
                    health = 17500;
                }};
                micropulsePump = new Pump("micropulse-pump")
                {{
                    requirements(Category.liquid, with(PvItems.zirconium,40,PvItems.lithium,60));
                    localizedName = "MicroPulse Pump";
                    size = 2;
                    pumpAmount = 0.1f;
                    liquidCapacity = 40;
                }};
                effluxPump = new Pump("efflux-pump")
                {{
                    requirements(Category.liquid, with(PvItems.zirconium,140,PvItems.lithium,100,PvItems.nobelium,40));
                    localizedName = "Efflux Pump";
                    size = 3;
                    pumpAmount = 14.5f/60f;
                    liquidCapacity = 40;
                }};

                concentratedRouter = new LiquidRouter("concentrated-router")
                {{
                    requirements(Category.liquid, with(PvItems.lithium,4,PvItems.zirconium,4));
                    localizedName = "Concentrated Router";
                    health = 60;
                    liquidCapacity = 40;
                }};
                concentratedJunction = new LiquidJunction("concentrated-junction")
                {{
                    requirements(Category.liquid, with(PvItems.lithium,4,PvItems.zirconium,4));
                    localizedName = "Concentrated Junction";
                    health = 60;
                    liquidCapacity = 40;
                }};
                concentratedConduit = new Conduit("concentrated-conduit")
                {{
                    requirements(Category.liquid, with(PvItems.lithium,4,PvItems.zirconium,2));
                    localizedName = "Concentrated conduit";
                    health = 60;
                    liquidCapacity = 30;
                    junctionReplacement = concentratedJunction;
                }};
                smallConcentratedTank = new LiquidRouter("small-concentrated-tank"){{
                    requirements(Category.liquid, with(PvItems.barium, 30,PvItems.lithium,10));
                    localizedName = "Concentrated Container";
                    size = 2;
                    solid = true;
                    liquidCapacity = 1500f;
                    liquidPadding = 1.5f;
                    health = 500;
                }};
                largeConcentratedTank = new LiquidRouter("large-concentrated-tank"){{
                    requirements(Category.liquid, with(PvItems.barium, 100,PvItems.lithium,50,PvItems.nobelium,20));
                    localizedName = "Concentrated Tank";
                    size = 4;
                    solid = true;
                    liquidCapacity = 6000f;
                    liquidPadding = 3.5f;
                    health = 1230;
                }};
                microLiquidTransportGate = new LiquidBridge("micro-liquid-transport-gate"){{
                    //todo adjust items
                    requirements(Category.liquid, with(PvItems.zirconium, 50, PvItems.lithium, 10));
                    size = 1;
                    localizedName = "Micro liquid transport gate";
                    health = 180;
                    liquidCapacity = 200;
                    range = 10;
                    //todo power consume
                    consumePower(5/60f);
                }};
                megaLiquidTransportGate = new LiquidBridge("mega-liquid-transport-gate"){{
                    //todo adjust items
                    requirements(Category.liquid, with(PvItems.zirconium, 50, PvItems.nobelium, 25, PvItems.lithium, 10));
                    size = 3;
                    localizedName = "Mega liquid transport gate";
                    health = 180;
                    liquidCapacity = 200;
                    //todo power consume
                    range = 120;
                    consumePower(330/60f);
                }};
                labelHandler = new PvLabelBlock("label-handler") {{
                    requirements(Category.logic, with(PvItems.zirconium,25,PvItems.lithium,20));
                    localizedName = "Label Handler";
                    description = "Everyone can read it with a little bit of power. need i say more?";
                    maxTextLength = 300;
                    maxNewlines = 30;
                    consumePower(10f/60f);
                    health = 100;
                }};
                piscoProcessor = new PvLogicBlock("pisco-processor")
                {{
                    requirements(Category.logic, with(PvItems.zirconium,75,PvItems.lithium,50,PvItems.nobelium,20));
                    localizedName = "Pisco Processor";
                    range = 8*20;
                    health = 90;
                    instructionsPerTick = 1;
                    maxInstructionsPerTick = 2;
                    maxInstructionScale = 5;
                    allStatements= Seq.with(
                            new Prov[]{
                                    //Input && Output
                                    LStatements.WriteStatement::new,
                                    LStatements.ReadStatement::new,
                                    //Unit Controll
                                    LStatements.UnitBindStatement::new,
                                    PvLogic.HealStatement::new,
                                    LStatements.UnitControlStatement::new,
                                    LStatements.UnitLocateStatement::new,
                                    LStatements.UnitRadarStatement::new,
                                    //Block Controll
                                    LStatements.SensorStatement::new,
                                    //Operator
                                    LStatements.SetStatement::new,
                                    LStatements.OperationStatement::new,
                                    LStatements.LookupStatement::new,
                                    //Flow Controll
                                    LStatements.EndStatement::new,
                                    LStatements.JumpStatement::new,
                                    PvLogic.DynamicJumpStatement::new,
                                    //Undefined
                                    PvLogic.CommentStatement::new
                            });
                }};
                voidReprocessingUnit = new PvLogicBlock("void-reprocessing-unit") {{
                    requirements(Category.logic, with(PvItems.zirconium,180,PvItems.lithium,120,Items.silicon,100,PvItems.nobelium,80));
                    localizedName = "Void Reprocessing Unit";
                    range = 8*32;
                    health = 90;
                    size = 2;
                    faction.add(PvFactions.Nullis);
                    instructionsPerTick = 5;
                    maxInstructionsPerTick = 20;
                    maxInstructionScale = 40;
                    allStatements= Seq.with(
                            new Prov[]{
                                    //Input && Output
                                    LStatements.WriteStatement::new,
                                    LStatements.ReadStatement::new,
                                    LStatements.PrintStatement::new,
                                    //Unit Controll
                                    LStatements.UnitBindStatement::new,
                                    PvLogic.HealStatement::new,
                                    PvLogic.ShieldStatement::new,
                                    LStatements.UnitControlStatement::new,
                                    LStatements.UnitLocateStatement::new,
                                    LStatements.UnitRadarStatement::new,
                                    //Block Controll
                                    LStatements.PrintFlushStatement::new,
                                    LStatements.SensorStatement::new,
                                    LStatements.RadarStatement::new,
                                    PvLogic.TransmitIptStatement::new,
                                    LStatements.GetLinkStatement::new,
                                    //Operator
                                    LStatements.SetStatement::new,
                                    LStatements.OperationStatement::new,
                                    LStatements.LookupStatement::new,
                                    //Flow Controll
                                    LStatements.EndStatement::new,
                                    LStatements.JumpStatement::new,
                                    PvLogic.DynamicJumpStatement::new,
                                    //Undefined
                                    PvLogic.CommentStatement::new
                            });
                    drawer = new DrawMulti(
                            new DrawLiquidStaticRegion(PvLiquids.concentratedVoid) {{
                                padding = 2f;
                                suffix = "-bottom";
                            }},
                            new DrawBubbles(Color.darkGray) {{
                                amount = 10;
                                radius = 3;
                                spread = 2;
                                timeScl = 120f;
                                recurrence = 4f;
                            }},
                            new DrawDefault()
                    );
                }};
                memoryByte = new MemoryBlock("memory-byte")
                {{
                    requirements(Category.logic, with(PvItems.zirconium,50,PvItems.lithium,100));
                    localizedName = "Memory Byte";
                    health = 145;
                    memoryCapacity = 8;
                }};
                statusSelector = new PvSelector("status-selector")
                {{
                    requirements(Category.logic, with(PvItems.zirconium,10,PvItems.lithium,10));
                    localizedName = "Status Selector";
                    health = 145;
                }};
                sus = new PvWall("sus")
                {{
                    requirements(Category.defense, BuildVisibility.sandboxOnly, with(PvItems.nobelium,1));
                    localizedName = "Sus";
                    description = """
                    its litteraly 1 peice of nobelium wi-
                    [teal]*[red]Emergency Meeting[]*[]
                    - [orange][[Orange] :[] [red]Red[] is [crimson]Sus[]!
                    - [red][[Red] :[] What.
                    [gold]Vote was cast. 3 remaining.
                    Vote was cast. 2 remaining.
                    Vote was cast. 1 remaining.[]
                    - [red][[Red] :[] You've got to be kiddin-
                    [grey]Red was the Imposter[]
                    """;
                    health = 8000;
                    armor = 10;
                    pierceReduction = 99;
                    absorbLasers = true;
                }};
                pocketContainer = new StorageBlock("pocket-container") {{
                    requirements(Category.effect,with(PvItems.zirconium,100,PvItems.lithium,80,PvItems.erbium,60,Items.silicon,50));
                    localizedName = "Pocket Container";
                    description = "A Container for everyday use. its only downside is that it cannot be merged with the core.";
                    coreMerge = false;
                    health = 380;
                    size = 2;
                    itemCapacity = 400;
                }};
                hydroProjector = new LiquidProjector("hydro-projector") {{
                    requirements(Category.effect,with(PvItems.zirconium,100,PvItems.lithium,80,Items.silicon,50));
                    localizedName = "Hydro Projector";
                    health = 140;
                    range = 7.3f*8;
                    defaultLiquid = Liquids.water;
                    extinguishMultiplier = 100f;
                    consumePower(25f/60f);
                    size = 1;
                    drawer = new DrawMulti(
                            new DrawDefault(),
                            new DrawRegion("-spine") {{
                                rotateSpeed = 1;
                                rotation = 0;
                            }},
                            new DrawRegion("-spine") {{
                                rotateSpeed = 1;
                                rotation = 90;
                            }},
                            new DrawRegion("-spine") {{
                                rotateSpeed = 1;
                                rotation = 180;
                            }},
                            new DrawRegion("-spine") {{
                                rotateSpeed = 1;
                                rotation = 270;
                            }}
                    );
                }};
                liquidProjector = new LiquidProjector("liquid-projector") {{
                    requirements(Category.effect, BuildVisibility.sandboxOnly,with(PvItems.zirconium,100,PvItems.lithium,80,PvItems.erbium,60,Items.silicon,50));
                    localizedName = "Liquid Projector";
                    health = 600;
                    extinguishMultiplier = 100;
                    range = 10f*8;
                    consumePower(50f/60f);
                    size = 1;
                    drawer = new DrawMulti(
                            new DrawDefault(),
                            new DrawTeamRegion()
                    );
                }};
                utilityProjector = new UtilityProjector("utility-projector")
                {{
                    requirements(Category.effect,with(PvItems.zirconium,250,PvItems.lithium,100,PvItems.nobelium,50));
                    size = 2;
                    range = 28*8;
                    consumePower(180f/60f);
                    localizedName = "Utility Projector";
                    statusEffects.addAll(
                            PvStatusEffects.mend,
                            PvStatusEffects.shield,
                            StatusEffects.overclock
                    );
                }};
            }
}
