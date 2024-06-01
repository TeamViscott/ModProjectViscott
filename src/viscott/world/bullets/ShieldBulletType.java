package viscott.world.bullets;

import arc.Events;
import arc.util.Tmp;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.EventType;
import mindustry.gen.*;

public class ShieldBulletType extends BasicBulletType {
    static final EventType.UnitDamageEvent bulletDamageEvent = new EventType.UnitDamageEvent();
    public float shieldDamage = 1;
    private float baseDamage = 0;
    public ShieldBulletType(float speed,float damage) {
        super(speed,damage);
        shieldDamage = damage;
    }
    @Override
    public void init() {
        super.init();
        baseDamage = damage;
        damage = shieldDamage;
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health) {
        float sDamage = b.damage/damage*shieldDamage;
        boolean wasDead = false;
        if (entity instanceof Unit unit) {
            if (unit.dead) {
                wasDead = true;
            }
        }
        if (entity instanceof Shieldc s) {
            if (s.shield() > sDamage)
                s.damagePierce(sDamage);
            else {
                float remainder = 1-s.shield()/sDamage;
                s.damagePierce(sDamage*(1-remainder));
                if (pierce)
                    s.damagePierce(b.damage*remainder);
                else
                    s.damage(b.damage*remainder);
            }
        }

        if (entity instanceof Unit unit) {
            Tmp.v3.set(unit).sub(b).nor().scl(this.knockback * 80.0F);
            if (this.impact) {
                Tmp.v3.setAngle(b.rotation() + (this.knockback < 0.0F ? 180.0F : 0.0F));
            }

            unit.impulse(Tmp.v3);
            unit.apply(this.status, this.statusDuration);
            Events.fire(bulletDamageEvent.set(unit, b));
        }

        if (!wasDead && entity instanceof Unit unit) {
            if (unit.dead) {
                Events.fire(new EventType.UnitBulletDestroyEvent(unit, b));
            }
        }

        this.handlePierce(b, health, entity.x(), entity.y());
    }
}
