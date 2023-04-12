package viscott.content;

import mindustry.Vars;
import viscott.types.PvTeam;

import static mindustry.content.TechTree.*;
import static viscott.content.PvBlocks.*;

public class ProjectViscottTechTree {
    public static void load()
    {
        PvPlanets.vercilus.techTree = nodeRoot("Vercilus",coreHover,() -> {

        });
        PvTeams.Xeal.techTree = nodeRoot("Xeal",coreHover,() -> {
            node(auditoryNode,() -> {

            });
        });
    }
}
