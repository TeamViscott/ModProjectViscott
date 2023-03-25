package viscott.world.statusEffects;

import arc.util.Time;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;

public class AlterStatusEffect extends StatusEffect {
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
    public void update(Unit unit, float time){
        if (time <= Time.delta * 2f)
        {{
            end(unit);
        }}
    }
}
