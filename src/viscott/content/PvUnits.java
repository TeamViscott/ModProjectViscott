package viscott.content;

import arc.graphics.Color;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.ExplosionEffect;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.HaloPart;
import mindustry.entities.part.RegionPart;
import mindustry.entities.part.ShapePart;
import mindustry.entities.pattern.ShootHelix;
import mindustry.gen.EntityMapping;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import viscott.abilitys.EnemyStatusFieldAbility;
import viscott.utilitys.PvUtil;

public class PvUnits {
    public static UnitType
        /*Core Units*/micro,infrared, spectrum,

        /*Flying Ion Path*/ particle, snippet, fragment, excerpt, pericope,

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
            drag = 0.15f;
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
            drag = 0.15f;
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
            drag = 0.15f;
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
                range = 48*8;
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
                    lifetime = 1;
                    trailColor = Pal.sap;
                    hitShake = 6f;
                    ammoMultiplier = 1f;
                }};
            }}
            );
        }};
        pericope = new UnitType("pericope")
        {{
            localizedName = "Pericope";
            constructor = EntityMapping.map("eclipse");
            health = 46000;
            armor = 4;
            engineSize = 10;
            engineOffset = 35;
            hitSize = 8*5;
            drag = 0.14f;
            flying = true;
            speed = 8f/7.5f;
            itemCapacity = 100;
            range = 47 * 8;
            deathExplosionEffect = PvEffects.particleDeath3;
            weapons.add(
                new Weapon("side-shooters")
                {{
                    shootSound = Sounds.shockBlast;
                    x = 0f;
                    y = -2f;
                    shootY = 0f;
                    reload = 2.5f*60f;
                    mirror = false;
                    minWarmup = 0.95f;
                    shake = 3f;
                    cooldownTime = reload - 10f;

                    bullet = new BasicBulletType(){{
                        shoot = new ShootHelix(){{
                            mag = 1f;
                            scl = 3.2f;
                        }};

                        shootEffect = new MultiEffect(Fx.shootTitan, new WaveEffect(){{
                            colorTo = Pal.sapBulletBack;
                            sizeTo = 26f;
                            lifetime = 14f;
                            strokeFrom = 4f;
                        }});
                        smokeEffect = Fx.shootSmokeTitan;
                        hitColor = Pal.sapBullet;
                        despawnSound = Sounds.spark;

                        sprite = "large-orb";
                        trailEffect = Fx.missileTrail;
                        trailInterval = 3f;
                        trailParam = 4f;
                        speed = 1.5f;
                        damage = 1300f;
                        lifetime = 120f;
                        width = height = 20f;
                        backColor = Pal.sapBulletBack;
                        frontColor = Pal.sapBullet;
                        shrinkX = shrinkY = 0f;
                        trailColor = Pal.sapBulletBack;
                        trailLength = 12;
                        trailWidth = 2.2f;
                        despawnEffect = hitEffect = new ExplosionEffect(){{
                            waveColor = Pal.sapBullet;
                            smokeColor = Color.gray;
                            sparkColor = Pal.sap;
                            waveStroke = 4f;
                            waveRad = 40f;
                        }};

                        intervalBullet = new LightningBulletType(){{
                            damage = 25;
                            collidesAir = false;
                            ammoMultiplier = 1f;
                            lightningColor = Pal.sapBullet;
                            lightningLength = 3;
                            lightningLengthRand = 6;

                            //for visual stats only.
                            buildingDamageMultiplier = 0.25f;

                            lightningType = new BulletType(0.0001f, 0f){{
                                lifetime = Fx.lightning.lifetime;
                                hitEffect = Fx.hitLancer;
                                despawnEffect = Fx.none;
                                status = StatusEffects.shocked;
                                statusDuration = 10f;
                                hittable = false;
                                lightColor = Color.white;
                                buildingDamageMultiplier = 0.25f;
                            }};
                        }};

                        bulletInterval = 0.04f*120f;

                        lightningColor = Pal.sapBullet;
                        lightningDamage = 25;
                        lightning = 3;
                        lightningLength = 2;
                        lightningLengthRand = 8;
                    }};
                }},
            new Weapon("mid-bullet")
            {{
                shootSound = Sounds.shockBlast;
                x = 0f;
                y = -2f;
                shootY = 0f;
                reload = 2.5f*60f;
                mirror = false;
                minWarmup = 0.95f;
                shake = 3f;
                cooldownTime = reload - 10f;

                bullet = new BasicBulletType(){{

                    shootEffect = new MultiEffect(Fx.shootTitan, new WaveEffect(){{
                        colorTo = Pal.sapBulletBack;
                        sizeTo = 26f;
                        lifetime = 14f;
                        strokeFrom = 4f;
                    }});
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Pal.sapBullet;
                    despawnSound = Sounds.spark;

                    sprite = "large-orb";
                    trailEffect = Fx.missileTrail;
                    trailInterval = 3f;
                    trailParam = 4f;
                    speed = 1.5f;
                    damage = 1300f;
                    lifetime = 120f;
                    width = height = 20f;
                    backColor = Pal.sapBulletBack;
                    frontColor = Pal.sapBullet;
                    shrinkX = shrinkY = 0f;
                    trailColor = Pal.sapBulletBack;
                    trailLength = 12;
                    trailWidth = 2.2f;
                    despawnEffect = hitEffect = new ExplosionEffect(){{
                        waveColor = Pal.sapBullet;
                        smokeColor = Color.gray;
                        sparkColor = Pal.sap;
                        waveStroke = 4f;
                        waveRad = 40f;
                    }};

                    intervalBullet = new LightningBulletType(){{
                        damage = 25;
                        collidesAir = false;
                        ammoMultiplier = 1f;
                        lightningColor = Pal.sapBullet;
                        lightningLength = 3;
                        lightningLengthRand = 6;

                        //for visual stats only.
                        buildingDamageMultiplier = 0.25f;

                        lightningType = new BulletType(0.0001f, 0f){{
                            lifetime = Fx.lightning.lifetime;
                            hitEffect = Fx.hitLancer;
                            despawnEffect = Fx.none;
                            status = StatusEffects.shocked;
                            statusDuration = 10f;
                            hittable = false;
                            lightColor = Color.white;
                            buildingDamageMultiplier = 0.25f;
                        }};
                    }};

                    bulletInterval = 0.04f*120f;

                    lightningColor = Pal.sapBullet;
                    lightningDamage = 25;
                    lightning = 3;
                    lightningLength = 2;
                    lightningLengthRand = 8;
                }};
            }}
            );
            abilities.add(
                    new EnemyStatusFieldAbility(PvStatusEffects.timeWarped, 1200, 60, 37*8)
            );
            parts.addAll(
                new RegionPart("-arm-1")
                {{
                    progress = PartProgress.warmup;
                    moveRot = 15;
                    mirror = true;
                    x = -20;
                    y = 15;
                    moveX = -5;
                    moveY = -2.5f;
                }},
                    new ShapePart(){{
                        progress = PartProgress.warmup;
                        rotateSpeed = -5;
                        color = Color.valueOf("875aab");
                        sides = 4;
                        hollow = true;
                        stroke = 0f;
                        strokeTo = 1.6f;
                        radius = 10f;
                        layer = Layer.effect;
                        under = false;
                    }},
                    new ShapePart(){{
                        progress = PartProgress.warmup;
                        rotateSpeed = 5;
                        color = Color.valueOf("875aab");
                        sides = 4;
                        hollow = true;
                        stroke = 0f;
                        strokeTo = 1.6f;
                        radius = 6f;
                        layer = Layer.effect;
                        under = false;
                    }},
                    new ShapePart(){{
                        progress = PartProgress.warmup;
                        rotateSpeed = -5;
                        color = Color.valueOf("875aab");
                        sides = 20;
                        hollow = true;
                        stroke = 0f;
                        strokeTo = 1.6f;
                        radius = 11f;
                        layer = Layer.effect;
                        under = false;
                    }},
                    new HaloPart(){{
                        progress = PartProgress.warmup;
                        color = Color.valueOf("875aab");
                        sides = 3;
                        hollow = true;
                        shapes = 3;
                        stroke = 0f;
                        strokeTo = 4f;
                        radius = 1f;
                        haloRadius = 13f;
                        haloRotateSpeed = 1;
                        layer = Layer.effect;
                        under = false;
                    }},
                    new ShapePart(){{
                        progress = PartProgress.warmup;
                        rotateSpeed = -5;
                        color = Color.valueOf("57d87e");
                        sides = 4;
                        hollow = true;
                        stroke = 0f;
                        strokeTo = 1.6f;
                        radius = 10f;
                        layer = Layer.effect;
                        under = false;
                    }},
                    new ShapePart(){{
                        progress = PartProgress.warmup;
                        rotateSpeed = 5;
                        color = Color.valueOf("57d87e");
                        sides = 4;
                        hollow = true;
                        stroke = 0f;
                        strokeTo = 1.6f;
                        radius = 6f;
                        layer = Layer.effect;
                        under = false;
                    }},
                    new ShapePart(){{
                        progress = PartProgress.warmup;
                        rotateSpeed = -5;
                        color = Color.valueOf("57d87e");
                        sides = 20;
                        hollow = true;
                        stroke = 0f;
                        strokeTo = 1.6f;
                        radius = 11f;
                        layer = Layer.effect;
                        under = false;
                    }},
                    new HaloPart(){{
                        progress = PartProgress.warmup;
                        color = Color.valueOf("57d87e");
                        sides = 3;
                        hollow = true;
                        shapes = 3;
                        stroke = 0f;
                        strokeTo = 4f;
                        radius = 1f;
                        haloRadius = 13f;
                        haloRotateSpeed = 1;
                        layer = Layer.effect;
                        under = false;
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
