package viscott.world.draw;

import arc.func.Cons;
import arc.func.Cons2;
import arc.func.Prov;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.world.draw.DrawRegion;

import java.util.function.Consumer;

public class DrawMoveRegion extends DrawRegion {
    public float moveX = 0, moveY = 0, moveRotate = 0;
    public Prov<Float> progress;

    public DrawMoveRegion(String suffix) {
        super(suffix);
    }

    @Override
    public void draw(Building build){
        float prog = 0;
        if (progress != null)
            prog = Mathf.clamp(progress.get(),-1,1);
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);
        if(spinSprite){
            Drawf.spinSprite(region, build.x + x + moveX * prog, build.y + y + moveY * prog, build.totalProgress() * rotateSpeed + rotation + moveRotate * prog);
        }else{
            Draw.rect(region, build.x + x + moveX * prog, build.y + y + moveY * prog, build.totalProgress() * rotateSpeed + rotation + moveRotate * prog);
        }
        Draw.z(z);
    }
}
