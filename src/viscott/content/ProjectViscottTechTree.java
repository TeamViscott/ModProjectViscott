package viscott.content;

import arc.Events;
import mindustry.Vars;
import mindustry.game.EventType;
import viscott.world.teamResearch;

import static mindustry.content.TechTree.*;
import static viscott.content.PvBlocks.*;

public class ProjectViscottTechTree {
    public static void load()
    {
        PvPlanets.vercilus.techTree = nodeRoot("Vercilus",PvPlanets.vercilus,() -> {
            node(PvFactions.Xeal.icon);
            node(PvFactions.Mortikai.icon);
            node(PvFactions.Nullis.icon);
            node(PvFactions.Azulex.icon);
            node(PvFactions.Psy.icon);
            node(coreHover,()-> {
                node(harvestGrinder,()->{
                    node(micromassConveyor,()-> {
                        node(massJunction);
                        node(massRouter);
                    });
                    node(harvestDrill);
                });
                node(coreElevate,()->{
                    node(coreUpraise);
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
            node(auditoryNode,() -> {

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
