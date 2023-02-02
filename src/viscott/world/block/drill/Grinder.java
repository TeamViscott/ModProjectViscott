package viscott.world.block.drill;


import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.Tile;
import viscott.world.block.PvBlock;
import viscott.world.block.environment.DepositWall;

public class Grinder extends PvBlock {

    public Block[] behind;
    public Grinder(String name)
    {
        super(name);
        behind = new Block[size*size];
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        return true;
    }

    @Override
    public boolean canReplace(Block other)
    {
        if (other instanceof DepositWall d)
        {
            for (int i = 1;i<behind.length;i++)
                behind[i-1] = behind[i];
            behind[0] = d;
            return true;
        }
        return other == Blocks.air;
    }

    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x,y,rotation,valid);
        Drawf.text();
    }
    public class GrinderBuild extends Building
    {

    }
}
