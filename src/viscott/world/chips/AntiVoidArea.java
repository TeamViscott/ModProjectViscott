package viscott.world.chips;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import viscott.content.PvStatusEffects;
import viscott.world.bullets.VoidBulletType;

import static mindustry.Vars.renderer;

public interface AntiVoidArea {
    default void updateVoid(Building building,float radius)
    {
        Groups.bullet.each(b -> {
            if (b.type instanceof VoidBulletType && Mathf.len(building.x-b.x,building.y-b.y) <= radius)
                b.keepAlive = true;
        });
        Groups.unit.each(unit ->
                {
                    if (unit.team == building.team) {
                        if (Mathf.len(building.x-unit.x,building.y-unit.y) <= radius) {
                            unit.buildSpeedMultiplier*= 100;
                            unit.apply(PvStatusEffects.antiVoidRepair,30);
                        }
                    } else {
                        if (Mathf.len(building.x-unit.x,building.y-unit.y) <= radius) {
                            unit.apply(PvStatusEffects.malfunction,30);

                        }
                    }
                }
        );
    }
    default void updateVoid(Unit u, float radius)
    {
        Groups.bullet.each(b -> {
            if (b.type instanceof VoidBulletType && Mathf.len(u.x-b.x,u.y-b.y) <= radius)
                b.keepAlive = true;
        });
        Groups.unit.each(unit ->
                {
                    if (unit.team == u.team) {
                        if (Mathf.len(u.x-unit.x,u.y-unit.y) <= radius) {
                            unit.apply(PvStatusEffects.antiVoidRepair,30);
                        }
                    } else {
                        if (Mathf.len(u.x-unit.x,u.y-unit.y) <= radius) {
                            unit.apply(PvStatusEffects.malfunction,30);

                        }
                    }
                }
        );
    }
    default void drawVoid(Building building,float radius)
    {
        Draw.z(Layer.bullet+38);
        Draw.color(Color.black);
        if(renderer.animateShields)
            Fill.poly(building.x,building.y,60,radius);
        else
        {
            Lines.stroke(2);
            Lines.poly(building.x,building.y,60,radius);
            Draw.color(Color.white.cpy().a(0.2f));
            Draw.z(Layer.bullet+34);
            Fill.poly(building.x,building.y,60,radius);
        }
    }
    default void drawVoid(Unit unit,float radius)
    {
        Draw.z(Layer.bullet+38);
        Draw.color(Color.black);
        if(renderer.animateShields)
            Fill.poly(unit.x,unit.y,60,radius);
        else
        {
            Lines.stroke(2);
            Lines.poly(unit.x,unit.y,60,radius);
            Draw.color(Color.white.cpy().a(0.2f));
            Draw.z(Layer.bullet+34);
            Fill.poly(unit.x,unit.y,60,radius);
        }
    }
}
