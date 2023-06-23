package viscott.types;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Payloadc;
import mindustry.gen.TimedKillc;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.blocks.environment.AirBlock;
import mindustry.world.blocks.payloads.BuildPayload;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class GridUnitType extends PvUnitType{
    HashMap<Unit,Seq<Seq<BuildPayload>>> grids = new HashMap<>();
    HashMap<Unit,AtomicBoolean> doneBuild = new HashMap<>();
    public int buildSize = 4;
    public byte[][] buildArea = new byte[buildSize][buildSize];
    final Block fillin = Blocks.air;
    public GridUnitType(String name)
    {
        super(name);
    }

    @Override
    public Unit create(Team team){
        Unit unit = super.create(team);
        grids.put(unit,buildGrid());
        doneBuild.put(unit,new AtomicBoolean(false));
        return unit;
    }
    Seq<Seq<BuildPayload>> buildGrid() {

        Seq<Seq<BuildPayload>> grid = new Seq<>();
        for(int i1 = 0;i1 < buildSize;i1++) {
            Seq<BuildPayload> row = new Seq<>();
            for (int i2 = 0; i2 < buildSize; i2++) {
                Building fill = fillin.newBuilding();
                fill.tile = Vars.world.tile(0);
                row.add(new BuildPayload(fill));
            }
            grid.add(row);
        }
        return grid;
    }
    public boolean buildAt(int x, int y, Unit unit, Building building) {
        Building b = grids.get(unit).get(x).get(y).build;
        if (b != null && b.isValid()) return false;
        if (building.tile.build == building)
            building.tile.setNet(Blocks.air);
        grids.get(unit).get(x).get(y).build = building;
        return true;
    }

    @Override
    public void update(Unit unit){
        super.update(unit);
        if (!unit.isFlying()) {
            unit.x = Math.round(unit.x/8+0.5)*8-4;
            unit.y = Math.round(unit.y/8+0.5)*8-4;
            unit.rotation = Math.round(unit.rotation/90)*90;
        }


        if (grids.get(unit) == null)
            grids.put(unit,buildGrid());
        Seq<Building> updated = new Seq<>();
        grids.get(unit).each(xRow -> {
            xRow.each(elemP -> {
                var elem = elemP.build;
                if (elem.block() != null && !updated.contains(elem)) {
                    elem.update();
                    updated.add(elem);
                }
            });
        });
    }
    @Override
    public void draw(Unit unit) {
        super.draw(unit);
        Seq<Building> drawed = new Seq<>();
        int[] gridPos = {0,0};
        grids.get(unit).each(xRow -> {
            float xOffset = (gridPos[0] - buildSize / 2) * 8;
            xRow.each(elemP -> {
                var elem = elemP.build;
                float yOffset = (gridPos[1] -  buildSize / 2) * 8;
                if (elem.block() != null && !drawed.contains(elem)) {
                    Draw.z(Layer.flyingUnit+0.1f);
                    elem.x = unit.x + Angles.trnsx(unit.rotation, xOffset, yOffset);
                    elem.y = unit.y + Angles.trnsy(unit.rotation, xOffset, yOffset);
                    elem.payloadRotation = unit.rotation;
                    Draw.rect(elem.block().region,elem.x,elem.y,elem.payloadRotation);
                    drawed.add(elem);
                }
                gridPos[1]++;
            });
            gridPos[0]++;
            gridPos[1] = 0;
        });
    }
}
