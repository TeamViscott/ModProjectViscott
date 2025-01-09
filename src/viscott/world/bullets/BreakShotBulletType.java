package viscott.world.bullets;

import arc.math.Angles;
import arc.math.Mathf;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;

public class BreakShotBulletType extends BasicBulletType {

    public float breakAwayRange = 8*4;
    public boolean breakAwayTargetAllies = false;
    public boolean breakAwayTargetEnemys = true;
    public boolean breakAwayTargetHurt = false;
    @Override public void createFrags(Bullet b, float x, float y) {
        if (this.fragBullet != null && (this.fragOnAbsorb || !b.absorbed)) {
            float len = 1;
            float direction = Float.NaN;

            Teamc targetUnit = null;
            if (breakAwayTargetEnemys)
                 targetUnit = Units.closestEnemy(b.team,b.x,b.y,breakAwayRange,(e)->!e.dead);

            if (breakAwayTargetAllies && targetUnit == null)
                targetUnit = Units.closestTarget(b.team,b.x,b.y,breakAwayRange, (e)-> !e.dead && (!breakAwayTargetHurt || e.health < e.maxHealth));

            if (targetUnit != null)
                direction = Angles.angle(b.x,b.y,targetUnit.x(),targetUnit.y());

            for(int i = 0; i < this.fragBullets; ++i) {
                float a =
                        (Float.isNaN(direction) ? b.rotation() + Mathf.range(this.fragRandomSpread / 2.0F) : direction) +
                        this.fragAngle +
                        (float)(i - this.fragBullets / 2) * this.fragSpread;
                this.fragBullet.create(b,
                        x+Angles.trnsx(a, len),
                        y+Angles.trnsy(a, len),
                        a,
                        Mathf.random(this.fragVelocityMin, this.fragVelocityMax),
                        Mathf.random(this.fragLifeMin, this.fragLifeMax)
                );
            }
        }

    }
}
