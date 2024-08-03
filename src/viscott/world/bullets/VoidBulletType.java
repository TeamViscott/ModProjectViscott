package viscott.world.bullets;

import arc.graphics.Color;
import mindustry.entities.bullet.BasicBulletType;

public class VoidBulletType extends BasicBulletType {
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
}
