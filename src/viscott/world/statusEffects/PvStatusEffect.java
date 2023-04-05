package viscott.world.statusEffects;

import arc.Core;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.gen.Unit;
import mindustry.graphics.MultiPacker;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import mindustry.world.meta.Stat;
import viscott.content.PvStats;
import viscott.utilitys.PvUtil;

public class PvStatusEffect extends StatusEffect {
    public float shield = 0;
    public float maxShield = 100;
    public PvStatusEffect(String name)
    {
        super(name);
        outline = true;
    }

    @Override
    public void setStats(){
        if (shield != 0) {
            stats.add(PvStats.shield, shield * 60 + "/sec");
            stats.add(PvStats.maxShield, maxShield + "");
        }
    }

    @Override
    public void update(Unit unit, float time){
        float shieldDiff = maxShield - shield;
        if (shieldDiff > 0)
            unit.shield += Math.min(shield,shieldDiff);
        super.update(unit,time);
    }
}
