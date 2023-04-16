package viscott;

import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.TechTree;
import mindustry.entities.part.DrawPart;
import mindustry.game.EventType;
import mindustry.graphics.Layer;
import mindustry.mod.Mod;
import viscott.content.*;
import viscott.sounds.PvSoundControl;
import viscott.types.PvTeam;

import static mindustry.Vars.control;

public class ViscottMod extends Mod {

    public ViscottMod(){
        Events.run(EventType.Trigger.drawOver, () -> {
            if (Vars.renderer.animateShields && PvShaders.nullisAura != null)
                Draw.drawRange(Layer.bullet+35, 2f, () -> {
                    if (!Vars.renderer.effectBuffer.isBound())
                        Vars.renderer.effectBuffer.begin(Color.clear);
                }, () ->
                {
                    Vars.renderer.effectBuffer.end();
                    Vars.renderer.effectBuffer.blit(PvShaders.nullisAura);
                    if (Vars.renderer.effectBuffer.isBound())
                        Vars.renderer.effectBuffer.endBind();
                });
        });
    }

    @Override
    public void loadContent(){
        Log.info("Loading PV content");
        PvMusics.load();
        PvLogic.load();
        PvTeams.load();
        PvUIs.load();
        PvShaders.init();
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

        PvTeam.all.forEach(team -> {
            if (team.techTree != null && TechTree.roots.contains(team.techTree))
                TechTree.roots.remove(team.techTree);
        });
    }

    public void overRideOldSound()
    {
        PvSoundControl.ogSoundControl = control.sound;
        control.sound = new PvSoundControl();
    }

}
