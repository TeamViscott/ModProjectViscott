package viscott.world.block.drill;


import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.util.Strings;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.StatValue;
import mindustry.world.meta.StatValues;
import viscott.content.PvStats;
import viscott.world.block.PvBlock;
import viscott.world.block.environment.DepositWall;

import static mindustry.Vars.*;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

public class Grinder extends PvBlock {

    public int range = 1;
    public float speedPerOre = 0.2f;
    public float maxProgress = 100;
    public Effect updateEffect = null;
    public float tier = 1;
    public Grinder(String name)
    {
        super(name);
        update = true;
        hasItems = true;
        itemCapacity = 10;
        canOverdrive = true;
        drawDisabled = false;
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        return true;
    }


    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x,y,rotation,valid);
        int fix = (size % 2) * 4 + Mathf.floor((size-1)/2)*8;
        Drawf.dashRect(Pal.lighterOrange,x*8-offset-range*8-fix,y*8-offset-range*8-fix,size * 8 + range * 16,size * 8 + range * 16);
        float width = drawPlaceText(Core.bundle.format("bar.grindspeed", Strings.fixed(getMineSpeed(x,y)-getHardness(x,y), 2)),x,y+size/2,true);
    }
    public float getMineSpeed(float x,float y)
    {
        int ix = ((int)x)-(int)Math.floor((size-1)/2)-range,
                iy = ((int)y)-(int)Math.floor((size-1)/2)-range,
                rangeSize = size + range * 2;
        List<Block> newBlockList = new Stack<>();
        for (int i1 = 0;i1<rangeSize;i1++)
            for (int i2 = 0;i2<rangeSize;i2++)
                if (world.tiles.get(ix+i1,iy+i2) != null)
                    if (world.tiles.get(ix+i1,iy+i2).block() instanceof DepositWall d)
                        if (d.tier <= tier)
                            newBlockList.add(d);
        return newBlockList.size() * speedPerOre * 60 / 100;
    }
    public float getHardness(float x,float y)
    {
        int ix = ((int)x)-(int)Math.floor((size-1)/2)-range,
                iy = ((int)y)-(int)Math.floor((size-1)/2)-range,
                rangeSize = size + range * 2;
        float newBlockList = 0;
        for (int i1 = 0;i1<rangeSize;i1++)
            for (int i2 = 0;i2<rangeSize;i2++)
                if (world.tiles.get(ix+i1,iy+i2) != null)
                    if (world.tiles.get(ix+i1,iy+i2).block() instanceof DepositWall d)
                        if (d.tier <= tier)
                            newBlockList += d.hardness;
        return newBlockList;
    }
    @Override
    public void setStats()
    {
        super.setStats();
        stats.add(PvStats.grinderTier,StatValues.blocks(b -> b instanceof DepositWall d && d.tier <= tier));
    }
    @Override
    public void setBars(){
        super.setBars();

        addBar("grindspeed", (GrinderBuild e) ->
                new Bar(() -> Core.bundle.format("bar.grindspeed", Strings.fixed((e.maxMineSpeed - getHardness(e.x/8,e.y/8)) * e.timeScale(), 2)), () -> Pal.lighterOrange, () -> e.progress));
    }

    public class GrinderBuild extends Building
    {
        public List<Block> mineable;
        float maxMineSpeed;

        @Override
        public void created()
        {
            super.created();
            mineable = visibleBlocks();
            List<Block> newMineable = new Stack<>();
            for(Block m : mineable)
                if (m instanceof DepositWall d)
                    if (d.tier <= tier)
                        newMineable.add(m);
            mineable = newMineable;
            maxMineSpeed = mineable.size() * speedPerOre * 60 / 100;
            hardness = getHardness(x/8,y/8);
        }

        public List<Block> visibleBlocks()
        {
            int ix = ((int)x/8)-(int)Math.floor((size-1)/2)-range,
                    iy = ((int)y/8)-(int)Math.floor((size-1)/2)-range,
                    rangeSize = size + range * 2;
            List<Block> newBlockList = new Stack<>();
            for (int i1 = 0;i1<rangeSize;i1++)
                for (int i2 = 0;i2<rangeSize;i2++)
                    if (world.tiles.get(ix+i1,iy+i2) != null)
                        newBlockList.add(world.tiles.get(ix+i1,iy+i2).block());
            return newBlockList;
        }
        float hardness = 0;
        float progress;

        @Override
        public void drawSelect() {
            super.drawSelect();
            int fix = 4 + Mathf.floor((size-1)/2)*8;
            Drawf.dashRect(Pal.lighterOrange,x-offset-range*8-fix,y-offset-range*8-fix,size * 8 + range * 16,size * 8 + range * 16);
        }

        @Override
        public boolean enabled()
        {
            return super.enabled();
        }
        @Override
        public void updateTile()
        {
            if (efficiency > 0) {
                progress = Mathf.approachDelta(progress, 1, ((maxMineSpeed - hardness) / 60)*efficiency);
                if (progress == 1) {
                    mineable.forEach(a ->
                    {
                        if (items.get(a.itemDrop) < itemCapacity)
                            items.add(a.itemDrop, 1);
                    });
                    if (updateEffect != null)
                        updateEffect.at(x, y, 0);
                    progress = 0;
                }
            }
            dump();
        }
    }
}
