package viscott.sounds;

import arc.Events;
import arc.audio.Music;
import arc.audio.Sound;
import arc.func.Cons;
import arc.math.Mathf;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Reflect;
import arc.util.Time;
import mindustry.Vars;
import mindustry.audio.SoundControl;
import mindustry.content.StatusEffects;
import mindustry.game.EventType;
import mindustry.game.SpawnGroup;
import mindustry.gen.Groups;
import mindustry.type.StatusEffect;
import viscott.content.PvMusics;
import viscott.content.PvPlanets;

import java.util.List;

import static mindustry.Vars.state;

public class PvSoundControl extends SoundControl {
    public static SoundControl ogSoundControl;
    boolean loaded = false;
    protected float fade; // .-.
    public PvSoundControl()
    {
        super();
        ObjectMap<Object, Seq<Cons<?>>> events = Reflect.get(Events.class,"events");
        if (events.get(EventType.WaveEvent.class).size == 5) {
            try {
                if (events.get(EventType.WaveEvent.class).get(3).toString().contains("SoundControl")) {
                    events.get(EventType.WaveEvent.class).remove(3);
                    loaded = true;
                }
            }
            catch (Exception e) {
                Log.err(e);
            }
        }
    }
    @Override
    public void playRandom(){
        if (!loaded) return;
        playOnce(PvMusics.orbit);
        /*
        if (Vars.state.getPlanet() == PvPlanets.vercilus)
            playOnce(PvMusics.orbit);
        else {
            super.playRandom();
        }
         */
    }

    protected void playOnce(Music music){
        if(current != null || music == null || !shouldPlay()) return; //do not interrupt already-playing tracks

        //save last random track played to prevent duplicates
        lastRandomPlayed = music;

        //set fade to 1 and play it, stopping the current when it's done
        super.fade = 1f;
        current = music;
        current.setVolume(1f);
        current.setLooping(false);
        current.play();
    }

    public void silence() {
        super.silence();
    }
}
