package viscott.content.shootpatterns;

import mindustry.entities.pattern.ShootPattern;
import mindustry.entities.pattern.ShootSpread;

/** copied from mindustry shoot pattern but modified a bit */

public class CyclicPatternRainmaker extends ShootSpread {
    private int Cycle = 1;
    public int MaxCycleIteration = 3;
    /** sets how many shots should be called per cycle done */
    public int ShotsPerCycle = 1;
    /** Called on a single "trigger pull". This function should call the handler with any bullets that result. */

    @Override
    public void shoot(int totalShots, BulletHandler handler){
        ShotsPerCycle = 5;
        spread = 45f / (ShotsPerCycle * Cycle);
        MaxCycleIteration = 9;
        shots = Cycle * ShotsPerCycle;
        for(int i = 0; i < shots; i++){
            float angleOffset = i * spread - (shots - 1) * spread / 2f;
            handler.shoot(0, 0, angleOffset, firstShotDelay + shotDelay * i);
        }
        Cycle += 1;
        if (Cycle >= MaxCycleIteration + 1){
            Cycle = 1;
        }
        }
    }