package viscott.world.ui;

import arc.ApplicationListener;
import arc.assets.Loadable;
import mindustry.core.UI;

public class PvUI implements ApplicationListener, Loadable {
    public PvLogicDialog customLogic;
    public SelectDialog selectedDialog;
    @Override
    public void init() {
        customLogic = new PvLogicDialog();
        selectedDialog = new SelectDialog();
    }
}
