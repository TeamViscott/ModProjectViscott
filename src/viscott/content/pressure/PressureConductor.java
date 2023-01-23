package viscott.content.pressure;

import arc.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.draw.*;

public class PressureConductor extends Block{
    public float visualMaxPressure = 15f;
    public DrawBlock drawer = new DrawDefault();
    public boolean splitPressure = false;

    public PressureConductor(String name){
        super(name);
        update = solid = rotate = true;
        rotateDraw = false;
        size = 3;
    }

    @Override
    public void setBars(){
        super.setBars();

        //TODO show number
        addBar("pressure", (PressureConductorBuild entity) -> new Bar(() -> Core.bundle.format("bar.pressureamount", (int)(entity.pressure + 0.001f)), () -> Pal.lightOrange, () -> entity.pressure / visualMaxPressure));
    }

    @Override
    public void load(){
        super.load();

        drawer.load(this);
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        drawer.drawPlan(this, plan, list);
    }

    @Override
    public TextureRegion[] icons(){
        return drawer.finalIcons(this);
    }

    public class PressureConductorBuild extends Building implements PressureBlock, PressureConsumer{
        public float pressure = 0f;
        public float[] sidePressure = new float[4];
        public IntSet cameFrom = new IntSet();
        public long lastPressureUpdate = -1;

        @Override
        public void draw(){
            drawer.draw(this);
        }

        @Override
        public void drawLight(){
            super.drawLight();
            drawer.drawLight(this);
        }

        @Override
        public float[] sidePressure(){
            return sidePressure;
        }

        @Override
        public float PressureRequirement(){
            return visualMaxPressure;
        }

        @Override
        public void updateTile(){
            updatePressure();
        }

        public void updatePressure(){
            if(lastPressureUpdate == Vars.state.updateId) return;

            lastPressureUpdate = Vars.state.updateId;
            pressure = calculateHeat(sidePressure, cameFrom);
        }

        @Override
        public float warmup(){
            return pressure;
        }

        @Override
        public float pressure(){
            return pressure;
        }

        @Override
        public float pressureFraction(){
            return (pressure / visualMaxPressure) / (splitPressure ? 3f : 1);
        }
    }
}
