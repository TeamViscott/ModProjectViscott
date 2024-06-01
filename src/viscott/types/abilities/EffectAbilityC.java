package viscott.types.abilities;

import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import viscott.world.chips.EffectAreaC;

public class EffectAbilityC extends Ability implements EffectAreaC {

    public float range = 8;
    public EffectAbilityC(float range) {
        this.range = range;
    }
    @Override
    public void update(Unit unit){
        updateVoid(unit,range);
    }

    @Override
    public void draw(Unit unit){
        drawVoid(unit,range);
    }
}
