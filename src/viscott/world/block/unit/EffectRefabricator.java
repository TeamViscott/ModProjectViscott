package viscott.world.block.unit;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.StatusEffect;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.blocks.payloads.UnitPayload;
import mindustry.world.blocks.units.Reconstructor;

import static mindustry.Vars.state;

public class EffectRefabricator extends Reconstructor {
    public StatusEffect statusEffect;
    public float statusDuration = 60;
    public float maxSize = 8;
    public EffectRefabricator(String name) {
        super(name);
    }

    public class EffectFactoryBuild extends ReconstructorBuild {

        @Override
        public boolean acceptPayload(Building source, Payload payload){
            if (this.payload == null && payload instanceof UnitPayload u)
                if (u.size() <= maxSize) return true;
            return false;
        }

        @Override
        public void draw(){
            Draw.rect(region, x, y);

            //draw input
            boolean fallback = true;
            for(int i = 0; i < 4; i++){
                if(blends(i) && i != rotation){
                    Draw.rect(inRegion, x, y, (i * 90) - 180);
                    fallback = false;
                }
            }
            if(fallback) Draw.rect(inRegion, x, y, rotation * 90);

            Draw.rect(outRegion, x, y, rotdeg());

            if(constructing() && hasArrived()){
                Draw.draw(Layer.blockOver, () -> {
                    Draw.alpha(1f - progress/ constructTime);
                    Draw.rect(payload.unit.type.fullIcon, x, y, payload.rotation() - 90);
                    Draw.reset();
                    Drawf.construct(this,payload.unit.type, payload.rotation() - 90f, progress / constructTime, speedScl, time);
                });
            }else{
                Draw.z(Layer.blockOver);

                drawPayload();
            }

            Draw.z(Layer.blockOver + 0.1f);
            Draw.rect(topRegion, x, y);
        }

        @Override
        public void updateTile(){
            boolean valid = false;

            if(payload != null){
                if (payload.unit.hasEffect(statusEffect))
                    moveOutPayload();
                else if (moveInPayload()) {
                    if(efficiency > 0){
                        valid = true;
                        progress += edelta() * state.rules.unitBuildSpeed(team);
                    }
                    if(progress >= constructTime){
                        payload.unit.apply(statusEffect,statusDuration);
                        progress %= 1;
                        Effect.shake(2f, 3f, this);
                        Fx.producesmoke.at(this);
                        consume();
                    }
                }
            }

            speedScl = Mathf.lerpDelta(speedScl, Mathf.num(valid), 0.05f);
            time += edelta() * speedScl * state.rules.unitBuildSpeed(team);
        }

        @Override
        public boolean constructing(){
            return payload != null;
        }
    }
}
