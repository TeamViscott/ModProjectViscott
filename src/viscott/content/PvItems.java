package viscott.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.type.Item;

public class PvItems {
    public static Item
    zirconium,lithium,barium,nobelium,platinum,erbium,carbonFiber
    ;
    public static Seq<Item> vercilusItems = new Seq<Item>();
    public static void load()
    {
        nobelium = new Item("nobelium"){{
            localizedName = "Nobelium";
            color = Color.valueOf("ef525b");
        }};
        barium = new Item("barium"){{
            localizedName = "Barium";
            color = Color.valueOf("3d3e47");
        }};
        platinum = new Item("platinum"){{
            localizedName = "Platinum";
            color = Color.valueOf("aaadaf");
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
            color = Color.valueOf("6f6e80");
        }};

        vercilusItems.addAll(zirconium,lithium,barium,nobelium,platinum,erbium,carbonFiber);
    }
}
