package viscott.content;

import arc.Core;
import arc.audio.Music;
import arc.audio.Sound;

public class PvSounds {
    public static Sound
            snowCrunch = new Sound(),
            splinter = new Sound();

    public static void load()
    {
        Core.assets.load("sounds/snow_crunch.ogg", Sound.class).loaded = (a) -> {
            snowCrunch = a;
        };
        Core.assets.load("sounds/splinter.ogg",Sound.class).loaded = (a) -> {
            splinter = a;
        };
    }
}
