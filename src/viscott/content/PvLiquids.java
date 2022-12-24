package viscott.content;

import arc.graphics.Color;
import mindustry.content.StatusEffects;
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
            heatCapacity = 2;
        }};
        xenon = new Liquid("liquid-xenon"){{
            localizedName = "Xenon";
            color = Color.valueOf("C080E2");
            heatCapacity = 0.8f;
        }};
        kerosene = new Liquid("liquid-kerosene")
        {{
            localizedName = "Kerosene";
            color = Color.valueOf("E2DF80");
            effect = StatusEffects.burning;
            heatCapacity = 1.2f;
        }};
    }
}
