package viscott.world.block.defense;

import arc.struct.Seq;
import mindustry.Vars;
import mindustry.gen.Bullet;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.StatValues;
import viscott.content.PvStats;
import viscott.types.PvFaction;
import viscott.world.block.environment.DepositWall;

import static mindustry.Vars.state;

public class PvWall extends Wall {
    public int pierceReduction = 0 ;

    public Seq<PvFaction> faction = new Seq<>();

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

    public boolean partOfPlayerFaction()
    {
        return faction.size == 0 || faction.count(f -> f.partOf(Vars.player.team())) > 0;
    }

    @Override
    public boolean isVisible(){
        return state.rules.editor || (partOfPlayerFaction() && !isHidden() && (!state.rules.hideBannedBlocks || !state.rules.isBanned(this)));
    }

    @Override
    public boolean isPlaceable(){
        return Vars.net.server() || (!state.rules.isBanned(this) || state.rules.editor) && supportsEnv(state.rules.env);
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
