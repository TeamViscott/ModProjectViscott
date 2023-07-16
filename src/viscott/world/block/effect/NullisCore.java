package viscott.world.block.effect;

import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.gen.*;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.modules.ItemModule;
import viscott.content.PvEffects;
import viscott.content.PvFactions;
import viscott.world.chips.VoidArea;

import java.util.HashMap;

public class NullisCore extends PvCore {
    public float voidRadius = 5;
    public UnitType defaultMiner = null;
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
    public class NullisCoreBlock extends PvCoreBuild implements VoidArea {
        public float pulsing = 0;
        Seq<Unit> visualizer = new Seq<>();
        ItemModule visualItems = new ItemModule();
        public Unit shadowMiner = null;
        int savedId = -1;
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
            if (defaultMiner == null) return;
            if (shadowMiner == null || !shadowMiner.isValid())
                if (savedId < 0) {
                    if (!Vars.net.client()) {
                        shadowMiner = defaultMiner.spawn(team(), x(), y());
                        PvEffects.nullisDeath.get(size - 1).at(x(), y());
                    }
                }
                else {
                    shadowMiner = Groups.unit.getByID(savedId);
                    savedId = -1;
                }
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
            if (shadowMiner == null || !shadowMiner.isValid())
                write.i(-1);
            else
                write.i(shadowMiner.id());
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            savedId = read.i();
        }
    }
}
