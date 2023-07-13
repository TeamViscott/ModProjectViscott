package viscott.content;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import viscott.utilitys.PvUtil;
import viscott.world.statusEffects.*;

import static java.lang.Float.POSITIVE_INFINITY;

public class PvStatusEffects {
    public static StatusEffect
    timeWarped,doused, disabled, expent, resiliant, ungratefull, crescendo ,tick ,tock,mend,shield, malfunction,
    voidShield,voidDecay,frag,aoe,homing,memoryExchange,dataLeak,endless, prevention, lastStand, voidConsume,
    //visual statuses
    pe,lse
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
            reloadMultiplier = 0.999f;
            dragMultiplier = 0.999f;
            healthMultiplier = 1.01f;
            speedMultiplier = 0.999f;
            charges = 100;
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
            effect = Fx.breakProp;
            effectChance = 0.8f;
            damage = 20f/60f;
            buildSpeedMultiplier = 0.1f;
            speedMultiplier = 0.94f;
        }};
        voidConsume = new PvStatusEffect("void-consume") {{
            localizedName = "Void Consume";
            color = Color.black;
            description = "Hovering so close to the Void was not a good idea. it dragged you down to the ground";
            damage = 5f/60f;
            buildSpeedMultiplier = 0.90f;
            ground = true;
        }};
        malfunction = new PvStatusEffect("malfunction")
        {{
            localizedName = "Malfunction";
            description = "Any healing / damage received will be applied at the end of this status effect\nlose 1 hp per second that its active.";
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
        aoe = new FragStatusEffect("aoe"){{
            localizedName = "AOE";
            description = "applies Aoe damage to the unit Bullet's";
            fragBullets = 1;
            fragRandomSpread = 0;
            fragBullet = new BasicBulletType(0,0)
            {{
                lifetime = 0;
                splashDamage = 30;
                despawnEffect = hitEffect = Fx.explosion;
                splashDamageRadius = 8*2;
            }};
        }};
        homing = new HomingStatusEffect("homing"){{
            localizedName = "Homing";
            description = "Silicon + Unit Go Brr...";
            homingRange = 8*15;
            homingPower = 0.05f;
            homingDelay = 0;
        }};
        memoryExchange = new CurseStatusEffect("memory-exchange"){{
            localizedName = "[#b]Memory Exchange";
            description = "When cast upon a Unit, when the Unit is killed while this effect is active their Data gets picked up by [purple]Siede";
            details = "[red]Please do not destroy Unit during this Exchange";
            effect = new MultiEffect(Fx.despawn,Fx.freezing,Fx.lava);
            effectChance = 0.1f;
        }};
        prevention = new RevivalStatusEffect("prevention"){{
            localizedName = "[green]Prevention[]";
            description = "This [gold]Enchantment[] given by [green]Omamori[] prevents death in any ally\n[orange]Activation Threshold |[lightgray]10%[]| Hp.\n[orange]Activation Repair |[lightgray]50%[]| Hp\n[orange]Activation Invincibility Duration |[lightgray]0.25[]| seconds";
            details = "A special [gold]Enchantment[] created from [gold]Yggdrasil's[] [green]Life Force] ";
            permanent = true;
        }};
        lastStand = new LsStatusEffect("last-stand"){{
            localizedName = "[blue]Last Stand";
            description = "This [gold]Enchantment[] given by [green]Omamori[] Protects you at low HP\n[orange]Activation Threshold |[lightgray]25%[]| Hp.\n[orange]Activation Invincibility Duration |[lightgray]3[]| seconds";
            details = "A special [gold]Enchantment[] created from [gold]Yggdrasil's[] [green]Bark]";
            permanent = true;
        }};
        dataLeak = new StatusEffect("data-leak") {{
            localizedName = "[purple]Data Leak";
            description = "The Rebuilding of the Unit with [#b]Broken Data[] has led to the unit's Data to constantly Leak.";
            details = "[purple]Balancing Goes Brr....";
            permanent = true;
            damage = 25f/60f;
            damageMultiplier = 0.8f;
            effect = Fx.sapped;
            effectChance = 0.2f;
        }};
        endless = new StatusEffectStack("endless") {{
            localizedName = "[purple]Endless";
            description = "[purple]Endless[] [gold]Amplification[]";
            details = "[purple]Endless";
            reloadMultiplier = 1.01f;
            dragMultiplier = 1.01f;
            healthMultiplier = 1.01f;
            speedMultiplier = 1.01f;
            charges = (int) POSITIVE_INFINITY;
            staticStat();
        }};
        //visual statuses
        pe = new StatusEffect("prevention-trigger-vfx") {{
            applyEffect = PvEffects.quadRushCraft;/*lotusActivate*/
            show = false;
        }};
        lse = new StatusEffect("last-stand-trigger-vfx") {{
            applyEffect = PvEffects.quadRushCraft;/*lastStandActivate*/
            show = false;
        }};
    }
}
