package viscott.content;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Font;
import arc.graphics.g2d.TextureRegion;
import arc.math.geom.Vec2;
import arc.util.Log;
import arc.util.Scaling;
import mindustry.game.Team;
import mindustry.game.Teams;
import viscott.types.PvTeam;

import java.util.Scanner;



public class PvTeams {
    public static PvTeam
        Xeal,Mortikai,Azulex,Nullis,Psy
            ;
    public static void load()
    {
        Xeal = new PvTeam(11,"xeal", Color.valueOf("#57d87e")) {{}};
        Mortikai = new PvTeam(12,"mortikai",Color.valueOf("#811f1f")){{}};
        Azulex = new PvTeam(13,"azulex",Color.valueOf("#5a56f1")){{}};
        Nullis = new PvTeam(14,"nullis",Color.valueOf("#ffffff")){{}};
        Psy = new PvTeam(15,"psy",Color.valueOf("#e892d1")){{}};
    }
}
