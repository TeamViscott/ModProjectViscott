package viscott.content;

import arc.graphics.Color;
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
import mindustry.entities.part.DrawPart;
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
import mindustry.world.blocks.heat.HeatBlock;
import mindustry.world.blocks.heat.HeatConsumer;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.BuildVisibility;
import viscott.content.shootpatterns.AlternateShootPatternTurret;
import viscott.content.shootpatterns.CyclicPatternRainmaker;
import viscott.content.shootpatterns.CyclicPatternStriker;
import viscott.utilitys.PvUtil;

import static mindustry.content.Items.silicon;
import static mindustry.type.ItemStack.with;

public class PvTurrets{
    public static Block
            splinter,shatter,euro,snap,hourglass,
            phantom,razor,rainmaker,striker,
            marksman, xacto,reaper,shuttle, nuero,
            xterminium,hel
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
    }
    public static void loadSize3()
    {
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
        striker = new ItemTurret("striker")
        {{
            localizedName = "Striker";
            size = 3;
            reload = 90f;
            outlineColor = Color.valueOf("343434");
            outlineRadius = 3;
            range = 25*8;
            shootY = 4f;
            inaccuracy = 2;
            recoil = 4;
            heatColor  = Color.valueOf( "d237a6");

            requirements(Category.turret,with(Items.copper,1)); //Todo
            ammo(
                    silicon, new BasicBulletType(10, 40){{
                        trailColor = frontColor = backColor = Color.valueOf( "d237a6");
                        shoot = new CyclicPatternStriker();
                        trailLength = 8;
                        trailWidth = 2;
                        pierce = true;
                        offset = 4f;
                        pierceCap = 3;
                        homingPower = 0.03f;
                        heatRequirement = 10;
                        maxHeatEfficiency = 3;
                        shoot.shotDelay = 1f;
                        lifetime = PvUtil.GetRange(this.speed,25);
                    }}
            );
            drawer = new DrawTurret(PvUtil.GetName("Pov")){{
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
                                }}
                        )
                );
            }};
        }};
    }
    public static void loadSize4()
    {
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
            range = 800;
            rotateSpeed = 0.2f;
            aimChangeSpeed = 1.3f;
            inaccuracy = 0;
            shootX = 0;
            shootY = 0;
            unitSort = UnitSorts.strongest;
            heatRequirement = 30;
            shootCone = 360;

            requirements(Category.turret,with(Items.copper,1)); //Todo

            shootType = new PointLaserBulletType()
                    {{
                        lightColor = color = Pal.techBlue;
                                trailLength = 2000;
                        trailWidth = 2;
                        trailColor = Pal.sap;
                        damage = 750;
                        maxHeatEfficiency = 7;
                        buildingDamageMultiplier = 0.001f;
                    }};
            drawer = new DrawTurret(PvUtil.GetName("Pov")){{
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
            requirements(Category.turret,with(Items.copper,1)); //Todo
            localizedName = "Nuero";
            shoot = new AlternateShootPatternTurret(20);
            reload = 4*60;
            inaccuracy = 2;
            recoilTime = 10;
            size = 4;
            health = 3000;
            minWarmup = 0.9f;
            range = 87*8;
            shootY = 16;
            recoil = 8;
            heatRequirement = 8;
            maxHeatEfficiency = 4;
            ammo(
                    PvItems.carbonFiber,new BasicBulletType(0,0)
                    {{
                        shoot.shotDelay = 10;
                        shoot.shots = 4;
                        shootEffect = Fx.shootSmall;
                        smokeEffect = Fx.shootSmallSmoke;
                        ammoMultiplier = 1f;

                        spawnUnit = new MissileUnitType("nuero-missile"){{
                            speed = 6.2f;
                            maxRange = 6f;
                            lifetime = PvUtil.GetRange(this.speed,129)+30;
                            outlineColor = Pal.darkOutline;
                            engineColor = trailColor = Pal.redLight;
                            engineLayer = Layer.effect;
                            engineSize = 5.4f;
                            engineOffset = 10f;
                            rotateSpeed = 0.5f;
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
                                shake = 30f;
                                bullet = new ExplosionBulletType(124f, 9.2f*8f){{
                                    hitColor = Pal.missileYellow;
                                    shootEffect = new MultiEffect(Fx.massiveExplosion, Fx.scatheExplosion, Fx.scatheLight, new WaveEffect(){{
                                        lifetime = 30f;
                                        strokeFrom = 4f;
                                        sizeTo = 130f;
                                    }});

                                    collidesAir = true;
                                    buildingDamageMultiplier = 0.3f;

                                    ammoMultiplier = 1f;
                                    fragLifeMin = 0.1f;
                                    fragBullets = 7;
                                    fragBullet = new ArtilleryBulletType(2f, 20){{
                                        drag = 0.02f;
                                        hitEffect = Fx.massiveExplosion;
                                        despawnEffect = Fx.scatheSlash;
                                        knockback = 0.8f;
                                        lifetime = PvUtil.GetRange(this.speed,2.7f);
                                        width = height = 18f;
                                        collidesTiles = false;
                                        splashDamageRadius = 21.6f;
                                        splashDamage = 30f;
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
                        Seq.with(
                                //Arms and Barrels
                                new RegionPart("-l"){{
                                    progress = PartProgress.recoil.mul(((AlternateShootPatternTurret)shoot).selectedBarrel1);
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = -4f;
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.recoil.mul(((AlternateShootPatternTurret)shoot).selectedBarrel2);
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = -4f;
                                }},
                                new RegionPart("-top"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = -4f;
                                    moveX = 0f;
                                    moveRot = 0;
                                }}
                        )
                );
            }
            };
        }};
        hel = new LiquidTurret("hel")
        {
            {
                requirements(Category.turret, with(PvItems.zirconium, 1000, PvItems.carbonFiber, 500, PvItems.nobelium, 100)); //Todo
                localizedName = "Hel";
                description = "Crystalizes liquid nitrogen to turn them into deadly projectiles with armor piercing";
                reload = 60 / 1;
                inaccuracy = 2;
                size = 4;
                health = 2800;
                range = 8 * 60;
                shootY = 16;
                recoil = 4;
                ammo(
                        PvLiquids.liquidNitrogen, new BasicBulletType(4,60) {{
                            lifetime = PvUtil.GetRange(4,60);
                            trailLength = 20;
                            trailWidth = 2;
                            status = StatusEffects.freezing;
                            statusDuration = 120;
                            trailColor = backColor = lightColor = Pal.lancerLaser;
                            trailInterval = 10;
                            intervalRandomSpread = 0;
                            intervalSpread = 360f/8f;
                            intervalAngle = 0;
                            intervalBullets = 8;
                            fragRandomSpread = 0;
                            fragSpread = 360f/8f;
                            fragAngle = 0;
                            fragBullets = 8;
                            fragVelocityMax = 1.8f;
                            fragVelocityMin = 0.8f;
                            fragBullet = intervalBullet = new BasicBulletType(4,40)
                            {{
                                trailLength = 10;
                                trailWidth = 2;
                                trailColor = backColor = lightColor = Pal.lancerLaser;
                                lifetime = PvUtil.GetRange(4,40);
                                drag = 0.1f;
                            }};

                        }}
                );
                drawer = new DrawTurret(PvUtil.GetName("Pov")) {{
                    parts.addAll(
                            Seq.with(
                                    new RegionPart("-barrel") {{
                                        progress = PartProgress.recoil;
                                        moveY = -4;
                                        under= true;
                                        mirror = false;

                                    }}
                            )
                    );
                }};
            }};
    }
    public static void loadSize5()
    {
        xacto = new ItemTurret("xacto")
        {{
            requirements(Category.turret,with(PvItems.erbium,1)); //Todo
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
            shoot = new ShootAlternate(8);
            ammo(
                    PvItems.platinum,new BasicBulletType(5,95)
                    {{
                        lifetime = PvUtil.GetRange(this.speed,58);
                        pierce = true;
                        pierceCap = 10;
                        trailLength = 15;
                        trailWidth = 2;
                        trailColor = backColor = lightColor = Pal.heal;
                    }},
                    silicon,new BasicBulletType(5,87)
                    {{
                        lifetime = PvUtil.GetRange(this.speed,58);
                        pierce = true;
                        pierceCap = 10;
                        trailLength = 15;
                        trailWidth = 2;
                        trailColor = backColor = lightColor = Pal.heal;
                        weaveMag = 2;
                        weaveScale = 2;
                        weaveRandom = true;
                        homingPower = 0.06f;
                    }},
                    PvItems.erbium,new BasicBulletType(5,130)
                    {
                        {
                            lifetime = PvUtil.GetRange(this.speed, 58);
                            pierce = true;
                            pierceCap = 10;
                            trailLength = 15;
                            trailWidth = 2;
                            trailColor = backColor = lightColor = Pal.heal;
                            fragBullets = 4;
                            fragRandomSpread = 45;
                            fragBullet = new BasicBulletType(7, 20) {{
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
                            pierceCap = 10;
                            trailLength = 15;
                            trailWidth = 2;
                            trailColor = backColor = lightColor = Pal.heal;
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
                                    mirror = false;
                                    under = true;
                                    moveY = -2.5f;
                                    moveX = -2.5f;
                                }},
                                new RegionPart("-rb"){{
                                    progress = PartProgress.warmup;
                                    heatProgress = PartProgress.warmup;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = true;
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
            consumePower(1100f/60f);
            liquidCapacity = 60;
            reload = 60f;
            inaccuracy = 2;
            range = 8*85;
            shootCone = 90;
            targetAir = true;
            targetGround = false;
            coolantMultiplier = 1.5f;
            recoil = 5;
            shake = 1;
            requirements(Category.turret,with(Items.copper,1)); //Todo
            ammo(
                    PvItems.platinum,new BasicBulletType(8,1075)
                    {{
                        lifetime = PvUtil.GetRange(this.speed,85);
                        collidesAir = true;
                        collidesGround = false;
                        spin = 1;
                        width = 10;
                        height = 10;
                        backColor = trailColor = Pal.redderDust;
                        trailWidth = 2;
                        trailLength = 40;
                        trailRotation = true;
                        fragBullets = 3;
                        bulletInterval = 2;
                        intervalBullets = 3;
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
                    }},
                    PvItems.erbium,new BasicBulletType(8,1650)
                    {{
                        lifetime = PvUtil.GetRange(this.speed,85);
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
            drawer = new DrawTurret(PvUtil.GetName("Pov")){{
                parts.addAll(
                        new RegionPart("-l"){{
                            progress = PartProgress.recoil;
                            heatProgress = PartProgress.recoil;
                            heatColor = Color.valueOf("ff6214");
                            mirror = false;
                            under = false;
                            moveY = -2f;
                            moveX = -1.5f;
                            moveRot = 10f;
                        }},
                        new RegionPart("-r"){{
                            progress = PartProgress.recoil;
                            heatProgress = PartProgress.recoil;
                            heatColor = Color.valueOf("ff6214");
                            mirror = false;
                            under = false;
                            moveY = -2f;
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
        shuttle = new ItemTurret("shuttle")
        {{
            requirements(Category.turret,with(Items.copper,1)); //Todo
            localizedName = "Shuttle";
            shoot = new AlternateShootPatternTurret(8);
            reload = 60f/4.6f;
            inaccuracy = 2;
            size = 6;
            health = 12000;
            minWarmup = 0.9f;
            consumePower(840f/60f);
            range = 87*8;
            shootY = 16;
            recoil = 4;
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
                        lifetime = PvUtil.GetRange(8,87);
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
                            damage = 20;
                            lightningLength = 8*3;
                            status = PvStatusEffects.resiliant;
                            statusDuration = 180;
                        }};
                    }}
            );
            drawer = new DrawTurret(PvUtil.GetName("Pov")){{
                parts.addAll(
                        Seq.with(
                                //Arms and Barrels
                                new RegionPart("-l1"){{
                                    progress = PartProgress.recoil.mul(((AlternateShootPatternTurret)shoot).selectedBarrel1);
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = -6f;
                                }},
                                new RegionPart("-r1"){{
                                    progress = PartProgress.recoil.mul(((AlternateShootPatternTurret)shoot).selectedBarrel2);
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
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
            requirements(Category.turret,with(Items.copper,1)); //Todo
            localizedName = "X-terminium";
            buildVisibility = BuildVisibility.sandboxOnly;
            shoot = new AlternateShootPatternTurret(20);
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
                        fragOnHit = false;
                        trailLength = 16;
                        lifetime = PvUtil.GetRange(2, 100);
                        frontColor = backColor = trailColor = Pal.slagOrange;
                        fragBullet = new BasicBulletType(3,200){{
                            fragBullets = 3;
                            fragRandomSpread = 0;
                            fragSpread = 36;
                            fragVelocityMin = 1;
                            fragOnHit = false;
                            drag = 1/100f;
                            lifetime = 120;
                            trailLength = 8;
                            frontColor = backColor = trailColor = Pal.redDust;
                            fragBullet = new BasicBulletType(5, 300){{
                                frontColor = backColor = trailColor = Pal.redderDust;
                                trailLength = 32;
                                homingPower = 1;
                                homingRange = 100*8;
                                lifetime = PvUtil.GetRange(5, 100);
                            }};
                        }};
                    }}
            );
            drawer = new DrawTurret(PvUtil.GetName("Pov")){{
                parts.addAll(
                        Seq.with(
                                //Arms and Barrels
                                new RegionPart("-l"){{
                                    progress = PartProgress.recoil.mul(((AlternateShootPatternTurret)shoot).selectedBarrel1);
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveY = -4f;
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.recoil.mul(((AlternateShootPatternTurret)shoot).selectedBarrel2);
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
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
                                    x = 40;
                                }}
                        )
                );
            }
            };
        }};
    }
}