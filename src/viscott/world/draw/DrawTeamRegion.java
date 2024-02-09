package viscott.world.draw;

import arc.graphics.g2d.Draw;
import mindustry.gen.Building;
import mindustry.world.draw.DrawRegion;

public class DrawTeamRegion extends DrawRegion {

    public DrawTeamRegion(){
        super("-team");
    }
    public DrawTeamRegion(String suffix){
        super(suffix);
    }
    @Override
    public void draw(Building build){
        Draw.color(build.team.color);
        super.draw(build);
        Draw.color();
    }
}
