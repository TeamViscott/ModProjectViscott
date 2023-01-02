package viscott.world.block;

import mindustry.world.Block;

public class PvBlock extends Block {
    public PvBlock(String name)
    {
        super(name);
        solid = true;
        destructible = true;
    }
}
