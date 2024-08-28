package viscott.world.ui;

import arc.Core;
import arc.Events;
import arc.graphics.g2d.Draw;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import viscott.world.block.logic.PvSelector;

import static mindustry.Vars.control;

public class TextBoxDialog extends Table {

    Table dialogue = new Table();
    Table buttons = new Table();

    boolean vis;
    public TextBoxDialog(){

        super(Tex.button);
        vis = false;

        setClip(false);

        Events.run(EventType.Trigger.draw,()->{
            if (visibility.get()) {
                Draw.z(Layer.endPixeled);
                setPosition(Core.camera.position.getX(),Core.camera.position.getY());
                draw();
            }
        });


        visible(() -> vis);

        clearChildren();



        center();

        row();

        setSize(640,320);

        add(dialogue).grow().name("Dialogue");

        row();

        add(buttons).growX().name("Buttons");
    }

    public void show(String text) {
        setup(text);
    }

    public void setup(String text)
    {
        vis = true;
        dialogue.clearChildren();
        dialogue.add(text);
        buttons.clearChildren();
        buttons.button("@back", Icon.left, ()->{vis=false;}).name("back");
        pack();
    }
}
