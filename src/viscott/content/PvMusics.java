package viscott.content;

import arc.Core;
import arc.audio.Music;
import arc.util.Log;
import mindustry.gen.Musics;
import viscott.sounds.PvSoundControl;

import static mindustry.Vars.*;

public class PvMusics {
    public static Music
        orbit = new Music();

    public static void load()
    {
        Core.assets.load("sounds/outer.wav", Music.class).loaded = (a) -> {
            orbit = a;
        };
    }

}
