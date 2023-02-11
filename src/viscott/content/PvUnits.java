package viscott.content;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.*;
import mindustry.gen.EntityMapping;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import viscott.utilitys.PvUtil;

public class PvUnits {
    public static UnitType
        /*Core Units*/micro,infrared, spectrum,

        /*Flying Ion Path*/particle, snippet,

        /*Extra Paths : */
        routerTank
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
                            shootStatusDuration = 120;
                            bullet = new LaserBoltBulletType(8, 20) {{
                                homingPower = 0.01f;
                                homingDelay = 1;
                                pierce = true;
                                pierceCap = 3;
                                sprite = "circle-bullet";
                                homingRange = 8 * 28f;
                                lifetime = PvUtil.GetRange(this.speed, 28);
                                lightColor = backColor = Pal.engine;
                                this.recoil = 0f;
                                despawnShake = hitShake = 0.5f;
                                fragBullets = 6;
                                fragBullet = new LaserBoltBulletType(6, 6) {{
                                    homingPower = 0.01f;
                                    homingDelay = 1;
                                    sprite = "circle-bullet";
                                    homingRange = 8 * 28f;
                                    lifetime = PvUtil.GetRange(this.speed, 28);
                                    lightColor = backColor = Pal.engine;
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
            deathExplosionEffect = PvEffects.particleDeath;
            weapons.add(
                    new Weapon()
                    {{
                        mirror = true;
                        top = false;
                        x = 4;
                        y = 0;
                        reload = 60f/2.6f;
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
            deathExplosionEffect = PvEffects.particleDeath;
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
                        bullet = new ContinuousLaserBulletType()
                        {{
                            damage = 14;
                            incendChance = 0;
                            hitColor = Color.valueOf("990acd");
                            lifetime = 60f;
                            lightColor = Color.valueOf("990acd");
                            trailColor = Color.valueOf("990acd");
                            colors = new Color[]{Pal.sap.cpy().a(.2f), Pal.sap.cpy().a(.5f), Pal.sap.cpy().mul(1.2f), Color.white};
                            buildingDamageMultiplier = 0.2f;
                            shake = 0;
                            status = StatusEffects.sapped;
                            statusDuration = 120f;
                            length = 14*8f;
                            width = 4f;
                        }};
                    }}
            );
        }};
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
                            sprite = PvUtil.GetName("router-bullet");
                            trailWidth = 2;
                            trailLength = 20;
                            trailColor = lightColor = Pal.lancerLaser;
                            splashDamageRadius = 8*4f;
                            splashDamage = 100;
                            despawnEffect = hitEffect = Fx.massiveExplosion;
                            hitShake = despawnShake = 1;
                            shake = 1;
                        }};
                    }}
            );
        }};
    }
}
