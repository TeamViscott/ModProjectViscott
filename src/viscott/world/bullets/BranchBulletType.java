package viscott.world.bullets;

import arc.graphics.*;
import arc.math.*;
import viscott.content.PvStatusEffects;
import viscott.utilitys.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;

public class BranchBulletType extends BulletType {
    public int branchLength = 15;
    public int branchLengthRand = 5;

    public BranchBulletType(float damage){
        super(0.0001f, damage);
        this.damage = damage;

        lifetime = 1;
        despawnEffect = Fx.none;
        hitEffect = Fx.hitLancer;
        keepVelocity = false;
        hittable = false;
        status = PvStatusEffects.splintered;
    }

    @Override
    public float calculateRange(){
        return (branchLength + branchLengthRand/2f) * 15f;
    }

    @Override
    public float estimateDPS(){
        return super.estimateDPS() * Math.max(branchLength / 10f, 1);
    }

    @Override
    public void draw(Bullet b){
    }

    @Override
    public void init(Bullet b){
        Branch.create(b.team, damage, b.x, b.y, b.rotation(), branchLength + Mathf.random(branchLengthRand));
    }
}
