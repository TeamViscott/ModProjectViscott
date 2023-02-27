package viscott.world.block.production;

import arc.struct.Seq;
import mindustry.ctype.UnlockableContent;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.consumers.ConsumeLiquid;

public class MultiCrafter extends GenericCrafter {
    Seq<Seq<ItemStack>> consumerItems = new Seq<>();
    Seq<Seq<LiquidStack>> consumerLiquids = new Seq<>();

    Seq<Seq<ItemStack>> outputItems = new Seq<>();
    Seq<Seq<LiquidStack>> outputLiquids = new Seq<>();
    Seq<UnlockableContent> icons = new Seq<>();
    public MultiCrafter(String name)
    {
        super(name);
    }

    public void newConsumer(UnlockableContent icon)
    {
        consumerItems.add(new Seq<ItemStack>());
        consumerLiquids.add(new Seq<LiquidStack>());
        outputItems.add(new Seq<ItemStack>());
        outputLiquids.add(new Seq<LiquidStack>());
        icons.add(icon);
        configurable = true;
        config(Integer.class,(MultiCrafterBuild build,Integer i) -> {
            
        });
    }
    @Override
    public ConsumeLiquid consumeLiquid(Liquid liquid, float amount)
    {
        LiquidStack ls = new LiquidStack(liquid, amount);
        consumerLiquids.get(consumerLiquids.size - 1).add(ls);
        return null;
    }

    public void outputLiquid(Liquid liquid, float amount)
    {
        LiquidStack ls = new LiquidStack(liquid, amount);
        outputLiquids.get(outputLiquids.size - 1).add(ls);
    }

    public void outputItem(Item item, int amount)
    {
        ItemStack is = new ItemStack(item,amount);
        outputItems.get(outputItems.size - 1).add(is);
    }

    @Override
    public ConsumeItems consumeItem(Item item, int amount)
    {
        ItemStack is = new ItemStack(item, amount);
        consumerItems.get(consumerItems.size - 1).add(is);
        return null;
    }

    public class MultiCrafterBuild extends GenericCrafterBuild
    {
        
    }
}
