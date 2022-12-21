package viscott.content;

import arc.fx.filters.FxaaFilter;
import arc.graphics.Color;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.entities.bullet.*;
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
import viscott.utilitys.PvUtil;

import static mindustry.type.ItemStack.*;

public class PvTurrets{
    public static Block
            splinter,shatter,euro,snap;

    public static void load(){
        splinter = new ItemTurret("splinter"){{
            localizedName = "Spliter";
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
            coolant = consumeCoolant(0.2f);
            coolantMultiplier = 1.2f;
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
            shootType = new MissileBulletType(6,26)
            {{
                lifetime = PvUtil.GetRange(this.speed,31);
                trailWidth = 2;
                trailLength = 10;
                trailColor = backColor = frontColor = Pal.lancerLaser;
                hitEffect = despawnEffect = Fx.hitBeam;
                homingRange = 10*8;
                homingDelay = 1f;
                homingPower = 0.1f;
                fragBullet = new BasicBulletType(4,6)
                {{
                    lifetime = PvUtil.GetRange(this.speed,6);
                    trailLength = 6;
                    trailWidth = 2;
                    trailColor = backColor = frontColor = Pal.darkFlame;
                }};
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
            requirements(Category.turret, with(Items.silicon, 450)); //Todo
            range = 87*8;
            localizedName = "Euro";
            inaccuracy = 10;
            reload = 120;
            size = 2;
            ammo(
                    Items.silicon, new BasicBulletType(0f, 1){{
                        shootEffect = Fx.shootBig;
                        smokeEffect = Fx.shootSmokeMissile;
                        ammoMultiplier = 1f;

                        spawnUnit = new MissileUnitType("euro-missile"){{
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
                                bullet = new ExplosionBulletType(42f, 4.8f*8f){{
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
                                    fragBullets = 7;
                                    fragBullet = new ArtilleryBulletType(2f, 32){{
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

                            abilities.add(new MoveEffectAbility(){{
                                effect = Fx.missileTrailSmoke;
                                rotation = 180f;
                                y = -9f;
                                color = Color.grays(0.6f).lerp(Pal.redLight, 0.5f).a(0.4f);
                                interval = 10f;
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
                                    moves.add(new PartMove(PartProgress.recoil, 1f, 0f, 0f));
                                }},
                                new RegionPart("-r"){{
                                    progress = PartProgress.recoil;
                                    heatProgress = PartProgress.recoil;
                                    heatColor = Color.valueOf("ff6214");
                                    mirror = false;
                                    under = false;
                                    moveX = 1f;
                                    moveRot = -7f;
                                    moves.add(new PartMove(PartProgress.recoil, -1f, 2f, 0f));
                                }}
                        )
                );
            }};
        }};
        snap = new LiquidTurret("snap")
        {{
            requirements(Category.turret, with(Items.silicon, 100)); //Todo
            localizedName = "Snap";
            size = 2;
            reload = 60f/4f;
            ammo(
                    Liquids.water,new LiquidBulletType(Liquids.water){{
                        knockback = 0.7f;
                        damage = 15;
                        drag = 0.01f;
                        layer = Layer.bullet - 2f;
                    }},
                    PvLiquids.kerosene, new LiquidBulletType(PvLiquids.kerosene){{
                        damage = 24;
                        drag = 0.01f;
                    }}
            );
            drawer = new DrawTurret(PvUtil.GetName("Pov"));
        }};
    }
}
