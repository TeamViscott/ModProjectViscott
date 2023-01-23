package viscott.content.pressure;
import arc.math.*;
import arc.util.io.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import viscott.content.PvStatUnits;

public class PressureProducer extends GenericCrafter{
    public float pressureOutput = 10f;
    public float warmupRate = 0.15f;

    public PressureProducer(String name){
        super(name);

        drawer = new DrawMulti(new DrawDefault(), new DrawPressureOutput());
        rotateDraw = false;
        rotate = true;
        canOverdrive = false;
        drawArrow = true;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.output, pressureOutput, PvStatUnits.pressureUnits);
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("pressure", (PressureProducerBuild entity) -> new Bar("bar.pressure", Pal.lightishGray, () -> entity.pressure / pressureOutput));
    }

    public class PressureProducerBuild extends GenericCrafterBuild implements PressureBlock{
        public float pressure;

        @Override
        public void updateTile(){
            super.updateTile();

            //heat approaches target at the same speed regardless of efficiency
            pressure = Mathf.approachDelta(pressure, pressureOutput * efficiency, warmupRate * delta());
        }

        @Override
        public float pressureFraction(){
            return pressure / pressureOutput;
        }

        @Override
        public float pressure(){
            return pressure;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(pressure);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            pressure = read.f();
        }
    }
}