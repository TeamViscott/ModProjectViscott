package viscott.world.block.distribution;

import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.world.Block;
import viscott.content.PvBlocks;
import viscott.world.block.PvBlock;

public class UnitPackerBlock extends PvBlock {
    public float range = 8;
    public UnitPackerBlock(String name)
    {
        super(name);
        hasItems = true;
        acceptsItems = true;
        itemCapacity = 10;
        update = true;
    }
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x,y,rotation,valid);
        Drawf.dashCircle(x*8,y*8,range,Pal.lighterOrange);
    }
    @Override
    public void init()
    {
        updateClipRadius(range);
        super.init();
    }

    public class UnitPackerBuild extends Building
    {

        @Override
        public boolean acceptItem(Building source, Item item)
        {
            return items.total() <= 0 || (items.first() != null && items.get(items.first()) < itemCapacity);
        }
        @Override
        public void updateTile()
        {
            Groups.unit.forEach(u -> {
                if (u.team == team() && Mathf.len(x-u.x,y-u.y) <= range)
                    if(items.total() > 0 && (!u.hasItem() || u.item() == items.first()) && u.itemCapacity() > u.stack.amount)
                    {
                        u.addItem(items.first(),1);
                        items.remove(items.first(),1);

                    }
            });
        }
        @Override
        public void drawSelect() {
            super.drawSelect();
            Drawf.dashCircle(x,y,range,Pal.lighterOrange);
            Groups.unit.forEach(u -> {
                if (u.team == team() && Mathf.len(x-u.x,y-u.y) <= range) {
                    Drawf.select(u.x,u.y,u.hitSize,Pal.lighterOrange);
                }
            });
        }
    }
}
