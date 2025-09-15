package viscott.world.ui;

import arc.Core;
import arc.scene.ui.Button;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.Styles;

public class DialogueManager {
    public static Table createdialogue(String text) {
        var table = new Table();

        table.setBackground(Styles.black3);
        float screenWidth = Core.scene.getWidth();
        float offset = screenWidth - 636f;

        table.setPosition(318,50);
        table.setSize(offset,250);

        var textTable = new Table();

        textTable.add(text);

        table.add(textTable).grow();

        table.row();

        var buttonTable = new Table();
        var btn = buttonTable.button(Tex.buttonDown, Styles.clearNoneTogglei,40, table::remove).tooltip("Close").right().get();
        btn.getStyle().imageUp = Icon.cancel;

        table.add(buttonTable).growX();

        Vars.ui.hudGroup.addChild(table);
        return table;
    }
}
