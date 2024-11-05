package viscott.world.statusEffects;

import arc.util.Time;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import viscott.content.PvStats;

public class AlterStatusEffect extends PvStatusEffect {
    public AlterStatusEffect(String name)
    {
        super(name);
    }
    public StatusEffect afterStatusEffect = null;
    public float afterStatusEffectDuration = 60f;

    public void end(Unit unit)
    {
        if (afterStatusEffect != null)
            unit.apply(afterStatusEffect,afterStatusEffectDuration);
    }

    @Override
    public void setStats(){
        super.setStats();
        if (afterStatusEffect != null) {
            stats.add(PvStats.onStatusEnd, afterStatusEffect.localizedName);
        }
    }

    @Override
    public void update(Unit unit, float time){
        super.update(unit,time);
        if (time <= Time.delta * 2f)
        {{
            end(unit);
            unit.unapply(this);
        }}
    }
}
