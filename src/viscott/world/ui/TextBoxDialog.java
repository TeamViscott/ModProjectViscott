package viscott.world.ui;

import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import viscott.world.block.logic.PvSelector;

import static mindustry.Vars.control;

public class TextBoxDialog extends BaseDialog {
    public TextBoxDialog(){
        super("TextBox");

        clearChildren();

        shouldPause = true;

        addCloseListener();

        shown(this::setup);
        hidden(() -> {

        });
        onResize(() -> {
            setup();
        });
    }
    public void call(Building build)
    {
        show();
    }
    public void setup()
    {
        setHeight(100);
        setWidth(100);
        buttons.clearChildren();
        buttons.defaults().size(160f, 64f);
        buttons.button("@back", Icon.left, this::hide).name("back");
        int index = 0;
    }
}
