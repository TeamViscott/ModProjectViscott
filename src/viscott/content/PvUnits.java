package viscott.content;

import arc.graphics.Color;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.entities.bullet.*;
import mindustry.gen.EntityMapping;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import viscott.utilitys.PvUtil;

public class PvUnits {
    public static UnitType
        /*Core Units*/micro,infrared, spectrum,

        /*Flying Ion Path*/ particle, snippet, fragment, excerpt,

        /*Extra Paths : */
        routerTank, routerBastion
                ;
    public static void load()
    {
        loadFlyingIonPath();
        loadCorePath();
        loadExtra();
    }
    public static void loadCorePath()
    {
        micro = new UnitType("micro")
        {{
            localizedName = "Micro";
            constructor = EntityMapping.map("alpha");
            health = 175;
            armor = 0;
            flying = true;
            buildSpeed = 0.75f;
            mineTier = 2;
            mineSpeed = 4.5f;
            itemCapacity = 40;
            speed = 20.7f / 7.5f;
            drag = 0.02f;
            range = 17*8;
            weapons.add(
                    new Weapon("micro-weapon")
                    {{
                        reload = 60f/8f;
                        x = 2;
                        y = 2;
                        mirror = true;
                        rotationLimit = 30;
                        shootStatus = PvStatusEffects.expent;
                        shootStatusDuration = 120;
                        bullet = new LaserBoltBulletType(5,6)
                        {{
                            homingPower = 0.008f;
                            homingDelay = 1;
                            homingRange = 8*17f;
                            lifetime = PvUtil.GetRange(this.speed,17);
                            lightColor = backColor = Pal.engine;
                            this.recoil = 0.1f;
                            despawnShake = hitShake = 0.5f;
                        }};
                    }}
            );
        }};
        infrared = new UnitType("infrared")
        {{
            localizedName = "Infrared";
            constructor = EntityMapping.map("beta");
            health = 190;
            armor = 0;
            flying = true;
            buildSpeed = 0.9f;
            mineTier = 2;
            mineSpeed = 6.5f;
            itemCapacity = 65;
            speed = 22.8f / 7.5f;
            drag = 0.05f;
            range = 18*8;
            weapons.add(
                    new Weapon("")
                    {{
                        x = 0;
                        y = 5;
                        reload = 60f;
                        rotate = true;
                        rotationLimit = 30;
                        bullet = new LaserBoltBulletType(5,6)
                        {{
                            homingPower = 0.01f;
                            trailLength = 20;
                            trailWidth = 2;
                            homingDelay = 1;
                            homingRange = 8*17f;
                            lifetime = PvUtil.GetRange(this.speed,17);
                            trailColor = lightColor = backColor = Pal.engine;
                            despawnShake = hitShake = 0.5f;
                        }};
                    }}
            );
        }};
        spectrum = new UnitType("spectrum")
        {{
            localizedName = "Spectrum";
            constructor = EntityMapping.map("alpha");
            health = 250;
            armor = 0;
            flying = true;
            buildSpeed = 1.15f;
            mineTier = 2;
            mineSpeed = 7.25f;
            itemCapacity = 90;
            speed = 30.7f / 7.5f;
            drag = 0.02f;
            range = 17*8;
            weapons.add(
                    new Weapon("spectrum-weapon")
                    {
                        {
                            reload = 60f / 8f;
                            x = 2;
                            y = 2;
                            mirror = true;
                            rotationLimit = 30;
                            shootStatus = PvStatusEffects.crescendo;
                            shootStatusDuration = 30;
                            bullet = new LaserBoltBulletType(8, 20) {{
                                homingPower = 0.01f;
                                homingDelay = 1;
                                buildingDamageMultiplier = 0.01f;
                                trailLength = 10;
                                trailWidth = 1;
                                pierce = true;
                                pierceCap = 3;
                                sprite = "circle-bullet";
                                homingRange = 8 * 28f;
                                lifetime = PvUtil.GetRange(this.speed, 28);
                                trailColor = lightColor = backColor = Pal.engine;
                                this.recoil = 0f;
                                despawnShake = hitShake = 0.5f;
                                fragBullets = 3;
                                fragBullet = new LaserBoltBulletType(6, 6) {{
                                    homingPower = 0.01f;
                                    homingDelay = 1;
                                    buildingDamageMultiplier = 0.01f;
                                    trailLength = 10;
                                    trailWidth = 1;
                                    sprite = "circle-bullet";
                                    homingRange = 8 * 28f;
                                    lifetime = PvUtil.GetRange(this.speed, 14);
                                    trailColor = lightColor = backColor = Pal.engine;
                                    this.recoil = 0f;
                                    despawnShake = hitShake = 0.5f;
                                }};
                            }};
                        }}
            );
        }};
    }
    public static void loadFlyingIonPath()
    {
        particle = new UnitType("particle")
        {{
            localizedName = "Particle";
            constructor = EntityMapping.map("flare");
            health = 180;
            armor = 0;
            hitSize = 8;
            drag = 0.1f;
            flying = true;
            speed = 27f/7.5f;
            canBoost = false;
            itemCapacity = 15;
            range = 21 * 8;
            deathExplosionEffect = PvEffects.particleDeath1;
            weapons.add(
                    new Weapon()
                    {{
                        mirror = true;
                        top = false;
                        x = 4;
                        y = 0;
                        reload = 60f/2.6f;
                        alternate = false;
                        bullet = new LaserBoltBulletType(8,17)
                        {{
                            trailColor = backColor = lightColor = Pal.sap;
                            trailWidth = 2;
                            trailLength = 25;
                            lifetime = PvUtil.GetRange(8,21);
                            status = StatusEffects.electrified;
                            statusDuration = 90;
                        }};
                    }}
            );
        }};

        snippet = new UnitType("snippet")
        {{
            localizedName = "Snippet";
            constructor = EntityMapping.map("flare");
            health = 800;
            armor = 1;
            hitSize = 8;
            drag = 0.1f;
            flying = true;
            speed = 25f/7.5f;
            canBoost = false;
            itemCapacity = 15;
            range = 9 * 8;
            deathExplosionEffect = PvEffects.particleDeath2;
            weapons.add(
                    new Weapon()
                    {{
                        mirror = true;
                        top = false;
                        x = 4;
                        range = 15*8;
                        y = 0;
                        continuous = true;
                        reload = 5f;
                        shootSound = Sounds.laserbeam;
                        bullet = new SapBulletType()
                        {{
                            incendChance = 0;
                            buildingDamageMultiplier = 0.2f;
                            shake = 0;
                            status = StatusEffects.sapped;
                            statusDuration = 120f;
                            sapStrength = 0.85f;
                            length = 15*8f;
                            damage = 50;
                            shootEffect = Fx.shootSmall;
                            hitColor = color = Color.valueOf("bf92f9");
                            despawnEffect = Fx.none;
                            width = 0.55f;
                            lifetime = 10f;
                            knockback = -1f;
                        }};
                    }}
            );
        }};
        fragment = new UnitType("fragment")
        {{
            localizedName = "Fragment";
            constructor = EntityMapping.map("horizon");
            health = 1650;
            armor = 2;
            hitSize = 16;
            drag = 0.1f;
            flying = true;
            speed = 19f/7.5f;
            itemCapacity = 25;
            canBoost = false;
            range = 48 * 8;
            deathExplosionEffect = PvEffects.particleDeath3;
            weapons.add(
                    new Weapon(PvUtil.GetName("fragment-mount"))
                    {{
                        x = 0;
                        y = 0;
                        shoot.shots = 3;
                        shoot.shotDelay = 5;
                        reload = 60/1.7f;
                        mirror = false;
                        recoil = 3;
                        inaccuracy = 5;
                        shootSound = Sounds.laserbeam;
                        bullet = new SapBulletType()
                        {{
                            incendChance = 0;
                            buildingDamageMultiplier = 0.2f;
                            shake = 0;
                            status = StatusEffects.sapped;
                            statusDuration = 120f;
                            sapStrength = 0.85f;
                            healPercent = 0.05f;
                            length = 24*8f;
                            damage = 50;
                            shootEffect = Fx.shootSmall;
                            hitColor = color = Color.valueOf("bf92f9");
                            despawnEffect = Fx.none;
                            width = 0.55f;
                            lifetime = 15f;
                            knockback = -1f;
                        }};
                    }},
                    new Weapon()
                    {{
                        x = 0;
                        y = 4;
                        reload = 60/0.5f;
                        mirror = false;
                        shootSound = Sounds.laserbeam;
                        bullet = new RailBulletType(){{
                            shootEffect = Fx.lancerLaserShoot;
                            length = 48*8;
                            pointEffectSpace = 60f;
                            pierceEffect = Fx.railHit;
                            pointEffect = PvEffects.railFrag;
                            hitEffect = Fx.massiveExplosion;
                            smokeEffect = Fx.shootBig2;
                            lightningColor = trailColor = lightColor = Pal.sap;
                            damage = 230;
                            pierceCap = 10;
                            pierceDamageFactor = 0.5f;
                        }};
                    }}
            );
        }};
        excerpt = new UnitType("excerpt")
        {{
            localizedName = "Excerpt";
            constructor = EntityMapping.map("mega");
            health = 15000;
            armor = 4;
            hitSize = 8;
            drag = 0.1f;
            flying = true;
            speed = 14f/7.5f;
            itemCapacity = 25;
            canBoost = false;
            range = 48 * 8;
            deathExplosionEffect = PvEffects.particleDeath3;
            weapons.add(
                    new Weapon(PvUtil.GetName("excerpt-barrage"))
                    {{
                        x = 4;
                        y = -2;
                        shoot.shots = 2;
                        shoot.shotDelay = 5;
                        reload = 60/1.8f;
                        mirror = true;
                        recoil = 3;
                        inaccuracy = 5;
                        shootSound = Sounds.laserbeam;
                        bullet = new LaserBoltBulletType(5.2f, 67){{
                            lifetime = 60f;
                            healPercent = 5f;
                            collidesTeam = true;
                            backColor = Pal.sap;
                            frontColor = Pal.sap;

                        }};
                    }},
            new Weapon(PvUtil.GetName("excerpt-rail-gun"))
            {{
                x = 0;
                y = 0;
                shoot.shots = 1;
                shoot.shotDelay = 5;
                reload = 120;
                mirror = false;
                recoil = 3;
                inaccuracy = 0;
                bullet = new PointBulletType(){{
                    shootEffect = Fx.instShoot;
                    hitEffect = Fx.instHit;
                    smokeEffect = Fx.smokeCloud;
                    trailEffect = Fx.instTrail;
                    despawnEffect = Fx.instBomb;
                    trailSpacing = 20f;
                    damage = 1350;
                    buildingDamageMultiplier = 0.2f;
                    speed = 48*8;
                    trailColor = Pal.sap;
                    hitShake = 6f;
                    ammoMultiplier = 1f;
                }};
            }}
            );
        }};

    }
    public static void loadTarredNavalTree(){

    }

    public static void loadExtra()
    {
        routerTank = new UnitType("router-tank")
        {{
            localizedName = "Router Tank";
            constructor = EntityMapping.map("stell");
            health = 6280;
            armor = 12;
            drag = 0.5f;
            hitSize = 8*2f;
            range = 20*8;
            weapons.add(
                    new Weapon(){{
                        reload = 10;
                        inaccuracy = 5;
                        mirror = false;
                        top = false;
                        shootY = 14;
                        shootX = 0;
                        x = 0;
                        y = 0;
                        bullet = new BasicBulletType(4,280)
                        {{
                            lifetime = PvUtil.GetRange(4,20);
                            sprite = PvUtil.GetName("router-bullet");
                            trailWidth = 2;
                            trailLength = 20;
                            trailColor = lightColor = Pal.lancerLaser;
                            splashDamageRadius = 8*4f;
                            splashDamage = 100;
                            despawnEffect = hitEffect = Fx.massiveExplosion;
                            hitShake = despawnShake = 1;
                            shake = 1;
                            spin = 4f;
                        }};
                    }}
            );
        }};
        routerBastion = new UnitType("router-bastion")
        {{
            localizedName = "Router Bastion";
            constructor = EntityMapping.map("stell");
            health = 16840;
            armor = 14;
            drag = 0.6f;
            hitSize = 8*3f;
            speed = 7 / 7.5f;
            range = 30*8;
            weapons.add(
                    new Weapon(){{
                        reload = 10;
                        inaccuracy = 10;
                        mirror = true;
                        alternate = true;
                        top = false;
                        shootY = 10;
                        shootX = 0;
                        x = 6;
                        y = 0;
                        bullet = new BasicBulletType(4,100)
                        {{
                            lifetime = PvUtil.GetRange(4,30);
                            width = height /= 2;
                            shrinkX = shrinkY = 0;
                            sprite = PvUtil.GetName("router-bullet");
                            trailWidth = 1;
                            trailLength = 10;
                            trailColor = lightColor = Pal.lancerLaser;
                            splashDamageRadius = 8*2f;
                            splashDamage = 20;
                            despawnEffect = hitEffect = Fx.massiveExplosion;
                            hitShake = despawnShake = 0.5f;
                            shake = 0.5f;
                            spin = 2f;
                            fragBullets = 1;
                            fragVelocityMax = fragVelocityMin = 0;
                            fragBullet = new BasicBulletType(0,50)
                            {{
                                lifetime = 60 * 2.6f;
                                width = height /= 2;
                                shrinkX = shrinkY = 0;
                                sprite = PvUtil.GetName("router-bullet");
                                trailWidth = 1;
                                trailLength = 10;
                                trailColor = lightColor = Pal.lancerLaser;
                                hitShake = despawnShake = 0.5f;
                            }};
                        }};
                    }},
                    new Weapon(){{
                        reload = 9;
                        inaccuracy = 10;
                        mirror = true;
                        alternate = true;
                        top = false;
                        shootY = 14;
                        shootX = 0;
                        x = 10;
                        y = 0;
                        bullet = new BasicBulletType(4,100)
                        {{
                            lifetime = PvUtil.GetRange(4,30);
                            width = height /= 2;
                            shrinkX = shrinkY = 0;
                            sprite = PvUtil.GetName("router-bullet");
                            trailWidth = 1;
                            trailLength = 10;
                            trailColor = lightColor = Pal.lancerLaser;
                            splashDamageRadius = 8*2f;
                            splashDamage = 20;
                            despawnEffect = hitEffect = Fx.massiveExplosion;
                            hitShake = despawnShake = 0.5f;
                            shake = 0.5f;
                            spin = 2f;
                            fragBullets = 1;
                            fragVelocityMax = fragVelocityMin = 0;
                            fragBullet = new BasicBulletType(0,50)
                            {{
                                lifetime = 60 * 2.6f;

                                width = height /= 2;
                                shrinkX = shrinkY = 0;
                                sprite = PvUtil.GetName("router-bullet");
                                trailWidth = 1;
                                trailLength = 10;
                                trailColor = lightColor = Pal.lancerLaser;
                                hitShake = despawnShake = 0.5f;
                            }};
                        }};
                    }},
                    new Weapon(){{
                        reload = 60;
                        inaccuracy = 5;
                        mirror = true;
                        alternate = true;
                        top = false;
                        shootY = 14;
                        shootX = 0;
                        x = 0;
                        y = 0;
                        parentizeEffects = true;
                        continuous = true;
                        cooldownTime = 60f;
                        bullet = new ContinuousLaserBulletType(){{
                            damage = 45f;
                            length = 8*34f;
                            hitEffect = Fx.hitMeltHeal;
                            drawSize = 420f;
                            lifetime = 60f * 2.5f;
                            shake = 1f;
                            despawnEffect = Fx.smokeCloud;
                            smokeEffect = Fx.none;

                            chargeEffect = Fx.greenLaserChargeSmall;

                            incendChance = 0.1f;
                            incendSpread = 5f;
                            incendAmount = 1;

                            //constant healing

                            colors = new Color[]{Pal.lancerLaser.cpy().a(.2f), Pal.lancerLaser.cpy().a(.5f), Pal.lancerLaser.cpy().mul(1.2f), Color.white};
                        }};
                    }}
            );
        }};
    }
}
