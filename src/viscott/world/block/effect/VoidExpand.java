package viscott.world.block.effect;

import arc.graphics.g2d.Draw;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.world.consumers.Consume;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.modules.ItemModule;
import viscott.world.chips.VoidArea;

public class VoidExpand extends VoidBlock{
    float voidGrowAmount = 1;
    float itemDuration = 300;
    public VoidExpand(String name) {
        super(name);
    }

    public class VoidExpandBuild extends VoidBuilding
    {
        float consumeTime = 0;
        Seq<Consume> consumes = Seq.with(consumers);
        ItemModule itemConsume = new ItemModule();
        {
            consumes.each(c -> {
                if(c instanceof ConsumeItems ic)
                    for(ItemStack t : ic.items)
                        itemConsume.add(t.item,t.amount);
            });
        }
        @Override
        public void updateTile()
        {
            super.updateTile();
            updateVoid(this,8*voidRadius);
            consumeTime += delta();
        }

        @Override
        public void draw()
        {
            Draw.z(Layer.blockOver-2);
            drawer.draw(this);
            drawVoid(this,8*voidRadius);
        }

        @Override
        public float warmup() {
            time += delta() / 60;
            time %= 180;
            return (float)(Math.cos(time) * 0.2f + 1f);
        }

        @Override
        public float totalProgress() {
            return 1;
        }
    }
}
