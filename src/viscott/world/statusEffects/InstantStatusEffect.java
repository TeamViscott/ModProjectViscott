package viscott.world.statusEffects;

import arc.util.Time;
import mindustry.entities.units.StatusEntry;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class InstantStatusEffect extends PvStatusEffect {
    public InstantStatusEffect(String name)
    {
        super(name);
    }
    @Override
    public void update(Unit unit, StatusEntry status){
        super.update(unit,status);
        unit.unapply(this);
    }

}
