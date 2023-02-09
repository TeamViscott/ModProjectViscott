package viscott.world.block.unit;

import arc.Events;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
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
    public BulkUnitFactory(String name)
    {
        super(name);
        clearOnDoubleTap = true;
        config(Float.class,(BulkUnitFactoryBuild tile, Float i) -> {
            tile.amount = Math.round(i);
        });
        configClear((BulkUnitFactoryBuild tile) -> tile.amount = 1);
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

        @Override
        public void updateTile()
        {
            if(!configurable){
                currentPlan = 0;
            }

            if(currentPlan < 0 || currentPlan >= plans.size){
                currentPlan = -1;
            }

            if(efficiency > 0 && currentPlan != -1){
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
                payload = new UnitPayload(unit);
                Events.fire(new EventType.UnitCreateEvent(payload.unit, this));
                payloadAmount--;
            }
            moveOutPayload();

            if(currentPlan != -1 && payload == null){
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
                    Events.fire(new EventType.UnitCreateEvent(payload.unit, this));
                }

                progress = Mathf.clamp(progress, 0, plan.time);
            }else{
                progress = 0f;
            }
        }

        @Override
        public void draw(){
            super.draw();
            rotation = Mathf.approachDelta(rotation,1,0.01f/amount);
            rotation %= 1;
            if (progress > 0)
                for(int i = (int)amount-1;i>=0;i--)
                {
                    Draw.color(Pal.lighterOrange);
                    Lines.stroke(progress/plans.get(currentPlan).time);
                    Lines.circle(x+Math.round(Math.sin((Mathf.pi*2)*(i/(amount))+(Mathf.pi*2*rotation))*Math.sqrt((amount-1)*6)),y+Math.round(Math.cos((Mathf.pi*2)*(i/(amount))+(Mathf.pi*2*rotation))*Math.sqrt((amount-1)*6)),2);
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
