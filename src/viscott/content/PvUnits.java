package viscott.content;

import arc.graphics.Color;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.ai.UnitCommand;
import mindustry.ai.types.HugAI;
import mindustry.ai.types.MinerAI;
import mindustry.ai.types.RepairAI;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.abilities.RepairFieldAbility;
import mindustry.entities.abilities.ShieldArcAbility;
import mindustry.entities.abilities.SpawnDeathAbility;
import mindustry.entities.abilities.StatusFieldAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.ExplosionEffect;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.effect.WrapEffect;
import mindustry.entities.part.*;
import mindustry.entities.pattern.ShootHelix;
import mindustry.entities.pattern.ShootSpread;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.ItemAmmoType;

import arc.math.geom.*;
import arc.math.*;

import mindustry.type.unit.MissileUnitType;
import mindustry.type.weapons.RepairBeamWeapon;
import viscott.content.shootpatterns.CyclicShootPattern;
import viscott.gen.CoinUnit;
import viscott.gen.weapons.LinkedWeapon;
import viscott.gen.weapons.RandWeapon;
import viscott.types.abilities.EnemyStatusFieldAbility;
import viscott.content.shootpatterns.ShootSpreadBounce;
import viscott.gen.FrogUnit;
import viscott.gen.ai.FrireAI;
import viscott.types.*;
import viscott.types.abilities.*;
import viscott.world.bullets.*;
import viscott.utilitys.PvUtil;
import viscott.world.draw.ChangeRegionPart;
import viscott.world.draw.FreeRegionPart;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;
import static mindustry.Vars.tilePayload;

public class PvUnits {
    public static UnitType
        /*Core Units*/micro,infrared, spectrum,
            /*nullis*/shadow,vessel,shell,puppet,
            /*xeal*/amp,volt,watt,
            /*malakai*/substance,
            /*psy*/warden,
            /*yggdrasil*/wood,
            /*unix*/amcro,
        /*Flying Ion Path*/ particle, snippet, fragment, excerpt, pericope,

        /*Rocket Hover Path*/milli,centi,deci,hecto, kilo,

        /*Xeal Naval Path*/rivulet,bourn,tributary,loch,atlantic,

        /*Assault Spooder Path*/chime,carillon,knell,peal,reverberate,

        /*yggdrasil path*/root,stick,branch,tree,cambrium,yggdrasil,

        /*Unix Spooder path*/circuit,

        /*Unix Worm path*/arrow,

        /*Unix Air path*/celestial,

        /*Nullis*/
            /*Storage Con Path*/pocket,container,capsule,vault,chamber, /* I u I */ symphony,

        /*Extra Paths : */
        routerTank, routerBastion, box,blockHost, coin,

                /*BOSSES*/
                    charlie,
                    vdoble,
                    ethanol,
                    siede,
                    frire, /*swarm mini extra*/
                        frire_swarm,frire_pop,frire_wyrm,
                    zepz, baron, //kapzduke's twin bosses
                    omamori,
                    plays; //a guy's boss cuz why not.
    public static void load()
    {
        //Base UnitLoads
        loadFlyingIonPath();
        loadRocketHoverPath();
        loadAssaultSpooderPath();
        //Faction Based UnitLoads
        loadXeonNavalPath();
        loadStoragePath();

        loadYggdrasilPath();
        loadUnixPath();
        //Extra UnitLoads
        loadCorePath();
        loadExtra();

        //For Every UnitType loaded in loadBosses it shall do that.
        Seq<UnitType> l = Vars.content.units().copy();
        loadBosses(); // code around it is like a wrapper. it does things to units loaded in this function.
        Vars.content.units().copy().removeAll(u->l.contains(u)).each(u->
                u.immunities.addAll( // These are boss status effects that can be very powerfull if applied to a boss.
                        PvStatusEffects.memoryExchange,
                        PvStatusEffects.prevention,
                        PvStatusEffects.lastStand
                )
        );
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
                    new Weapon()
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
                            bullet = new LaserBoltBulletType(16, 14) {{
                                homingPower = 0.01f;
                                width = 0;
                                height = 0;
                                homingDelay = 1;
                                buildingDamageMultiplier = 0.01f;
                                trailLength = 2;
                                trailWidth = 0.5f;
                                pierce = true;
                                pierceCap = 3;
                                homingRange = 8 * 28f;
                                lifetime = PvUtil.GetRange(this.speed, 28);
                                trailColor = backColor = Pal.engine;
                                lightColor = Color.black;
                                this.recoil = 0f;
                                despawnShake = hitShake = 0.1f;
                            }};
                        }},
                    new Weapon("spectrum-weapon2")
                    {
                        {
                            reload = 60f / 2f;
                            x = 6;
                            y = 2;
                            mirror = true;
                            rotationLimit = 30;
                            shootStatus = PvStatusEffects.crescendo;
                            shootStatusDuration = 30;
                            bullet = new LaserBoltBulletType(8, 18) {{
                                homingPower = 0.01f;
                                homingDelay = 1;
                                buildingDamageMultiplier = 0.01f;
                                trailLength = 10;
                                trailWidth = 1;
                                homingRange = 8 * 28f;
                                lifetime = PvUtil.GetRange(this.speed, 28);
                                trailColor = lightColor = backColor = Pal.engine;
                                this.recoil = 0f;
                                despawnShake = hitShake = 0.5f;
                                fragBullets = 3;
                                hitEffect = despawnEffect = Fx.hitBulletSmall;
                                fragBullet = new LaserBoltBulletType(6, 6) {{
                                    homingPower = 0.01f;
                                    homingDelay = 1;
                                    buildingDamageMultiplier = 0.01f;
                                    trailLength = 10;
                                    trailWidth = 1;
                                    drag = 0.01f;
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
        wood = new PvUnitType("wood")
        {{
            localizedName = "[#766e4d]Wood";
            constructor = EntityMapping.map("alpha");
            health = 650;
            armor = 20;
            flying = true;
            buildSpeed = 3.15f;
            mineTier = 3;
            mineSpeed = 7.25f;
            itemCapacity = 300;
            speed = 20.7f / 7.5f;
            drag = 0.15f;
            range = 17*8;
        }};


        //Nullis


        shadow = new NullisUnitType("shadow")
        {{
            health = 1f;
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
            buildSpeed = 0.4f;
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
                        mirror = false;
                        reload = 60f;
                        shoot.shots = 3;
                        shoot.shotDelay = 10;
                        inaccuracy = 4;
                        rotate = true;
                        rotationLimit = 30;
                        bullet = new VoidBulletType(6,30)
                        {{
                            recoil = 0.5f;
                            trailLength = 20;
                            trailWidth = 2;
                            lifetime = 2;
                            homingDelay = 0;
                            homingRange = 8*17f;
                            homingPower = 0.001f;
                        }};
                    }}
            );
        }};
        shell = new NullisUnitType("shell") {{
            localizedName = "Shell";
            constructor = EntityMapping.map("beta");
            health = 540;
            armor = 0;
            flying = true;
            hitSize = 8*1f;
            buildSpeed = 0.6f;
            mineTier = 2;
            mineSpeed = 10f;
            itemCapacity = 120;
            speed = 21.2f / 7.5f;
            trailLength = 60;
            trailScl = 1;
            trailColor = Color.black;
            engineColor = Color.black;
            healColor = Color.black;
            drag = 0.1f;
            range = 22*8;
            weapons.add(
                    new Weapon()
                    {{
                        reload = 35f;
                        x = 0f;
                        y = 6.5f;
                        shootY = 5f;
                        recoil = 1f;
                        rotate = false;
                        shootCone = 30;
                        mirror = false;
                        shoot = new CyclicShootPattern(2,2,10);
                        shoot.shotDelay = 0;
                        bullet = new VoidBulletType(5f, 34){{
                            recoil = 0.2f;
                            width = 7f;
                            height = 12f;
                            lifetime = 2f;
                            frontColor = Color.white;
                            trailWidth = 1.5f;
                            trailLength = 5;
                            homingDelay = 0;
                            homingRange = 8*2;
                            homingPower = 0.01f;
                            hitEffect = despawnEffect = Fx.hitBulletColor;
                        }};
                    }}
            );
        }};
        puppet = new NullisUnitType("puppet") {{
            localizedName = "Puppet";
            constructor = EntityMapping.map("beta");
            health = 800;
            armor = 0;
            flying = true;
            hitSize = 8*1.5f;
            buildSpeed = 1f;
            mineTier = 2;
            mineSpeed = 14f;
            itemCapacity = 200;
            speed = 18.2f / 7.5f;
            trailLength = 90;
            engineOffset = 8;
            trailScl = 1;
            trailColor = Color.black;
            engineColor = Color.black;
            healColor = Color.black;
            drag = 0.1f;
            range = 22*8;
            weapons.add(
                    new Weapon()
                    {{
                        reload = 35f;
                        x = 0f;
                        y = 6.5f;
                        shootY = 5f;
                        recoil = 1f;
                        rotate = false;
                        shootCone = 30;
                        mirror = false;
                        shoot = new CyclicShootPattern(4,3,0);
                        bullet = new VoidBulletType(5f, 34){{
                            recoil = 0.2f;
                            width = 7f;
                            height = 12f;
                            lifetime = 2f;
                            frontColor = Color.white;
                            trailWidth = 1.5f;
                            trailLength = 5;
                            homingDelay = 0;
                            homingRange = 8*2;
                            homingPower = 0.01f;
                            hitEffect = despawnEffect = Fx.hitBulletColor;
                        }};
                    }}
            );
            parts.addAll(
                new RegionPart("-arm") {{
                    mirror = true;
                    moveRot = 30;
                    progress = PartProgress.smoothReload;
                }},
                new ChangeRegionPart("-strings") {{
                    mirror = false;
                    parts = 4;
                    layer = Layer.effect;
                    progress = PartProgress.life;
                    lifeEnabled = true;
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
                            knockback = 4f;
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
            range = 25*8;
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
                            knockback = 5f;
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
                            knockback = 8f;
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
        substance = new PvUnitType("substance") {{
            localizedName = "Substance";
            constructor = EntityMapping.map("flare");
            health = 300;
            armor = 3;
            flying = false;
            strafePenalty = 0.5f;
            engineOffset = 8;
            buildSpeed = 0.8f;
            mineSpeed = 4.75f;
            itemCapacity = 30;
            speed = 30f / 18.5f;
            drag = 0.1f;
            range = 28*8;
            weapons.add(
                    new Weapon()
                    {{
                        mirror = false;
                        top = false;
                        x = 0;
                        y = 0;
                        reload = 60f*2f;
                        alternate = false;
                        shoot.firstShotDelay = Fx.lancerLaserCharge.lifetime;
                        bullet = new LaserBoltBulletType(24,125)
                        {{
                            chargeEffect = Fx.lancerLaserCharge;
                            lifetime = PvUtil.GetRange(24,28);
                            buildingDamageMultiplier = 0.01f;
                        }};
                    }}
            );
        }};
        warden = new PvUnitType("warden") {{
            localizedName = "Warden";
            constructor = EntityMapping.map("CrawlUnit");
            health = 160;
            armor = 0;
            drawCell = drawBody = false;
            flying = false;
            segments = 3;

            strafePenalty = 0.5f;
            engineOffset = 8;
            itemCapacity = 30;
            buildSpeed = 4f;
            mineSpeed = 3f;
            speed = 30f / 7.5f;
            drag = 0.1f;
            range = 17*8;
            weapons.add(
                    new Weapon()
                    {{
                        mirror = false;
                        top = false;
                        x = 0;
                        y = 0;
                        reload = 60f/3f;
                        alternate = false;
                        bullet = new LaserBoltBulletType(14,22)
                        {{
                        }};
                    }}
            );
        }};
        amcro = new PvUnitType("amcro") {{
            localizedName = "Amcro";
            constructor = EntityMapping.map("alpha");
            health = 200;
            armor = 0;
            flying = true;
            buildSpeed = 0.6f;
            mineSpeed = 4f;
            itemCapacity = 30;
            speed = 24f/7.5f;
            drag = 0.2f;
            range = 28*8;
            hitSize = 8*1.5f;
            weapons.add(
                    new Weapon()
                    {{
                        mirror = false;
                        top = false;
                        x = 0;
                        y = 0;
                        reload = 60f*8f;
                        alternate = false;
                        bullet = new ArtilleryBulletType(4,75)
                        {{
                            buildingDamageMultiplier = 0.01f;
                            trailWidth = 2;
                            trailLength = 25;
                            lifetime = PvUtil.GetRange(4,10);

                            fragBullets = 1;
                            fragSpread = 0;
                            fragVelocityMin = 1;
                            fragVelocityMax = 1;
                            fragOnHit = false;
                            fragBullet = new BasicBulletType(0, 75, "large-bomb")
                            {{
                               buildingDamageMultiplier = 0.01f;
                               lifetime = 60f * 5f;
                               spin = 2;
                               pierce = true;
                               pierceCap = 3;
                               shrinkX = 0.3f;
                               shrinkY = 0.3f;
                               width = 24f;
                               height = 24f;

                               intervalBullets = 1;
                               bulletInterval = 0.5f * 60f;
                               intervalRandomSpread = 0;
                               intervalSpread = 0;
                               intervalAngle = 0;
                               intervalBullet = new BulletType(0f,0)
                               {{
                                       buildingDamageMultiplier = 0.00001f;
                                       lifetime = 30f;

                                       homingRange = 28 * 8;
                                       homingPower = 1;

                                       fragBullets = 1;
                                       fragVelocityMin = 1;
                                       fragVelocityMax = 1;
                                       fragRandomSpread = 0;
                                       fragSpread = 0;
                                       fragBullet = new BasicBulletType(6,25)
                                       {{
                                           buildingDamageMultiplier = 0.001f;
                                           trailWidth = 2;
                                           trailLength = 8;
                                           lifetime = PvUtil.GetRange(6, 28);
                                       }};
                               }};
                            }};
                        }};

                    }}
            );

            parts.addAll(
                    new RegionPart("-plate")
                    {{
                        progress = PartProgress.warmup;
                        mirror = true;

                        x = 0;
                        y = 0;
                        moveX = -0.5f;
                        moveY = 0.5f;

                        moves.add(new PartMove(PartProgress.smoothReload, 1f, -1f, 0f));
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
            engineOffset = 8;
            engineSize = 6;
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
                        rotate = true;
                        shootCone = 10;
                        rotateSpeed = 6;
                        mirror = false;
                        recoil = 3;
                        inaccuracy = 5;
                        bullet = new SapBulletType()
                        {{
                            incendChance = 0;
                            shake = 0;
                            status = StatusEffects.sapped;
                            statusDuration = 120f;
                            sapStrength = 0.85f;
                            healPercent = 0.02f;
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
                        y = 30;
                        reload = 60/0.5f;
                        mirror = false;
                        bullet = new RailBulletType(){{
                            shootEffect = Fx.lancerLaserShoot;
                            length = 32*8;
                            pointEffectSpace = 60f;
                            buildingDamageMultiplier = 0.2f;
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
            hitSize = 8*4;
            drag = 0.05f;
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
                        bullet = new LaserBoltBulletType(5.2f, 24){{
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
                y = 15;
                range = 44*8;
                reload = 120;
                mirror = false;
                recoil = 3;
                top = true;
                inaccuracy = 0;
                bullet = new RailBulletType(){{
                    damage = 240;
                    buildingDamageMultiplier = 0.2f;
                    hitShake = 6f;
                    shootEffect = Fx.lancerLaserShoot;
                    length = 44*8;
                    pointEffectSpace = 30f;
                    pierceEffect = Fx.railHit;
                    pointEffect = PvEffects.railFrag;
                    hitEffect = Fx.massiveExplosion;
                    smokeEffect = Fx.shootBig2;
                    lightningColor = trailColor = lightColor = Pal.sap;
                    pierceCap = 2;
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
        coin = new UnitType("coin"){{
            localizedName = "Coin";
            constructor = CoinUnit::new;
            speed = 0.00001f;
            //TODO allowed to fly for testing purposes
            canBoost = true;
            boostMultiplier = 10000;
            health = 1;
            description = "A coin. With trickshot capabilities.";
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
            payloadCapacity = tilePayload * 8;
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
            health = 54200;
            deathExplosionEffect = PvEffects.nullisDeath.get(7);
            factions.add(PvFactions.Nullis);
            armor = 22;
            drownTimeMultiplier = 30;
            rotateSpeed = 90/60;
            canBoost = true;
            hitSize = 16*8;
            buildSize = 10;
            setGridLayout(new byte[][]{
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
            });
            healColor = Color.black;
            engineColor = Color.black;
            itemCapacity = 10000;
            speed = 0.004f / 7.5f;
            boostMultiplier = 400;
            drag = 0.03f;
            lowAltitude = true;
            range = 12*8;
            engineSize = 0;
            abilities.add(new EffectAbilityC(18*8));
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
        symphony = new GridUnitType("symphony") {{
            localizedName = "Symphony";
            description = "[#b]The Giant of Giants. Its size is perfect for your entire base.";
            health = 428500;
            deathExplosionEffect = PvEffects.nullisDeath.get(7);
            factions.add(PvFactions.Nullis);
            armor = 30;
            drownTimeMultiplier = 30;
            rotateSpeed = 60f/60f;
            canBoost = true;
            hitSize = 40*8;
            buildSize = 34;
            setGridLayout(new byte[][]{
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0},
                    {0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                    {0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                    {0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
                    {0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
                    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                    {0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
                    {0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
                    {0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                    {0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                    {0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                    {0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                    {0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                    {0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                    {0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
                    {0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
                    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                    {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
                    {0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
                    {0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0},
                    {0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                    {0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                    {0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0},
                    {0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0},
                    {0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                    {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
            });
            healColor = Color.black;
            engineColor = Color.black;
            itemCapacity = 200000;
            speed = 0.004f / 7.5f;
            boostMultiplier = 450;
            drag = 0.03f;
            lowAltitude = true;
            range = 12*8;
            engineSize = 0;
            abilities.add(new EffectAbilityC(70*8));
        }};
    }
    public static void loadBosses() {
        vdoble = new PvUnitType("vdoble") {{
            localizedName = "Vdoble";
            constructor = EntityMapping.map("toxopid");
            drag = 0.1f;
            speed = 3.2f / 7.5f;
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


            abilities.add(new ShieldArcAbility() {{
                region = "tecta-shield";
                radius = 40f;
                angle = 120f;
                regen = 30f / 60f;
                cooldown = 60f * 6f;
                max = 3000f;
                y = -20f;
                width = 6f;
            }});
            hovering = true;
            shadowElevation = 0.95f;
            groundLayer = Layer.legUnit;

            weapons.add(
                    new Weapon(PvUtil.GetName("large-purple-mount-vdoble")) {{
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
                        immunities.add(PvStatusEffects.prevention);
                        immunities.add(PvStatusEffects.lastStand);

                        shoot = new ShootSpread(5, 11f);

                        bullet = new ShrapnelBulletType() {{
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

            weapons.add(new Weapon(PvUtil.GetName("vdoble-cannon")) {{
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

                bullet = new ArtilleryBulletType(5f, 80) {{
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

                    fragBullet = new ArtilleryBulletType(4.3f, 50) {{
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
                        fragBullet = new ArtilleryBulletType(3.3f, 30) {{
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
            weapons.add(new Weapon(PvUtil.GetName("vdoble-cannon")) {{
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

                bullet = new ArtilleryBulletType(3f, 80) {{
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

                    fragBullet = new ArtilleryBulletType(3.3f, 50) {{
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
        charlie = new PvUnitType("charlie") {
            {
                localizedName = "Choo Choo Charlie";
                constructor = EntityMapping.map("toxopid");
                drag = 0.1f;
                speed = 35f / 7.5f;
                hitSize = 8 * 8f;
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
                            immunities.add(PvStatusEffects.prevention);
                            immunities.add(PvStatusEffects.lastStand);

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
                                fragBullet = new BulletType(8, 60) {{
                                    lifetime = 60;
                                    fromColor = Pal.redLight;
                                    toColor = Pal.redderDust;
                                    hitShake = despawnShake = 0.5f;
                                    fragBullets = 8;
                                    fragLifeMax = 1;
                                    fragLifeMin = 0.8f;
                                    fragBullet = new BulletType(8, 60) {{
                                        lifetime = 60;
                                        fromColor = Pal.redLight;
                                        toColor = Pal.redderDust;
                                        hitShake = despawnShake = 0.5f;
                                    }};
                                }};
                            }};
                        }});
            }
        };
        ethanol = new PvUnitType("ethanol") {{
                localizedName = "[lime]Ethanol[]";
                description = "N/A";
                details = """
                    Hundreds of generations after the Corvus was originally built and countless modifications had led to the creation of the [lime]Ethanol[]
                    While this unit resembles nothing of its predecessor's spider like appearance. it still retains that long range and [green]support capabilities[].
                    The designer of this [gold]behemoth[] is the infamous [teal]Dr Lupellia![] However she had recently [crimson]gone missing[] for [purple]unknown reasons.[] 
                    [crimson]Rumors[] have spread that she was [purple]kidnapped[] by the [crimson]Mortikai Stealth team[] to force intel and blueprints from her.
                
                    [orange]Usual Behaviour Between Factions : 
                    [green]Ally Factions : []None
                    [red]Enemy Factions : []Mortikai
                    """;
                constructor = EntityMapping.map("eclipse");
                health = 100000;
                armor = 15;
                flying = true;
                speed = 5.1f/7.5f;
                engineOffset = 40;
                engineSize = 8*5;
                lowAltitude = true;
                fogRadius = 70;
                hitSize = 8*14;
                range = 60*8;

                abilities.add(
                        new StatusFieldAbility(StatusEffects.overdrive,360,300,8*14)
                );

                var cannonCharge = Fx.greenLaserCharge;
                var armWeapon = new LinkedWeapon() {{
                    reload = 60f;
                    shoot.shots = 5;
                    shoot.shotDelay = 5f;
                    mirror = true;
                    x = 4*11;
                    y = 4*16;
                    inaccuracy = 5f;
                    bullet = new BasicBulletType(24,360) {{
                        trailLength = 30;
                        trailWidth = 1;
                        drag = -0.01f;
                        trailColor = backColor = lightColor = Pal.heal;
                        frontColor = Color.white;
                        homingDelay = 0;
                        homingPower =  0.08f;
                        homingRange = 8*30;
                        lifetime = PvUtil.GetRange(32,60);
                        pierce = true;
                        pierceCap = 3;
                        status = StatusEffects.shocked;
                        statusDuration = 180;
                        splashDamage = 100;
                        splashDamageRadius = 7.5f * 4;
                        trailEffect = Fx.vaporSmall;
                        trailInterval = 1;
                    }};
                }};
                var cannonWeapon = new LinkedWeapon() {{
                    reload = 60f/0.05f;
                    x = 0;
                    y = 3.5f*8;
                    mirror = false;
                    timeout = 60f*4;

                    shoot.firstShotDelay = cannonCharge.lifetime;
                    shootStatus = StatusEffects.slow;
                    shootStatusDuration = cannonCharge.lifetime;
                    bullet = new BasicBulletType(8,2300) {{
                        chargeEffect = cannonCharge;
                        trailLength = 30;
                        trailWidth = 1;
                        trailColor = backColor = lightColor = Pal.lancerLaser;
                        frontColor = Color.white;
                        lifetime = PvUtil.GetRange(8,44);
                        splashDamage = 2300;
                        splashDamageRadius = 18*8;
                        splashDamagePierce = true;
                        trailEffect = Fx.smokeCloud;
                        trailInterval = 1;
                        despawnEffect = hitEffect = Fx.impactReactorExplosion;
                    }};
                }};
                armWeapon.setLink(cannonWeapon);
                weapons.add(armWeapon,cannonWeapon,
                        new Weapon(PvUtil.GetName( "ethanol-weapon")) {{
                            reload = 40f;
                            shoot.shots = 3;
                            shoot.shotDelay = 5f;
                            mirror = true;
                            x = -4*12;
                            y = 4*-4;
                            recoil = 8;
                            recoilPow = 2;
                            shootY = 8;
                            rotate = true;
                            rotationLimit = 90;
                            inaccuracy = 5f;
                            bullet = new BasicBulletType(32,120) {{
                                trailLength = 30;
                                trailWidth = 0.5f;
                                trailColor = backColor = lightColor = Pal.heal;
                                frontColor = Color.white;
                                homingDelay = 0;
                                homingPower =  0.08f;
                                homingRange = 8*30;
                                lifetime = PvUtil.GetRange(32,60);
                                pierce = true;
                                pierceCap = 3;
                                status = StatusEffects.shocked;
                                statusDuration = 180;
                                splashDamage = 100;
                                splashDamageRadius = 7.5f * 4;
                                trailEffect = Fx.vaporSmall;
                                trailInterval = 1;
                            }};
                        }}
                );
                var swing = new Interp.Swing(2f);
                parts.addAll(
                        new FreeRegionPart("-arm-l") {{
                            progress = (r) -> swing.apply(r.smoothReload);
                            mirror = false;
                            x = -10;
                            y = 0;
                            moveRot = 15;
                            weaponIndex = 1;

                            moves.add(
                                    new PartMove() {{
                                        rot = -10;
                                        this.progress = (r) -> {
                                            var point = (cannonCharge.lifetime / cannonWeapon.timeout);
                                            if (r.charge < point)
                                                return Interp.circleIn.apply(r.charge * (1/point));
                                            var newCharge = (r.charge - point) * (1/(1-point)) * 2;
                                            if (newCharge < 1)
                                                return -Interp.swingOut.apply(newCharge);
                                            return Interp.circleOut.apply(newCharge-1)-1;
                                        };
                                    }}
                            );

                        }},
                        new FreeRegionPart("-arm-r") {{
                            progress = (r) -> Interp.swing.apply(r.smoothReload);
                            mirror = false;
                            x = 10;
                            y = 0;
                            moveRot = -15;
                            weaponIndex = 0;
                            moves.add(
                                    new PartMove() {{
                                        rot = 10;
                                        this.progress = (r) -> {
                                            var point = (cannonCharge.lifetime / cannonWeapon.timeout);
                                            if (r.charge < point)
                                                return Interp.circleIn.apply(r.charge * (1/point));
                                            var newCharge = (r.charge - point) * (1/(1-point)) * 2;
                                            if (newCharge < 1)
                                                return -Interp.swingOut.apply(newCharge);
                                            return Interp.circleOut.apply(newCharge-1)-1;
                                        };
                                    }}
                            );
                        }}
                );
        }};
        siede = new PvUnitType("siede") {
            {
                localizedName = "[orange]Sie[red]de[][]";
                description = "A Powerful Programmer Capable of doing a lot of things. His many Attributes are [orange]\n1. Control Units\n2. Summon Units\n3. Transform into a Tier 5 unit\n4. Use a Shield";
                details = """
                    [gold]Siede[]. the so called [#b]\"Necromaniac\"[] is widely known for his strategic Power.
                    No matter how or when, even a million Units would falter to this menace of a unit.
                    [orange]The Fallen[] [crimson]close[] to him would just get sucked up and get [purple]Reused[] to help him Fight.
                    Mindless Spamming will not get you anywhere with this Foe. try to have a mix of Weak and Strong units.
                    His [purple]Corruption[] [blue]slowly creeps[] into any [#b]Unit's software[], so be sure that they [orange]Target the Weak[] as to not risk losing the Strong units.
                    
                    [orange]Usual Behaviour Between Factions : 
                    [green]Ally Factions : []Nullis , Psy
                    [red]Enemy Factions : []Xeal, Mortikai, Azulex
                    """;
                constructor = FrogUnit::new;
                flying = true;
                engineColor = Color.black;
                health = 50000;
                armor = 10;
                hitSize = 8 * 2;
                buildSpeed = 1;
                buildBeamOffset = 12;
                abilities.add(
                        new EnemyStatusFieldAbility(PvStatusEffects.memoryExchange, 180, 120, 20 * 8),
                        new EffectAbilityC(8 * 4)
                );
                weapons.add(
                        new Weapon() {{
                            reload = 60 / 0.8f;
                            mirror = false;
                            shoot = new ShootSpreadBounce(10, 2, 2);
                            shoot.shotDelay = 1;
                            x = 0;
                            y = 0;
                            immunities.add(PvStatusEffects.prevention);
                            immunities.add(PvStatusEffects.lastStand);
                            bullet = new MissileBulletType(12, 48) {{
                                backColor = lightColor = trailColor = Pal.reactorPurple;
                                frontColor = Pal.reactorPurple2;
                                lifetime = PvUtil.GetRange(this.speed, 38);
                                trailLength = 60;
                                trailWidth = 2;
                                pierceCap = 3;
                                weaveMag = 1;
                                weaveScale = 1;
                                weaveRandom = true;
                                homingPower = 0;
                                despawnEffect = hitEffect = Fx.absorb;
                                status = PvStatusEffects.doused;
                                statusDuration = 180;

                            }};
                        }}
                );
                float swingTime = 60;
                float swingTime2 = 50;
                parts.addAll(
                        new RegionPart("-l1") {{
                            progress = p -> Mathf.cos(Time.time / swingTime) / 2 + 0.5f;
                            mirror = true;
                            moveY = -4;
                        }},
                        new RegionPart("-l2") {{
                            progress = p -> Mathf.sin(Time.time / swingTime2) / 2 + 0.5f;
                            mirror = true;
                            moveY = -4;
                        }},
                        new RegionPart("-arm") {{
                            progress = PartProgress.smoothReload;
                            moveY = -2;
                            moveX = -6;
                            moveRot = 8;
                            mirror = true;
                        }}
                );
            }

            @Override
            public void init() {
                super.init();
                range = 8 * 14;
            }
        };
        plays = new PvUnitType("plays") {{
            health = 500000;
            armor = 20;
            localizedName = "Plays";
            constructor = EntityMapping.map("eclipse");
            speed = 6f / 7.5f;
            rotateSpeed = 2f;
            drag = 0.02f;
            accel = 0.03f;
            hitSize = 8*6;
            flying = true;
            drawCell = false;
            engineOffset = 26;
            engineSize = 6;
            weapons.add(
                new Weapon() {{ //Laser Side Cannons
                    x = 20;
                    y = 8;
                    reload = 30f/0.6f;
                    mirror = true;
                    shootCone = 360;
                    rotationLimit = 45;
                    bullet = new LaserBulletType() {{
                        colors = new Color[]{Pal.heal, Pal.heal.cpy().add(50,50,50), Color.white};
                        damage = 600;
                        length = 8*33;
                        lifetime = 30;
                    }};
                }},
                new RandWeapon() {{ //Bullet Barrage.
                    bulletSpeedMin = 2;
                    bulletSpeedMax = 6;
                    shootCone = 90;
                    inaccuracy = 10;
                    x = 0;
                    y = 0;
                    shootY = 16;
                    mirror = false;
                    reload = 60*8f;
                    rotationLimit = 10;
                    shoot.shots = 120;
                    shoot.shotDelay = 0.4f;
                    shoot.firstShotDelay = Fx.greenLaserCharge.lifetime;
                    bullet = new BasicBulletType(6,60) {{
                        chargeEffect = Fx.greenLaserCharge;
                        frontColor = Color.white;
                        trailColor = backColor = Pal.gray;
                        trailWidth = 1;
                        trailLength = 20;
                        homingPower = 0.08f;
                        homingDelay = 20;
                    }};
                }}
            );
        }};
        frire_wyrm = new PvUnitType("wyrm") {{
            health = Integer.MAX_VALUE;
            armor = Integer.MAX_VALUE;
            speed = 2f/7.5f;
            localizedName = "Wyrm";
            constructor = EntityMapping.map("CrawlUnit");
            aiController = HugAI::new;
            segments = 3;
            segmentMag = 0.5f;
            segmentScl = 2f;
            segmentPhase = 1f;
            omniMovement = false;
            drawCell = false;
            drawBody = false;
            crushDamage = Integer.MAX_VALUE;
        }};
        frire_swarm = new PvUnitType("frire-mite-swarm") {{
            health = 650;
            armor = 20;
            localizedName = "Swarming Frire Mite";
            constructor = EntityMapping.map("CrawlUnit");
            aiController = HugAI::new;
            segments = 3;
            segmentMag = 1f;
            omniMovement = false;
            drawCell = false;
            drawBody = false;
            crushDamage = 0.5f;
        }};
        frire_pop = new PvUnitType("frire-mite-pop") {{
            health = 650;
            armor = 20;
            localizedName = "Popping Frire Mite";
            constructor = EntityMapping.map("CrawlUnit");
            aiController = HugAI::new;
            segments = 3;
            segmentMag = 1f;
            omniMovement = false;
            drawCell = false;
            drawBody = false;
            crushDamage = 0f;

            weapons.add(new Weapon(){{
                shootOnDeath = true;
                reload = 24f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                x = shootY = 0f;
                mirror = false;
                bullet = new BulletType(){{
                    collidesTiles = false;
                    collides = false;
                    hitSound = Sounds.explosion;

                    rangeOverride = 30f;
                    hitEffect = Fx.pulverize;
                    speed = 0f;
                    splashDamageRadius = 7*8f;
                    instantDisappear = true;

                    splashDamage = 180f;

                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};
        frire = new PvUnitType("frire") {{
            localizedName = "FrireDragon";
            description = "The [red]Frire[orange]Dragon [][]is a large worm like machine, with a primal hunger for metal and flesh.";
            details = """
                    The [red]Frire[orange]Dragon [][]is a mystical machine. It was made at some point, by some unknown person, but one thing is for sure. 
                    That person held this machine as if it was a beast... which it was. But one thing led to another, and this machine hated its creator, so much it grew a hunger for their flesh.
                    It broke free, consuming the very person who made it, before traveling the land.
                    The [red]Frire[orange]Dragon [][]was docile to most other machines, even befriending select few factions. A magical faction called [purple]Unix []changed its programming to allow it to make its own drones to help attack.
                    The [red]Frire[orange]Dragon [][]even helped factions it had befriended attack other factions, unless it was attacked first. In which case it attempted to do the very same thing it did to its owner, consume.
                    The [red]Frire[orange]Dragon [][]now lays dormant after its most recent escapade where it worked alongside other machines like it, waiting for the one day it may return to smite down its enemies again...                    
                    \n[orange]Usual Behaviour Between Factions : 
                    [green]Ally Factions : []Unix, Xeal, Psy, Nullis
                    [red]Enemy Factions : []Mortikai, Azulex,
            """;
            health = 230000;
            armor = 35;
            constructor = EntityMapping.map("CrawlUnit");

            controller = u -> new FrireAI();

            aiController = FrireAI::new;

            segments = 5;
            segmentMag = 1.5f;
            segmentScl = 3f;
            hitSize = 8 * 4;
            speed = 5f / 7.5f;
            omniMovement = false;
            drawCell = false;
            rotateSpeed = 90/60f;
            drawBody = false;
            drownTimeMultiplier = 5;
            crushDamage = 2;
            weapons.add(new Weapon("rush") {{
                reload = 60 * 6;
                shootCone = 360;
                x = y = 0;
                shoot.firstShotDelay = 20;
                bullet = new BulletType() {{
                    this.range = 8*6;
                    shootStatus = PvStatusEffects.consume;
                    shootStatusDuration = 240;
                    recoil = -8 * 3;
                    lifetime = 0;
                    shootSound = hitSound = despawnSound = Sounds.none;
                    fallEffect = smokeEffect = shootEffect = despawnEffect = hitEffect = Fx.none;
                    ejectEffect = Fx.smokeCloud;
                }};
            }},
                    new Weapon("frireSpawn") {{
                        mirror = true;
                        x = 8*1.4f;
                        y = 0;
                        shootCone = 360;
                        alternate = true;
                        reload = 60*2;
                        rotate = false;
                        controllable = false;
                        autoTarget = true;
                        bullet = new BulletType() {{
                            this.range = 8*16;
                            lifetime = 0;
                            spawnUnit = PvUnits.frire_swarm;
                            shootSound = hitSound = despawnSound = Sounds.none;
                            ejectEffect = fallEffect = smokeEffect = shootEffect = despawnEffect = hitEffect = Fx.none;
                        }};
                    }},
                    new Weapon("frireSpawn2") {{
                        mirror = true;
                        x = -8*1.4f;
                        y = 0;
                        shootCone = 360;
                        alternate = true;
                        reload = 60*2;
                        rotate = false;
                        controllable = false;
                        autoTarget = true;
                        bullet = new BulletType() {{
                            this.range = 8*16;
                            lifetime = 0;
                            spawnUnit = PvUnits.frire_pop;
                            shootSound = hitSound = despawnSound = Sounds.none;
                            ejectEffect = fallEffect = smokeEffect = shootEffect = despawnEffect = hitEffect = Fx.none;
                        }};
                    }},
                    new Weapon("bite") {{
                        mirror = false;
                        x = y = 0;
                        reload = 60;
                        rotate = false;
                        shootCone = 45;
                        shoot = new ShootSpread(10,4);
                        bullet = new BasicBulletType(4,82) {{
                            trailLength = 20;
                            trailWidth = 2;
                            drag = 0.06f;
                            lifetime = 100;
                            trailColor = backColor = lightColor = Pal.redderDust;
                            frontColor = Pal.redLight;
                        }};
                    }}
            );
        }};
        zepz = new UnitType("zepz") {{
            localizedName = "[white]Zepz[]";
            constructor = EntityMapping.map("mega");
            flying = true;
            drag = 0.3f;
            speed = 2f;
            hitSize = 10f;
            health = 6000;
            armor = 10f;
            immunities.add(PvStatusEffects.endlessAmp);
            abilities.add(new StatusFieldAbility(PvStatusEffects.endlessAmp, 60f, 10f, 400f));
            abilities.add(new SourceAbility(24 * 4));
        }};
        baron = new UnitType("baron") {{
            immunities.add(PvStatusEffects.endlessAmp);
            localizedName = "[#202020]Baron[]";
            constructor = EntityMapping.map("eclipse");
            flying = true;
            drag = 0.3f;
            speed = 5f;
            hitSize = 10;
            health = 6000;
            armor = 14f;
            zepz.abilities.add(new SpawnDeathAbility(this,1,0));
            abilities.add(new EnemyStatusFieldAbility(PvStatusEffects.endlessDot, 60, 10, 400f));
            abilities.add(new EffectAbilityC(24 * 4));
            abilities.add(new SpawnDeathAbility(zepz,1,0));
            weapons.add(
                    new Weapon(name + "shotty"){{
                        y = 0f;
                        x = 0f;
                        mirror = false;
                        reload = 300;
                        shootSound = Sounds.shootAltLong;

                        shoot = new ShootSpread(10, 1f);

                        bullet = new BulletType()
                        {{

                                despawnSound = Sounds.shootAltLong;
                                hittable = false;
                                reflectable = false;
                                absorbable = false;
                                collides = false;
                                collideTerrain = true;
                                hitEffect = Fx.none;
                                despawnEffect = Fx.none;
                                damage = 0;
                                speed = 14;
                                lifetime = 15;
                                fragRandomSpread = 0;
                                fragBullets = 1;
                                fragBullet = new ShrapnelBulletType()
                                {{
                                    fromColor = Color.valueOf("000000");
                                    toColor = Color.valueOf("202020");
                                    width = 30;
                                    length = 60;
                                    damage = 60;
                                    serrationLenScl = 7f;
                                    serrationSpaceOffset = 60f;
                                    serrationFadeOffset = 0f;
                                    serrations = 10;
                                    serrationWidth = 6f;
                                    speed = 0;
                                    lifetime = 5;
                                    fragRandomSpread = 0;
                                    fragBullets = 1;
                                    fragBullet = new BulletType()
                                    {{
                                        despawnSound = Sounds.shootAltLong;
                                        hittable = false;
                                        reflectable = false;
                                        absorbable = false;
                                        collides = false;
                                        collideTerrain = true;
                                        hitEffect = Fx.none;
                                        despawnEffect = Fx.none;
                                        damage = 0;
                                        speed = 14;
                                        lifetime = 2;
                                        fragRandomSpread = 0;
                                        fragBullets = 2;
                                        fragBullet = new ShrapnelBulletType()
                                        {{
                                            fromColor = Color.valueOf("000000");
                                            toColor = Color.valueOf("202020");
                                            width = 30;
                                            length = 60;
                                            damage = 60;
                                            serrationLenScl = 7f;
                                            serrationSpaceOffset = 60f;
                                            serrationFadeOffset = 0f;
                                            serrations = 10;
                                            serrationWidth = 6f;
                                            speed = 0;
                                            lifetime = 5;
                                            fragRandomSpread = 0;
                                            fragBullets = 1;
                                            fragBullet = new BulletType()
                                            {{
                                                despawnSound = Sounds.shootAltLong;
                                                hittable = false;
                                                reflectable = false;
                                                absorbable = false;
                                                collides = false;
                                                collideTerrain = true;
                                                hitEffect = Fx.none;
                                                despawnEffect = Fx.none;
                                                damage = 0;
                                                speed = 14;
                                                lifetime = 2;
                                                fragRandomSpread = 0;
                                                fragBullets = 4;
                                                fragBullet = new ShrapnelBulletType(){{
                                                    fromColor = Color.valueOf("000000");
                                                    toColor = Color.valueOf("202020");
                                                    width = 30;
                                                    length = 60;
                                                    damage = 60;
                                                    serrationLenScl = 7f;
                                                    serrationSpaceOffset = 60f;
                                                    serrationFadeOffset = 0f;
                                                    serrations = 10;
                                                    serrationWidth = 6f;
                                                    speed = 0;
                                                    lifetime = 5;
                                                    fragRandomSpread = 0;
                                                    fragBullets = 1;
                                                    fragBullet = new BulletType()
                                                    {{
                                                            despawnSound = Sounds.shootAltLong;
                                                            hittable = false;
                                                            reflectable = false;
                                                            absorbable = false;
                                                            collides = false;
                                                            collideTerrain = true;
                                                            hitEffect = Fx.none;
                                                            despawnEffect = Fx.none;
                                                            damage = 0;
                                                            speed = 14;
                                                            lifetime = 2;
                                                            fragRandomSpread = 0;
                                                            fragBullets = 6;
                                                            fragBullet = new ShrapnelBulletType()
                                                            {{
                                                                    fromColor = Color.valueOf("000000");
                                                                    toColor = Color.valueOf("202020");
                                                                    width = 30;
                                                                    length = 60;
                                                                    damage = 60;
                                                                    serrationLenScl = 7f;
                                                                    serrationSpaceOffset = 60f;
                                                                    serrationFadeOffset = 0f;
                                                                    serrations = 10;
                                                                    serrationWidth = 6f;
                                                                    speed = 0;
                                                                    lifetime = 5;
                                                            }};
                                                    }};
                                                }};
                                            }};
                                        }};
                                    }};
                                }};
                        }};
                    }});
        }};
        omamori = new PvUnitType("omamori") {{
                localizedName = "[green]Omamori[]";
                description = "Omamori protects his allies to the very end. His many Attributes are [orange]\n1. Revive Allys\n2. Last stand for allies.\n3. Spawn Omais.";
                Seq<String> detailList = new Seq<>();
                detailList.addAll(
                        "[green]Omamori[].The Large Tank Run by a peaceful [green]Medusa AI[] Know to only attack when its place of rest has been disrespected",
                        "the gentle giant that Omamori is makes him greatly feared but he ins't one prone to attack",
                        "he gained the power to protect his allies with [green]Prevention[] & [blue]Last Stand[], [green]He can give a ally a second life[] and [blue]Great resistance at low health.",
                        "[orange]Usual Behaviour Between Factions : ",
                        "[green]Allianced Factions : []Xeal , Psy , Yggdrasil",
                        "[grey]Neutral Factions :[] Nullis, Azulex",
                        "[red]Enemy Factions : []Mortikai"
                );
                StringBuilder sb = new StringBuilder();
                detailList.each(cs -> sb.append(cs + "\n"));
                sb.replace(sb.length() - 1, sb.length(), "");
                details = sb.toString();
                constructor = EntityMapping.map("conquer");
                drag = 0.1f;
                speed = 0.5f;
                health = 80000;
                hitSize = 8*8;
                armor = 50f;
                omniMovement = false;
                faceTarget = false;
                rotateSpeed = 0.8f;
                drownTimeMultiplier = 0.5f;
                buildSpeed = 0.2f;
                ammoType = new ItemAmmoType(PvItems.darkMatter, 8);
                treadPullOffset = 5;
                treadRects = new Rect[]{new Rect(-120f, -140f, 55f, 280)};
                treadFrames = 2*28;
                abilities.add(new StatusFieldAbility(PvStatusEffects.prevention, 10f, 6000f, 400f));
                abilities.add(new StatusFieldAbility(PvStatusEffects.lastStand, 10f, 2000f, 400f));
                abilities.add(new RepairFieldAbility(5000f, 4000, 400f));
                weapons.add(
                new Weapon(name + "-weapon-main") {{
                    shootY = 3f;
                    x = 0f;
                    y = 0f;
                    top = false;
                    mirror = false;
                    shootWarmupSpeed = 0.06f;
                    minWarmup = 0.9f;
                    reload = 100f;
                    targetAir = true;
                    targetGround = true;
                    inaccuracy = 10f;
                    chargeSound = Sounds.plasmadrop;
                    shootStatusDuration = 200f;
                    shootStatus = StatusEffects.slow;
                    shootSound = Sounds.plasmaboom;
                    cooldownTime = 300f;
                    heatColor = Color.valueOf("addada");
                    recoil = 0f;
                    shoot.firstShotDelay = 80f;
                    shoot.shots = 100;
                    shoot.shotDelay = 1f;
                    rotate = true;
                    rotateSpeed = 0.8f;
                    layerOffset = 2f;
                    bullet = new BasicBulletType(2.5f, 175f) {{
                        pierce = true;
                        pierceBuilding = true;
                        pierceCap = 8;
                        laserAbsorb = false;
                        buildingDamageMultiplier = 0.1f;
                        sprite = PvUtil.GetName("error-bullet-top");
                        backSprite = PvUtil.GetName("error-bullet-bottom");
                        shootEffect = smokeEffect = Fx.none;
                        reflectable = false;
                        absorbable = true;
                        height = width = 18f;
                        shrinkY = shrinkX = -0.4f;
                        spin = 4f;
                        lifetime = 50f;
                        trailColor = backColor = Color.valueOf("011414");
                        frontColor = Color.valueOf("021d1d");
                        trailEffect = hitEffect = despawnEffect = Fx.none;
                        trailInterval = 0f;
                        trailParam = 0f;
                        trailLength = 7;
                        trailWidth = 4f;
                        laserAbsorb = false;
                        homingRange = 600f;
                        homingPower = 0.04f;
                        homingDelay = 30f;
                    }};
                }},
                new Weapon(name + "-weapon-rail") {{
                    x = 26f;
                    y = 16f;
                    top = false;
                    mirror = true;
                    reload = 10f;
                    inaccuracy = 0f;
                    shootSound = Sounds.malignShoot;
                    cooldownTime = 300f;
                    heatColor = Color.valueOf("addada");
                    recoil = 2f;
                    rotate = true;
                    rotateSpeed = 2f;
                    layerOffset = 1f;
                    bullet = new RailBulletType() {{
                        length = 300f;
                        damage = 50f;
                        hitColor = Color.valueOf("021d1d");
                        hitEffect = endEffect = Fx.hitBulletColor;
                        pierce = true;
                        pierceBuilding = true;
                        pierceCap = 4;
                        laserAbsorb = false;
                        smokeEffect = Fx.colorSpark;
                        endEffect = new Effect(14f, e -> {
                            color(e.color);
                            Drawf.tri(e.x, e.y, e.fout() * 1.5f, 5f, e.rotation);
                        });
                        shootEffect = new Effect(10, e -> {
                            color(e.color);
                            float w = 1.2f + 7 * e.fout();
                            Drawf.tri(e.x, e.y, w, 30f * e.fout(), e.rotation);
                            color(e.color);
                            for(int i : Mathf.signs){
                                Drawf.tri(e.x, e.y, w * 0.9f, 18f * e.fout(), e.rotation + i * 90f);
                            }
                            Drawf.tri(e.x, e.y, w, 4f * e.fout(), e.rotation + 180f);
                        });
                        lineEffect = new Effect(20f, e -> {
                            if(!(e.data instanceof Vec2 v)) return;
                            color(e.color);
                            stroke(e.fout() * 0.9f + 0.6f);
                            Fx.rand.setSeed(e.id);
                            for(int i = 0; i < 7; i++){
                                Fx.v.trns(e.rotation, Fx.rand.random(8f, v.dst(e.x, e.y) - 8f));
                                Lines.lineAngleCenter(e.x + Fx.v.x, e.y + Fx.v.y, e.rotation + e.finpow(), e.foutpowdown() * 20f * Fx.rand.random(0.5f, 1f) + 0.3f);
                            }
                            e.scaled(14f, b -> {
                                stroke(b.fout() * 1.5f);
                                color(e.color);
                                Lines.line(e.x, e.y, v.x, v.y);
                            });
                        });
                    }};
                }},
                new Weapon(name + "-weapon-rail") {{
                    x = 18f;
                    y = -24f;
                    top = false;
                    mirror = true;
                    reload = 10f;
                    inaccuracy = 0f;
                    shootSound = Sounds.malignShoot;
                    cooldownTime = 300f;
                    heatColor = Color.valueOf("addada");
                    recoil = 2f;
                    rotate = true;
                    rotateSpeed = 2f;
                    shoot.firstShotDelay = 5f;
                    layerOffset = 1f;
                    bullet = new RailBulletType() {{
                        length = 300f;
                        damage = 50f;
                        hitColor = Color.valueOf("021d1d");
                        hitEffect = endEffect = Fx.hitBulletColor;
                        pierce = true;
                        pierceBuilding = true;
                        pierceCap = 4;
                        laserAbsorb = false;
                        smokeEffect = Fx.colorSpark;
                        endEffect = new Effect(14f, e -> {
                            color(e.color);
                            Drawf.tri(e.x, e.y, e.fout() * 1.5f, 5f, e.rotation);
                        });
                        shootEffect = new Effect(10, e -> {
                            color(e.color);
                            float w = 1.2f + 7 * e.fout();
                            Drawf.tri(e.x, e.y, w, 30f * e.fout(), e.rotation);
                            color(e.color);
                            for(int i : Mathf.signs){
                                Drawf.tri(e.x, e.y, w * 0.9f, 18f * e.fout(), e.rotation + i * 90f);
                            }
                            Drawf.tri(e.x, e.y, w, 4f * e.fout(), e.rotation + 180f);
                        });
                        lineEffect = new Effect(20f, e -> {
                            if(!(e.data instanceof Vec2 v)) return;
                            color(e.color);
                            stroke(e.fout() * 0.9f + 0.6f);
                            Fx.rand.setSeed(e.id);
                            for(int i = 0; i < 7; i++){
                                Fx.v.trns(e.rotation, Fx.rand.random(8f, v.dst(e.x, e.y) - 8f));
                                Lines.lineAngleCenter(e.x + Fx.v.x, e.y + Fx.v.y, e.rotation + e.finpow(), e.foutpowdown() * 20f * Fx.rand.random(0.5f, 1f) + 0.3f);
                            }
                            e.scaled(14f, b -> {
                                stroke(b.fout() * 1.5f);
                                color(e.color);
                                Lines.line(e.x, e.y, v.x, v.y);
                            });
                        });
                    }};
                }},

                new Weapon(name + "-weapon-wave"){{
                    x = 0f;
                    y = 0f;
                    shootY = 0f;
                    top = false;
                    mirror = false;
                    reload = 800f;
                    inaccuracy = 360f;
                    shootSound = Sounds.plasmadrop;
                    cooldownTime = 300f;
                    heatColor = Color.valueOf("addada");
                    recoil = 0f;
                    shootCone = 360;
                    shoot.shots = 180;
                    shoot.shotDelay = 0f;
                    useAmmo = false;
                    controllable = false;
                    autoTarget = true;
                    bullet = new BulletType(2.5f, 20f) {{
                        buildingDamageMultiplier = 2f;
                        pierce = true;
                        pierceBuilding = true;
                        pierceCap = 400;
                        shootEffect = smokeEffect = Fx.none;
                        reflectable = false;
                        absorbable = false;
                        hittable = false;
                        lifetime = 80f;
                        trailEffect = hitEffect = despawnEffect = Fx.none;
                        laserAbsorb = false;
                        trailColor = Color.valueOf("011414");
                        trailInterval = 0.2f;
                        trailParam = 0.2f;
                        trailLength = 2;
                        trailWidth = 4f;
                        knockback = 8;
                        impact = true;
                        collidesAir = false;
                        keepVelocity = false;
                    }};
                }});
                weapons.add(
                new Weapon(name + "-factory") {{
                    x = 0f;
                    y = 0f;
                    shootWarmupSpeed = 0.06f;
                    minWarmup = 0.3f;
                    top = true;
                    mirror = true;
                    alternate = false;
                    shootX = 40f;
                    reload = 1200f;
                    shootSound = Sounds.respawn;
                    recoil = 0f;
                    shootCone = 360f;
                    layerOffset = 0.06f;
                    parts.add(
                    new RegionPart("-out"){{
                        progress = PartProgress.recoil;
                        mirror = false;
                        under = true;
                        layerOffset = -0.02f;
                        outline = false;
                    }},
                    new RegionPart("-unit"){{
                        progress = PartProgress.reload.curve(Interp.pow2In);

                        colorTo = new Color(1f, 1f, 1f, 0f);
                        color = Color.white;
                        mixColorTo = Color.white;
                        mixColor = new Color(1f, 1f, 1f, 0f);
                        outline = false;
                        under = true;
                        x = 1;

                        layerOffset = -0.01f;


                        moves.add(new PartMove(PartProgress.recoil.inv(), -2f, 0f, 0f));
                    }});
                    bullet = new BasicBulletType() {{
                        speed = 0f;
                        keepVelocity = false;
                        collidesAir = false;
                        spawnUnit = new PvUnitType("omai") {{
                            localizedName = "[green]Omai[]";
                            description = "[gold]Children Of Omamori[], Helps Omamori deal with eneimes.";
                            constructor = EntityMapping.map("stell");
                            drag = 0.1f;
                            speed = 0.6f;
                            this.hitSize = 8f;
                            health = 4000;
                            armor = 20f;
                            omniMovement = false;
                            faceTarget = false;
                            rotateSpeed = 1.4f;
                            drownTimeMultiplier = 0.5f;
                            treadPullOffset = 5;
                            treadRects = new Rect[]{new Rect(17 - 96f / 2f, 10 - 96f / 2f, 19, 76)};
                            ammoType = new ItemAmmoType(PvItems.darkMatter, 8);
                            weapons.add(
                            new Weapon(name + "-weapon") {{
                                shootY = 3f;
                                x = 0f;
                                y = 0f;
                                top = false;
                                mirror = false;
                                reload = 100f;
                                inaccuracy = 10f;
                                chargeSound = Sounds.plasmadrop;
                                shootStatusDuration = 200f;
                                shootStatus = StatusEffects.slow;
                                shootSound = Sounds.plasmaboom;
                                cooldownTime = 300f;
                                heatColor = Color.valueOf("addada");
                                recoil = 0f;
                                shoot.firstShotDelay = 80f;
                                shoot.shots = 100;
                                shoot.shotDelay = 1f;
                                rotate = true;
                                rotateSpeed = 0.8f;
                                layerOffset = 1f;
                                immunities.add(PvStatusEffects.prevention);
                                immunities.add(PvStatusEffects.lastStand);
                                bullet = new BasicBulletType(2.5f, 20f) {{
                                    pierce = true;
                                    pierceBuilding = true;
                                    pierceCap = 8;
                                    laserAbsorb = false;
                                    buildingDamageMultiplier = 0.1f;
                                    sprite = PvUtil.GetName("error-bullet-top");
                                    backSprite = PvUtil.GetName("error-bullet-bottom");
                                    shootEffect = smokeEffect = Fx.none;
                                    reflectable = false;
                                    absorbable = false;
                                    height = width = 6f;
                                    shrinkY = shrinkX = -0.4f;
                                    spin = 4f;
                                    lifetime = 50f;
                                    trailColor = backColor = Color.valueOf("011414");
                                    frontColor = Color.valueOf("021d1d");
                                    trailEffect = hitEffect = despawnEffect = Fx.none;
                                    trailInterval = 0f;
                                    trailParam = 0f;
                                    trailLength = 7;
                                    trailWidth = 4f;
                                    laserAbsorb = false;
                                    homingRange = 600f;
                                    homingPower = 0.04f;
                                    homingDelay = 30f;
                                }};
                            }},
                            new Weapon(name + "-weapon-wave"){{
                                x = 0f;
                                y = 0f;
                                shootY = 0f;
                                top = false;
                                mirror = false;
                                reload = 800f;
                                inaccuracy = 360f;
                                shootSound = Sounds.plasmadrop;
                                cooldownTime = 300f;
                                heatColor = Color.valueOf("addada");
                                recoil = 0f;
                                shootCone = 360;
                                shoot.shots = 180;
                                shoot.shotDelay = 0f;
                                useAmmo = false;
                                controllable = false;
                                autoTarget = true;
                                bullet = new BulletType(2.5f, 2f) {{
                                    pierce = true;
                                    pierceBuilding = true;
                                    pierceCap = 400;
                                    buildingDamageMultiplier = 2f;
                                    removeAfterPierce = false;
                                    shootEffect = smokeEffect = Fx.none;
                                    reflectable = false;
                                    absorbable = false;
                                    hittable = false;
                                    lifetime = 40f;
                                    trailEffect = hitEffect = despawnEffect = Fx.none;
                                    laserAbsorb = false;
                                    trailColor = Color.valueOf("011414");
                                    trailInterval = 0.2f;
                                    trailParam = 0.2f;
                                    trailLength = 2;
                                    trailWidth = 4f;
                                    knockback = 8;
                                    impact = true;
                                    collidesAir = false;
                                    keepVelocity = false;
                                }};
                            }});
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
                bullet = new BasicBulletType(3f, 28){{
                    width = 12f;
                    height = 15f;
                    homingPower = 0.01f;
                    lifetime = PvUtil.GetRange(3,26);
                    trailColor = backColor = lightColor = Pal.neoplasm1;
                    frontColor = Pal.neoplasm2;
                    trailLength = 24;
                    trailChance = 0.1f;
                    trailWidth = 1.4f;
                    splashDamage = 30;
                    splashDamageRadius = 8*1.5f;
                    ammoMultiplier = 2;
                    despawnEffect = hitEffect = new MultiEffect(Fx.explosion,Fx.smokeCloud);
                }};
            }});
        }};
        centi = new PvUnitType("centi") {{
            localizedName = "Centi";
            constructor = EntityMapping.map("ElevationMoveUnit");
            speed = 9.8f/7.5f;
            engineOffset = 8;
            drag = 0.13f;
            hitSize = 10f;
            health = 925;
            armor = 5;
            range = 29*8;
            accel = 0.4f;
            rotateSpeed = 3.3f;
            faceTarget = false;
            hovering = true;
            trailLength = 15;
            trailScl = 1;
            trailColor = Pal.neoplasm1;
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
                reload = 54;
                x = 0f;
                shootY = 0f;
                y = -1f;
                rotate = true;
                mirror = false;
                ejectEffect = Fx.casing1;
                recoil = 0f;
                minWarmup = 0.6f;
                shoot.shots = 5;
                shoot.shotDelay = 5;
                shootWarmupSpeed = 0.1f;
                layerOffset = 1f;
                inaccuracy = 1;
                bullet = new MissileBulletType(3f, 18){{
                    width = 3f;
                    height = 7f;
                    homingPower = 0.01f;
                    lifetime = PvUtil.GetRange(this.speed,29);
                    trailColor = backColor = lightColor = Pal.neoplasm1;
                    frontColor = Pal.neoplasm2;
                    trailLength = 12;
                    trailChance = 0.1f;
                    trailWidth = 0.6f;
                    splashDamage = 11;
                    splashDamageRadius = 10f;
                    ammoMultiplier = 2;
                    despawnEffect = hitEffect = new MultiEffect(Fx.explosion,Fx.smokeCloud);
                }};
                parts.add(
                new RegionPart("-barrel"){{
                    progress = PartProgress.warmup;
                    heatProgress = PartProgress.recoil;
                    heatColor = Pal.neoplasm1;
                    mirror = true;
                    under = false;
                    moveX = -0.6f;
                    moveY = -0.6f;
                    moves.add(new PartMove(PartProgress.recoil, 0f, -0.5f, 0f));
                }});
            }});
        }};
        deci = new PvUnitType("deci") {{
            localizedName = "Deci";
            constructor = EntityMapping.map("ElevationMoveUnit");
            speed = 8.5f/7.5f;
            engineOffset = 8;
            drag = 0.13f;
            hitSize = 8f*2;
            health = 2500;
            armor = 7;
            range = 34*8;
            accel = 0.4f;
            rotateSpeed = 3.3f;
            faceTarget = true;
            hovering = true;
            parts.add(
                    new HoverPart(){{
                        x = -12.7f;
                        y = -11.5f;
                        mirror = true;
                        radius = 10;
                        phase = 60f;
                        stroke = 5f;
                        layerOffset = -0.1f;
                        color = Pal.neoplasmMid;
                        //back hovers
                    }},
                    new RegionPart("-blade"){{
                        y = -2.3f;
                        x = -6.95f;
                        moveRot = 15f;
                        under = true;
                        moves.add(new PartMove(PartProgress.reload, 0f, -1f, 5f));
                        progress = PartProgress.warmup;
                        mirror = true;

                        children.add(new RegionPart("-hover"){{
                            moveX = 2f;
                            moveY = 2f;
                            progress = PartProgress.warmup;
                            under = true;

                            children.add(new HoverPart(){{
                                x = -6f;
                                y = 13.5f;
                                mirror = true;
                                radius = 10;
                                phase = 60f;
                                stroke = 5f;
                                layerOffset = -0.1f;
                                color = Pal.neoplasmMid;
                                //front hovers
                            }});
                        }});
                    }});


            weapons.add(
                    new Weapon(){{
                reload = 60f/0.7f;
                x = 0f;
                shootY = 0f;
                y = -2f;
                mirror = false;
                ejectEffect = Fx.casing1;
                recoil = 0f;
                minWarmup = 0.9f;
                shootWarmupSpeed = 0.1f;
                shoot = new ShootHelix(){{
                    mag = 2f;
                    scl = 5f;
                }};
                bullet = new MissileBulletType(3f, 84){{
                    width = 6f;
                    height = 8f;
                    homingPower = 0.01f;
                    lifetime = PvUtil.GetRange(this.speed,34);
                    trailColor = backColor = lightColor = Pal.neoplasm1;
                    frontColor = Pal.neoplasm2;
                    trailLength = 12;
                    trailChance = 0.1f;
                    trailWidth = 0.6f;
                    splashDamage = 22;
                    splashDamageRadius = 10f;
                    ammoMultiplier = 2;
                    despawnEffect = hitEffect = new MultiEffect(Fx.explosion,Fx.smokeCloud);
                }};
            }}, new Weapon(){{
                reload = 60f / 0.7f;
                minWarmup = 0.9f;
                x = 0;
                y = 0;
                mirror = false;
                shootWarmupSpeed = 0.1f;
                bullet = new MissileBulletType(3f,84f) {{
                    width = 12f;
                    height = 16f;
                    homingPower = 0.01f;
                    lifetime = PvUtil.GetRange(this.speed,34);
                    trailColor = backColor = lightColor = Pal.neoplasm1;
                    frontColor = Pal.neoplasm2;
                    trailLength = 30;
                    trailChance = 0.1f;
                    trailWidth = 1.2f;
                    splashDamage = 40;
                    splashDamageRadius = 8*1.5f;
                    despawnEffect = hitEffect = new MultiEffect(Fx.explosion,Fx.smokeCloud);
                    fragBullets = 10;
                    fragBullet = new MissileBulletType(2,24)
                    {{
                        width = 3f;
                        height = 4f;
                        homingPower = 0.01f;
                        lifetime = PvUtil.GetRange(this.speed,5);
                        trailColor = backColor = lightColor = Pal.neoplasm1;
                        frontColor = Pal.neoplasm2;
                        trailLength = 12;
                        trailChance = 0.1f;
                        trailWidth = 0.6f;
                        splashDamage = 22;
                        splashDamageRadius = 10f;
                        ammoMultiplier = 2;
                    }};
                }};
                    }}
            );
        }};
        hecto = new PvUnitType("hecto") {{
            localizedName = "Hecto";
            constructor = EntityMapping.map("ElevationMoveUnit");
            speed = 6.2f/7.5f;
            engineOffset = 8;
            drag = 0.13f;
            hitSize = 8*5f;
            health = 18500;
            armor = 9;
            range = 34*8;
            accel = 0.4f;
            rotateSpeed = 3.3f;
            faceTarget = true;
            hovering = true;
            weapons.add(
                    new Weapon(PvUtil.GetName("hecto-turret")){{
                        reload = 60f/2.3f;
                        x = 12f;
                        shootY = 2f;
                        layerOffset = 1;
                        y = 6f;
                        mirror = true;
                        alternate = true;
                        ejectEffect = Fx.casing1;
                        recoil = 4f;
                        shootWarmupSpeed = 0.1f;
                        rotate = true;
                        rotateSpeed = 2;
                        rotationLimit = 90;
                        shoot = new ShootHelix();
                        bullet = new MissileBulletType(3f, 12){{
                            width = 6f;
                            height = 8f;
                            homingPower = 0.01f;
                            lifetime = PvUtil.GetRange(this.speed,34);
                            trailColor = backColor = lightColor = Pal.neoplasm1;
                            frontColor = Pal.neoplasm2;
                            trailLength = 12;
                            trailChance = 0.1f;
                            trailWidth = 0.6f;
                            splashDamage = 22;
                            splashDamageRadius = 10f;
                            ammoMultiplier = 2;
                            despawnEffect = hitEffect = new MultiEffect(Fx.explosion,Fx.smokeCloud);
                        }};
                    }}, new Weapon(){{
                        reload = 60f / 0.7f;
                        minWarmup = 0.9f;
                        x = 0;
                        y = 0;
                        mirror = false;
                        shootWarmupSpeed = 0.1f;
                        bullet = new BasicBulletType(5,50){{
                            shootEffect = new MultiEffect(Fx.shootTitan, new WaveEffect(){{
                                colorTo = Pal.neoplasm1;
                                sizeTo = 26f;
                                lifetime = 14f;
                                strokeFrom = 4f;
                            }});
                            smokeEffect = Fx.shootSmokeTitan;
                            hitColor = Pal.neoplasm1;

                            sprite = "large-orb";
                            trailEffect = Fx.missileTrail;
                            trailInterval = 3f;
                            trailParam = 4f;
                            pierceCap = 3;
                            fragOnHit = true;
                            lifetime = PvUtil.GetRange(this.speed,39);
                            width = height = 16f;
                            backColor = Pal.neoplasm1;
                            frontColor = Color.white;
                            shrinkX = shrinkY = 0f;
                            trailColor = Pal.neoplasm1;
                            trailLength = 12;
                            trailWidth = 2.2f;
                            despawnEffect = hitEffect = new ExplosionEffect(){{
                                waveColor = Pal.neoplasm1;
                                smokeColor = Color.gray;
                                sparkColor = Pal.sap;
                                waveStroke = 4f;
                                waveRad = 40f;
                            }};
                            despawnSound = Sounds.dullExplosion;

                            //TODO shoot sound
                            shootSound = Sounds.cannon;

                            fragBullet = intervalBullet = new BasicBulletType(3f, 35){{
                                width = 9f;
                                hitSize = 5f;
                                height = 15f;
                                pierce = true;
                                lifetime = 35f;
                                pierceBuilding = true;
                                hitColor = backColor = trailColor = Pal.neoplasm1;
                                frontColor = Color.white;
                                trailWidth = 2.1f;
                                trailLength = 5;
                                hitEffect = despawnEffect = new WaveEffect(){{
                                    colorFrom = colorTo = Pal.neoplasm1;
                                    sizeTo = 4f;
                                    strokeFrom = 4f;
                                    lifetime = 10f;
                                }};
                                homingPower = 0.2f;
                            }};

                            bulletInterval = 5f;
                            intervalRandomSpread = 15f;
                            intervalBullets = 2;
                            intervalAngle = 180f;
                            intervalSpread = 300f;

                            fragBullets = 10;
                            fragVelocityMin = 0.6f;
                            fragVelocityMax = 1.0f;
                            fragLifeMin = 0.8f;
                        }};
                    }}
            );
            parts.add(
                    new HoverPart(){{
                        x = -14f;
                        y = -14;
                        mirror = true;
                        radius = 10;
                        phase = 60f;
                        stroke = 5f;
                        layerOffset = -0.1f;
                        color = Pal.neoplasmMid;
                        //back hovers
                    }},
                    new RegionPart("-front"){{
                        y = x = 0;
                        moveRot = 15f;
                        under = true;
                        //moves.add(new PartMove(PartProgress.reload, 0f, -1f, 5f));
                        progress = PartProgress.warmup;
                        mirror = true;
                        weaponIndex = 2;
                        recoilIndex = 0;
                        children.add(
                                new RegionPart("-rail") {{
                                    mirror = false;
                                    y = x = 0;
                                    moveX = 0;
                                    moveY = -2;
                                    moveRot = 5;
                                    progress = PartProgress.reload;
                                }}
                        );
                    }});
        }};
        kilo = new PvUnitType("kilo") {
            {
                localizedName = "Kilo";
                constructor = EntityMapping.map("ElevationMoveUnit");
                speed = 6.2f / 7.5f;
                engineOffset = 8;
                drag = 0.13f;
                hitSize = 8 * 5f;
                health = 18500;
                armor = 9;
                range = 34 * 8;
                accel = 0.4f;
                rotateSpeed = 3.3f;
                faceTarget = true;
                hovering = true;
                weapons.add(
                        new Weapon(PvUtil.GetName("kilo-weapon")) {{
                            reload = 60f / 2.3f;
                            x = 22f;
                            shootY = 10f;
                            layerOffset = 1;
                            y = 12f;
                            mirror = true;
                            alternate = true;
                            ejectEffect = Fx.casing1;
                            recoil = 4f;
                            shootWarmupSpeed = 0.1f;
                            rotate = true;
                            rotateSpeed = 2;
                            rotationLimit = 90;
                            bullet = new RailBulletType() {{
                                length = 300f;
                                damage = 50f;
                                hitColor = Pal.neoplasm1;
                                hitEffect = endEffect = Fx.hitBulletColor;
                                pierce = true;
                                pierceBuilding = true;
                                pierceCap = 4;
                                laserAbsorb = false;
                                smokeEffect = Fx.colorSpark;
                                endEffect = new Effect(14f, e -> {
                                    color(e.color);
                                    Drawf.tri(e.x, e.y, e.fout() * 1.5f, 5f, e.rotation);
                                });
                                shootEffect = new Effect(10, e -> {
                                    color(e.color);
                                    float w = 1.2f + 7 * e.fout();
                                    Drawf.tri(e.x, e.y, w, 30f * e.fout(), e.rotation);
                                    color(e.color);
                                    for(int i : Mathf.signs){
                                        Drawf.tri(e.x, e.y, w * 0.9f, 18f * e.fout(), e.rotation + i * 90f);
                                    }
                                    Drawf.tri(e.x, e.y, w, 4f * e.fout(), e.rotation + 180f);
                                });
                                lineEffect = new Effect(20f, e -> {
                                    if(!(e.data instanceof Vec2 v)) return;
                                    color(e.color);
                                    stroke(e.fout() * 0.9f + 0.6f);
                                    Fx.rand.setSeed(e.id);
                                    for(int i = 0; i < 7; i++){
                                        Fx.v.trns(e.rotation, Fx.rand.random(8f, v.dst(e.x, e.y) - 8f));
                                        Lines.lineAngleCenter(e.x + Fx.v.x, e.y + Fx.v.y, e.rotation + e.finpow(), e.foutpowdown() * 20f * Fx.rand.random(0.5f, 1f) + 0.3f);
                                    }
                                    e.scaled(14f, b -> {
                                        stroke(b.fout() * 1.5f);
                                        color(e.color);
                                        Lines.line(e.x, e.y, v.x, v.y);
                                    });
                                });
                            }};
                        }}, new Weapon() {{
                            reload = 60f / 0.2f;
                            minWarmup = 0.9f;
                            x = 0;
                            y = 16;
                            mirror = false;
                            shootWarmupSpeed = 0.1f;
                            bullet = new BasicBulletType() {{
                                var CoLor = Pal.neoplasm1;
                                lifetime = PvUtil.GetRange(10f, 120);
                                speed = 10f;
                                collides = false;
                                collidesAir = false;
                                collidesGround = false;
                                bulletInterval = PvUtil.GetRange(10f, 4);
                                intervalDelay = PvUtil.GetRange(10f, 12);
                                intervalBullets = 1;
                                lightColor = backColor = CoLor;
                                fragBullets = 1;
                                fragOnHit = false;
                                intervalRandomSpread = intervalSpread = intervalAngle = 0;
                                intervalBullet = new AreaShockBulletType(300, 60) {{
                                    sprite = PvUtil.GetName("arrow");
                                    backSprite = PvUtil.GetName("arrow-back");
                                    height = width = 20;
                                    splashDelay = 90;
                                    splashAmount = 2;
                                    frontColor = CoLor.cpy().a(0.4f);
                                    particleColor = bottomColor = backColor = CoLor;
                                }};
                            }};
                        }}
                );
                parts.add(
                        new HoverPart() {{
                            x = -14f;
                            y = -22f;
                            mirror = true;
                            radius = 20;
                            phase = 120f;
                            stroke = 5f;
                            layerOffset = -0.1f;
                            color = Pal.neoplasmMid;
                            //back hovers
                        }},
                        new HoverPart() {{
                            x = 28f;
                            y = 28f;
                            mirror = true;
                            radius = 12;
                            phase = 90f;
                            stroke = 5f;
                            layerOffset = -0.1f;
                            color = Pal.neoplasmMid;
                        }}
                );
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
                bullet = new BasicBulletType(3f, 12){{
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
                bullet = new BasicBulletType(3f, 46){{
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
            // I don't know what stats to put so basic shit /otamamori
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
            hitSize = 3*8f;
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
                inaccuracy = 3;
                layerOffset = 0.001f;
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
                    outline = true;
                    moves.add(new PartMove(PartProgress.reload, 0f, -0.5f, -10f));
                    progress = PartProgress.warmup;
                    mirror = true;
                }});
                bullet = new BasicBulletType(3f, 144){{
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
                inaccuracy = 3;
                y = 4f;
                recoil = 2;
                rotate = true;
                layerOffset = 0.001f;
                mirror = true;
                top = true;
                ejectEffect = Fx.casing1;
                parts.add(
                new RegionPart("-blade"){{
                    moveRot = -10f;
                    moveY = -1f;
                    outline = true;
                    moves.add(new PartMove(PartProgress.reload, 0f, -0.5f, -10f));
                    progress = PartProgress.warmup;
                    mirror = true;
                }});
                bullet = new BasicBulletType(3f, 144){{
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
    public static void loadAssaultSpooderPath() {
        chime = new PvUnitType("chime") {{
            health = 325;
            armor = 3;
            speed = 9.5f/7.5f;
            hovering = true;
            localizedName = "[orange]Chime";
            constructor = EntityMapping.map("toxopid");
            legCount = 3;
            legMoveSpace = 3f;
            legPairOffset = 1.5f;
            legLength = 10f;
            legExtension = -2f;
            legBaseOffset = 4f;
            stepShake = 0f;
            legLengthScl = 0.64f;
            rippleScale = 1f;
            legSpeed = 0.2f;
            weapons.add(new Weapon(name + "-hs") {{
                x = 0f;
                y = 0f;
                mirror = false;
                reload = 48f;
                inaccuracy = 0f;
                recoil = 0;
                heatColor = Pal.neoplasm1;
                shootY = -1;
                bullet = new BasicBulletType(2f, 4.5f) {{
                    splashDamage = 42;
                    splashDamageRadius = 3.8f;
                    lifetime = PvUtil.GetRange(2f,12);
                    trailColor = backColor = lightColor = Pal.neoplasm1;
                    frontColor = Pal.neoplasm2;
                    trailLength = 24;
                    trailChance = 0.1f;
                    trailWidth = 1.4f;
                }};
            }});
        }};
        carillon = new PvUnitType("carillon") {{
            health = 950;
            armor = 5;
            speed = 8.1f/7.5f;
            hovering = true;
            localizedName = "[orange]Carillon";
            constructor = EntityMapping.map("toxopid");
            legCount = 4;
            legMoveSpace = 2f;
            legPairOffset = 2f;
            legLength = 12f;
            legExtension = -4f;
            legBaseOffset = 4f;
            stepShake = 0f;
            legLengthScl = 0.74f;
            rippleScale = 2f;
            legSpeed = 0.2f;

            for(int j = 0; j < 3; j++){
                int i = j;
                parts.add(new RegionPart("-spine"){{
                    layerOffset = -0.01f;
                    heatLayerOffset = 0.005f;
                    y = -1;
                    x = 2f;
                    moveX = 6f + i * 1.9f;
                    moveY = 8f + -4f * i;
                    moveRot = 40f - i * 25f;
                    mirror = true;
                    progress = PartProgress.warmup.delay(i * 0.2f);
                    heatProgress = p -> Mathf.absin(Time.time + i * 14f, 7f, 1f);

                    heatColor = Pal.neoplasm1;
                }});
            }
            weapons.add(new Weapon() {{
                x = 4f;
                y = -1f;
                mirror = true;
                reload = 54f;
                alternate = false;
                inaccuracy = 0f;
                recoil = 0;
                baseRotation = -60f;
                shootCone = 140;
                bullet = new BasicBulletType(2.5f, 18f) {{
                    splashDamage = 45;
                    splashDamageRadius = 2.5f;
                    lifetime = PvUtil.GetRange(2.5f,19);
                    homingPower = 0.1f;
                    homingRange = 200;
                    trailColor = backColor = lightColor = Pal.neoplasm1;
                    frontColor = Pal.neoplasm2;
                    trailLength = 24;
                    trailChance = 0.1f;
                    trailWidth = 1.4f;
                }};
            }});
        }};
        knell = new PvUnitType("knell"){{
            health = 2750;
            armor = 7;
            speed = 6.8f/7.5f;
            hovering = true;
            localizedName = "[orange]Knell";
            constructor = EntityMapping.map("toxopid");
            legCount = 6;
            hitSize = 8*2;
            legMoveSpace = 6f;
            legPairOffset = 3f;
            legLength = 24f;
            legExtension = -4f;
            legBaseOffset = 4f;
            stepShake = 0.2f;
            legLengthScl = 1f;
            rippleScale = 2f;
            legSpeed = 0.2f;

            for(int j = 0; j < 4; j++){
                int i = j;
                parts.add(new RegionPart("-spine"){{
                    layerOffset = -0.01f;
                    heatLayerOffset = 0.005f;
                    y = 2;
                    x = 6f;
                    moveX = 2.5f + i * 1f;
                    moveY = 5f + -4f * i;
                    moveRot = 15f - i * 25f;
                    mirror = true;
                    progress = PartProgress.warmup.delay(i * 0.2f);
                    heatProgress = p -> Mathf.absin(Time.time + i * 14f, 7f, 1f);

                    heatColor = Pal.neoplasm1;
                }});
            }
            weapons.add(
                    new Weapon(name+"-weapon") {{
                        x = 7f;
                        y = 4f;
                        mirror = true;
                        reload = 30f/3.2f;
                        alternate = false;
                        recoil = 0;
                        baseRotation = -30f;
                        shootCone = 90;
                        minWarmup = 0.9f;
                        shootWarmupSpeed = 0.02f;
                        shootSound = Sounds.mineDeploy;
                        shootStatus = StatusEffects.slow;
                        shootStatusDuration = 30;
                        inaccuracy = 15;
                        bullet = new BasicBulletType(4f, 45f) {{
                            splashDamage = 73;
                            splashDamageRadius = 4*8f;
                            lifetime = PvUtil.GetRange(4f,25);
                            homingPower = 0.1f;
                            homingRange = 200;
                            homingDelay = 10;
                            trailColor = backColor = lightColor = Pal.neoplasm1;
                            frontColor = Pal.neoplasm2;
                            despawnEffect = hitEffect = Fx.explosion;
                            trailLength = 24;
                            trailChance = 0.1f;
                            trailWidth = 1.4f;
                        }};
                    }}
            );
        }};
        peal = new PvUnitType("peal") {{
            health = 18500;
            armor = 10;
            speed = 5.6f/7.5f;
            hovering = true;
            localizedName = "[orange]Peal";
            constructor = EntityMapping.map("toxopid");
            legCount = 8;
            hitSize = 8*2;
            legMoveSpace = 6f;
            legPairOffset = 4f;
            legLength = 30f;
            legExtension = -4f;
            legBaseOffset = 4f;
            stepShake = 0.5f;
            legLengthScl = 0.94f;
            rippleScale = 4f;
            legSpeed = 0.1f;

            for(int j = 0; j < 4; j++){
                int i = j;
                parts.add(new RegionPart("-spine"){{
                    layerOffset = -0.01f;
                    heatLayerOffset = 0.005f;
                    y = 2;
                    x = 6f;
                    moveX = 5.5f + i * 1.9f;
                    moveY = 9f + -4f * i;
                    moveRot = 30f - i * 25f;
                    mirror = true;
                    progress = PartProgress.warmup.delay(i * 0.2f);
                    heatProgress = p -> Mathf.absin(Time.time + i * 14f, 7f, 1f);

                    heatColor = Pal.neoplasm1;
                }});
            }
            weapons.add(
            new Weapon(name+"-weapon") {{
                x = 9f;
                y = 6f;
                mirror = true;
                reload = 82f;
                alternate = false;
                inaccuracy = 0f;
                recoil = 0;
                baseRotation = -45f;
                shootCone = 140;
                shootSound = Sounds.mineDeploy;

                shoot = new ShootSpread(5, 4f);
                bullet = new BasicBulletType(2.5f, 18f) {{
                    splashDamage = 45;
                    splashDamageRadius = 2.5f*8f;
                    lifetime = PvUtil.GetRange(2.5f,19);
                    homingPower = 0.1f;
                    homingRange = 200;
                    homingDelay = 10;
                    trailColor = backColor = lightColor = Pal.neoplasm1;
                    frontColor = Pal.neoplasm2;
                    trailLength = 24;
                    trailChance = 0.1f;
                    trailWidth = 1.4f;
                }};
            }},
            new Weapon() {{
                x = 0f;
                y = 0f;
                minWarmup = 0.9f;
                mirror = false;
                reload = 300f;
                inaccuracy = 0f;
                recoil = 0;
                shootSound = Sounds.dullExplosion;
                bullet = new BulletType(){{
                    shootEffect = new MultiEffect(Fx.shootBigColor, new Effect(9, e -> {
                        color(Color.white, Pal.neoplasm1, e.fin());
                        stroke(0.7f + e.fout());
                        Lines.square(e.x, e.y, e.fin() * 5f, e.rotation + 45f);

                        Drawf.light(e.x, e.y, 23f, Pal.neoplasm1, e.fout() * 0.7f);
                    }), new WaveEffect(){{
                        colorFrom = colorTo = Pal.neoplasm1;
                        sizeTo = 35f;
                        lifetime = 22f;
                        strokeFrom = 5f;
                    }});

                    smokeEffect = Fx.shootBigSmoke2;
                    shake = 2f;
                    speed = 0f;
                    keepVelocity = false;
                    inaccuracy = 0f;

                    spawnUnit = new MissileUnitType("peal-missile"){{
                        trailColor = engineColor = Pal.neoplasm1;
                        engineSize = 1.75f;
                        engineLayer = Layer.effect;
                        speed = 3.7f;
                        maxRange = 46f;
                        lifetime = PvUtil.GetRange(3.7f,48);
                        outlineColor = Pal.darkOutline;
                        health = 55;
                        lowAltitude = true;

                        weapons.add(new Weapon(){{
                            shootCone = 360f;
                            mirror = false;
                            reload = 1f;
                            shootOnDeath = true;
                            bullet = new ExplosionBulletType(240f, 6.9f){{
                                fragBullets = 6;
                                fragSpread = 45;
                                fragBullet = new FlakBulletType(3,0){{
                                    splashDamage = 15;
                                    splashDamageRadius = 1.6f;
                                    width = height = 5;
                                    homingPower = 6f;
                                    homingRange = 200;
                                    trailColor = backColor = lightColor = Pal.neoplasm1;
                                    frontColor = Pal.neoplasm2;
                                    trailLength = 12;
                                    trailChance = 0.1f;
                                    trailWidth = 0.7f;
                                }};
                                shootEffect = new MultiEffect(Fx.massiveExplosion, new WrapEffect(Fx.dynamicSpikes, Pal.neoplasm1, 24f), new WaveEffect(){{
                                    colorFrom = colorTo = Pal.neoplasm1;
                                    sizeTo = 40f;
                                    lifetime = 12f;
                                    strokeFrom = 4f;
                                }});
                            }};
                        }});
                    }};
                }};
            }});
        }};
        reverberate = new PvUnitType("reverberate") {{
            health = 18500;
            armor = 10;
            speed = 5.6f/7.5f;
            hovering = true;
            localizedName = "[orange]Reverberate";
            constructor = EntityMapping.map("toxopid");
            legCount = 8;
            hitSize = 8*2;
            legMoveSpace = 6f;
            legPairOffset = 4f;
            legLength = 30f;
            legExtension = -4f;
            legBaseOffset = 4f;
            stepShake = 0.5f;
            legLengthScl = 0.94f;
            rippleScale = 4f;
            legSpeed = 0.1f;
            weapons.add(
                    new Weapon(name+"-weapon") {{
                        x = 9f;
                        y = 6f;
                        mirror = true;
                        reload = 82f;
                        alternate = false;
                        inaccuracy = 0f;
                        recoil = 0;
                        baseRotation = -45f;
                        shootCone = 140;
                        shootSound = Sounds.mineDeploy;

                        shoot = new ShootSpread(5, 4f);
                        bullet = new BasicBulletType(2.5f, 18f) {{
                            splashDamage = 45;
                            splashDamageRadius = 2.5f*8f;
                            lifetime = PvUtil.GetRange(2.5f,19);
                            homingPower = 0.1f;
                            homingRange = 200;
                            homingDelay = 10;
                            trailColor = backColor = lightColor = Pal.neoplasm1;
                            frontColor = Pal.neoplasm2;
                            trailLength = 24;
                            trailChance = 0.1f;
                            trailWidth = 1.4f;
                        }};
                    }});
        }};
    }
    public static void loadYggdrasilPath() {
        root = new PvUnitType("root") {{
            health = 250;
            armor = 20;
            localizedName = "[#766e4d]Root";
            constructor = EntityMapping.map("toxopid");
            hitSize = 12;
            speed = 0.54f;
            drag = 0.4f;
            rotateSpeed = 3f;
            legCount = 4;
            legLength = 22f;
            legForwardScl = 0.8f;
            legMoveSpace = 1.4f;
            legBaseOffset = 0f;
            legExtension = -3;
            hovering = true;
            weapons.add(new Weapon(name + "-weapon") {{
                x = 4f;
                y = -1f;
                mirror = true;
                reload = 100f;
                inaccuracy = 10f;
                shootSound = Sounds.mud;
                shoot.shots = 4;
                shoot.shotDelay = 20f;
                rotate = true;
                rotateSpeed = 2.8f;
                shootStatus = PvStatusEffects.photosynthesis;
                shootStatusDuration = 105;
                bullet = new BasicBulletType(0.8f, 15f) {{
                    pierce = true;
                    pierceBuilding = true;
                    pierceCap = 2;
                    sprite = PvUtil.GetName("leaf-bullet");
                    backSprite = PvUtil.GetName("leaf-bullet-back");
                    shootEffect = smokeEffect = Fx.none;
                    height = width = 10f;
                    spin = 0.4f;
                    lifetime = 280f;
                    backColor = Color.valueOf("61726a");
                    frontColor = Color.valueOf("ccdd98");
                    hitEffect = despawnEffect = Fx.hitLaser;
                    laserAbsorb = false;
                    homingRange = 400f;
                    homingPower = 0.02f;
                    homingDelay = 10f;
                }};
            }});
        }};
        stick = new TreebUnitType("stick") {{
            health = 750;
            armor = 40;
            drag = 0.1f;
            speed = 0.62f;
            hitSize = 24;

            rotateSpeed = 2.7f;

            legCount = 6;
            legMoveSpace = 1f;
            legPairOffset = 3;
            legLength = 20f;
            legExtension = -10;
            legBaseOffset = 5f;
            stepShake = 1f;
            legLengthScl = 0.96f;
            rippleScale = 2f;
            legSpeed = 0.2f;
            hovering = true;
            bLength = 1;
            bDamage = 3;
            range = 21 * 8;
            localizedName = "[#766e4d]Stick";
            weapons.add(new Weapon() {{
                display = false;
                shootSound = Sounds.none;
                shootStatus = PvStatusEffects.photosynthesis;
                shootStatusDuration = 65;
                reload = 60;
                bullet = new BulletType() {{
                    instantDisappear = true;
                    smokeEffect = shootEffect = hitEffect = despawnEffect = Fx.none;
                }};
            }});
        }};
        branch = new TreebUnitType("branch") {{
            health = 2250;
            armor = 80;
            drag = 0.1f;
            speed = 0.62f;

            rotateSpeed = 2.7f;

            legCount = 6;
            legMoveSpace = 1f;
            legPairOffset = 3;
            legLength = 30f;
            legExtension = -15;
            legBaseOffset = 10f;
            stepShake = 1f;
            legLengthScl = 0.96f;
            rippleScale = 2f;
            legSpeed = 0.2f;
            hovering = true;
            bDamage = 5;
            bLength = 2;
            range = 30 * 8;
            localizedName = "[#766e4d]Branch";
            weapons.add(new Weapon(PvUtil.GetName("root-weapon")) {{
                        x = 4f;
                        y = -1f;
                        mirror = true;
                        reload = 100f;
                        inaccuracy = 10f;
                        shootSound = Sounds.mud;
                        shoot.shots = 4;
                        shoot.shotDelay = 20f;
                        rotate = true;
                        rotateSpeed = 2.8f;
                        shootStatus = PvStatusEffects.photosynthesis;
                        shootStatusDuration = 105;
                        bullet = new BasicBulletType(0.8f, 15f) {{
                            pierce = true;
                            pierceBuilding = true;
                            pierceCap = 2;
                            sprite = PvUtil.GetName("leaf-bullet");
                            backSprite = PvUtil.GetName("leaf-bullet-back");
                            shootEffect = smokeEffect = Fx.none;
                            height = width = 10f;
                            spin = 0.4f;
                            lifetime = 280f;
                            backColor = Color.valueOf("61726a");
                            frontColor = Color.valueOf("ccdd98");
                            hitEffect = despawnEffect = Fx.hitLaser;
                            laserAbsorb = false;
                            homingRange = 400f;
                            homingPower = 0.02f;
                            homingDelay = 10f;
                        }};
                    }});
        }};
        tree = new TreebUnitType("tree") {{
            health = 6750;
            armor = 100;
            drag = 0.1f;
            speed = 0.5f;
            hovering = true;
            lightRadius = 140f;

            rotateSpeed = 1.9f;
            drownTimeMultiplier = 3f;

            legCount = 8;
            legMoveSpace = 0.8f;
            legPairOffset = 3;
            legLength = 55f;
            legExtension = -15;
            legBaseOffset = 6f;
            stepShake = 1f;
            legLengthScl = 0.93f;
            rippleScale = 3f;
            legSpeed = 0.19f;
            bDamage = 8;
            bLength = 3f;
            range = 45 * 8;
            localizedName = "[#766e4d]Tree";
            weapons.add(new Weapon() {{
                x = 0f;
                y = 0f;
                top = false;
                mirror = false;
                reload = 50f;
                inaccuracy = 360f;
                shootCone = 360;
                shoot = new ShootSpread(){{
                    shots = 8;
                    spread = 45;
                }};
                recoil = 0f;
                shootStatus = PvStatusEffects.photosynthesis;
                shootStatusDuration = 55;
                bullet = new VariantBulletType(){{
                    variants.add(new BasicBulletType(0.8f, 15f) {{
                        pierce = true;
                        pierceBuilding = true;
                        pierceCap = 2;
                        sprite = PvUtil.GetName("leaf-bullet");
                        backSprite = PvUtil.GetName("leaf-bullet-back");
                        shootEffect = smokeEffect = Fx.none;
                        height = width = 10f;
                        spin = 0.4f;
                        lifetime = 280f;
                        backColor = Color.valueOf("61726a");
                        frontColor = Color.valueOf("ccdd98");
                        hitEffect = despawnEffect = Fx.hitLaser;
                        laserAbsorb = false;
                        homingRange = 400f;
                        homingPower = 0.02f;
                        homingDelay = 10f;
                    }});
                    variants.add(new BulletType() {{
                        instantDisappear = true;
                        smokeEffect = shootEffect = hitEffect = despawnEffect = Fx.none;
                        bulletInterval = 5;
                        intervalBullet = new BasicBulletType(0.8f, 15f) {{
                            pierce = true;
                            pierceBuilding = true;
                            pierceCap = 2;
                            sprite = PvUtil.GetName("leaf-bullet");
                            backSprite = PvUtil.GetName("leaf-bullet-back");
                            shootEffect = smokeEffect = Fx.none;
                            height = width = 10f;
                            spin = 0.4f;
                            lifetime = 280f;
                            backColor = Color.valueOf("61726a");
                            frontColor = Color.valueOf("ccdd98");
                            hitEffect = despawnEffect = Fx.hitLaser;
                            laserAbsorb = false;
                            homingRange = 400f;
                            homingPower = 0.02f;
                            homingDelay = 10f;
                        }};
                    }});
                    variants.add(new BulletType() {{
                        instantDisappear = true;
                        smokeEffect = shootEffect = hitEffect = despawnEffect = Fx.none;
                        bulletInterval = 5;
                        intervalBullet = new VariantBulletType(){{
                            variants.add(new BasicBulletType(0.8f, 15f) {{
                                pierce = true;
                                pierceBuilding = true;
                                pierceCap = 2;
                                sprite = PvUtil.GetName("leaf-bullet");
                                backSprite = PvUtil.GetName("leaf-bullet-back");
                                shootEffect = smokeEffect = Fx.none;
                                height = width = 10f;
                                spin = 0.4f;
                                lifetime = 280f;
                                backColor = Color.valueOf("61726a");
                                frontColor = Color.valueOf("ccdd98");
                                hitEffect = despawnEffect = Fx.hitLaser;
                                laserAbsorb = false;
                                homingRange = 400f;
                                homingPower = 0.02f;
                                homingDelay = 10f;
                            }});
                            variants.add(new BulletType() {{
                                instantDisappear = true;
                                smokeEffect = shootEffect = hitEffect = despawnEffect = Fx.none;
                                bulletInterval = 5;
                                intervalBullet = new BasicBulletType(0.8f, 15f) {{
                                    pierce = true;
                                    pierceBuilding = true;
                                    pierceCap = 2;
                                    sprite = PvUtil.GetName("leaf-bullet");
                                    backSprite = PvUtil.GetName("leaf-bullet-back");
                                    shootEffect = smokeEffect = Fx.none;
                                    height = width = 10f;
                                    spin = 0.4f;
                                    lifetime = 280f;
                                    backColor = Color.valueOf("61726a");
                                    frontColor = Color.valueOf("ccdd98");
                                    hitEffect = despawnEffect = Fx.hitLaser;
                                    laserAbsorb = false;
                                    homingRange = 400f;
                                    homingPower = 0.02f;
                                    homingDelay = 10f;
                                }};
                            }});
                        }};
                    }});
                }};
            }});
        }};
        cambrium = new TreebUnitType("cambrium") {{
            health = 20250;
            armor = 120;
            hovering = true;
            drag = 0.1f;
            speed = 0.5f;
            lightRadius = 140f;

            rotateSpeed = 1.9f;
            drownTimeMultiplier = 3f;

            legCount = 8;
            legMoveSpace = 0.8f;
            legPairOffset = 3;
            legLength = 75f;
            legExtension = -20;
            legBaseOffset = 8f;
            stepShake = 1f;
            legLengthScl = 0.93f;
            rippleScale = 3f;
            legSpeed = 0.19f;
            bDamage = 10;
            bLength = 4;
            range = 60 * 8;
            localizedName = "[#766e4d]Cambrium";
        }};
        yggdrasil = new TreebUnitType("yggdrasil") {{
            health = 546750;
            armor = 140;
            hovering = true;
            legCount = 10;
            legMoveSpace = 1.2f;
            legPairOffset = 9f;
            legLength = 100f;
            legExtension = -30;
            legBaseOffset = 24f;
            stepShake = 0f;
            legLengthScl = 1.7f;
            rippleScale = 6f;
            rotateSpeed = 0.25f;
            speed = 0.4f;
            legSpeed = 0.9f;
            bDamage = 15;
            bLength = 6;
            range = 90 * 8;
            localizedName = "[#766e4d]Yggdrasil";
            weapons.add(new Weapon( "project-viscott-hel") {{
                x = 0f;
                y = -6f;
                mirror = false;
                reload = 60;
                inaccuracy = 0;
                minWarmup = 0.8f;
                shootY = 16;
                recoil = 4;
                rotate = true;
                rotateSpeed = 2.8f;
                shootStatus = PvStatusEffects.photosynthesis;
                shootStatusDuration = 105;
                bullet = new BasicBulletType(2,140) {{
                    lifetime = PvUtil.GetRange(2,58);
                    trailLength = 20;
                    trailWidth = 2;
                    drag = -0.001f;
                    status = PvStatusEffects.torture;
                    statusDuration = 120;
                    trailColor = backColor = lightColor = Pal.lancerLaser;
                    trailInterval = 6;
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
                }};
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
            }});
        }};

    }
    public static void loadUnixPath() {
        circuit = new PvUnitType("circuit") {{
            constructor = EntityMapping.map("LegsUnit");
            localizedName = "Circuit";
            drag = 0.08f;
            speed = 14f/7.5f;
            rotateSpeed = 2.5f;
            accel = 0.09f;
            health = 600f;
            armor = 6f;
            hitSize = 8*1.25f;

            range = 15*8;

            stepShake = 0;
            legCount = 4;
            legLength = 16;
            legExtension = 4;
            legGroupSize = 2;
            baseLegStraightness = 0.5f;
            legMoveSpace = 1.5f;
            legStraightness = 0.7f;

            parts.add(
                    new RegionPart("-arm"){{
                            moveRot = -10;
                            progress = PartProgress.warmup;
                            under = true;
                            mirror = true;
                            moves.add(new PartMove(PartProgress.reload, 1f, -1f, 0f));
                    }}
            );

            weapons.add(new Weapon(){{
                x = 0f;
                y = -2f;
                shootY = 0f;
                reload = 60f*1f;
                mirror = false;
                minWarmup = 0.95f;
                shake = 3f;
                cooldownTime = reload - 10f;

                bullet = new BasicBulletType(6,18){{
                    lifetime = PvUtil.GetRange(speed,15);
                    knockback = 0.3f;
                    width = 10f;
                    height = 14f;
                    ammoMultiplier = 2;
                    hitColor = backColor = trailColor = Color.valueOf("6f6e80");
                    frontColor = Color.valueOf("9a9aa6");
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    trailWidth = 2.5f;
                    trailLength = 30;
                    despawnEffect = hitEffect = Fx.explosion;
                    splashDamage = 35f;
                    splashDamageRadius = 3*8;
                    splashDamagePierce = true;

                }};

            }});
        }};

        arrow = new PvUnitType("arrow") {{
            constructor = EntityMapping.map("CrawlUnit");
            localizedName = "Arrow";
            drag = 0.08f;
            speed = 12f/7.5f;
            rotateSpeed = 2.5f;
            accel = 0.09f;
            health = 600f;
            armor = 6f;
            hitSize = 8*1.5f;
            range = 22*8;

            segments = 3;

            omniMovement = false;

            drawBody = false;
            drawCell = false;
            weapons.add(
                    new Weapon(PvUtil.GetName("arrow")) {{
                       shoot.shots = 0;
                       rotate = true;
                       rotateSpeed = 5;
                       top = false;
                       x = y = 0;

                    }},
                    new Weapon(PvUtil.GetName("arrow-arm")){{

                x = 0f;
                y = 0f;
                shootX = 4f;
                shootY = 1f;
                reload = 60f*1f;
                mirror = true;
                top = false;
                rotateSpeed = 5;
                rotate = true;
                minWarmup = 0.95f;
                shake = 3f;
                cooldownTime = reload - 10f;

                bullet = new BasicBulletType(6,12){{
                    lifetime = PvUtil.GetRange(speed,22);
                    width = 10f;
                    height = 14f;
                    hitColor = backColor = trailColor = Pal.heal;
                    frontColor = Color.white;
                    hitEffect = despawnEffect = Fx.heal;
                    trailWidth = 2.5f;
                    trailLength = 30;
                    collidesTeam = true;
                    healPercent = 1.2f;
                    healAmount = 4;

                }};

            }});
        }};

        celestial = new PvUnitType("celestial") {{
            constructor = EntityMapping.map("UnitEntity");
            localizedName = "Celestial";
            drag = 0.03f;
            speed = 26f/7.5f;
            rotateSpeed = 7f;
            accel = 0.2f;
            health = 250f;
            armor = 4f;
            hitSize = 8*1.5f;
            range = 14*8;
            itemCapacity = 20;
            flying = true;

            aiController = RepairAI::new;
            defaultCommand = UnitCommand.repairCommand;
            mineTier = 1;
            mineSpeed = 1.2f;
            buildSpeed = 0.2f;

            weapons.add(new RepairBeamWeapon(){{

                widthSinMag = 0.14f;
                reload = 10f;
                x = 5f;
                y = 4f;
                rotate = false;
                shootY = 0f;
                beamWidth = 0.5f;
                aimDst = 0f;
                shootCone = 40f;
                mirror = true;

                repairSpeed = 0.6f / 2f;
                fractionRepairSpeed = 0.02f;

                targetUnits = false;
                targetBuildings = true;
                autoTarget = false;
                controllable = true;
                laserColor = Pal.heal;
                healColor = Pal.heal;

                bullet = new BulletType(){{
                    maxRange = 14*8;
                }};
            }});
        }};
    }
}