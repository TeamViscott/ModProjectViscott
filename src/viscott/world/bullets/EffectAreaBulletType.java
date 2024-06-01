package viscott.world.bullets;

import mindustry.content.Fx;
import mindustry.entities.bullet.*;
import mindustry.gen.Bullet;
import mindustry.type.StatusEffect;
import viscott.content.PvStatusEffects;
import viscott.types.PvLayers;
import viscott.world.chips.EffectAreaC;
import viscott.world.chips.EffectAreaTags;

public class EffectAreaBulletType extends BasicBulletType implements EffectAreaC {
    public float voidRadius = 8*5;
    public StatusEffect voidEffectAlly = null;
    public StatusEffect voidEffectEnemy = PvStatusEffects.voidDecay;
    public float voidDrawLayer = PvLayers.voidLayer;
    public EffectAreaTags areaTag = EffectAreaTags.voidEffects;
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
        super.update(b);
        if (b.fin() < 0.05)
            drawArea(b,b.fin()*(1/0.05f)*voidRadius,voidDrawLayer);
        else
            drawArea(b,b.fout()*voidRadius,voidDrawLayer);
    }
    @Override
    public void update(Bullet b) {
        super.update(b);
        updateVoid(b,b.fout()*voidRadius,voidEffectEnemy,voidEffectAlly,statusDuration,areaTag);
    }
}
