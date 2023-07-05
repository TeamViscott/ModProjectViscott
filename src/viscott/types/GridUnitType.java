package viscott.types;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.Scaled;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Reflect;
import arc.util.Timer;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.core.World;
import mindustry.entities.abilities.Ability;
import mindustry.entities.part.DrawPart;
import mindustry.entities.units.WeaponMount;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.Tile;
import mindustry.world.Tiles;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.environment.AirBlock;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.payloads.BuildPayload;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawTurret;
import viscott.content.PvBlocks;

import java.io.Writer;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static mindustry.Vars.player;

public class GridUnitType extends PvUnitType{
    public HashMap<Unit, World> grids = new HashMap<>();
    public HashMap<Unit,AtomicBoolean> doneBuild = new HashMap<>();
    public int buildSize = 4;
    boolean[][] buildArea = new boolean[buildSize][buildSize];
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
    public void View(Unit unit) {
        Vars.world = grids.get(unit);
    }
    World buildGrid() {

        World gridWorld = new World();
        gridWorld.resize(buildSize,buildSize);
        gridWorld.loadGenerator(buildSize,buildSize,(tiles) -> {
            for(int iy = 0;iy < buildSize;iy++)
                for(int ix = 0;ix < buildSize;ix++) {
                    tiles.set(ix,iy,new Tile(ix,iy,PvBlocks.densePlate,Blocks.air,Blocks.air));
                }
        });
        return gridWorld;
    }
    public void setGridLayout(byte[][] layout) {
        buildArea = new boolean[layout.length][layout[0].length];
        for(int i1 = 0;i1 < layout.length;i1++) {
            for (int i2 = 0;i2 < layout[i1].length;i2++) {
                buildArea[i1][i2] = layout[i1][i2] > 0;
            }
        }
        buildSize = Math.max(layout.length,layout[0].length);
    }
    public boolean buildAt(int x, int y, Unit unit, Building building,byte rotation) {
        int cx = Mathf.ceil(unit.x / 8) - buildSize / 2,
                cy = Mathf.ceil(unit.y / 8) - buildSize / 2;
        if (building == null || building.block() == null) return false;
        if (building.block instanceof CoreBlock) return false;
        World curWorld = Vars.world;
        int s = buildSize-1;
        World gridWorld = grids.get(unit);
        Tile cT = building.tile;
        Tile t = gridWorld.tile(0,0);
        int size = building.block().size;
        int min = -Mathf.floor((size - 1) / 2),
                max = Mathf.floor(size / 2);
        int xMin = min,xMax = max,
                yMin = min,yMax = max;
        switch(rotation%4) {
            case 0:
                t = gridWorld.tile(cT.x - cx,cT.y - cy);
                if (!buildArea[y][x]) return false;
                break;
            case 1:
                t = gridWorld.tile(cT.y - cy,s- (cT.x - cx));
                if (!buildArea[x][y]) return false;
                break;
            case 2:
                t = gridWorld.tile(s- (cT.x - cx),s- (cT.y - cy));
                if (!buildArea[s-y][s-x]) return false;
                break;
            case 3:
                t = gridWorld.tile(s- (cT.y - cy),cT.x - cx);
                if (!buildArea[s-x][s-y]) return false;
                break;
        }
        if (t == null) return false;
        if (t.x + min < 0 || t.y + min < 0) return false;
        if (t.x + max >= buildSize || t.y + max >= buildSize) return false;
        if (building.tile.build == building)
            building.tile.setNet(Blocks.air);
        Vars.world = grids.get(unit);
        t.setBlock(building.block(),unit.team);
        t.build = building;
        building.rotation -= rotation;
        building.rotation %= 4;
        building.tile = t;
        for(int i1 = xMin;i1 <= xMax;i1++)
            for(int i2 = yMin;i2 <= yMax;i2++)
                Vars.world.tile(t.x + i1,t.y + i2).build = building;
        building.updateProximity();
        Vars.world = curWorld;
        return true;
    }
    public boolean placeFrom(int x,int y,Unit unit,byte rotation) {
        int bx = Mathf.ceil(unit.x / 8) - buildSize / 2,
                by = Mathf.ceil(unit.y / 8) - buildSize / 2;
        World g = grids.get(unit);
        Building b = g.tile(x,y).build;
        if (b == null || b.block() == null) return false;

        int size = b.block().size;
        int min = -Mathf.floor((size - 1) / 2),
                max = Mathf.floor(size / 2);

        Tile cT = b.tile();
        for(int i1 = min;i1 <= max;i1++)
            for(int i2 = min;i2 <= max;i2++)
                g.tile(cT.x+i1,cT.y+i2).setBlock(Blocks.air);
        int s = buildSize-1;
        Tile t = Vars.world.tile(0,0);
        switch(rotation%4) {
            case 0:
                t = Vars.world.tile(bx + cT.x,by + cT.y);
                break;
            case 1:
                t = Vars.world.tile(bx + s - cT.y,by + cT.x);
                break;
            case 2:
                t = Vars.world.tile(bx + s - cT.x,by + s - cT.y);
                break;
            case 3:
                t = Vars.world.tile(bx + cT.y,by + s - cT.x);
                break;
        }
        if (t == null) {
            Log.warn("No Tile found at : " + (bx + cT.x) + ", " + (by + cT.y) + " | bx : " + bx + ", by : " + by, 0);
            return false;
        }
        t.setBlock(b.block(),unit.team);
        t.build = b;
        b.tile = t;
        b.rotation += rotation;
        b.rotation %= 4;
        for(int i1 = min;i1 <= max;i1++)
            for(int i2 = min;i2 <= max;i2++)
                Vars.world.tile(t.x + i1,t.y + i2).build = b;
        b.set(t.x * 8 + (b.block().size-1) % 2 * 4,t.y * 8 + (b.block().size-1) % 2 * 4);
        b.created();
        b.add();
        return true;
    }

    @Override
    public void update(Unit unit){
        if (doneBuild.get(unit) == null)
            doneBuild.put(unit,new AtomicBoolean(false));
        if (grids.get(unit) == null)
            grids.put(unit,buildGrid());
        super.update(unit);
        if (!unit.isFlying()) {
            unit.x = Math.round(unit.x / 8 + 0.5) * 8 - 4;
            unit.y = Math.round(unit.y / 8 + 0.5) * 8 - 4;
            unit.rotation = Math.round(unit.rotation / 90) * 90;
            if (doneBuild.get(unit).get()) {
                if (doneBuild.get(unit).get()) { //unbuild time
                    Seq<Building> proxupdate = new Seq<>();
                    for (int i1 = 0; i1 < buildSize; i1++) {
                        for (int i2 = 0; i2 < buildSize; i2++) {
                            Tile t = grids.get(unit).tile(i1,i2);
                            if (t.build != null && t.block() != null)
                                proxupdate.add(t.build);
                            placeFrom(i1, i2, unit, (byte) Math.round(unit.rotation / 90));
                        }
                    }
                    proxupdate.each(b -> b.updateProximity());
                    doneBuild.get(unit).set(false);
                }
            }
        } else {
            if (!doneBuild.get(unit).get()) { //building time
                int bx = Mathf.ceil(unit.x / 8) - buildSize / 2,
                        by = Mathf.ceil(unit.y / 8) - buildSize / 2;
                for(int i1 = 0;i1 < buildSize;i1++) {
                    for(int i2 = 0;i2 < buildSize;i2++) {
                        Tile t = Vars.world.tile(bx+i1,by+i2);
                        if (t != null && t.block() != null)
                            buildAt(i1,i2,unit,t.build, (byte) Math.round(unit.rotation / 90));
                    }
                }
                for(int i1 = 0;i1 < buildSize;i1++) {
                    for(int i2 = 0;i2 < buildSize;i2++) {
                        Tile t = Vars.world.tile(bx+i1,by+i2);
                        if (t.build != null && t.block() != null) {
                            t.build.onProximityUpdate();
                            t.build.updateProximity();
                        }
                    }
                }
                doneBuild.get(unit).set(true);
            }
        }

        Seq<Building> updated = new Seq<>();
        World w = Vars.world;
        Tiles tiles = grids.get(unit).tiles;
        Vars.world = grids.get(unit);
        grids.get(unit).tiles.each((x,y) -> {
            Tile tile = tiles.get(x,y);
            if (tile.block() != null && !updated.contains(tile.build)) {
                if (tile.build != null && tile.build.block() != null)
                tile.build.update();
                updated.add(tile.build);
            }
        });
        Vars.world = w;
    }
    @Override
    public void draw(Unit unit) {
        if (grids.get(unit) == null)
            grids.put(unit,buildGrid());

        float z = unit.isFlying() ? Layer.flyingUnitLow : Layer.block-1;
        if(unit.controller().isBeingControlled(player.unit())){
            drawControl(unit);
        }
        if((unit.isFlying() || shadowElevation > 0)){
            Draw.z(Math.min(Layer.darkness, z - 1f));
            drawShadow(unit);
        }
        Draw.z(z-0.1f);
        if(drawBody) drawOutline(unit);
        drawWeaponOutlines(unit);
        if(engineLayer > 0) Draw.z(engineLayer);
        if(trailLength > 0 && !naval && (unit.isFlying() || !useEngineElevation)){
            drawTrail(unit);
        }
        if(engines.size > 0) drawEngines(unit);
        Draw.z(z);
        if(drawBody) drawBody(unit);
        if(drawCell) drawCell(unit);
        drawWeapons(unit);
        if(drawItems) drawItems(unit);
        drawLight(unit);

        if(unit.shieldAlpha > 0 && drawShields){
            drawShield(unit);
        }

        if(parts.size > 0){
            for(int i = 0; i < parts.size; i++){
                var part = parts.get(i);

                WeaponMount first = unit.mounts.length > part.weaponIndex ? unit.mounts[part.weaponIndex] : null;
                if(first != null){
                    DrawPart.params.set(first.warmup, first.reload / weapons.first().reload, first.smoothReload, first.heat, first.recoil, first.charge, unit.x, unit.y, unit.rotation);
                }else{
                    DrawPart.params.set(0f, 0f, 0f, 0f, 0f, 0f, unit.x, unit.y, unit.rotation);
                }

                if(unit instanceof Scaled s){
                    DrawPart.params.life = s.fin();
                }

                part.draw(DrawPart.params);
            }
        }

        for(Ability a : unit.abilities){
            Draw.reset();
            a.draw(unit);
        }

        Draw.reset();

        Seq<Building> drawed = new Seq<>();
        World w = Vars.world;
        Tiles t = grids.get(unit).tiles;
        Vars.world = grids.get(unit);
        grids.get(unit).tiles.each((x,y) -> {
            float xOffset = (x - buildSize / 2) * 8 + 4;
            float yOffset = (y -  buildSize / 2) * 8 + 4;

            Building build = t.get(x,y).build;
            if (build != null && !drawed.contains(build)) {
                if (build.block() == null) return;
                Draw.z(Layer.flyingUnitLow + 0.1f);
                int size = build.block().size;
                float off = Mathf.floor((size - 1) * 4);
                float Dx = unit.x + Angles.trnsx(unit.rotation, xOffset + off, yOffset + off);
                float Dy = unit.y + Angles.trnsy(unit.rotation, xOffset + off, yOffset + off);
                build.x = Dx;
                build.y = Dy;
                build.payloadRotation = unit.rotation;
                if (build instanceof Turret.TurretBuild tb) {
                    Turret turret = (Turret) tb.block;
                    DrawTurret drawer = (DrawTurret) turret.drawer;
                    Draw.rect(drawer.base, Dx, Dy, unit.rotation);
                    Draw.color();
                    Drawf.shadow(drawer.preview, Dx + tb.recoilOffset.x - turret.elevation, Dy + tb.recoilOffset.y - turret.elevation, tb.drawrot());
                    Draw.z(Layer.flyingUnitLow + 0.2f);
                    drawer.drawTurret(turret, tb);
                    drawer.drawHeat(turret, tb);
                    drawTurretParts(tb, Dx, Dy);
                }
                else if (build instanceof Conveyor.ConveyorBuild cb) {
                    Draw.rect(build.block().getGeneratedIcons()[0], Dx, Dy, unit.rotation+build.rotation*90);
                }
                else {
                    Draw.rect(build.block().getGeneratedIcons()[0], Dx, Dy, unit.rotation+build.rotation*90);
                }

                drawed.add(build);
                Draw.reset();
            }
        });
        Vars.world = w;
    }
    void drawTurretParts(Turret.TurretBuild tb, float x, float y) {
        DrawTurret drawer = (DrawTurret) ((Turret) tb.block()).drawer;
        if(drawer.parts.size > 0){
            if(drawer.outline.found()){
                //draw outline under everything when parts are involved
                Draw.z(Layer.flyingUnitLow+0.15f);
                Draw.rect(drawer.outline, x + tb.recoilOffset.x, y + tb.recoilOffset.y, tb.drawrot());
                Draw.z(Layer.flyingUnitLow+0.21f);
            }

            float progress = tb.progress();

            //TODO no smooth reload
            var params = DrawPart.params.set(tb.warmup(), 1f - progress, 1f - progress, tb.heat, tb.curRecoil, tb.charge, x + tb.recoilOffset.x, y + tb.recoilOffset.y, tb.rotation);

            for(var part : drawer.parts){
                params.setRecoil(part.recoilIndex >= 0 && tb.curRecoils != null ? tb.curRecoils[part.recoilIndex] : tb.curRecoil);
                part.draw(params);
            }
        }
    }
}
