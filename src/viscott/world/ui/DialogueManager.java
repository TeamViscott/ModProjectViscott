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
        var frameTable = new Table(Tex.buttonEdge3);

        float screenWidth = Core.scene.getWidth();
        float offset = screenWidth - 636f;

        frameTable.setPosition(318,50);
        frameTable.setSize(offset,250);

        var table = frameTable.table().grow().pad(10).get();

        var textTable = table.table().grow().get();

        textTable.add(text).left().top();

        table.row();

        var buttonTable = table.table().growX().right().get();
        buttonTable.right();
        var btn = buttonTable.button(Tex.buttonDown, Styles.clearNoneTogglei,40, table::remove).tooltip("Close").right().get();
        btn.getStyle().imageUp = Icon.cancel;

        Vars.ui.hudGroup.addChild(table);
        return frameTable;
    }
}
