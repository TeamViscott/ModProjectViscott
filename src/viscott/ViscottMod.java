package viscott;

import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.TechTree;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.mod.Mods;
import viscott.audio.PvMusics;
import viscott.audio.PvSounds;
import viscott.content.*;
import viscott.maps.filters.PvFilterOption;
import viscott.sounds.PvSoundControl;
import viscott.types.PvFaction;
import viscott.types.PvLayers;
import viscott.utilitys.PvPacketHandler;
import viscott.utilitys.PvWorldState;

import static arc.Core.*;
import static mindustry.Vars.*;

public class ViscottMod extends Mod {

    public ViscottMod(){
        Events.run(EventType.Trigger.drawOver, () -> {
            if (Vars.renderer.animateShields && PvShaders.nullisAura != null)
                Draw.drawRange(PvLayers.voidLayer, 0.1f, () -> {
                    if (!Vars.renderer.effectBuffer.isBound())
                        Vars.renderer.effectBuffer.begin(Color.clear);
                }, () ->
                {
                    Vars.renderer.effectBuffer.end();
                    Vars.renderer.effectBuffer.blit(PvShaders.nullisAura);
                    if (Vars.renderer.effectBuffer.isBound())
                        Vars.renderer.effectBuffer.endBind();
                });

            if (Vars.renderer.animateShields && PvShaders.antiVoid != null)
                Draw.drawRange(PvLayers.antivoidLayer, 0.1f, () -> {
                    if (!Vars.renderer.effectBuffer.isBound())
                        Vars.renderer.effectBuffer.begin(Color.clear);
                }, () ->
                {
                    Vars.renderer.effectBuffer.end();
                    Vars.renderer.effectBuffer.blit(PvShaders.antiVoid);
                    if (Vars.renderer.effectBuffer.isBound())
                        Vars.renderer.effectBuffer.endBind();
                });

            if (Vars.renderer.animateShields && PvShaders.timeWarpAura != null)
                Draw.drawRange(PvLayers.timeWarp, 0.1f, () -> {
                    if (!Vars.renderer.effectBuffer.isBound())
                        Vars.renderer.effectBuffer.begin(Color.clear);
                }, () ->
                {
                    Vars.renderer.effectBuffer.end();
                    Vars.renderer.effectBuffer.blit(PvShaders.timeWarpAura);
                    if (Vars.renderer.effectBuffer.isBound())
                        Vars.renderer.effectBuffer.endBind();
                });
        });
    }

    @Override
    public void loadContent(){
        Log.info("Loading PV content");

        PvPacketHandler.load();
        PvWorldState.load();
        PvUnitMapper.load();
        PvMusics.load();
        PvSounds.load();
        PvLogic.load();
        PvFactions.load();
        PvUIs.load();
        PvShaders.init();
        PvAttributes.load();
        PvStats.load();
        PvEffects.load();
        PvItems.load();
        PvStatusEffects.load();
        PvLiquids.load();
        PvUnits.load();
        PvWeathers.load();
        PvBlocks.load();
        PvMaptools.load();
        PvTurrets.load();
        PvPlanets.load();
        PvSectorPresets.load();
        PvVanillaChanges.load();
        ProjectViscottTechTree.load();
        bTurrets.load();

        PvFilterOption.init();

        Log.info("PV content load finished");
    }
    public void loadSettings() {
        ui.settings.addCategory(bundle.get("setting.pv-title"), "test-utils-settings-icon", t -> {
            t.checkPref("pv-extra-factions", false);
        });
    }
    @Override
    public void init(){
        loadSettings();
        Mods.LoadedMod tu = mods.locateMod("project-viscott");
        tu.meta.author = """
                [yellow]CREDITS[]:
                
                [crimson]Spriters : [green]Ethanol10[], [yellow]ThomasThings[], [green]TLB1[], [orange]FrireDragon[], [white]EyeOf[gray]Darkness[][]
                ----------------------------
                [crimson]Sfx/Music : [cyan]Vdoble MSG [yellow], [green]TempoAlch3mist[]
                ----------------------------
                [crimson]Programmers : [orange]Si[red]ede ,[purple]Manuwar [yellow],Otamamori [lightgrey],Kapzduke[yellow], [white]EyeOf[gray]Darkness[yellow]
                ----------------------------
                [crimson]Mappers : [purple]Nicodium[yellow]
                ----------------------------
                
                [purple]Special thanks to MEEPofFaith for creating the pseudo3d system.
                """;
        tu.meta.description = """
                [green]Project viscott[] is a mindustry mod that adds a 
                new Planet to the campaign, [purple]Vercilus[], and gives the player a [orange]new[] and [red]unique[] gameplay experience 
                It adds [blue]5 new Factions[], each with their own [orange]tech tree[]
                To add to that, this mod makes a completely new progression and a new way to make units, [yellow]bulk factories[].
                
                This mod is [yellow]still in development[]. 
                Expect it to have some[scarlet] slight balancing issues[]
                """;
        super.init();
        overRideOldSound();
        PvUIs.init();

        PvFaction.all.each(team -> {
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
