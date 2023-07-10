package viscott.content;

import arc.graphics.Color;
import viscott.types.PvFaction;


public class PvFactions {
    public static PvFaction
        Xeal,Mortikai,Azulex,Nullis,Psy
            ;
    public static void load()
    {
        Xeal = new PvFaction("xeal", Color.valueOf("#57d87e")) {{
            description = "A faction with an incredible Technological Advantage";
        }};
        Mortikai = new PvFaction("mortikai",Color.valueOf("#811f1f")){{
            description = "This faction has solely centered in offense and defense since ancient times. They are specified in defense in a different way than psy";
        }};
        Azulex = new PvFaction("azulex",Color.valueOf("#5a56f1")){{
            description = "Azulex is a faction that has learnt to bend liquids and transport them with ease.";
        }};
        Nullis = new PvFaction("nullis",Color.valueOf("#ffffff")){{
            description = "Having found a way to extract value from Black Holes made them an unstable Faction.";
            info = "Nullis is a Faction of Robots wich have found a way to harness the Power of a Black Hole.\n" +
                    "With that they have constructed and improved themselfs to not rely as much on the usual\n" +
                    "resouces but rather the black hole energy\n" +
                    "with every Unit in Nullis using a microscopical black hole as their core.";
        }};
        Psy = new PvFaction("psy",Color.valueOf("#e892d1")){{
            description = "The Psy faction has been able to create extremely powerful shields and walls. They are specified in defense in a different way than mortikai";
        }};
    }
}
