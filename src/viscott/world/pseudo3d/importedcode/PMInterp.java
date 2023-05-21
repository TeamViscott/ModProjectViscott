/**
 * 
 * WARNING: We are not the authors of this code. The original author is MEEP of faith. We'll maybe make our custom version of the pseudo3d system.
 **/
package viscott.world.pseudo3d.importedcode;

import arc.math.*;

public class PMInterp{
    public static Interp
        flightArc = a -> Interp.sineOut.apply(Interp.slope.apply(a)),
        sineInverse = a -> a < 0.5f ? (Interp.sineOut.apply(a * 2) / 2f) : (0.5f + Interp.sineIn.apply(a * 2 - 1) / 2f);
}