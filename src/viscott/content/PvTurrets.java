package viscott.content;

import arc.graphics.Color;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootSpread;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.content.*;
import mindustry.world.draw.DrawTurret;
import viscott.utilitys.PvUtil;

import static mindustry.type.ItemStack.*;

public class PvTurrets{
    public static Block
            splinter;

    public static void load(){
        splinter = new ItemTurret("splinter"){{
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
    }
}
