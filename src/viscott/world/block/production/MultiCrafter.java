package viscott.world.block.production;

import arc.scene.ui.layout.Table;
import arc.struct.EnumSet;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.consumers.ConsumeItemDynamic;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.DrawDefault;
import mindustry.world.meta.BlockFlag;

import java.util.concurrent.atomic.AtomicInteger;

public class MultiCrafter extends GenericCrafter {
    Seq<Seq<ItemStack>> consumerItems = new Seq<>();
    Seq<Seq<LiquidStack>> consumerLiquids = new Seq<>();

    Seq<Seq<ItemStack>> outputItems = new Seq<>();
    Seq<Seq<LiquidStack>> outputLiquids = new Seq<>();
    Seq<UnlockableContent> icons = new Seq<>();
    public MultiCrafter(String name)
    {
        super(name);
        this.craftEffect = Fx.none;
        this.updateEffect = Fx.none;
        this.updateEffectChance = 0.04F;
        this.warmupSpeed = 0.019F;
        this.legacyReadWarmup = false;
        this.drawer = new DrawDefault();
        this.update = true;
        this.solid = true;
        this.hasItems = true;
        this.ambientSound = Sounds.machine;
        this.sync = true;
        this.ambientSoundVolume = 0.03F;
        this.flags = EnumSet.of(new BlockFlag[]{BlockFlag.factory});
        this.drawArrow = false;
        configurable = true;
        clearOnDoubleTap = true;
        config(Integer.class,(MultiCrafterBuild build,Integer i) -> {
            build.selected = i;
        });
        configClear((MultiCrafterBuild build) -> {
            build.selected = -1;
        });
        consume(new ConsumeItemDynamic(
                // items seq is already shrunk, it's safe to access
                (MultiCrafterBuild b) -> {
                    if (b.selected == -1) return ItemStack.empty;
                    ItemStack[] is = new ItemStack[consumerItems.get(b.selected).size];
                    AtomicInteger i = new AtomicInteger();
                    consumerItems.get(b.selected).forEach(item -> {is[i.getAndIncrement()] = item;});
                    return is;
                })
        );
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
        int selected = -1;

        @Override
        public void updateTile() {
            if (selected > 0)
                dump();
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            if (selected == -1) return false;
            return consumerItems.get(selected).filter(i -> i.item == item).size > 0 && this.items.get(item) < itemCapacity;
        }
        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);
            ItemSelection.buildTable(MultiCrafter.this, table, icons, () -> selected == -1 ? null : icons.get(selected), icon -> configure(icons.indexOf(icon)), selectionRows, selectionColumns);
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.i(selected);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            this.selected = read.i();
        }
    }
}
