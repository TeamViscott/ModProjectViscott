package viscott.content.shootpatterns;

import arc.util.Log;
import mindustry.entities.pattern.ShootSpread;

/** copied from mindustry shoot pattern but modified a bit */

public class CyclicShootPattern extends ShootSpread {
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
        super(shotsPerCycle,spread);
        this.maxCycleIteration = cycles;
        this.spread = spread;
    }
    public int maxCycleIteration = 3;
    /**
     * sets how many shots should be called per cycle done
     */


    @Override
    public void shoot(int totalShots, BulletHandler handler) {
        int Cycle = 0;
        int maxSum = (int)((maxCycleIteration + 1) / 2f * (maxCycleIteration*shots));
        int cutShots = totalShots % maxSum;
        while(Cycle < maxCycleIteration)
        {
            int sum = (int)(Cycle / 2f * (++Cycle*shots));
            if (cutShots == sum)
                break;
        }
        float dynamicspread = spread / (shots * Cycle);
        int allShots = Cycle * shots;
        for (int i = 0; i < allShots; i++) {
            float angleOffset = i * dynamicspread - (allShots - 1) * dynamicspread / 2f;
            handler.shoot(0, 0, angleOffset, firstShotDelay + shotDelay * i);
        }
    }
}