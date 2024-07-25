package viscott.world.block.environment;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Point2;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.blocks.environment.TallBlock;
import viscott.world.block.drill.Grinder;

import static mindustry.Vars.world;

public class DepositWall extends TallBlock {
    public float tier = 1;

    public TextureRegion large;

    public DepositWall(String name)
    {
        super(name);
    }

    @Override
    public void drawBase(Tile tile){
        if (large == null)
            large = Core.atlas.find(name + "-large");
        int rx = tile.x/2*2, ry = tile.y/2*2;
        if (canLarge(rx,ry) && Mathf.randomSeed(Point2.pack(rx,ry)) < 0.5) {
            if (tile.x / 2 * 2 == tile.x && tile.y / 2 * 2 == tile.y)
                Draw.rect(large, tile.worldx() + 4, tile.worldy() + 4);
        }
        else
            Draw.rect(region, tile.worldx(), tile.worldy());
    }

    boolean canLarge(int x,int y) {
        for (int xC = 0; xC < 2; xC++)
            for (int yC = 0; yC < 2; yC++)
                if (world.tile(x + xC, y + yC).block() != this)
                    return false;
        return true;
    }
}
