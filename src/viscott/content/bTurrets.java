package viscott.content;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.UnitSorts;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.bullet.PointBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.meta.Env;
import viscott.utilitys.PvUtil;

import static mindustry.type.ItemStack.with;

public class bTurrets {
    public static Block
    bDuo, bLancer, bForeshadow, bScorch;
    public static void load(){
        bDuo = new ItemTurret("b-duo"){{
            requirements(Category.turret, with(Items.copper, 35));
            localizedName = "Photon";
            ammo(
                    Items.copper,  new BasicBulletType(5f, 19){{
                        width = 7f;
                        height = 9f;
                        lifetime = 60f;
                        ammoMultiplier = 2;
                        lightning = 10;
                        lightningDamage = 5;
                        lightningCone = 180;
                        lightningLengthRand = 40;
                        lightningLength = 16;
                        lightningColor = Color.valueOf("9bc4e4");
                    }},
                    Items.graphite, new BasicBulletType(7f, 28){{
                        width = 9f;
                        height = 12f;
                        reloadMultiplier = 0.6f;
                        ammoMultiplier = 4;
                        lifetime = 60f;
                        lightning = 10;
                        lightningDamage = 5;
                        lightningCone = 180;
                        lightningLengthRand = 40;
                        lightningLength = 16;
                        lightningColor = Color.valueOf("9bc4e4");
                    }},
                    Items.silicon, new BasicBulletType(6f, 22){{
                        width = 7f;
                        height = 9f;
                        homingPower = 0.2f;
                        reloadMultiplier = 1.5f;
                        ammoMultiplier = 5;
                        lifetime = 60f;
                        lightning = 7;
                        lightningDamage = 10;
                        lightningCone = 360;
                        lightningLengthRand = 30;
                        lightningLength = 8;
                        lightningColor = Color.valueOf("9bc4e4");
                    }}
            );

            shoot = new ShootAlternate(3.5f);

            shootY = 3f;
            reload = 10f;
            range = 220;
            shootCone = 15f;
            ammoUseEffect = Fx.casing1;
            health = 450;
            inaccuracy = 2f;
            rotateSpeed = 10f;
            coolant = consumeCoolant(0.1f);

            limitRange();
        }};
        bLancer = new PowerTurret("b-lancer"){{
            requirements(Category.turret, with(Items.copper, 60, Items.lead, 70, Items.silicon, 60, Items.titanium, 30));
            range = 190f;
            localizedName = "Plasma";

            shoot.firstShotDelay = 20f;

            recoil = 2f;
            reload = 80f;
            shake = 2f;
            shootEffect = Fx.lancerLaserShoot;
            smokeEffect = Fx.none;
            heatColor = Color.red;
            size = 2;
            scaledHealth = 320;
            targetAir = true;
            moveWhileCharging = false;
            accurateDelay = false;
            shootSound = Sounds.laser;
            coolant = consumeCoolant(0.2f);

            consumePower(10f);
            shoot = new ShootSpread(3, 45/3f);

            shootType = new LaserBulletType(140){{
                colors = new Color[]{Pal.redDust.cpy().a(0.4f), Pal.redDust, Color.black};
                //TODO merge
                chargeEffect = new MultiEffect(Fx.lancerLaserCharge, Fx.lancerLaserChargeBegin);

                buildingDamageMultiplier = 0.25f;
                hitEffect = Fx.hitLancer;
                hitSize = 4;
                lifetime = 16f;
                drawSize = 400f;
                collidesAir = false;
                length = 173f;
                ammoMultiplier = 1f;
                pierceCap = 12;
                lightning = 3;
                lightningCone = 22.5f;
                lightningDamage = 30;
                lightningLength = 5;
                lightningColor = Pal.redDust;

                status = PvStatusEffects.doused;
                statusDuration = 5*60f;
            }};
        }};
        bForeshadow = new ItemTurret("b-foreshadow"){{
            range = 500f;
            localizedName = "Cryo";

            requirements(Category.turret, with(Items.copper, 1000, Items.metaglass, 600, Items.surgeAlloy, 300, Items.plastanium, 200, Items.silicon, 600));
            ammo(
                    Items.surgeAlloy, new BasicBulletType(){{
                        shootEffect = Fx.instShoot;
                        hitEffect = Fx.instHit;
                        smokeEffect = Fx.smokeCloud;
                        trailEffect = Fx.instTrail;
                        despawnEffect = Fx.instBomb;
                        damage = 500;
                        buildingDamageMultiplier = 0.2f;
                        speed = range/180f;
                        hitShake = 6f;
                        intervalBullets = 6;
                        intervalRandomSpread = 0;
                        intervalSpread = 60;
                        intervalAngle = 60;
                        bulletInterval = 60;
                        scaleLife = true;
                        lifetime = (speed/60f) * range;
                    ammoMultiplier = 1f;
                    intervalBullet = new BasicBulletType(5,100)
                    {{
                        lifetime = PvUtil.GetRange(5, 10);
                        buildingDamageMultiplier = 0.1f;
                    }};
                    }}
            );

            maxAmmo = 40;
            ammoPerShot = 5;
            rotateSpeed = 3f;
            reload = 240f;
            ammoUseEffect = Fx.casing3Double;
            recoil = 5f;
            cooldownTime = reload;
            shake = 4f;
            size = 4;
            shootCone = 2f;
            shootSound = Sounds.railgun;
            unitSort = UnitSorts.strongest;
            envEnabled |= Env.space;

            coolantMultiplier = 0.4f;
            scaledHealth = 200;

            coolant = consumeCoolant(1f);
            consumePower(10f);
        }};
        bScorch = new ItemTurret("b-scorch"){{
            requirements(Category.turret, with(Items.copper, 25, Items.graphite, 22));
            localizedName = "Igniter";
            //todo everything should change
            ammo(
                    Items.coal, new BulletType(3.35f, 17f){{
                        ammoMultiplier = 3f;
                        hitSize = 7f;
                        lifetime = 18f;
                        pierce = true;
                        collidesAir = false;
                        statusDuration = 60f * 4;
                        shootEffect = Fx.shootSmallFlame;
                        hitEffect = Fx.hitFlameSmall;
                        despawnEffect = Fx.none;
                        status = StatusEffects.burning;
                        keepVelocity = false;
                        hittable = false;
                    }},
                    Items.pyratite, new BulletType(4f, 60f){{
                        ammoMultiplier = 6f;
                        hitSize = 7f;
                        lifetime = 18f;
                        pierce = true;
                        collidesAir = false;
                        statusDuration = 60f * 10;
                        shootEffect = Fx.shootPyraFlame;
                        hitEffect = Fx.hitFlameSmall;
                        despawnEffect = Fx.none;
                        status = StatusEffects.burning;
                        hittable = false;
                    }}
            );
            recoil = 0f;
            reload = 6f;
            coolantMultiplier = 1.5f;
            range = 60f;
            shootCone = 50f;
            targetAir = false;
            ammoUseEffect = Fx.none;
            health = 400;
            shootSound = Sounds.flame;
            coolant = consumeCoolant(0.1f);
        }};
    }
}
