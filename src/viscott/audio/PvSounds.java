package viscott.audio;

import arc.Core;
import arc.audio.Music;
import arc.audio.Sound;
import arc.files.Fi;
import mindustry.Vars;
import mindustry.gen.Sounds;
import mindustry.logic.LExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PvSounds {
    static AtomicInteger loadCounter = new AtomicInteger();
    public static Sound
            snowCrunch,
            splinter;


    public static void load()
    {
        snowCrunch = Vars.tree.loadSound("snow-crunch");
        splinter = Vars.tree.loadSound("splinter");
    }
}
