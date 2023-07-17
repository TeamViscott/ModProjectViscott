package viscott.world.statusEffects;

import mindustry.gen.Unit;
import mindustry.world.Tile;
import viscott.content.PvStats;

import java.util.HashMap;

public class ConsumeStatusEffect extends PvStatusEffect{
    public float hpThreshhold = 1000;
    public int radius = 1;
    public int consumeLimit = 10;
    HashMap<Unit,Integer[]> consumes = new HashMap<>();
    public ConsumeStatusEffect(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(PvStats.hpLimit,hpThreshhold);
        stats.add(PvStats.maxConsumes,consumeLimit);
    }

    @Override
    public void start(Unit unit) {
        consumes.put(unit,new Integer[]{consumeLimit});
    }

    @Override
    public void update(Unit unit, float time){
        super.update(unit,time);
        Tile t = unit.tileOn();
        if (t == null) return;
        for(int xo = -radius;xo <= radius;xo++)
            for(int yo = -radius;yo <= radius;yo++) {
                Tile n = t.nearby(xo,yo);
                if (n.build != null && n.build.health <= hpThreshhold) {
                    float hpGap = unit.maxHealth - unit.health;
                    if (hpGap == 0 && n.build.team() == unit.team()) continue;
                    unit.health+=Math.min(n.build.health,hpGap);
                    n.build.kill();
                    consumes.get(unit)[0]--;
                    if (consumes.get(unit)[0] <= 0) {
                        unit.unapply(this);
                        return;
                    }
                }
            }
    }
}
