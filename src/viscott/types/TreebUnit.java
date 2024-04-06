package viscott.types;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import viscott.utilitys.*;
import mindustry.gen.*;
import mindustry.graphics.*;

public class TreebUnit extends LegsUnit{
    Seq<TreebBranch> branches = new Seq<>();
    int group;
    float smoothProgress;

    int branchIdx = 0;
    float branchReload = 0;

    @Override
    public void update(){
        float maxProg = 0f;

        smoothProgress = Math.max(Mathf.lerpDelta(smoothProgress, Math.min(maxProg, 2f), 0.25f), smoothProgress);
        if(smoothProgress > 1) smoothProgress = 1f;
        int ir = 0;
        if(smoothProgress >= (1f - 0.001f)){
            group++;
            smoothProgress = 0f;
        }

        super.update();

        for(TreebBranch t : branches){
            t.updateTargetPosition(aimX, aimY);
            t.update(this);
        }
        if(isShooting){
            TreebBranch t = branches.get(branchIdx);
            if(branchReload <= 0f && t.canShoot()){
                t.shoot(aimX, aimY);

                branchIdx = (branchIdx + 1) % branches.size;
                branchReload = 140f / branches.size + 3f;
            }
        }
        if(branchReload > 0) branchReload -= Time.delta;
    }

    @Override
    public void draw(){
        float z = !isAdded() ? Draw.z() : elevation > 0.5f ? (type.lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : type.groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
        Draw.z(z - 0.02f);
        type.applyColor(this);
        Draw.z(z);
        for(TreebBranch t : branches){
            t.draw(this);
        }
        Draw.reset();

        super.draw();
    }

    @Override
    public void add(){
        if(!isAdded()){
            for(int i = 0; i < 24; i++){
                TreebBranch t = new TreebBranch();
                t.set(this, Mathf.random(360f));
                branches.add(t);
            }
        }
        super.add();
    }

    @Override
    public boolean serialize(){
        return false;
    }
}
