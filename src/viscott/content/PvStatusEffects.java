package viscott.content;

import mindustry.content.StatusEffects;
import mindustry.type.StatusEffect;

public class PvStatusEffects {
    public static StatusEffect
    magnefied
            ;
    public static void load()
    {
        magnefied =new StatusEffect("magnefied"){{
            localizedName = "Magnefied";
            speedMultiplier = 0.6f;
            dragMultiplier = 0.6f;
            reloadMultiplier = 0.6f;
            buildSpeedMultiplier = 0.6f;
        }};
    }
}
