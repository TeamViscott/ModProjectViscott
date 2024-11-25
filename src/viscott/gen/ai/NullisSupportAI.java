package viscott.gen.ai;

import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.ai.types.MinerAI;
import mindustry.content.Blocks;
import mindustry.entities.Units;
import mindustry.entities.units.AIController;
import mindustry.gen.Building;
import mindustry.gen.Call;
import mindustry.gen.Unit;
import mindustry.type.Item;
import mindustry.world.Tile;

public class NullisSupportAI extends AIController {
    public boolean mining = true;
    public Item targetItem;
    public Tile ore;

    public NullisSupportAI() {

    }

    @Override
    public boolean keepState() {
        return true;
    }
    @Override
    public void updateMovement() {
        Building core = this.unit.closestCore();

        if (core.wasRecentlyDamaged()) {
            circle(core, core.block.size * 4);
            faceTarget();
            return;
        } else if (Units.nearEnemy(unit.team,unit.x,unit.y,unit.range(),unit.range())) {
            faceTarget();
            return;
        }

        if (this.unit.canMine() && core != null) {
            if (this.unit.mineTile != null && !this.unit.mineTile.within(this.unit, this.unit.type.mineRange)) {
                this.unit.mineTile(null);
            }

            if (this.mining) {
                if (this.timer.get(1, 240.0F) || this.targetItem == null) {
                    this.targetItem = this.unit.type.mineItems.min(
                            (i) -> Vars.indexer.hasOre(i) && this.unit.canMine(i),
                            (i) -> (float)core.items.get(i)
                    );
                }

                if (this.targetItem == null)
                    circle(core,unit.range()-16,unit.speed()/3);

                if (this.targetItem != null && core.acceptStack(this.targetItem, 1, this.unit) == 0) {
                    this.unit.clearItem();
                    this.unit.mineTile = null;
                    circle(core,unit.range()-16,unit.speed()/3);
                    return;
                }

                if (this.unit.stack.amount < this.unit.type.itemCapacity && (this.targetItem == null || this.unit.acceptsItem(this.targetItem))) {
                    if (this.timer.get(2, 60.0F) && this.targetItem != null) {
                        ore = Vars.indexer.findClosestOre(this.unit, this.targetItem);
                    }
                    Log.info(this.ore);
                    if (ore != null) {
                        this.moveTo(ore, this.unit.type.mineRange / 2.0F, 20.0F);
                        if (ore.block() == Blocks.air && this.unit.within(this.ore, this.unit.type.mineRange)) {
                            this.unit.mineTile = this.ore;
                        }

                        if (ore.block() != Blocks.air) {
                            ore = null;
                        }
                    }
                }
            }
        }
    }
}
