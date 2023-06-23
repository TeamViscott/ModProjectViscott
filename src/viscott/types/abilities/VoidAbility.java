package viscott.types.abilities;

import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import viscott.world.chips.VoidArea;

public class VoidAbility extends Ability implements VoidArea {

    public float range = 8;
    public VoidAbility(float range) {
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
