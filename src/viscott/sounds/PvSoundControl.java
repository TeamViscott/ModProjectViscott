package viscott.sounds;

import arc.Events;
import arc.audio.Music;
import arc.audio.Sound;
import arc.func.Cons;
import arc.math.Mathf;
import arc.struct.ObjectMap;
import arc.struct.Seq;
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

import static mindustry.Vars.state;

public class PvSoundControl extends SoundControl {
    public static SoundControl ogSoundControl;
    public PvSoundControl()
    {
        super();
        ObjectMap<Object, Seq<Cons<?>>> events = Reflect.get(Events.class,"events");
        var a = events.get(EventType.WaveEvent.class).pop();
        var b = events.get(EventType.WaveEvent.class).pop();
        var c = events.get(EventType.WaveEvent.class).pop();
        events.get(EventType.WaveEvent.class).pop();
        events.get(EventType.WaveEvent.class).add(a);
        events.get(EventType.WaveEvent.class).add(b);
        events.get(EventType.WaveEvent.class).add(c);
    }
    @Override
    public void playRandom(){
        //playOnce(PvMusics.orbit);
        if (Vars.state.getPlanet() == PvPlanets.vercilus)
            playOnce(PvMusics.orbit);
        else {
            super.playRandom();
        }
    }

    protected void playOnce(Music music){
        if(current != null || music == null || !shouldPlay()) return; //do not interrupt already-playing tracks

        //save last random track played to prevent duplicates
        lastRandomPlayed = music;

        //set fade to 1 and play it, stopping the current when it's done
        fade = 1f;
        current = music;
        current.setVolume(1f);
        current.setLooping(false);
        current.play();
    }
}
