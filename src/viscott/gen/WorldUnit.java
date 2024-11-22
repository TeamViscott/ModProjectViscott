package viscott.gen;

import arc.func.Cons;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.math.geom.Point2;
import arc.math.geom.QuadTree;
import arc.struct.IntSeq;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.ai.UnitCommand;
import mindustry.content.Blocks;
import mindustry.core.World;
import mindustry.entities.abilities.Ability;
import mindustry.entities.part.DrawPart;
import mindustry.entities.units.UnitController;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.Item;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.Tiles;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.environment.Prop;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.DrawTurret;
import viscott.content.PvBlocks;
import viscott.content.PvUnitMapper;
import viscott.types.WorldUnitType;
import viscott.utilitys.PvPacketHandler;
import viscott.world.draw.DrawBatchRotate;

import static mindustry.Vars.*;
import static mindustry.Vars.itemSize;

public class WorldUnit extends MechUnit {
    public World innerWorld = new World();
    public boolean built = false;
    public int buildSize = 0;
    // I forgot why but buildArea is [y][x] base. dont ask why.
    public boolean[][] buildArea = new boolean[0][0];
    private static DrawBatchRotate drawBatch = new DrawBatchRotate();
    public WorldUnitType gu;

    class Pos2 {
        int x = 0;
        int y = 0;

        public Pos2() {}
        public Pos2(int x,int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + ", "+y;
        }
    }
    Pos2 tilePos = new Pos2();
    public WorldUnit(WorldUnitType type) {
        this();

        gu = type;
        build();
    }
    public WorldUnit() {
        super();
    }

    @Override
    public void type(UnitType type) {
        super.type(type);
    }

    @Override
    public void resetController() {
        super.resetController();
    }

    @Override
    public void controller(UnitController cont) {
        super.controller(cont);
    }

    public void build() {
        buildSize = gu.buildSize;
        buildArea = gu.buildArea;
        innerWorld = buildGrid();
    }

    World buildGrid() {
        if (ui.editor == null) return null;
        World gridWorld = new World();
        gridWorld.resize(buildSize,buildSize);
        Cons<Tiles> gen = (tiles) -> {
            for(int iy = 0;iy < buildSize;iy++)
                for(int ix = 0;ix < buildSize;ix++) {
                    tiles.set(ix,iy,new Tile(ix,iy, PvBlocks.densePlate, Blocks.air,Blocks.air));
                }
        };
        gen.get(gridWorld.tiles);
        return gridWorld;

    }

    /**
     * Returns true if the x and y are still in buildArea after accounting for placement rotation.
     */
    public boolean buildOntoUnit(Building building) {
        // initiating some variables
        byte blockRotation = (byte) Math.round(rotation / 90);
        int size = building.block().size;
        int min = -(size-1) / 2,       // 1-1 = 0/2 = 0    | 2-1 = 1/2 = 0    | 3-1 = 2/2 = -1
            max = size / 2 + 1;        // 1/2 = 0 + 1 = 1  | 2/2 = 1 + 1 = 2  | 3/2 = 1 + 1 = 2

        World curWorld = Vars.world;


        // returns a coordinate that would fit in the sub world.
        Pos2 sub_tile = new Pos2(
                building.tileX() - tilePos.x,
                building.tileY() - tilePos.y
        );

        // check if its in bounds
        if (sub_tile.x + min < 0 || sub_tile.y + min < 0)
            return false; // bottom left check. tile bounds needs to be within the grid range.
        if (sub_tile.x + max >= buildSize || sub_tile.y + max >= buildSize)
            return false; // up right check.

        Tile t;
        int unitWorldSize = buildSize-1;
        // offset is used to correct the center of 2x2 and any even squared block.
        int offset = (building.block().size-1) % 2;
        // rotates the block accordingly. accommodates for offset.
        switch (blockRotation % 4) {
            case 0:
                // Right
                t = innerWorld.tile(sub_tile.x,sub_tile.y);
                break;
            case 1:
                // Up
                t = innerWorld.tile(sub_tile.y,unitWorldSize - sub_tile.x - offset);
                break;
            case 2:
                // Left
                t = innerWorld.tile(unitWorldSize - sub_tile.x - offset,unitWorldSize - sub_tile.y - offset);
                break;
            default:  // also case 3
                // Down
                t = innerWorld.tile(unitWorldSize - sub_tile.y - offset,sub_tile.x);
                break;
        }

        // checks the range of the block for if its on the unit. all should return true. one false and it fails.
        for (int o_x = min; o_x < max; o_x++) {
            short current_x = (short) (t.x+o_x);

            for (int o_y = min; o_y < max; o_y++) {
                short current_y = (short) (t.y+o_y);

                if (!buildArea[current_y][current_x])
                    return false; // one of the tiles of the multiblock is not on the unit. cancel buildOn.
            }
        }

        // moves building to the sub world. (with rotation.)
        building.tile.setNet(Blocks.air);
        building.rotation -= blockRotation;
        building.rotation = (building.rotation+4)%4;

        Vars.world = innerWorld;

        t.setBlock(building.block(),team,building.rotation,()->building);
        // removes building from Groups.all so that it doesnt get updated. the unit will handle the update.
        // this is because it needs to update in its own world.
        if (Groups.all.contains(b->b==building)) {
            building.setIndex__all(-1);
            Groups.all.remove(building);
        }

        // return to actual world at the end. don't wanna accidentally put the player in the unit.
        Vars.world = curWorld;
        return true;
    }

    public void updateTilePos() {
        tilePos.x = Mathf.ceil(x / 8) - buildSize / 2;
        tilePos.y = Mathf.ceil(y / 8) - buildSize / 2;
    }

    public IntSeq energyGet(Building build,Tile newOrigin) {
        if (build.power != null) {
            Tile tileFrom = build.tile;
            IntSeq newLinks = new IntSeq();
            build.power.links.each(l -> {
                int offX = Point2.x(l) - tileFrom.x,
                    offY = Point2.y(l) - tileFrom.y;
                newLinks.add(Point2.pack(newOrigin.x+offX,newOrigin.y+offY));
            });
            return newLinks;
        }
        return null;
    }

    public boolean placeFrom(Tile c,int x,int y,byte rotation) {
        int bx = c.x,
                by = c.y;
        Building b = innerWorld.tile(x,y).build;
        if (b == null || b.block() == null) return false;

        int size = b.block().size;
        int min = -Mathf.floor((size - 1) / 2),
                max = Mathf.floor(size / 2);

        Tile cT = b.tile();
        for(int i1 = min;i1 <= max;i1++)
            for(int i2 = min;i2 <= max;i2++)
                innerWorld.tile(cT.x+i1,cT.y+i2).setBlock(Blocks.air);
        int s = buildSize-1;
        Tile t = Vars.world.tile(0,0);
        int offset = (b.block().size-1) % 2;
        switch(rotation%4) {
            case 0:
                t = Vars.world.tile(bx + cT.x,by + cT.y);
                break;
            case 1:
                t = Vars.world.tile(bx + s - cT.y-offset,by + cT.x);
                break;
            case 2:
                t = Vars.world.tile(bx + s - cT.x-offset,by + s - cT.y-offset);
                break;
            case 3:
                t = Vars.world.tile(bx + cT.y,by + s - cT.x-offset);
                break;
        }
        if (t == null) {
            Log.warn("No Tile found at : " + (bx + cT.x) + ", " + (by + cT.y) + " | bx : " + bx + ", by : " + by, 0);
            return false;
        }
        b.rotation += rotation;
        b.rotation %= 4;
        t.setBlock(b.block(),team,b.rotation,()->b);
        b.set(t.x * 8 + (b.block().size-1) % 2 * 4,t.y * 8 + (b.block().size-1) % 2 * 4);
        if(!Groups.all.contains(r->r == b)) {
            int i = Groups.all.addIndex(b);
            b.setIndex__all(i);
        }
        return true;
    }

    @Override
    public void update() {
        super.update();
        float   midX = Mathf.ceil(x / 8) * 8 - 4,
                midY = Mathf.ceil(y / 8) * 8 - 4;

        if (innerWorld == null) return; // prevents crashes

        if (built && ((player != null && !player.boosting) || (isCommandable() && command().currentCommand() != UnitCommand.boostCommand))) {
            int bx = Mathf.ceil(midX / 8) - buildSize / 2,
                by = Mathf.ceil(midY / 8) - buildSize / 2;
            int rot = (Math.round(rotation / 90) + 4) % 4;
            boolean canLand = true;
            for(int xo = 0;xo < buildArea.length;xo++)
                for(int yo = 0;yo < buildArea[xo].length;yo++)
                    if (buildArea[xo][yo] && innerWorld.tile(xo,yo).block() != Blocks.air) {
                        Tile t = null;
                        switch(rot) {
                            case 0:
                                t = Vars.world.tile(bx + xo, by + yo);
                                break;
                            case 1:
                                t = Vars.world.tile(bx + buildSize - yo, by + xo);
                                break;
                            case 2:
                                t = Vars.world.tile(bx + buildSize - xo, by+ buildSize - yo);
                                break;
                            case 3:
                                t = Vars.world.tile(bx + yo, by + buildSize - xo);
                                break;
                        }
                        if (t == null || t.floor() == null) {
                            canLand = false;
                            continue;
                        }
                        if (t.solid()) {
                            canLand = false;
                            continue;
                        }
                        if (t.block() != Blocks.air && !(t.block() instanceof Prop)) {
                            canLand = false;
                        }
                    }
            if (!canLand)
                updateBoosting(true);
        }
        if (!isFlying()) {
            x = midX;
            y = midY;
            rotation = Math.round(rotation / 90) * 90;
            int bx = Mathf.ceil(x / 8) - buildSize / 2,
                    by = Mathf.ceil(y / 8) - buildSize / 2;
            Tile bt = Vars.world.tile(bx,by);
            if (built) { //unbuild time
                Seq<Building> proxupdate = new Seq<>();
                for (int i1 = 0; i1 < buildSize; i1++) {
                    for (int i2 = 0; i2 < buildSize; i2++) {
                        Tile t = innerWorld.tile(i1, i2);
                        if (t.build != null && t.block() != null)
                            proxupdate.add(t.build);
                        placeFrom(bt,i1, i2,  (byte) Math.round(rotation / 90));
                    }
                }
                proxupdate.each(b -> {
                    if (!b.isAdded())
                        b.add();
                    b.updateProximity();
                    if (b instanceof Turret.TurretBuild tb) {
                        tb.unit = BlockUnitUnit.create();
                    }
                });
                built = false;
            }
        } else if (!built) {//building time
            updateTilePos();
            Seq<Building> proxupdate = new Seq<>();
            if (!Vars.net.client()) { //clients should get info from packages, not from itself. just to keep it synced.

                Seq<Building> validBuildings = new Seq<>();
                QuadTree<Building> buildings = team.data().buildingTree;
                if (buildings == null) {
                    Log.info("Ouch, team has no buildings");
                } else {
                    buildings.intersect(tilePos.x*8, tilePos.y*8, buildSize*8, buildSize*8, (b) -> {
                        if (!(b.block() instanceof CoreBlock)) {
                            validBuildings.add(b);
                        }
                    });

                    for(var b : validBuildings) {

                        proxupdate.add(b);
                        if (!buildOntoUnit(b)) {
                            continue;
                        }
                    }
                }
            }
            World w = Vars.world;
            Vars.world = innerWorld;
            proxupdate.each(b -> {
                b.onProximityUpdate();
            });
            Vars.world = w;
            built = true;
            if (Vars.net.server())
                PvPacketHandler.gridUnitLoad(this);
        }

        Seq<Building> updated = new Seq<>();
        World w = Vars.world;
        Tiles tiles = innerWorld.tiles;
        Vars.world = innerWorld;
        innerWorld.tiles.each((x,y) -> {
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
    public void draw() {
        float z = isFlying() ? Layer.flyingUnitLow : Layer.block-1;
        if(controller().isBeingControlled(player.unit())){
            type.drawControl(this);
        }
        if((isFlying() || type.shadowElevation > 0)){
            Draw.z(Math.min(Layer.darkness, z - 1f));
            type.drawShadow(this);
        }
        Draw.z(z-0.1f);
        if(type.drawBody)
            type.drawOutline(this);
        type.drawWeaponOutlines(this);
        if(type.engineLayer > 0)
            Draw.z(type.engineLayer);
        if(type.trailLength > 0 && !type.naval && (isFlying() || !type.useEngineElevation)){
            type.drawTrail(this);
        }
        if(type.engines.size > 0)
            type.drawEngines(this);
        Draw.z(z);
        if(type.drawBody)
            type.drawBody(this);
        if(type.drawCell)
            type.drawCell(this);
        type.drawWeapons(this);
        if(type.drawItems)
            type.drawItems(this);
        type.drawLight(this);

        if(shieldAlpha > 0 && type.drawShields){
            type.drawShield(this);
        }

        if(type.parts.size > 0){
            for(int i = 0; i < type.parts.size; i++){
                var part = type.parts.get(i);

                WeaponMount first = mounts.length > part.weaponIndex ? mounts[part.weaponIndex] : null;
                if(first != null){
                    DrawPart.params.set(first.warmup, first.reload / type.weapons.first().reload, first.smoothReload, first.heat, first.recoil, first.charge, x, y, rotation);
                }else{
                    DrawPart.params.set(0f, 0f, 0f, 0f, 0f, 0f, x, y, rotation);
                }

                if(this instanceof Scaled s){
                    DrawPart.params.life = s.fin();
                }

                part.draw(DrawPart.params);
            }
        }

        for(Ability a : abilities){
            Draw.reset();
            a.draw(this);
        }

        Draw.reset();
        if (innerWorld == null || !built || drawBatch == null) return;
        Seq<Building> drawed = new Seq<>();
        World w = Vars.world;
        Tiles t = innerWorld.tiles;
        Vars.world = innerWorld;
        drawBatch.rotation = rotation;
        drawBatch.minZ = z+1;
        innerWorld.tiles.each((x,y) -> {
            float xOffset = (x - buildSize / 2) * 8 + 4;
            float yOffset = (y -  buildSize / 2) * 8 + 4;
            Building build = t.get(x,y).build;
            if (build != null && !drawed.contains(build)) {
                var block = build.block();
                if (build.block() == null) return;
                int size = build.block().size;
                float off = Mathf.floor((size - 1) * 4);
                float Dx = this.x + Angles.trnsx(rotation, xOffset + off, yOffset + off);
                float Dy = this.y + Angles.trnsy(rotation, xOffset + off, yOffset + off);
                build.x = Dx;
                build.y = Dy;
                build.payloadRotation = rotation;
                Draw.z(z+1);
                if (build instanceof Turret.TurretBuild tb) { //Turrets require a custom Drawer
                    Turret turret = (Turret) tb.block;
                    DrawTurret drawer = (DrawTurret) turret.drawer;
                    Draw.rect(drawer.base, Dx, Dy, rotation);
                    Draw.color();
                    Drawf.shadow(drawer.preview, Dx + tb.recoilOffset.x - turret.elevation, Dy + tb.recoilOffset.y - turret.elevation, tb.drawrot());
                    Draw.z(z + 1.1f);
                    drawer.drawTurret(turret, tb);
                    drawer.drawHeat(turret, tb);
                    drawTurretParts(tb, Dx, Dy);
                }
                else if (build instanceof Conveyor.ConveyorBuild cb) { // so do conveyors
                    float rot = rotation+build.rotation*90;
                    Conveyor conv = (Conveyor) block;
                    int frame = cb.enabled && cb.clogHeat <= 0.5f ? (int)(((Time.time * conv.speed * 8f * cb.timeScale() * cb.efficiency)) % 4) : 0;
                    Draw.rect(conv.regions[cb.blendbits][frame], Dx, Dy,tilesize * cb.blendsclx, tilesize * cb.blendscly, rot);
                    for(int i = 0; i < cb.len; i++){
                        Item item = cb.ids[i];
                        Tmp.v1.trns(rot, tilesize, 0);
                        Tmp.v2.trns(rot, -tilesize / 2f, cb.xs[i] * tilesize / 2f);

                        float
                                ix = (cb.x + Tmp.v1.x * cb.ys[i] + Tmp.v2.x),
                                iy = (cb.y + Tmp.v1.y * cb.ys[i] + Tmp.v2.y);

                        //keep draw position deterministic.
                        Draw.z(Draw.z()+0.1f);
                        Draw.rect(item.fullIcon, ix, iy, itemSize, itemSize);
                    }
                }
                else if (build instanceof StackConveyor.StackConveyorBuild sb) { //StackConveyor broken without custom drawer.
                    StackConveyor stackConv = (StackConveyor) block;
                    Draw.rect(stackConv.regions[sb.state],sb.x,sb.y,rotation + sb.rotation * 90);
                    for(int i = 0; i < 4; i++){
                        if((sb.blendprox & 1 << i) == 0){
                            Draw.rect(stackConv.edgeRegion, sb.x, sb.y, (sb.rotation - i) * 90 + rotation);
                        }
                    }
                    Tile from = world.tile(sb.link);
                    if (from != null) {
                        int fromRot = from.build == null ? sb.rotation : from.build.rotation;
                        float a = (fromRot % 4) * 90;
                        float b = (sb.rotation % 4) * 90;
                        if ((fromRot % 4) == 3 && (sb.rotation % 4) == 0) a = -1 * 90;
                        if ((fromRot % 4) == 0 && (sb.rotation % 4) == 3) a = 4 * 90;
                        if (sb.items != null && sb.items.total() != 0) {
                            if (from.build != null)
                                Tmp.v1.set(from.build.x, from.build.y);
                            else
                                Tmp.v1.set(sb.x,sb.y);
                            Tmp.v2.set(sb.x, sb.y);
                            Tmp.v1.interpolate(Tmp.v2, 1f - sb.cooldown, Interp.linear);
                            Draw.z(Draw.z()+0.1f);
                            Draw.rect(stackConv.stackRegion, Tmp.v1.x, Tmp.v1.y, rotation + Mathf.lerp(a, b, Interp.smooth.apply(1f - Mathf.clamp(sb.cooldown * 2, 0f, 1f))));
                            TextureRegion itemRegion = sb.items.first().fullIcon;
                            float scl = Mathf.clamp(sb.items.total() / stackConv.itemCapacity);
                            Draw.rect(itemRegion, Tmp.v1.x, Tmp.v1.y, scl*5f, scl*5f, 0);
                        }
                    }
                } else {
                    drawBatch.inject();
                    build.draw();
                    drawBatch.eject();
                }
                drawed.add(build);
                Draw.reset();
                Draw.blend();
            }
        });
        Vars.world = w;
    }

    void drawTurretParts(Turret.TurretBuild tb, float x, float y) {
        DrawTurret drawer = (DrawTurret) ((Turret) tb.block()).drawer;
        if(drawer.parts.size > 0){
            Draw.z(Layer.flyingUnitLow+1.18f);
            if(drawer.outline.found()){
                //draw outline under everything when parts are involved
                Draw.z(Layer.flyingUnitLow+1.15f);
                Draw.rect(drawer.outline, x + tb.recoilOffset.x, y + tb.recoilOffset.y, tb.drawrot());
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

    @Override
    public int classId() {
        return PvUnitMapper.GridUnitId;
    }
    @Override
    public void read(Reads read) {
        super.read(read);
        built = read.bool();
        int typeId = read.i();
        if (content.units().find(u->u.id == typeId) instanceof WorldUnitType g) {
            gu = g;
            build();
        }

        World w = world;
        world = innerWorld;
        if (built) {
            innerWorld.tiles.each((x,y) -> {
                Tile t = innerWorld.tile(x, y);
                int bId = read.i();
                if (bId == -1) return;
                int rot = read.b();
                Block b = content.block(bId);
                Building build = b.newBuilding();
                build.rotation = rot;
                build.read(read,Byte.MAX_VALUE);
                t.setBlock(b, team, build.rotation, () -> build);
                Groups.all.remove(build);
                build.setIndex__all(-1);

            });
            innerWorld.tiles.each((x,y) -> {
                Building build = innerWorld.tile(x,y).build;
                if (build == null) return;
                build.updateProximity();
            });
        }
        world = w;
    }

    @Override
    public void write(Writes write) {
        super.write(write);
        write.bool(built);
        write.i(gu.id);
        if (built) {
            innerWorld.tiles.each((x,y) -> {
                Tile t = innerWorld.tile(x,y);
                if (t.build == null || t != t.build.tile)
                    write.i(-1);
                else {
                    write.i(t.build.block().id);
                    write.b(t.build.rotation);
                    t.build.write(write);
                }

            });
        }
    }
}
