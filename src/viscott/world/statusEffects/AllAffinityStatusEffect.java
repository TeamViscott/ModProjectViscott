package viscott.world.statusEffects;

import mindustry.Vars;
import mindustry.entities.units.StatusEntry;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import java.util.Iterator;

public class AllAffinityStatusEffect extends PvStatusEffect{
    public TransitionHandler affinityHandler;
    public AllAffinityStatusEffect(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        this.stats.add(Stat.affinities, "[gold]All Status Effects[]", new Object[0]);
        this.stats.add(Stat.affinities, "/ [accent]" + (int)this.transitionDamage + " " + Stat.damage.localized(), new Object[0]);

    }

    @Override
    public boolean applyTransition(Unit unit, StatusEffect to, StatusEntry entry, float time) {
        if (to == this) return false;
        affinityHandler.handle(unit,entry,time);
        return true;
    }
}
