package viscott.world.block.distribution;

import arc.func.Boolf;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.struct.Seq;
import mindustry.entities.units.BuildPlan;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import viscott.content.PvBlocks;

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
        return other instanceof AirBlock || other instanceof Prop ||other instanceof MassConveyor || other instanceof Junction;
    }

    @Override
    public Block getReplacement(BuildPlan req, Seq<BuildPlan> plans) {
        if (junction == null) return this;

        Boolf<Point2> cont = p -> plans.contains(o -> o.x == req.x + p.x && o.y == req.y + p.y && (req.block instanceof MassConveyor || req.block instanceof Junction));
        return cont.get(Geometry.d4(req.rotation)) &&
                cont.get(Geometry.d4(req.rotation - 2)) &&
                req.tile() != null &&
                req.tile().block() instanceof MassConveyor &&
                Mathf.mod(req.tile().build.rotation - req.rotation, 2) == 1 ? junction : this;
    }

}
