package viscott.types.abilities;

import arc.math.Angles;
import arc.util.Time;
import mindustry.entities.Units;
import mindustry.entities.abilities.StatusFieldAbility;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;

public class EnemyStatusFieldAbility extends StatusFieldAbility {
    public EnemyStatusFieldAbility(StatusEffect effect,float duration,float reload,float range)
    {
        super(effect,duration,reload,range);
    }

    @Override
    public void update(Unit unit){
        timer += Time.delta;

        if(timer >= reload && (!onShoot || unit.isShooting)){
            for (Team t : Team.all)
                if (t != unit.team && t.active())
                    Units.nearby(t, unit.x, unit.y, range, other -> {
                        other.apply(effect, duration);
                        applyEffect.at(other, parentizeEffects);
                    });

            float x = unit.x + Angles.trnsx(unit.rotation, effectY, effectX), y = unit.y + Angles.trnsy(unit.rotation, effectY, effectX);
            activeEffect.at(x, y, effectSizeParam ? range : unit.rotation, parentizeEffects ? unit : null);

            timer = 0f;
        }
    }
}
