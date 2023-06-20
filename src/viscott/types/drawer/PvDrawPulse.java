package viscott.types.drawer;

import arc.graphics.g2d.Draw;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.world.draw.DrawRegion;

public class PvDrawPulse extends DrawRegion {
    public PvDrawPulse(String suffix){
        super(suffix);
    }

    @Override
    public void draw(Building build){
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);
        if(spinSprite){
            Drawf.spinSprite(region, build.x + x, build.y + y, build.totalProgress() * rotateSpeed + rotation);
        }else{
            Draw.rect(region, build.x + x, build.y + y, 32*build.warmup(),32*build.warmup(),build.totalProgress() * rotateSpeed + rotation);
        }
        Draw.z(z);
    }
}
