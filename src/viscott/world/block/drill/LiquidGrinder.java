package viscott.world.block.drill;

import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValues;
import viscott.content.PvStats;
import viscott.world.block.environment.DepositWall;

public class LiquidGrinder extends Grinder{
    public LiquidStack extractedLiquid;
    public LiquidGrinder(String name)
    {
        super(name);
        hasItems = false;
        hasLiquids = true;
        liquidCapacity = 25;
        outputsLiquid = true;

    }
    @Override
    public void setStats()
    {
        super.setStats();
        stats.add(Stat.output, StatValues.liquid(extractedLiquid.liquid,  extractedLiquid.amount , false));
    }

    public class LiquidGrinderBuild extends GrinderBuild
    {
        @Override
        public void update()
        {
            super.update();
            dumpLiquid(extractedLiquid.liquid);
        }
        @Override
        public void craft(Block d)
        {
            if (liquids.currentAmount() < liquidCapacity)
                liquids.add(extractedLiquid.liquid,extractedLiquid.amount);
        }
    }
}
