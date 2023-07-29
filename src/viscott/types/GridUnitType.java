package viscott.types;

import arc.Events;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.Scaled;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Reflect;
import arc.util.Timer;
import arc.util.Tmp;
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
import mindustry.type.Item;
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
import viscott.gen.GridUnit;

import java.io.Writer;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static mindustry.Vars.*;
import static mindustry.Vars.itemSize;

public class GridUnitType extends PvUnitType{
    public int buildSize = 4;
    public boolean[][] buildArea = new boolean[buildSize][buildSize];
    public GridUnitType(String name)
    {
        super(name);
        constructor = () -> new GridUnit(this);
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
}
