package viscott.content;

import arc.graphics.Color;
import mindustry.type.Liquid;
import viscott.types.VoidLiquid;

public class PvLiquids {
    public static Liquid
    kerosene,xenon,liquidNitrogen,
    /*Nullis*/concentratedVoid
    ;
    public static void load()
    {
        liquidNitrogen = new Liquid("liquid-nitrogen")
        {{
            localizedName = "Liquid Nitrogen";
            color = Color.valueOf("ECDCF5");
            heatCapacity = 2;
        }};
        kerosene = new Liquid("liquid-kerosene")
        {{
            localizedName = "Kerosene";
            color = Color.valueOf("FED894");
            effect = PvStatusEffects.doused;
            flammability = 1f;
            heatCapacity = 1.25f;
        }};
        xenon = new Liquid("liquid-xenon"){{
            localizedName = "Xenon";
            color = Color.valueOf("C080E2");
            gas = true;
            coolant = false;
        }};
        concentratedVoid = new VoidLiquid("liquid-concentrated-void"){{
            localizedName = "Concentrated Void";
            description = "[#1a1a1a]So Concentrated that it manifested into a Liquid.";
            color = Color.valueOf("0a0a0a");
            colorFrom = Color.black;
            colorTo = Color.valueOf("111111");
            heatCapacity = 4;
            temperature = 0;
            effect = PvStatusEffects.voidDecay;
            voidFlyingEffect = PvStatusEffects.voidConsume;
            explosiveness = 0.1f;
            viscosity = 0.995f;
            coolant = true;
            capPuddles = false;
        }};
    }
}
