package viscott.content;

import mindustry.entities.Effect;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class PvEffects {
    public static Effect
        slowEnergeticEffect
            ;
    public static void load()
    {
        slowEnergeticEffect = new Effect(32,e -> {
            color(Pal.sap);
            alpha(e.fout());
            stroke(e.fout()*4);
            circle(e.x,e.y,e.fin()*8.3f*8);
        });
    }
}
