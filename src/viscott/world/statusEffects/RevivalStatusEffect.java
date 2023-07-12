package viscott.world.statusEffects;

import arc.Events;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Unit;
import viscott.content.PvStatusEffects;

public class RevivalStatusEffect extends PvStatusEffect {


    public RevivalStatusEffect(String name) {
        super(name);
        Seq<Unit> PreservableUnits = new Seq<>();


    }

    @Override
    public void update(Unit unit, float time) {
        if (unit.health < unit.maxHealth() / 10f) {
            unit.add();
            unit.health = unit.maxHealth() / 1.26f;
            unit.dead = false;
            unit.clearStatuses();
            unit.apply(PvStatusEffects.revived);
        }
    }
}
