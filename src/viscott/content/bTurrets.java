package viscott.content;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;

import static mindustry.type.ItemStack.with;

public class bTurrets {
    public static Block
    bDuo;
    public static void load(){
        bDuo = new ItemTurret("b-duo"){{
            requirements(Category.turret, with(Items.copper, 35));
            localizedName = "B series Duo";
            ammo(
                    Items.copper,  new BasicBulletType(5f, 19){{
                        width = 7f;
                        height = 9f;
                        lifetime = 60f;
                        ammoMultiplier = 2;
                        lightning = 10;
                        lightningDamage = 5;
                        lightningCone = 180;
                        lightningLengthRand = 40;
                        lightningLength = 16;
                        lightningColor = Color.valueOf("9bc4e4");
                    }},
                    Items.graphite, new BasicBulletType(7f, 28){{
                        width = 9f;
                        height = 12f;
                        reloadMultiplier = 0.6f;
                        ammoMultiplier = 4;
                        lifetime = 60f;
                        lightning = 10;
                        lightningDamage = 5;
                        lightningCone = 180;
                        lightningLengthRand = 40;
                        lightningLength = 16;
                        lightningColor = Color.valueOf("9bc4e4");
                    }},
                    Items.silicon, new BasicBulletType(6f, 22){{
                        width = 7f;
                        height = 9f;
                        homingPower = 0.2f;
                        reloadMultiplier = 1.5f;
                        ammoMultiplier = 5;
                        lifetime = 60f;
                        lightning = 10;
                        lightningDamage = 5;
                        lightningCone = 180;
                        lightningLengthRand = 40;
                        lightningLength = 16;
                        lightningColor = Color.valueOf("9bc4e4");
                    }}
            );

            shoot = new ShootAlternate(3.5f);

            shootY = 3f;
            reload = 10f;
            range = 220;
            shootCone = 15f;
            ammoUseEffect = Fx.casing1;
            health = 450;
            inaccuracy = 2f;
            rotateSpeed = 10f;
            coolant = consumeCoolant(0.1f);

            limitRange();
        }};
    }
}
