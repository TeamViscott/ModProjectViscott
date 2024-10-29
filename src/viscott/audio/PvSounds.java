package viscott.audio;

import arc.Core;
import arc.audio.Music;
import arc.audio.Sound;

public class PvSounds {
    public static Sound
            snowCrunch,
            splinter;

    public static void load()
    {
        Core.assets.load("sounds/project-viscott-snow_crunch.ogg", Sound.class).loaded = (a) -> {
            snowCrunch = a;
        };
        Core.assets.load("sounds/project-viscott-splinter.ogg",Sound.class).loaded = (a) -> {
            splinter = a;
        };
    }
}
