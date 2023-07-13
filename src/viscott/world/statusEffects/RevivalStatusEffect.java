package viscott.world.statusEffects;

import mindustry.content.StatusEffects;
import mindustry.gen.Unit;
import viscott.content.PvStatusEffects;

public class RevivalStatusEffect extends PvStatusEffect {


    public RevivalStatusEffect(String name) {
        super(name);
    }

    @Override
    public void update(Unit unit, float time) {
        if (unit.health < unit.maxHealth() / 6.66666666666f) {
            unit.apply(PvStatusEffects.pe);
            unit.add();
            unit.health = unit.maxHealth() / 2f;
            unit.dead = false;
            unit.unapply(this);
            unit.apply(StatusEffects.invincible,15);
        }
    }
}
