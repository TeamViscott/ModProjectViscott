package viscott.world.bullets;

import arc.graphics.Color;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.Damage;
import mindustry.entities.Fires;
import mindustry.entities.Lightning;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.Bullet;
import mindustry.type.Liquid;
import mindustry.world.Tile;

import static mindustry.Vars.world;

public class LiquidLaserBulletType extends LaserBulletType {
    Liquid liquid = null;
    public LiquidLaserBulletType(float damage,Liquid liquid) {
        super(damage);
        this.liquid = liquid;
        colors = new Color[3];
        colors[0] = colors[1] = colors[2] = liquid.color;
    }

    @Override
    public void init(Bullet b){
        float resultLength = Damage.collideLaser(b, length, largeHit, laserAbsorb, pierceCap), rot = b.rotation();

        laserEffect.at(b.x, b.y, rot, resultLength * 0.75f);

        for(float i = 0; i <= resultLength; i++){
            float cx = b.x + Angles.trnsx(rot,  i),
                    cy = b.y + Angles.trnsy(rot, i);
            if(liquid.canExtinguish()){
                Tile tile = world.tileWorld(cx, cy);
                if(tile != null && Fires.has(tile.x, tile.y)){
                    Fires.extinguish(tile, 100f);
                }
            }
        }

        if(lightningSpacing > 0){
            int idx = 0;
            for(float i = 0; i <= resultLength; i += lightningSpacing){
                float cx = b.x + Angles.trnsx(rot,  i),
                        cy = b.y + Angles.trnsy(rot, i);
                int f = idx++;

                for(int s : Mathf.signs){
                    Time.run(f * lightningDelay, () -> {
                        if(b.isAdded() && b.type == this){
                            Lightning.create(b, lightningColor,
                                    lightningDamage < 0 ? damage : lightningDamage,
                                    cx, cy, rot + 90*s + Mathf.range(lightningAngleRand),
                                    lightningLength + Mathf.random(lightningLengthRand));
                        }
                    });
                }
            }
        }
    }
}
