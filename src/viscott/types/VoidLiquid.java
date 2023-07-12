package viscott.types;

import mindustry.gen.Puddle;
import mindustry.type.Liquid;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Prop;
import mindustry.world.blocks.environment.StaticWall;

public class VoidLiquid extends Liquid {
    public float voidDamage = 5;
    public int[][] checks = {
            {0,1},
            {1,0},
            {0,-1},
            {-1,0}
    };
    public VoidLiquid(String name) {
        super(name);
    }
    @Override
    public void update(Puddle puddle){
        for(int[] i : checks) {
            Tile t = puddle.tile.nearby(i[0], i[1]);
            if ( t == null || t.block() == null ) continue;
            if (t.block() instanceof Prop prop && !(prop instanceof StaticWall))
                t.removeNet();
            if (t.build == null|| !t.build.isValid() ) continue;
            t.build.damage(voidDamage/60);
        }
        super.update(puddle);
    }
}
