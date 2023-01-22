package viscott.content.shootpatterns;

import mindustry.entities.pattern.ShootPattern;

/** copied from mindustry shoot pattern but modified a bit */

/** keep this file to understand how cyclic system works*/

public class CyclicPatternStriker extends ShootPattern {
    private int Cycle = 1;
    public int MaxCycleIteration = 3;
    /** sets how many shots should be called per cycle done */
    public int ShotsPerCycle = 1;
    /** Called on a single "trigger pull". This function should call the handler with any bullets that result. */

    @Override
    public void shoot(int totalShots, BulletHandler handler){
        ShotsPerCycle = 4;
        MaxCycleIteration = 4;
        shots = Cycle * ShotsPerCycle;
        for(int i = 0; i < shots; i++){
            handler.shoot(0, 0, 0, firstShotDelay + shotDelay * i);
        }
        Cycle += 1;
        if (Cycle >= MaxCycleIteration + 1){
            Cycle = 1;
        }
        }
    }