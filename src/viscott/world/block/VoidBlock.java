package viscott.world.block;

import mindustry.gen.Building;
import viscott.content.PvFactions;
import viscott.world.chips.VoidArea;

public class VoidBlock extends PvBlock{
    public float voidRadius = 1;
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
    }

    public class VoidBuilding extends Building implements VoidArea
    {
        @Override
        public void updateTile()
        {
            super.updateTile();
            updateVoid(this,8*voidRadius);
        }

        @Override
        public void draw()
        {
            super.draw();
            drawVoid(this,8*voidRadius);
        }
    }
}
