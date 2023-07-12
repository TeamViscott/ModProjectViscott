package viscott.world.block.liquids;

import mindustry.entities.Puddles;
import mindustry.gen.Building;
import mindustry.gen.Puddle;
import mindustry.world.blocks.liquid.Conduit;
import viscott.content.PvLiquids;
import viscott.types.VoidLiquid;
import viscott.world.block.PvBlock;

public class VoidSource extends PvBlock {
    public float voidAmount = 1;
    public VoidSource(String name) {
        super(name);
        update = true;
    }

    public class VoidSourceBuild extends Building {

        @Override
        public void update() {
            Puddles.deposit(tile(),PvLiquids.concentratedVoid,voidAmount);
        }

        @Override
        public void damage(float damage) {
            if (damage != ((VoidLiquid)PvLiquids.concentratedVoid).voidDamage/60)
                super.damage(damage);
        }
    }
}
