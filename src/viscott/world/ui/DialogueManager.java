package viscott.world.ui;

import arc.Core;

import arc.scene.ui.Image;
import arc.scene.ui.Label;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Table;
import arc.util.Scaling;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.Styles;

public class DialogueManager {
    public static Table createDialogue(String text, String title, String image) {
        var scopeTable = new Table();

        float screenWidth = Core.scene.getWidth();
        float offset = screenWidth - 636f;

        scopeTable.setPosition(318,30);
        scopeTable.setSize(offset,250);

        var headerTable = scopeTable.table().growX().get();
        headerTable.left();
        var titleTable = headerTable.table(Tex.buttonEdge3).left().get();
        titleTable.left();
        titleTable.add(title);

        scopeTable.row();

        var frameCell = scopeTable.table(Tex.buttonEdge3).grow();

        var frameTable = frameCell.get();

        var iconTable = frameTable.table().growY().pad(10).get();

        var iconFrameTable = iconTable.table().margin(2).get();

        iconFrameTable.background(Tex.buttonEdge3);
        var iconImage = new Image(Core.atlas.find(image)) {
            @Override
            public float getPrefWidth() {
                return Math.min(super.getPrefWidth(), super.getPrefHeight());
            }

            @Override
            public float getPrefHeight() {
                return getPrefWidth(); // force square aspect
            }
        };

        iconImage.setScaling(Scaling.fit);
        iconImage.setFillParent(false);

        iconFrameTable.add(iconImage).pad(5).get();

        var table = frameTable.table().grow().pad(10).get();

        var textTable = table.table().grow().get();

        var textLabel = new Label(text);
        textLabel.setWrap(true);

        var scrollPane = new ScrollPane(textLabel);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        textTable.add(scrollPane).growX().expandY();

        table.row();

        var buttonTable = table.table().growX().right().get();
        buttonTable.right();
        var btn = buttonTable.button(Tex.buttonDown, Styles.clearNoneTogglei,40, scopeTable::remove).tooltip("Close").right().get();
        btn.getStyle().imageUp = Icon.cancel;

        Vars.ui.hudGroup.addChild(scopeTable);
        return scopeTable;
    }
}
