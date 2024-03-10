package viscott.content;

import arc.Core;
import arc.audio.Music;

public class PvMusics {
    public static Music
        orbit = new Music();

    public static void load()
    {
        Core.assets.load("music/outer.ogg", Music.class).loaded = (a) -> {
            orbit = a;
        };
    }

}
