package viscott.content;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;

public class PvFx {
    public static final Effect

    pureFlame = new Effect(25f, e -> {
        color(Color.valueOf("ffffff"), Color.valueOf("5d9bff"), e.fin());

        randLenVectors(e.id, 8, 8f + e.fin() * 9f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.8f + e.fslope() * 6f);
        });

        color();

        Drawf.light(e.x, e.y, 80f * e.fslope(), Color.valueOf("ffffff"), 2f);
    });
}

    