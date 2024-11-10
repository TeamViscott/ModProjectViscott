package viscott.world.block.storage;

import mindustry.world.blocks.storage.StorageBlock;
import viscott.gen.WorldUnit;

public class GridUnitStoreLink extends StorageBlock {
    public GridUnitStoreLink(String name) {
        super(name);
        solid = false;
    }
    public class GridUnitStoreBuild extends StorageBuild {
        public WorldUnit gu;
        boolean initialized = false;
        @Override
        public void update() {
            if (gu != null) {
                if (initialized) {

                } else {
                    initialized = true;
                    items.clear();
                }
                items.add(gu.stack.item,gu.stack.amount);
            }
        }
        @Override
        public void draw() {

        }
    }
}
