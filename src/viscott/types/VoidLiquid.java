package viscott.types;

import arc.math.Mathf;
import mindustry.content.Liquids;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.Puddle;
import mindustry.type.CellLiquid;
import mindustry.type.Liquid;
import mindustry.type.StatusEffect;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Prop;
import mindustry.world.blocks.environment.StaticWall;
import viscott.world.statusEffects.PvStatusEffect;

public class VoidLiquid extends CellLiquid {
    public float voidDamage = 5;
    public StatusEffect voidFlyingEffect = null;
    public int[][] checks = {
            {0,1},
            {1,0},
            {0,-1},
            {-1,0}
    };
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
            });
        Building build = puddle.tile.build;
        if (build != null && build.liquids != null && (build.liquids.currentAmount() == 0 || build.liquids.current() == puddle.liquid)) {
            build.handleLiquid(null,puddle.liquid,puddle.amount);
            puddle.remove();
        }
        for(int[] i : checks) {
            Tile t = puddle.tile.nearby(i[0], i[1]);
            if ( t == null || t.block() == null ) continue;
            if (t.block() instanceof Prop prop && !(prop instanceof StaticWall))
                t.removeNet();
            if (t.build == null|| !t.build.isValid() ) continue;
            t.build.damage(voidDamage/60);
            puddle.amount += voidDamage/60;
        }
        super.update(puddle);
    }
}