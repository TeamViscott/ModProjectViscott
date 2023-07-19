package viscott.world.block.distribution;

import arc.func.Cons;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Button;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.ai.UnitCommand;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.UnitType;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.blocks.ItemSelection;
import viscott.content.PvBlocks;
import viscott.world.block.PvBlock;

public class UnitPackerBlock extends PvBlock {
    public float range = 8;
    public Seq<UnitType> quickCalledUnits = new Seq<>();
    public UnitPackerBlock(String name)
    {
        super(name);
        hasItems = true;
        acceptsItems = true;
        itemCapacity = 10;
        update = true;
        configurable = true;
    }
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x,y,rotation,valid);
        Drawf.dashCircle(x*8,y*8,range,Pal.lighterOrange);
    }
    @Override
    public void init()
    {
        updateClipRadius(range);
        super.init();
    }

    public class UnitPackerBuild extends Building
    {

        @Override
        public boolean acceptItem(Building source, Item item)
        {
            return items.total() <= 0 || (items.first() != null && items.get(items.first()) < itemCapacity);
        }
        @Override
        public void updateTile()
        {
            Groups.unit.each(u -> {
                if (u.team == team() && Mathf.len(x-u.x,y-u.y) <= range)
                    if(items.total() > 0 && (!u.hasItem() || u.item() == items.first()) && u.itemCapacity() > u.stack.amount)
                    {
                        u.addItem(items.first(),1);
                        items.remove(items.first(),1);

                    }
            });
        }
        @Override
        public void buildConfiguration(Table table){
            table.background(Styles.black6);

            for(UnitType u : quickCalledUnits) {
                ImageButton b = table.button(Tex.whiteui, Styles.clearNoneTogglei,40, () -> {
                    Unit c = Groups.unit.find(g -> g.team() == team() && g.type == u && g.isCommandable() && !g.isFlying());
                    if (c == null) return;
                    c.flag(1);
                    c.command().command(UnitCommand.boostCommand);
                    c.command().commandPosition(new Vec2(this.x, this.y));
                }).tooltip(u.localizedName).get();
                b.changed(()->b.setChecked(false));
                b.getStyle().imageUp = new TextureRegionDrawable(u.fullIcon);
            }
        }
        @Override
        public void drawSelect() {
            super.drawSelect();
            Drawf.dashCircle(x,y,range,Pal.lighterOrange);
            Groups.unit.each(u -> {
                if (u.team == team() && Mathf.len(x-u.x,y-u.y) <= range) {
                    Drawf.select(u.x,u.y,u.hitSize,Pal.lighterOrange);
                }
            });
        }
    }
}
