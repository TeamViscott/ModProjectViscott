package viscott.content;

import mindustry.type.Item;

public class PvItems {
    public static Item
    zirconium,lithium,barium,nobelium,platinum,erbium,carbonFiber
    ;
    public static void load()
    {
        nobelium = new Item("nobelium"){{
            localizedName = "Nobelium";
        }};
        barium = new Item("barium"){{
            localizedName = "Barium";
        }};
        platinum = new Item("platinum"){{
            localizedName = "Platinum";
        }};
        lithium = new Item("lithium"){{
            localizedName = "Lithium";
        }};
        carbonFiber = new Item("carbon-fiber"){{
            localizedName = "Carbon Fiber";
        }};
        erbium = new Item("erbium"){{
            localizedName = "Erbium";
        }};
        zirconium = new Item("zirconium"){{
            localizedName = "Zirconium";
        }};
    }
}
