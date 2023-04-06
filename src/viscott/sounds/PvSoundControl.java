package viscott.sounds;

import arc.audio.Sound;
import mindustry.Vars;
import mindustry.audio.SoundControl;
import viscott.content.PvMusics;
import viscott.content.PvPlanets;

public class PvSoundControl extends SoundControl {
    public PvSoundControl()
    {
        super();
    }

    @Override
    public void playRandom(){
        if (Vars.state.getPlanet() == PvPlanets.vercilus)
            playOnce(PvMusics.orbit);
        else
            super.playRandom();
    }
}
