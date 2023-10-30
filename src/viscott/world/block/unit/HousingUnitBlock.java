package viscott.world.block.unit;

import arc.Core;
import arc.Events;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Eachable;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.units.BuildPlan;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.type.UnitType;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.blocks.payloads.UnitPayload;
import mindustry.world.blocks.units.Reconstructor;

import static mindustry.Vars.state;

public class HousingUnitBlock extends Reconstructor {
    //Cheaper Reconstructor. but pops the unit out from the top
    public int payloadSize = 3;
    public HousingUnitBlock(String name) {
        super(name);
        rotate = false;
        outputsPayload = false;
        solid = false;
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        Draw.rect(region, plan.drawx(), plan.drawy());
        if (teamRegion.found()) {
            if (teamRegions[Vars.player.team().id] == teamRegion) {
                Draw.color(Vars.player.team().color);
            }
            Draw.rect(teamRegions[Vars.player.team().id], plan.drawx(), plan.drawy());
            Draw.color();
        }
        Draw.rect(topRegion, plan.drawx(), plan.drawy());
    }

    @Override
    public void loadIcon(){
        super.loadIcon();
        fullIcon = Core.atlas.find(name + "-full",fullIcon);
        uiIcon = Core.atlas.find(name + "-ui",fullIcon);
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region, teamRegion, topRegion};
    }

    public class HousingUnitBuild extends ReconstructorBuild {
        {
            rotation = -1;
        }
        @Override
        public void updateTile() {
            boolean valid = false;

            if(payload != null){
                    if(moveInPayload()){
                        if(efficiency > 0){
                            valid = true;
                            progress += edelta() * state.rules.unitBuildSpeed(team);
                        }

                        //upgrade the unit
                        if(progress >= constructTime){
                            payload.unit.health = payload.unit.type.health;

                            if(payload.unit.isCommandable()){
                                if(commandPos != null){
                                    payload.unit.command().commandPosition(commandPos);
                                }
                                if(command != null){
                                    //this already checks if it is a valid command for the unit type
                                    payload.unit.command().command(command);
                                }
                            }

                            progress %= 1f;
                            Effect.shake(2f, 3f, this);
                            Fx.producesmoke.at(this);
                            consume();
                            payload.unit.x(x);
                            payload.unit.y(y);
                            dumpPayload();
                        }
                    }
            }

            speedScl = Mathf.lerpDelta(speedScl, Mathf.num(valid), 0.05f);
            time += edelta() * speedScl * state.rules.unitBuildSpeed(team);
        }

        @Override
        public boolean acceptPayload(Building source, Payload payload){
            if(!(this.payload == null && payload instanceof UnitPayload pay)){
                return false;
            }
            return pay.unit.hitSize / 8f <= payloadSize;
        }

        @Override
        public void draw() {
            Draw.rect(region,x,y);
            if (this.block.teamRegion.found()) {
                if (this.block.teamRegions[this.team.id] == this.block.teamRegion) {
                    Draw.color(this.team.color);
                }
                Draw.rect(this.block.teamRegions[this.team.id], this.x, this.y);
                Draw.color();
            }
            for(int i = 0; i < 4; i++){
                if(blends(i)){
                    Draw.rect(inRegion, x, y, (i * 90) - 180);
                }
            }
            if (payload != null) {
                payload.draw();
                UnitType u = upgrade(payload.unit.type);
                if (u != null) {
                    float a = progress / constructTime;
                    Draw.alpha(a);
                    Draw.rect(u.fullIcon, x, y);
                    Draw.alpha(1);
                }
            }
            Draw.rect(topRegion,x,y);
        }
    }
}
