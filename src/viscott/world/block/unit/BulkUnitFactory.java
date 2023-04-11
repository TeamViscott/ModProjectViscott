package viscott.world.block.unit;

import arc.Events;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Structs;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.world.blocks.payloads.UnitPayload;
import mindustry.world.blocks.units.UnitFactory;
import viscott.utilitys.PvUtil;

import java.util.List;

public class BulkUnitFactory extends UnitFactory {
    /*
    So Ethanol wants this Type to have 3 things :
    1. Selection of How many units to spawn
    2. Selection of What unit to spawn
    3. have liquid requirements per unit. not per standers :/
    So ye. im too lazy to do it rn. if anyone wants to work on it feel free to.
     */
    public int maxAmount = 10;

    public class conf
    {
        int amount = 1;
        int currentConfig = -1;
    }

    public BulkUnitFactory(String name)
    {
        super(name);
        clearOnDoubleTap = true;
        config(String.class,(BulkUnitFactoryBuild tile, String i) -> {
            tile.amount = Integer.parseInt(i.split(";")[0]);
            tile.currentPlan = Integer.parseInt(i.split(";")[1]);
        });
        config(Float.class,(BulkUnitFactoryBuild tile, Float i) -> {
            tile.amount = Math.round(i);
        });
        configClear((BulkUnitFactoryBuild tile) -> {tile.amount = 1f;tile.currentPlan = -1;});
    }

    public class BulkUnitFactoryBuild extends UnitFactoryBuild
    {
        float amount = 1;
        float rotation = 0;
        float payloadAmount = 0;
        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);
            table.row();
            table.add("Amount:");
            table.row();
            table.slider(1,maxAmount,1f,amount, true,this::configure);
        }

        public boolean hasAllItems()
        {
            UnitPlan plan = plans.get(currentPlan);
            for(ItemStack iS : plan.requirements)
                if(items.get(iS.item) < iS.amount * amount)
                    return false;
            return true;
        }

        @Override
        public Object config()
        {
            return amount + ";" + currentPlan;
        }

        @Override
        public void updateTile()
        {
            rotation = Mathf.approachDelta(rotation,1,edelta()*0.01f/amount);
            rotation %= 1;
            if(!configurable){
                currentPlan = 0;
            }

            if(currentPlan < 0 || currentPlan >= plans.size){
                currentPlan = -1;
            }

            if(efficiency > 0 && currentPlan != -1 && hasAllItems()){
                time += edelta() * speedScl * Vars.state.rules.unitBuildSpeed(team);
                progress += edelta() * Vars.state.rules.unitBuildSpeed(team);
                speedScl = Mathf.lerpDelta(speedScl, 1f, 0.05f);
            }else{
                speedScl = Mathf.lerpDelta(speedScl, 0f, 0.05f);
            }
            if(currentPlan != -1 && payloadAmount > 0 && payload == null) {
                Unit unit = plans.get(currentPlan).unit.create(team);
                if(commandPos != null && unit.isCommandable()){
                    unit.command().commandPosition(commandPos);
                }
                consume();
                payload = new UnitPayload(unit);
                payloadAmount--;
                //if (Vars.net.client()) return;
                Events.fire(new EventType.UnitCreateEvent(payload.unit, this));
            }
            moveOutPayload();

            if(currentPlan != -1 && payload == null && hasAllItems()){
                UnitPlan plan = plans.get(currentPlan);

                //make sure to reset plan when the unit got banned after placement
                if(plan.unit.isBanned()){
                    currentPlan = -1;
                    return;
                }

                if(progress >= plan.time){
                    progress %= 1f;

                    Unit unit = plan.unit.create(team);
                    if(commandPos != null && unit.isCommandable()){
                        unit.command().commandPosition(commandPos);
                    }
                    payload = new UnitPayload(unit);
                    payVector.setZero();
                    consume();
                    payloadAmount += amount-1;
                    //if (Vars.net.client()) return;
                    Events.fire(new EventType.UnitCreateEvent(payload.unit, this));
                }

                progress = Mathf.clamp(progress, 0, plan.time);
            }else{
                progress = 0f;
            }
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return currentPlan != -1 && items.get(item) < getMaximumAccepted(item) &&
                    Structs.contains(plans.get(currentPlan).requirements, stack -> stack.item == item);
        }

        @Override
        public int getMaximumAccepted(Item item){
            int ret = 0;
            for (ItemStack iS : plans.get(currentPlan).requirements)
                if (iS.item == item)
                    ret = iS.amount * 2 * maxAmount;
            return ret;
        }

        @Override
        public void draw(){
            super.draw();
            if (currentPlan != -1) {
                UnitPlan plan = plans.get(currentPlan);

                float revProg = (progress / plan.time - 1) * -1;
                if (progress > 0)
                    for (int i = (int) amount - 1; i >= 0; i--) {
                        Draw.color(Pal.lighterOrange);
                        Lines.stroke(progress / plan.time);
                        Lines.circle(x + Math.round(Math.sin((Mathf.pi * 2) * (i / (amount)) + (Mathf.pi * 2 * rotation)) * Math.sqrt((amount - 1) * size)) * revProg, y + Math.round(Math.cos((Mathf.pi * 2) * (i / (amount)) + (Mathf.pi * 2 * rotation)) * Math.sqrt((amount - 1) * size)) * revProg, 2);
                    }
            }
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(amount);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            amount = read.f();
        }
    }
}
