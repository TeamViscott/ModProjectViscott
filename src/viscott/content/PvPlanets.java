package viscott.content;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.content.Weathers;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.HexMesh;
import mindustry.maps.planet.AsteroidGenerator;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.type.Planet;
import mindustry.type.Weather;
import viscott.gen.VercilusPlanetGenerator;

import static mindustry.Vars.content;

public class PvPlanets{
    public static Planet
        vercilus
            ;
    public static void load()
    {
        for(var p : content.planets().items)
            p.hiddenItems.addAll(PvItems.vercilusOnlyItems);
        vercilus = new Planet("vercilus", Planets.sun,1f,2)
        {{
            localizedName = "Vercilus";
            generator = new VercilusPlanetGenerator();
            lightColor = Color.valueOf("ffffff");
            alwaysUnlocked = true;
            tidalLock = false;
            accessible = true;
            meshLoader = () -> new HexMesh(this, 5);
            atmosphereColor = Color.valueOf("ffffff");
            startSector = 15;
            totalRadius = 50f;
            clipRadius = 4;
            lightColor = Color.valueOf("aaaaaa");
            atmosphereRadIn = -0.01f;
            atmosphereRadOut = 0.5f;
            landCloudColor = Color.valueOf("ffffff");
            bloom = true;
            hiddenItems.addAll(content.items()).removeAll(PvItems.vercilusItems);
            iconColor = atmosphereColor = Pal.heal;
            alwaysUnlocked = true;
            ruleSetter = r -> {
            };
        }};
    }
}
