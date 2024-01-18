package viscott.world.bullets;

import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.Damage;
import mindustry.entities.Lightning;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.Bullet;

public class RaygunBulletType extends LaserBulletType {
    public BulletType trailBullet;
    public int trailBullets = 1;
    public RaygunBulletType(float damage) {
        super(damage);
    }
    @Override
    public void init(Bullet b) {
        float resultLength = Damage.collideLaser(b, length, largeHit, laserAbsorb, pierceCap), rot = b.rotation();

        laserEffect.at(b.x, b.y, rot, resultLength * 0.75f);

        if (trailBullets > 0 && trailBullet != null) {
            int idx = 0;
            for (float i = 0; i <= resultLength; i += 8) {
                int f = idx++;
                float cx = b.x + Angles.trnsx(rot,  i),
                        cy = b.y + Angles.trnsy(rot, i);

                for(int s : Mathf.signs){
                    Time.run(f * intervalDelay, () -> {
                        if(b.isAdded() && b.type == this)
                            for(int c = 0;c < trailBullets;c++)
                                trailBullet.create(b,cx,cy,b.rotation());
                    });
                }
            }
        }
    }
}
