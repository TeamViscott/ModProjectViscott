package viscott.content;

import arc.Events;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Objectives;
import viscott.gen.TechtreeObjective;
import viscott.world.teamResearch;

import static mindustry.content.TechTree.*;
import static viscott.content.PvBlocks.*;
import static viscott.content.PvTurrets.*;
import static mindustry.content.Items.*;
import static viscott.content.PvItems.*;
import static mindustry.content.Liquids.*;
import static viscott.content.PvLiquids.*;
import static viscott.content.PvUnits.*;

public class ProjectViscottTechTree {
    public static void load()
    {
        PvPlanets.vercilus.techTree = nodeRoot("Vercilus",PvPlanets.vercilus,() -> {
            node(PvFactions.Xeal.icon);
            node(PvFactions.Mortikai.icon);
            node(PvFactions.Nullis.icon);
            node(PvFactions.Azulex.icon);
            node(PvFactions.Psy.icon);
            node(zirconium,()->{
                node(water,()->{
                    node(nitrogen,()->{
                        node(liquidNitrogen);
                    });
                    node(oil,()-> {
                        node(kerosene, () -> {
                            node(xenon);
                        });
                    });
                });
                node(lithium,()->{
                    node(platinum,()->{
                        node(carbonFiber,()->{
                            node(rushAlloy);
                        });
                    });
                    node(erbium);
                });
                node(barium,()->{
                    node(silicon,()->{
                        node(nobelium);
                    });
                });
            });
            node(coreHover,()-> {
                node(harvestGrinder,()->{
                    node(micromassConveyor,()-> {
                        node(massJunction,() -> {
                            node(pocketContainer,()->{
                                node(bulkUnloader);
                            });
                        });
                        node(massRouter,() -> {
                            node(microTransportGate,()->{
                                node(megaTransportGate);
                                node(nueromassConveyor,() -> {
                                    node(megaMassConveyor);
                                    node(megaMassJunction);
                                    node(megaMassRouter);
                                });
                            });
                        });
                    });
                    node(harvestDrill,()->{
                        node(tetraDrill,()->{
                            node(spectrumDrill);
                        });
                        node(behemothGrinder);
                        node(oilGrinder);
                    });
                    node(concentratedConduit,()->{
                        node(concentratedJunction,()->{
                            node(smallConcentratedTank,()->{
                                node(largeConcentratedTank);
                            });
                        });
                        node(concentratedRouter,()->{
                            node(microLiquidTransportGate,()->{
                                node(megaLiquidTransportGate);
                            });
                        });
                        node(micropulsePump,()->{
                            node(effluxPump);
                        });
                    });
                });
                node(coreElevate,()->{
                    node(coreUpraise);
                });
                node(splinter,()->{
                    node(zirconWall,()->{
                        node(zirconWallLarge);
                        node(siliconWall,()-> {
                            node(siliconWallLarge);
                        });
                        node(platinumWall,()->{
                            node(platinumWallLarge);
                            node(erbiumWall,()->{
                                node(erbiumWallLarge);
                            });
                            node(carbonWall,()->{
                                node(carbonWallLarge);
                            });
                        });
                    });
                    node(shatter,()->{
                        node(shredder);
                        node(glaive,()->{
                            node(hel);
                            node(reaper);
                        });
                    });
                    node(euro,()->{
                        node(phantom);
                        node(marksman);
                        node(nuero,()->{
                            node(jaeger);
                            node(razor,()->{
                                node(rainmaker);
                                node(xacto,()->{
                                    node(shuttle);
                                });
                            });
                        });
                    });
                    node(snap,()->{
                        node(spring);
                        node(hourglass,()->{
                            node(striker);
                            node(falarica,()->{
                                node(javelin);
                                node(sumaya);
                            });
                        });
                    });
                });
                node(templateMolder,()->{
                    node(densePayloadConveyor,()->{
                        node(densePayloadRouter,()->{
                            node(denseConstructor,()-> {
                                node(denseDeconstructor);
                            });
                            node(denseLoader,()->{
                                node(denseUnloader);
                            });
                        });
                    });
                    node(nueroSpawnPad,()->{
                        node(neuroRemolder,()->{
                            node(grandRemolder);
                            node(regularHousingUnit);
                            node(forceModifier);

                        });
                        node(minimalHousingUnit);
                        node(particle,()->{
                            node(snippet,()->{
                                node(fragment,()->{
                                    node(excerpt,()->{
                                        node(pericope);
                                    });
                                });
                            });
                        });
                        node(milli,()->{
                            node(centi,()->{
                                node(deci,()->{
                                    node(hecto,()->{
                                        node(kilo);
                                    });
                                });
                            });
                        });
                        node(chime,()->{
                            node(carillon,()->{
                                node(knell,()->{
                                    node(peal);
                                });
                            });
                        });
                    });
                });
                node(piscoProcessor,()->{
                    node(labelHandler);
                    node(memoryByte);
                    node(statusSelector);
                });
            });
            node(siliconMassForge,()->{
                node(heatPathfinder,()->{
                    node(keroseneHeater,()->{
                        node(xeroPointHeater);
                    });
                    node(blastHeater,()->{
                        node(hybridHeater);
                    });
                });
                node(particalAccelerator,()->{
                    node(carbonWeaver,()->{
                        node(quadRushForge);
                    });
                });
                node(nitrogenDistiller,()->{
                    node(fractionIonizer);
                    node(utilityProjector);
                });
                node(keroseneMixer);
            });
            node(opticalNode,()->{
                node(auditoryNode);
                node(compressedBattery);
                node(radiator,()->{
                    node(smallCarbonPanel,()->{
                        node(largeCarbonPanel);
                    });
                });
                node(lithiumDegenerator,()->{
                    node(keroseneGenerator,()->{
                        node(subzeroReactor);
                    });
                    node(blastReactor,()->{
                        node(feverReactor);
                    });
                });
            });
        });
        PvFactions.Psy.techTree = nodeRoot ("Psy", PvFactions.Psy.icon,() -> {
            node(PvFactions.Yggdrasil.icon);
        });
        PvFactions.Yggdrasil.techTree = nodeRoot ("[gold]Yggdrasil", PvFactions.Yggdrasil.icon,() -> {
            node(PvItems.hardenedOak,() -> {

            });
            node(PvItems.darkMatter,() -> {

            });
            node(yggdrasilsHeartwood,() -> {
                node(PvUnits.wood,() -> {

                });
            });
        });
        PvFactions.Xeal.techTree = nodeRoot("Xeal", PvFactions.Xeal.icon,() -> {
            node(zirconWallHuge, Seq.with(TechtreeObjective.newObjective(zirconWallLarge)),() -> {
                node(siliconWallHuge,Seq.with(TechtreeObjective.newObjective(siliconWallLarge)),() -> {});
                node(platinumWallHuge,Seq.with(TechtreeObjective.newObjective(platinumWallLarge)),() -> {});
                node(erbiumWallHuge,Seq.with(TechtreeObjective.newObjective(erbiumWallLarge)),() -> {});
                node(carbonWallHuge,Seq.with(TechtreeObjective.newObjective(carbonWallLarge)),() -> {});
            });
        });
        PvFactions.Nullis.techTree = nodeRoot("Nullis", PvFactions.Nullis.icon,() -> {
            node(auditoryNode,() -> {

            });
        });

        Events.on(EventType.ResearchEvent.class,(res) -> {
             if (res.content instanceof teamResearch t && t.refTeam.techTree != null)
             {
                 t.refTeam.techTree.planet = PvPlanets.vercilus;
                 Vars.ui.research.rebuildTree(t.refTeam.techTree);
             }
        });

    }
}
