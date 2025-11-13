package viscott.world.bullets;

import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.gen.Bullet;
import mindustry.type.StatusEffect;
import viscott.content.PvStatusEffects;
import viscott.types.PvLayers;
import viscott.world.chips.EffectAreaC;
import viscott.world.chips.EffectAreaTags;

public class EffectAreaBulletType extends BasicBulletType implements EffectAreaC {
    public float voidRadius = 8*5;
    public StatusEffect voidEffectAlly = PvStatusEffects.voidShield;
    public StatusEffect voidEffectEnemy = PvStatusEffects.voidDecay;
    public float voidDrawLayer = PvLayers.voidLayer;
    public EffectAreaTags areaTag = EffectAreaTags.voidEffects;

    public boolean fragOnSpawn = true;

    public EffectAreaBulletType() {
        this(0,0);
    }
    public EffectAreaBulletType(float speed, float damage)
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
        super.draw(b);
        if (b.fin() < 0.05)
            drawArea(b,b.fin()*(1/0.05f)*voidRadius,voidDrawLayer);
        else
            drawArea(b,b.fout()*voidRadius,voidDrawLayer);
    }

    @Override
    public void init(Bullet b) {
        if (fragOnSpawn)
            createFrags(b, b.x, b.y);
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        updateVoid(b,b.fout()*voidRadius,voidEffectEnemy,voidEffectAlly,statusDuration,areaTag);
    }

    @Override
    public void despawned(Bullet b) {
        if (!this.fragOnSpawn) {
            if (this.despawnHit) {
                this.hit(b);
            } else {
                this.createUnits(b, b.x, b.y);
            }
        }

        this.despawnEffect.at(b.x, b.y, b.rotation(), this.hitColor);
        this.despawnSound.at(b);
        Effect.shake(this.despawnShake, this.despawnShake, b);
    }
}
