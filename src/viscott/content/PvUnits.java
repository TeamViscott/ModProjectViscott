package viscott.content;

import arc.graphics.Color;
import arc.struct.Seq;
import arc.util.Reflect;
import mindustry.ai.types.MinerAI;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.abilities.RepairFieldAbility;
import mindustry.entities.abilities.ShieldArcAbility;
import mindustry.entities.abilities.StatusFieldAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.ExplosionEffect;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.HaloPart;
import mindustry.entities.part.HoverPart;
import mindustry.entities.part.RegionPart;
import mindustry.entities.part.ShapePart;
import mindustry.entities.pattern.ShootHelix;
import mindustry.entities.pattern.ShootSpread;
import mindustry.game.Team;
import mindustry.gen.EntityMapping;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.PayloadStack;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.ItemAmmoType;
import viscott.abilitys.EnemyStatusFieldAbility;
import viscott.types.BuildUnitType;
import viscott.types.GridUnitType;
import viscott.types.NullisUnitType;
import viscott.types.PvUnitType;
import viscott.types.abilities.DamageAbility;
import viscott.types.abilities.VoidAbility;
import viscott.world.bullets.VoidBulletType;
import viscott.utilitys.PvUtil;

import static mindustry.Vars.tilePayload;

public class PvUnits {
    public static UnitType
        /*Core Units*/micro,infrared, spectrum,
            shadow,proton,vessel,
            amp,volt,watt,

        /*Flying Ion Path*/ particle, snippet, fragment, excerpt, pericope,

        /*Rocket Hover Path*/milli,centi,deci,

        /*Xeal Naval Path*/rivulet,bourn,tributary,loch,atlantic,

        /*Nullis*/
            /*Storage Con Path*/pocket,container, capsule,vault,chamber,

        /*Extra Paths : */
        routerTank, routerBastion, box,blockHost,

                /*BOSSES*/
                    vdoble,charlie
                ;
    public static void load()
    {
        //Base UnitLoads
        loadFlyingIonPath();
        loadRocketHoverPath();
        //Faction Based UnitLoads
        loadXeonNavalPath();
        loadStoragePath();
        //Extra UnitLoads
        loadCorePath();
        loadExtra();
        loadBosses();
    }
    public static void loadCorePath()
    {
        micro = new PvUnitType("micro")
        {{
            localizedName = "Micro";
            constructor = EntityMapping.map("alpha");
            health = 175;
            armor = 0;
            flying = true;
            buildSpeed = 0.75f;
            mineTier = 1;
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
        infrared = new PvUnitType("infrared")
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
                            buildingDamageMultiplier = 0.1f;
                            lifetime = PvUtil.GetRange(this.speed,17);
                            trailColor = lightColor = backColor = Pal.engine;
                            despawnShake = hitShake = 0.5f;
                            fragBullets = 2;
                            fragRandomSpread = 0;
                            splashDamage = 10;
                            splashDamageRadius = 16;
                            hitEffect = Fx.explosion;
                            fragSpread = 180;
                            fragAngle = 90;
                            fragVelocityMin = fragVelocityMax = 1;
                            fragBullet = new LaserBoltBulletType(2,6)
                            {{
                                homingPower = 0.01f;
                                trailLength = 20;
                                trailWidth = 2;
                                homingDelay = 1;
                                homingRange = 8*17f;
                                buildingDamageMultiplier = 0.1f;
                                lifetime = PvUtil.GetRange(this.speed,8);
                                trailColor = lightColor = backColor = Pal.engine;
                                despawnShake = hitShake = 0.5f;
                                fragBullets = 1;
                                fragRandomSpread = 0;
                                fragAngle = 180;
                                fragVelocityMin = fragVelocityMax = 1;
                                fragBullet = new LaserBoltBulletType(2,6)
                                {{
                                    homingPower = 0.015f;
                                    trailLength = 20;
                                    trailWidth = 2;
                                    homingDelay = 1;
                                    homingRange = 8*17f;
                                    buildingDamageMultiplier = 0.1f;
                                    lifetime = PvUtil.GetRange(this.speed,10);
                                    trailColor = lightColor = backColor = Pal.engine;
                                    despawnShake = hitShake = 0.5f;
                                }};
                            }};
                        }};
                    }}
            );
        }};
        spectrum = new PvUnitType("spectrum")
        {{
            localizedName = "Spectrum";
            constructor = EntityMapping.map("alpha");
            health = 250;
            armor = 0;
            flying = true;
            buildSpeed = 1.15f;
            mineTier = 3;
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

        //Nullis


        shadow = new NullisUnitType("shadow")
        {{
            health = 0.5f;
            abilities.add(new DamageAbility(0.05f/60f));
            localizedName = "Shadow";
            constructor = EntityMapping.map("mono");
            controller = a -> new MinerAI();
            armor = 0;
            flying = true;
            buildSpeed = 1.2f;
            mineTier = 2;
            engineColor = Color.black;
            mineSpeed = 3f;
            itemCapacity = 40;
            speed = 30.8f / 7.5f;
            healColor = Color.black;
            drag = 0.3f;
            range = 10*8;
            weapons.add(
                    new Weapon()
                    {{
                        x = 0;
                        reload = 60f;
                        shoot.shots = 2;
                        shoot.shotDelay = 20;
                        rotate = true;
                        rotationLimit = 30;
                        bullet = new VoidBulletType(1,20)
                        {{
                            recoil = 2f;
                            homingPower = 0.01f;
                            trailLength = 20;
                            trailWidth = 2;
                            lifetime = 2;
                            homingDelay = 1;
                            homingRange = 8*17f;
                        }};
                    }}
            );
        }};
        vessel = new NullisUnitType("vessel")
        {{
            localizedName = "Vessel";
            constructor = EntityMapping.map("beta");
            health = 320;
            armor = 0;
            flying = true;
            buildSpeed = 1.2f;
            mineTier = 2;
            mineSpeed = 7f;
            itemCapacity = 50;
            speed = 22.8f / 7.5f;
            engineColor = Color.black;
            healColor = Color.black;
            drag = 0.1f;
            range = 20*8;
            weapons.add(
                    new Weapon()
                    {{
                        x = 0;
                        reload = 60f;
                        shoot.shots = 3;
                        shoot.shotDelay = 10;
                        inaccuracy = 4;
                        rotate = true;
                        rotationLimit = 30;
                        bullet = new VoidBulletType(6,30)
                        {{
                            recoil = 0.5f;
                            homingPower = 0.01f;
                            trailLength = 20;
                            trailWidth = 2;
                            lifetime = 2;
                            homingDelay = 1;
                            homingRange = 8*17f;
                        }};
                    }}
            );
        }};
        //Xeal
        amp = new PvUnitType("amp")
        {{
            localizedName = "Amp";
            constructor = EntityMapping.map("alpha");
            description = "A small core unit created by the Xeal faction. Specializes in base building and support.";
            health = 130;
            armor = 0;
            flying = true;
            strafePenalty = 0.2f;
            buildSpeed = 1.5f;
            rotateMoveFirst = true;
            mineTier = 1;
            mineSpeed = 5f;
            itemCapacity = 60;
            speed = 25.2f / 7.5f;
            drag = 0.1f;
            range = 17*8;
            weapons.add(
                    new Weapon()
                    {{
                        mirror = true;
                        top = false;
                        x = 4;
                        y = 2;
                        reload = 60f/1.8f;
                        alternate = false;
                        bullet = new LaserBoltBulletType(8,10)
                        {{
                            buildingDamageMultiplier = 0.01f;
                            trailColor = backColor = lightColor = Pal.heal;
                            trailWidth = 2;
                            knockback = 2.5f;
                            trailLength = 25;
                            hitEffect = Fx.hitLancer;
                            lifetime = PvUtil.GetRange(8,21);
                            status = StatusEffects.shocked;
                            statusDuration = 60;
                        }};
                    }}
            );
        }};
        volt = new PvUnitType("volt")
        {{
            localizedName = "Volt";
            constructor = EntityMapping.map("alpha");
            description = "A mid sized core unit created by the Xeal faction. Specializes in base building and support.";
            health = 140;
            armor = 0;
            flying = true;
            strafePenalty = 0.2f;
            buildSpeed = 1.75f;
            rotateMoveFirst = true;
            mineTier = 1;
            mineSpeed = 6f;
            itemCapacity = 80;
            speed = 26.8f / 7.5f;
            drag = 0.1f;
            range = 17*8;
            weapons.add(
                    new Weapon()
                    {{
                        mirror = true;
                        top = false;
                        x = 4;
                        y = 2;
                        reload = 60f/1.8f;
                        alternate = false;
                        bullet = new LaserBoltBulletType(10,14)
                        {{
                            buildingDamageMultiplier = 0.01f;
                            trailColor = backColor = lightColor = Pal.heal;
                            trailWidth = 2;
                            knockback = 3f;
                            trailLength = 25;
                            hitEffect = Fx.hitLancer;
                            lifetime = PvUtil.GetRange(10,25);
                            status = StatusEffects.shocked;
                            statusDuration = 60;
                        }};
                    }}
            );
        }};
        watt = new PvUnitType("watt")
        {{
            localizedName = "Watt";
            constructor = EntityMapping.map("alpha");
            health = 160;
            armor = 0;
            flying = true;
            strafePenalty = 0.2f;
            buildSpeed = 2.25f;
            rotateMoveFirst = true;
            mineTier = 2;
            mineSpeed = 7.5f;
            itemCapacity = 100;
            speed = 26.8f / 7.5f;
            drag = 0.1f;
            range = 17*8;
            weapons.add(
                    new Weapon()
                    {{
                        mirror = true;
                        top = false;
                        x = 4;
                        y = 2;
                        reload = 60f/1.8f;
                        alternate = false;
                        bullet = new LaserBoltBulletType(14,22)
                        {{
                            buildingDamageMultiplier = 0.01f;
                            trailColor = backColor = lightColor = Pal.heal;
                            trailWidth = 2;
                            knockback = 5f;
                            trailLength = 35;
                            lifetime = PvUtil.GetRange(14,25);
                            status = StatusEffects.shocked;
                            statusDuration = 60;
                            homingPower = 0.02f;
                            weaveMag = 1;
                            weaveScale = 1;
                            weaveRandom = true;
                            fragBullets = 3;
                            hitEffect = Fx.hitLancer;
                            fragBullet = new LightningBulletType() {{
                                damage = 10;
                                lightningLength = 8;
                                collidesAir = true;
                            }};
                        }};
                    }}
            );
        }};
    }
    public static void loadFlyingIonPath()
    {
        particle = new PvUnitType("particle")
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

        snippet = new PvUnitType("snippet")
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
        fragment = new PvUnitType("fragment")
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
            lowAltitude = true;
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
        excerpt = new PvUnitType("excerpt")
        {{
            localizedName = "Excerpt";
            constructor = EntityMapping.map("mega");
            health = 15000;
            armor = 4;
            hitSize = 8;
            drag = 0.02f;
            lowAltitude = true;
            flying = true;
            engineOffset = 18;
            engineSize = 10;
            speed = 14f/7.5f;
            accel = 0.1f;
            itemCapacity = 25;
            canBoost = false;
            range = 48 * 8;
            deathExplosionEffect = PvEffects.particleDeath3;
            weapons.add(
                    new Weapon(PvUtil.GetName("excerpt-minigun"))
                    {{
                        x = 8;
                        y = -4;
                        shoot.shots = 4;
                        shoot.shotDelay = 2.5f;
                        reload = 60/1.8f;
                        mirror = true;
                        recoil = 3;
                        inaccuracy = 5;
                        bullet = new LaserBoltBulletType(5.2f, 67){{
                            lifetime = 60f;
                            trailWidth = 1.8f;
                            trailLength = 20;
                            healPercent = 5f;
                            despawnEffect = hitEffect = Fx.hitFuse;
                            collidesTeam = true;
                            trailColor = lightColor = backColor = Pal.sap;
                            frontColor = Pal.sap;

                        }};
                    }},
            new Weapon(PvUtil.GetName("excerpt-rail-gun"))
            {{
                x = 0;
                y = 0;
                range = 48*8;
                reload = 120;
                mirror = false;
                recoil = 3;
                top = true;
                inaccuracy = 0;
                bullet = new RailBulletType(){{
                    recoil = 2;
                    damage = 1350;
                    buildingDamageMultiplier = 0.2f;
                    hitShake = 6f;
                    shootEffect = Fx.lancerLaserShoot;
                    length = 48*8;
                    pointEffectSpace = 60f;
                    pierceEffect = Fx.railHit;
                    pointEffect = PvEffects.railFrag;
                    hitEffect = Fx.massiveExplosion;
                    smokeEffect = Fx.shootBig2;
                    lightningColor = trailColor = lightColor = Pal.sap;
                    pierceCap = 10;
                    pierceDamageFactor = 0.5f;
                }};
            }}
            );
        }};
        pericope = new PvUnitType("pericope")
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
                            mag = 1.5f;
                            scl = 8f;
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
                        speed = 3f;
                        damage = 1300f;
                        lifetime = 180f;
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
                shootStatus = StatusEffects.slow;
                shootStatusDuration = 60f;
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
                    speed = 3f;
                    damage = 1300f;
                    lifetime = 185f;
                    width = height = 22f;
                    backColor = Pal.sapBulletBack;
                    frontColor = Pal.sapBullet;
                    shrinkX = shrinkY = 0f;
                    trailColor = Pal.sapBulletBack;
                    trailLength = 24;
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
                    new EnemyStatusFieldAbility(PvStatusEffects.timeWarped, 60*5, 90, 37*8)
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
                    children.add(
                            new RegionPart("-arm-2")
                            {{
                                progress = PartProgress.reload;
                                mirror = true;
                                y = 5;
                                x = -2.5f;
                                moveY = -5f;
                                moveX = -5f;
                            }}
                    );
                }},
                    new ShapePart(){{
                        progress = PartProgress.warmup;
                        rotateSpeed = 5;
                        color = Pal.sap;
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
                        color = Pal.sap;
                        sides = 20;
                        hollow = true;
                        stroke = 0f;
                        strokeTo = 1.6f;
                        radius = 7f;
                        layer = Layer.effect;
                        under = false;
                    }},
                    new HaloPart(){{
                        progress = PartProgress.warmup;
                        color = Pal.sap;
                        sides = 3;
                        hollow = true;
                        shapes = 3;
                        stroke = 0f;
                        strokeTo = 4f;
                        radius = 1.4f;
                        haloRadius = 9f;
                        haloRotateSpeed = 1;
                        layer = Layer.effect;
                        under = false;
                    }},
                    new ShapePart(){{
                        progress = PartProgress.warmup;
                        rotateSpeed = -5;
                        color = Pal.sap;
                        sides = 4;
                        hollow = true;
                        stroke = 0f;
                        strokeTo = 1.6f;
                        radius = 10f;
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
        routerTank = new PvUnitType("router-tank")
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
        routerBastion = new PvUnitType("router-bastion")
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
        box = new PvUnitType("the-box")
        {{
            localizedName = "THE BOX";
            constructor = EntityMapping.map("stell");
            health = 150;
            armor = 12;
            rotateSpeed = 0;
            drag = 0.5f;
            speed = 0;
            canBoost = true;
            hitSize = 8*2f;
            range = 20*8;
            weapons.add(
                    new Weapon(){{
                        reload = 60;
                        inaccuracy = 0;
                        mirror = false;
                        top = false;
                        rotate = true;
                        shootY = 0;
                        shootX = 0;
                        x = 0;
                        y = 0;
                        bullet = new BasicBulletType(6,50)
                        {{
                            lifetime = PvUtil.GetRange(6,20);
                            sprite = PvUtil.GetName("router-bullet");
                            trailWidth = 5;
                            trailLength = 40;
                            trailColor = lightColor = Pal.lancerLaser;
                            splashDamageRadius = 8*4f;
                            splashDamage = 700;
                            despawnEffect = hitEffect = Fx.massiveExplosion;
                            hitShake = despawnShake = 1;
                            shake = 1;
                            spin = 4f;

                        }};

                    }}

            );
            abilities.add(new ShieldArcAbility(){{
                region = "tecta-shield";
                radius = 34f;
                angle = 82f;
                angleOffset = 0;
                regen = 1f;
                cooldown = 60f * 12f;
                max = 10000f;
                y = -20f;
                width = 6f;
            }});
            abilities.add(new ShieldArcAbility(){{
                region = "tecta-shield";
                radius = 34f;
                angle = 82f;
                angleOffset = 90;
                regen = 1f;
                cooldown = 60f * 12f;
                max = 5000f;
                x = -20f;
                width = 6f;
            }});
            abilities.add(new ShieldArcAbility(){{
                region = "tecta-shield";
                radius = 34f;
                angle = 82f;
                angleOffset = 180;
                regen = 1f;
                cooldown = 60f * 12f;
                max = 5000f;
                y = 20f;
                width = 6f;
            }});
            abilities.add(new ShieldArcAbility(){{
                region = "tecta-shield";
                radius = 34f;
                angle = 82f;
                angleOffset = -90;
                regen = 1f;
                cooldown = 60f * 12f;
                max = 5000f;
                x = 20f;
                width = 6f;
            }});
        }};
        blockHost = new BuildUnitType("block-host")
        {{
            localizedName = "Block Host";
            constructor = EntityMapping.map("quad");
            health = 1000;
            armor = 10;
            flying = true;
            hitSize = 1;
            engineColor = Color.black;
            itemCapacity = 3000;
            speed = 3f / 7.5f;
            drag = 0.05f;
            lowAltitude = true;
            pickupUnits = true;
            payloadCapacity = tilePayload * (9 * 9);
            range = 12*8;
        }};
    }
    public static void loadStoragePath()
    {
        pocket = new NullisUnitType("pocket")
        {{
            localizedName = "Pocket";
            constructor = EntityMapping.map("dagger");
            health = 200;
            armor = 2;
            flying = false;
            canBoost = true;
            engineColor = Color.black;
            buildSpeed = 0.01f;
            itemCapacity = 80;
            speed = 4f / 7.5f / 60;
            boostMultiplier = 60;
            drag = 0.2f;
            range = 10*8;
            engineSize = 0;
            engines = Seq.with(
                    new UnitEngine(6,6,4,45),
                    new UnitEngine(-6,6,4,90+45),
                    new UnitEngine(-6,-6,4,180+45),
                    new UnitEngine(6,-6,4,270+45)
            );
        }};
        container = new NullisUnitType("container")
        {{
            localizedName = "Container";
            constructor = EntityMapping.map("dagger");
            health = 1800;
            armor = 4;
            flying = false;
            hitSize = 8*2;
            canBoost = true;
            engineColor = Color.black;
            itemCapacity = 300;
            speed = 3f / 7.5f / 60;
            boostMultiplier = 40;
            drag = 0.05f;
            range = 12*8;
            engineSize = 0;
            engines = Seq.with(
                    new UnitEngine(10,10,6,45),
                    new UnitEngine(-10,10,6,90+45),
                    new UnitEngine(-10,-10,6,180+45),
                    new UnitEngine(10,-10,6,270+45)
            );
        }};
        capsule = new NullisUnitType("capsule")
        {{
            localizedName = "Capsule";
            constructor = EntityMapping.map("dagger");
            health = 5200;
            armor = 8;
            flying = false;
            hitSize = 8*4;
            canBoost = true;
            engineColor = Color.black;
            itemCapacity = 1200;
            speed = 2f / 7.5f / 60;
            boostMultiplier = 40;
            drag = 0.05f;
            range = 12*8;
            engineSize = 0;
            engines = Seq.with(
                    new UnitEngine(14,14,8,45),
                    new UnitEngine(-14,14,8,90+45),
                    new UnitEngine(-14,-14,8,180+45),
                    new UnitEngine(14,-14,8,270+45)
            );
        }};
        vault = new BuildUnitType("vault")
        {{
            localizedName = "Vault";
            description = "First in its kind to link a picked up Turret to the Main Item Feed. With this any Turret that requires items will be able to function while on the Unit.";
            constructor = EntityMapping.map("quad");
            health = 25600;
            deathExplosionEffect = PvEffects.nullisDeath.get(7);
            armor = 15;
            factions.add(PvFactions.Nullis);
            flying = true;
            hitSize = 8*8;
            healColor = Color.black;
            engineColor = Color.black;
            itemCapacity = 3000;
            speed = 2f / 7.5f;
            drag = 0.03f;
            lowAltitude = true;
            pickupUnits = true;
            payloadCapacity = tilePayload * (2 * 2);
            range = 12*8;
            engineSize = 0;
            /*
            engines = Seq.with(
                    new UnitEngine(26,26,12,45),
                    new UnitEngine(-26,26,12,90+45),
                    new UnitEngine(-26,-26,12,180+45),
                    new UnitEngine(26,-26,12,270+45)
            );
            */
            for(float f : new int[]{0,1}){
                parts.add(new HoverPart(){{
                    x = 22f;
                    y = f%2==0 ? 22f : -22f;
                    mirror = true;
                    radius = 22f;
                    phase = 50f;
                    stroke = 4f;
                    layerOffset = -0.001f;
                    color = Team.green.color;
                }});
            }
        }};
        chamber = new GridUnitType("chamber")
        {{
            localizedName = "Chamber";
            description = "[#444444]Where the [crimson]End[] Begins...";
            constructor = EntityMapping.map("quad");
            health = 54200;
            deathExplosionEffect = PvEffects.nullisDeath.get(7);
            factions.add(PvFactions.Nullis);
            armor = 22;
            drownTimeMultiplier = 30;
            rotateSpeed = 90/60;
            canBoost = true;
            hitSize = 16*8;
            buildSize = 10;
            buildArea = new byte[][]{
                    {0,0,0,0,0,0,0,0,0,0},
                    {0,0,1,1,0,0,0,0,0,0},
                    {0,1,1,1,1,1,0,0,0,0},
                    {1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,0,0,1,1,1,1},
                    {1,1,1,1,0,0,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1},
                    {0,1,1,1,1,1,0,0,0,0},
                    {0,0,1,1,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0}
            };
            healColor = Color.black;
            engineColor = Color.black;
            itemCapacity = 10000;
            speed = 0.004f / 7.5f;
            boostMultiplier = 400;
            drag = 0.03f;
            lowAltitude = true;
            range = 12*8;
            engineSize = 0;
            abilities.add(new VoidAbility(18*8));
            engines = Seq.with(
                    new UnitEngine(34,12,10,45),
                    new UnitEngine(-34,12,10,90+45)
            );
            for(float f : new int[]{0,1}){
                parts.add(new HoverPart(){{
                    x = 50f;
                    y = f%2==0 ? 50f : -50f;
                    mirror = true;
                    radius = 30f;
                    phase = 50f;
                    stroke = 4f;
                    layerOffset = -0.001f;
                    color = Team.green.color;
                }});
            }
        }};
    }
    public static void loadBosses()
    {
        vdoble = new PvUnitType("vdoble")
        {{
            localizedName = "Vdoble";
            constructor = EntityMapping.map("toxopid");
            drag = 0.1f;
            speed = 3.2f/7.5f;
            hitSize = 26f;
            health = 110000;
            armor = 20f;
            lightRadius = 140f;

            rotateSpeed = 1.9f;
            drownTimeMultiplier = 3f;

            legCount = 12;
            legMoveSpace = 0.8f;
            legPairOffset = 3;
            legLength = 90f;
            legExtension = -20;
            legBaseOffset = 8f;
            stepShake = 1f;
            legLengthScl = 0.93f;
            rippleScale = 3f;
            legSpeed = 0.19f;
            ammoType = new ItemAmmoType(Items.graphite, 8);

            deathExplosionEffect = PvEffects.nullisDeath.get(3);
            legSplashDamage = 80;
            legSplashRange = 60;


            abilities.add(new ShieldArcAbility(){{
                region = "tecta-shield";
                radius = 40f;
                angle = 120f;
                regen = 30f/60f;
                cooldown = 60f * 6f;
                max = 3000f;
                y = -20f;
                width = 6f;
            }});
            hovering = true;
            shadowElevation = 0.95f;
            groundLayer = Layer.legUnit;

            weapons.add(
                    new Weapon(PvUtil.GetName("large-purple-mount-vdoble")){{
                        y = -5f;
                        x = 13f;
                        shootY = 7f;
                        reload = 60;
                        shake = 4f;
                        rotateSpeed = 2f;
                        ejectEffect = Fx.casing1;
                        shootSound = Sounds.shootBig;
                        rotate = true;
                        shadow = 12f;
                        recoil = 3f;

                        shoot = new ShootSpread(5, 11f);

                        bullet = new ShrapnelBulletType(){{
                            length = 90f;
                            damage = 220f;
                            width = 25f;
                            serrationLenScl = 7f;
                            serrationSpaceOffset = 60f;
                            serrationFadeOffset = 0f;
                            serrations = 10;
                            serrationWidth = 6f;
                            fromColor = Pal.sapBullet;
                            toColor = Pal.sapBulletBack;
                            shootEffect = smokeEffect = Fx.sparkShoot;
                        }};
                    }});

            weapons.add(new Weapon(PvUtil.GetName("vdoble-cannon")){{
                y = -14f;
                x = 0f;
                shootY = 22f;
                mirror = false;
                reload = 300;
                shake = 10f;
                recoil = 10f;
                rotateSpeed = 1f;
                ejectEffect = Fx.casing3;
                shootSound = Sounds.artillery;
                rotate = true;
                shadow = 30f;

                rotationLimit = 80f;

                bullet = new ArtilleryBulletType(5f, 80){{
                    hitEffect = Fx.sapExplosion;
                    knockback = 0.8f;
                    lifetime = 48f;
                    width = height = 25f;
                    collidesTiles = collides = true;
                    ammoMultiplier = 4f;
                    splashDamageRadius = 80f;
                    splashDamage = 75f;
                    backColor = Pal.sapBulletBack;
                    frontColor = lightningColor = Pal.sapBullet;
                    lightning = 8;
                    lightningLength = 25;
                    smokeEffect = Fx.shootBigSmoke2;
                    hitShake = 10f;
                    lightRadius = 40f;
                    lightColor = Pal.sap;
                    lightOpacity = 0.6f;

                    status = StatusEffects.sapped;
                    statusDuration = 60f * 10;

                    fragLifeMin = 0.3f;
                    fragBullets = 9;

                    fragBullet = new ArtilleryBulletType(4.3f, 50){{
                        hitEffect = Fx.sapExplosion;
                        knockback = 0.8f;
                        lifetime = 60f;
                        width = height = 20f;
                        collidesTiles = false;
                        splashDamageRadius = 70f;
                        splashDamage = 40f;
                        backColor = Pal.sapBulletBack;
                        frontColor = lightningColor = Pal.sapBullet;
                        lightning = 2;
                        lightningLength = 5;
                        smokeEffect = Fx.shootBigSmoke2;
                        hitShake = 5f;
                        lightRadius = 30f;
                        lightColor = Pal.sap;
                        lightOpacity = 0.5f;

                        status = StatusEffects.sapped;
                        statusDuration = 60f * 10;
                        fragBullet = new ArtilleryBulletType(3.3f, 30){{
                            hitEffect = Fx.sapExplosion;
                            knockback = 0.8f;
                            lifetime = 15f;
                            width = height = 20f;
                            collidesTiles = false;
                            splashDamageRadius = 70f;
                            splashDamage = 40f;
                            backColor = Pal.sapBulletBack;
                            frontColor = lightningColor = Pal.sapBullet;
                            lightning = 2;
                            lightningLength = 5;
                            smokeEffect = Fx.shootBigSmoke2;
                            hitShake = 5f;
                            lightRadius = 30f;
                            lightColor = Pal.sap;
                            lightOpacity = 0.5f;

                            status = StatusEffects.sapped;
                            statusDuration = 60f * 10;
                        }};
                    }};
                }};
            }});
            weapons.add(new Weapon(PvUtil.GetName("vdoble-cannon")){{
                y = -18f;
                x = 6f;
                shootY = 22f;
                mirror = true;
                reload = 220;
                shake = 10f;
                recoil = 10f;
                rotateSpeed = 1f;
                ejectEffect = Fx.casing3;
                shootSound = Sounds.artillery;
                rotate = true;
                shadow = 30f;

                rotationLimit = 80f;

                bullet = new ArtilleryBulletType(3f, 80){{
                    hitEffect = Fx.sapExplosion;
                    knockback = 0.8f;
                    lifetime = 80f;
                    width = height = 25f;
                    collidesTiles = collides = true;
                    ammoMultiplier = 4f;
                    splashDamageRadius = 80f;
                    splashDamage = 75f;
                    backColor = Pal.sapBulletBack;
                    frontColor = lightningColor = Pal.sapBullet;
                    lightning = 8;
                    lightningLength = 25;
                    smokeEffect = Fx.shootBigSmoke2;
                    hitShake = 10f;
                    lightRadius = 40f;
                    lightColor = Pal.sap;
                    lightOpacity = 0.6f;

                    status = StatusEffects.sapped;
                    statusDuration = 60f * 10;

                    fragLifeMin = 0.3f;
                    fragBullets = 9;

                    fragBullet = new ArtilleryBulletType(3.3f, 50){{
                        hitEffect = Fx.sapExplosion;
                        knockback = 0.8f;
                        lifetime = 90f;
                        width = height = 20f;
                        collidesTiles = false;
                        splashDamageRadius = 70f;
                        splashDamage = 40f;
                        backColor = Pal.sapBulletBack;
                        frontColor = lightningColor = Pal.sapBullet;
                        lightning = 2;
                        lightningLength = 5;
                        smokeEffect = Fx.shootBigSmoke2;
                        hitShake = 5f;
                        lightRadius = 30f;
                        lightColor = Pal.sap;
                        lightOpacity = 0.5f;

                        status = StatusEffects.sapped;
                        statusDuration = 60f * 10;
                    }};
                }};
            }});
        }};
        charlie = new PvUnitType("charlie")
        {
            {
                localizedName = "Choo Choo Charlie";
                constructor = EntityMapping.map("toxopid");
                drag = 0.1f;
                speed = 35f / 7.5f;
                hitSize = 8*8f;
                health = 200000;
                armor = 50f;
                lightRadius = 140f;
                omniMovement = false;
                rotateSpeed = 1.9f;
                drownTimeMultiplier = 3f;

                legCount = 8;
                legMoveSpace = 0.8f;
                legPairOffset = 3;
                legLength = 90f;
                legExtension = -20;
                legBaseOffset = 8f;
                stepShake = 1f;
                legLengthScl = 0.93f;
                rippleScale = 3f;
                legSpeed = 0.19f;
                ammoType = new ItemAmmoType(Items.graphite, 8);

                deathExplosionEffect = PvEffects.nullisDeath.get(7);
                legSplashDamage = 200;
                legSplashRange = 60;

                hovering = true;
                shadowElevation = 0.95f;
                groundLayer = Layer.legUnit;

                weapons.add(
                        new Weapon() {{
                            y = 55f;
                            x = 0f;
                            reload = 120;
                            top = false;
                            mirror = false;
                            shake = 4f;
                            rotateSpeed = 2f;
                            ejectEffect = Fx.casing1;
                            shootSound = Sounds.shootBig;
                            rotate = true;
                            recoil = 3f;

                            shoot = new ShootSpread(4, 8.5f);

                            bullet = new ShrapnelBulletType() {{
                                length = 90f;
                                damage = 110f;
                                width = 25f;
                                serrationLenScl = 7f;
                                serrationSpaceOffset = 60f;
                                serrationFadeOffset = 0f;
                                serrations = 10;
                                serrationWidth = 6f;
                                fromColor = Pal.redLight;
                                toColor = Pal.redderDust;
                                shootEffect = smokeEffect = Fx.sparkShoot;
                                fragBullets = 8;
                                hitShake = despawnShake = 0.5f;
                                fragLifeMax = 1;
                                fragLifeMin = 0.8f;
                                fragBullet = new BulletType(8,60)
                                {{
                                    lifetime = 60;
                                    fromColor = Pal.redLight;
                                    toColor = Pal.redderDust;
                                    hitShake = despawnShake = 0.5f;
                                    fragBullets = 8;
                                    fragLifeMax = 1;
                                    fragLifeMin = 0.8f;
                                    fragBullet = new BulletType(8,60)
                                    {{
                                        lifetime = 60;
                                        fromColor = Pal.redLight;
                                        toColor = Pal.redderDust;
                                        hitShake = despawnShake = 0.5f;
                                    }};
                                }};
                            }};
                        }});
        }};
    }
    public static void loadRocketHoverPath() {
        milli = new PvUnitType("milli") {{
            localizedName = "Milli";
            constructor = EntityMapping.map("ElevationMoveUnit");
            speed = 12.3f/7.5f;
            engineOffset = 8;
            drag = 0.13f;
            buildSpeed = 2.2f;
            buildBeamOffset = 0;
            hitSize = 10f;
            health = 275;
            armor = 3;
            range = 8 * 26;
            accel = 0.4f;
            rotateSpeed = 3.3f;
            faceTarget = true;
            hovering = true;
            parts.add(new HoverPart(){{
                x = 0f;
                y = 0f;
                mirror = false;
                radius = 18f;
                phase = 60f;
                stroke = 5f;
                layerOffset = -0.001f;
                color = Pal.neoplasmMid;
            }});

            weapons.add(new Weapon(name+"-gun"){{
                reload = 60f/0.8f;
                x = -5f;
                shootY = 0f;
                y = 0f;
                rotate = true;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(3f, 14){{
                    width = 12f;
                    height = 15f;
                    homingPower = 0.01f;
                    lifetime = PvUtil.GetRange(3,26);
                    trailColor = backColor = lightColor = Pal.neoplasm1;
                    frontColor = Pal.neoplasm2;
                    trailLength = 24;
                    trailChance = 0.1f;
                    trailWidth = 1.4f;
                    splashDamage = 15;
                    splashDamageRadius = 8*1.5f;
                    ammoMultiplier = 2;
                    despawnEffect = hitEffect = new MultiEffect(Fx.explosion,Fx.smokeCloud);
                }};
            }});
        }};
        centi = new PvUnitType("centi") {{
            localizedName = "Centi";
            constructor = EntityMapping.map("ElevationMoveUnit");
            speed = 9.8f;
            engineOffset = 8;
            drag = 0.13f;
            buildSpeed = 2.2f;
            buildBeamOffset = 0;
            hitSize = 10f;
            health = 925;
            armor = 5;
            range = 29;
            accel = 0.4f;
            rotateSpeed = 3.3f;
            faceTarget = true;
            hovering = true;
            parts.add(
            new HoverPart(){{
               x = 8f;
               y = 8f;
               mirror = true;
               radius = 8f;
               phase = 60f;
               stroke = 5f;
               layerOffset = -0.001f;
               color = Pal.neoplasmMid;
            }},
            new HoverPart(){{
                x = -8f;
                y = -8f;
                mirror = true;
                radius = 8;
                phase = 60f;
                stroke = 5f;
                layerOffset = -0.001f;
                color = Pal.neoplasmMid;
            }});  
            

            weapons.add(new Weapon(name+"-gun"){{
                reload = 27;
                x = 0f;
                shootY = 0f;
                y = -1f;
                rotate = true;
                mirror = false;
                ejectEffect = Fx.casing1;
                recoil = 0f;
                shoot = new ShootAlternate(0.5f);
                bullet = new MissileBulletType(3f, 12){{
                    width = 1f;
                    height = 4.3f;
                    homingPower = 0.01f;
                    lifetime = PvUtil.GetRange(3,26);
                    trailColor = backColor = lightColor = Pal.neoplasm1;
                    frontColor = Pal.neoplasm2;
                    trailLength = 12;
                    trailChance = 0.1f;
                    trailWidth = 0.6f;
                    splashDamage = 6;
                    splashDamageRadius = 10f;
                    ammoMultiplier = 2;
                    despawnEffect = hitEffect = new MultiEffect(Fx.explosion,Fx.smokeCloud);
                }};
                parts.add(
                new RegionPart("-barrel-l"){{
                    progress = PartProgress.recoil;
                    heatProgress = PartProgress.recoil;
                    heatColor = Pal.neoplasm1;
                    mirror = false;
                    under = true;
                    moveX = 0;
                    moveY = -0.5f;
                }},
                new RegionPart("-barrel-r"){{
                    progress = PartProgress.recoil.delay(27f);
                    heatProgress = PartProgress.recoil.delay(27f);
                    heatColor = Pal.neoplasm1;
                    mirror = false;
                    under = true;
                    moveX = 0;
                    moveY = -0.5f;
                }});
            }});
        }};
    }

    public static void loadXeonNavalPath()
    {
        rivulet = new PvUnitType("rivulet")
        {{
            localizedName = "Rivulet";
            factions.add(PvFactions.Xeal);
            constructor = EntityMapping.map("risso");
            canBoost = true;
            speed = 8.5f/7.5f;
            engineOffset = 8;
            drag = 0.13f;
            buildSpeed = 2.2f;
            buildBeamOffset = 0;
            abilities.add(new RepairFieldAbility(10,120,8*12));
            hitSize = 10f;
            health = 380;
            armor = 2;
            accel = 0.4f;
            rotateSpeed = 3.3f;
            faceTarget = false;

            weapons.add(new Weapon(name+"-gun"){{
                reload = 60f/1.8f;
                shoot.shots = 2;
                shoot.shotDelay = 10;
                x = 5f;
                shootY = 4f;
                y = 0f;
                rotate = true;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(3f, 6){{
                    width = 7f;
                    height = 9f;
                    lifetime = PvUtil.GetRange(3,19);
                    trailColor = backColor = lightColor = Pal.heal;
                    trailLength = 30;
                    trailWidth = 1.4f;
                    healPercent = 3;
                    collidesTeam = true;
                    ammoMultiplier = 2;
                }};
            }});
        }};
        bourn = new PvUnitType("bourn")
        {{
            localizedName = "Bourn";
            factions.add(PvFactions.Xeal);
            constructor = EntityMapping.map("risso");
            canBoost = true;
            speed = 8.5f/7.5f;
            engineOffset = 8;
            drag = 0.13f;
            buildSpeed = 2.2f;
            buildBeamOffset = 0;
            abilities.add(new RepairFieldAbility(6,300,8*8),
                    new StatusFieldAbility(PvStatusEffects.frag,240,180,8*15)
                    );
            hitSize = 10f;
            health = 880;
            armor = 4;
            accel = 0.4f;
            rotateSpeed = 3.3f;
            faceTarget = false;

            weapons.add(new Weapon(name+"-turret"){{
                reload = 60f/1.6f;
                shoot = new ShootSpread(4,0);
                x = 0f;
                shootY = 2f;
                inaccuracy = 20;
                y = -4f;
                recoil = 2;
                rotate = true;
                mirror = false;
                top = true;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(3f, 23){{
                    width = 7f;
                    height = 9f;
                    lifetime = PvUtil.GetRange(3,22);
                    trailColor = backColor = lightColor = Pal.heal;
                    trailLength = 30;
                    weaveScale = 2;
                    weaveMag = 2;
                    homingPower = 0.008f;
                    trailWidth = 1.4f;
                    weaveRandom = true;
                    healPercent = 7;
                    collidesTeam = true;
                    ammoMultiplier = 2;
                    /*
                    fragBullets = 3;
                    fragBullet = new BasicBulletType(3,5)
                    {{
                         width = 7f;
                         height = 9f;
                         lifetime = PvUtil.GetRange(3,6);
                        trailWidth = 1.4f;
                        trailLength = 10;
                         trailColor = backColor = lightColor = Pal.heal;
                         healPercent = 1;
                    }};
                     */
                }};
            }});
        }};
        tributary = new PvUnitType("tributary")
        {{
            localizedName = "Tributary";
            factions.add(PvFactions.Xeal);
            constructor = EntityMapping.map("risso");
            canBoost = true;
            speed = 6.8f/7.5f;
            engineOffset = 8;
            drag = 0.13f;
            buildSpeed = 3f;
            buildBeamOffset = 0;
            abilities.add(new RepairFieldAbility(10,720,8*20),
                    new StatusFieldAbility(PvStatusEffects.aoe,240,180,8*20)
            );
            hitSize = 10f;
            health = 2400;
            armor = 6;
            accel = 0.4f;
            rotateSpeed = 3.3f;
            faceTarget = false;

            weapons.add(new Weapon(name+"-weapon"){{
                reload = 60f/1.2f;
                shoot = new ShootSpread(3,0);
                x = 0f;
                shootY = 2f;
                hitSize = 2*8;
                inaccuracy = 15;
                y = -4f;
                recoil = 2;
                rotate = true;
                mirror = false;
                top = true;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(3f, 30){{
                    width = 7f;
                    height = 9f;
                    lifetime = PvUtil.GetRange(3,22);
                    trailColor = backColor = lightColor = Pal.heal;
                    trailLength = 30;
                    weaveScale = 2;
                    weaveMag = 2;
                    homingPower = 0.008f;
                    trailWidth = 1.4f;
                    weaveRandom = true;
                    healPercent = 5;
                    collidesTeam = true;
                    ammoMultiplier = 2;
                    /*
                    fragBullets = 3;
                    fragBullet = new BasicBulletType(3,5)
                    {{
                         width = 7f;
                         height = 9f;
                         lifetime = PvUtil.GetRange(3,6);
                        trailWidth = 1.4f;
                        trailLength = 10;
                         trailColor = backColor = lightColor = Pal.heal;
                         healPercent = 1;
                    }};
                     */
                }};
            }});
        }};
        loch = new PvUnitType("loch") {{
            localizedName = "Loch";
            factions.add(PvFactions.Xeal);
            constructor = EntityMapping.map("risso");
            canBoost = true;
            speed = 6.2f / 7.5f;
            engineOffset = 14;
            engineSize = 12;
            drag = 0.13f;
            buildSpeed = 4f;
            buildBeamOffset = 0;
            abilities.add(new RepairFieldAbility(4,1080,8*30),
                    new StatusFieldAbility(PvStatusEffects.homing,300,240,8*24)
            );
            hitSize = 18f;
            health = 18000;
            armor = 8;
            accel = 0.4f;
            rotateSpeed = 3.3f;
            faceTarget = false;

            weapons.add(new Weapon(name+"-turret"){{
                reload = 60f/0.8f;
                shoot = new ShootSpread(3,0);
                shoot.shotDelay = 5;
                x = 0f;
                shootY = 2f;
                hitSize = 2*8;
                inaccuracy = 3;
                y = -4f;
                recoil = 2;
                rotate = true;
                mirror = false;
                top = true;
                ejectEffect = Fx.casing1;
                bullet = new BasicBulletType(3f, 72){{
                    width = 7f;
                    height = 9f;
                    lifetime = PvUtil.GetRange(3,34);
                    trailColor = backColor = lightColor = Pal.heal;
                    trailLength = 30;
                    weaveScale = 2;
                    weaveMag = 2;
                    homingPower = 0.008f;
                    trailWidth = 1.4f;
                    weaveRandom = true;
                    healPercent = 5;
                    collidesTeam = true;
                    ammoMultiplier = 2;
                    fragBullets = 3;
                    fragBullet = new BasicBulletType(3,45)
                    {{
                         width = 7f;
                         height = 9f;
                         lifetime = PvUtil.GetRange(3,12);
                         trailWidth = 1.4f;
                         trailLength = 10;
                         trailColor = backColor = lightColor = Pal.heal;
                         healPercent = 1;
                    }};
                }};
            }});
        }};
         atlantic = new PvUnitType("atlantic") {{
            // i dont know what stats to put so basic shit /otamamori
            localizedName = "Atlantic";
            factions.add(PvFactions.Xeal);
            constructor = EntityMapping.map("risso");
            canBoost = true;
            speed = 6.2f / 7.5f;
            engineOffset = 14;
            engineSize = 12;
            drag = 0.13f;
            buildSpeed = 4f;
            buildBeamOffset = 0;
            /*abilities.add(new RepairFieldAbility(4,1080,8*30),
                    new StatusFieldAbility(PvStatusEffects.homing,300,240,8*24)
            );
            for shield abillty /otamamori
            */
            hitSize = 18f;
            health = 24000;
            armor = 12;
            accel = 0.4f;
            rotateSpeed = 3.3f;
            faceTarget = false;

            weapons.add(new Weapon(name+"-weapon"){{
                reload = 80f/0.8f;
                shoot = new ShootSpread(3,0);
                shoot.shotDelay = 5;
                x = 17f;
                shootY = 2f;
                hitSize = 2*8;
                inaccuracy = 3;
                y = -12f;
                recoil = 2;
                rotate = true;
                mirror = true;
                top = true;
                ejectEffect = Fx.casing1;
                parts.add(
                new RegionPart("-blade"){{
                    moveRot = -10f;
                    moveY = -1f;
                    under = false;
                    moves.add(new PartMove(PartProgress.reload, 0f, -0.5f, -10f));
                    progress = PartProgress.warmup;
                    mirror = true;
                }});
                bullet = new BasicBulletType(3f, 72){{
                    width = 12f;
                    height = 15f;
                    lifetime = PvUtil.GetRange(3,34);
                    trailColor = backColor = lightColor = Pal.heal;
                    trailLength = 40;
                    weaveScale = 4;
                    weaveMag = 2;
                    homingPower = 0.008f;
                    trailWidth = 3f;
                    weaveRandom = true;
                    healPercent = 5;
                    collidesTeam = true;
                    ammoMultiplier = 2;
                    /*fragBullets = 3;
                    fragBullet = new BasicBulletType(3,45)
                    {{
                         width = 7f;
                         height = 9f;
                         lifetime = PvUtil.GetRange(3,12);
                         trailWidth = 1.4f;
                         trailLength = 10;
                         trailColor = backColor = lightColor = Pal.heal;
                         healPercent = 1;
                    }};
                    is this important? /otamamori
                    */
                }};
            }});
            weapons.add(new Weapon(name+"-weapon"){{
                reload = 60f/0.8f;
                shoot = new ShootSpread(3,0);
                shoot.shotDelay = 5;
                x = 17f;
                shootY = 2f;
                hitSize = 2*8;
                inaccuracy = 3;
                y = 4f;
                recoil = 2;
                rotate = true;
                mirror = true;
                top = true;
                ejectEffect = Fx.casing1;
                parts.add(
                new RegionPart("-blade"){{
                    moveRot = -10f;
                    moveY = -1f;
                    under = false;
                    moves.add(new PartMove(PartProgress.reload, 0f, -0.5f, -10f));
                    progress = PartProgress.warmup;
                    mirror = true;
                }});
                bullet = new BasicBulletType(3f, 72){{
                    width = 12f;
                    height = 15f;
                    lifetime = PvUtil.GetRange(3,34);
                    trailColor = backColor = lightColor = Pal.heal;
                    trailLength = 40;
                    weaveScale = 4;
                    weaveMag = 2;
                    homingPower = 0.008f;
                    trailWidth = 3f;
                    weaveRandom = true;
                    healPercent = 5;
                    collidesTeam = true;
                    ammoMultiplier = 2;
                    /*fragBullets = 3;
                    fragBullet = new BasicBulletType(3,45)
                    {{
                         width = 7f;
                         height = 9f;
                         lifetime = PvUtil.GetRange(3,12);
                         trailWidth = 1.4f;
                         trailLength = 10;
                         trailColor = backColor = lightColor = Pal.heal;
                         healPercent = 1;
                    }};
                    is this important? /otamamori
                    */
                }};
            }});
        }};
    }

}
