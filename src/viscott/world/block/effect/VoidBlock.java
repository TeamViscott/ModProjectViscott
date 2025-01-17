package viscott.world.block.effect;

import arc.graphics.g2d.Draw;
import arc.math.geom.Intersector;
import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.input.Placement;
import mindustry.world.Tile;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import viscott.content.PvFactions;
import viscott.world.block.PvBlock;
import viscott.world.chips.EffectAreaC;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class VoidBlock extends PvBlock {
    public float voidRadius = 1;
    public DrawBlock drawer = new DrawDefault();
    public VoidBlock(String name)
    {
        super(name);
        update = true;
        faction.add(PvFactions.Nullis);
    }

    @Override
    public void init()
    {
        updateClipRadius(voidRadius*8);
        super.init();
        drawer.load(this);
    }

    @Override
    public void changePlacementPath(Seq<Point2> points, int rotation){
        Placement.calculateNodes(points, this, rotation, (point, other) -> overlaps(world.tile(point.x, point.y), world.tile(other.x, other.y)));
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x,y,rotation,valid);
        Drawf.dashCircle(x*8+offset,y*8+offset,voidRadius*8, Pal.lighterOrange);
    }
    boolean overlaps(@Nullable Tile src, @Nullable Tile other){
        if(src == null || other == null) return true;
        return Intersector.overlaps(Tmp.cr1.set(src.worldx() + offset, src.worldy() + offset, voidRadius * tilesize), Tmp.r1.setSize(size * tilesize).setCenter(other.worldx() + offset, other.worldy() + offset));
    }
    public class VoidBuilding extends Building implements EffectAreaC
    {
        float time = 0;
        @Override
        public void updateTile()
        {
            super.updateTile();
            updateVoid(this,8*voidRadius);
        }

        @Override
        public void draw()
        {
            Draw.z(Layer.blockOver-2);
            drawer.draw(this);
            drawVoid(this,8*voidRadius);
        }

        @Override
        public float warmup() {
            time += Time.delta / 60;
            time %= 180;
            return (float)(Math.cos(time) * 0.2f + 1f);
        }

        public float voidRadius() {
            return voidRadius;
        }

        @Override
        public float totalProgress() {
            return 1;
        }
    }
}
