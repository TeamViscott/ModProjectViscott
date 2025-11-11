package viscott.world.ui;

import arc.Core;

import arc.Events;
import arc.func.Cons;
import arc.func.Func;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.scene.ui.Image;
import arc.scene.ui.Label;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.Styles;

public class DialogueManager {

    public interface Hand {
        void get();
    }

    static class ProgTable {
        Table table;
        float progress;
        float progSpeed;
        boolean done;
        float tableSize;

        ProgTable() {
            progress = 0;
            progSpeed = 0;
            done = false;
        }
    }

    static Seq<ProgTable> dialogueTables = new Seq<>();

    public static void initDialogueTimeline() {
        Events.run(EventType.Trigger.preDraw,()->{
            updateDialogue(Time.delta);
        });
    }

    static void updateDialogue(float delta) {
        for(int i = 0; i < dialogueTables.size;i++) {
            var tableProg = dialogueTables.get(i);
            var table = tableProg.table;

            if (!tableProg.done) {
                tableProg.progSpeed += delta / 20;
                tableProg.progress = Mathf.sin(tableProg.progSpeed * Mathf.pi / 1.6f) / Mathf.sin(Mathf.pi/1.6f);
                if (tableProg.progSpeed >= 1) { // going down, it should snap to place.
                    tableProg.progress = 1;
                    tableProg.done = true;
                    dialogueTables.remove(i--);
                }
            }

            var progress = tableProg.progress;
            table.setColor(Color.black.lerp(Color.white,progress));
            table.y = -250 + 280 * progress;

            if (tableProg.done)
                return;

            if (dialogueTables.get(i).table.parent == null) {
                dialogueTables.remove(i--); // as it's index is cleared, the next ones fill the line.
            }
        }
    }

    public static void createDialogue(String text, String title, String image, @Nullable Hand exit) {
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

        var iconFrameTable = iconTable.table().margin(2).maxWidth(250 - 50).get();

        iconFrameTable.background(Tex.buttonEdge3);
        var iconImage = new Image(Core.atlas.find(image));

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
        var btn = buttonTable.button(Tex.buttonDown, Styles.clearNoneTogglei,40,() -> {
            scopeTable.remove();
            exit.get();
        }).tooltip("Close").right().get();
        btn.getStyle().imageUp = Icon.cancel;

        Vars.ui.hudGroup.addChild(scopeTable);

        dialogueTables.add(new ProgTable() {{
            this.table = scopeTable;
            this.tableSize = 250;
        }});
        updateDialogue(0);
    }
}
