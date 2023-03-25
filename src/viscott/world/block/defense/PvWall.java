package viscott.world.block.defense;

import mindustry.gen.Bullet;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.StatValues;
import viscott.content.PvStats;
import viscott.world.block.environment.DepositWall;

public class PvWall extends Wall {
    public int pierceReduction = 0 ;
    public PvWall(String name)
    {
        super(name);
    }

    @Override
    public void setStats()
    {
        super.setStats();
        if (pierceReduction > 0)
            stats.add(PvStats.pierceReduction, StatValues.string(pierceReduction+" Pierce",""));
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
