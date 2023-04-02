package viscott.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.graphics.Pal;
import mindustry.type.Item;

import static mindustry.content.Items.silicon;

public class PvItems {
    public static Item
    zirconium,lithium,barium,nobelium,platinum,erbium,carbonFiber,copium
    ;
    public static Seq<Item> vercilusItems = new Seq<Item>(),
                            vercilusOnlyItems = new Seq<Item>();
    public static void load()
    {
        nobelium = new Item("nobelium"){{
            localizedName = "Nobelium";
            color = Color.valueOf("ef525b");
        }};
        barium = new Item("barium"){{
            localizedName = "Barium";
            color = Color.valueOf("666558");
        }};
        platinum = new Item("platinum"){{
            localizedName = "Platinum";
            color = Color.valueOf("d0d6db");
        }};
        lithium = new Item("lithium"){{
            localizedName = "Lithium";
            color = Color.valueOf("47463d");
        }};
        carbonFiber = new Item("carbon-fiber"){{
            localizedName = "Carbon Fiber";
            color = Color.valueOf("3d3e47");
        }};
        erbium = new Item("erbium"){{
            localizedName = "Erbium";
            color = Color.valueOf("aaadaf");
        }};
        zirconium = new Item("zirconium"){{
            localizedName = "Zirconium";
            color = Color.valueOf("6f6d83");
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

        vercilusItems.addAll(zirconium,lithium,barium,silicon,nobelium,platinum,erbium,carbonFiber);
        vercilusOnlyItems.addAll(zirconium,lithium,barium,nobelium,platinum,erbium,carbonFiber);
    }
}
