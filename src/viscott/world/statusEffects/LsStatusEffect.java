package viscott.world.statusEffects;

import mindustry.gen.Unit;
import mindustry.content.StatusEffects;
import viscott.content.PvStatusEffects;

public class LsStatusEffect extends PvStatusEffect {


    public LsStatusEffect(String name) {
        super(name);
    }

    @Override
    public void update(Unit unit, float time) {
        if (unit.health < unit.maxHealth() / 3.333333333333f) {
            unit.apply(PvStatusEffects.lse);
            unit.add();
            unit.dead = false;
            unit.unapply(this);
            unit.apply(StatusEffects.invincible ,180);
        }
    }
}
