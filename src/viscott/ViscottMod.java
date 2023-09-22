package viscott;

import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.TechTree;
import mindustry.content.UnitTypes;
import mindustry.game.EventType;
import mindustry.graphics.Layer;
import mindustry.mod.Mod;
import mindustry.mod.Mods;
import viscott.content.*;
import viscott.sounds.PvSoundControl;
import viscott.types.PvFaction;
import viscott.utilitys.PvPacketHandler;
import viscott.utilitys.PvWorldState;

import static mindustry.Vars.*;

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
            if (Vars.renderer.animateShields && PvShaders.antiVoid != null)
                Draw.drawRange(Layer.bullet+38, 1f, () -> {
                    if (!Vars.renderer.effectBuffer.isBound())
                        Vars.renderer.effectBuffer.begin(Color.clear);
                }, () ->
                {
                    Vars.renderer.effectBuffer.end();
                    Vars.renderer.effectBuffer.blit(PvShaders.antiVoid);
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
        PvWeathers.load();
        PvUnits.load();
        PvBlocks.load();
        PvMaptools.load();
        PvTurrets.load();
        PvPlanets.load();
        PvSectorPresets.load();
        PvVanillaChanges.load();
        ProjectViscottTechTree.load();
        bTurrets.load();
        Log.info("PV content load finished");
    }
    @Override
    public void init(){
        Mods.LoadedMod tu = mods.locateMod("project-viscott");
        tu.meta.author = "[yellow]CREDITS[]: \n\n" +
                "[crimson]Spriters : [green]Ethanol10[], [yellow]ThomasThings \n" +
                "----------------------------\n" +
                "[crimson]Sfx/Music : [cyan]Vdoble MSG [yellow], [green]TempoAlch3mist[]\n" +
                "----------------------------\n" +
                "[crimson]Programmers : [orange]Si[red]ede ,[purple]Manuwar [yellow],Otamamori [lightgrey],Kapzduke[yellow]\n" +
                "----------------------------\n" +
                "[crimson]Mappers : [purple]Nicodium[yellow] \n" +
                "----------------------------\n \n " +
                "[purple]Special thanks to MEEPofFaith for creating the pseudo3d system.";
        tu.meta.description = "[green]Project viscott[] is a mindustry mod that adds a \nnew Planet to the campaign, [purple]Vercilus[], and gives the player a [orange]new[] and [red]unique[] gameplay experience \nIt adds [blue]5 new Factions[], each with their own [orange]tech tree[]\nTo add to that, this mod makes a completely new progression and a new way to make units, [yellow]bulk factories[].\n\nThis mod is [yellow]still in development[]. \n Expect it to have some[scarlet] slight balancing issues[]";
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
