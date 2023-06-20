package viscott.world.block.distribution;

import arc.func.Boolf;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Intersector;
import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Tmp;
import mindustry.entities.units.BuildPlan;
import mindustry.input.Placement;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import viscott.content.PvBlocks;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class MassConveyor extends StackConveyor {
    public Block junction;
    public MassConveyor(String name)
    {
        super(name);
    }

    @Override
    public void init()
    {
        super.init();
        if (junction == null) junction = PvBlocks.massJunction;
    }

    @Override
    public boolean canReplace(Block other){
        return other instanceof AirBlock || (!(other instanceof StaticWall) && other instanceof Prop) ||other instanceof MassConveyor || other instanceof Junction;
    }
}
