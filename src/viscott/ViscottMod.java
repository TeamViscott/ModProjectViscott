package viscott;

import arc.Events;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import mindustry.entities.part.DrawPart;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import viscott.content.*;
import viscott.sounds.PvSoundControl;

import static mindustry.Vars.control;

public class ViscottMod extends Mod {

    public ViscottMod(){
    }

    @Override
    public void loadContent(){
        Log.info("Loading PV content");
        PvMusics.load();
        PvLogic.load();
        PvUIs.load();
        PvAttributes.load();
        PvStats.load();
        PvMaptools.load();
        PvEffects.load();
        PvItems.load();
        PvStatusEffects.load();
        PvLiquids.load();
        PvWeathers.load();
        PvUnits.load();
        PvBlocks.load();
        PvTurrets.load();
        PvPlanets.load();
        PvVanillaChanges.load();
        ProjectViscottTechTree.load();
        bTurrets.load();
        Log.info("PV content load finished");
    }
    @Override
    public void init(){
        super.init();
        overRideOldSound();
        PvUIs.init();
    }

    public void overRideOldSound()
    {
        PvSoundControl.ogSoundControl = control.sound;
        control.sound = new PvSoundControl();
    }

}
