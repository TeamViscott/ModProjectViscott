package viscott.content;

import arc.graphics.Color;
import viscott.types.PvFaction;


public class PvFactions {
    public static PvFaction
        Xeal,Mortikai,Azulex,Nullis,Psy,
            /*Extra's*/
            Yggdrasil,Unix
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
            description = "Having found a way to extract value from Black Holes made them an unstable Faction.\n" +
                    "[green]Positive Attributes : \n" +
                    "   Unit Storage and Core Storage is Linked\n" +
                    "   Void Area's\n" +
                    "   Near Endless Growth\n" +
                    "[red]Negative Attributes : \n" +
                    "   Base Core Capacity is Low\n" +
                    "   Slower Startup\n";
            info = "Nullis is a Faction of Units that have found a way to use Black Hole's in an Efficient way.\n" +
                    "Once a Small Faction with nothing more than a few Units and some machinery to mess around with,\n" +
                    "is now a Force to be reckoned with.\nTheir expansion is close to non once they set up base.";
        }};
        Psy = new PvFaction("psy",Color.valueOf("#e892d1")){{
            description = "The Psy faction has been able to create extremely powerful shields and walls. They are specified in defense in a different way than mortikai";
        }};
        Yggdrasil = new PvFaction("team-yggdrasil",Color.valueOf("#766e4d")) {{
            description = "[#e892d1]Psy[][pink] Sub Faction][]\nFollowers of The [green]Omamori[]. they use nature to defend and harvest resources.";
            info = "once upon a time there was the [green]Omamori[]. the Peaceful giant who loved to move around and nurture the wildlife around him.\n" +
                    "Over time. he accumulated followers who called themselves the Yggdrasil's, who wanted to follow in the Giants footsteps.\n" +
                    "And so the Giant helped them build in a way as to not hurt mother nature.";
        }};
        /*Unix = new PvFaction("unix",Color.purple) {{
            description = "[pink][Extra Faction][]\nInterdimensional Travel goes brrrrr";
        }};*/
    }
}
