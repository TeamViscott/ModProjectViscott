package viscott.content.shootpatterns;

import arc.math.Mathf;
import mindustry.entities.pattern.ShootPattern;

public class RandomShootpattern extends ShootPattern {
    public float randomSpread = 0;
    @Override
    public void shoot(int totalShots, BulletHandler handler) {
        for(int i = 0; i < this.shots; ++i) {
            handler.shoot(0.0F, 0.0F, Mathf.random(randomSpread)-(randomSpread/2), this.firstShotDelay + this.shotDelay * (float)i);
        }

    }
}
