package viscott.world.statusEffects;

import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import viscott.content.PvStats;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class StatusEffectStack extends StatusEffect {
    public int charges = 3;
    HashMap<Unit, Integer> unitCharges = new HashMap<>();
    HashMap<Unit, Float> unitTime = new HashMap<>();
    List<Float> statsStatic = new Stack<>();

    public StatusEffectStack(String name)
    {
        super(name);
        staticStat();
    }

    public void staticStat()
    {
        statsStatic.clear();
        statsStatic.add(damageMultiplier);
        statsStatic.add(healthMultiplier);
        statsStatic.add(speedMultiplier);
        statsStatic.add(reloadMultiplier);
        statsStatic.add(buildSpeedMultiplier);
    }

    @Override
    public void setStats(){
        if(statsStatic.get(0) != 1) {
            stats.addPercent(Stat.damageMultiplier, statsStatic.get(0));
            stats.addPercent(PvStats.maxDamageMultiplier, 1+(statsStatic.get(0)-1)*charges);
        }
        if(statsStatic.get(1) != 1) {
            stats.addPercent(Stat.healthMultiplier, statsStatic.get(1));
            stats.addPercent(PvStats.maxHealthMultiplier, 1+(statsStatic.get(1)-1)*charges);
        }
        if(statsStatic.get(2) != 1) {
            stats.addPercent(Stat.speedMultiplier, statsStatic.get(2));
            stats.addPercent(PvStats.maxSpeedMultiplier, 1+(statsStatic.get(2)-1)*charges);
        }
        if(statsStatic.get(3) != 1) {
            stats.addPercent(Stat.reloadMultiplier, statsStatic.get(3));
            stats.addPercent(PvStats.maxReloadSpeedMultiplier, 1+(statsStatic.get(3)-1)*charges);
        }
        if(statsStatic.get(4) != 1) {
            stats.addPercent(Stat.buildSpeedMultiplier, statsStatic.get(4));
            stats.addPercent(PvStats.maxBuildSpeedMultiplier, 1+(statsStatic.get(4)-1)*charges);
        }

        if(damage > 0) {
            stats.add(Stat.damage, damage * 60f, StatUnit.perSecond);
            stats.add(PvStats.maxDamage, damage * 60f * charges, StatUnit.perSecond);
        }
        else if(damage < 0) {
            stats.add(Stat.healing, -damage * 60f, StatUnit.perSecond);
            stats.add(PvStats.maxHealing, -damage * 60f * charges, StatUnit.perSecond);
        }
        stats.add(PvStats.maxCharges, charges,StatUnit.none);

        boolean reacts = false;

        for(var e : opposites.toSeq().sort()){
            stats.add(Stat.opposites, e.emoji() + "" + e);
        }

        if(reactive){
            var other = Vars.content.statusEffects().find(f -> f.affinities.contains(this));
            if(other != null && other.transitionDamage > 0){
                stats.add(Stat.reactive, other.emoji() + other + " / [accent]" + (int)other.transitionDamage + "[lightgray] " + Stat.damage.localized());
                reacts = true;
            }
        }

        //don't list affinities *and* reactions, as that would be redundant
        if(!reacts){
            for(var e : affinities.toSeq().sort()){
                stats.add(Stat.affinities, e.emoji() + "" + e);
            }

            if(affinities.size > 0 && transitionDamage != 0){
                stats.add(Stat.affinities, "/ [accent]" + (int)transitionDamage + " " + Stat.damage.localized());
            }
        }

    }
    @Override
    public void init()
    {
        super.init();
    }

    @Override
    public void update(Unit unit, float time){
        if (!unitTime.containsKey(unit) && time > 1)
        {
            unitTime.put(unit,time);
            unitCharges.put(unit,1);
        }

        if (unitTime.get(unit) < time)
            unitCharges.replace(unit,Mathf.clamp(unitCharges.get(unit)+1,0,charges));

        damageMultiplier = 1+(statsStatic.get(0)-1)*unitCharges.get(unit);
        healthMultiplier = 1+(statsStatic.get(1)-1)*unitCharges.get(unit);
        speedMultiplier = 1+(statsStatic.get(2)-1)*unitCharges.get(unit);
        reloadMultiplier = 1+(statsStatic.get(3)-1)*unitCharges.get(unit);
        buildSpeedMultiplier = 1+(statsStatic.get(4)-1)*unitCharges.get(unit);

        if(damage > 0){
            unit.damageContinuousPierce(damage*unitCharges.get(unit));
        }else if(damage < 0){ //heal unit
            unit.heal(-1f * damage * unitCharges.get(unit) * Time.delta);
        }

        if(effect != Fx.none && Mathf.chanceDelta(effectChance)){
            Tmp.v1.rnd(Mathf.range(unit.type.hitSize/2f));
            effect.at(unit.x + Tmp.v1.x, unit.y + Tmp.v1.y, 0, color, parentizeEffect ? unit : null);
        }

        if (time <= 1 && unitTime.containsKey(unit))
        {
            unitTime.remove(unit);
            unitCharges.remove(unit);
        }
    }

    @Override
    public String toString()
    {
        return localizedName;
    }
}
