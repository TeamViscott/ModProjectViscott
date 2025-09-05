package viscott.types.abilities;

import arc.struct.Seq;
import arc.util.Time;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;

public class DamageAbility extends Ability {
    public float damage;

    public Seq<StatusEffect> blockStatusEffects = new Seq<>(); // Status effects that stop this ability from doing its thing.
    public DamageAbility(float damage)
    {
        this.damage = damage;
    }

    @Override
    public void update(Unit unit){
        if (blockStatusEffects.isEmpty() || blockStatusEffects.contains(s -> !unit.hasEffect(s)))
            unit.health-=damage * Time.delta;
    }
}
