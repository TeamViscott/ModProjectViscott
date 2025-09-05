package viscott.types.abilities;

import arc.util.Time;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;

public class DamageAbility extends Ability {
    public float damage;
    public DamageAbility(float damage)
    {
        this.damage = damage;
    }

    @Override
    public void update(Unit unit){
        unit.health-=damage * Time.delta;
    }
}
