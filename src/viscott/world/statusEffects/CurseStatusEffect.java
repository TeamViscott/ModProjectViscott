package viscott.world.statusEffects;

import arc.Events;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Unit;

public class CurseStatusEffect extends PvStatusEffect{

    public Seq<Unit> cursedUnits = new Seq<>();
    public Seq<Unit> deadCursed = new Seq<>();

    public CurseStatusEffect(String name) {
        super(name);
        Events.run(EventType.Trigger.update,()->{
            if (!Vars.state.isPaused()) {
                Seq<Unit> removeUnits = new Seq<>();
                cursedUnits.each(unit -> {
                     if (!unit.isValid() || !unit.hasEffect(this))
                         removeUnits.add(unit);
                });
                removeUnits.each(u -> {
                    cursedUnits.remove(u);
                    if (!u.isValid() && !deadCursed.contains(u)) {
                        deadCursed.add(u);
                        u.remove();
                    }
                });
            }
        });
    }
    @Override
    public void update(Unit unit, float time){
        if (!cursedUnits.contains(unit))
            cursedUnits.add(unit);
        super.update(unit,time);
    }
}
