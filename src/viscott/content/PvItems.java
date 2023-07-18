package viscott.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.type.Item;

import static mindustry.content.Items.silicon;

public class PvItems {
    public static Item
    zirconium,lithium,barium,nobelium,platinum,erbium,carbonFiber,copium,
            rushAlloy,tideAlloy,bulkAlloy,darkMatter,hardenedOak
    ;
    public static Seq<Item> vercilusItems = new Seq<Item>(),
                            vercilusOnlyItems = new Seq<Item>();
    public static void load()
    {
        zirconium = new Item("zirconium"){{
            localizedName = "Zirconium";
            color = Color.valueOf("6f6d83");
            hardness = 1;
        }};
        lithium = new Item("lithium"){{
            localizedName = "Lithium";
            color = Color.valueOf("47463d");
            hardness = 1;
        }};
        erbium = new Item("erbium"){{
            localizedName = "Erbium";
            color = Color.valueOf("aaadaf");
            hardness = 2;
        }};
        platinum = new Item("platinum"){{
            localizedName = "Platinum";
            color = Color.valueOf("d0d6db");
            hardness = 2;
        }};
        // ------------------------------------
        nobelium = new Item("nobelium"){{
            localizedName = "Nobelium";
            color = Color.valueOf("ef525b");
        }};
        barium = new Item("barium"){{
            localizedName = "Barium";
            color = Color.valueOf("666558");
        }};
        carbonFiber = new Item("carbon-fiber"){{
            localizedName = "Carbon Fiber";
            color = Color.valueOf("3d3e47");
        }};
        copium = new Item("copium")
        {{
            localizedName = "Copium";
            color = Pal.redLight;
            explosiveness = 10;
            flammability = 1;
            charge = 100;
            radioactivity = -2;
        }};
        rushAlloy = new Item("rush-alloy")
        {{
            localizedName = "Rush Alloy";
            color = Team.sharded.color;
        }};
        tideAlloy = new Item("tide-alloy")
        {{
            localizedName = "Tide Alloy";
            color = Team.green.color;
        }};
        bulkAlloy = new Item("bulk-alloy")
        {{
            localizedName = "Bulk Alloy";
            color = Pal.neoplasm1;
        }};
        //fun item mainly for omamori/omai and neuro/jaeger
        darkMatter = new Item("dark-matter")
        {{
            localizedName = "Dark Matter";
            description = "A dark mystical material, durring the creation of [green]Omamori[], [gold]Yggdrasil[] Made Dark matter for [green]Omamori[]";
            color = Color.valueOf("011414");
            explosiveness = 100;
            radioactivity = 40;
        }};
        hardenedOak = new Item("hardened-oak")
        {{
            localizedName = "[gold]Hardened Oak";
            color = Color.valueOf("392f32");
            explosiveness = -10;
            flammability = 2;
            radioactivity = -4;
        }};

        vercilusItems.addAll(zirconium,lithium,barium,silicon,nobelium,platinum,erbium,carbonFiber,rushAlloy,tideAlloy);
        vercilusOnlyItems = vercilusItems.copy();
        vercilusOnlyItems.remove(silicon);
    }
}
