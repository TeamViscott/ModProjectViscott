package viscott.world.statusEffects;

import arc.Core;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.gen.Unit;
import mindustry.graphics.MultiPacker;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import mindustry.world.meta.Stat;
import viscott.content.PvStats;
import viscott.utilitys.PvUtil;

import java.util.HashMap;

public class PvStatusEffect extends StatusEffect {
    public float shield = 0;
    public float maxShield = 100;

    public boolean numbness = false;

    public class healthDmg
    {
        float health = 0;
        float dmg = 0;
        public healthDmg(float hp)
        {
            health = hp;
        }
    }
    HashMap<Unit, healthDmg> damageNumbness = new HashMap<>();

    Seq<Unit> effectOn = new Seq<>();
    public PvStatusEffect(String name)
    {
        super(name);
        outline = true;
    }

    @Override
    public void setStats(){
        super.setStats();
        if (shield != 0) {
            stats.add(PvStats.shield, shield * 60 + "/sec");
            stats.add(PvStats.maxShield, maxShield + "");
        }
    }

    @Override
    public void update(Unit unit, float time){
        if (!effectOn.contains(unit))
        {
            start(unit);
            effectOn.add(unit);
        }
        if (numbness) {
            if(damageNumbness.containsKey(unit)) {
                float staticHealth = damageNumbness.get(unit).health;
                float healthDiff = staticHealth - unit.health;
                unit.health = staticHealth;
                damageNumbness.get(unit).dmg+=healthDiff;
            }
        }
        float shieldDiff = maxShield - unit.shield;
        if (shieldDiff > 0)
            unit.shield += Math.min(shield,shieldDiff);
        super.update(unit,time);
        if (time <= Time.delta * 2f)
        {{
            end(unit);
            effectOn.remove(unit);
            unit.unapply(this);
        }}
    }

    public void start(Unit unit)
    {
        if (numbness)
            damageNumbness.put(unit,new healthDmg(unit.health));
    }

    public void end(Unit unit)
    {
        if (numbness)
            if(damageNumbness.containsKey(unit))
            {
                unit.health = damageNumbness.get(unit).health;
                unit.damage(damageNumbness.get(unit).dmg);
                damageNumbness.remove(unit);
            }
    }
}
