package viscott.content;

import static mindustry.content.TechTree.*;
import static viscott.content.PvBlocks.*;

public class ProjectViscottTechTree {
    public static void load()
    {
        PvPlanets.vercilus.techTree = nodeRoot("Vercilus",coreHover,() -> {

        });
    }
}
