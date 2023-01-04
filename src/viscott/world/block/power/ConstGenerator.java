package viscott.world.block.power;

import mindustry.world.blocks.power.SolarGenerator;

public class ConstGenerator extends SolarGenerator {
    public ConstGenerator(String name)
    {
        super(name);
    }
    public class ConstGeneratorBuild extends  SolarGeneratorBuild
    {
        @Override
        public void updateTile(){
            productionEfficiency = 1;
        }
    }
}
