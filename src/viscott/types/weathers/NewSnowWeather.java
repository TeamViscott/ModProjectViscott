package viscott.types.weathers;

import arc.Core;
import arc.Events;
import arc.fx.filters.FxaaFilter;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.math.geom.Rect;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import arc.util.noise.Noise;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.UnitTypes;
import mindustry.content.Weathers;
import mindustry.entities.Leg;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.graphics.Trail;
import mindustry.type.UnitType;
import mindustry.type.unit.TankUnitType;
import mindustry.type.weather.ParticleWeather;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.draw.DrawRegion;
import mindustry.world.meta.Attribute;
import viscott.content.PvEffects;
import viscott.content.PvSounds;
import viscott.utilitys.PvUtil;
import viscott.world.block.drill.Grinder;

import java.util.HashMap;

public class NewSnowWeather extends ParticleWeather {
    static TextureRegion[] snowOverlay;
    static TextureRegion[][] snowOverlayConveyor;
    static final int maxSnowSize = 5;
    public float alpha = 0;
    HashMap<Unit,Byte> walkPrintDirectory = new HashMap<>();
    HashMap<Unit, Trail[]> treadPrints = new HashMap<>();

    public class Vec2Rot extends Vec2 {
        public float rotation = 0;
        public Vec2Rot(Vec2 vec,float rot) {
            this(vec.x,vec.y,rot);
        }
        public Vec2Rot(float x,float y,float rot) {
            super(x,y);
            rotation = rot;
        }
    }

    HashMap<Leg, Vec2Rot> legPositions = new HashMap<>();
    public NewSnowWeather(String name) {
        super(name);
    }

    @Override
    public void load(){
        super.load();
        if(Vars.headless) return;
        snowOverlayConveyor = new TextureRegion[4][4];
        for(int i = 0;i < 4;i++)
            for(int i2 = 0;i2 < 4;i2++)
                snowOverlayConveyor[i][i2] = Core.atlas.find(PvUtil.GetName("snow-c-"+i+"-"+i2));

        snowOverlay = new TextureRegion[maxSnowSize];
        for(int i = 0;i < maxSnowSize;i++)
            snowOverlay[i] = Core.atlas.find(PvUtil.GetName("snow-"+(i+1)));
    }

    @Override
    public void init() {
        super.init();
        if(Vars.headless) return;
        Events.run(EventType.Trigger.draw,()->{
            if (!isActive())
                alpha = Mathf.lerp(alpha,0,0.01f);
            if (alpha < 0.01) return;
            Draw.z(Layer.blockOver);
            Draw.alpha(alpha);
            Vars.state.teams.getActive().each((teamData) -> {
                teamData.buildings.each((building) -> {
                    int ns = Mathf.floor(Math.abs(Noise.noise(building.x,building.y,0xffff,0xffff)))%4;
                    if (building instanceof Conveyor.ConveyorBuild cb ) {
                        for(int i = 0;i < 4;i++)
                            if (cb.rotation != i && (cb.nearby(i) == null || !(cb.nearby(i).block instanceof Conveyor && (cb.nearby(i).rotation+2)%4 == i)))
                                Draw.rect(snowOverlayConveyor[i][ns],building.x,building.y);
                    } else if (building instanceof Conduit.ConduitBuild cb) {
                        for(int i = 0;i < 4;i++)
                            if (cb.rotation != i && (cb.nearby(i) == null || !(cb.nearby(i).block instanceof Conduit && (cb.nearby(i).rotation+2)%4 == i)))
                                Draw.rect(snowOverlayConveyor[i][ns],building.x,building.y);
                    } else if (building instanceof StackConveyor.StackConveyorBuild cb) {
                        for(int i = 0;i < 4;i++)
                            if ((cb.rotation == i && cb.nearby(i) == null) || (cb.rotation != i && (cb.nearby(i) == null || !(cb.nearby(i).block instanceof StackConveyor && (cb.nearby(i).rotation+2)%4 == i))))
                                Draw.rect(snowOverlayConveyor[i][ns],building.x,building.y);
                    } else {
                        if (maxSnowSize >= building.block.size)
                            Draw.rect(snowOverlay[building.block.size - 1], building.x, building.y);
                    }
                });
            });
            if (Vars.android) return; // Android cant support forEach and i dont wanna have android users crash. so yeah...
            Groups.unit.each(u->{
                if (u instanceof TankUnit tu) {
                    TankUnitType typeu = (TankUnitType) tu.type;
                    if (!treadPrints.containsKey(tu)) {
                        treadPrints.put(tu, new Trail[typeu.treadRects.length * 2]);
                        for (int i = 0; i < treadPrints.get(u).length; i++)
                            treadPrints.get(u)[i] = new Trail(300);
                    }
                    for (int i = 0;i < typeu.treadRects.length;i++) {
                        treadPrints.get(u)[i * 2].draw(Color.black.a(alpha * 0.2f), typeu.treadRects[i].width / 8);
                        treadPrints.get(u)[i * 2 + 1].draw(Color.black.a(alpha * 0.2f), typeu.treadRects[i].width / 8);
                    }
                }
                if (u.type == UnitTypes.flare) {
                    if (!u.moving()) return;
                    if (Vars.state.isPaused()) return;
                    Fx.fireSmoke.at(u.x + Mathf.sinDeg(u.rotation+90) * -u.type.hitSize,u.y + Mathf.sinDeg(u.rotation) * -u.type.hitSize);
                }
                if (!u.isGrounded()) return;
                if (Vars.state.isPaused()) return;
                var remD = new Seq<Unit>();
                walkPrintDirectory.keySet().forEach(s->{
                    if (!s.isValid())
                        remD.add(s);
                });
                remD.each(un->{
                    walkPrintDirectory.remove(un);
                });
                var remD2 = new Seq<Unit>();
                treadPrints.keySet().forEach(s->{
                    if (!s.isValid())
                        remD2.add(s);
                });
                remD2.each(un->{
                    treadPrints.remove(un);
                });
                if (u instanceof MechUnit mu) {
                    if (!u.moving()) return;
                    if (u.floorOn().isLiquid) return;
                    if (!walkPrintDirectory.containsKey(mu))
                        walkPrintDirectory.put(u, (byte) 0);

                    if (mu.walkExtend(true) >= 1 && walkPrintDirectory.get(u) == 0) {
                        walkPrintDirectory.replace(u,(byte) 1);
                        PvEffects.snowPrintL.at(u.x, u.y, mu.baseRotation + 90, u.type.legRegion);
                        PvSounds.snowCrunch.at(u.x,u.y);
                    } else if (mu.walkExtend(true) >= 3 && walkPrintDirectory.get(u) == 1) {
                        walkPrintDirectory.replace(u,(byte) 2);
                        PvEffects.snowPrintR.at(u.x, u.y, mu.baseRotation + 90, u.type.legRegion);
                        PvSounds.snowCrunch.at(u.x,u.y);
                    } else if (mu.walkExtend(true) < 3 && walkPrintDirectory.get(u) == 2 ) {
                        walkPrintDirectory.replace(u,(byte) 0);
                    }
                }
                else if (u instanceof TankUnit tu) {
                    TankUnitType typeu = (TankUnitType) tu.type;
                    for (int i = 0;i < typeu.treadRects.length;i++) {
                        Rect tread = typeu.treadRects[i];
                        float dx = Angles.trnsx(tu.rotation+90,tread.x-tread.width/2,0)/8;
                        float dy = Angles.trnsy(tu.rotation+90,tread.x-tread.width/2,0)/8;
                        treadPrints.get(u)[i*2].update(u.x+dx,u.y+dy);

                        dx = Angles.trnsx(tu.rotation+90,-tread.x+tread.width/2,0)/8;
                        dy = Angles.trnsy(tu.rotation+90,-tread.x+tread.width/2,0)/8;
                        treadPrints.get(u)[i*2+1].update(u.x+dx,u.y+dy);
                    }
                }
                else if (u instanceof LegsUnit lu) {
                    if (!u.moving()) return;
                    if (u.floorOn().isLiquid) return;
                    for(int i = 0;i<lu.legs.length;i++) {
                        Leg leg = lu.legs[i];
                        if (leg.moving) {
                            if (legPositions.containsKey(leg)) {
                                Vec2Rot pos = legPositions.get(leg);
                                if (lu.type.footRegion.found())
                                    PvEffects.snowPrint.at(pos.x,pos.y,pos.rotation,lu.type.footRegion);
                                else
                                    PvEffects.snowPrint.at(pos.x,pos.y,pos.rotation);
                                legPositions.remove(leg);
                            }
                        } else {
                            if (!legPositions.containsKey(leg)) {
                                legPositions.put(leg, new Vec2Rot(leg.base, lu.legAngle(i)));
                                PvSounds.snowCrunch.at(u.x,u.y);
                            }
                        }
                    }
                }
            });
        });
    }

    @Override
    public WeatherState create(float intensity, float duration){
        alpha = 0;
        legPositions.clear();
        treadPrints.clear();
        walkPrintDirectory.clear();
        return super.create(intensity,duration);
    }

    @Override
    public void update(WeatherState state){
        super.update(state);
        alpha = Mathf.lerp(alpha,Mathf.clamp(state.life/60-2,0,1),0.01f);
    }

    @Override
    public void drawOver(WeatherState state){
        super.drawOver(state);
    }
    @Override
    public void drawUnder(WeatherState state){
        super.drawUnder(state);
        Draw.color(Color.white.cpy().a(0.3f * alpha));
        Draw.rect();
    }

}
