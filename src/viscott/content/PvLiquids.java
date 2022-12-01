package viscott.content;

import arc.graphics.Color;
import mindustry.type.Liquid;

public class PvLiquids {
    public static Liquid
    kerosene,xenon,liquidNitrogen
    ;
    public static void load()
    {
        liquidNitrogen = new Liquid("liquid-nitrogen")
        {{
            localizedName = "Nitrogen";
            color = Color.valueOf("ECDCF5");
        }};
        xenon = new Liquid("liquid-xenon"){{
            localizedName = "Xenon";
            color = Color.valueOf("C080E2");
        }};
        kerosene = new Liquid("liquid-kerosene")
        {{
            localizedName = "Kerosene";
            color = Color.valueOf("E2DF80");
        }};
    }
}
