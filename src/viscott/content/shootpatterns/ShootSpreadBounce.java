package viscott.content.shootpatterns;

import arc.util.Nullable;
import mindustry.entities.pattern.ShootSpread;

public class ShootSpreadBounce extends ShootSpread {
    int bounces = 0;

    public ShootSpreadBounce(int shots, float spread,int bounces){
        super(shots,spread);
        this.bounces = bounces;
    }


    @Override
    public void shoot(int totalShots, BulletHandler handler, @Nullable Runnable barrelIncrementer){
        for(int b = 0; b < bounces;b++) {
            short s = (short) (b % 2 == 0 ? 1 : -1);
            for (int i = 0; i < shots; i++) {
                float angleOffset = i * spread - (shots - 1) * spread / 2f;
                handler.shoot(0, 0, angleOffset * s, firstShotDelay + shotDelay * i /*+ shotDelay * shots * b*/);
            }
        }
    }
}
