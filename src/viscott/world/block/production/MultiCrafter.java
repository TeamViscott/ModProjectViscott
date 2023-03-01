package viscott.world.block.production;

import arc.math.Interp;
import arc.math.Mathf;
import arc.scene.ui.layout.Cell;
import arc.scene.ui.layout.Table;
import arc.struct.EnumSet;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;
import mindustry.ui.Bar;
import mindustry.ui.ItemImage;
import mindustry.ui.LiquidDisplay;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.consumers.ConsumeItemDynamic;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.DrawDefault;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiCrafter extends GenericCrafter {
    Seq<Seq<ItemStack>> consumerItems = new Seq<>();
    Seq<Seq<LiquidStack>> consumerLiquids = new Seq<>();

    Seq<Seq<ItemStack>> outputerItems = new Seq<>();
    Seq<Seq<LiquidStack>> outputerLiquids = new Seq<>();
    Seq<UnlockableContent> icons = new Seq<>();
    boolean itemNameVisible = false;
    public MultiCrafter(String name)
    {
        super(name);
        this.craftEffect = Fx.smeltsmoke;
        this.updateEffect = Fx.smoke;
        this.updateEffectChance = 0.04F;
        this.craftTime = 60;
        this.warmupSpeed = 0.019F;
        this.legacyReadWarmup = false;
        this.drawer = new DrawDefault();
        this.update = true;
        this.solid = true;
        this.hasItems = true;
        this.ambientSound = Sounds.machine;
        this.outputsLiquid = false;
        this.sync = true;
        this.liquidCapacity = 25;
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

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.output, t -> {
            buildStats(t);
        });
    }

    public void buildStats(Table stat) {
        stat.row();
        int index = 0;
        for (UnlockableContent icon : icons) {
            Table t = new Table();
            t.background(Tex.whiteui);
            t.setColor(Pal.darkestGray);
            // Input
            buildIOEntry(t, index, true);
            // Time
            Table time = new Table();
            if (consumerItems.get(index).size != 0 || outputerItems.get(index).size != 0) {
                {
                    final float[] duration = {0f};
                    float visualCraftTime = craftTime;
                    time.update(() -> {
                        duration[0] += Time.delta;
                        if (duration[0] > visualCraftTime) duration[0] = 0f;
                    });
                    String craftTime = this.craftTime == 0 ? "0" : String.format("%.2f", this.craftTime / 60f);
                    Cell<Bar> barCell = time.add(new Bar(() -> craftTime,
                                    () -> Pal.accent,
                                    () -> Interp.smooth.apply(duration[0] / visualCraftTime)))
                            .height(45f);
                    if (Vars.mobile)
                        barCell.width(220f);
                    else
                        barCell.width(250f);
                }}
            else
            {{
                Cell<Bar> barCell = time.add(new Bar(() -> "=>",
                                () -> Pal.accent,
                                () -> Interp.smooth.apply(0)))
                        .height(0f);
                if (Vars.mobile)
                    barCell.width(220f);
                else
                    barCell.width(250f);
            }}
            Cell<Table> timeCell = t.add(time).pad(12f);
            // Output
            buildIOEntry(t, index, false);
            stat.add(t).pad(10f).grow();
            stat.row();
            index++;
        }
        stat.row();
        stat.defaults().grow();
    }

    protected void buildIOEntry(Table table, int index, boolean isInput) {
        Table t = new Table();
        if (isInput) t.left();
        else t.right();
        Table mat = new Table();
        Seq<ItemStack> entryItems = isInput ? consumerItems.get(index) : outputerItems.get(index);
        Seq<LiquidStack> entryLiquids = isInput ? consumerLiquids.get(index) : outputerLiquids.get(index);
        int i = 0;
        for (ItemStack stack : entryItems) {
            Cell<ItemImage> iconCell = mat.add(new ItemImage(stack.item.uiIcon, stack.amount))
                    .pad(2f);
            if (itemNameVisible) iconCell.tooltip(stack.item.localizedName);
            if (isInput) iconCell.left();
            else iconCell.right();
            mat.row();
            i++;
        }
        i++;
        for (LiquidStack stack : entryLiquids) {
            Cell<LiquidDisplay> iconCell = mat.add(new LiquidDisplay(stack.liquid, stack.amount * 60f,true))
                    .pad(2f);
            if (itemNameVisible) iconCell.tooltip(stack.liquid.localizedName);
            if (isInput) iconCell.left();
            else iconCell.right();
            mat.row();
            i++;
        }
        Cell<Table> matCell = t.add(mat);
        if (isInput) matCell.left();
        else matCell.right();
        t.row();
        // No redundant ui
        Cell<Table> tCell = table.add(t).pad(12f).fill();
        /*if(Vars.mobile)
            tCell.width(100f);
        else*/
        tCell.width(120f);
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
        if (consumerLiquids.filter(liq -> liq.size != 0).size != 0) {
            this.hasLiquids = true;
        }
        super.init();
        if (consumerLiquids.filter(liq -> liq.size != 0).size != 0) {
            this.outputsLiquid = true;
        }

    }

    public class MultiCrafterBuild extends GenericCrafterBuild
    {

        int selected = -1;

        public boolean gotLiquids()
        {
            AtomicBoolean enough = new AtomicBoolean(true);
            consumerLiquids.get(selected).forEach(liquid -> {if (liquids.get(liquid.liquid) < liquid.amount) enough.set(false);});
            return enough.get();
        }

        @Override
        public boolean shouldConsume() {
            return true;
        }
        @Override
        public void updateTile() {
            dump();
            if (selected == -1) return;

            if (this.efficiency > 0.0F && gotLiquids()) {
                this.progress += this.getProgressIncrease(MultiCrafter.this.craftTime);
                this.warmup = Mathf.approachDelta(this.warmup, this.warmupTarget(), MultiCrafter.this.warmupSpeed);
                if (MultiCrafter.this.outputerLiquids.get(selected).size != 0) {
                    MultiCrafter.this.outputerLiquids.get(selected).forEach(liquid -> {
                        liquids.add(liquid.liquid, Math.min(liquid.amount, MultiCrafter.this.liquidCapacity - this.liquids.get(liquid.liquid)));
                        dumpLiquid(liquid.liquid);
                    });
                }
                if (MultiCrafter.this.consumerLiquids.get(selected).size != 0) {
                    MultiCrafter.this.consumerLiquids.get(selected).forEach(liquid -> {
                        liquids.remove(liquid.liquid, liquid.amount);
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
        public boolean acceptLiquid(Building source, Liquid liquid) {
            if (selected == -1) return false;
            return this.liquids.get(liquid) < liquidCapacity && consumerLiquids.get(selected).find(l -> l.liquid == liquid) != null;
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
