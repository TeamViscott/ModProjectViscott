package viscott.content;

import mindustry.content.Planets;
import mindustry.graphics.Pal;
import mindustry.type.Planet;

public class PvPlanets {
    public static Planet
        vercilus
            ;
    public static void load()
    {
        vercilus = new Planet("Vercilus", Planets.sun,1.4f,2)
        {{
            iconColor = atmosphereColor = Pal.heal;
            alwaysUnlocked = true;
        }};
    }
}
