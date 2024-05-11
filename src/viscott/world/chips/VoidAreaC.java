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
import viscott.types.PvLayers;
import viscott.world.block.defense.VoidWall;
import viscott.world.bullets.VoidBulletType;

import static mindustry.Vars.renderer;

public interface VoidAreaC {
    default void updateVoid(Teamc t,float radius) {
        updateVoid(t,radius,PvStatusEffects.voidDecay,PvStatusEffects.voidShield);
    }
    default void updateVoid(Teamc t, float radius, StatusEffect ef, StatusEffect af)
    {
        updateVoid(t,radius,ef,af,30f);
    }
    default void updateVoid(Teamc t, float radius, StatusEffect ef, StatusEffect af,float effectTimer)
    {
        updateVoid(t,radius,ef,af,30f,true);
    }
    default void updateVoid(Teamc t, float radius, StatusEffect ef, StatusEffect af,float effectTimer,boolean voidEffect)
    {
        if (voidEffect)
            Groups.bullet.each(b -> {
                if (b.type instanceof VoidBulletType && Mathf.len(t.x()-b.x,t.y()-b.y) <= radius)
                    b.keepAlive = true;
            });
        Groups.unit.each(unit ->
                {
                    if (unit.team == t.team()) {
                        if (af == null) return;
                        if (Mathf.len(t.x()-unit.x,t.y()-unit.y) <= radius) {
                            unit.apply(af,effectTimer);
                        }
                    } else {
                        if (ef == null) return;
                        if (Mathf.len(t.x()-unit.x,t.y()-unit.y) <= radius) {
                            unit.apply(ef,effectTimer);
                        }
                    }
                }
        );
        t.team().data().buildings.each(b -> {
            if (b instanceof VoidWall.VoidWallBuild v && Mathf.len(t.x()-b.x,t.y()-b.y) <= radius)
                v.inVoid = true;
        });
    }

    default void drawVoid(Posc pos,float radius) {
        drawArea(pos,radius,PvLayers.voidLayer);
    }
    default void drawArea(Posc pos,float radius,float layer)
    {
        Draw.z(layer);
        Draw.color(Color.white);
        if(renderer.animateShields)
            Fill.poly(pos.x(),pos.y(),60,radius);
        else
        {
            Lines.stroke(2);
            Lines.poly(pos.x(),pos.y(),60,radius);
            Draw.color(Color.black.cpy().a(0.2f));
            Draw.z(layer-0.1f);
            Fill.poly(pos.x(),pos.y(),60,radius);
        }
    }
}
