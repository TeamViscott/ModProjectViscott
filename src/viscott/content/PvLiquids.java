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
            localizedName = "Liquid Nitrogen";
            color = Color.valueOf("ECDCF5");
            heatCapacity = 2;
        }};
        xenon = new Liquid("liquid-xenon"){{
            localizedName = "Xenon";
            color = Color.valueOf("C080E2");
            gas = true;
            coolant = false;
        }};
        kerosene = new Liquid("liquid-kerosene")
        {{
            localizedName = "Kerosene";
            color = Color.valueOf("FED894");
            effect = PvStatusEffects.doused;
            flammability = 1f;
            heatCapacity = 1.25f;
        }};
    }
}
