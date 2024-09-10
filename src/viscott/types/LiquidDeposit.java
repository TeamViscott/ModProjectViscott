package viscott.types;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.blocks.liquid.LiquidBlock;

public class LiquidDeposit extends LiquidBlock {
    public float liquidPadding = 0.0F;

    public LiquidDeposit(String name) {
        super(name);
        solid = true;
        noUpdateDisabled = true;
        canOverdrive = false;
        floating = true;
    }

    public TextureRegion[] icons() {
        return new TextureRegion[]{bottomRegion, region};
    }

    public class LiquidDepositBuild extends LiquidBlock.LiquidBuild {
        public LiquidDepositBuild() {
            super();
        }

        public void updateTile() {
            if (liquids.currentAmount() > 0.01F) {
                dumpLiquid(liquids.current());
            }

        }

        public void draw() {
            Draw.rect(bottomRegion, x, y);
            if (liquids.currentAmount() > 0.001F) {
                LiquidBlock.drawTiledFrames(size, x, y, liquidPadding, liquids.current(), liquids.currentAmount() / liquidCapacity);
            }

            Draw.rect(region, x, y);
        }

        public boolean acceptLiquid(Building source, Liquid liquid) {
            return liquids.current() == liquid || liquids.currentAmount() < 0.2F;
        }
    }
}

