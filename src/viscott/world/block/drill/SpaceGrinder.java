package viscott.world.block.drill;

import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.content.Blocks;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.consumers.Consume;

import java.util.concurrent.atomic.AtomicInteger;

import static mindustry.Vars.world;

public class SpaceGrinder extends Grinder{
    public ItemStack outputItem = new ItemStack();
    public SpaceGrinder(String name) {
        super(name);
    }

    public interface intCons
    {
        int get(Seq<Pos> positions,Integer x,Integer y,Float tier);
    }
    public interface ScanRectEmpty
    {
        intCons getFreeSpace = (positions,x,y,tier) -> {
            AtomicInteger c = new AtomicInteger();
            positions.each(pos -> {
                int aX = x + pos.x,
                        aY = y + pos.y;
                if (aX >= 0 && aX < world.width() && aY >= 0 && aY < world.height() )
                    if (!world.tile(aX,aY).block().solid)
                        c.getAndIncrement();
            });
            return c.get();
        };
    }

    @Override
    public float getMineSpeed(int x,int y)
    {
        int spaceCount = ScanRectEmpty.getFreeSpace.get(checkPattern,x,y,tier);
        return spaceCount * speedPerOre * 60;
    }

    @Override
    public float getHardness(int x,int y)
    {
        return 1;
    }

    public class SpaceGrinderBuild extends GrinderBuild {
        @Override
        public void updateTile()
        {
            maxMineSpeed = getMineSpeed(tileX(),tileY());
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
                progress = Mathf.approachDelta(progress, 1, (maxMineSpeed / 60)*disEff);
                if (progress >= 1) {
                    craft();
                    if (updateEffect != null)
                        updateEffect.at(x, y, 0);
                    progress %= 1;
                }
            }
            dump();
        }


        public void craft()
        {
            items.add(outputItem.item,Math.min(itemCapacity - items.get(outputItem.item) ,outputItem.amount));
        }
    }
}
