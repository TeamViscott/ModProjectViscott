package viscott.audio;

import arc.Core;
import arc.audio.Music;
import mindustry.gen.Musics;

public class PvMusics {
    public static Music
        orbit;

    public static void load()
    {
        Core.assets.load("music/project-viscott-outer.ogg", Music.class).loaded = (a) -> {
            orbit = a;
        };
    }

}
