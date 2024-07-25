package viscott.world.block.drill;


import arc.Core;
import arc.func.Cons;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Strings;
import arc.util.io.Reads;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.Tile;
import mindustry.world.consumers.Consume;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValue;
import mindustry.world.meta.StatValues;
import viscott.content.PvStats;
import viscott.world.block.PvBlock;
import viscott.world.block.environment.DepositWall;

import static mindustry.Vars.*;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Grinder extends PvBlock {

    public int range = 1;
    public float speedPerOre = 0.2f;
    public Effect updateEffect = null;
    public float tier = 1;
    public float boostMult = 1;
    public DrawBlock drawer = new DrawDefault();

    protected Seq<Pos> checkPattern = new Seq<>();
    int sizeOffset = 0;
    public Grinder(String name)
    {
        super(name);
        update = true;
        hasItems = true;
        itemCapacity = 10;
        canOverdrive = true;
        drawDisabled = false;
    }

    public class Pos
    {
        public int x = 0,
                y = 0;
    }

    @Override
    public void init()
    {
        super.init();
        if (optionalConsumers.length == 0)
            boostMult = 0;
        drawer.load(this);
        sizeOffset = size/2;
        int blockRange = range*2 + size;
        int ind = 0;
        for(int iy = 0;iy < blockRange;iy++)
        {
            int piy = iy - range;
            for(int ix = 0;ix < blockRange;ix++)
            {
                int pix = ix - range;
                if ((pix < 0 || pix >= size) || (piy < 0 || piy >= size)) {
                    checkPattern.add(new Pos());
                    checkPattern.get(ind).x = pix;
                    checkPattern.get(ind).y = piy;
                    ind++;
                }
            }
        }
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        return true;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x,y,rotation,valid);
        if (itemCapacity != 0) {
            int fix = (size % 2) * 4 + Mathf.floor((size - 1) / 2) * 8;
            Drawf.dashRect(Pal.lighterOrange, x * 8 - offset - range * 8 - fix, y * 8 - offset - range * 8 - fix, size * 8 + range * 16, size * 8 + range * 16);
            float width = drawPlaceText(Core.bundle.format("bar.grindspeed", Strings.fixed(getMineSpeed(x, y) * getHardness(x, y), 2)), x, y + size / 2, true);
        }
    }

    public interface RectCons
    {
        Seq<Block> get(Seq<Pos> positions,Integer x,Integer y,Float tier);
    }
    public interface ScanRect
    {
        RectCons getMinableBlocks = (positions,x,y,tier) -> {
            Seq<Block> newBlockList = new Seq<>();
            positions.each(pos -> {
                int aX = x - pos.x,
                        aY = y - pos.y;
                if (aX >= 0 && aX < world.width() && aY >= 0 && aY < world.height() )
                    if (world.tile(aX,aY).block() instanceof DepositWall d && d.tier <= tier)
                        newBlockList.add(d);
            });
            return newBlockList;
        };
    }
    public float getMineSpeed(int x,int y)
    {
        Seq<Block> newBlockList = ScanRect.getMinableBlocks.get(checkPattern,x+sizeOffset,y+sizeOffset,tier);
        return newBlockList.size * speedPerOre * 60;
    }
    public float getHardness(int x,int y)
    {
        Seq<Block> newBlockList = ScanRect.getMinableBlocks.get(checkPattern,x+sizeOffset,y+sizeOffset,tier);
        float avgHardness = 0;
        float div = 0;
        for (Block b : newBlockList)
            if(b instanceof DepositWall d) {
                if (++div == 1)
                    avgHardness = d.tier;
                else
                    avgHardness = (avgHardness * ((div-1) / div) + d.tier * (1 / div));
            }
        return tier/avgHardness;
    }
    public Seq<Block> getBlocks(int x,int y)
    {
        return ScanRect.getMinableBlocks.get(checkPattern,x+sizeOffset,y+sizeOffset,tier);
    }
    @Override
    public void setStats()
    {
        super.setStats();
        if (boostMult > 0)
            stats.addPercent(Stat.boostEffect,boostMult);
        stats.add(PvStats.grinderTier,StatValues.blocks(b -> b instanceof DepositWall d && d.tier <= tier));
    }
    @Override
    public void setBars(){
        super.setBars();
        if (itemCapacity != 0)
        addBar("grindspeed", (GrinderBuild e) ->
                new Bar(() -> Core.bundle.format("bar.grindspeed", Strings.fixed((e.maxMineSpeed * getHardness((int)e.x/8+sizeOffset,(int)e.y/8+sizeOffset)) * e.timeScale() * e.disEff, 2)), () -> Pal.lighterOrange, () -> e.progress));
    }

    public class GrinderBuild extends Building
    {
        float maxMineSpeed;

        @Override
        public void created()
        {
            super.created();
            updateProximity();
        }
        float hardness = 0;
        float disEff = efficiency;
        float progress;

        @Override
        public void drawSelect() {
            super.drawSelect();
            int fix = 4 + Mathf.floor((size-1)/2)*8;
            Drawf.dashRect(Pal.lighterOrange,x-offset-range*8-fix,y-offset-range*8-fix,size * 8 + range * 16,size * 8 + range * 16);
        }
        @Override
        public void updateProximity() {
            super.updateProximity();
            maxMineSpeed = getMineSpeed((int)x/8,(int)y/8);
            hardness = getHardness((int)x/8,(int)y/8);
        }
        @Override
        public void draw() {
            drawer.draw(this);
        }

        @Override
        public boolean enabled()
        {
            return super.enabled();
        }
        @Override
        public void update()
        {
            super.update();
            if (hardness == -1)
                updateProximity();
            if (efficiency > 0) {
                float percent = 0;
                if (optionalConsumers.length > 0) {
                    for (Consume c : optionalConsumers)
                        percent += c.efficiency(this);
                    percent /= optionalConsumers.length;
                }
                disEff = (efficiency+boostMult*percent);
                progress = Mathf.approachDelta(progress, 1, ((maxMineSpeed * hardness) / 60)*disEff);
                if (progress >= 1) {
                    Seq<Block> blockList = getBlocks((int)x/8,(int)y/8);
                    blockList.each(b -> craft(b));
                    if (updateEffect != null)
                        updateEffect.at(x, y, 0);
                    progress %= 1;
                }
            }
            dump();
        }
        public void craft(Block d)
        {
            if (hasItems && items.get(d.itemDrop) < itemCapacity) {
                items.add(d.itemDrop, 1);
                if (items.get(d.itemDrop) == itemCapacity) //lul
                    dump();
            }
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            maxMineSpeed = -1;
            hardness = -1 ;
        }
    }
}
