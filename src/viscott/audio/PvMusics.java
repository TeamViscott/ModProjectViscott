package viscott.audio;

import arc.Core;
import arc.audio.Music;
import mindustry.Vars;
import mindustry.gen.Musics;

public class PvMusics {
    public static Music
        orbit = new Music();

    public static void load()
    {
        orbit = Vars.tree.loadMusic("outer");
    }

}
