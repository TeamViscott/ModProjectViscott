package viscott.content;

import arc.Events;
import arc.util.Reflect;
import arc.util.Structs;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.logic.GlobalVars;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.blocks.heat.HeatProducer;
import rhino.ContextFactory;
import rhino.JavaAdapter;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import java.lang.reflect.Constructor;
import java.security.cert.Extension;
import java.util.function.Function;

public class PvVanillaChanges {
    public static void load()
    {
        UnitTypes.latum.hidden = false;
        UnitTypes.renale.hidden = false;
        ((HeatProducer)Blocks.heatSource).heatOutput = 10000;
        StatusEffects.corroded.localizedName = "Corroded";
        StatusEffects.corroded.show = true;
    }
}
