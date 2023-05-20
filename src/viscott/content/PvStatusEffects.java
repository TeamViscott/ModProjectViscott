package viscott.content;

import arc.graphics.Color;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import viscott.utilitys.PvUtil;
import viscott.world.statusEffects.AlterStatusEffect;
import viscott.world.statusEffects.FragStatusEffect;
import viscott.world.statusEffects.PvStatusEffect;
import viscott.world.statusEffects.StatusEffectStack;

public class PvStatusEffects {
    public static StatusEffect
    timeWarped,doused, disabled, expent, resiliant, ungratefull, crescendo ,tick ,tock,mend,shield, malfunction,
    voidShield,voidDecay,frag
            ;
    public static void load() {
        timeWarped = new StatusEffectStack("time-warped") {{
            localizedName = "Time Warped";
            speedMultiplier = 0.98f;
            dragMultiplier = 0.98f;
            reloadMultiplier = 0.98f;
            buildSpeedMultiplier = 0.98f;
            charges = 20;
            color = Pal.sap;
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
            color = Color.valueOf("af3a30");
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
            color = Color.valueOf("dbc5c5");
            afterStatusEffectDuration = 60;
        }};
        tock = new AlterStatusEffect("tock"){{
            localizedName = "Tock";
            description = "Tick and Tock it goes back and forth";
            speedMultiplier = 2f;
            reloadMultiplier = 2f;
            dragMultiplier = 2f;
            buildSpeedMultiplier = 2f;
            color = Color.valueOf("dbc5c5");
            ((AlterStatusEffect)tick).afterStatusEffect = this;
            afterStatusEffect = tick;
            afterStatusEffectDuration = 60;

        }};
        mend = new StatusEffect("mend")
        {{
            localizedName = "Mend";
            color = Pal.heal;
            description = "Mends a unit over time";
            damage = -3f/60f;
        }};

        shield = new PvStatusEffect("shield")
        {{
            localizedName = "Shield";
            color = Color.valueOf("77a9f3");
            description = "Applies a small amount of shield to the unit";
            shield = 5f/60f;
            maxShield = 60;
        }};
        voidShield = new PvStatusEffect("void-shield")
        {{
             localizedName = "Void Shield";
             color = Color.valueOf("000000");
             description = "a shield made of Void that reduces taken damage by 5.";
             shield = 5;
             maxShield = 5;
             damage = -0.1f/60f;
        }};
        voidDecay = new PvStatusEffect("void-decay")
        {{
            localizedName = "Void Decay";
            color = Color.valueOf("000000");
            description = "the result of a non void unit entering the void.";
            damage = 20f/60f;
        }};
        malfunction = new PvStatusEffect("malfunction")
        {{
            localizedName = "Malfunction";
            description = "Any healing / damage recieved will be applied at the end of this status effect\nlose 1 hp per second that its active.";
            damage = 1f/60f;
            numbness = true;
        }};
        frag = new FragStatusEffect("frag"){{
            localizedName = "Frag";
            description = "applies Frag to the unit's bullets";
            fragBullets = 6;
            fragRandomSpread = 135;
            fragBullet = new BasicBulletType(4,5)
            {{
                lifetime = PvUtil.GetRange(4,8);
                trailColor = lightColor = backColor = Pal.heal.cpy().a(0.5f);
                trailWidth = 1;
                trailLength = 10;
            }};
        }};
    }
}
