package viscott.world.bullets;

import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.entities.Mover;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.logic.LExecutor;

public class VariantBulletType extends BasicBulletType {
 public Seq<BulletType> variants = new Seq<>();
/* public VariantBulletType() {
  this(new BulletType(0,0));
 }*/
 public VariantBulletType(BulletType... bulletTypes) {
   variants.addAll(bulletTypes);
 }
 @Override
 public void init(Bullet bullet){
  super.init(bullet);
  /* This is not used when creation of a bullet is made. its used to initialize with test bullet */
 }

 @Override
 public @Nullable Bullet create(@Nullable Entityc owner, @Nullable Entityc shooter, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data, @Nullable Mover mover, float aimX, float aimY){

  return randomizeBullet().create(owner, shooter, team, x, y, angle, damage, velocityScl, lifetimeScl, data, mover, aimX, aimY);
 }
 private BulletType randomizeBullet(){

  return variants.random();
 }
}

