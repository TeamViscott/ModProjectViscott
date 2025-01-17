package viscott.world.block.effect;

import arc.Events;
import arc.func.Cons;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.world.consumers.Consume;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.modules.ItemModule;

public class VoidBeacon extends VoidBlock{
    public float voidGrowAmount = 1;
    public float itemDuration = 300;
    public int beaconMin = 3;
    public int warmupTime = 60;
    public Cons<Seq<Building>> beaconTask = (b) -> {};
    public static ObjectMap<Team,Cons<Seq<Building>>> runtask = new ObjectMap<>();


    private Seq<Team> emptyRunTask = new Seq<Team>();
    /*
     * Rework:
     * VoidBeacons now require a minimum of 3 active beacons before a scripted sequence happend.
     * 1. Every enemy player will be alerted of the Beacons activation.
     * 2. The Beacons will start a 4 min timer to warmup
     * 3. Once warmup is finished, nullis beacons will increase in radius and all nullis structures have the following.
     * 4. Void area, once upgraded now overdrives 50% + 10% for every extra beacon
     * 5. Void area, once upgraded now has a stack effect
     * 6. Void area, once upgraded now heals structures passively that are in its radius
     */
    public VoidBeacon(String name) {
        super(name);
        canOverdrive = false;

        Events.run(EventType.Trigger.update,()->{
            if (Vars.state.isPaused())
                return;
            emptyRunTask.clear();
            runtask.each((team,handler) -> {
                var buildings = team.data().buildings.select(b -> b.team() == team && b.block() == this);
                if (buildings.size == 0) {
                    emptyRunTask.add(team);
                    return;
                }
                handler.get(buildings);
            });
            emptyRunTask.each(t -> runtask.remove(t));

        });
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x,y,rotation,valid);
        Drawf.dashCircle(x*8+offset,y*8+offset,(voidGrowAmount*itemCapacity+voidRadius)*8, Pal.sap);
    }
    public class EffectExpandBuildC extends VoidBuilding
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
            int beacons = team.data().buildings.count(b -> b.block() == this.block() && b.team() == this.team());
            if (beacons < beaconMin) {
                updateVoid(this, 8 * voidRadius);
            } else {
                if (!runtask.containsKey(team()))
                    runtask.put(team(),beaconTask);
                if (consumeTime > itemDuration) {
                    consumeTime %= itemDuration;
                    consume();
                }
                buildVoidRad = Mathf.lerp(buildVoidRad, voidRadius(), 0.02f);
                updateVoid(this, 8 * buildVoidRad);
            }
        }

        @Override
        public void draw()
        {
            Draw.z(Layer.blockOver-2);
            drawer.draw(this);
            drawVoid(this,8*buildVoidRad);
        }

        @Override
        public float voidRadius() {
            return voidRadius + items.total() * voidGrowAmount;
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
