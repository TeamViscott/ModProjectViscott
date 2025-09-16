package viscott.world.block.drill;

import arc.Core;
import arc.func.Cons2;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.scene.ui.layout.Table;
import arc.struct.EnumSet;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Liquids;
import mindustry.entities.Effect;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.type.*;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.Attributes;
import mindustry.world.meta.*;

import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

public class WallAttributeCrafter extends Block {

    public TextureRegion topRegion;
    public TextureRegion rotatorBottomRegion;
    public TextureRegion rotatorRegion;

    public Effect updateEffect;
    public float updateEffectChance;
    public float rotateSpeed;

    public Seq<AttributeModule> craftTable;

    public static class AttributeModule {
        public Attribute attribute;
        public Seq<ItemStack> outputItems;
        public LiquidStack outputLiquid;
        public float drillTime;

        public AttributeModule() {
            this.attribute = Attribute.sand;
            this.outputItems = new Seq<>();
            this.outputLiquid = new LiquidStack(Liquids.water,0);
            this.drillTime = 60;
        }
        public AttributeModule(Attribute attribute, Seq<ItemStack> outputItems,LiquidStack outputLiquid,float drillTime) {
            this.attribute = attribute;
            this.outputItems = outputItems;
            this.outputLiquid = outputLiquid;
            this.drillTime = drillTime;
        }

        public void setStats(Block block,Table table) {
            table.background(Styles.black3);
            // table.add("[stat]" + attribute.name + "[]").left().padTop(10);
            var baseTable = table.table().growX().padTop(12).get();
            baseTable.add(attribute.name.substring(0,1).toUpperCase() + attribute.name.substring(1)).left();
            baseTable.row();
            var tileTable = baseTable.table().growX().left().get();
            tileTable.left();
            tileTable.add(" [stat]Tiles :[]").left();
            var statValues = StatValues.blocks(this.attribute, block.floating, 1.0F, true, false);
            var statTable = tileTable.table().growX().left().get();
            statValues.display(statTable);
            statTable.left();
            baseTable.row();
            baseTable.add("Drillspeed : " + Strings.fixed(60/drillTime,2) + "/s").left();
            baseTable.row();
            baseTable.add("Outputs :").left();
            table.row();
            for (var itemStack : outputItems) {
                var itemTable = table.table().left().growX().padLeft(18).left().get();
                itemTable.background(Styles.grayPanel).left();
                itemTable.image(itemStack.item.fullIcon).left().tooltip( Strings.fixed(itemStack.amount * (60f/drillTime),2) + " " + itemStack.item.localizedName + "/s");
                itemTable.add(Strings.fixed(itemStack.amount * (60f/drillTime),2) + "/s").left();
                table.row();
            }
            if (!outputItems.any())
                table.row();

            if (outputLiquid.amount > 0) {
                var liquidTable = table.table().left().growX().padLeft(18).left().get();
                liquidTable.background(Styles.grayPanel).left();
                liquidTable.image(outputLiquid.liquid.fullIcon).left().tooltip(Strings.fixed(outputLiquid.amount * (60/drillTime),2) + " " + outputLiquid.liquid.localizedName + "/s");
                liquidTable.add(Strings.fixed(outputLiquid.amount * (60/drillTime),2) + "/s").left();
                table.row();
            }
        }

        public void craft(WallAttributeCrafterBuild building) {
            if (outputLiquid.amount > 0 && building.block.hasLiquids && building.liquids.currentAmount() < building.block.liquidCapacity) {
                building.liquids.add(outputLiquid.liquid,outputLiquid.amount);
            }
            if (outputItems.any() && building.block.hasItems && building.items.total() < building.block.itemCapacity) {
                for (var itemStack : outputItems) {
                    building.items.add(itemStack.item,itemStack.amount);
                }
            }
        }
    }

    public @Nullable AttributeModule fromAttribute(Attribute attribute) {
        return craftTable.find((a) -> a.attribute == attribute);
    }

    public @Nullable Seq<AttributeModule> fromAttribute(Attributes attributes) {
        return craftTable.select((a) -> attributes.get(a.attribute) > 0);
    }

    public WallAttributeCrafter(String name) {
        super(name);
        this.updateEffect = Fx.mineWallSmall;
        this.updateEffectChance = 0.02F;
        this.rotateSpeed = 2.0F;
        this.craftTable = new Seq<>();
        this.hasItems = true;
        this.rotate = true;
        this.update = true;
        this.solid = true;
        this.regionRotated1 = 1;
        this.envEnabled |= 2;
        this.flags = EnumSet.of(new BlockFlag[]{BlockFlag.drill});
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.output,(table) -> {
            var objTable = table.table().get();
            objTable.background(Styles.grayPanel);
            for (var craftEntry : craftTable) {
                var craftTable = objTable.table().left().width(300).get();
                craftEntry.setStats(this,craftTable);
                objTable.row();
            }
        });
    }

    public void addCraftingAttribute(Attribute attribute, Liquid liquid, float drillTime) {
        this.addCraftingAttribute(attribute,new Seq<>(),new LiquidStack(liquid,1f/60f),drillTime);
    }

    public void addCraftingAttribute(Attribute attribute, LiquidStack liquidStack,float drillTime) {
        this.addCraftingAttribute(attribute,new Seq<>(),liquidStack,drillTime);
    }

    public void addCraftingAttribute(Attribute attribute, Item outputItem,float drillTime) {
        this.addCraftingAttribute(attribute,Seq.with(new ItemStack(outputItem,1)),new LiquidStack(Liquids.water,0),drillTime);
    }

    public void addCraftingAttribute(Attribute attribute, ItemStack[] outputItems,float drillTime) {
        this.addCraftingAttribute(attribute,Seq.with(outputItems),new LiquidStack(Liquids.water,0),drillTime);
    }

    public void addCraftingAttribute(Attribute attribute, ItemStack outputItems,float drillTime) {
        this.addCraftingAttribute(attribute,Seq.with(outputItems),new LiquidStack(Liquids.water,0),drillTime);
    }

    public void addCraftingAttribute(Attribute attribute, Seq<ItemStack> outputItems,float drillTime) {
        this.addCraftingAttribute(attribute,outputItems,new LiquidStack(Liquids.water,0),drillTime);
    }

    public void addCraftingAttribute(Attribute attribute, ItemStack[] outputItems, LiquidStack liquidStack, float drillTime) {
        this.addCraftingAttribute(attribute,Seq.with(outputItems),liquidStack,drillTime);
    }


    public void addCraftingAttribute(Attribute attribute, Seq<ItemStack> outputItems,LiquidStack liquidStack,float drillTime) {
        if (fromAttribute(attribute) != null)
            Log.warn("An Attribute Module overwrote another. attribute : " + attribute.name);
        this.craftTable.add(new AttributeModule(attribute,outputItems,liquidStack,drillTime));
    }

    @Override
    public boolean outputsItems() {
        return true;
    }

    @Override
    public boolean rotatedOutput(int x, int y) {
        return false;
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{this.region, this.topRegion};
    }

    @Override
    public void load() {
        super.load();
        topRegion = Core.atlas.find(name + "-top");
        rotatorRegion = Core.atlas.find(name + "-rotator");
        rotatorBottomRegion = Core.atlas.find(name + "-rotator-bottom");
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        Draw.rect(this.region, plan.drawx(), plan.drawy());
        Draw.rect(this.topRegion, plan.drawx(), plan.drawy(), (float)(plan.rotation * 90));
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        var table = getDrillEfficiency(x,y,rotation);
        if (table.isEmpty()) {
            this.drawPlaceText(Core.bundle.formatFloat("bar.drillspeed", 0f, 2), x, y, valid);
        } else {
            var iterator = table.keys();
            var yLoc = y;
            while (iterator.hasMoreElements()) {
                var element = iterator.nextElement();
                var eff = table.get(element);
                var attName = element.attribute.name;
                this.drawPlaceText(   (attName.substring(0,1).toUpperCase() + attName.substring(1)) + " " + Core.bundle.formatFloat("bar.drillspeed", 60 / element.drillTime * eff, 2), x, yLoc++, valid);
            }
        }
    }

    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return !this.getDrillEfficiency(tile.x, tile.y, rotation).isEmpty();
    }

    void getEfficiencyBase(int tx, int ty, int rotation, Cons2<Tile,Float> ctile) {
        int cornerX = tx - (this.size - 1) / 2;
        int cornerY = ty - (this.size - 1) / 2;
        int s = this.size;



        for(int i = 0; i < this.size; ++i) {
            int rx = 0;
            int ry = switch (rotation) {
                case 0 -> {
                    rx = cornerX + s;
                    yield cornerY + i;
                }
                case 1 -> {
                    rx = cornerX + i;
                    yield cornerY + s;
                }
                case 2 -> {
                    rx = cornerX - 1;
                    yield cornerY + i;
                }
                case 3 -> {
                    rx = cornerX + i;
                    yield cornerY - 1;
                }
                default -> 0;
            };

            Tile other = Vars.world.tile(rx, ry);
            if (other != null && other.solid()) {
                var attributeMods = fromAttribute(other.block().attributes);
                float eff = 0;
                for(var attributeMod : attributeMods) {
                    eff += other.block().attributes.get(attributeMod.attribute) / size;
                }

                ctile.get(other,eff);
            }
        }
    }

    Hashtable<AttributeModule, Float> getDrillEfficiency(int tx, int ty, int rotation) {
        Hashtable<AttributeModule, Float> modules = new Hashtable<>();
        int cornerX = tx - (this.size - 1) / 2;
        int cornerY = ty - (this.size - 1) / 2;
        int s = this.size;

        for(int i = 0; i < this.size; ++i) {
            int rx = 0;
            int ry = switch (rotation) {
                case 0 -> {
                    rx = cornerX + s;
                    yield cornerY + i;
                }
                case 1 -> {
                    rx = cornerX + i;
                    yield cornerY + s;
                }
                case 2 -> {
                    rx = cornerX - 1;
                    yield cornerY + i;
                }
                case 3 -> {
                    rx = cornerX + i;
                    yield cornerY - 1;
                }
                default -> 0;
            };

            Tile other = Vars.world.tile(rx, ry);
            if (other != null && other.solid()) {
                var attributeMods = fromAttribute(other.block().attributes);
                for(var attributeMod : attributeMods) {
                    if (!modules.containsKey(attributeMod)) {
                        modules.put(attributeMod,other.block().attributes.get(attributeMod.attribute) / size);
                    } else {
                        modules.replace(attributeMod,modules.get(attributeMod) + other.block().attributes.get(attributeMod.attribute) / size);
                    }
                }
            }
        }

        return modules;
    }

    public class WallAttributeCrafterBuild extends Building {
        public class AttributeElement {
            public AttributeModule attributeModule;
            public float progress = 0;
            public float efficiency = 0;

            public AttributeElement(AttributeModule attributeModule) {
                this.attributeModule = attributeModule;
            }

            public boolean canDeposit() {
                return (hasItems && attributeModule.outputItems.any() && items.total() < itemCapacity)
                        || (hasLiquids && attributeModule.outputLiquid.amount > 0 && liquids.currentAmount() < liquidCapacity);
            }
        }
        float warmup = 0;
        public Seq<AttributeElement> craftElements = new Seq<>();
        public float totalTime;


        @Override
        public void created() {
            super.created();
            updateProximity();
        }

        @Override
        public void updateProximity() {
            super.updateProximity();
            craftElements.clear();
            var table = getDrillEfficiency(tileX(),tileY(),rotation);
            var keyList = table.keys();
            while (keyList.hasMoreElements()) {
                var element = keyList.nextElement();
                var eff = table.get(element);
                craftElements.add(new AttributeElement(element) {{efficiency = eff;}});
            }
        }

        @Override
        public boolean shouldConsume() {
            return craftElements.contains(AttributeElement::canDeposit);
        }

        @Override
        public void updateTile() {
            super.updateTile();
            boolean cons = this.shouldConsume();
            this.warmup = Mathf.approachDelta(this.warmup, (float)Mathf.num(this.efficiency > 0.0F), 0.025F);
            float dx = (float)Geometry.d4x(this.rotation) * 0.5F;
            float dy = (float)Geometry.d4y(this.rotation) * 0.5F;
            getEfficiencyBase(this.tile.x, this.tile.y, this.rotation, (dest,eff) -> {
                if (this.wasVisible && cons && Mathf.chanceDelta((double)(updateEffectChance * this.warmup))) {
                    updateEffect.at(dest.worldx() + Mathf.range(3.0F) - dx * 8.0F, dest.worldy() + Mathf.range(3.0F) - dy * 8.0F, dest.block().mapColor);
                }
            });

            craftElements.each((element) -> {
                if (cons && (element.progress += this.edelta() * element.efficiency) >= element.attributeModule.drillTime) {
                    element.attributeModule.craft(this);

                    element.progress %= element.attributeModule.drillTime;
                }
            });

            this.totalTime += this.edelta() * this.warmup;

            if (hasItems && this.timer(timerDump, 5.0F))
                this.dump();
            if (hasLiquids)
                this.dumpLiquid(liquids.current());
        }

        @Override
        public void draw() {
            Draw.rect(this.block.region, this.x, this.y);
            Draw.rect(topRegion, this.x, this.y, this.rotdeg());
            float ds = 0.6F;
            float dx = (float) Geometry.d4x(this.rotation) * ds;
            float dy = (float)Geometry.d4y(this.rotation) * ds;
            int bs = this.rotation != 0 && this.rotation != 3 ? -1 : 1;
            AtomicInteger idx = new AtomicInteger();
            getEfficiencyBase(this.tile.x, this.tile.y, this.rotation, (tile, eff) -> {
                int cx = tile.x;
                int cy = tile.y;
                int sign = idx.getAndIncrement() >= size / 2 && size % 2 == 0 ? -1 : 1;
                float vx = ((float)cx - dx) * 8.0F;
                float vy = ((float)cy - dy) * 8.0F;
                Draw.z(35.0F);
                Draw.rect(rotatorBottomRegion, vx, vy, this.totalTime * rotateSpeed * (float)sign * (float)bs);
                Draw.rect(rotatorRegion, vx, vy);
            });
        }

    }
}
