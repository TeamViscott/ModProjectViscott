package viscott.content;

import mindustry.game.Team;
import mindustry.type.StatusEffect;
import viscott.world.statusEffects.StatusEffectStack;

public class PvStatusEffects {
    public static StatusEffect
    timeWarped,doused, disabled
            ;
    public static void load()
    {
        timeWarped =new StatusEffectStack("time-warped"){{
            localizedName = "Time Warped";
            speedMultiplier = 0.98f;
            dragMultiplier = 0.98f;
            reloadMultiplier = 0.98f;
            buildSpeedMultiplier = 0.98f;
            charges = 20;
            staticStat();
        }};
        doused = new StatusEffectStack("doused"){{
            localizedName = "Doused";
            damage = 2f/60f;
            charges = 30;
            staticStat();
        }};
        disabled = new StatusEffectStack("disabled"){{
            localizedName = "Disabled";
            newTeam = Team.derelict;
            speedMultiplier = 0;
            reloadMultiplier = 0;
            staticStat();
        }};
    }
}
