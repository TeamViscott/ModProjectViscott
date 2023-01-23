package viscott.content;
import arc.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.world.meta.StatUnit;

import java.util.*;

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
