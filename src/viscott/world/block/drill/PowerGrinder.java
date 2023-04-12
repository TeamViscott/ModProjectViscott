package viscott.world.block.drill;

import arc.Core;
import arc.math.Mathf;
import arc.util.Strings;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Block;
import viscott.content.PvAttributes;
import viscott.world.block.environment.DepositWall;

import java.util.List;
import java.util.Stack;

import static mindustry.Vars.world;

public class PowerGrinder extends Grinder{
    public float powerProduction = 1f;
    public PowerGrinder(String name)
    {
        super(name);
        hasPower = true;
        outputsPower = true;
        consumesPower = false;
        hasItems = false;
        itemCapacity = 0;
    }

    public float getMineable(float x,float y)
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
        return newBlockList.size();
    }

    public float getPower(float x,float y)
    {
        int ix = ((int)x)-(int)Math.floor((size-1)/2)-range,
                iy = ((int)y)-(int)Math.floor((size-1)/2)-range,
                rangeSize = size + range * 2;
        float powerAddition = 0f;
        for (int i1 = 0;i1<rangeSize;i1++)
            for (int i2 = 0;i2<rangeSize;i2++)
                if (world.tiles.get(ix+i1,iy+i2) != null)
                    if (world.tiles.get(ix+i1,iy+i2).block() instanceof DepositWall d)
                        if (d.tier <= tier)
                            powerAddition += d.attributes.get(PvAttributes.power) + 1f;
        return powerAddition;
    }

    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x,y,rotation,valid);
        int fix = (size % 2) * 4 + Mathf.floor((size-1)/2)*8;
        Drawf.dashRect(Pal.lighterOrange,x*8-offset-range*8-fix,y*8-offset-range*8-fix,size * 8 + range * 16,size * 8 + range * 16);
        float width = drawPlaceText(Core.bundle.format("bar.poweroutput",  Strings.fixed(getPower(x,y) * powerProduction * 60 , 1) + "/s"),x,y+size/2,true);
    }

    @Override
    public void setBars(){
        super.setBars();

        if(hasPower && outputsPower){
            addBar("power", (PowerGrinderBuild entity) -> new Bar(() ->
                    Core.bundle.format("bar.poweroutput",
                            Strings.fixed(entity.getPowerProduction() * 60 * entity.timeScale(), 1)),
                    () -> Pal.powerBar,
                    () -> entity.productionEfficiency));
        }
    }

    public class PowerGrinderBuild extends GrinderBuild
    {
        float productionEfficiency = 0;

        @Override
        public float warmup(){
            return ScanRect.getMinableBlocks.get(checkPattern,(int)x/8+sizeOffset,(int)x/8+sizeOffset,tier).size;
        }

        @Override
        public void updateTile()
        {
            productionEfficiency = Mathf.approachDelta(productionEfficiency,getPower(x/8,y/8),0.1f);
        }

        @Override
        public float getPowerProduction(){
            return powerProduction * productionEfficiency;
        }
    }
}
