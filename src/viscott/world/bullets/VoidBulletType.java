package viscott.world.bullets;

import arc.graphics.Color;
import arc.util.Time;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;

public class VoidBulletType extends BasicBulletType {
    public float voidPreservation = 1f;
    public float voidDamageGrowth = 0f;
    public VoidBulletType(float speed,float damage)
    {
        super(speed,damage);
        backColor = frontColor = trailColor = lightColor = Color.darkGray;
        width = 10;
        fragBullet = new EffectAreaBulletType(){{
            voidRadius = 8*2;
            lifetime = 60*2;
        }};
    }
    @Override
    public void update(Bullet b) {
        if (b.keepAlive) {
            b.time -= Time.delta * voidPreservation;
            b.damage += Time.delta * voidDamageGrowth;
            b.keepAlive = false;
        }
        super.update(b);
    }
}
