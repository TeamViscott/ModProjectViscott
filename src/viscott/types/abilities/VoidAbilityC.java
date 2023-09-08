package viscott.types.abilities;

import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import viscott.world.chips.VoidAreaC;

public class VoidAbilityC extends Ability implements VoidAreaC {

    public float range = 8;
    public VoidAbilityC(float range) {
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
