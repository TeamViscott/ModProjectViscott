package viscott.world.block.unit;

import arc.Events;
import arc.graphics.Color;
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
import mindustry.gen.Payloadc;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.blocks.payloads.BuildPayload;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.blocks.payloads.UnitPayload;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.world.consumers.ConsumeItemDynamic;
import viscott.types.BuildUnitType;
import viscott.types.PvUnitPlan;
import viscott.utilitys.PvUtil;

import java.util.List;

public class BulkUnitFactory extends Reconstructor {
    public int maxAmount = 10;
    public Seq<UnitFactory.UnitPlan> plans = new Seq<>();

    public BulkUnitFactory(String name)
    {
        super(name);
        clearOnDoubleTap = true;
        configurable = true;
        copyConfig = true;
        saveConfig = false;
        hasItems = true;
        config(String.class,(BulkUnitFactoryBuild tile, String i) -> {
            tile.amount = Float.parseFloat(i.split(";")[0]);
            tile.currentPlan = Integer.parseInt(i.split(";")[1]);
        });
        config(Float.class,(BulkUnitFactoryBuild tile, Float n) -> {
            tile.amount = Math.round(n);
        });
        config(Integer.class,(BulkUnitFactoryBuild tile, Integer n) -> {
            tile.currentPlan = n;
        });
        configClear((BulkUnitFactoryBuild tile) -> {tile.amount = 1f;tile.currentPlan = -1;});

        consume(
                new ConsumeItemDynamic((BulkUnitFactory.BulkUnitFactoryBuild e) ->
                        e.currentPlan != -1 ? plans.get(Math.min(e.currentPlan, plans.size - 1)).requirements : ItemStack.empty)
        );
    }

    @Override
    public void init() {
        super.init();
        acceptsPayload = true;
    }

    public class BulkUnitFactoryBuild extends ReconstructorBuild
    {
        float amount = 1;
        float rotation = 0;
        float payloadAmount = 0;
        int currentPlan = -1;
        Block curTemplate = null;
        @Override
        public void buildConfiguration(Table table){
            Seq<UnitType> units = Seq.with(plans).map(u -> u.unit).filter(u -> u.unlockedNow() && !u.isBanned());

            if(units.any()){
                ItemSelection.buildTable(BulkUnitFactory.this, table, units, () -> currentPlan < 0 ? null : plans.get(currentPlan).unit, unit -> configure(plans.indexOf(u -> u.unit == unit)), selectionRows, selectionColumns);
            }else{
                table.table(Styles.black3, t -> t.add("@none").color(Color.lightGray));
            }
            table.row();
            table.add("Amount:");
            table.row();
            table.slider(1,maxAmount,1f,amount, true,this::configure);
        }

        @Override
        public boolean shouldShowConfigure(Player player){
            return player.team() == team();
        }

        public boolean hasAllItems()
        {
            UnitFactory.UnitPlan plan = plans.get(currentPlan);
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
        public boolean acceptPayload(Building Source, Payload payload) {
            if (currentPlan < 0) return false;
            return plans.get(currentPlan) instanceof PvUnitPlan pvPlan &&
                    pvPlan.needsTemplate() && pvPlan.template == payload.content() && curTemplate != pvPlan.template;
        }
        @Override
        public void handlePayload(Building Source, Payload payload) {
            if (payload.content() instanceof PvTemplate p)
                curTemplate = p;
        }

        @Override
        public void updateTile()
        {
            efficiency = power.status;
            rotation = Mathf.approachDelta(rotation,1,edelta()*0.01f/amount);
            rotation %= 1;
            if(!configurable){
                currentPlan = 0;
            }

            if(currentPlan < 0 || currentPlan >= plans.size){
                currentPlan = -1;
            }

            if(efficiency > 0 && currentPlan != -1 && hasAllItems() && templateCorrect()){
                time += edelta() * speedScl * Vars.state.rules.unitBuildSpeed(team);
                constructTime = plans.get(currentPlan).time * amount;
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
                UnitFactory.UnitPlan plan = plans.get(currentPlan);

                //make sure to reset plan when the unit got banned after placement
                if(plan.unit.isBanned()){
                    currentPlan = -1;
                    return;
                }

                if(progress >= constructTime){
                    progress %= 1f;

                    Unit unit = plan.unit.create(team);
                    if(commandPos != null && unit.isCommandable()){
                        unit.command().commandPosition(commandPos);
                    }
                    curTemplate = null;
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
        public boolean templateCorrect() {
            if (currentPlan < 0) return false;
            if (plans.get(currentPlan) instanceof PvUnitPlan pvPlan)
                return pvPlan.template == null || pvPlan.template == curTemplate;
            return true;
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
            Draw.z(Layer.blockOver-2);
            if (currentPlan != -1) {
                UnitFactory.UnitPlan plan = plans.get(currentPlan);

                float revProg = (progress / plan.time - 1) * -1;
                if (progress > 0)
                    for (int i = (int) amount - 1; i >= 0; i--) {
                        Draw.color(Pal.lighterOrange);
                        Lines.stroke(progress / plan.time);
                        Lines.circle(x + Math.round(Math.sin((Mathf.pi * 2) * (i / (amount)) + (Mathf.pi * 2 * rotation)) * Math.sqrt((amount - 1) * size)) * revProg, y + Math.round(Math.cos((Mathf.pi * 2) * (i / (amount)) + (Mathf.pi * 2 * rotation)) * Math.sqrt((amount - 1) * size)) * revProg, 2);
                    }
            }
            if (curTemplate == null || currentPlan < 0 || plans.get(currentPlan) instanceof PvUnitPlan pvPlan && curTemplate != pvPlan.template) return;
            Draw.z(Layer.blockOver-3);
            float m = 1 + -progress/constructTime;
            Draw.color(Pal.lighterOrange.cpy().add(m,m,m));
            Draw.rect(curTemplate.fullIcon,x,y);
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(amount);
            write.i(currentPlan);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            amount = read.f();
            currentPlan = read.i();
        }
    }
}
