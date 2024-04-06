package viscott.world.chips;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.Posc;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import viscott.content.PvStatusEffects;
import viscott.types.PvLayers;
import viscott.world.bullets.VoidBulletType;

import static mindustry.Vars.mods;
import static mindustry.Vars.renderer;

public interface SourceArea {
    default void updateVoid(Building building,float radius)
    {
        Groups.bullet.each(b -> {
            if (b.type instanceof VoidBulletType && Mathf.len(building.x-b.x,building.y-b.y) <= radius)
                b.absorb(); //now it destroys void bullets
        });
        Groups.unit.each(unit ->
                {
                    if (unit.team == building.team) {
                        if (Mathf.len(building.x-unit.x,building.y-unit.y) <= radius) {
                            unit.buildSpeedMultiplier*= 100;
                            unit.apply(PvStatusEffects.sourceRepair,30);
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
                b.absorb(); //same as last one
        });
        Groups.unit.each(unit ->
                {
                    if (unit.team == u.team) {
                        if (Mathf.len(u.x-unit.x,u.y-unit.y) <= radius) {
                            unit.apply(PvStatusEffects.sourceRepair,30);
                        }
                    } else {
                        if (Mathf.len(u.x-unit.x,u.y-unit.y) <= radius) {
                            unit.apply(PvStatusEffects.malfunction,30);

                        }
                    }
                }
        );
    }
    default void drawVoid(Posc pos, float radius)
    {
        Draw.z(PvLayers.antivoidLayer);
        Draw.color(Color.white);
        if(renderer.animateShields)
            Fill.poly(pos.x(),pos.y(),60,radius);
        else
        {
            Lines.stroke(2);
            Lines.poly(pos.x(),pos.y(),60,radius);
            Draw.color(Team.sharded.color.cpy().a(0.2f));
            Draw.z(PvLayers.antivoidLayer);
            Fill.poly(pos.x(),pos.y(),60,radius);
        }
    }
}
