package viscott.world.block.effect;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.world.consumers.Consume;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.modules.ItemModule;
import viscott.content.PvStatusEffects;
import viscott.world.chips.VoidArea;

public class VoidExpand extends VoidBlock{
    public float voidGrowAmount = 1;
    public float itemDuration = 300;
    public VoidExpand(String name) {
        super(name);
        canOverdrive = false;
    }

    public class VoidExpandBuild extends VoidBuilding
    {
        public float consumeTime = 0;
        float buildVoidRad = voidRadius;
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
            float flowRate = 1;
            for(Item i : Vars.content.items().list())
                if (itemConsume.get(i) > 0)
                    flowRate *= items.get(i) / itemConsume.get(i);
            consumeTime += delta() * flowRate;
            if (consumeTime > itemDuration) {
                consumeTime %= itemDuration;
                consume();
            }
            buildVoidRad = Mathf.lerp(buildVoidRad,voidRadius+items.total()*voidGrowAmount,0.02f);
            updateVoid(this,8*buildVoidRad);
            if (items.total() > itemCapacity/2)
                updateVoid(this,8*buildVoidRad, PvStatusEffects.voidDecayExpand,null);
        }

        @Override
        public void draw()
        {
            Draw.z(Layer.blockOver-2);
            drawer.draw(this);
            drawVoid(this,8*buildVoidRad);
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
