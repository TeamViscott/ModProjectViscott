package viscott.content;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;

public class PvFx {
    public static final Effect

    pureFlame = new Effect(150f, e -> {
        color(Color.valueOf("ffffff"), Color.valueOf("5d9bff"), e.fin());

        randLenVectors(e.id, 6, 6f + e.fin() * 9f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.6f + e.fslope() * 4.5f);
        });

        color();

        Drawf.light(e.x, e.y, 60f * e.fslope(), Color.valueOf("ffffff"), 1.5f);
    });
}

    