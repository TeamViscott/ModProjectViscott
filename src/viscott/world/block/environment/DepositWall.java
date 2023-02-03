package viscott.world.block.environment;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.StaticWall;
import viscott.world.block.drill.Grinder;

import static mindustry.Vars.world;

public class DepositWall extends StaticWall {
    public float tier = 1;
    public DepositWall(String name)
    {
        super(name);
    }

    @Override
    public void drawBase(Tile tile){
        Draw.rect(region, tile.worldx(), tile.worldy());
    }


}
