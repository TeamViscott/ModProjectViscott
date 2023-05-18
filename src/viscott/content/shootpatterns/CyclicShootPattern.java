package viscott.content.shootpatterns;

import arc.util.Log;
import arc.util.Nullable;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootPattern;
import mindustry.entities.pattern.ShootSpread;

/** copied from mindustry shoot pattern but modified a bit */

public class CyclicShootPattern extends ShootPattern {
    public float spread = 0;
    public CyclicShootPattern(int cycles)
    {
        this(cycles,0);
    }
    public CyclicShootPattern(int cycles,int spread)
    {
        this(1,cycles,spread);
    }
    public CyclicShootPattern(int shotsPerCycle,int cycles,int spread)
    {
        this.shots = shotsPerCycle;
        this.maxCycleIteration = cycles;
        this.spread = spread;
    }
    public int maxCycleIteration = 3;
    /**
     * sets how many shots should be called per cycle done
     */

    @Override
    public void shoot(int totalShots, BulletHandler handler,@Nullable Runnable totalShotsIncrement) {
        int Cycle = totalShots % maxCycleIteration + 1;
        totalShotsIncrement.run();
        float dynamicspread = spread / (shots * Cycle);
        int allShots = Cycle * shots;
        for (int i = 0; i < allShots; i++) {
            float angleOffset = i * dynamicspread - (allShots - 1) * dynamicspread / 2f;
            handler.shoot(0, 0, angleOffset, firstShotDelay + shotDelay * i);
        }
    }
}