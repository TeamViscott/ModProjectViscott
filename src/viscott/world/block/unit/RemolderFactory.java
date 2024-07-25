package viscott.world.block.unit;

import arc.Core;
import arc.Events;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Scaling;
import mindustry.content.Fx;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.Effect;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.UnitType;
import mindustry.ui.Bar;
import mindustry.ui.Styles;
import mindustry.world.blocks.payloads.BuildPayload;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.blocks.payloads.PayloadBlock;
import mindustry.world.blocks.payloads.UnitPayload;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import java.util.HashMap;

import static mindustry.Vars.state;

public class RemolderFactory extends Reconstructor {

    HashMap<UnitType,PvTemplate> templates = new HashMap<>();
    Seq<PvTemplate> tempList = new Seq<>();
    public int templateUseCount = 5;
    public RemolderFactory(String name) {
        super(name);
    }


    public void addUpgrade(UnitType from, UnitType to, PvTemplate template){
        upgrades.add(new UnitType[]{from, to});
        templates.put(from,template);
        tempList.add(template);
    }

    @Override
    public void setStats(){
        stats.timePeriod = constructTime;
        super.setStats();
        stats.add(Stat.output, table -> {
            table.clear();
            table.row();
            for(var upgrade : upgrades){
                if(upgrade[0].unlockedNow() && upgrade[1].unlockedNow()){
                    table.table(Styles.grayPanel, t -> {
                        t.left();

                        t.image(upgrade[0].uiIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                        t.table(info -> {
                            info.add(upgrade[0].localizedName).left();
                            info.row();
                        }).pad(10).left();
                    }).fill().padTop(5).padBottom(5);

                    if (templates.containsKey(upgrade[0])) {
                        table.table(Styles.grayPanel, t -> {

                            t.image(Icon.add).color(Pal.darkishGray).size(40).pad(10f);
                        }).fill().padTop(5).padBottom(5);

                        table.table(Styles.grayPanel, t -> {
                            t.left();

                            t.image(templates.get(upgrade[0]).uiIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                            t.table(info -> {
                                info.add(templates.get(upgrade[0]).localizedName).left();
                                info.row();
                            }).pad(10).left();
                        }).fill().padTop(5).padBottom(5);
                    }

                    table.table(Styles.grayPanel, t -> {

                        t.image(Icon.right).color(Pal.darkishGray).size(40).pad(10f);
                    }).fill().padTop(5).padBottom(5);

                    table.table(Styles.grayPanel, t -> {
                        t.left();

                        t.image(upgrade[1].uiIcon).size(40).pad(10f).right().scaling(Scaling.fit);
                        t.table(info -> {
                            info.add(upgrade[1].localizedName).right();
                            info.row();
                        }).pad(10).right();
                    }).fill().padTop(5).padBottom(5);

                    table.row();
                }
            }
        });
    }
    @Override
    public void setBars() {
        super.setBars();
        addBar("template uses left", (RemolderFactoryBuild entity) -> new Bar(() -> Core.bundle.format("bar.templateuse",entity.templateUses), () -> Pal.gray, () -> (entity.templateUses*1f)/(templateUseCount*1f)));
    }
    public class RemolderFactoryBuild extends ReconstructorBuild {
        public PvTemplate curTemp;
        public int templateUses;
        @Override
        public void updateTile(){
            boolean valid = false;

            if(payload != null){
                //check if offloading
                if(!hasUpgrade(payload.unit.type)){
                    moveOutPayload();
                }else{ //update progress
                    if(moveInPayload()){
                        valid = pIn();
                    }
                }
            }

            speedScl = Mathf.lerpDelta(speedScl, Mathf.num(valid), 0.05f);
            time += edelta() * speedScl * state.rules.unitBuildSpeed(team);
        }

        boolean pIn() {
            if(templates.containsKey(payload.unit.type) && curTemp != templates.get(payload.unit.type)) return false;
            boolean valid = false;
            if(efficiency > 0){
                valid = true;
                progress += edelta() * state.rules.unitBuildSpeed(team);
            }

            //upgrade the unit
            if(progress >= constructTime){
                payload.unit = upgrade(payload.unit.type).create(payload.unit.team());

                if(payload.unit.isCommandable()){
                    if(commandPos != null){
                        payload.unit.command().commandPosition(commandPos);
                    }
                    if(command != null){
                        //this already checks if it is a valid command for the unit type
                        payload.unit.command().command(command);
                    }
                }
                templateUses--;
                if (!(templateUses > 0))
                    curTemp = null;
                progress %= 1f;
                Effect.shake(2f, 3f, this);
                Fx.producesmoke.at(this);
                consume();
                Events.fire(new EventType.UnitCreateEvent(payload.unit, this));
            }
            return valid;
        }

        @Override
        public boolean acceptPayload(Building source, Payload payload){
            if(!((this.enabled || source == this)
                    && relativeTo(source) != rotation)){
                return false;
            }
            if (payload instanceof UnitPayload pay) {
                if (this.payload != null) return false;
                var upgrade = upgrade(pay.unit.type);

                if (upgrade != null) {
                    if (!upgrade.unlockedNowHost() && !team.isAI()) {
                        //flash "not researched"
                        pay.showOverlay(Icon.tree);
                    }

                    if (upgrade.isBanned()) {
                        //flash an X, meaning 'banned'
                        pay.showOverlay(Icon.cancel);
                    }
                }

                return upgrade != null && (team.isAI() || upgrade.unlockedNowHost()) && !upgrade.isBanned();
            } else if (payload instanceof BuildPayload pay && pay.build.block instanceof PvTemplate temp) {
                return tempList.contains(temp) && curTemp != temp;
            } else return false;
        }
        public void handlePayload(Building source,Payload payload) {
            if (payload instanceof BuildPayload pay) {
                templateUses = templateUseCount;
                curTemp = (PvTemplate) pay.build.block;
            } else
                super.handlePayload(source,payload);
        }
    }
}
