package viscott.content;

import arc.fx.filters.FxaaFilter;
import arc.graphics.Color;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.ExplosionEffect;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.type.unit.MissileUnitType;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.content.*;
import mindustry.world.blocks.defense.turrets.LiquidTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.draw.DrawTurret;
import viscott.content.shootpatterns.CyclicPatternRainmaker;
import viscott.content.shootpatterns.CyclicPatternStriker;
import viscott.content.shootpatterns.CyclicShootPattern;
import viscott.utilitys.PvUtil;

import static mindustry.content.Items.silicon;
import static mindustry.type.ItemStack.*;

public class PvTurrets{
    public static Block
            splinter,shatter,euro,snap,hourglass,phantom,razor,rainmaker,striker,marksman;

    public static void load(){
        splinter = new ItemTurret("splinter"){{
            localizedName = "Splinter";
            size = 2;
            health = 875;
            range = 26 * 8;
            recoil = 2;
            rotateSpeed = 6f;
            requirements(Category.turret, with(PvItems.zirconium, 75));
            ammo(
                    PvItems.zirconium,  new BasicBulletType(6f, 14){{
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
                    }},
                    PvItems.platinum,  new BasicBulletType(6f, 37){{
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
                    }},
                    PvItems.nobelium,  new BasicBulletType(6f, 32){{
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
                    }}
            );

            shoot = new ShootSpread(3, 1f);

            shootY = 6f;
            reload = 60f;
            ammoUseEffect = Fx.casing1;
            inaccuracy = 1f;
            coolant = consumeCoolant(0.1f);
            researchCostMultiplier = 0.05f;
            drawer = new DrawTurret(PvUtil.GetName("Pov")){{
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
            requirements(Category.turret, with(PvItems.zirconium, 75)); //Todo
            range = 31*8;
            health = 800;
            size = 2;
            localizedName = "Shatter";
            targetAir = true;
            targetGround = true;
            consumePower(120f/60f);
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
                trailInterval = 3f;
                trailParam = 4f;
                pierceCap = 2;
                fragOnHit = false;
                speed = 5f;
                damage = 26f;
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

            drawer = new DrawTurret(PvUtil.GetName("Pov")){{
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
                                    moves.add(new PartMove(PartProgress.recoil, 0f, 2f, 3f));
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = 1f;
                                    moveRot = -7f;
                                    moves.add(new PartMove(PartProgress.recoil, 0f, 2f, -3f));
                                }}
                        )
                );
            }};

        }};
        euro = new ItemTurret("euro")
        {{
            requirements(Category.turret, with(silicon, 450)); //Todo
            range = 87*8;
            localizedName = "Euro";
            inaccuracy = 10;
            reload = 120;
            size = 2;
            coolant = consumeCoolant(0.1f);
            ammo(
                    silicon, new BasicBulletType(0f, 1){{
                        ammoMultiplier = 1f;

                        spawnUnit = new MissileUnitType("euro-missile1"){{
                            speed = 4.6f;
                            maxRange = 6f;
                            lifetime = PvUtil.GetRange(this.speed,87)+30;
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
                                    shootEffect = new MultiEffect(Fx.massiveExplosion, Fx.scatheExplosion, Fx.scatheLight, new WaveEffect(){{
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
                    }},
                    PvItems.nobelium, new BasicBulletType(0f, 1){{
                        shootEffect = Fx.shootBig;
                        smokeEffect = Fx.shootSmokeMissile;
                        ammoMultiplier = 1f;

                        spawnUnit = new MissileUnitType("euro-missile2"){{
                            speed = 4.6f;
                            maxRange = 6f;
                            lifetime = PvUtil.GetRange(this.speed,87)+30;
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

            drawer = new DrawTurret(PvUtil.GetName("Pov")){{
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
            requirements(Category.turret, with(silicon, 100)); //Todo
            localizedName = "Snap";
            size = 2;
            reload = 60f/1f;
            range = 20*8;
            ammo(
                    Liquids.water,new LaserBulletType(15) {{
                            colors = new Color[3];
                            colors[0] = colors[1] = colors[2] = Liquids.water.color;
                            length = 20*8;
                            status = StatusEffects.wet;
                            statusDuration = 90;

                    }},
                    PvLiquids.kerosene, new LaserBulletType(24){{
                            colors = new Color[3];
                            colors[0] = colors[1] = colors[2] = PvLiquids.kerosene.color;
                            length = 20*8;
                            status = PvStatusEffects.doused;
                            statusDuration = 90;

                    }}
            );
            drawer = new DrawTurret(PvUtil.GetName("Pov"));
        }};
        hourglass = new ItemTurret("hourglass")
        {{
            localizedName = "Hourglass";
            size = 2;
            reload = 60f/2f;
            range = 26*8;
            coolant = consumeCoolant(0.1f);
            requirements(Category.turret,with(Items.copper,1)); //Todo
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
            drawer = new DrawTurret(PvUtil.GetName("Pov")){{
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
        phantom = new ItemTurret("phantom")
        {{
            requirements(Category.turret,with(PvItems.erbium,1)); //Todo
            localizedName = "Phantom";
            size = 3;
            health = 1980;
            consumePower(290f/60f);
            range = 56*8;
            liquidCapacity = 35;
            targetAir = true;
            targetGround = false;
            reload = 60f/0.9f;
            coolant = consumeCoolant(0.1f);
            ammo(
                    PvItems.platinum,new BasicBulletType(8,360)
                    {{
                        lifetime = PvUtil.GetRange(this.speed,56);
                        trailLength = 20;
                        trailWidth = 2;
                        width = 20;
                        height = 20;
                        trailColor = backColor = Pal.gray;

                    }},
                    PvItems.erbium,new BasicBulletType(8,500)
                    {{
                        lifetime = PvUtil.GetRange(this.speed,56);
                        trailLength = 20;
                        trailWidth = 2;
                        width = 20;
                        height = 20;
                        trailColor = backColor = Pal.gray;
                    }}
            );
            drawer = new DrawTurret(PvUtil.GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-l"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.heat;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = -2f;
                                    moveY = -1f;
                                    moveRot = 8f;
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.heat;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = 2f;
                                    moveY = -1f;
                                    moveRot = -8f;
                                }}
                        )
                );
            }};
            limitRange();
        }};
        razor = new ItemTurret("razor")
        {{
            requirements(Category.turret,with(PvItems.erbium,1)); //Todo
            localizedName = "Razor";
            size = 3;
            health = 2175;
            range = 34*8;
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
                        lifetime = PvUtil.GetRange(this.speed,34);
                        pierce = true;
                        pierceCap = 10;
                        trailLength = 15;
                        trailWidth = 2;
                        trailColor = backColor = lightColor = Pal.heal;
                    }},
                    silicon,new BasicBulletType(5,21)
                    {{
                        lifetime = PvUtil.GetRange(this.speed,34);
                        pierce = true;
                        pierceCap = 10;
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
                        lifetime = PvUtil.GetRange(this.speed,34);
                        pierce = true;
                        pierceCap = 10;
                        trailLength = 15;
                        trailWidth = 2;
                        trailColor = backColor = lightColor = Pal.heal;
                        fragBullets = 2;
                        fragRandomSpread = 90;
                        fragBullet = new BasicBulletType(4,15)
                        {{
                            lifetime = PvUtil.GetRange(this.speed,7);
                            trailLength = 15;
                            trailWidth = 2;
                            trailColor = backColor = lightColor = Pal.heal;
                        }};
                    }}
            );
            drawer = new DrawTurret(PvUtil.GetName("Pov")){{
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
        rainmaker = new ItemTurret("rainmaker")
        {{
            localizedName = "Rainmaker";
            size = 4;
            reload = 60f;
            range = 480;
            recoil = 12f;
            requirements(Category.turret,with(Items.copper,1)); //Todo
            ammo(
                    silicon,new BasicBulletType(6,40)
                    {{
                        trailColor = frontColor = backColor = Pal.surge;
                        shoot = new CyclicPatternRainmaker();
                        trailLength = 8;
                        trailWidth = 2;
                        shoot.shotDelay = 0;
                        homingPower = 0.02f;
                        homingRange = 80f;
                        homingDelay = 10f;
                        lifetime = PvUtil.GetRange(this.speed,60);
                    }}
            );
            drawer = new DrawTurret(PvUtil.GetName("Pov")){{
                parts.addAll(
                        parts.add(
                                new RegionPart("-down"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = -1f;
                                    moveRot = 0f;
                                    suffix = "-down";
                                }},
                                new RegionPart("-up"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = 2f;
                                    moveRot = 0f;
                                    suffix = "up";
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
            range = 240;
            inaccuracy = 2;
            requirements(Category.turret,with(Items.copper,1)); //Todo
            ammo(
                    silicon,new BasicBulletType(6,35)
                    {{
                        trailColor = frontColor = backColor = Pal.techBlue;
                        shoot = new CyclicPatternStriker();
                        trailLength = 8;
                        trailWidth = 2;
                        pierce = true;
                        pierceCap = 3;
                        homingPower = 0.03f;
                        shoot.shotDelay = 2.5f;
                        lifetime = PvUtil.GetRange(this.speed,30);
                    }}
            );
            drawer = new DrawTurret(PvUtil.GetName("Pov")){{
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
        marksman = new ItemTurret("marksman")
        {{
            localizedName = "Marksman";
            size = 5;
            reload = 300f;
            range = 1600;
            inaccuracy = 2;
            requirements(Category.turret,with(Items.copper,1)); //Todo
            ammo(
                    silicon,new BasicBulletType(10,300) //ammo should be changed in the future
                    {{
                        trailColor = frontColor = backColor = Pal.missileYellow;
                        trailLength = 200;
                        trailWidth = 2;
                        pierce = true;
                        drag = 0.001f;
                        pierceCap = 9999;
                        homingPower = 0.7f;
                        homingDelay = 1;
                        homingRange = 200;
                        shoot.shotDelay = 2.5f;
                        lifetime = 600;
                    }}
            );
            drawer = new DrawTurret(PvUtil.GetName("Pov")){{
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
        }
    }
