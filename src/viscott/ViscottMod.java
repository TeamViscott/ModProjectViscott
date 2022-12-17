package viscott;

import arc.Core;
import arc.Events;
import arc.util.Log;
import arc.util.Time;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.ui.dialogs.BaseDialog;
import viscott.content.PvBlocks;
import viscott.content.PvItems;
import viscott.content.PvLiquids;
import viscott.content.PvTurrets;

public class ViscottMod extends Mod {

    public ViscottMod(){
    }

    @Override
    public void loadContent(){
        Log.info("Loading some example content.");
        PvItems.load();
        PvLiquids.load();
        PvBlocks.load();
        PvTurrets.load();
    }

}
