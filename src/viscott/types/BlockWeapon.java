package viscott.types;

import arc.Core;
import arc.audio.Sound;
import arc.func.Boolf;
import arc.func.Boolf2;
import arc.func.Cons;
import arc.func.Func;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Collapser;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.ai.types.MissileAI;
import mindustry.audio.SoundLoop;
import mindustry.content.Bullets;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.*;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.part.DrawPart;
import mindustry.entities.pattern.ShootPattern;
import mindustry.entities.units.UnitController;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.mod.Mods;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;
import mindustry.world.meta.Stats;

import java.util.Iterator;

import static mindustry.Vars.content;

public class BlockWeapon implements Cloneable {
    public String name;
    public BulletType bullet;
    public Seq<DrawPart> parts;
    public boolean display;

    public float inaccuracy;
    public float reload;
    public float smoothReloadSpeed;

    public float shootWarmupSpeed;
    public boolean linearWarmup;
    public float minWarmup;
    public float layerOffset;
    public boolean continuous;
    public boolean alwaysContinuous;
    public boolean isAlwaysContinuous;

    public ShootPattern shoot;
    public boolean alwaysShooting;
    public Sound shootSound;
    public Sound chargeSound;
    public float baseRotation;
    public float rotateSpeed;
    public float rotationLimit;
    public Func<BlockWeapon, BlockWeaponMount> mountType;

    public TextureRegion region;
    public TextureRegion heatRegion;
    public TextureRegion cellRegion;
    public TextureRegion outlineRegion;
    public Color heatColor;
    public Effect ejectEffect;
    public float shadow;
    public boolean top;
    public boolean flipSprite;
    public float x;
    public float y;
    public float xRand;

    public float shootX;
    public float shootY;
    public float velocityRnd;

    public float cooldownTime;
    public float recoil;
    public float recoilTime;
    public float recoilPow;
    public int recoils;
    public float targetInterval;
    public float targetSwitchInterval;
    public boolean predictTarget;
    public float soundPitchMin;
    public float soundPitchMax;
    public float shake;
    public float shootCone;
    public boolean parentizeEffects;

    public Item itemCost;

    public Boolf<Building> canShootEvent;
    public Cons<Building> shotEvent;

    public String localizedName;


    public BlockWeapon(String name) {
        this.bullet = Bullets.placeholder;
        this.ejectEffect = Fx.none;
        this.display = true;
        this.flipSprite = false;
        this.baseRotation = 0.0F;
        this.top = true;
        this.alwaysShooting = false;
        this.predictTarget = true;
        this.targetInterval = 40.0F;
        this.targetSwitchInterval = 70.0F;
        this.rotateSpeed = 20.0F;
        this.reload = 1.0F;
        this.inaccuracy = 0.0F;
        this.shake = 0.0F;
        this.recoil = 1.5F;
        this.recoils = -1;
        this.recoilTime = 1.0F;
        this.recoilPow = 1.8F;
        this.cooldownTime = 20.0F;
        this.shootX = 0.0F;
        this.shootY = 3.0F;
        this.x = 5.0F;
        this.y = 0.0F;
        this.xRand = 0.0F;
        this.shoot = new ShootPattern();
        this.shadow = -1.0F;
        this.velocityRnd = 0.0F;
        this.shootCone = 5.0F;
        this.rotationLimit = 361.0F;
        this.minWarmup = 0.0F;
        this.shootWarmupSpeed = 0.1F;
        this.smoothReloadSpeed = 0.15F;
        this.linearWarmup = false;
        this.soundPitchMin = 0.8F;
        this.soundPitchMax = 1.0F;
        this.layerOffset = 0.001F;
        this.shootSound = Sounds.pew;
        this.chargeSound = Sounds.none;
        this.heatColor = Pal.turretHeat;
        this.mountType = BlockWeaponMount::new;
        this.parts = new Seq(DrawPart.class);

        Mods.LoadedMod currentMod = Reflect.get(content,"currentMod");

        this.name = currentMod.name + "-" + name;
        this.localizedName = this.name;
        this.canShootEvent = (build) -> true;
        this.shotEvent = (build) -> {};
    }

    public BlockWeapon() {
        this("");
    }

    public boolean hasStats(Block block) {
        return this.display;
    }

    private static String ammoStat(float val) {
        return (val > 0.0F ? "[stat]+" : "[negstat]") + Strings.autoFixed(val, 1);
    }
    public void addStats(Block block) {
        block.stats.add(Stat.weapons,(t) -> {
            if (this.inaccuracy > 0.0F) {
                t.row();
                t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int)this.inaccuracy + " " + StatUnit.degrees.localized());
            }

            if (!this.alwaysContinuous && this.reload > 0.0F) {
                t.row();
                t.add("[lightgray]" + Stat.reload.localized() + ": " + "[white]" + Strings.autoFixed(60.0F / this.reload * (float)this.shoot.shots, 2) + " " + StatUnit.perSecond.localized());
            }
            if (itemCost == null) {
                var table = t;
                table.row();

                BulletType type = bullet;
                if (type.spawnUnit != null && type.spawnUnit.weapons.size > 0) {
                    StatValues.ammo(ObjectMap.of(new Object[]{t, ((Weapon)type.spawnUnit.weapons.first()).bullet}), 0, false).display(table);
                } else {
                    table.table(Styles.grayPanel, (bt) -> {
                        bt.left().top().defaults().padRight(3.0F).left();
                        if (localizedName != name) {
                            bt.table((title) -> {
                                title.add(localizedName).padRight(10.0F).left().top();
                            });
                            bt.row();
                        }

                        if (type.damage > 0.0F && (type.collides || type.splashDamage <= 0.0F)) {
                            if (type.continuousDamage() > 0.0F) {
                                bt.add(Core.bundle.format("bullet.damage", new Object[]{type.continuousDamage()}) + StatUnit.perSecond.localized());
                            } else {
                                bt.add(Core.bundle.format("bullet.damage", new Object[]{type.damage}));
                            }
                        }

                        int val;
                        if (type.buildingDamageMultiplier != 1.0F) {
                            val = (int)(type.buildingDamageMultiplier * 100.0F - 100.0F);
                            bt.row();
                            bt.add(Core.bundle.format("bullet.buildingdamage", new Object[]{ammoStat((float)val)}));
                        }

                        if (type.rangeChange != 0.0F) {
                            bt.row();
                            bt.add(Core.bundle.format("bullet.range", new Object[]{ammoStat(type.rangeChange / 8.0F)}));
                        }

                        if (type.splashDamage > 0.0F) {
                            bt.row();
                            bt.add(Core.bundle.format("bullet.splashdamage", new Object[]{(int)type.splashDamage, Strings.fixed(type.splashDamageRadius / 8.0F, 1)}));
                        }

                        /*
                        if (!Mathf.equal(type.ammoMultiplier, 1.0F) && type.displayAmmoMultiplier) {
                            label114: {
                                if (t instanceof Turret) {
                                    Turret turret = (Turret)t;
                                    if (!turret.displayAmmoMultiplier) {
                                        break label114;
                                    }
                                }

                                sep(bt, Core.bundle.format("bullet.multiplier", new Object[]{(int)type.ammoMultiplier}));
                            }
                        }
                        */

                        if (!Mathf.equal(type.reloadMultiplier, 1.0F)) {
                            val = (int)(type.reloadMultiplier * 100.0F - 100.0F);
                            bt.row();
                            bt.add(Core.bundle.format("bullet.reload", new Object[]{ammoStat((float)val)}));
                        }

                        if (type.knockback > 0.0F) {
                            bt.row();
                            bt.add(Core.bundle.format("bullet.knockback", new Object[]{Strings.autoFixed(type.knockback, 2)}));
                        }

                        if (type.healPercent > 0.0F) {
                            bt.row();
                            bt.add(Core.bundle.format("bullet.healpercent", new Object[]{Strings.autoFixed(type.healPercent, 2)}));
                        }

                        if (type.healAmount > 0.0F) {
                            bt.row();
                            bt.add(Core.bundle.format("bullet.healamount", new Object[]{Strings.autoFixed(type.healAmount, 2)}));
                        }

                        if (type.pierce || type.pierceCap != -1) {
                            bt.row();
                            bt.add(type.pierceCap == -1 ? "@bullet.infinitepierce" : Core.bundle.format("bullet.pierce", new Object[]{type.pierceCap}));
                        }

                        if (type.incendAmount > 0) {
                            bt.row();
                            bt.add("@bullet.incendiary");
                        }

                        if (type.homingPower > 0.01F) {
                            bt.row();
                            bt.add("@bullet.homing");
                        }

                        if (type.lightning > 0) {
                            bt.row();
                            bt.add(Core.bundle.format("bullet.lightning", new Object[]{type.lightning, type.lightningDamage < 0.0F ? type.damage : type.lightningDamage}));
                        }

                        if (type.pierceArmor) {
                            bt.row();
                            bt.add("@bullet.armorpierce");
                        }

                        if (type.suppressionRange > 0.0F) {
                            bt.row();
                            bt.add(Core.bundle.format("bullet.suppression", new Object[]{Strings.autoFixed(type.suppressionDuration / 60.0F, 2), Strings.fixed(type.suppressionRange / 8.0F, 1)}));
                        }

                        if (type.status != StatusEffects.none) {
                            bt.row();
                            bt.add((type.status.minfo.mod == null ? type.status.emoji() : "") + "[stat]" + type.status.localizedName + (type.status.reactive ? "" : "[lightgray] ~ [stat]" + (int)(type.statusDuration / 60.0F) + "[lightgray] " + Core.bundle.get("unit.seconds")));
                        }

                        Collapser coll;
                        Table fc;
                        if (type.intervalBullet != null) {
                            bt.row();
                            fc = new Table();
                            StatValues.ammo(ObjectMap.of(new Object[]{t, type.intervalBullet}), 1, false).display(fc);
                            coll = new Collapser(fc, true);
                            coll.setDuration(0.1F);
                            Collapser finalColl = coll;
                            bt.table((it) -> {
                                it.left().defaults().left();
                                it.add(Core.bundle.format("bullet.interval", new Object[]{Strings.autoFixed((float)type.intervalBullets / type.bulletInterval * 60.0F, 2)}));
                                it.button(Icon.downOpen, Styles.emptyi, () -> {
                                    finalColl.toggle(false);
                                }).update((i) -> {
                                    i.getStyle().imageUp = !finalColl.isCollapsed() ? Icon.upOpen : Icon.downOpen;
                                }).size(8.0F).padLeft(16.0F).expandX();
                            });
                            bt.row();
                            bt.add(coll);
                        }

                        if (type.fragBullet != null) {
                            bt.row();
                            fc = new Table();
                            StatValues.ammo(ObjectMap.of(new Object[]{t, type.fragBullet}), 1, false).display(fc);
                            coll = new Collapser(fc, true);
                            coll.setDuration(0.1F);
                            Collapser finalColl = coll;
                            bt.table((ft) -> {
                                ft.left().defaults().left();
                                ft.add(Core.bundle.format("bullet.frags", new Object[]{type.fragBullets}));
                                ft.button(Icon.downOpen, Styles.emptyi, () -> {
                                    finalColl.toggle(false);
                                }).update((i) -> {
                                    i.getStyle().imageUp = !finalColl.isCollapsed() ? Icon.upOpen : Icon.downOpen;
                                }).size(8.0F).padLeft(16.0F).expandX();
                            });
                            bt.row();
                            bt.add(coll);
                        }

                    }).padLeft((float)(0)).padTop(5.0F).padBottom(5.0F).growX().margin(10.0F);
                    table.row();
                }
            } else {
                StatValues.ammo(ObjectMap.of(new Object[]{itemCost, this.bullet})).display(t);
            }
        });
    }

    public float dps() {
        return this.bullet.estimateDPS() / this.reload * (float)this.shoot.shots * 60.0F;
    }

    public float shotsPerSec() {
        return (float)this.shoot.shots * 60.0F / this.reload;
    }

    public void drawOutline(Building build, BlockWeaponMount mount) {
        if (this.outlineRegion.found()) {
            float rotation = build.rotdeg() - 90.0F;
            float realRecoil = Mathf.pow(mount.recoil, this.recoilPow) * this.recoil;
            float weaponRotation = rotation + mount.rotation;
            float wx = build.x + Angles.trnsx(rotation, this.x, this.y) + Angles.trnsx(weaponRotation, 0.0F, -realRecoil);
            float wy = build.y + Angles.trnsy(rotation, this.x, this.y) + Angles.trnsy(weaponRotation, 0.0F, -realRecoil);
            Draw.xscl = (float)(-Mathf.sign(this.flipSprite));
            Draw.rect(this.outlineRegion, wx, wy, weaponRotation);
            Draw.xscl = 1.0F;
        }
    }

    public void draw(Building build, BlockWeaponMount mount) {
        float z = Draw.z();
        Draw.z(z + this.layerOffset);
        float rotation = build.rotdeg() - 90.0F;
        float realRecoil = Mathf.pow(mount.recoil, this.recoilPow) * this.recoil;
        float weaponRotation = rotation + mount.rotation;
        float wx = build.x + Angles.trnsx(rotation, this.x, this.y) + Angles.trnsx(weaponRotation, 0.0F, -realRecoil);
        float wy = build.y + Angles.trnsy(rotation, this.x, this.y) + Angles.trnsy(weaponRotation, 0.0F, -realRecoil);
        if (this.shadow > 0.0F) {
            Drawf.shadow(wx, wy, this.shadow);
        }

        if (this.top) {
            this.drawOutline(build, mount);
        }

        int i;
        DrawPart part;
        if (this.parts.size > 0) {
            DrawPart.params.set(mount.warmup, mount.reload / this.reload, mount.smoothReload, mount.heat, mount.recoil, mount.charge, wx, wy, weaponRotation + 90.0F);
            DrawPart.params.sideMultiplier = this.flipSprite ? -1 : 1;

            for(i = 0; i < this.parts.size; ++i) {
                part = (DrawPart)this.parts.get(i);
                DrawPart.params.setRecoil(part.recoilIndex >= 0 && mount.recoils != null ? mount.recoils[part.recoilIndex] : mount.recoil);
                if (part.under) {
                    part.draw(DrawPart.params);
                }
            }
        }

        Draw.xscl = (float)(-Mathf.sign(this.flipSprite));

        if (this.region.found()) {
            Draw.rect(this.region, wx, wy, weaponRotation);
        }

        if (this.cellRegion.found()) {
            Draw.color(build.team().color);
            Draw.rect(this.cellRegion, wx, wy, weaponRotation);
            Draw.color();
        }

        if (this.heatRegion.found() && mount.heat > 0.0F) {
            Draw.color(this.heatColor, mount.heat);
            Draw.blend(Blending.additive);
            Draw.rect(this.heatRegion, wx, wy, weaponRotation);
            Draw.blend();
            Draw.color();
        }

        Draw.xscl = 1.0F;
        if (this.parts.size > 0) {
            for(i = 0; i < this.parts.size; ++i) {
                part = (DrawPart)this.parts.get(i);
                DrawPart.params.setRecoil(part.recoilIndex >= 0 && mount.recoils != null ? mount.recoils[part.recoilIndex] : mount.recoil);
                if (!part.under) {
                    part.draw(DrawPart.params);
                }
            }
        }

        Draw.xscl = 1.0F;
        Draw.z(z);
    }

    public BlockWeapon copy() {
        try {
            return (BlockWeapon) this.clone();
        } catch (CloneNotSupportedException var2) {
            throw new RuntimeException("vgld", var2);
        }
    }

    public float range() {
        return this.bullet.range;
    }

    public void update(Building build, BlockWeaponMount mount) {
        boolean can = build.enabled() && canShootEvent.get(build);
        float lastReload = mount.reload;
        mount.reload = Math.max(mount.reload - Time.delta * build.efficiency(), 0.0F);
        mount.recoil = Mathf.approachDelta(mount.recoil, 0.0F, build.efficiency() / this.recoilTime);
        if (this.recoils > 0) {
            if (mount.recoils == null) {
                mount.recoils = new float[this.recoils];
            }

            for(int i = 0; i < this.recoils; ++i) {
                mount.recoils[i] = Mathf.approachDelta(mount.recoils[i], 0.0F, build.efficiency() / this.recoilTime);
            }
        }

        mount.smoothReload = Mathf.lerpDelta(mount.smoothReload, mount.reload / this.reload, this.smoothReloadSpeed);
        mount.charge = mount.charging && this.shoot.firstShotDelay > 0.0F ? Mathf.approachDelta(mount.charge, 1.0F, 1.0F / this.shoot.firstShotDelay) : 0.0F;
        float warmupTarget = (!can || !mount.shoot) && (!this.continuous || mount.bullet == null) && !mount.charging ? 0.0F : 1.0F;
        if (this.linearWarmup) {
            mount.warmup = Mathf.approachDelta(mount.warmup, warmupTarget, this.shootWarmupSpeed);
        } else {
            mount.warmup = Mathf.lerpDelta(mount.warmup, warmupTarget, this.shootWarmupSpeed);
        }

        float weaponRotation;
        float mountX;
        float mountY;
        if ((mount.rotate || mount.shoot) && can) {
            weaponRotation = build.x + Angles.trnsx(build.rotdeg() - 90.0F, this.x, this.y);
            mountX = build.y + Angles.trnsy(build.rotdeg() - 90.0F, this.x, this.y);
            mount.targetRotation = Angles.angle(weaponRotation, mountX, mount.aimX, mount.aimY) - build.rotdeg();
            mount.rotation = Angles.moveToward(mount.rotation, mount.targetRotation, this.rotateSpeed * Time.delta);
            if (this.rotationLimit < 360.0F) {
                mountY = Angles.angleDist(mount.rotation, this.baseRotation);
                if (mountY > this.rotationLimit / 2.0F) {
                    mount.rotation = Angles.moveToward(mount.rotation, this.baseRotation, mountY - this.rotationLimit / 2.0F);
                }
            }
        }

        weaponRotation = build.rotdeg() - 90.0F + mount.rotation;
        mountX = build.x + Angles.trnsx(build.rotdeg() - 90.0F, this.x, this.y);
        mountY = build.y + Angles.trnsy(build.rotdeg() - 90.0F, this.x, this.y);
        float bulletX = mountX + Angles.trnsx(weaponRotation, this.shootX, this.shootY);
        float bulletY = mountY + Angles.trnsy(weaponRotation, this.shootX, this.shootY);
        float shootAngle = this.bulletRotation(build, mount, bulletX, bulletY);
        boolean wasFlipped;

        if ((mount.retarget -= Time.delta) <= 0.0F) {
            mount.target = this.findTarget(build, mountX, mountY, this.bullet.range, this.bullet.collidesAir, this.bullet.collidesGround);
            mount.retarget = mount.target == null ? this.targetInterval : this.targetSwitchInterval;
        }

        if (mount.target != null && this.checkTarget(build, mount.target, mountX, mountY, this.bullet.range)) {
            mount.target = null;
        }

        wasFlipped = false;
        if (mount.target != null) {
            Teamc var10000 = mount.target;
            float var10003 = this.bullet.range + Math.abs(this.shootY);
            Teamc var14 = mount.target;
            float var10004;
            if (var14 instanceof Sized) {
                Sized s = (Sized)var14;
                var10004 = s.hitSize() / 2.0F;
            } else {
                var10004 = 0.0F;
            }

            wasFlipped = var10000.within(mountX, mountY, var10003 + var10004) && can;
            if (this.predictTarget) {
                Vec2 to = Predict.intercept(build, mount.target, this.bullet.speed);
                mount.aimX = to.x;
                mount.aimY = to.y;
            } else {
                mount.aimX = mount.target.x();
                mount.aimY = mount.target.y();
            }
        }

        mount.shoot = mount.rotate = wasFlipped;


        if (this.alwaysShooting) {
            mount.shoot = true;
        }

        if (this.continuous && mount.bullet != null) {
            if (mount.bullet.isAdded() && !(mount.bullet.time >= mount.bullet.lifetime) && mount.bullet.type == this.bullet) {
                mount.bullet.rotation(weaponRotation + 90.0F);
                mount.bullet.set(bulletX, bulletY);
                mount.reload = this.reload;
                mount.recoil = 1.0F;
                if (this.shootSound != Sounds.none && !Vars.headless) {
                    if (mount.sound == null) {
                        mount.sound = new SoundLoop(this.shootSound, 1.0F);
                    }

                    mount.sound.update(bulletX, bulletY, true);
                }

                if (this.alwaysContinuous && mount.shoot) {
                    mount.bullet.time = mount.bullet.lifetime * mount.bullet.type.optimalLifeFract * mount.warmup;
                    mount.bullet.keepAlive = true;
                }
            } else {
                mount.bullet = null;
            }
        } else {
            mount.heat = Math.max(mount.heat - Time.delta * build.efficiency() / this.cooldownTime, 0.0F);
            if (mount.sound != null) {
                mount.sound.update(bulletX, bulletY, false);
            }
        }

        if (mount.shoot && can && (itemCost == null || (build.block.hasItems && build.items.has(itemCost))) && (mount.reload <= 1.0E-4F || this.alwaysContinuous && mount.bullet == null) && (this.alwaysShooting || Angles.within(mount.rotation, mount.targetRotation, this.shootCone))) {
            this.shoot(build, mount, bulletX, bulletY, shootAngle);
            mount.reload = this.reload;
            if (itemCost != null && build.block.hasItems) {
                mount.cost += 1;
                var cost = Mathf.floor(mount.cost / bullet.ammoMultiplier);
                if (cost == 0) {
                    return;
                }
                build.items.remove(itemCost,cost);
                mount.cost -= cost * bullet.ammoMultiplier;
            }
        }
    }

    protected Teamc findTarget(Building build, float x, float y, float range, boolean air, boolean ground) {
        return Units.closestTarget(build.team, x, y, range + Math.abs(this.shootY), (u) -> u.checkTarget(air, ground), (t) -> ground);
    }

    protected boolean checkTarget(Building build, Teamc target, float x, float y, float range) {
        return Units.invalidateTarget(target, build.team, x, y, range + Math.abs(this.shootY));
    }

    protected float bulletRotation(Building build, BlockWeaponMount mount, float bulletX, float bulletY) {
        return build.rotdeg() + mount.rotation;
    }

    protected void shoot(Building build, BlockWeaponMount mount, float shootX, float shootY, float rotation) {
        shotEvent.get(build);

        if (this.shoot.firstShotDelay > 0.0F) {
            mount.charging = true;
            this.chargeSound.at(shootX, shootY, Mathf.random(this.soundPitchMin, this.soundPitchMax));
            this.bullet.chargeEffect.at(shootX, shootY, rotation, !this.bullet.keepVelocity && !this.parentizeEffects ? null : build);
        }

        this.shoot.shoot(mount.barrelCounter, (xOffset, yOffset, angle, delay, mover) -> {
            ++mount.totalShots;
            if (delay > 0.0F) {
                Time.run(delay, () -> {
                    this.bullet(build, mount, xOffset, yOffset, angle, mover);
                });
            } else {
                this.bullet(build, mount, xOffset, yOffset, angle, mover);
            }

        }, () -> {
            ++mount.barrelCounter;
        });
    }

    protected void bullet(Building build, BlockWeaponMount mount, float xOffset, float yOffset, float angleOffset, Mover mover) {
        if (build.isAdded()) {
            mount.charging = false;
            float xSpread = Mathf.range(this.xRand);
            float weaponRotation = build.rotdeg() - 90.0F + mount.rotation;
            float mountX = build.x + Angles.trnsx(build.rotdeg() - 90.0F, this.x, this.y);
            float mountY = build.y + Angles.trnsy(build.rotdeg() - 90.0F, this.x, this.y);
            float bulletX = mountX + Angles.trnsx(weaponRotation, this.shootX + xOffset + xSpread, this.shootY + yOffset);
            float bulletY = mountY + Angles.trnsy(weaponRotation, this.shootX + xOffset + xSpread, this.shootY + yOffset);
            float shootAngle = this.bulletRotation(build, mount, bulletX, bulletY) + angleOffset;
            float lifeScl = this.bullet.scaleLife ? Mathf.clamp(Mathf.dst(bulletX, bulletY, mount.aimX, mount.aimY) / this.bullet.range) : 1.0F;
            float angle = angleOffset + shootAngle + Mathf.range(this.inaccuracy + this.bullet.inaccuracy);

            /*
            UnitController var18 = unit.controller();
            Unit var10000;
            if (var18 instanceof MissileAI) {
                MissileAI ai = (MissileAI)var18;
                var10000 = ai.shooter;
            } else {
                var10000 = unit;
            }
             */

            // Entityc shooter = var10000;
            mount.bullet = this.bullet.create(build, build, build.team, bulletX, bulletY, angle, -1.0F, 1.0F - this.velocityRnd + Mathf.random(this.velocityRnd), lifeScl, (Object)null, mover, mount.aimX, mount.aimY);
            this.handleBullet(build, mount, mount.bullet);
            if (!this.continuous) {
                this.shootSound.at(bulletX, bulletY, Mathf.random(this.soundPitchMin, this.soundPitchMax));
            }

            this.ejectEffect.at(mountX, mountY, angle * (float)Mathf.sign(this.x));
            this.bullet.shootEffect.at(bulletX, bulletY, angle, this.bullet.hitColor, build);
            this.bullet.smokeEffect.at(bulletX, bulletY, angle, this.bullet.hitColor, build);
            Effect.shake(this.shake, this.shake, bulletX, bulletY);
            mount.recoil = 1.0F;
            if (this.recoils > 0) {
                mount.recoils[mount.barrelCounter % this.recoils] = 1.0F;
            }

            mount.heat = 1.0F;
        }
    }

    protected void handleBullet(Building build, BlockWeaponMount mount, Bullet bullet) {
    }

    public void init() {
        if (this.alwaysContinuous) {
            this.continuous = true;
        }
    }

    public void load() {

        this.region = Core.atlas.find(this.name);
        this.heatRegion = Core.atlas.find(this.name + "-heat");
        this.cellRegion = Core.atlas.find(this.name + "-cell");
        this.outlineRegion = Core.atlas.find(this.name + "-outline");
        Iterator var1 = this.parts.iterator();

        while(var1.hasNext()) {
            DrawPart part = (DrawPart)var1.next();
            part.turretShading = false;
            part.load(this.name);
        }
    }

    public String toString() {
        return this.name != null && !this.name.isEmpty() ? "Weapon: " + this.name : "Weapon";
    }

}
