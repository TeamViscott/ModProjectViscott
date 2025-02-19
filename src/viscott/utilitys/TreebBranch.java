package viscott.utilitys;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.game.Teams.*;
import mindustry.gen.*;
import viscott.content.PvStatusEffects;
import viscott.types.TreebUnitType;

/** @author EyeOfDarkness */
public class TreebBranch {
    FloatSeq points = new FloatSeq(), velocities = new FloatSeq();

    float x, y,Length, rotation;
    float targetX, targetY;

    float attackX, attackY;

    boolean shoot;
    float progress;
    int randSeed;
    static Vec2 v = new Vec2();
    static Rect b = new Rect();
    static Seq<Building> tseq = new Seq<>();

    public void update(Unit unit){
        TreebUnitType type = (TreebUnitType)unit.type;
        Length = type.bLength;
        float dx = unit.x - x, dy = unit.y - y;
        int size = points.size;
        int size2 = size / 2;
        float[] items = points.items;

        for(int i = 0; i < size; i += 2){
            int s = i / 2;
            float fout = Mathf.clamp(1f - s / (size2 - 1f));
            items[i] += dx * fout;
            items[i + 1] += dy * fout;
        }
        x += dx;
        y += dy;

        if(shoot){
            progress = Mathf.approachDelta(progress, 1f, 1f / 60f);

            updateAttack(unit);

            if(progress >= 1f) shoot = false;
        }else if(progress > 0){
            progress = Mathf.approachDelta(progress, 0f, 1f / 80f);
            if(progress <= 0f){
                points.clear();
                velocities.clear();
            }
        }
    }
    void updateAttack(Unit unit){
        float prog = getProgress();
        int size = points.size / 2;
        float[] items = points.items;

        int idx1 = Mathf.clamp((int)(prog * (size - 1)), 0, size - 1) * 2;

        int idxS = idx1 + 2;

        Rect r = b.set(x, y, 0f, 0f);

        for(int i = 0; i < idxS; i += 2){
            float lx = items[i], ly = items[i + 1];
            r.merge(lx, ly);
        }
        r.grow(6f);

        TreebUnitType type = (TreebUnitType)unit.type;
        Groups.unit.intersect(r.x, r.y, r.width, r.height, u -> {
            if(u.team != unit.team){
                float rad = u.hitSize / 2f + 3f;
                float ax = x, ay = y;
                boolean hit = false;

                for(int i = 0; i < idxS; i += 2){
                    float lx = items[i], ly = items[i + 1];
                    float dst = Mathf.dst(u.x, u.y, lx, ly);
                    if(dst < rad){
                        if(!hit){
                            float ang = Angles.angle(ax, ay, lx, ly);
                            Fx.hitBulletBig.at(lx, ly, ang);
                        }
                        hit = true;
                    }
                    ax = lx;
                    ay = ly;
                }

                if(hit){
                    u.damagePierce(type.bDamage * Time.delta);
                    for (Team t : Team.all)
                        if (t != unit.team && t.active())
                            Units.nearby(t, unit.x, unit.y, 2, other -> {
                                other.apply(PvStatusEffects.splintered, 60*8);
                            });
                }
            }
        });
        for(TeamData data : Vars.state.teams.active){
            if(data.team != unit.team && data.buildingTree != null){
                tseq.clear();
                data.buildingTree.intersect(r, tseq);
                for(Building b : tseq){
                    float rad = b.hitSize() / 2f + 3f;
                    float ax = x, ay = y;
                    boolean hit = false;

                    for(int i = 0; i < idxS; i += 2){
                        float scl = (i / (idxS - 2f)) * 0.75f + 0.25f;
                        float lx = items[i], ly = items[i + 1];
                        float dst = Mathf.dst(b.x, b.y, lx, ly);
                        if(dst < rad){
                            if(!hit){
                                float ang = Angles.angle(ax, ay, lx, ly);
                                Fx.hitBulletBig.at(lx, ly, ang);
                            }
                            hit = true;
                        }
                        ax = lx;
                        ay = ly;
                    }

                    if(hit){
                        b.damagePierce(type.bDamage * Time.delta);
                        for (Team t : Team.all)
                            if (t != unit.team && t.active())
                                Units.nearby(t, unit.x, unit.y, 2, other -> {
                                    other.apply(PvStatusEffects.splintered, 60*8);
                                });
                    }
                }
            }
        }
    }

    public void updateTargetPosition(float tx, float ty){
        targetX = tx;
        targetY = ty;
        if(velocities.isEmpty() || !shoot) return;

        float prog = getProgress();
        int size = velocities.size / 2;

        int idx1 = Mathf.clamp((int)(prog * (size - 1)), 0, size - 1);
        float vr = velocities.items[idx1 * 2], lr = velocities.items[idx1 * 2 + 1];
        float lx = points.items[idx1 * 2], ly = points.items[idx1 * 2 + 1];

        float[] vel = velocities.items;
        float[] pos = points.items;

        for(int i = idx1 + 1; i < size; i++){
            int i2 = i * 2;

            v.trns(lr, Length).add(lx, ly);

            vr -= Mathf.clamp(TreebUtils.angleDistSigned(lr, Angles.angle(lx, ly, targetX, targetY)) * 0.02f, -1f, 1f);
            lr += vr;
            vr *= 1f - 0.05f;
            vel[i2] = vr;
            vel[i2 + 1] = lr;

            lx = v.x;
            ly = v.y;
            pos[i2] = lx;
            pos[i2 + 1] = ly;
        }
    }

    public void set(Unit unit, float rotation){
        x = unit.x;
        y = unit.y;
        this.rotation = rotation;

        randSeed = Mathf.rand.nextInt();
    }

    public float getProgress(){
        Interp inter = shoot ? MultiInterp.fastfastslow : Interp.pow3;
        return inter.apply(progress);
    }

    public boolean canShoot(){
        return !(shoot || progress > 0);
    }

    public void shoot(float targetX, float targetY){
        if(shoot || progress > 0) return;

        shoot = true;

        points.clear();
        velocities.clear();

        float vr = 0f;
        float lx = x;
        float ly = y;
        float lr = rotation;

        attackX = x;
        attackY = y;

        this.targetX = targetX;
        this.targetY = targetY;

        points.add(lx, ly);
        velocities.add(0, lr);

        for(int i = 0; i < 120; i++){
            v.trns(lr, Length).add(lx, ly);

            vr -= Mathf.clamp(TreebUtils.angleDistSigned(lr, Angles.angle(lx, ly, targetX, targetY)) * 0.02f, -1f, 1f);
            lr += vr;
            vr *= 1f - 0.05f;
            velocities.add(vr, lr);

            lx = v.x;
            ly = v.y;
            points.add(lx, ly);
        }
    }
    public void draw(Unit unit){
        TreebUnitType type = (TreebUnitType)unit.type;
        if(points.isEmpty()) return;
        int size = points.size;
        int size2 = size / 2;
        float fout = 1f - getProgress();
        float[] items = points.items;
        float segments = size2 * type.bLength;

        TextureRegion region = type.branchEndRegion;
        Rand r = TreebUtils.rand;
        r.setSeed(randSeed);

        float c = (region.width * Draw.scl) / segments;
        float lc = 0f;
        while(lc < 1f){
            float c1 = Mathf.clamp(lc - fout);
            float c2 = Mathf.clamp((lc + c) - fout);

            int ridx = r.random(0, 2);

            if(c2 > c1){
                TextureRegion tex = (lc + c) < 1 ? type.branchRegions[ridx] : type.branchEndRegion;

                float wfin = 1f - (lc) * 0.5f;
                int idx1 = Mathf.clamp((int)(c1 * (size2 - 1)), 0, size2 - 1) * 2;
                int idx2 = Mathf.clamp((int)(c1 * (size2 - 1) + 1), 0, size2 - 1) * 2;
                int idx3 = Mathf.clamp((int)(c2 * (size2 - 1)), 0, size2 - 1) * 2;
                int idx4 = Mathf.clamp((int)(c2 * (size2 - 1) + 1), 0, size2 - 1) * 2;

                float n1 = c1 * (size2 - 1) - (int)(c1 * (size2 - 1));
                float n2 = c2 * (size2 - 1) - (int)(c2 * (size2 - 1));
                float x1 = Mathf.lerp(items[idx1], items[idx2], n1), y1 = Mathf.lerp(items[idx1 + 1], items[idx2 + 1], n1);
                float x2 = Mathf.lerp(items[idx3], items[idx4], n2), y2 = Mathf.lerp(items[idx3 + 1], items[idx4 + 1], n2);

                v.set(x1, y1).sub(x2, y2).scl(1.2f).add(x2, y2);

                Lines.stroke(tex.height * Draw.scl * wfin);
                Lines.line(tex, v.x, v.y, x2, y2, false);
            }

            lc += c;
        }
    }
}
