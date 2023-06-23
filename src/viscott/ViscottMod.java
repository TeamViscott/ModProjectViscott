package viscott;

import arc.Events;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Reflect;
import mindustry.Vars;
import mindustry.content.TechTree;
import mindustry.core.NetClient;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.gen.ClientPacketUnreliableCallPacket;
import mindustry.graphics.Layer;
import mindustry.mod.Mod;
import mindustry.mod.Mods;
import mindustry.net.Packets;
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
        });
        PvPacketHandler.load();
        PvWorldState.load();
    }

    @Override
    public void loadContent(){
        Log.info("Loading PV content");
        PvMusics.load();
        PvLogic.load();
        PvFactions.load();
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
        Mods.LoadedMod tu = mods.locateMod("project-viscott");
        tu.meta.author = "[crimson]Sprites : [][white]\n [green]Ethanol10[] \n [yellow]ThomasThings[] \n[crimson]Sfx/Music : [][white]\n [cyan]Vdoble MSG[] \n[crimson]Programmers : [white]\n [orange]Si[red]ede[][] \n [orange]Manuwar[] \n [white][Pseudo3D] [orange]MeepOfFaith \n[crimson]Mappers : \n [purple]Nicodium";
        tu.meta.description = "[red]Project Viscott[] is a [orange]Mindustry mod[] that tries to add a \n[lime]new Planet[] and intends to give the player a [cyan]new[] and \n[purple]unique[] playing experience.\nIt adds [yellow]5 new Teams[], each with their own tech tree\nTo add to that it has a somewhat interresting progression.";
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
