package viscott.content;

import mindustry.content.StatusEffects;
import mindustry.type.StatusEffect;
import viscott.world.statusEffects.StatusEffectStack;

public class PvStatusEffects {
    public static StatusEffect
    timeWarped,doused,buffer
            ;
    public static void load()
    {
        timeWarped =new StatusEffectStack("time-warped"){{
            localizedName = "Time Warped";
            speedMultiplier = 0.95f;
            dragMultiplier = 0.95f;
            reloadMultiplier = 0.95f;
            buildSpeedMultiplier = 0.95f;
            charges = 15;
            staticStat();
        }};
        doused = new StatusEffectStack("doused"){{
            localizedName = "Doused";
            damage = 2f/60f;
            charges = 10;
            staticStat();
        }};
        buffer = new StatusEffectStack("buffer"){{
            localizedName = "Buffer";
            damage = -1f/60f;
            healthMultiplier = 1.1f;
            charges = 90;
            staticStat();
        }};
    }
}
