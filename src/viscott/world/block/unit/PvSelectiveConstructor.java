package viscott.world.block.unit;

import arc.struct.Seq;
import mindustry.world.Block;
import mindustry.world.blocks.payloads.Constructor;
import mindustry.world.blocks.storage.CoreBlock;

import static mindustry.Vars.state;

public class PvSelectiveConstructor extends Constructor {
    public Seq<Block> constructables = new Seq<>();
    public PvSelectiveConstructor(String name) {
        super(name);
    }

    @Override
    public boolean canProduce(Block b){
        return constructables.contains(b) && b.isVisible()  && !(b instanceof CoreBlock) && (filter.isEmpty() || filter.contains(b));
    }
}
