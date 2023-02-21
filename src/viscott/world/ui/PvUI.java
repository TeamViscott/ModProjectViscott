package viscott.world.ui;

import arc.ApplicationListener;
import arc.assets.Loadable;
import mindustry.core.UI;
import viscott.types.logic.PvLogicDialog;

public class PvUI extends UI implements ApplicationListener, Loadable {
    public PvLogicDialog customLogic;
    @Override
    public void init() {
        customLogic = new PvLogicDialog();
    }
}
