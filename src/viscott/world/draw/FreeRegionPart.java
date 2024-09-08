package viscott.world.draw;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Tmp;
import mindustry.entities.part.DrawPart;
import mindustry.entities.part.RegionPart;
import mindustry.graphics.Drawf;

import javax.swing.plaf.synth.Region;
import java.util.Iterator;

public class FreeRegionPart extends RegionPart {
    /// Idea is to remove restrictions such as clamps.

    public FreeRegionPart() {
        super();
    }
    public FreeRegionPart(String prefix) {
        super(prefix);
    }

    @Override
    public void draw(DrawPart.PartParams params) {
        float z = Draw.z();
        if (this.layer > 0.0F) {
            Draw.z(this.layer);
        }

        if (this.under && this.turretShading) {
            Draw.z(z - 1.0E-4F);
        }

        Draw.z(Draw.z() + this.layerOffset);
        float prevZ = Draw.z();
        float prog = this.progress.get(params);
        float sclProg = this.growProgress.get(params);
        float mx = this.moveX * prog;
        float my = this.moveY * prog;
        float mr = this.moveRot * prog + this.rotation;
        float gx = this.growX * sclProg;
        float gy = this.growY * sclProg;
        int len;
        float preYscl;
        if (this.moves.size > 0) {
            for(len = 0; len < this.moves.size; ++len) {
                DrawPart.PartMove move = (DrawPart.PartMove)this.moves.get(len);
                preYscl = move.progress.get(params);
                mx += move.x * preYscl;
                my += move.y * preYscl;
                mr += move.rot * preYscl;
                gx += move.gx * preYscl;
                gy += move.gy * preYscl;
            }
        }

        len = this.mirror && params.sideOverride == -1 ? 2 : 1;
        float preXscl = Draw.xscl;
        preYscl = Draw.yscl;
        Draw.xscl *= this.xScl + gx;
        Draw.yscl *= this.yScl + gy;

        int s;
        int i;
        for(s = 0; s < len; ++s) {
            i = params.sideOverride == -1 ? s : params.sideOverride;
            TextureRegion region = this.drawRegion ? this.regions[Math.min(i, this.regions.length - 1)] : null;
            float sign = (float)((i == 0 ? 1 : -1) * params.sideMultiplier);
            Tmp.v1.set((this.x + mx) * sign, this.y + my).rotateRadExact((params.rotation - 90.0F) * 0.017453292F);
            float rx = params.x + Tmp.v1.x;
            float ry = params.y + Tmp.v1.y;
            float rot = mr * sign + params.rotation - 90.0F;
            Draw.xscl *= sign;
            if (this.outline && this.drawRegion) {
                Draw.z(prevZ + this.outlineLayerOffset);
                Draw.rect(this.outlines[Math.min(i, this.regions.length - 1)], rx, ry, rot);
                Draw.z(prevZ);
            }

            if (this.drawRegion && region.found()) {
                if (this.color != null && this.colorTo != null) {
                    Draw.color(this.color, this.colorTo, prog);
                } else if (this.color != null) {
                    Draw.color(this.color);
                }

                if (this.mixColor != null && this.mixColorTo != null) {
                    Draw.mixcol(this.mixColor, this.mixColorTo, prog);
                } else if (this.mixColor != null) {
                    Draw.mixcol(this.mixColor, this.mixColor.a);
                }

                Draw.blend(this.blending);
                Draw.rect(region, rx, ry, rot);
                Draw.blend();
                if (this.color != null) {
                    Draw.color();
                }
            }

            if (this.heat.found()) {
                float hprog = this.heatProgress.get(params);
                this.heatColor.write(Tmp.c1).a(hprog * this.heatColor.a);
                Drawf.additive(this.heat, Tmp.c1, rx, ry, rot, this.turretShading ? this.turretHeatLayer : Draw.z() + this.heatLayerOffset);
                if (this.heatLight) {
                    Drawf.light(rx, ry, this.heat, rot, Tmp.c1, this.heatLightOpacity * hprog);
                }
            }

            Draw.xscl *= sign;
        }

        Draw.color();
        Draw.mixcol();
        Draw.z(z);
        if (this.children.size > 0) {
            for(s = 0; s < len; ++s) {
                i = params.sideOverride == -1 ? s : params.sideOverride;
                float sign = (float)((i == 1 ? -1 : 1) * params.sideMultiplier);
                Tmp.v1.set((this.x + mx) * sign, this.y + my).rotateRadExact((params.rotation - 90.0F) * 0.017453292F);
                this.childParam.set(params.warmup, params.reload, params.smoothReload, params.heat, params.recoil, params.charge, params.x + Tmp.v1.x, params.y + Tmp.v1.y, (float)i * sign + mr * sign + params.rotation);
                this.childParam.sideMultiplier = params.sideMultiplier;
                this.childParam.life = params.life;
                this.childParam.sideOverride = i;
                Iterator var24 = this.children.iterator();

                while(var24.hasNext()) {
                    DrawPart child = (DrawPart)var24.next();
                    child.draw(this.childParam);
                }
            }
        }

        Draw.scl(preXscl, preYscl);
    }
}
