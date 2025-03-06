package viscott.gen;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.input.Controller;
import arc.math.Angles;
import arc.math.Mat;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.entities.units.AIController;
import mindustry.gen.EffectState;
import mindustry.gen.Player;
import mindustry.gen.Posc;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Layer;
import mindustry.input.DesktopInput;
import mindustry.input.InputHandler;
import mindustry.type.UnitType;
import viscott.content.PvStats;
import viscott.content.PvStatusEffects;
import viscott.content.PvUnitMapper;
import viscott.input.PvBinds;
import viscott.types.PvUnitType;
import viscott.types.weathers.NewSnowWeather;

public class DodgeUnit extends UnitEntity {
    boolean strafing = false;
    float strafeBursts = 1;
    float strafeCooldown;
    byte keyDir = 0;
    float lastKeyPress = 0;

    Color trailCol;
    float trailAlpha;
    float a2_directBoost = 0;
    boolean a2_directBoosting = false;

    float a3_strafeSlash_Cooldown = 0;
    float a3_strafeSlash_Timer = 0;
    int a3_strafeSlash_Stage = 0;
    Seq<NewSnowWeather.Vec2Rot> strafeTrail;
    PvUnitType pvType;

    public DodgeUnit() {
        super();
    }

    @Override
    public void setType(UnitType type) {
        super.setType(type);

        pvType = ((PvUnitType)type);
        strafeTrail = new Seq<>(pvType.strafeTrail);
        strafeCooldown = 0;
        trailCol = ((PvUnitType) type).strafeTint.cpy();
        trailAlpha = trailCol.a;
        strafeBursts = pvType.strafeBursts;
    }

    @Override
    public void rawDamage(float amount) {
        if (strafing)
            super.rawDamage(pvType.strafeDamageMultiplier*amount);
        else
            super.rawDamage(amount);
    }

    @Override
    public int classId() {
        return PvUnitMapper.DodgeUnit;
    }

    @Override
    public void draw() {
        strafeDraw();
        super.draw();
    }

    @Override
    public void update() {
        if (a3_strafeSlash_Stage != 0) {
            strafeSlashUpdate();
            super.update();
            return;
        }
        strafeUpdate();
        directBoostUpdate();
        if (a2_directBoosting)
            Log.info("boosting");
        Vec2 additive = null;
        if (a2_directBoosting) {
            if (a2_directBoost == Float.POSITIVE_INFINITY)
                additive = vel.cpy().mulAdd(vel, pvType.directBoost - 1);
            else
                additive = vel.cpy().mulAdd(vel, Mathf.lerp(-1f,pvType.directBoost - 1,a2_directBoost/pvType.directDecay));
            vel.add(additive);
        }
        super.update();
        if (a2_directBoosting)
            vel.sub(additive);
    }

    public void strafeSlashUpdate() {
        a3_strafeSlash_Timer -= Time.delta;

        if (a3_strafeSlash_Timer < 0) {
            switch(a3_strafeSlash_Stage) {
                case 1:
                    a3_strafeSlash_Stage = 2;
                    a3_strafeSlash_Timer = pvType.strafeSlashBullet.chargeEffect.lifetime;
                    pvType.strafeSlashBullet.chargeEffect.at(this,true);
                    rotation += 180;
                    break;
                case 2:
                    a3_strafeSlash_Stage = 3;
                    if (pvType.strafeSlashBullet != null)
                    pvType.strafeSlashBullet.create(this,x,y,rotation);
                    a3_strafeSlash_Timer = pvType.strafeSlashDelay;
                    break;
                case 3:
                    a3_strafeSlash_Stage = 0;
            }
        }
    }
    public void directBoostUpdate() {
        if (a2_directBoosting && a2_directBoost != Float.POSITIVE_INFINITY) {
            a2_directBoost -= Time.delta;
            if (a2_directBoost <= 0) {
                a2_directBoost = 0;
                a2_directBoosting = false;
            }
        }
    }

    @Override
    public void lookAt(float angle) {
        if (pvType.strafeLevel < 2) {
            super.lookAt(angle);
            return;
        }

        if (Math.abs(angle - rotation) <= 2 && vel.len() >= type.speed / 2  && Math.abs(rotation - vel.angle()) <= 2) {
            // needs to have little to no rotation edit and also be aligned with velocity.
            if (!a2_directBoosting) {
                a2_directBoost += Time.delta;
                if (a2_directBoost >= 60*1) {
                    a2_directBoost = Float.POSITIVE_INFINITY;
                    a2_directBoosting = true;
                }
            }
        } else {
            if (a2_directBoosting) {
                if (a2_directBoost == Float.POSITIVE_INFINITY)
                    a2_directBoost = pvType.directDecay;
            } else {
                a2_directBoost = 0;
            }
        }
        super.lookAt(angle);
    }

    public void strafeDraw() {
        if (pvType.strafeTrail != 0) {
            if (!Vars.state.isPaused()) {
                if (strafing == false && a2_directBoost != Float.POSITIVE_INFINITY && a3_strafeSlash_Stage != 1) {
                    if (strafeTrail.size == 0)
                        return;
                    strafeTrail.remove(0);
                    if (strafeTrail.size == 0)
                        return;
                    strafeTrail.remove(0);
                    strafeTrail.add(new NewSnowWeather.Vec2Rot(x, y, rotation));
                } else {
                    if (strafeTrail.size == ((PvUnitType) type).strafeTrail) {
                        strafeTrail.remove(0);
                    }
                    strafeTrail.add(new NewSnowWeather.Vec2Rot(x, y, rotation));
                }
            }

            Draw.z(type.lowAltitude ? Layer.flyingUnitLow+0.0001f : Layer.flyingUnit+0.0001f);
            for (int i = 0; i < strafeTrail.size;i++) {
                NewSnowWeather.Vec2Rot trailElem = strafeTrail.get(i);
                Draw.color(trailCol.a(trailAlpha*(1-Mathf.pow(1-(((float)i)/strafeTrail.size),3))));
                Draw.rect(type.fullIcon, trailElem.x, trailElem.y, trailElem.rotation - 90);
            }
        }
    }

    public void strafeUpdate() {
        strafeCooldown -= Time.delta;
        a3_strafeSlash_Cooldown -= Time.delta;
        if (strafing) {
            if (strafeCooldown <= 0) { // strafe expired.
                strafing = false;
                drag = type.drag;
                strafeCooldown = pvType.strafeCooldown;
            }

            return; // don't run stuff past this if strafing.
        }

        if (!isLocal()) return; // player is controlling it.
        if (strafeCooldown <= 0) {
            if (strafeBursts < pvType.strafeBursts) {
                strafeBursts++;
                strafeCooldown = pvType.strafeCooldown;
            }
        }
        if (strafeBursts == 0) return;


        if (keyDir != 0)
            lastKeyPress += Math.min(10,Time.delta);

        if (pvType.strafeLevel >= 3 && a3_strafeSlash_Stage == 0 && a3_strafeSlash_Cooldown <= 0) {
            if (Core.input.keyDown(PvBinds.special_ability)) {
                strafeSlashInit();
            }
        }

        if (Core.settings.getBool("pv-strafe-double-tap")) {
            if (Core.input.keyTap(PvBinds.strafe_left)) {
                if (keyDir == 1 && lastKeyPress <= 30) {
                    var dir = rotation + 90;
                    strafeDir(dir);
                    keyDir = 0;
                } else {
                    keyDir = 1;
                    lastKeyPress = 0;
                }
            }
            if (Core.input.keyTap(PvBinds.strafe_right)) {
                if (keyDir == 2 && lastKeyPress <= 30) {
                    var dir = rotation - 90;
                    strafeDir(dir);
                    keyDir = 0;
                } else {
                    keyDir = 2;
                    lastKeyPress = 0;
                }
            }
        } else { // normal no double tap.
            if (Core.input.keyTap(PvBinds.strafe_left)) {
                var dir = rotation + 90;
                strafeDir(dir);
            }
            if (Core.input.keyTap(PvBinds.strafe_right)) {
                var dir = rotation - 90;
                strafeDir(dir);
            }
        }
    }

    public void strafeSlashInit() {
        a3_strafeSlash_Timer = pvType.strafeSlashTime;
        a3_strafeSlash_Stage = 1;
        a3_strafeSlash_Cooldown = pvType.strafeSlashCooldown;
        apply(PvStatusEffects.prepFire,pvType.strafeSlashTime + pvType.strafeSlashBullet.chargeEffect.lifetime + pvType.strafeSlashDelay);

        vel.set(Mathf.cosDeg(rotation)*pvType.strafeSlashStrength,Mathf.sinDeg(rotation)*pvType.strafeSlashStrength);

    }

    public void movePref(Vec2 movement) {
        if (isLocal() &&
                (strafing || a3_strafeSlash_Stage != 0)
        ) return; // no movePref updates while strafing or a3.
        if (isLocal() && Core.settings.getBool("pv-strafe-units-move-rotation")) {
            var rot = rotation - 90;
            movement.set(movement.x*Mathf.cosDeg(rot) - movement.y*Mathf.sinDeg(rot),movement.y*Mathf.cosDeg(rot) + movement.x*Mathf.sinDeg(rot));
            super.movePref(movement);
        } else {
            super.movePref(movement);
        }
    }

    public void strafeDir(float dir) {
        var norms = new Vec2(Mathf.cosDeg(dir)*pvType.strafeStrength,Mathf.sinDeg(dir)*pvType.strafeStrength);
        strafeCooldown = pvType.strafeTime;
        strafing = true;
        drag = pvType.strafeDrag;
        vel.x += norms.x;
        vel.y += norms.y;
        dragMultiplier = 0;
        strafeBursts--;

    }

    @Override
    public void read(Reads read) {
        super.read(read);
    }

    @Override
    public void write(Writes writes) {
        super.write(writes);
    }
}
