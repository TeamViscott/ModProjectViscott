package viscott.world.bullets;

import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;

public class VariantBulletType extends BasicBulletType {
 @Override
 public void init(Bullet bullet){
  super.init(bullet);
  bullet.data = randomizeBullet();
 }
 public Seq<BulletType> variants = new Seq().with(new BasicBulletType(0, 0)); //Remember to remove this one when using this.
 private BulletType randomizeBullet(){

  return variants.random();
 }
}

