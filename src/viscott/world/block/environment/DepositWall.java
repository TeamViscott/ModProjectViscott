package viscott.world.block.environment;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.StaticWall;

import static mindustry.Vars.world;

public class DepositWall extends StaticWall {
    public DepositWall(String name)
    {
        super(name);
    }

    @Override
    public void drawBase(Tile tile){
        int rx = tile.x / 2 * 2;
        int ry = tile.y / 2 * 2;

        if(Core.atlas.isFound(large) && eq(rx, ry)){
            Draw.rect(split[tile.x % 2][1 - tile.y % 2], tile.worldx(), tile.worldy());
        }else if(variants > 0){
            Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx(), tile.worldy());
        }else{
            Draw.rect(region, tile.worldx(), tile.worldy());
        }

        //draw ore on top
        if(tile.overlay().wallOre){
            tile.overlay().drawBase(tile);
        }
    }

    boolean eq(int rx, int ry){
        return rx < world.width() - 1 && ry < world.height() - 1
                && world.tile(rx + 1, ry).block() == this
                && world.tile(rx, ry + 1).block() == this
                && world.tile(rx, ry).block() == this
                && world.tile(rx + 1, ry + 1).block() == this;
    }
}
