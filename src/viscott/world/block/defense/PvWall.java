package viscott.world.block.defense;

import mindustry.gen.Bullet;
import mindustry.world.blocks.defense.Wall;
import viscott.content.PvStats;

public class PvWall extends Wall {
    public int pierceReduction = 0 ;
    public PvWall(String name)
    {
        super(name);
    }

    public class PvWallBuild extends WallBuild
    {
        @Override
        public boolean collision(Bullet bullet) {
            for (int i = pierceReduction;i>0;i--)
            bullet.collided.add(0);
            return super.collision(bullet);
        }
    }
}
