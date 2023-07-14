package viscott.world.statusEffects;

import mindustry.content.StatusEffects;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import viscott.content.PvStatusEffects;

public class TriggerStatusEffect extends PvStatusEffect {
    public boolean activationRepair = false;
    public StatusEffect activationStatusFx = PvStatusEffects.basicFx;
    public float activationThreshold = 2, ActivationRepairAmount = 2,
    /*by percentage  1.25/75%  2/50%  3.333333333333/30%  4/25%  5/20%  6.66666666666/15%  10/10%*/
                 activationResistanceTime = 60;

    public TriggerStatusEffect(String name) {super(name);}

    @Override
    public void update(Unit unit, float time) {
        if (unit.health < unit.maxHealth() / activationThreshold) {
            unit.apply(activationStatusFx);
            if (activationRepair) {
                unit.health = unit.maxHealth() / ActivationRepairAmount;
            }
            unit.add();
            unit.dead = false;
            unit.unapply(this);
            unit.apply(StatusEffects.invincible ,activationResistanceTime);
        }
    }
}
