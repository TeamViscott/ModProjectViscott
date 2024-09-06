package viscott.world.block.environment;

import arc.struct.Seq;
import mindustry.graphics.CacheLayer;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UnstableFloor extends Floor {
    /// Idea is the following : this floor is deep liquid like, until its stabilized.
    /// it does so by storing the count and incrementing / reducing it, anytime its incremented or reduced it will update
    /// if it should change form.

    HashMap<Tile, AtomicInteger> solidity;

    static Seq<UnstableFloor> allUnstables = new Seq<>();

    public UnstableFloor(String name) {
        super(name);
        isLiquid = true;
        drownTime = 240f;
        cacheLayer = CacheLayer.water;
        albedo = 0.9f;
        solidity = new HashMap<>();
    }

    /// when switching the map n stuff, you don't want to keep that info.
    public static void ResetUnstables() {
        allUnstables.each(u -> u.solidity.clear());
    }

    public void setStability(Tile tile, int count) {
        solidity.put(tile,new AtomicInteger(count));
    }


    @Override
    public boolean isDeep() {
        return super.isDeep();
    };
}
