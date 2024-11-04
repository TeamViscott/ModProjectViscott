package viscott.sounds;

import arc.Events;
import arc.audio.Music;
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
import viscott.audio.PvMusics;
import viscott.content.PvPlanets;

public class PvSoundControl extends SoundControl {
    public static SoundControl ogSoundControl;
    boolean loaded = false;
    protected float fade; // .-.
    public PvSoundControl()
    {
        // Take out normal SoundController.
        ObjectMap<Object, Seq<Cons<?>>> events = Reflect.get(Events.class,"events");
        var waveEvents = events.get(EventType.WaveEvent.class);
        try {
            waveEvents.remove(waveEvents.find((a)->a.toString().contains("SoundControl")));
            loaded = true;
        }
        catch (Exception e) {
            Log.err(e);
        }

        // now impliment urself :>
        Events.on(EventType.ClientLoadEvent.class, (e) -> {
            this.reload();
        });
        Events.on(EventType.WaveEvent.class, (e) -> {
            Time.run(Mathf.random(8.0F, 15.0F) * 60.0F, () -> {
                boolean boss = Vars.state.rules.spawns.contains((group) -> group.getSpawned(Vars.state.wave - 2) > 0 && group.effect == StatusEffects.boss);
                if (boss) {
                    this.playOnce(this.bossMusic.random(this.lastRandomPlayed));
                } else if (Mathf.chance(this.musicWaveChance)) {
                    this.playRandom();
                }

            });
        });
        setupFilters();
    }
    @Override
    public void playRandom(){
        if (!loaded) return;
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
