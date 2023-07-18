package viscott.content;
import mindustry.gen.*;
import mindustry.world.meta.StatUnit;

/**
 * Defines a unit of measurement for block stats.
 */
public class PvStatUnits extends mindustry.world.meta.StatUnit {
    public static final mindustry.world.meta.StatUnit

            pressureUnits = new StatUnit("pressuretUnits", "[white]" + Iconc.waves + "[]");

    public PvStatUnits(String name) {
        super(name);
    }
}
