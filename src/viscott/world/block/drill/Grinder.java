package viscott.world.block.drill;


import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Tile;
import viscott.world.block.PvBlock;
import viscott.world.block.environment.DepositWall;

public class Grinder extends PvBlock {
    public Grinder(String name)
    {
        super(name);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        return tile.block() instanceof DepositWall;
    }
}
