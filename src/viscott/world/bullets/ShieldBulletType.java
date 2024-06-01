package viscott.world.bullets;

import arc.Events;
import arc.util.Tmp;
import mindustry.entities.Fires;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.world.blocks.ConstructBlock;

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
        boolean wasDead = false;
        if (entity instanceof Unit unit) {
            if (unit.dead) {
                wasDead = true;
            }
        }
        if (entity instanceof Shieldc s) {
            if (s.shield() > b.damage)
                s.damagePierce(b.damage);
            else {
                float remainder = 1-s.shield()/b.damage;
                s.damagePierce(b.damage*(1-remainder));
                if (pierce)
                    s.damagePierce(baseDamage*remainder);
                else
                    s.damage(baseDamage*remainder);
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

    @Override
    public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct) {
        float perc = b.damage/damage;
        b.damage = perc*baseDamage;
        if (this.makeFire && build.team != b.team) {
            Fires.create(build.tile);
        }

        if (this.heals() && build.team == b.team && !(build.block instanceof ConstructBlock)) {
            this.healEffect.at(build.x, build.y, 0.0F, this.healColor, build.block);
            build.heal(this.healPercent / 100.0F * build.maxHealth + this.healAmount);
        } else if (build.team != b.team && direct) {
            this.hit(b);
        }
        this.handlePierce(b, initialHealth, x, y);
        b.damage = perc*damage;
    }
}
