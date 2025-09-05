package viscott.types;

import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import mindustry.content.Liquids;
import mindustry.entities.Puddles;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.Puddle;
import mindustry.type.CellLiquid;
import mindustry.type.Liquid;
import mindustry.type.StatusEffect;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Prop;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.blocks.liquid.Conduit;
import viscott.content.PvLiquids;
import viscott.world.statusEffects.PvStatusEffect;

public class VoidLiquid extends CellLiquid {
    public float voidDamage = 5;
    public StatusEffect voidFlyingEffect = null;
    public VoidLiquid(String name) {
        super(name);
        canStayOn.addAll(Liquids.water);
        spreadTarget = null;
    }
    @Override
    public void update(Puddle puddle){
        if (voidFlyingEffect != null)
            Groups.unit.each(u-> Math.abs(u.x - puddle.x) < 8 && Math.abs(u.y - puddle.y) < 8,u-> {
                if (u.hovering || u.isFlying()) {
                    u.apply(voidFlyingEffect, 30);
                    u.apply(effect, 30);

                }
                puddle.amount += voidDamage/5;
            });
        Building build = puddle.tile.build;
        if (build != null && build.liquids != null && (build.liquids.currentAmount() == 0 || build.liquids.current() == puddle.liquid)) {
            build.handleLiquid(null,puddle.liquid,puddle.amount);
            puddle.remove();
        }
        for(Point2 offset : Geometry.d4) {
            Tile t = puddle.tile.nearby(offset);
            if (t == null) return;
            Puddle np = Puddles.get(t);

            if (np != null && np.liquid == this) {
                puddle.amount += 0.1f; // nearby puddles increase amount gain.
            }

            if ( t == null || t.block() == null ) continue;
            if (t.block() instanceof Prop prop && !(prop instanceof StaticWall)) { // Void eats props
                t.removeNet();
                prop.breakEffect.at(t.x,t.y,0,prop.mapColor);
                puddle.amount += 100;
            }

            if (t.build instanceof Conduit.ConduitBuild cb) {
                if(cb.liquids.current() == this || cb.liquids.currentAmount() == 0) {
                    cb.liquids.add(this,puddle.amount);
                    cb.noSleep();
                    puddle.remove();
                }
            }
            if (t.build == null|| !t.build.isValid() ) continue;
            if (voidDamage < 0)
                t.build.heal(-voidDamage/60);
            else
                t.build.damage(voidDamage/60);
            puddle.amount += voidDamage/60;
        }
    }
}
