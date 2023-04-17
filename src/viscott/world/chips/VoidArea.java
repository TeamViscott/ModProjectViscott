package viscott.world.chips;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.graphics.Layer;
import viscott.content.PvStatusEffects;
import viscott.world.bullets.VoidBulletType;

import static mindustry.Vars.renderer;

public interface VoidArea {
    default void updateVoid(Building building,float radius)
    {
        Groups.bullet.forEach(b -> {
            if (b.type instanceof VoidBulletType && Mathf.len(building.x-b.x,building.y-b.y) <= radius)
                b.time = 0;
        });
        Groups.unit.forEach(unit ->
                {
                    if (unit.team == building.team) {
                        if (Mathf.len(building.x-unit.x,building.y-unit.y) <= radius) {
                            unit.buildSpeedMultiplier*= 100;
                            unit.apply(PvStatusEffects.voidShield,30);
                        }
                    } else {
                        if (Mathf.len(building.x-unit.x,building.y-unit.y) <= radius) {
                            unit.apply(PvStatusEffects.voidDecay,30);

                        }
                    }
                }
        );
    }
    default void drawVoid(Building building,float radius)
    {
        Draw.z(Layer.bullet+35);
        Draw.color(Color.white);
        if(renderer.animateShields)
            Fill.poly(building.x,building.y,60,radius);
        else
        {
            Lines.stroke(2);
            Lines.poly(building.x,building.y,60,radius);
            Draw.color(Color.black.cpy().a(0.2f));
            Draw.z(Layer.bullet+34);
            Fill.poly(building.x,building.y,60,radius);
        }
    }
}