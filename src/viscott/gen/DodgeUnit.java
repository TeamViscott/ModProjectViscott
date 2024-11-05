package viscott.gen;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.input.Controller;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.gen.Player;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Layer;
import mindustry.input.DesktopInput;
import mindustry.input.InputHandler;
import mindustry.type.UnitType;
import viscott.content.PvUnitMapper;
import viscott.input.PvBinds;
import viscott.types.PvUnitType;
import viscott.types.weathers.NewSnowWeather;

public class DodgeUnit extends UnitEntity {
    boolean strafing = false;
    float strafeCooldown;
    byte keyDir = 0;
    float lastKeyPress = 0;

    Color trailCol;
    float trailAlpha;
    Seq<NewSnowWeather.Vec2Rot> strafeTrail;
    PvUnitType pvType;

    @Override
    public void setType(UnitType type) {
        super.setType(type);

        pvType = ((PvUnitType)type);
        strafeTrail = new Seq<>(pvType.strafeTrail);
        strafeCooldown = 0;
        trailCol = ((PvUnitType) type).strafeTint.cpy();
        trailAlpha = trailCol.a;
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
        strafeUpdate();
        super.update();
        
    }

    public void strafeDraw() {
        if (((PvUnitType) type).strafeTrail != 0) {
            if (!Vars.state.isPaused()) {
                if (strafing == false) {
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
        if (strafing) {
            if (strafeCooldown <= 0) { // strafe expired.
                strafing = false;
                drag = type.drag;
                strafeCooldown = ((PvUnitType)type).strafeCooldown;
            }

            return; // don't run stuff past this if strafing.
        }

        if (!isLocal()) return; // player is controlling it.
        if (strafeCooldown > 0) return;

        if (keyDir != 0)
            lastKeyPress += Math.min(10,Time.delta);

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

    public void movePref(Vec2 movement) {
        if (isLocal() && strafing) return; // no movePref updates while strafing.
        if (isLocal() && Core.settings.getBool("pv-strafe-units-move-rotation")) {
            var rot = rotation - 90;
            movement.set(movement.x*Mathf.cosDeg(rot) - movement.y*Mathf.sinDeg(rot),movement.y*Mathf.cosDeg(rot) + movement.x*Mathf.sinDeg(rot));
            super.movePref(movement);
        } else {
            super.movePref(movement);
        }
    }

    public void strafeDir(float dir) {
        var pvType = ((PvUnitType)type);
        var norms = new Vec2(Mathf.cosDeg(dir)*pvType.strafeStrength,Mathf.sinDeg(dir)*pvType.strafeStrength);
        strafeCooldown = pvType.strafeTime;
        strafing = true;
        drag = pvType.strafeDrag;
        vel.x += norms.x;
        vel.y += norms.y;
        dragMultiplier = 0;
    }

}
