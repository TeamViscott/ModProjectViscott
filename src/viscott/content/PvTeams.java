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
        Xeal = new PvTeam(11,"xeal", Color.valueOf("#57d87e")) {{
            description = "A powerfull Faction with a lot of defensive and offensive potential.";
        }};
        Mortikai = new PvTeam(12,"mortikai",Color.valueOf("#811f1f")){{
            description = "The Technological Advantage has given this Faction an edge above all other Factions.";
        }};
        Azulex = new PvTeam(13,"azulex",Color.valueOf("#5a56f1")){{
            description = "No Description Provided - Azulex";
        }};
        Nullis = new PvTeam(14,"nullis",Color.valueOf("#ffffff")){{
            description = "Having found a way to extract value from Black Holes made them an unstable Faction.";
            info = "Nullis is a Faction of Robots wich have found a way to harness the Power of a Black Hole.\n" +
                    "With that they have constructed and improved themselfs to not rely as much on the usual\n" +
                    "resouces but rather the black hole energy\n" +
                    "with every Unit in Nullis using a microscopical black hole as their core.";
        }};
        Psy = new PvTeam(15,"psy",Color.valueOf("#e892d1")){{
            description = "No Description Provided - Psy";
        }};
    }
}
