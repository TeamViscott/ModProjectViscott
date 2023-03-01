package viscott.world.block.production;

import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.EnumSet;
import arc.struct.Seq;
import arc.util.Time;
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
import mindustry.world.blocks.ItemSelection;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.consumers.ConsumeItemDynamic;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.DrawDefault;
import mindustry.world.meta.BlockFlag;

import java.util.concurrent.atomic.AtomicInteger;

import static mindustry.world.meta.Stat.output;

public class MultiCrafter extends GenericCrafter {
    Seq<Seq<ItemStack>> consumerItems = new Seq<>();
    Seq<Seq<LiquidStack>> consumerLiquids = new Seq<>();

    Seq<Seq<ItemStack>> outputerItems = new Seq<>();
    Seq<Seq<LiquidStack>> outputerLiquids = new Seq<>();
    Seq<UnlockableContent> icons = new Seq<>();
    public MultiCrafter(String name)
    {
        super(name);
        this.craftEffect = Fx.smeltsmoke;
        this.updateEffect = Fx.smoke;
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
        outputerItems.add(new Seq<ItemStack>());
        outputerLiquids.add(new Seq<LiquidStack>());
        icons.add(icon);
        configurable = true;
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
        outputerLiquids.get(outputerLiquids.size - 1).add(ls);
    }

    public void outputItem(Item item, int amount)
    {
        ItemStack is = new ItemStack(item,amount);
        outputerItems.get(outputerItems.size - 1).add(is);
    }

    @Override
    public ConsumeItems consumeItem(Item item, int amount)
    {
        ItemStack is = new ItemStack(item, amount);
        consumerItems.get(consumerItems.size - 1).add(is);
        return null;
    }

    @Override
    public void init()
    {
        if (consumerLiquids.filter(liq -> liq.size != 0).size != 0)
            hasLiquids = true;
        super.init();
    }

    public class MultiCrafterBuild extends GenericCrafterBuild
    {

        int selected = -1;

        @Override
        public void updateTile() {
            dump();
            if (selected == -1) return;

            if (this.efficiency > 0.0F) {
                this.progress += this.getProgressIncrease(MultiCrafter.this.craftTime);
                this.warmup = Mathf.approachDelta(this.warmup, this.warmupTarget(), MultiCrafter.this.warmupSpeed);
                if (MultiCrafter.this.outputerLiquids.get(selected).size != 0) {
                    MultiCrafter.this.outputerLiquids.get(selected).forEach(liquid -> {
                        this.handleLiquid(this, liquid.liquid, Math.min(liquid.amount, MultiCrafter.this.liquidCapacity - this.liquids.get(liquid.liquid)));
                    });
                }

                if (this.wasVisible && Mathf.chanceDelta((double)MultiCrafter.this.updateEffectChance)) {
                    MultiCrafter.this.updateEffect.at(this.x + Mathf.range((float)MultiCrafter.this.size * 4.0F), this.y + (float)Mathf.range(MultiCrafter.this.size * 4));
                }
            } else {
                this.warmup = Mathf.approachDelta(this.warmup, 0.0F, MultiCrafter.this.warmupSpeed);
            }

            this.totalProgress += this.warmup * Time.delta;
            if (this.progress >= 1.0F) {
                this.craft();
            }
        }

        @Override
        public void craft() {
            this.consume();
            if (MultiCrafter.this.outputerItems.get(selected).size != 0) {
                MultiCrafter.this.outputerItems.get(selected).forEach(item ->
                {
                    items.add(item.item,Math.min(item.amount,itemCapacity-items.get(item.item)));
                });
            }

            if (this.wasVisible) {
                MultiCrafter.this.craftEffect.at(this.x, this.y);
            }

            this.progress %= 1.0F;
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            if (selected == -1) return false;
            return this.items.get(item) < itemCapacity && consumerItems.get(selected).find(i -> i.item == item) != null;
        }

        @Override
        public Object config()
        {
            return selected;
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
