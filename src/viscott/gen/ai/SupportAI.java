package viscott.gen.ai;

import mindustry.Vars;
import mindustry.ai.types.MinerAI;
import mindustry.content.Blocks;
import mindustry.gen.Building;
import mindustry.gen.Call;
import mindustry.type.Item;
import mindustry.world.Tile;

public class SupportAI extends MinerAI {

    @Override
    public void updateMovement() {
        Building core = this.unit.closestCore();
        if (core.wasRecentlyDamaged()) {
            this.moveTo(core, core.block.size * 4);
            faceTarget();
        } else if (this.unit.canMine() && core != null) {
            if (this.unit.mineTile != null && !this.unit.mineTile.within(this.unit, this.unit.type.mineRange)) {
                this.unit.mineTile((Tile) null);
            }

            if (this.ore != null && !this.unit.validMine(this.ore)) {
                this.ore = null;
                this.unit.mineTile = null;
            }

            if (this.mining) {
                if (this.timer.get(1, 240.0F) || this.targetItem == null) {
                    this.targetItem = (Item) this.unit.type.mineItems.min(
                            (i) -> Vars.indexer.hasOre(i) && this.unit.canMine(i),
                            (i) -> (float) core.items.get(i)
                    );
                }

                if (this.targetItem != null && core.acceptStack(this.targetItem, 1, this.unit) == 0) {
                    this.unit.clearItem();
                    this.unit.mineTile = null;
                    return;
                }

                if (this.unit.stack.amount < this.unit.type.itemCapacity && (this.targetItem == null || this.unit.acceptsItem(this.targetItem))) {
                    if (this.timer.get(2, 60.0F) && this.targetItem != null) {
                        this.ore = Vars.indexer.findClosestOre(this.unit, this.targetItem);
                    }

                    if (this.ore != null) {
                        this.moveTo(this.ore, this.unit.type.mineRange / 2.0F, 20.0F);
                        if (this.ore.block() == Blocks.air && this.unit.within(this.ore, this.unit.type.mineRange)) {
                            this.unit.mineTile = this.ore;
                        }

                        if (this.ore.block() != Blocks.air) {
                            this.mining = false;
                        }
                    }
                } else {
                    this.mining = false;
                }
            }

        } else {
            this.unit.mineTile = null;
            if (this.unit.stack.amount == 0) {
                this.mining = true;
                return;
            }

            if (this.unit.within(core, this.unit.type.range)) {
                if (core.acceptStack(this.unit.stack.item, this.unit.stack.amount, this.unit) > 0) {
                    Call.transferItemTo(this.unit, this.unit.stack.item, this.unit.stack.amount, this.unit.x, this.unit.y, core);
                }

                this.unit.clearItem();
                this.mining = true;
            }

            this.circle(core, this.unit.type.range / 1.8F);
        }

    }
}
