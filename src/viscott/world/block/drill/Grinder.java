package viscott.world.block.drill;


import arc.math.Mathf;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.Tile;
import viscott.world.block.PvBlock;
import viscott.world.block.environment.DepositWall;

import static mindustry.Vars.*;
import java.util.Arrays;

public class Grinder extends PvBlock {

    public int range = 1;
    public float speedPerOre = 0.2f;
    public float maxProgress = 100;
    public Grinder(String name)
    {
        super(name);
        update = true;;
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        return true;
    }


    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x,y,rotation,valid);
        int fix = (size % 2) * 4;
        Drawf.dashRect(Pal.lighterOrange,x*8-offset-range*8-fix,y*8-offset-range*8-fix,size * 8 + range * 16,size * 8 + range * 16);
    }

    public class GrinderBuild extends Building
    {
        public Block[] mineable = visibleBlocks();
        public Block[] visibleBlocks()
        {
            int ix = ((int)x/8)-(int)Math.floor((size-1)/2)-range,
            iy = ((int)y/8)-(int)Math.floor((size-1)/2)-range,
            rangeSize = size + range * 2;
            Block[] newBlockList = new Block[rangeSize*rangeSize];
            for (int i1 = 0;i1<rangeSize;i1++)
                for (int i2 = 0;i2<rangeSize;i2++)
                    newBlockList[i1*rangeSize+i2] = world.tiles.get(ix+i1,iy+i2).block();
            return newBlockList;
        }

        float maxMineSpeed = Arrays.stream(mineable).filter(a -> a instanceof DepositWall).count() * speedPerOre;
        float progress;
        @Override
        public void update()
        {
            progress = Mathf.approachDelta(progress,100,maxMineSpeed);
        }
    }
}
