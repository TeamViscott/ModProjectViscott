package viscott.world.block.liquids;

import mindustry.entities.Puddles;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import viscott.content.PvLiquids;
import viscott.types.VoidLiquid;
import viscott.world.block.PvBlock;

public class SpecialSource extends PvBlock {
    public float liquidAmount = 1;

    public Liquid liquid = PvLiquids.concentratedVoid;
    public SpecialSource(String name) {
        super(name);
        update = true;
    }

    public class SpecialSourceBuild extends Building {

        @Override
        public void update() {
            Puddles.deposit(tile(),liquid,liquidAmount);
        }

        @Override
        public void damage(float damage) {
            if (damage != ((VoidLiquid)PvLiquids.concentratedVoid).voidDamage/60)
                super.damage(damage);
        }
    }
}
