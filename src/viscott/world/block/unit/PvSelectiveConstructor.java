package viscott.world.block.unit;

import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.world.Block;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.blocks.payloads.Constructor;

public class PvSelectiveConstructor extends Constructor {
    public Seq<Block> constructions = new Seq<>();
    public PvSelectiveConstructor(String name) {
        super(name);
    }

    @Override
    public boolean canProduce(Block b){
        return constructions.contains(b);
    }

    public class PvSelectiveConstructorBuild extends ConstructorBuild {
        @Override
        public void buildConfiguration(Table table){
            ItemSelection.buildTable(PvSelectiveConstructor.this, table, constructions, () -> recipe, this::configure, selectionRows, selectionColumns);
        }
    }
}
