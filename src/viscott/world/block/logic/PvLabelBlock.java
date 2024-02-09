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
        Font font = Fonts.outline;

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
            if (smoothEfficiency < 0.05) return;
            Draw.z(Layer.endPixeled);

            float fontSize = 1.5f * smoothEfficiency;

            GlyphLayout layout = Pools.obtain(GlyphLayout.class, GlyphLayout::new);

            boolean ints = font.usesIntegerPositions();
            font.setUseIntegerPositions(false);
            font.getData().setScale(0.25f / Scl.scl(1f) * fontSize);
            layout.setText(font, message);

            // Box draw
            Draw.color(0f, 0f, 0f, 0.3f);
            Fill.rect(x, y /* - layout.height / 2 */, layout.width + 2, layout.height + 3);
            Draw.color();

            // Text draw
            font.setColor(Color.white);
            font.draw(message, x, y + layout.height / 2, 0, Align.center, false);

            // Resets the font and pools.
            Draw.reset();
            Pools.free(layout);
            font.getData().setScale(1f);
            font.setColor(Color.white);
            font.setUseIntegerPositions(ints);
        }
    }
}
