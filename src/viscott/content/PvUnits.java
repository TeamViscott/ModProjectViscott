package viscott.content;

import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.LaserBoltBulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.gen.EntityMapping;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import viscott.utilitys.PvUtil;

public class PvUnits {
    public static UnitType
        micro,infrared
            ;
    public static void load()
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
            drag = 0.02f;
            range = 18*8;
            weapons.add(
                    new Weapon("")
                    {{
                        x = 0;
                        y = 5;
                        reload = 60f/2f;
                        rotate = true;
                        rotationLimit = 30;
                        shootStatus = PvStatusEffects.expent;
                        shootStatusDuration = 120;
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
    }
}
