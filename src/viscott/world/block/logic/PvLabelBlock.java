package viscott.world.block.logic;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Font;
import arc.graphics.g2d.GlyphLayout;
import arc.math.Mathf;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.pooling.Pools;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.ui.Fonts;
import mindustry.ui.Styles;
import mindustry.world.blocks.logic.MessageBlock;

import static mindustry.Vars.tilesize;

public class PvLabelBlock extends MessageBlock {
    public PvLabelBlock(String name) {
        super(name);
        update = true;
    }
    public class PvLabelBuild extends MessageBuild {
        Table t = new Table();

        float smoothEfficiency = 0;

        @Override
        public void update() {
            efficiency = power.status;
            smoothEfficiency = Mathf.lerp(smoothEfficiency,efficiency,0.06f);
            if (smoothEfficiency > 0.98)
                smoothEfficiency = 1;
            super.update();
        }
        @Override
        public void draw() {
            super.draw();
            if (smoothEfficiency < 0.5) return;
            Draw.z(Layer.endPixeled);
            CharSequence mes = message;
            String[] labels = mes.toString().split("\n");
            t.clear();
            t.setPosition(x,y);
            t.layout();
            for(String s : labels) {
                Table it = new Table();
                it.background(Styles.black6);
                it.add(s, 0.3f * (smoothEfficiency*2-1)).row();
                it.margin(1);
                t.add(it).row();

            }
            t.draw();
        }
    }
}
