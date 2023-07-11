package viscott.content;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.entities.UnitSorts;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.ExplosionEffect;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.HaloPart;
import mindustry.entities.part.RegionPart;
import mindustry.entities.part.ShapePart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.Weapon;
import mindustry.type.unit.MissileUnitType;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ContinuousTurret;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.BuildVisibility;
import viscott.content.shootpatterns.CyclicShootPattern;
import viscott.utilitys.PvUtil;
import viscott.world.pseudo3d.importedcode.BallisticMissileBulletType;
import viscott.world.statusEffects.PvStatusEffect;

import static mindustry.content.Items.silicon;
import static mindustry.type.ItemStack.with;
import static viscott.utilitys.PvUtil.GetName;

public class PvTurrets{
    public static Block
            splinter,shatter,euro,snap,hourglass,
            phantom,razor,rainmaker,striker,
            marksman, xacto,reaper,shuttle, nuero, jaeger, glaive,
            xterminium,hel,falarica,spring,shredder,

            fracture,javelin
            ;

    public static void load(){
        loadSize2();
        loadSize3();
        loadSize4();
        loadSize5();
        loadSize6();
};

    public static void loadSize2() //cuz u guys cant sort stuff by size i did it 4 u.
    {
        splinter = new ItemTurret("splinter"){{
            localizedName = "Splinter";
            size = 2;
            health = 875;
            range = 26 * 8;
            recoil = 2;
            rotateSpeed = 6f;
            requirements(Category.turret, with(PvItems.zirconium, 75));
            ammo(
                    PvItems.zirconium,  new BasicBulletType(6f, 21){{
                        lifetime = PvUtil.GetRange(speed,range-1);
                        knockback = 0.3f;
                        width = 10f;
                        height = 14f;
                        ammoMultiplier = 2;
                        hitColor = backColor = trailColor = Color.valueOf("6f6e80");
                        frontColor = Color.valueOf("9a9aa6");
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        trailWidth = 2.5f;
                        trailLength = 30;
                        status = StatusEffects.shocked;
                        statusDuration = 30;
                    }},
                    PvItems.erbium,  new BasicBulletType(6f, 42){{
                        lifetime = PvUtil.GetRange(speed,range-1);
                        knockback = 0.3f;
                        width = 10f;
                        height = 14f;
                        ammoMultiplier = 2;
                        hitColor = backColor = trailColor = Color.valueOf("aaadaf");
                        frontColor = Color.valueOf("d1d6db");
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        trailWidth = 2.5f;
                        trailLength = 30;
                        status = StatusEffects.shocked;
                        statusDuration = 30;
                    }},
                    PvItems.nobelium,  new BasicBulletType(6f, 36){{
                        lifetime = PvUtil.GetRange(speed,range-1);
                        knockback = 0.2f;
                        width = 10f;
                        height = 14f;
                        ammoMultiplier = 3;
                        hitColor = backColor = trailColor = Color.valueOf("ef525b");
                        frontColor = Color.valueOf("ffa1a7");
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        status = StatusEffects.blasted;
                        statusDuration = 120f;
                        trailWidth = 2.5f;
                        trailLength = 30;
                        status = StatusEffects.shocked;
                        statusDuration = 30;
                    }}
            );

            shoot = new ShootSpread(3, 1f);

            shootY = 6f;
            reload = 60f;
            ammoUseEffect = Fx.casing1;
            inaccuracy = 1f;
            coolant = consumeCoolant(0.1f);
            researchCostMultiplier = 0.05f;
            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-l"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = -1f;
                                    moveRot = 7f;
                                    moves.add(new PartMove(PartProgress.recoil, 0f, -2f, -3f));
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = 1f;
                                    moveRot = -7f;
                                    moves.add(new PartMove(PartProgress.recoil, 0f, -2f, 3f));
                                }}
                        )
                );
            }};
            limitRange();
        }};
        shatter = new PowerTurret("shatter")
        {{
            requirements(Category.turret, with(PvItems.zirconium, 100,PvItems.lithium,100)); //Todo 2
            range = 29*8;
            health = 800;
            size = 2;
            localizedName = "Shatter";
            targetAir = true;
            targetGround = true;
            consumePower(120f/60f);
            coolant = consumeCoolant(0.1f);
            reload = 60f/1.2f;
            rotateSpeed = 4f;

            shootType = new BasicBulletType(){{
                shootEffect = new MultiEffect(Fx.shootTitan, new WaveEffect(){{
                    colorTo = Pal.lancerLaser;
                    sizeTo = 26f;
                    lifetime = 14f;
                    strokeFrom = 4f;
                }});
                smokeEffect = Fx.shootSmokeTitan;
                hitColor = Pal.lancerLaser;

                sprite = "large-orb";
                trailEffect = Fx.missileTrail;
                trailInterval = 6f;
                trailParam = 4f;
                pierceCap = 2;
                fragOnHit = false;
                speed = 5f;
                damage = 30f;
                lifetime = PvUtil.GetRange(this.speed,31);
                width = height = 16f;
                backColor = Pal.lancerLaser;
                frontColor = Color.white;
                shrinkX = shrinkY = 0f;
                trailColor = Pal.lancerLaser;
                trailLength = 12;
                trailWidth = 2.2f;
                despawnEffect = hitEffect = new ExplosionEffect(){{
                    waveColor = Pal.lancerLaser;
                    smokeColor = Color.gray;
                    sparkColor = Pal.sap;
                    waveStroke = 4f;
                    waveRad = 40f;
                }};
                despawnSound = Sounds.dullExplosion;

                //TODO shoot sound
                shootSound = Sounds.cannon;

                fragBullet = intervalBullet = new BasicBulletType(3f, 6){{
                    width = 9f;
                    hitSize = 5f;
                    height = 15f;
                    pierce = true;
                    lifetime = PvUtil.GetRange(this.speed,6);
                    pierceBuilding = true;
                    hitColor = backColor = trailColor = Pal.lancerLaser;
                    frontColor = Color.white;
                    trailWidth = 2.1f;
                    trailLength = 5;
                    hitEffect = despawnEffect = new WaveEffect(){{
                        colorFrom = colorTo = Pal.lancerLaser;
                        sizeTo = 4f;
                        strokeFrom = 4f;
                        lifetime = 10f;
                    }};
                    buildingDamageMultiplier = 0.3f;
                    homingPower = 0.2f;
                }};

                bulletInterval = 3f;
                intervalRandomSpread = 20f;
                intervalBullets = 2;
                intervalAngle = 180f;
                intervalSpread = 300f;

                fragBullets = 20;
                fragVelocityMin = 0.5f;
                fragVelocityMax = 1.5f;
                fragLifeMin = 0.5f;
            }};

            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-l"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = -1f;
                                    moveRot = 7f;
                                    moves.add(new PartMove(PartProgress.recoil, 0f, 1.5f, 3f));
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = 1f;
                                    moveRot = -7f;
                                    moves.add(new PartMove(PartProgress.recoil, 0f, 1.5f, -3f));
                                }}
                        )
                );
            }};

        }};
        euro = new ItemTurret("euro")
        {{
            requirements(Category.turret, with(silicon, 300,PvItems.erbium,150)); //Todo 2
            range = 36*8;
            localizedName = "Euro";
            inaccuracy = 10;
            reload = 180;
            size = 2;
            coolant = consumeCoolant(0.1f);
            targetAir = false;
            ammo(
                    silicon, new BasicBulletType(0f, 1){{
                        ammoMultiplier = 1f;

                        spawnUnit = new MissileUnitType("euro-missile1"){{
                            speed = 4.6f;
                            maxRange = 6f;
                            lifetime = PvUtil.GetRange(this.speed,36)+30;
                            outlineColor = Pal.darkOutline;
                            engineColor = trailColor = Pal.redLight;
                            engineLayer = Layer.effect;
                            engineSize = 3.1f;
                            engineOffset = 10f;
                            rotateSpeed = 0.25f;
                            trailLength = 18;
                            missileAccelTime = 50f;
                            lowAltitude = true;
                            loopSound = Sounds.missileTrail;
                            loopSoundVolume = 0.6f;
                            deathSound = Sounds.largeExplosion;
                            targetAir = false;
                            collidesAir = false;

                            fogRadius = 6f;

                            health = 210;

                            weapons.add(new Weapon(){{
                                shootCone = 360f;
                                mirror = false;
                                reload = 1f;
                                deathExplosionEffect = Fx.massiveExplosion;
                                shootOnDeath = true;
                                shake = 10f;
                                bullet = new ExplosionBulletType(32f, 4.8f*8f){{
                                    hitColor = Pal.redLight;
                                    shootEffect = new MultiEffect(Fx.massiveExplosion, Fx.scatheLight, new WaveEffect(){{
                                        lifetime = 10f;
                                        strokeFrom = 4f;
                                        sizeTo = 130f;
                                    }});

                                    collidesAir = false;
                                    buildingDamageMultiplier = 0.3f;

                                    ammoMultiplier = 1f;
                                    fragLifeMin = 0.1f;
                                    fragBullets = 3;
                                    fragBullet = new ArtilleryBulletType(2f, 13){{
                                        drag = 0.02f;
                                        hitEffect = Fx.massiveExplosion;
                                        despawnEffect = Fx.scatheSlash;
                                        knockback = 0.8f;
                                        lifetime = PvUtil.GetRange(this.speed,1.2f);
                                        width = height = 18f;
                                        collidesTiles = false;
                                        splashDamageRadius = 40f;
                                        splashDamage = 20f;
                                        backColor = trailColor = hitColor = Pal.bulletYellow;
                                        frontColor = Color.white;
                                        smokeEffect = Fx.shootBigSmoke2;
                                        despawnShake = 1f;
                                        lightRadius = 30f;
                                        lightColor = Pal.redLight;
                                        lightOpacity = 0.5f;

                                        trailLength = 20;
                                        trailWidth = 3.5f;
                                        trailEffect = Fx.none;
                                    }};
                                }};
                            }});

                        }};
                    }},
                    PvItems.nobelium, new BasicBulletType(0f, 1){{
                        shootEffect = Fx.shootBig;
                        smokeEffect = Fx.shootSmokeMissile;
                        ammoMultiplier = 1f;
                        rangeChange = 8*10;

                        spawnUnit = new MissileUnitType("euro-missile2"){{
                            speed = 4.6f;
                            maxRange = 6f;
                            lifetime = PvUtil.GetRange(this.speed,46)+30;
                            outlineColor = Pal.darkOutline;
                            engineColor = trailColor = Pal.redLight;
                            engineLayer = Layer.effect;
                            engineSize = 3.1f;
                            engineOffset = 10f;
                            rotateSpeed = 0.25f;
                            trailLength = 18;
                            missileAccelTime = 50f;
                            lowAltitude = true;
                            loopSound = Sounds.missileTrail;
                            loopSoundVolume = 0.6f;
                            deathSound = Sounds.largeExplosion;
                            targetAir = false;
                            collidesAir = false;

                            fogRadius = 6f;

                            health = 210;

                            weapons.add(new Weapon(){{
                                shootCone = 360f;
                                mirror = false;
                                reload = 1f;
                                deathExplosionEffect = Fx.massiveExplosion;
                                shootOnDeath = true;
                                shake = 10f;
                                bullet = new ExplosionBulletType(40f, 6.4f*8f){{
                                    hitColor = Pal.redLight;
                                    shootEffect = new MultiEffect(Fx.massiveExplosion, Fx.scatheExplosion, Fx.scatheLight, new WaveEffect(){{
                                        lifetime = 10f;
                                        strokeFrom = 4f;
                                        sizeTo = 130f;
                                    }});

                                    collidesAir = false;
                                    buildingDamageMultiplier = 0.3f;

                                    ammoMultiplier = 1f;
                                    fragLifeMin = 0.1f;
                                    fragBullets = 4;
                                    fragBullet = new ArtilleryBulletType(2f, 20){{
                                        drag = 0.02f;
                                        hitEffect = Fx.massiveExplosion;
                                        despawnEffect = Fx.scatheSlash;
                                        knockback = 0.8f;
                                        lifetime = PvUtil.GetRange(this.speed,2.7f);
                                        width = height = 18f;
                                        collidesTiles = false;
                                        splashDamageRadius = 40f;
                                        splashDamage = 80f;
                                        backColor = trailColor = hitColor = Pal.bulletYellow;
                                        frontColor = Color.white;
                                        smokeEffect = Fx.shootBigSmoke2;
                                        despawnShake = 1f;
                                        lightRadius = 30f;
                                        lightColor = Pal.redLight;
                                        lightOpacity = 0.5f;

                                        trailLength = 20;
                                        trailWidth = 3.5f;
                                        trailEffect = Fx.none;
                                    }};
                                }};
                            }});

                        }};
                    }}
            );

            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-l"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = -1f;
                                    moveRot = 7f;
                                    moves.add(new PartMove(PartProgress.recoil, 1f, 1f, 0f));
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = 1f;
                                    moveRot = -7f;
                                    moves.add(new PartMove(PartProgress.recoil, -1f, 1f, 0f));
                                }}
                        )
                );
            }};
            limitRange();
        }};
        snap = new LiquidTurret("snap")
        {{
            requirements(Category.turret, with(silicon, 100)); //Todo 2
            localizedName = "Snap";
            size = 2;
            reload = 60f/1f;
            range = 28*8;
            ammo(
                    Liquids.water,new LaserBulletType(13) {{
                        colors = new Color[3];
                        colors[0] = colors[1] = colors[2] = Liquids.water.color;
                        length = 28*8;
                        status = StatusEffects.wet;
                        statusDuration = 90;

                    }},
                    PvLiquids.kerosene, new LaserBulletType(20){{
                        colors = new Color[3];
                        colors[0] = colors[1] = colors[2] = PvLiquids.kerosene.color;
                        length = 28*8;
                        status = PvStatusEffects.doused;
                        statusDuration = 90;

                    }}
            );
            drawer = new DrawTurret(GetName("Pov"));
        }};
        hourglass = new ItemTurret("hourglass")
        {{
            localizedName = "Hourglass";
            size = 2;
            reload = 60f/2f;
            range = 26*8;
            coolant = consumeCoolant(0.1f);
            requirements(Category.turret,with(PvItems.zirconium,100,silicon,50)); //Todo 2
            ammo(
                    PvItems.zirconium,new BasicBulletType(6,0)
                    {{
                        trailColor = frontColor = backColor = Pal.sap;
                        trailLength = 10;
                        trailWidth = 2;
                        lifetime = PvUtil.GetRange(this.speed,26);
                        splashDamageRadius = 8.3f*8;
                        status = PvStatusEffects.timeWarped;
                        statusDuration = 150;
                        despawnEffect = hitEffect = PvEffects.slowEnergeticEffect;
                    }}
            );
            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-l"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = -1f;
                                    moveRot = 7f;
                                    moves.add(new PartMove(PartProgress.recoil, 1f, 0f, 10f));
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = 1f;
                                    moveRot = -7f;
                                    moves.add(new PartMove(PartProgress.recoil, -1f, 0f, -10f));
                                }}
                        )
                );
            }};
            limitRange();
        }};
    }
    public static void loadSize3()
    {
        phantom = new ItemTurret("phantom")
        {{
            requirements(Category.turret,with(PvItems.erbium,450,PvItems.zirconium,300, silicon,75)); //Todo 2
            localizedName = "Phantom";
            size = 3;
            health = 1980;
            consumePower(290f/60f);
            range = 50*8;
            liquidCapacity = 35;
            targetAir = true;
            targetGround = false;
            reload = 60f/0.9f;
            coolant = consumeCoolant(0.1f);
            ammo(
                    PvItems.platinum,new BasicBulletType(4,120)
                    {{
                        rangeChange = -8*4;
                        reloadMultiplier = 1.5f;
                        lifetime = PvUtil.GetRange(this.speed,46);
                        trailLength = 10;
                        trailWidth = 1;
                        width = 15;
                        height = 15;
                        trailColor = backColor = Pal.gray;

                    }},
                    PvItems.erbium,new BasicBulletType(8,500)
                    {{
                        lifetime = PvUtil.GetRange(this.speed,50);
                        trailLength = 20;
                        trailWidth = 2;
                        width = 20;
                        height = 20;
                        trailColor = backColor = Pal.gray;
                    }}
            );
            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-l"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.heat;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = 0f;
                                    moveY = -0.5f;
                                    moveRot = 12f;
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.heat;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = 0f;
                                    moveY = -0.5f;
                                    moveRot = -12f;
                                }}
                        )
                );
            }};
            limitRange();
        }};
        razor = new ItemTurret("razor")
        {{
            requirements(Category.turret,with(PvItems.nobelium,30,PvItems.lithium,140)); //Todo 2
            localizedName = "Razor";
            size = 3;
            health = 2175;
            range = 30*8;
            liquidCapacity = 20;
            targetAir = true;
            targetGround = true;
            reload = 60f/4.8f;
            recoil = 3;
            coolant = consumeCoolant(0.1f);
            shoot = new ShootAlternate(8);
            ammo(
                    PvItems.platinum,new BasicBulletType(5,24)
                    {{
                        lifetime = PvUtil.GetRange(this.speed,30);
                        pierce = true;
                        pierceCap = 1;
                        trailLength = 15;
                        trailWidth = 2;
                        trailColor = backColor = lightColor = Pal.heal;
                    }},
                    silicon,new BasicBulletType(5,21)
                    {{
                        lifetime = PvUtil.GetRange(this.speed,30);
                        trailLength = 15;
                        trailWidth = 2;
                        trailColor = backColor = lightColor = Pal.heal;
                        weaveMag = 2;
                        weaveScale = 2;
                        weaveRandom = true;
                        homingPower = 0.02f;
                    }},
                    PvItems.erbium,new BasicBulletType(5,32)
                    {{
                        lifetime = PvUtil.GetRange(this.speed,30);
                        pierce = true;
                        pierceCap = 2;
                        trailLength = 15;
                        trailWidth = 2;
                        trailColor = backColor = lightColor = Pal.heal;
                        fragBullets = 2;
                        fragRandomSpread = 90;
                        fragLifeMin = 0.9f;
                        fragVelocityMin = 1f;
                        fragBullet = new BasicBulletType(4,15)
                        {{
                            lifetime = PvUtil.GetRange(this.speed,7);
                            trailLength = 15;
                            trailWidth = 2;
                            trailColor = backColor = lightColor = Pal.heal;
                        }};
                    }}
            );
            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-l"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = -1f;
                                    moveRot = 1f;
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = -1f;
                                    moveRot = -1f;
                                }}
                        )
                );
            }};
        }};

        falarica = new PowerTurret("falarica")
        {{
            requirements(Category.turret,with(silicon,100,PvItems.nobelium,80,PvItems.zirconium,120)); //Todo 2
            localizedName = "Falarica";
            size = 3;
            health = 2045;
            consumePower(560f/60f);
            liquidCapacity = 50;
            range = 35*8;
            minWarmup = 0.7f;
            reload = 60f/0.7f;
            targetAir = true;
            targetGround = true;
            shootY = 10;
            recoil = 5;

            shootType = new BasicBulletType(14,275)
            {{
                lifetime = PvUtil.GetRange(speed,35);
                 collidesAir = true;
                 collidesGround = true;
                 pierce = true;
                 pierceCap = 10;
                 pierceBuilding = true;
                 trailInterval = 0;
                 trailChance = Integer.MAX_VALUE;
                 bulletInterval = 3;
                 intervalBullets = 2;
                 trailLength = 40;
                 trailWidth = 2;
                 trailColor = lightColor = backColor = Pal.lancerLaser;
                 trailEffect = PvEffects.waveBulletFalerica;
                 trailRotation = true;
                 fragBullets = 4;
                 fragBullet = intervalBullet = new LightningBulletType()
                {{
                    damage = 50;
                    lightningLength = 15;

                }};
            }};
            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-l"){{
                                    progress = PartProgress.warmup;
                                    heatProgress = PartProgress.warmup;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = 1f;
                                    moveX = -2;
                                    moveRot = 15;
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.warmup;
                                    heatProgress = PartProgress.warmup;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = 1f;
                                    moveX = 2;
                                    moveRot = -15;
                                }}
                        )
                );
            }};
        }};
        striker = new ItemTurret("striker")
        {{
            localizedName = "Striker";
            size = 3;
            reload = 90f;
            outlineColor = Color.valueOf("343434");
            outlineRadius = 3;
            range = 26*8;
            shootY = 4f;
            inaccuracy = 2;
            health = 1050;
            recoil = 4;
            heatColor  = Color.valueOf( "d237a6");
            shoot = new CyclicShootPattern(4,5,0);
            requirements(Category.turret,with(PvItems.zirconium,120,PvItems.nobelium,50,PvItems.lithium,200,PvItems.barium,50)); //Todo 2
            ammo(
                    PvItems.nobelium, new BasicBulletType(10, 40){{
                        trailColor = frontColor = backColor = Color.valueOf( "d237a6");
                        trailLength = 8;
                        trailWidth = 2;
                        offset = 4f;
                        homingPower = 0.03f;
                        heatRequirement = 10;
                        maxHeatEfficiency = 3;
                        shoot.shotDelay = 1f;
                        lifetime = PvUtil.GetRange(this.speed,26);
                    }}
            );
            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-barrel"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = true;
                                    moveY = -3f;
                                    moveRot = 0f;
                                    x = 0;
                                    y = 0;
                                }},
                                new RegionPart("-l"){{
                                    progress = PartProgress.warmup;
                                    heatProgress = PartProgress.warmup;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.warmup;
                                    heatProgress = PartProgress.warmup;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                }}
                        )
                );
            }};
        }};
        glaive = new LiquidTurret("glaive"){{
            requirements(Category.turret,BuildVisibility.shown , with(PvItems.zirconium,120,PvItems.nobelium,50,PvItems.erbium,100));
            size = 3;
            reload = 40f;
            localizedName = "Glaive";
            velocityRnd = 0f;
            inaccuracy = 0f;
            shootX = 0;
            shootY = 0;
            health = 3200;
            recoil = -8*9f;
            shootCone = 45f;
            liquidCapacity = 40f;
            shootEffect = Fx.shootLiquid;
            range = 8*12f;
            scaledHealth = 250;
            ammo(
                    Liquids.water, new BasicBulletType(range/20, 140){{
                        trailLength = 20;
                        trailWidth = 2;
                        pierce = true; pierceCap = 1000;
                        trailColor = lightColor = backColor = Color.valueOf("000000");
                        lifetime = Mathf.ceil(PvUtil.GetRange(this.speed,12));
                        hitSize = 8*1.5f;
                        status = PvStatusEffects.timeWarped;
                        knockback = -4f;
                        hitEffect = Fx.hitLancer;
                        despawnEffect = Fx.none;
                        statusDuration = 8*60;
                    }}
            );
            drawer = new DrawTurret(GetName("Pov")){{

            }};
        }};
    }
    public static void loadSize4()
    {
        shredder = new ItemTurret("shredder")
        {{
            localizedName = "Shredder";
            size = 4;
            health = 2900;
            range = 36 * 8;
            recoil = 6;
            rotateSpeed = 4f;
            requirements(Category.turret, with(PvItems.zirconium, 250,PvItems.barium,500,PvItems.nobelium,100)); //Todo 2
            ammo(
                    PvItems.zirconium,  new BasicBulletType(6f, 48){{
                        lifetime = PvUtil.GetRange(this.speed,36);
                        knockback = 0.7f;
                        width = 10f;
                        height = 14f;
                        ammoMultiplier = 3;
                        hitColor = backColor = trailColor = Color.valueOf("6f6e80");
                        frontColor = Color.valueOf("9a9aa6");
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        trailWidth = 2.5f;
                        trailLength = 30;
                    }},
                    PvItems.platinum,  new BasicBulletType(6f, 64){{
                        lifetime = PvUtil.GetRange(this.speed,36);
                        knockback = 0.9f;
                        width = 10f;
                        height = 14f;
                        ammoMultiplier = 3;
                        hitColor = backColor = trailColor = Color.valueOf("aaadaf");
                        frontColor = Color.valueOf("d1d6db");
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        trailWidth = 2.5f;
                        trailLength = 30;
                    }},
                    PvItems.nobelium,  new BasicBulletType(6f, 58){{
                        lifetime = PvUtil.GetRange(this.speed,40);
                        rangeChange = 4*8;
                        width = 10f;
                        height = 14f;
                        ammoMultiplier = 3;
                        hitColor = backColor = trailColor = Color.valueOf("ef525b");
                        frontColor = Color.valueOf("ffa1a7");
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        status = StatusEffects.blasted;
                        statusDuration = 120f;
                        trailWidth = 2.5f;
                        trailLength = 30;
                    }},
                    PvItems.carbonFiber, new BasicBulletType(6f, 97){{
                        lifetime = PvUtil.GetRange(this.speed,40);
                        rangeChange = 4*8;
                        knockback = 1.2f;
                        width = 10f;
                        height = 14f;
                        ammoMultiplier = 5;
                        hitColor = backColor = trailColor = Pal.reactorPurple;
                        frontColor = Color.white;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        status = StatusEffects.slow;
                        statusDuration = 60;
                        trailWidth = 2.5f;
                        trailLength = 30;
                    }}
            );

            shoot = new ShootSpread(3, 1f);

            shootY = 6f;
            reload = 60f;
            ammoUseEffect = Fx.casing1;
            inaccuracy = 1f;
            coolant = consumeCoolant(0.1f);
            researchCostMultiplier = 0.05f;
            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-l"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = -1f;
                                    moveRot = 7f;
                                    moves.add(new PartMove(PartProgress.recoil, 0f, -2f, -3f));
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = 1f;
                                    moveRot = -7f;
                                    moves.add(new PartMove(PartProgress.recoil, 0f, -2f, 3f));
                                }}
                        )
                );
            }};
            limitRange();
        }};
        fracture = new PowerTurret("fracture")
        {{
            requirements(Category.turret, with(PvItems.zirconium, 150,PvItems.lithium,200,PvItems.nobelium,50,silicon,100)); //Todo 2
            range = 48*8;
            health = 2820;
            size = 4;
            localizedName = "Fracture";
            targetAir = true;
            targetGround = true;
            consumePower(630f/60f);
            reload = 60f/0.5f;
            rotateSpeed = 5f;
            inaccuracy = 1;

            shootType = new BasicBulletType(5,150){
                {
                    shootEffect = new MultiEffect(Fx.shootTitan, new WaveEffect() {{
                        colorTo = Pal.lancerLaser;
                        sizeTo = 26f;
                        lifetime = 14f;
                        strokeFrom = 4f;
                    }});
                    smokeEffect = Fx.shootSmokeTitan;
                    hitColor = Pal.lancerLaser;

                    sprite = "large-orb";
                    trailEffect = Fx.missileTrail;
                    trailInterval = 3f;
                    trailParam = 4f;
                    fragOnHit = false;
                    lifetime = PvUtil.GetRange(this.speed, 40);
                    width = height = 16f;
                    backColor = Pal.lancerLaser;
                    frontColor = Color.white;
                    shrinkX = shrinkY = 0f;
                    trailColor = Pal.lancerLaser;
                    trailLength = 12;
                    trailWidth = 2.2f;
                    despawnEffect = hitEffect = new ExplosionEffect() {{
                        waveColor = Pal.lancerLaser;
                        smokeColor = Color.gray;
                        sparkColor = Pal.sap;
                        waveStroke = 4f;
                        waveRad = 40f;
                    }};
                    splashDamage = 35;
                    splashDamageRadius = 8 * 2.5f;
                    despawnSound = Sounds.dullExplosion;
                    fragBullets = 5;

                    //TODO shoot sound
                    shootSound = Sounds.cannon;
                    fragBullet = new BasicBulletType(5,40) {{
                        shootEffect = new MultiEffect(Fx.shootTitan, new WaveEffect() {{
                            colorTo = Pal.lancerLaser;
                            sizeTo = 26f;
                            lifetime = 14f;
                            strokeFrom = 4f;
                        }});
                        smokeEffect = Fx.shootSmokeTitan;
                        hitColor = Pal.lancerLaser;

                        sprite = "large-orb";
                        trailEffect = Fx.missileTrail;
                        trailInterval = 3f;
                        trailParam = 4f;
                        fragOnHit = false;
                        lifetime = PvUtil.GetRange(this.speed, 16);
                        width = height = 16f;
                        backColor = Pal.lancerLaser;
                        frontColor = Color.white;
                        shrinkX = shrinkY = 0f;
                        trailColor = Pal.lancerLaser;
                        trailLength = 12;
                        trailWidth = 2.2f;
                        despawnEffect = hitEffect = new ExplosionEffect() {{
                            waveColor = Pal.lancerLaser;
                            smokeColor = Color.gray;
                            sparkColor = Pal.sap;
                            waveStroke = 4f;
                            waveRad = 40f;
                        }};
                        splashDamage = 35;
                        splashDamageRadius = 8 * 2.5f;
                        despawnSound = Sounds.dullExplosion;

                        //TODO shoot sound
                        shootSound = Sounds.cannon;

                        fragBullet = intervalBullet = new BasicBulletType(3f, 6) {{
                            width = 9f;
                            hitSize = 5f;
                            height = 15f;
                            pierce = true;
                            lifetime = PvUtil.GetRange(this.speed, 6);
                            pierceBuilding = true;
                            hitColor = backColor = trailColor = Pal.lancerLaser;
                            frontColor = Color.white;
                            trailWidth = 2.1f;
                            trailLength = 5;
                            hitEffect = despawnEffect = new WaveEffect() {{
                                colorFrom = colorTo = Pal.lancerLaser;
                                sizeTo = 4f;
                                strokeFrom = 4f;
                                lifetime = 10f;
                            }};
                            buildingDamageMultiplier = 0.3f;
                            homingPower = 0.2f;
                        }};

                        bulletInterval = 3f;
                        intervalRandomSpread = 20f;
                        intervalBullets = 2;
                        intervalAngle = 180f;
                        intervalSpread = 300f;

                        fragBullets = 10;
                        fragVelocityMin = 0.5f;
                        fragVelocityMax = 1.5f;
                        fragLifeMin = 0.5f;
                    }};
                    intervalBullet = new BasicBulletType(5f, 20) {{
                        width = 9f;
                        hitSize = 5f;
                        height = 15f;
                        pierce = true;
                        lifetime = PvUtil.GetRange(this.speed, 6);
                        pierceBuilding = true;
                        hitColor = backColor = trailColor = Pal.lancerLaser;
                        frontColor = Color.white;
                        trailWidth = 2.1f;
                        trailLength = 8;
                        hitEffect = despawnEffect = new WaveEffect() {{
                            colorFrom = colorTo = Pal.lancerLaser;
                            sizeTo = 4f;
                            strokeFrom = 4f;
                            lifetime = 10f;
                        }};
                        buildingDamageMultiplier = 0.3f;
                        homingPower = 0.2f;
                    }};

                    bulletInterval = 3f;
                    intervalRandomSpread = 10f;
                    intervalBullets = 2;
                    intervalAngle = 180f;
                    intervalSpread = 270f;

                    fragBullets = 10;
                    fragVelocityMin = 0.5f;
                    fragVelocityMax = 1.5f;
                    fragLifeMin = 0.5f;
                }};

            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-barrel"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = true;
                                    moveY = -4f;
                                    parts.add(
                                        new RegionPart("-peak"){{
                                            progress = PartProgress.recoil;
                                            heatProgress = PartProgress.recoil;
                                            heatColor = Color.valueOf("ff6214");
                                            mirror = false;
                                            under = true;
                                            moveY = -8f;
                                        }}
                                    );
                                }}
                        )
                );
            }};
        }};
        rainmaker = new ItemTurret("rainmaker")
        {{
            localizedName = "Rainmaker";
            size = 4;
            health = 3000;
            reload = 60f;
            range = 8 * 50;
            recoil = 12f;
            requirements(Category.turret,with(PvItems.zirconium, 100,PvItems.lithium,150,PvItems.nobelium,150,silicon,50)); //Todo 2
            shoot = new CyclicShootPattern(5,9,45);
            ammo(
                    PvItems.erbium,new BasicBulletType(6,40)
                    {{
                        trailColor = frontColor = backColor = Pal.surge;
                        trailLength = 8;
                        trailWidth = 2;
                        shoot.shotDelay = 0;
                        lifetime = PvUtil.GetRange(this.speed,50);
                    }}
            );
            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-down"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    drawRegion = true;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = true;
                                    under = true;
                                    y = 0;
                                    x = 0;
                                    moveY = -2f;
                                    moveRot = 1f;
                                }},
                                new RegionPart("-up"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    drawRegion = true;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = true;
                                    under = true;
                                    y = 0;
                                    x = 0;
                                    moveX = 3f;
                                    moveRot = 1f;
                                }}
                        )
                );
            }};
        }};
        marksman = new ContinuousTurret("marksman")
        {{
            localizedName = "Marksman";
            size = 4;
            consumePower(30);
            range = 8 * 60;
            rotateSpeed = 0.2f;
            aimChangeSpeed = 1.3f;
            health = 2100;
            inaccuracy = 0;
            shootX = 0;
            shootY = 0;
            unitSort = UnitSorts.strongest;
            heatRequirement = 30;
            shootCone = 360;

            requirements(Category.turret,with(PvItems.zirconium, 100,PvItems.lithium,300,PvItems.nobelium,100,PvItems.platinum,100)); //Todo 2

            shootType = new PointLaserBulletType()
                    {{
                        lightColor = color = Pal.techBlue;
                                trailLength = 200;
                        trailWidth = 2;
                        trailColor = Pal.sap;
                        damage = 750;
                        maxHeatEfficiency = 7;
                        buildingDamageMultiplier = 0.001f;
                    }};
            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-l"){{
                                    progress = PartProgress.warmup;
                                    heatProgress = PartProgress.warmup;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = -1f;
                                    moveX = -3f;
                                    moveRot = 1f;
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.warmup;
                                    heatProgress = PartProgress.warmup;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = -1f;
                                    moveX = 3f;
                                    moveRot = -1f;
                                }}
                        )
                );
            }};
        }};
        nuero = new ItemTurret("nuero")
        {{
            requirements(Category.turret,with(PvItems.zirconium, 120,PvItems.lithium,100,PvItems.platinum,50,silicon,200)); //Todo 2
            localizedName = "Nuero";
            reload = 4*60;
            inaccuracy = 2;
            recoilTime = 10;
            size = 4;
            health = 3000;
            minWarmup = 0.9f;
            range = 62*8;
            shootY = 16;
            recoil = 8;
            heatRequirement = 8;
            maxHeatEfficiency = 4;
            shootCone = 30;
            ammo(
                    PvItems.carbonFiber, new BallisticMissileBulletType(GetName("nuero-missile")){{
                        splashDamage = 123f;
                        splashDamageRadius = 73f;
                        buildingDamageMultiplier = 0.5f;
                        hitShake = 1f;
                        homingRange = 24;
                        homingPower = 0.03f;
                        speed = 8;
                        lifetime = 20;
                        height = 24f;
                        //tfec
                        trailLength = 25;
                        trailWidth = 1f;
                        trailColor = targetColor = Color.yellow;
                    }},
                    PvItems.darkMatter, new BallisticMissileBulletType(GetName("nuero-missile")){{
                        //sandbox only for now
                        splashDamage = 423f;
                        splashDamageRadius = 123f;
                        buildingDamageMultiplier = 0.5f;
                        hitShake = 4f;
                        homingRange = 84;
                        homingPower = 0.01f;
                        speed = 10;
                        lifetime = 16;
                        height = 24f;
                        //tfec
                        trailLength = 25;
                        trailWidth = 1f;
                        trailColor = targetColor = Color.valueOf("011414");
                    }});



            shoot = new ShootAlternate(20);
            shoot.shotDelay = 10;
            shoot.shots = 4;
            recoils = 2;
            drawer = new DrawTurret(GetName("Pov")){{

                for(int i = 0; i < 2; i++){
                    int f = i;
                    parts.add(new RegionPart("-barrel-" + (i == 0 ? "l" : "r")){{
                        progress = PartProgress.recoil;
                        heatProgress = PartProgress.recoil;
                        heatColor = Color.valueOf("ff6214");
                        mirror = false;
                        recoilIndex = f;
                        under = false;
                        moveY = -4f;
                    }});
                }
                parts.addAll(
                        Seq.with(
                                new RegionPart("-top"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = 0f;
                                    moveX = 0f;
                                    moveRot = 0;
                                }}
                        )
                );
            }
            };
            limitRange();
        }};
        hel = new LiquidTurret("hel")
        {
            {
                requirements(Category.turret, with(PvItems.zirconium, 50,PvItems.lithium,150,PvItems.carbonFiber,30)); //Todo 2
                localizedName = "Hel";
                description = "Crystalizes liquid nitrogen to turn them into deadly projectiles with armor piercing";
                reload = 60 / 1;
                inaccuracy = 0;
                size = 4;
                minWarmup = 0.8f;
                health = 2800;
                range = 8 * 60;
                shootY = 16;
                recoil = 4;
                ammo(
                        PvLiquids.liquidNitrogen, new BasicBulletType(1,140) {{
                            lifetime = PvUtil.GetRange(1,60);
                            trailLength = 20;
                            trailWidth = 2;
                            status = StatusEffects.freezing;
                            statusDuration = 120;
                            trailColor = backColor = lightColor = Pal.lancerLaser;
                            trailInterval = 10;
                            intervalRandomSpread = 90;
                            intervalAngle = -45;
                            intervalBullets = 1;
                            buildingDamageMultiplier = 0.5f;
                            fragRandomSpread = 4;
                            fragSpread = 90f/8f;
                            fragAngle = 0;
                            fragBullets = 8;
                            fragVelocityMax = 1.8f;
                            fragVelocityMin = 0.8f;
                            pierce = true;
                            pierceCap = 3;
                            pierceBuilding = true;
                            fragBullet = intervalBullet = new BasicBulletType(4,75)
                            {{
                                trailLength = 10;
                                trailWidth = 2;
                                trailColor = backColor = lightColor = Pal.lancerLaser;
                                lifetime = PvUtil.GetRange(4,40);
                                drag = 0.1f;
                            }};

                        }}
                );
                drawer = new DrawTurret(GetName("Pov")) {{
                    parts.addAll(
                            Seq.with(
                                    new RegionPart("-l"){{
                                        progress = PartProgress.warmup;
                                        heatProgress = PartProgress.warmup;
                                        heatColor = Color.valueOf("ff6214");
                                        mirror = false;
                                        under = false;
                                        moveY = 1f;
                                        moveX = -2;
                                        moveRot = 15;
                                    }},
                                    new RegionPart("-r"){{
                                        progress = PartProgress.warmup;
                                        heatProgress = PartProgress.warmup;
                                        heatColor = Color.valueOf("ff6214");
                                        mirror = false;
                                        under = false;
                                        moveY = 1f;
                                        moveX = 2;
                                        moveRot = -15;
                                    }}
                            )
                    );
                }};
            }};
        spring = new LiquidTurret("spring")
        {{
            requirements(Category.turret, with(PvItems.zirconium, 100,PvItems.lithium,300,PvItems.nobelium,100,silicon,300)); //Todo 2
            localizedName = "Spring";
            health = 2600;
            size = 4;
            reload = 60f/1.5f;
            heatRequirement = 12;
            maxHeatEfficiency = 10;
            range = 47*8;
            ammo(
                    Liquids.water,new LaserBulletType(24) {{
                        colors = new Color[3];
                        colors[0] = colors[1] = colors[2] = Liquids.water.color;
                        length = 20*8;
                        width *= 1.4f;
                        status = StatusEffects.wet;
                        statusDuration = 90;
                    }},
                    PvLiquids.kerosene, new LaserBulletType(52){{
                        colors = new Color[3];
                        colors[0] = colors[1] = colors[2] = PvLiquids.kerosene.color;
                        length = 20*8;
                        width *= 1.4f;
                        status = PvStatusEffects.doused;
                        statusDuration = 90;

                    }}
            );
            drawer = new DrawTurret(GetName("Pov"));
        }};
    }
    public static void loadSize5()
    {
        javelin = new PowerTurret("javelin")
        {{
            requirements(Category.turret,with(PvItems.zirconium, 1500,PvItems.lithium,400,PvItems.carbonFiber,30,PvItems.platinum,100,PvItems.nobelium,100)); //Todo 2
            localizedName = "Javelin";
            size = 5;
            health = 8700;
            consumePower(1350f/60f);
            liquidCapacity = 60;
            range = 65*8;
            reload = 60f/0.8f;
            minWarmup = 0.7f;
            targetAir = true;
            targetGround = true;
            shootY = 10;
            recoil = 10;
            consumeLiquid(PvLiquids.xenon,30/60f);
            shootType = new BasicBulletType(20,830)
            {{
                lifetime = PvUtil.GetRange(speed,65);
                collidesAir = true;
                collidesGround = true;
                pierce = true;
                pierceCap = 10;
                pierceBuilding = true;
                trailInterval = 0;
                trailChance = Integer.MAX_VALUE;
                bulletInterval = 3;
                intervalBullets = 2;
                trailLength = 40;
                trailWidth = 2;
                trailColor = lightColor = backColor = Pal.lancerLaser;
                trailEffect = PvEffects.waveBulletJavelin;
                trailRotation = true;
                fragBullets = 7;
                fragBullet = intervalBullet = new LightningBulletType()
                {{
                    damage = 38;
                    lightningLength = 15;

                }};
            }};
            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-l"){{
                                    progress = PartProgress.warmup;
                                    heatProgress = PartProgress.warmup;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = 1f;
                                    moveX = -2;
                                    moveRot = 15;
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.warmup;
                                    heatProgress = PartProgress.warmup;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = 1f;
                                    moveX = 2;
                                    moveRot = -15;
                                }}
                        )
                );
            }};
        }};
        xacto = new ItemTurret("xacto")
        {{
            requirements(Category.turret,with(PvItems.zirconium, 200,PvItems.erbium,1000,PvItems.carbonFiber,50,PvItems.nobelium,200)); //Todo 2
            localizedName = "X-acto";
            size = 5;
            health = 8450;
            range = 58*8;
            liquidCapacity = 50;
            targetAir = true;
            targetGround = true;
            reload = 60f/4.4f;
            recoil = 4;
            coolant = consumeCoolant(0.1f);
            shoot = new ShootAlternate(10);
            ammo(
                    PvItems.nobelium,new BasicBulletType(5,130)
                    {
                        {
                            lifetime = PvUtil.GetRange(this.speed, 50);
                            rangeChange = -8*8;
                            trailLength = 15;
                            trailWidth = 2;
                            trailColor = backColor = lightColor = Pal.heal;
                            fragBullets = 4;
                            fragRandomSpread = 45;
                            fragBullet = new BasicBulletType(7, 10) {{
                                lifetime = PvUtil.GetRange(this.speed, 12);
                                trailLength = 15;
                                trailWidth = 2;
                                homingPower = 0.008f;
                                homingRange = 12;
                                trailColor = backColor = lightColor = Pal.heal;
                            }};
                        }},
                    PvItems.carbonFiber,new BasicBulletType(5,215)
                    {
                        {
                            lifetime = PvUtil.GetRange(this.speed, 58);
                            pierce = true;
                            pierceCap = 2;
                            trailLength = 15;
                            trailWidth = 2;
                            trailColor = backColor = lightColor = Pal.heal;
                        }}
            );
            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-l"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = -2.66f;
                                    moveRot = 1f;
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = -2.66f;
                                    moveRot = -1f;
                                }},
                                new RegionPart("-lb"){{
                                    progress = PartProgress.warmup;
                                    heatProgress = PartProgress.warmup;
                                    heatColor = Color.valueOf("ff6214");
                                    layer = Layer.turret-1.2f;
                                    turretHeatLayer = Layer.turret-0.1f;
                                    mirror = false;
                                    moveY = -2.5f;
                                    moveX = -2.5f;
                                }},
                                new RegionPart("-rb"){{
                                    progress = PartProgress.warmup;
                                    heatProgress = PartProgress.warmup;
                                    heatColor = Color.valueOf("ff6214");
                                    layer = Layer.turret-1.2f;
                                    turretHeatLayer = Layer.turret-0.1f;
                                    mirror = false;
                                    moveY = -2.5f;
                                    moveX = 2.5f;
                                }}
                        )
                );
            }};
        }};
        reaper = new ItemTurret("reaper")
        {{
            localizedName = "Reaper";
            description = "The Bringer of death to even the strongest T4. its only downside is that it can easily be overrun with a lot of enemies.";
            size = 5;
            health = 8500;
            minWarmup = 0.8f;
            consumePower(1100f/60f);
            liquidCapacity = 60;
            reload = 60f;
            inaccuracy = 2;
            range = 8*55;
            shootCone = 90;
            targetAir = true;
            targetGround = false;
            coolantMultiplier = 1.5f;
            recoil = 5;
            shake = 1;
            requirements(Category.turret,with(PvItems.platinum, 500,PvItems.erbium,300,PvItems.carbonFiber,100,PvItems.nobelium,200,silicon,100)); //Todo 2
            ammo(
                    PvItems.erbium,new BasicBulletType(8,1650)
                    {{
                        lifetime = PvUtil.GetRange(this.speed,55);
                        reloadMultiplier = 0.7f;
                        collidesAir = true;
                        collidesGround = false;
                        spin = 1;
                        width = 10;
                        height = 10;
                        backColor = trailColor = Pal.redderDust;
                        trailWidth = 2;
                        trailLength = 40;
                        trailRotation = true;
                        fragBullets = 5;
                        bulletInterval = 2;
                        intervalBullets = 5;
                        weaveRandom = true;
                        homingPower = 0.01f;
                        weaveScale = 1;
                        weaveMag = 1;
                        intervalBullet = new LightningBulletType()
                        {{
                            lightningColor = lightColor = Pal.redderDust;
                            lightningDamage = 15;
                            lightningLength = 10;
                        }};
                        fragBullet = new LightningBulletType()
                        {{
                            lightningColor = lightColor = Pal.redderDust;
                            lightningDamage = 15;
                            lightningLength = 20;
                        }};
                    }}
            );
            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        new RegionPart("-l"){{
                            progress = PartProgress.warmup;
                            heatProgress = PartProgress.recoil;
                            heatColor = Color.valueOf("ff6214");
                            mirror = false;
                            under = false;
                            moveY = -2.5f;
                            moveX = -1.5f;
                            moveRot = 10f;
                        }},
                        new RegionPart("-r"){{
                            progress = PartProgress.warmup;
                            heatProgress = PartProgress.recoil;
                            heatColor = Color.valueOf("ff6214");
                            mirror = false;
                            under = false;
                            moveY = -2.5f;
                            moveX = 1.5f;
                            moveRot = -10f;
                        }},
                        new ShapePart(){{
                            progress = PartProgress.warmup;
                            rotateSpeed = -5;
                            color = Pal.lightishGray;
                            sides = 7;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = 1.6f;
                            radius = 12f;
                            layer = Layer.effect;
                            y = -25;
                        }},
                        new ShapePart(){{
                            progress = PartProgress.warmup;
                            rotateSpeed = 4;
                            color = Pal.lightishGray;
                            sides = 4;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = 1.6f;
                            radius = 8f;
                            layer = Layer.effect;
                            y = -25;
                        }},
                        new ShapePart(){{
                            progress = PartProgress.warmup;
                            rotateSpeed = -5;
                            color = Pal.lightishGray;
                            sides = 20;
                            hollow = true;
                            stroke = 0f;
                            strokeTo = 1.6f;
                            radius = 14f;
                            layer = Layer.effect;
                            y = -25;
                        }},
                        new HaloPart(){{
                            progress = PartProgress.warmup;
                            color = Pal.lightishGray;
                            sides = 5;
                            hollow = true;
                            shapes = 3;
                            stroke = 0f;
                            strokeTo = 4f;
                            radius = 3f;
                            haloRadius = 13f;
                            haloRotateSpeed = 1;
                            layer = Layer.effect;
                            y = -25;
                        }}
                );
            }};
        }};
    }
    public static void loadSize6()
    {
        jaeger = new ItemTurret("jaeger")
        {{
            requirements(Category.turret,with(PvItems.zirconium, 420,PvItems.lithium,500,PvItems.platinum,500,silicon,400,PvItems.carbonFiber,100)); //Todo 2
            localizedName = "Jaeger";
            maxAmmo = 40;
            ammoPerShot = 10;
            reload = 60 / 0.12f;
            inaccuracy = 10;
            recoilTime = 10;
            size = 6;
            health = 7000;
            range = 98*8;
            shootY = 14;
            soundPitchMin = 0.8f;
            soundPitchMax = 0.85f;
            recoil = 8;
            heatRequirement = 40;
            maxHeatEfficiency = 2;
            shake = 8;
            shootCone = 40;
            ammo(
                    PvItems.carbonFiber, new BallisticMissileBulletType(GetName("jaeger-missile")){{
                        splashDamage = 0f;
                        splashDamageRadius = 60f;
                        buildingDamageMultiplier = 0.5f;
                        hitShake = 1f;
                        homingRange = 24;
                        homingPower = 0.03f;
                        hitSoundVolume = 2;
                        targetRadius = 8;
                        speed = 7;
                        lifetime = 40;
                        height = 36f;
                        trailLength = 25;
                        trailWidth = 1f;
                        trailColor = targetColor = Pal.techBlue;
                        fragBullets = 6;
                        soundPitchMin = 0.8f;
                        soundPitchMax = 0.85f;
                        fragSpread = 8;
                        fragRandomSpread = 36;
                        fragBullet = new BallisticMissileBulletType(GetName("nuero-missile")){{
                            splashDamage = 30f;
                            splashDamageRadius = 40f;
                            buildingDamageMultiplier = 0.5f;
                            hitShake = 1f;
                            homingRange = 24;
                            homingPower = 0.03f;
                            speed = 10;
                            lifetime = 40;
                            height = 24f;
                            trailLength = 25;
                            trailWidth = 1f;
                            trailColor = targetColor = Pal.techBlue;
                        }};
                    }},
                    PvItems.darkMatter, new BallisticMissileBulletType(GetName("jaeger-missile")){{
                        splashDamage = 0f;
                        splashDamageRadius = 60f;
                        buildingDamageMultiplier = 0.5f;
                        hitShake = 1f;
                        homingRange = 24;
                        homingPower = 0.03f;
                        hitSoundVolume = 2;
                        targetRadius = 8;
                        speed = 9;
                        lifetime = 36;
                        height = 36f;
                        trailLength = 25;
                        trailWidth = 1f;
                        trailColor = targetColor = Color.valueOf("011414");
                        fragBullets = 6;
                        soundPitchMin = 0.8f;
                        soundPitchMax = 0.85f;
                        fragSpread = 8;
                        fragRandomSpread = 36;
                        fragBullet = new BallisticMissileBulletType(GetName("nuero-missile")){{
                            splashDamage = 230f;
                            splashDamageRadius = 140f;
                            buildingDamageMultiplier = 0.5f;
                            hitShake = 1f;
                            homingRange = 24;
                            homingPower = 0.03f;
                            speed = 12;
                            lifetime = 36;
                            height = 24f;
                            trailLength = 25;
                            trailWidth = 1f;
                            trailColor = targetColor = Color.valueOf("011414");
                        }};
                    }});


            shoot = new ShootAlternate(20);
            shoot.shotDelay = 8;
            shoot.shots = 4;
            recoils = 8;
            drawer = new DrawTurret(GetName("Pov")){{

                for(int i = 0; i < 2; i++){
                    int f = i;
                    parts.add(new RegionPart("-barrelt-" + (i == 0 ? "l" : "r")){{
                        progress = PartProgress.recoil;
                        heatProgress = PartProgress.recoil.delay(0.5f);
                        heatColor = Color.valueOf("ff6214");
                        mirror = false;
                        recoilIndex = f;
                        under = false;
                        moveY = -5f;
                        layerOffset = 0.5f;
                    }});
                }
                for(int i = 0; i < 2; i++){
                    int f = i;
                    parts.add(new RegionPart("-barrelb-" + (i == 0 ? "l" : "r")){{
                        progress = PartProgress.recoil;
                        heatProgress = PartProgress.recoil;
                        heatColor = Color.valueOf("ff6214");
                        mirror = false;
                        recoilIndex = f;
                        under = false;
                        moveY = -3.5f;
                        layerOffset = -0.5f;
                    }});
                }
            }
            };
            limitRange();
        }};
        shuttle = new ItemTurret("shuttle")
        {{
            requirements(Category.turret,with(PvItems.platinum, 200,PvItems.erbium,600,PvItems.carbonFiber,50,PvItems.nobelium,500,silicon,500)); //Todo 2
            localizedName = "Shuttle";
            shoot = new ShootAlternate(8);
            reload = 60f/4.6f;
            inaccuracy = 2;
            size = 6;
            health = 12000;
            minWarmup = 0.9f;
            consumePower(840f/60f);
            range = 68*8;
            shootY = 16;
            recoil = 4;
            recoils = 2;
            ammo(
                    PvItems.carbonFiber,new BasicBulletType(8,200)
                    {{
                        sprite = "missile-large";
                        status = PvStatusEffects.resiliant;
                        statusDuration = 600;
                        shootEffect = Fx.shootSmokeSquareBig;
                        smokeEffect = Fx.shootSmokeDisperse;
                        trailWidth = 4;
                        width *= 2;
                        height *= 2;
                        trailLength = 40;
                        lifetime = PvUtil.GetRange(8,68);
                        hitShake = despawnShake = 4;
                        shake = 2;
                        backColor = trailColor = lightColor = Pal.lighterOrange;
                        trailInterval = 5;
                        trailChance = 90f;
                        weaveMag = 2;
                        weaveScale = 5f;
                        weaveRandom = true;
                        trailEffect = Fx.missileTrail;
                        pierceCap = 4;
                        pierce = true;
                        laserAbsorb = true;
                        buildingDamageMultiplier = 0.1f;
                        fragBullets = 8;
                        homingPower = 0.02f;
                        homingRange = 87*8f;
                        fragBullet = new LightningBulletType()
                        {{
                            lightColor = lightningColor = Pal.lighterOrange;
                            lightningDamage = 20;
                            damage = 20;
                            lightningLength = 8*3;
                            status = PvStatusEffects.resiliant;
                            statusDuration = 180;
                        }};
                    }},
                    PvItems.rushAlloy,new BasicBulletType(16,1000)
                    {{
                        sprite = "missile-large";
                        rangeChange = 8*10;
                        status = PvStatusEffects.resiliant;
                        statusDuration = 600;
                        shootEffect = Fx.shootSmokeSquareBig;
                        smokeEffect = Fx.shootSmokeDisperse;
                        trailWidth = 4;
                        width *= 2;
                        height *= 2;
                        trailLength = 40;
                        lifetime = PvUtil.GetRange(16,78);
                        hitShake = despawnShake = 4;
                        shake = 2;
                        backColor = trailColor = lightColor = Pal.lighterOrange;
                        trailInterval = 5;
                        trailChance = 90f;
                        weaveMag = 2;
                        weaveScale = 5f;
                        weaveRandom = true;
                        trailEffect = Fx.missileTrail;
                        pierceCap = 10;
                        pierce = true;
                        laserAbsorb = true;
                        buildingDamageMultiplier = 0.1f;
                        fragBullets = 8;
                        homingPower = 0.02f;
                        homingRange = 87*8f;

                        fragBullet = new LightningBulletType()
                        {{
                            lightColor = lightningColor = Pal.lighterOrange;
                            lightningDamage = 20;
                            damage = 100;
                            lightningLength = 8*3;
                            status = PvStatusEffects.resiliant;
                            statusDuration = 180;
                        }};
                    }}
            );
            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        Seq.with(
                                //Arms and Barrels
                                new RegionPart("-l1"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    recoilIndex = 0;
                                    under = false;
                                    moveY = -6f;
                                }},
                                new RegionPart("-r1"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    recoilIndex = 1;
                                    under = false;
                                    moveY = -6f;
                                }},
                                new RegionPart("-l2"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = -2f;
                                    moveX = -4f;
                                    moveRot = 3;
                                }},
                                new RegionPart("-r2"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = -2f;
                                    moveX = 4f;
                                    moveRot = -3;
                                }},
                                new RegionPart("-l3"){{
                                    progress = PartProgress.warmup;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = 4f;
                                    moveX = -10f;
                                    moveRot = 30;
                                }},
                                new RegionPart("-r3"){{
                                    progress = PartProgress.warmup;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = 4f;
                                    moveX = 10f;
                                    moveRot = -30;
                                }},
                                //Summoning Circle
                                new ShapePart(){{
                                    progress = PartProgress.warmup;
                                    rotateSpeed = -5;
                                    color = Pal.lighterOrange;
                                    sides = 4;
                                    hollow = true;
                                    stroke = 0f;
                                    strokeTo = 1.6f;
                                    radius = 10f;
                                    layer = Layer.effect;
                                    y = -25;
                                }},
                                new ShapePart(){{
                                    progress = PartProgress.warmup;
                                    rotateSpeed = 5;
                                    color = Pal.lighterOrange;
                                    sides = 4;
                                    hollow = true;
                                    stroke = 0f;
                                    strokeTo = 1.6f;
                                    radius = 6f;
                                    layer = Layer.effect;
                                    y = -25;
                                }},
                                new ShapePart(){{
                                    progress = PartProgress.warmup;
                                    rotateSpeed = -5;
                                    color = Pal.lighterOrange;
                                    sides = 20;
                                    hollow = true;
                                    stroke = 0f;
                                    strokeTo = 1.6f;
                                    radius = 11f;
                                    layer = Layer.effect;
                                    y = -25;
                                }},
                                new HaloPart(){{
                                    progress = PartProgress.warmup;
                                    color = Pal.lighterOrange;
                                    sides = 3;
                                    hollow = true;
                                    shapes = 3;
                                    stroke = 0f;
                                    strokeTo = 4f;
                                    radius = 1f;
                                    haloRadius = 13f;
                                    haloRotateSpeed = 1;
                                    layer = Layer.effect;
                                    y = -25;
                                }}
                        )
                );
            }};
        }};
        xterminium = new ItemTurret("xterminium")
        {{
            requirements(Category.turret,BuildVisibility.sandboxOnly,with(PvItems.platinum, 9999,PvItems.erbium,9999,PvItems.carbonFiber,9999,PvItems.nobelium,9999,silicon,9999,PvItems.copium,1)); //Todo 2
            localizedName = "X-terminium";
            shoot = new ShootAlternate(20);
            reload = 200;
            inaccuracy = 2;
            recoilTime = 30;
            size = 8;
            health = 100000;
            minWarmup = 0.7f;
            range = 100*8;
            shootWarmupSpeed = 1/240f;
            warmupMaintainTime = 100f;
            shootY = 16;
            recoil = 8;
            heatRequirement = 1000;
            maxHeatEfficiency = 20;
            ammo(
                    PvItems.carbonFiber,new ArtilleryBulletType(2,0)
                    {{
                        fragBullets = 10;
                        fragRandomSpread = 0;
                        fragSpread = 36;
                        fragVelocityMin = 1;
                        trailLength = 16;
                        lifetime = PvUtil.GetRange(2, 100);
                        frontColor = backColor = trailColor = Pal.slagOrange;
                        fragBullet = new BasicBulletType(3,200){{
                            drag = 1/100f;
                            lifetime = 60;
                            trailLength = 6;
                            frontColor = backColor = trailColor = Pal.redDust;
                            fragBullets = 10;
                            fragRandomSpread = 0;
                            fragSpread = 36;
                            fragVelocityMin = 1;
                            fragBullet = new BasicBulletType(5, 300){{
                                frontColor = backColor = trailColor = Pal.redderDust;
                                trailLength = 32;
                                lifetime = 120;
                                homingPower = 1;
                                homingRange = 100*8;
                                fragBullets = 5;
                                fragRandomSpread = 0;
                                fragSpread = 36;
                                fragVelocityMin = 1;
                                fragBullet = new BasicBulletType(5, 300){{
                                    frontColor = backColor = trailColor = Pal.redderDust;
                                    trailLength = 32;
                                    lifetime = 60;
                                    homingPower = 1;
                                    homingRange = 100*8;
                                    fragBullets = 4;
                                    fragRandomSpread = 0;
                                    fragSpread = 36;
                                    fragVelocityMin = 1;
                                    fragBullet = new BasicBulletType(5, 300){{
                                        frontColor = backColor = trailColor = Pal.redderDust;
                                        trailLength = 32;
                                        lifetime = 30;
                                        homingPower = 1;
                                        homingRange = 100*8;
                                    }};
                                }};
                            }};
                        }};
                    }}
            );
            researchCost = with(
                PvItems.copium,1
            );
            recoils = 2;
            drawer = new DrawTurret(GetName("Pov")){{
                parts.addAll(
                        Seq.with(
                                //Arms and Barrels
                                new RegionPart("-l"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    recoilIndex = 0;
                                    under = false;
                                    y = 14;
                                    moveY = -4f;
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    recoilIndex = 0;
                                    under = false;
                                    y = 14;
                                    moveY = -4f;
                                }},

                                //Summoning Circles
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
                                    y = 14;
                                    x = -40;
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
                                    y = 14;
                                    x = -40;
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
                                    y = 14;
                                    x = -40;
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
                                    y = 14;
                                    x = -40;
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
                                    y = 14;
                                    x = 40;
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
                                    y = 14;
                                    x = 40;
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
                                    y = 14;
                                    x = 40;
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
                                    y = 14;
                                    x = 40;
                                }}
                        )
                );
            }
            };
        }};
    }
}