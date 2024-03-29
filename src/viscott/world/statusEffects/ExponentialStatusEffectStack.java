package viscott.world.statusEffects;

import arc.Events;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import viscott.content.PvStats;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class ExponentialStatusEffectStack extends StatusEffectStack {

    public ExponentialStatusEffectStack(String name)
    {
        super(name);
    }

    @Override
    public void setStats() {
        if (setStatsInfinity) {
            if (statsStatic.get(0) != 1) {
                stats.addPercent(Stat.damageMultiplier, statsStatic.get(0));
                if (charges != 1)stats.add(PvStats.maxDamageMultiplier, "Exponential Infinity%");
            }
            if(statsStatic.get(1) != 1) {
                stats.addPercent(Stat.healthMultiplier, statsStatic.get(1));
                if (charges != 1)stats.add(PvStats.maxHealthMultiplier, "Exponential Infinity%");
            }
            if(statsStatic.get(2) != 1) {
                stats.addPercent(Stat.speedMultiplier, statsStatic.get(2));
                if (charges != 1)stats.add(PvStats.maxSpeedMultiplier, "Exponential Infinity%");
            }
            if(statsStatic.get(3) != 1) {
                stats.addPercent(Stat.reloadMultiplier, statsStatic.get(3));
                if (charges != 1)stats.add(PvStats.maxReloadSpeedMultiplier, "Exponential Infinity%");
            }
            if(statsStatic.get(4) != 1) {
                stats.addPercent(Stat.buildSpeedMultiplier, statsStatic.get(4));
                if (charges != 1)stats.add(PvStats.maxBuildSpeedMultiplier, "Exponential Infinity%");
            }
            if(statsStatic.get(4) != 1) {
                stats.addPercent(PvStats.dragMultiplier, statsStatic.get(5));
                if (charges != 1)stats.add(PvStats.maxDragMultiplier, "Exponential Infinity%");
            }

            if(damage > 0) {
                stats.add(Stat.damage, damage * 60f, StatUnit.perSecond);
                if (charges != 1)stats.add(PvStats.maxDamage, "Infinity", StatUnit.perSecond);
            }
            else if(damage < 0) {
                stats.add(Stat.healing, -damage * 60f, StatUnit.perSecond);
                if (charges != 1)stats.add(PvStats.maxHealing, "Infinity", StatUnit.perSecond);
            }
            if (charges != 1)stats.add(PvStats.maxCharges,"Infinity");
        }else{
               if (statsStatic.get(0) != 1) {
                   stats.addPercent(Stat.damageMultiplier, statsStatic.get(0));
                   if (charges != 1)stats.addPercent(PvStats.maxDamageMultiplier, Mathf.round(1 + (statsStatic.get(0) - 1) * Mathf.pow(2f, charges), 0.01f));
               }
               if(statsStatic.get(1) != 1) {
                   stats.addPercent(Stat.healthMultiplier, statsStatic.get(1));
                   if (charges != 1)stats.addPercent(PvStats.maxHealthMultiplier, Mathf.round(1+(statsStatic.get(1)-1)*Mathf.pow(2f, charges),0.01f));
               }
               if(statsStatic.get(2) != 1) {
                   stats.addPercent(Stat.speedMultiplier, statsStatic.get(2));
                   if (charges != 1)stats.addPercent(PvStats.maxSpeedMultiplier, Mathf.round(1+(statsStatic.get(2)-1)*Mathf.pow(2f, charges),0.01f));
               }
               if(statsStatic.get(3) != 1) {
                   stats.addPercent(Stat.reloadMultiplier, statsStatic.get(3));
                   if (charges != 1)stats.addPercent(PvStats.maxReloadSpeedMultiplier, Mathf.round(1+(statsStatic.get(3)-1)*Mathf.pow(2f, charges),0.01f));
               }
               if(statsStatic.get(4) != 1) {
                   stats.addPercent(Stat.buildSpeedMultiplier, statsStatic.get(4));
                   if (charges != 1)stats.addPercent(PvStats.maxBuildSpeedMultiplier, Mathf.round(1+(statsStatic.get(4)-1)*Mathf.pow(2f, charges),0.01f));
               }
               if(statsStatic.get(4) != 1) {
                   stats.addPercent(PvStats.dragMultiplier, statsStatic.get(5));
                   if (charges != 1)stats.addPercent(PvStats.maxDragMultiplier, Mathf.round(1+(statsStatic.get(5)-1)*Mathf.pow(2f, charges),0.01f));
               }

               if(damage > 0) {
                   stats.add(Stat.damage, damage * 60f, StatUnit.perSecond);
                   if (charges != 1)stats.add(PvStats.maxDamage, damage * 60f * Mathf.pow(2f, charges), StatUnit.perSecond);
               }
               else if(damage < 0) {
                   stats.add(Stat.healing, -damage * 60f, StatUnit.perSecond);
                   if (charges != 1)stats.add(PvStats.maxHealing, -damage * 60f * Mathf.pow(2f, charges), StatUnit.perSecond);
               }
           }
        }
    @Override
    public void init()
    {
        statsStatic.clear();
        statsStatic.add(damageMultiplier);
        statsStatic.add(healthMultiplier);
        statsStatic.add(speedMultiplier);
        statsStatic.add(reloadMultiplier);
        statsStatic.add(buildSpeedMultiplier);
        statsStatic.add(dragMultiplier);
        damageMultiplier = 1;
        healthMultiplier = 1;
        speedMultiplier = 1;
        reloadMultiplier = 1;
        buildSpeedMultiplier = 1;
        dragMultiplier = 1;

        super.init();
        localName = localizedName;
        Events.run(EventType.Trigger.update,()-> {
            if (unitCharges.containsKey(Vars.player.unit()) && Vars.player.unit().hasEffect(this))
                if (setStatsInfinity) {
                    localizedName = localName + " [accent]x" + unitCharges.get(Vars.player.unit()) + " | Infinity";
                }else{
                    localizedName = localName + " [accent]x" + unitCharges.get(Vars.player.unit()) + " | " + charges;
                }
            else
                localizedName = localName;
        });
    }

    public void start(Unit unit,float time)
    {
        if (!unitCharges.containsKey(unit))
        {{
                unitCharges.put(unit, 0);
                unitTime.put(unit,time);
                unitTeam.put(unit,unit.team);
        }}
        unitCharges.replace(unit,Mathf.clamp(unitCharges.get(unit)+1,0,charges));
    }

    public void end(Unit unit)
    {
        unitCharges.remove(unit);
        unitTime.remove(unit);
        if (newTeam != null)
            unit.team(unitTeam.get(unit));
        unitTeam.remove(unit);
    }

    @Override
    public void update(Unit unit, float time){
        if (!unitCharges.containsKey(unit))
            start(unit,time);
        if (unitTime.get(unit) < time)
            start(unit,time);
        unitTime.replace(unit,time);
        unit.damageMultiplier *= 1+(statsStatic.get(0)-1)*Mathf.pow(2f, unitCharges.get(unit)-1);
        unit.healthMultiplier *= 1+(statsStatic.get(1)-1)*Mathf.pow(2f, unitCharges.get(unit)-1);
        unit.speedMultiplier *= 1+(statsStatic.get(2)-1)*Mathf.pow(2f, unitCharges.get(unit)-1);
        unit.reloadMultiplier *= 1+(statsStatic.get(3)-1)*Mathf.pow(2f, unitCharges.get(unit)-1);
        unit.buildSpeedMultiplier *= 1+(statsStatic.get(4)-1)*Mathf.pow(2f, unitCharges.get(unit)-1);

        float shieldDiff = maxShield - shield;
        if (shieldDiff > 0)
            unit.shield += Math.min(shield,shieldDiff);

        if(damage > 0){
            unit.damageContinuousPierce(damage * Mathf.pow(2f, unitCharges.get(unit)-1));
        }else if(damage < 0){ //heal unit
            unit.heal(-1f * damage * Mathf.pow(2f, unitCharges.get(unit)-1) * Time.delta);
        }
    }

    @Override
    public String toString()
    {
        return localizedName;
    }
}
