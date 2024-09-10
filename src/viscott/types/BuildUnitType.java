package viscott.types;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import arc.util.Scaling;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.noise.Simplex;
import viscott.types.*;
//import axthrix.world.types.block.defense.PayloadTurretType;
//import axthrix.world.util.DrawIPayloadTurret;
import com.sun.nio.sctp.MessageInfo;
import mindustry.Vars;
import mindustry.ai.types.LogicAI;
import mindustry.content.Blocks;
import mindustry.content.Liquids;
import mindustry.content.UnitTypes;
import mindustry.core.UI;
import mindustry.entities.Puddles;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.entities.part.DrawPart;
import mindustry.gen.Building;
import mindustry.gen.Iconc;
import mindustry.gen.Payloadc;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.defense.ForceProjector;
import mindustry.world.blocks.defense.MendProjector;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.payloads.BuildPayload;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.blocks.units.RepairTurret;
import mindustry.world.draw.DrawTurret;
import arc.math.Interp;
import arc.math.Rand;
import viscott.content.*;


import java.util.HashMap;
import java.util.Objects;

import static arc.input.KeyCode.mouseLeft;
import static mindustry.Vars.*;
import static mindustry.input.Binding.dropCargo;

public class BuildUnitType extends PvUnitType {
    public BuildUnitType(String name)
    {
        super(name);
    }
    //liquid
    public HashMap<Unit, Liquid> liquidType = new HashMap<>();
    public HashMap<Unit, Integer> liquidAmount = new HashMap<>();
    public Integer liquidCap = 160;
    public Integer liquidPickupRange = 40;
    //power
    public HashMap<Unit, Float> powTick = new HashMap<>();
    public HashMap<Unit, Boolean> Validation = new HashMap<>();
    public HashMap<Unit, Float> DelpeltionRate = new HashMap<>();
    public float BaseItemDelpeltionRate = 20;

    // payload
    static final Rand rand = new Rand();

    // textures

    public TextureRegion liquidRegion;
    public TextureRegion laser;
    public TextureRegion laserEnd;
    public TextureRegion laserTop;
    public TextureRegion laserTopEnd;

    @Override
    public void display(Unit unit, Table table){
        table.table(t -> {
            t.left();
            t.add(new Image(uiIcon)).size(iconMed).scaling(Scaling.fit);
            t.labelWrap(localizedName).left().width(190f).padLeft(5);
        }).growX().left();
        table.row();

        table.table(bars -> {
            bars.defaults().growX().height(20f).pad(4);

            bars.add(new Bar("stat.health", Pal.health, unit::healthf).blink(Color.white));
            bars.row();

            bars.add(new Bar(liquidType.get(unit) != PvLiquids.nothing ? liquidType.get(unit).localizedName : "Liquid", liquidType.get(unit) != null ? liquidType.get(unit).barColor() : Color.black, () -> ((float) liquidAmount.get(unit) / (liquidCap) )));
            bars.row();

            for(Ability ability : unit.abilities){
                ability.displayBars(unit, bars);
            }

            if(payloadCapacity > 0 && unit instanceof Payloadc payload){
                bars.add(new Bar("stat.payloadcapacity", Pal.items, () -> payload.payloadUsed() / unit.type().payloadCapacity));
                bars.row();

                var count = new float[]{-1};
                bars.table().update(t -> {
                    if(count[0] != payload.payloadUsed()){
                        payload.contentInfo(t, 8 * 2, 270);
                        count[0] = payload.payloadUsed();
                    }
                }).growX().left().height(0f).pad(0f);
            }
        }).growX();

        table.row();
    }

    @Override
    public <T extends Unit & Payloadc> void drawPayload(T unit){
        Units.nearbyBuildings(unit.x,unit.y,liquidPickupRange, b -> {
            if(b instanceof LiquidRouter.LiquidRouterBuild lr) {
                if(lr.liquids.currentAmount() != 0  && lr.team() == unit.team()) {
                    if (!Objects.equals(liquidAmount.get(unit), liquidCap) && liquidType.get(unit) == lr.liquids.current() ){
                        Draw.z(116.0F);
                        Draw.color(lr.liquids.current().color);
                        float f = (Time.time / 85.0F + rand.random(1.0F)) % 1.0F;
                        Draw.alpha(1.0F - Interp.pow5In.apply(f));
                        Lines.stroke(1);
                        Lines.circle(unit.x, unit.y, 1.0F + f * 5);
                        Draw.color(lr.liquids.current().color);
                        Drawf.laser(laser, laserEnd, lr.x, lr.y, unit.x, unit.y, 1);
                        Draw.z(116.1F);
                        Draw.color(lr.liquids.current().color);
                        Drawf.laser(laserTop, laserTopEnd, lr.x, lr.y, unit.x, unit.y, 1);
                        Draw.color();
                    }
                }
            } else if (b instanceof LiquidDeposit.LiquidDepositBuild ld) {
                if (ld.liquids.currentAmount() < ld.block.liquidCapacity && ld.team() == unit.team()) {
                    if(ld.liquids.current() == liquidType.get(unit) || ld.liquids.current() == Liquids.water) {
                        if (liquidAmount.get(unit) != 0){
                            Draw.z(116.0F);
                            Draw.color(liquidType.get(unit).color);
                            float f = (Time.time / 85.0F + rand.random(1.0F)) % 1.0F;
                            Draw.alpha(1.0F - Interp.pow5In.apply(f));
                            Lines.stroke(1);
                            Lines.circle(ld.x, ld.y, 1.0F + f * 5);
                            Draw.color(liquidType.get(unit).color);
                            Drawf.laser(laser, laserEnd, unit.x, unit.y, ld.x, ld.y, 1);
                            Draw.z(116.1F);
                            Draw.color(liquidType.get(unit).color);
                            Drawf.laser(laserTop, laserTopEnd, unit.x, unit.y, ld.x, ld.y, 1);
                            Draw.color();
                        }
                    }
                }

            }
        });
        if(unit.hasPayload()){
            Payload turret = unit.payloads().first();
            float layer = unit.isFlying() ? Layer.flyingUnitLow : Layer.groundUnit;
            Draw.z(layer + 5);
            if (turret instanceof BuildPayload buildTurret && buildTurret.build.block() instanceof Turret)
            {
                Turret.TurretBuild build = (Turret.TurretBuild)buildTurret.build;
                float rot = unit.rotation - build.payloadRotation;
                turret.set(unit.x, unit.y, unit.rotation);
                build.rotation += rot;
                drawBuild(build,unit,layer);
            }
            else {
                Draw.rect(turret.content().fullIcon, unit.x, unit.y, unit.rotation - 90);
            }
            Payload module = unit.payloads().peek();
            if (module instanceof BuildPayload buildModule && !(buildModule.build.block() instanceof Turret)){
                if (buildModule.build.block instanceof MendProjector mend) {
                    MendProjector.MendBuild mendb = (MendProjector.MendBuild)buildModule.build;
                    float rot = unit.rotation - mendb.payloadRotation;
                    module.set(unit.x, unit.y, unit.rotation);
                    mendb.rotation += rot;
                    float f = 1.0F - Time.time / 100.0F % 1.0F;
                    Draw.color(mend.baseColor, mend.phaseColor, mendb.phaseHeat);
                    Draw.alpha(mendb.heat * Mathf.absin(Time.time, 7.957747F, 1.0F) * 0.5F);
                    Draw.rect(mend.topRegion, unit.x, unit.y,unit.rotation);
                    Draw.alpha(1.0F);
                    Lines.stroke((2.0F * f + 0.2F) * mendb.heat);
                    Lines.square(unit.x, unit.y, Math.min(1.0F + (1.0F - f) * (float)mend.size * 8.0F / 2.0F, (float)(mend.size * 8) / 2.0F), unit.rotation);
                    Draw.reset();
                }
                if (buildModule.build.block instanceof ForceProjector force) {
                    ForceProjector.ForceBuild forceb = (ForceProjector.ForceBuild)buildModule.build;
                    float rot = unit.rotation - forceb.payloadRotation;
                    module.set(unit.x, unit.y, unit.rotation);
                    forceb.rotation += rot;
                    if (forceb.buildup > 0.0F) {
                        Draw.alpha(forceb.buildup / force.shieldHealth * 0.75F);
                        Draw.z(31.0F);
                        Draw.blend(Blending.additive);
                        Draw.rect(force.topRegion, unit.x, unit.y, unit.rotation);
                        Draw.blend();
                        Draw.z(30.0F);
                        Draw.reset();
                    }

                    forceb.drawShield();
                }
                if (buildModule.build.block instanceof RepairTurret repair) {
                    RepairTurret.RepairPointBuild repairb = (RepairTurret.RepairPointBuild)buildModule.build;
                    float rot = unit.rotation - repairb.payloadRotation;
                    module.set(unit.x, unit.y, unit.rotation);
                    repairb.rotation += rot;
                    Draw.z(50.0F);
                    Drawf.shadow(repair.region, unit.x - (float)repair.size / 2.0F, unit.y - (float)repair.size / 2.0F, unit.rotation - 90.0F);
                    Draw.rect(repair.region, unit.x, unit.y, unit.rotation);
                    RepairTurret.drawBeam(unit.x, unit.y, unit.rotation, repair.length, repairb.id, repairb.target, repairb.team, repairb.strength, repair.pulseStroke, repair.pulseRadius, repair.beamWidth, repairb.lastEnd, repairb.offset, repair.laserColor, repair.laserTopColor, repair.laser, repair.laserEnd, repair.laserTop, repair.laserTopEnd);
                }

            }
            Draw.z(layer);
        }
    }

    @Override
    public void update(Unit unit){
        if (unit.dead()){
            liquidDeath(unit);
            powTick.remove(unit);
            DelpeltionRate.remove(unit);
            Validation.remove(unit);
            liquidAmount.remove(unit);
            liquidType.remove(unit);
        }
        existCheck(unit);
        Units.nearbyBuildings(unit.x,unit.y,liquidPickupRange, b -> {
            if(b instanceof LiquidRouter.LiquidRouterBuild lr) {
                if(lr.liquids.currentAmount() != 0  && lr.team() == unit.team()) {
                    if (liquidType.get(unit) == PvLiquids.nothing && lr.liquids.current() != null){
                        liquidType.replace(unit,lr.liquids.current());
                    }
                    if (!Objects.equals(liquidAmount.get(unit), liquidCap) && liquidType.get(unit) == lr.liquids.current() ) {
                        liquidAmount.replace(unit,liquidAmount.get(unit)+1);
                        lr.liquids.set(lr.liquids.current(),lr.liquids.currentAmount() - 1);
                    }
                }
            } else if (b instanceof LiquidDeposit.LiquidDepositBuild ld) {
                if (ld.liquids.currentAmount() <= ld.block.liquidCapacity && ld.team() == unit.team()) {
                    if (liquidType.get(unit) != PvLiquids.nothing && liquidAmount.get(unit) == 0){
                        liquidType.replace(unit, PvLiquids.nothing);
                    }
                    if (liquidAmount.get(unit) > 0 && ld.liquids.currentAmount() != ld.block.liquidCapacity) {
                        if (ld.liquids.current() == liquidType.get(unit) || ld.liquids.current() == Liquids.water){
                            if(liquidType.get(unit) != Liquids.water){
                                liquidAmount.replace(unit,liquidAmount.get(unit)-1);
                                ld.liquids.set(liquidType.get(unit),ld.liquids.currentAmount() + 1);
                            }else{
                                liquidAmount.replace(unit,liquidAmount.get(unit)-1);
                                ld.liquids.set(Liquids.water,ld.liquids.currentAmount() + 1);
                            }
                        }
                    }
                }

            }
        });
        if (unit.stack.amount != 0) {
            if (unit.stack.item.charge != 0 || unit.stack.item.flammability != 0) {
                if (unit.stack.item.charge > unit.stack.item.flammability) {
                    DelpeltionRate.replace(unit, BaseItemDelpeltionRate + BaseItemDelpeltionRate * unit.stack.item.charge);
                    Validation.replace(unit, true);
                } else if (unit.stack.item.flammability > unit.stack.item.charge) {
                    DelpeltionRate.replace(unit, BaseItemDelpeltionRate + BaseItemDelpeltionRate * unit.stack.item.flammability);
                    Validation.replace(unit, true);
                }
            }

            if (powTick.get(unit) >= DelpeltionRate.get(unit)) {
                powTick.replace(unit, 0f);
                unit.stack.amount -= 1;
            }
        }
        if (unit instanceof Payloadc p && p.hasPayload()) {
            Payload turret = p.payloads().first();
            Payload module = p.payloads().peek();
            if (module instanceof BuildPayload buildModule && !(buildModule.build.block() instanceof Turret)){
                if(buildModule.build.block.hasLiquids && buildModule.build.liquids != null && liquidType.get(unit) != null) {
                    if(buildModule.build.acceptLiquid(null,liquidType.get(unit)) && liquidAmount.get(unit) != 0){
                        if (buildModule.build.liquids.currentAmount() < buildModule.build.block.liquidCapacity){
                            buildModule.build.handleLiquid(null,liquidType.get(unit),1);
                            liquidAmount.replace(unit,liquidAmount.get(unit)-1);
                        }

                    }
                }
                if (buildModule.build instanceof MendProjector.MendBuild mend) {
                    mend.x(unit.x);
                    mend.y(unit.y);
                    tickEnd(mend,unit);
                }
                if (buildModule.build instanceof ForceProjector.ForceBuild force) {
                    force.x(unit.x);
                    force.y(unit.y);
                    tickEnd(force,unit);
                }
                if (buildModule.build instanceof RepairTurret.RepairPointBuild repair) {
                    repair.x(unit.x);
                    repair.y(unit.y);
                    tickEnd(repair,unit);
                }

            }
            if (turret instanceof BuildPayload buildTurret && buildTurret.build.block() instanceof Turret) {
                Turret.TurretBuild build = (Turret.TurretBuild)buildTurret.build;
                build.x(unit.x);
                build.y(unit.y);
                if(build.block.hasLiquids && build.liquids != null && liquidType.get(unit) != null) {
                    if(build.acceptLiquid(null,liquidType.get(unit)) && liquidAmount.get(unit) != 0){
                        if (build.liquids.currentAmount() < build.block.liquidCapacity){
                            build.handleLiquid(null,liquidType.get(unit),1);
                            liquidAmount.replace(unit,liquidAmount.get(unit)-1);
                        }

                    }
                }

                if (build.block instanceof ItemTurret) {
                    ItemTurret.ItemTurretBuild Itembuild = (ItemTurret.ItemTurretBuild) buildTurret.build;
                    if (Itembuild.acceptItem(null, unit.item()) && unit.stack.amount != 0) {
                        Itembuild.handleItem(null, unit.item());
                        unit.stack.amount -= 1;
                    }
                    tickEnd(build,unit);
                    // crossed out until Eth decides if he wants payload turrets
                /*} else if (build.block instanceof PayloadTurretType) {

                    Payload ammo = p.payloads().random();

                    PayloadTurretType.PayloadTurretTypeBuild paybuild = (PayloadTurretType.PayloadTurretTypeBuild) buildTurret.build;
                    paybuild.unitCarry = unit;
                    if (paybuild.acceptPayload(null, ammo)) {
                        paybuild.handlePayload(null, ammo);
                        p.payloads().remove(ammo);
                    }
                    tickEnd(build,unit);
                    paybuild.unitCarry = null;*/

                }else{
                    tickEnd(build,unit);
                }
            }
        }
    }
    public void tickEnd(Building build,Unit unit){
        if (build.block.consumesPower && build.power != null) {
            if (Validation.get(unit)) {
                build.power.status = 1;
            } else {
                build.power.status = 0f;
            }
            if(!mobile){
                if (Core.input.keyTap(dropCargo)){
                    build.power.status = 0f;
                }
            } else if (Core.input.keyDown(mouseLeft)){
                build.power.status = 0f;
            }

            powTick.replace(unit,powTick.get(unit)+1);
        }
        if(build.block.hasLiquids && build.liquids != null) {
            if (liquidAmount.get(unit) == 0){
                liquidType.replace(unit, PvLiquids.nothing);
            }
        }
        build.update();
        build.warmup();
    }
    public void existCheck(Unit unit){
        if (!powTick.containsKey(unit)){
            powTick.put(unit, 0f);
        }
        if (!liquidType.containsKey(unit)){
            liquidType.put(unit, PvLiquids.nothing);
        }
        if (!liquidAmount.containsKey(unit)){
            liquidAmount.put(unit, Mathf.clamp(0,0,liquidCap));
        }

        if (!DelpeltionRate.containsKey(unit)){
            DelpeltionRate.put(unit, BaseItemDelpeltionRate);
        }else{
            DelpeltionRate.replace(unit, BaseItemDelpeltionRate);
        }

        if (!Validation.containsKey(unit)){
            Validation.put(unit, false);
        }else{
            Validation.replace(unit, false);
        }
    }

    public void drawBuild(Building build,Unit unit,float layer){
        Turret turret = (Turret)build.block;
        Turret.TurretBuild tb = (Turret.TurretBuild)build;
        DrawTurret drawer = (DrawTurret)turret.drawer;
       // if(drawer instanceof DrawIPayloadTurret dpt){
          //  Draw.rect(drawer.base, build.x, build.y,unit.rotation);
          //  Draw.z(layer + 2000f);
           // Draw.rect(dpt.cover, build.x + tb.recoilOffset.x, build.y + tb.recoilOffset.y, unit.rotation);
        //}else{
            Draw.rect(drawer.base, build.x, build.y,unit.rotation);
        //}
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
    public void liquidDeath(Unit unit) {
        int tx = unit.tileX();
        int ty = unit.tileY();
        int rad = Math.max((int)(unit.hitSize / 8.0F * 3), 1);
        float realNoise = unit.hitSize / 5;

        for(int x = -rad; x <= rad; ++x) {
            for(int y = -rad; y <= rad; ++y) {
                if ((float)(x * x + y * y) <= (float)(rad * rad) - Simplex.noise2d(0, 2.0, 0.5, (double)(1.0F / 5), (double)(x + tx), (double)(y + ty)) * realNoise * realNoise) {
                    float scaling = (1.0F - Mathf.dst((float)x, (float)y) / (float)rad) * 5;
                    Tile tile = Vars.world.tile(tx + x, ty + y);
                    if (tile != null && liquidAmount.get(unit) != 0) {
                        Puddles.deposit(tile, liquidType.get(unit), (liquidAmount.get(unit)/ (liquidAmount.get(unit) /  10)) * scaling);
                    }
                }
            }
        }

    }

    @Override
    public void draw(Unit unit){
        super.draw(unit);
        if (liquidAmount.get(unit) > 0.001F) {
            Draw.z( Layer.flyingUnitLow + 4.99f);
            Draw.color(liquidType.get(unit).color, (float) liquidAmount.get(unit) / liquidCap * liquidType.get(unit).color.a);
            Draw.rect(liquidRegion, unit.x, unit.y, unit.rotation - 90);
            Draw.color();
        }
        Draw.reset();
    }
    @Override
    public void load() {
        liquidRegion = Core.atlas.find(name + "-liquid", name);
        laser = Core.atlas.find(name + "-laser", "laser");
        laserEnd = Core.atlas.find(name + "-laser-end", "laser-end");
        laserTop = Core.atlas.find(name + "-laser-top", "laser-top");
        laserTopEnd = Core.atlas.find(name + "-laser-top-end", "laser-top-end");
        super.load();
    }
}
