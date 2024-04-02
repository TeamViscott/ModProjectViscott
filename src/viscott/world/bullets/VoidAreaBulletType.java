package viscott.world.bullets;

import mindustry.content.Fx;
import mindustry.entities.bullet.*;
import mindustry.gen.Bullet;
import viscott.world.chips.VoidAreaC;

public class VoidAreaBulletType extends BasicBulletType implements VoidAreaC {
    public float voidRadius = 8*5;
    public VoidAreaBulletType() {
        this(0,0);
    }
    public VoidAreaBulletType(float speed,float damage)
    {
        super(speed,damage);
        collideFloor = collidesAir = collidesTeam = collides = false;
        width = height = shrinkX = shrinkY = 0;
        despawnEffect = hitEffect = Fx.none;
        lifetime = 60*5;
        hittable = false;
    }

    @Override
    public void draw(Bullet b) {
        super.update(b);
        if (b.fin() < 0.05)
            drawVoid(b,b.fin()*(1/0.05f)*voidRadius);
        else
            drawVoid(b,b.fout()*voidRadius);
    }
    @Override
    public void update(Bullet b) {
        super.update(b);
        updateVoid(b,b.fout()*voidRadius);
    }
}
