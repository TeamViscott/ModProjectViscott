package viscott.world.bullets;

import arc.math.Mathf;
import mindustry.content.Fx;
import viscott.content.PvStatusEffects;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import viscott.utilitys.LargeBranch;

public class LargeBranchBulletType extends BulletType {
    public int branchLength = 30;
    public int branchLengthRand = 10;
    public LargeBranchBulletType(float damage) {
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
        LargeBranch.create(b.team, damage, b.x, b.y, b.rotation(), branchLength + Mathf.random(branchLengthRand));
    }
}
