package viscott.content;

import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import viscott.world.statusEffects.AlterStatusEffect;
import viscott.world.statusEffects.InstantStatusEffect;
import viscott.world.statusEffects.PvStatusEffect;
import viscott.world.statusEffects.StatusEffectStack;

public class PvStatusEffects {
    public static StatusEffect
    timeWarped,doused, disabled, expent, resiliant, ungratefull, crescendo ,tick ,tock,mend,shield
            ;
    public static void load() {
        timeWarped = new StatusEffectStack("time-warped") {{
            localizedName = "Time Warped";
            speedMultiplier = 0.98f;
            dragMultiplier = 0.98f;
            reloadMultiplier = 0.98f;
            buildSpeedMultiplier = 0.98f;
            charges = 20;
            staticStat();
        }};
        doused = new StatusEffectStack("doused") {{
            localizedName = "Doused";
            damage = 2f / 60f;
            charges = 30;
            staticStat();
        }};
        disabled = new StatusEffectStack("disabled") {{
            localizedName = "Disabled";
            newTeam = Team.derelict;
            speedMultiplier = 0;
            reloadMultiplier = 0;
            staticStat();
        }};
        expent = new StatusEffectStack("expent") {
            {
                localizedName = "Expent";
                reloadMultiplier = 0.95f;
                dragMultiplier = 1.2f;
                speedMultiplier = 0.98f;
                charges = 12;
                show = false;
                staticStat();
            }
        };
        crescendo = new StatusEffectStack("crescendo") {{
            localizedName = "Crescendo";
            reloadMultiplier = 1.05f;
            dragMultiplier = 1.01f;
            speedMultiplier = 0.995f;
            charges = 80;
            show = false;
            staticStat();
        }};
        resiliant = new StatusEffectStack("resiliant") {{
            localizedName = "Resiliant";
            description = "The Lava on you is cooling off jamming your Weapons but allowing bigger resistance to Damage.";
            reloadMultiplier = 0.99f;
            dragMultiplier = 0.99f;
            healthMultiplier = 1.01f;
            speedMultiplier = 0.99f;
            charges = 10;
            staticStat();
        }};
        ungratefull = new StatusEffectStack("ungratefull") {{
            localizedName = "Ungratefull";
            description = "The enemy of everyone";
            newTeam = Team.get(250);
            staticStat();
        }};
        tick = new AlterStatusEffect("tick") {{
            localizedName = "Tick";
            description = "Tick and Tock it goes back and forth";
            speedMultiplier = 0.5f;
            reloadMultiplier = 0.5f;
            dragMultiplier = 0.5f;
            buildSpeedMultiplier = 0.5f;
            afterStatusEffectDuration = 60;
        }};
        tock = new AlterStatusEffect("tock"){{
            localizedName = "Tock";
            description = "Tick and Tock it goes back and forth";
            speedMultiplier = 2f;
            reloadMultiplier = 2f;
            dragMultiplier = 2f;
            buildSpeedMultiplier = 2f;
            ((AlterStatusEffect)tick).afterStatusEffect = this;
            afterStatusEffect = tick;
            afterStatusEffectDuration = 60;

        }};
        mend = new StatusEffect("mend")
        {{
            localizedName = "Mend";
            description = "Mends a unit over time";
            damage = -5f/60f;
        }};

        shield = new PvStatusEffect("shield")
        {{
            localizedName = "Shield";
            color = Pal.heal;
            description = "Applies a small amount of shield to the unit";
            shield = 5f/60f;
            maxShield = 60;
        }};
    }
}
