package viscott.world.block.unit;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import mindustry.Vars;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.type.UnitType;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.blocks.payloads.UnitPayload;
import mindustry.world.blocks.units.Reconstructor;

public class HousingUnitBlock extends Reconstructor {
    //Cheaper Reconstructor. but pops the unit out from the top.
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
        public void update() {
            super.update();
            if (payload == null) return;
            if(hasUpgrade(payload.unit.type)) return;
            payload.unit.x(x);
            payload.unit.y(y);
            dumpPayload();
        }

        @Override
        public boolean acceptPayload(Building source, Payload payload){
            if(!(this.payload == null && payload instanceof UnitPayload pay)){
                return false;
            }

            var upgrade = upgrade(pay.unit.type);

            if(upgrade != null){
                if(!upgrade.unlockedNowHost() && !team.isAI()){
                    //flash "not researched"
                    pay.showOverlay(Icon.tree);
                }

                if(upgrade.isBanned()){
                    //flash an X, meaning 'banned'
                    pay.showOverlay(Icon.cancel);
                }
                return (team.isAI() || upgrade.unlockedNowHost()) && !upgrade.isBanned();
            }
            return false;
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
