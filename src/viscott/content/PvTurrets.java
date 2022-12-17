package viscott.content;

import arc.graphics.Color;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootSpread;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.content.*;
import mindustry.world.draw.DrawTurret;

import static mindustry.type.ItemStack.*;

public class PvTurrets{
    public static Block
            splinter;

    public static void load(){
        splinter = new ItemTurret("splinter"){{
            size = 2;
            health = 875;
            requirements(Category.turret, with(PvItems.zirconium, 75));
            ammo(
                    PvItems.zirconium,  new BasicBulletType(6f, 14){{
                        knockback = 0.3f;
                        width = 10f;
                        height = 14f;
                        lifetime = 45f;
                        ammoMultiplier = 2;
                        hitColor = backColor = trailColor = Color.valueOf("6f6e80");
                        frontColor = Color.valueOf("9a9aa6");
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                    }},
                    PvItems.platinum,  new BasicBulletType(6f, 37){{
                        knockback = 0.3f;
                        width = 10f;
                        height = 14f;
                        lifetime = 45f;
                        ammoMultiplier = 2;
                        hitColor = backColor = trailColor = Color.valueOf("aaadaf");
                        frontColor = Color.valueOf("d1d6db");
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                    }},
                    PvItems.nobelium,  new BasicBulletType(6f, 32){{
                        knockback = 0.2f;
                        width = 10f;
                        height = 14f;
                        lifetime = 45f;
                        ammoMultiplier = 3;
                        hitColor = backColor = trailColor = Color.valueOf("ef525b");
                        frontColor = Color.valueOf("ffa1a7");
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        status = StatusEffects.blasted;
                        statusDuration = 120f;
                    }}
            );

            shoot = new ShootSpread(3, 5f);

            shootY = 3f;
            reload = 60f;
            range = 208;
            shootCone = 10f;
            ammoUseEffect = Fx.casing1;
            inaccuracy = 8f;
            rotateSpeed = 15f;
            coolant = consumeCoolant(0.2f);
            coolantMultiplier = 1.2f;
            researchCostMultiplier = 0.05f;

            limitRange();
        }};
    }
}
