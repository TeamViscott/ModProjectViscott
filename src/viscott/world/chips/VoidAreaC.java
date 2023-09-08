package viscott.world.chips;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.StatusEffect;
import viscott.content.PvStatusEffects;
import viscott.world.bullets.VoidBulletType;

import static mindustry.Vars.renderer;

public interface VoidAreaC {
    default void updateVoid(Teamc t,float radius) {
        updateVoid(t,radius,PvStatusEffects.voidDecay,PvStatusEffects.voidShield);
    }
    default void updateVoid(Teamc t, float radius, StatusEffect ef, StatusEffect af)
    {
        Groups.bullet.each(b -> {
            if (b.type instanceof VoidBulletType && Mathf.len(t.x()-b.x,t.y()-b.y) <= radius)
                b.keepAlive = true;
        });
        Groups.unit.each(unit ->
                {
                    if (unit.team == t.team()) {
                        if (af == null) return;
                        if (Mathf.len(t.x()-unit.x,t.y()-unit.y) <= radius) {
                            unit.apply(af,30);
                        }
                    } else {
                        if (ef == null) return;
                        if (Mathf.len(t.x()-unit.x,t.y()-unit.y) <= radius) {
                            unit.apply(ef,30);

                        }
                    }
                }
        );
    }
    default void drawVoid(Posc pos,float radius)
    {
        Draw.z(Layer.bullet+35);
        Draw.color(Color.white);
        if(renderer.animateShields)
            Fill.poly(pos.x(),pos.y(),60,radius);
        else
        {
            Lines.stroke(2);
            Lines.poly(pos.x(),pos.y(),60,radius);
            Draw.color(Color.black.cpy().a(0.2f));
            Draw.z(Layer.bullet+34);
            Fill.poly(pos.x(),pos.y(),60,radius);
        }
    }
}
