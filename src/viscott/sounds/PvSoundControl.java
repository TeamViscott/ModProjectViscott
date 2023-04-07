package viscott.sounds;

import arc.Events;
import arc.audio.Sound;
import arc.func.Cons;
import arc.math.Mathf;
import arc.struct.Seq;
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
    public PvSoundControl()
    {
        super();
    }
    @Override
    public void playRandom(){
        if (Vars.state.getPlanet() == PvPlanets.vercilus)
            playOnce(PvMusics.orbit);
        else {
            super.playRandom();
        }
    }
}
