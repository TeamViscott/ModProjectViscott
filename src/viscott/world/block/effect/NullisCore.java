package viscott.world.block.effect;

import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.UnitType;
import mindustry.world.modules.ItemModule;
import viscott.content.PvEffects;
import viscott.content.PvFactions;
import viscott.world.chips.EffectAreaC;

public class NullisCore extends PvCore {
    public float voidRadius = 5;
    public UnitType defaultUnit = null;
    public int defaultUnits = 1;
    public NullisCore(String name)
    {
        super(name);
        faction.add(PvFactions.Nullis);
    }

    @Override
    public void init()
    {
        updateClipRadius(voidRadius*8);
        super.init();
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x,y,rotation,valid);
        Drawf.dashCircle(x*8+offset,y*8+offset,voidRadius*8, Pal.lighterOrange);
    }
    public class NullisCoreBlock extends PvCoreBuild implements EffectAreaC {
        public float pulsing = 0;
        Seq<Unit> visualizer = new Seq<>();
        ItemModule visualItems = new ItemModule();
        public Seq<Unit> shadowMiner = new Seq<>();
        Seq<Integer> savedIds = new Seq<>();
        @Override
        public void updateTile()
        {
            if (team.core() == this && !Vars.net.client()) {
                Vars.content.items().each(i -> {
                    int amount = visualItems.get(i);
                    if (amount > 0)
                        items.remove(i, amount);
                }); //Removes the Visual Component again.
                visualItems.clear();
            }
            pulsing = Mathf.approachDelta(pulsing,1,0.01f);
            pulsing%=1;
            super.updateTile();
            visualizer.clear();
            Groups.unit.each(unit ->
                    {
                        if (unit.team == team) {
                            visualizer.add(unit);
                            if (unit.stack != null && unit.stack.amount > 0)
                                transferItems(unit);
                        }
                    }
            );
            if (team.core() == this && !Vars.net.client()) {
                for (Unit u : visualizer)
                    if (!u.isValid())
                        visualizer.remove(u);
                    else {
                        items.add(u.stack.item, u.stack.amount);
                        visualItems.add(u.stack.item, u.stack.amount);
                    }
            }
            updateVoid(this, 8 * voidRadius);
            if (defaultUnit == null) return;
            for(Unit u : shadowMiner.list())
                if (!u.isValid())
                    shadowMiner.remove(u);
            if (savedIds.size > 0) {
                savedIds.each(uid -> {
                    Unit u = Groups.unit.find(r -> r.id == uid);
                    if (u == null) return;
                    shadowMiner.add(u);
                });
                savedIds.clear();
            }
            if (shadowMiner.size < defaultUnits)
                if (!Vars.net.client()) {
                    shadowMiner.add(defaultUnit.spawn(team(), x(), y()));
                    PvEffects.nullisDeath.get(size - 1).at(x(), y());
                }
        }

        @Override
        public void remove() {
            shadowMiner.each(u -> u.spawnedByCore = true);
            super.remove();
        }

        @Override
        public int getMaximumAccepted(Item item){
            return storageCapacity;
        }

        public void transferItems(Unit unit)
        {
            if (items.get(unit.stack.item) < storageCapacity) {
                handleItem(this,unit.item());
                unit.stack.amount-=1;
            }
        }

        @Override
        public void draw()
        {
            super.draw();
            drawVoid(this,8*voidRadius);
        }
        @Override
        public void requestSpawn(Player player)
        {
            super.requestSpawn(player);
            PvEffects.nullisDeath.get(size-1).at(player);
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.s(shadowMiner.size);
            shadowMiner.each(s -> write.i(s.id));
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            int size = read.s();
            for(;size > 0;size--)
                savedIds.add(read.i());
        }
    }
}
