package viscott.world.block;

import arc.graphics.g2d.Draw;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import viscott.content.PvFactions;
import viscott.world.chips.VoidArea;

public class VoidBlock extends PvBlock{
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

    public class VoidBuilding extends Building implements VoidArea
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

        @Override
        public float totalProgress() {
            return 1;
        }
    }
}
