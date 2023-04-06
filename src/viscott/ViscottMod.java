package viscott;

import arc.util.Log;
import mindustry.mod.Mod;
import viscott.content.*;

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
        PvMusics.init();
        PvUIs.init();
    }

}
