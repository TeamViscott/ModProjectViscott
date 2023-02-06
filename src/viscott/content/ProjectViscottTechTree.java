package viscott.content;

import mindustry.content.TechTree;

import static mindustry.content.TechTree.nodeRoot;

public class ProjectViscottTechTree {
    public static void load()
    {
        PvPlanets.vercilus.techTree = nodeRoot("Vercilus",PvBlocks.coreHover,() -> {

        });
    }
}
