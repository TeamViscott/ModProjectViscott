package viscott.utilitys;

import static arc.Core.bundle;
import static mindustry.Vars.ui;

public class PvOptions {
    public static void load() {
        ui.settings.addCategory(bundle.get("setting.pv-title"), "project-viscott-settings-icon", t -> {
            t.checkPref("pv-extra-stuff", false);
            t.checkPref("pv-strafe-double-tap",false);
            t.checkPref("pv-strafe-units-move-rotation",false);
        });
    }
}
