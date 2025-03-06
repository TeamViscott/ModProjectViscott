package viscott.world.bullets;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Posc;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;

public class CrackShotBulletType extends BasicBulletType {

    public CrackShotBulletType() {
        super();
    }

    public CrackShotBulletType(float speed,float damage) {
        super(speed,damage);
    }
    public float breakAwayRange = 8*4;
    public breakAwayTargetTypes breakAwayTarget = breakAwayTargetTypes.Enemies;
    public boolean breakAwayRotationLock = false; // forces to bullets sprite to be facing the target.
    public enum breakAwayTargetTypes {
        Allies,
        Enemies,
        AlliesHurt,
        Cursor
    }

    @Override
    public void draw(Bullet b) {
        this.drawTrail(b);
        this.drawParts(b);
        float shrink = this.shrinkInterp.apply(b.fout());
        float height = this.height * (1.0F - this.shrinkY + this.shrinkY * shrink);
        float width = this.width * (1.0F - this.shrinkX + this.shrinkX * shrink);
        float offset;
        if (breakAwayRotationLock)
            offset = -90.0F + Angles.angle(b.x,b.y,target.x,target.y);
        else
            offset = -90.0F + (this.spin != 0.0F ? Mathf.randomSeed(b.id, 360.0F) + b.time * this.spin : 0.0F) + this.rotationOffset;
        Color mix = Tmp.c1.set(this.mixColorFrom).lerp(this.mixColorTo, b.fin());
        Draw.mixcol(mix, mix.a);
        if (this.backRegion.found()) {
            Draw.color(this.backColor);
            Draw.rect(this.backRegion, b.x, b.y, width, height, b.rotation() + offset);
        }

        Draw.color(this.frontColor);
        Draw.rect(this.frontRegion, b.x, b.y, width, height, b.rotation() + offset);
        Draw.reset();
    }
    @Override
    public void update(Bullet b) {
        super.update(b);
        Posc pos;
        switch(breakAwayTarget) {
            case Enemies:
                pos = Units.closestEnemy(b.team,b.x,b.y,breakAwayRange,(e)->!e.dead);
                target.set(pos);

                break;
            case Allies:
                pos = Units.closestTarget(b.team,b.x,b.y,breakAwayRange, (e)-> !e.dead);
                target.set(pos);
                break;
            case AlliesHurt:
                pos = Units.closestTarget(b.team,b.x,b.y,breakAwayRange, (e)-> !e.dead && e.health < e.maxHealth);
                target.set(pos);
                break;
            case Cursor:
                var owner = (Unit)b.owner;
                if (owner.isPlayer()) {
                    target.x = owner.getPlayer().mouseX;
                    target.y = owner.getPlayer().mouseY;
                } else {
                    target.x = owner.aimX;
                    target.y = owner.aimY;
                }
                break;
        }
    }
    public class Target {
        public float x;
        public float y;
        public void set(Posc pos) {
            x = pos.x();
            y = pos.y();
        }
    }
    Target target = new Target();

    @Override public void createFrags(Bullet b, float x, float y) {
        if (this.fragBullet != null && (this.fragOnAbsorb || !b.absorbed)) {
            float len = 1;
            float direction = Float.NaN;

            if (target != null)
                direction = Angles.angle(b.x,b.y,target.x,target.y);

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
