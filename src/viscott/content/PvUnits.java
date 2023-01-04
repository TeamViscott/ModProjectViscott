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
        micro
            ;
    public static void load()
    {
        micro = new UnitType("micro")
        {{
            localizedName = "Micro";
            constructor = EntityMapping.map("flare");
            health = 175;
            armor = 0;
            flying = true;
            buildSpeed = 0.75f;
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
                        lifetime = PvUtil.GetRange(this.speed,17);
                        lightColor = backColor = Pal.heal;
                        this.recoil = 0.1f;
                        despawnShake = hitShake = 0.5f;
                    }};
                }}
            );
        }};
    }
}
