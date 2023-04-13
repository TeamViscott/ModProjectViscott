package viscott.content;

import arc.Core;
import arc.Events;
import arc.input.KeyCode;
import mindustry.Vars;
import mindustry.content.TechTree;
import mindustry.game.EventType;
import viscott.types.PvTeam;
import viscott.world.teamResearch;

import static mindustry.content.TechTree.*;
import static viscott.content.PvBlocks.*;

public class ProjectViscottTechTree {
    public static void load()
    {
        PvPlanets.vercilus.techTree = nodeRoot("Vercilus",coreHover,() -> {
            node(PvTeams.Xeal.icon);
            node(PvTeams.Psy.icon);
            node(PvTeams.Nullis.icon);
            node(PvTeams.Mortikai.icon);
            node(PvTeams.Azulex.icon);
        });
        PvTeams.Xeal.techTree = nodeRoot("Xeal",PvTeams.Xeal.icon,() -> {
            node(auditoryNode,() -> {

            });
        });
        PvTeams.Nullis.techTree = nodeRoot("Nullis",PvTeams.Nullis.icon,() -> {
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
