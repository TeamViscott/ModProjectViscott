package viscott.world.ui;

import arc.Core;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.util.Log;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import viscott.world.block.logic.PvSelector;

import static mindustry.Vars.control;

public class SelectDialog extends BaseDialog {
    PvSelector.PvSelectorBuild caller;
    Table selection = new Table();
    TextureRegionDrawable selectedIcon;
    int width = 20;
    public SelectDialog(){
        super("Select");

        clearChildren();

        shouldPause = true;

        addCloseListener();

        shown(this::setup);
        hidden(() -> {
            if (selectedIcon != null)
                caller.addIcon(selectedIcon);
        });
        onResize(() -> {
            setup();
        });
        add("Select an icon :").growX().center().name("Pcanvas");

        row();

        add(selection).grow().name("Pcanvas");

        row();

        add(buttons).growX().name("Pcanvas");
    }
    public void call(Building build)
    {
        if (build instanceof PvSelector.PvSelectorBuild selB)
            caller = selB;
        selectedIcon = null;
        show();
    }
    public void setup()
    {
        buttons.clearChildren();
        selection.clearChildren();
        buttons.defaults().size(160f, 64f);
        buttons.button("@back", Icon.left, this::hide).name("back");
        int index = 0;
        for(String iconName : Icon.icons.keys()) {
            if (caller.hasIcon(Icon.icons.get(iconName))) continue;
            if (iconName.contains("Small")) continue;
            ImageButton btn = selection.button(Tex.whiteui, Styles.clearNoneTogglei, 40, () -> {
                control.input.config.hideConfig();
                hide();
            }).tooltip(iconName).get();
            btn.getStyle().imageUp = Icon.icons.get(iconName);
            btn.changed(() -> {
                if (btn.isChecked())
                {
                    selectedIcon = Icon.icons.get(iconName);
                }
            });
            if (++index%width==0)
                selection.row();
        }
    }
}
