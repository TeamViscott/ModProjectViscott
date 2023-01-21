package viscott.content.shootpatterns;

import mindustry.entities.*;
/** copied from mindustry shoot pattern but modified a bit */

public class CyclicShootPattern implements Cloneable{
    /** amount of shots per "trigger pull". Don't use, It won't work */
    public int shots = 1;
    /** delay in ticks before first shot */
    public float firstShotDelay = 0;
    /** delay in ticks between shots */
    public float shotDelay = 5;
    private int Cycle = 1;
    public int MaxCycleIteration = 3;
    /** sets how many shots should be called per cycle done */
    public int ShotsPerCycle = 1;
    /** Called on a single "trigger pull". This function should call the handler with any bullets that result. */
    public void shoot(int totalShots, BulletHandler handler){
        shots = Cycle / ShotsPerCycle;
        for(int i = 0; i < shots; i++){
            handler.shoot(0, 0, 0, firstShotDelay + shotDelay * i);
        }
        Cycle += 1;
        if (Cycle >= MaxCycleIteration + 1){
            Cycle = 1;
        }
        }

    /** Subclasses should override this to flip its sides. */
    public void flip(){

    }

    public CyclicShootPattern copy(){
        try{
            return (CyclicShootPattern)clone();
        }catch(CloneNotSupportedException absurd){
            throw new RuntimeException("impending doom", absurd);
        }
    }

    public interface BulletHandler{
        /**
         * @param x x offset of bullet, should be transformed by weapon rotation
         * @param y y offset of bullet, should be transformed by weapon rotation
         * @param rotation rotation offset relative to weapon
         * @param delay bullet delay in ticks
         * */
        default void shoot(float x, float y, float rotation, float delay){
            shoot(x, y, rotation, delay, null);
        }

        void shoot(float x, float y, float rotation, float delay, Mover move);
    }
}