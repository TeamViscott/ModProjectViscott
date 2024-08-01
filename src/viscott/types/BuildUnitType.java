package viscott.types;

import arc.Core;
import arc.graphics.Pixmap;
import arc.graphics.Pixmaps;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureAtlas;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import mindustry.content.UnitTypes;
import mindustry.entities.part.DrawPart;
import mindustry.gen.Building;
import mindustry.gen.Payloadc;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.MultiPacker;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.payloads.BuildPayload;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.draw.DrawTurret;

import static arc.input.KeyCode.rightBracket;

public class BuildUnitType extends PvUnitType{
        public BuildUnitType(String name)
        {
            super(name);
        }
        float powTick = 0;
        public int BaseItemDelpeltionRate = 20;


        @Override
        public <T extends Unit & Payloadc> void drawPayload(T unit){
            if(unit.hasPayload()){
                Payload pay = unit.payloads().first();
                float layer = unit.isFlying() ? Layer.flyingUnitLow : Layer.groundUnit;
                Draw.z(layer + 5);
                if (pay instanceof BuildPayload buildPay && buildPay.build.block() instanceof Turret)
                {
                    Turret.TurretBuild build = (Turret.TurretBuild)buildPay.build;
                    float rot = unit.rotation - build.payloadRotation;
                    pay.set(unit.x, unit.y, unit.rotation);
                    build.rotation += rot;
                    drawBuild(build,unit,layer);
                }
                else {
                    Draw.rect(pay.content().fullIcon, unit.x, unit.y, unit.rotation - 90);
                }
                Draw.z(layer);
            }
        }

        @Override
        public void update(Unit unit){
            if (unit instanceof Payloadc p && p.hasPayload()) {
                Payload pay = p.payloads().first();
                if (pay instanceof BuildPayload buildPay && buildPay.build.block() instanceof Turret) {
                    Turret.TurretBuild build = (Turret.TurretBuild)buildPay.build;
                    build.x(unit.x);
                    build.y(unit.y);
                    if(build.block.consumesPower && build.power != null) {
                        if (unit.stack.amount != 0) {
                            float TakeTime = BaseItemDelpeltionRate;
                            float ItemDepleteRate = 0;
                            if (unit.stack.item.charge != 0 || unit.stack.item.flammability != 0) {
                                if (unit.stack.item.charge > unit.stack.item.flammability) {
                                    ItemDepleteRate = unit.stack.item.charge;
                                } else if (unit.stack.item.flammability > unit.stack.item.charge) {
                                    ItemDepleteRate = unit.stack.item.flammability;
                                }
                                if (powTick == 1) {
                                    unit.stack.amount -= 1;
                                }
                            }
                            TakeTime *= ItemDepleteRate;
                            if (powTick >= TakeTime) {
                                powTick = 0;
                            }
                        }
                    }


                    if (build.block instanceof ItemTurret) {
                        ItemTurret.ItemTurretBuild Itembuild = (ItemTurret.ItemTurretBuild) buildPay.build;
                        if (Itembuild.acceptItem(null, unit.item()) && unit.stack.amount != 0) {
                            Itembuild.handleItem(null, unit.item());
                            unit.stack.amount -= 1;
                        }
                        tickEnd(Itembuild);
                    }else{
                        tickEnd(build);
                    }
                }
            }
        }
        public void tickEnd(Building build){
            if (build.block.consumesPower && build.power != null) {
                build.power.status = 1;
                powTick += 1;
                if(Core.input.keyTap(rightBracket)){
                    build.power.status = 0;
                }
            }
            build.update();
            build.warmup();
        }

        public void drawBuild(Building build,Unit unit,float layer){
            Turret turret = (Turret)build.block;
            Turret.TurretBuild tb = (Turret.TurretBuild)build;
            DrawTurret drawer = (DrawTurret)turret.drawer;
            Draw.rect(drawer.base, build.x, build.y,unit.rotation);
            Draw.color();

            Draw.z(layer + 4.5f);

            Drawf.shadow(drawer.preview, build.x + tb.recoilOffset.x - turret.elevation, build.y + tb.recoilOffset.y - turret.elevation, tb.drawrot());

            Draw.z(layer + 5);
            drawer.drawTurret(turret, tb);
            drawer.drawHeat(turret, tb);

            if(drawer.parts.size > 0){
                if(drawer.outline.found()){
                    //draw outline under everything when parts are involved
                    Draw.z(layer + 4.99f);
                    Draw.rect(drawer.outline, build.x + tb.recoilOffset.x, build.y + tb.recoilOffset.y, tb.drawrot());
                    Draw.z(layer + 5);
                }

                float progress = tb.progress();

                //TODO no smooth reload
                var params = DrawPart.params.set(build.warmup(), 1f - progress, 1f - progress, tb.heat, tb.curRecoil, tb.charge, tb.x + tb.recoilOffset.x, tb.y + tb.recoilOffset.y, tb.rotation);
                build.warmup();

                for(var part : drawer.parts){
                    params.setRecoil(part.recoilIndex >= 0 && tb.curRecoils != null ? tb.curRecoils[part.recoilIndex] : tb.curRecoil);
                    part.draw(params);
                }
            }
        }
    }
