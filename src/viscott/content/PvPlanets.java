package viscott.content;

import arc.graphics.Color;
import mindustry.content.Blocks;
import mindustry.content.Planets;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.HexMesh;
import mindustry.type.Planet;
import mindustry.world.meta.Env;
import viscott.gen.VercilusPlanetGenerator;
import viscott.utilitys.PvUtil;
import viscott.world.meta.PvEnv;

import static mindustry.Vars.*;

public class PvPlanets{
    public static Planet
        vercilus
            ;
    public static void load()
    {
        content.planets().each(p->{p.hiddenItems.addAll(PvItems.vercilusOnlyItems);});

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
                r.bannedBlocks.add(Blocks.air);
            };
            defaultEnv |= PvEnv.Viscott;
            defaultCore = PvBlocks.coreHover;
        }};

        /*
            new Planet("test0",Planets.sun,1f,0);
            new Planet("test1",Planets.sun,1f,1);
            new Planet("test2",Planets.sun,1f,2);
            new Planet("test3",Planets.sun,1f,3);
            new Planet("test4",Planets.sun,1f,4);
         */
    }
}
