package viscott.content;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.UnitSorts;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
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
    bScatter, bLancer, bForeshadow, bFuse;
    public static void load(){
        bScatter = new ItemTurret("b-scatter"){{
            requirements(Category.turret, with(Items.copper, 35));
            localizedName = "Photon";
            
            ammo(
                    Items.copper,  new BasicBulletType(5f, 19){{
                        width = 7f;
                        shoot.shots = 3;
                        shoot.shotDelay = 3;
                        height = 9f;
                        lifetime = 60f;
                        ammoMultiplier = 2;
                        lightning = 5;
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
                        lightning = 5;
                        lightningDamage = 5;
                        lightningCone = 180;
                        lightningLengthRand = 40;
                        lightningLength = 16;
                        lightningColor = Color.valueOf("9bc4e4");
                        shoot.shots = 3;
                        shoot.shotDelay = 3;
                    }},
                    Items.silicon, new BasicBulletType(6f, 22){{
                        width = 7f;
                        height = 9f;
                        homingPower = 0.2f;
                        shoot.shots = 3;
                        shoot.shotDelay = 3;
                        reloadMultiplier = 1.5f;
                        ammoMultiplier = 5;
                        lifetime = 60f;
                        lightning = 5;
                        lightningDamage = 10;
                        lightningCone = 360;
                        lightningLengthRand = 30;
                        lightningLength = 8;
                        lightningColor = Color.valueOf("9bc4e4");
                    }}
            );

            shootY = 3f;
            reload = 10f;
            range = 220;
            shootCone = 15f;
            ammoUseEffect = Fx.casing1;
            health = 450;
            inaccuracy = 5f;
            size = 2;
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
            targetAir = false;
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
                width = 8;
                drawSize = 400f;
                collidesAir = false;
                length = 173f;
                ammoMultiplier = 1f;
                pierceCap = 12;
                lightning = 3;
                lightningCone = 22.5f;
                lightningDamage = 30;
                lightningLength = 16;
                lightningColor = Pal.redDust;

                status = PvStatusEffects.doused;
                statusDuration = 5*60f;
            }};
        }};
        bForeshadow = new ItemTurret("b-foreshadow"){{
            range = 8*80f;
            localizedName = "Cryo";

            requirements(Category.turret, with(Items.copper, 1000, Items.metaglass, 600, Items.surgeAlloy, 300, Items.plastanium, 200, Items.silicon, 600));
            ammo(
                    Items.surgeAlloy, new BasicBulletType(){{
                        shootEffect = Fx.instShoot;
                        damage = 500;
                        buildingDamageMultiplier = 0.2f;
                        pierce = true;
                        pierceCap = 3;
                        pierceBuilding = true;
                        speed = 2;
                        hitShake = 6f;
                        intervalBullets = 10;
                        intervalRandomSpread = 0;
                        intervalSpread = 36;
                        intervalAngle = 36;
                        bulletInterval = 15;
                        trailWidth = 2;
                        trailLength = 20;
                        trailColor = backColor = lightColor = Pal.lancerLaser;
                        lifetime = PvUtil.GetRange(2, 80);
                    ammoMultiplier = 1f;
                    intervalBullet = new BasicBulletType(1,200)
                    {{
                        lifetime = PvUtil.GetRange(4, 15);
                        buildingDamageMultiplier = 0.1f;
                        drag = -0.05f;
                        intervalBullets = 5;
                        intervalRandomSpread = 0;
                        intervalSpread = 72;
                        intervalAngle = 72;
                        trailWidth = 2;
                        trailLength = 20;
                        trailColor = backColor = lightColor = Pal.lancerLaser;
                        bulletInterval = 10;
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
        bFuse = new ItemTurret("b-fuse"){{
            requirements(Category.turret, with(Items.copper, 225, Items.graphite, 225, Items.thorium, 100));
localizedName = "OverBurner";
            reload = 20f;
            shake = 4f;
            range = 160f;
            recoil = 5f;

            shoot = new ShootSpread(5, 15f);

            shootCone = 30;
            size = 3;
            envEnabled |= Env.space;

            scaledHealth = 240;
            shootSound = Sounds.shotgun;
            coolant = consumeCoolant(0.3f);

            float brange = range + 10f;

            ammo(
                    Items.pyratite, new ShrapnelBulletType(){{
                        length = brange;
                        damage = 150f;
                        ammoMultiplier = 5f;
                        incendChance = 1;
                        incendAmount = 1;
                        incendSpread = 1;
                        status = PvStatusEffects.doused;
                        statusDuration = 1200;
                        toColor = Pal.lightPyraFlame;
                        shootEffect = smokeEffect = Fx.shootPyraFlame;
                    }}
            );
        }};
    }
}
