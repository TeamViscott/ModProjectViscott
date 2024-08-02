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
            description = "A faction with an incredible Technological Advantage.\n" +
                    "[green]Positive Attributes : \n" +
                    "   Larger Range of Units and Turrets\n" +
                    "   Option to select type of Target in its faction Turrets\n" +
                    "   Energy Core's\n" +
                    "[red]Negative Attributes : \n" +
                    "   Overall Weaker Core Units";
        }};
        Mortikai = new PvFaction("mortikai",Color.valueOf("#811f1f")){{
            description = "This faction has solely centered in offense and defense since ancient times. They are specified in defense in a different way than psy.\n" +
                    "[green]Positive Attributes : \n" +
                    "   Stronger and Tougher Units\n" +
                    "   Bulk Crafting\n" +
                    "   Natural Core Protection\n" +
                    "[red]Negative Attributes : \n" +
                    "   Weaker Turrets\n" +
                    "   No access to Logic";
        }};
        Azulex = new PvFaction("azulex",Color.valueOf("#5a56f1")){{
            description = "Azulex is a faction that has learnt to bend liquids and transport them with ease.\n"+
                    "[green]Positive Attributes : \n" +
                    "   Large array of Liquids and Puddle's with special effects\n" +
                    "   More Liquid Transport options\n" +
                    "   More Liquid Turrets and Defenses\n" +
                    "[red]Negative Attributes : \n" +
                    "   Limited Item Transportation\n" +
                    "   No Item Turrets";
        }};
        Nullis = new PvFaction("nullis",Color.valueOf("#ffffff")){{
            description = "Having found a way to extract value from Black Holes made them an unstable Faction.\n" +
                    "[green]Positive Attributes : \n" +
                    "   Unit Storage and Core Storage is Linked\n" +
                    "   Void Area's\n" +
                    "   Near Endless Growth\n" +
                    "[red]Negative Attributes : \n" +
                    "   Base Core Capacity is Low\n" +
                    "   Slower Startup";
            info = "Nullis is a Faction of Units that have found a way to use Black Hole's in an Efficient way.\n" +
                    "Once a Small Faction with nothing more than a few Units and some machinery to mess around with,\n" +
                    "is now a Force to be reckoned with.\nTheir expansion is close to non once they set up base.";
        }};
        Psy = new PvFaction("psy",Color.valueOf("#e892d1")){{
            description = "The Psy faction has been able to create extremely powerful shields and walls. They are specified in defense in a different way than mortikai\n" +
                    "[green]Positive Attributes : \n" +
                    "   Incredible Defenses and more Turret options\n" +
                    "   Compact Factory's\n" +
                    "   Core Shield\n" +
                    "[red]Negative Attributes : \n" +
                    "   Weaker Unit's\n" +
                    "   Low Unit Cap";
        }};
        Yggdrasil = new PvFaction("team-yggdrasil",Color.valueOf("#766e4d")) {{
            description = "[#e892d1]Psy[][pink] Sub Faction][]\nFollowers of The [green]Omamori[]. they use nature to defend and harvest resources.";
            info = "once upon a time there was the [green]Omamori[]. the Peaceful giant who loved to move around and nurture the wildlife around him.\n" +
                    "Over time. he accumulated followers who called themselves the Yggdrasil's, who wanted to follow in the Giants footsteps.\n" +
                    "And so the Giant helped them build in a way as to not hurt mother nature.";
            isExtraFaction = true;
        }};
        Unix = new PvFaction("unix",Color.purple) {{ // LOL
            description = """
            [pink][Extra Faction][]\nInterdimensional Travel goes brrrrr
            
            The Unix, a special faction consistant of units often built for space travel, their strength lies with their Mothership,
            a unit as large as a planet that is the hub for all cores and expeditions.
            [green]Positive Attributes :
                extraterrestrial units (units outside planet)
                Depoless Grinders
            [red]Negative Attributes :
                Reliance on the Mothership
                Requires a lot of Space
            """;
            isExtraFaction = true;
        }};
    }
}
