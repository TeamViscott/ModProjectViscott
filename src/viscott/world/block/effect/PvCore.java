package viscott.world.block.effect;

import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Timer;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.graphics.Pal;
import mindustry.world.blocks.storage.CoreBlock;
import viscott.types.PvFaction;

import static mindustry.Vars.state;

public class PvCore extends CoreBlock {
    public Seq<PvFaction> faction = new Seq<>();
    public float healTime = -1;
    public Effect warmupEffect = Fx.none;
    public Effect spawnEffect = Fx.none;

    public PvCore(String name)
    {
        super(name);
        update = true;
        noUpdateDisabled = false;
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

    public class PvCoreBuild extends CoreBuild {
        float charge = 0;
        Seq<Player> playerQue = new Seq<>();

        @Override
        public void requestSpawn(Player player){
            //do not try to respawn in unsupported environments at all
            if(!unitType.supportsEnv(state.rules.env)) return;
            if(playerQue.contains(player)) return;
            warmupEffect.at(tile);
            playerQue.add(player);
            Timer.schedule(()->{
                Call.playerSpawn(tile, player);
                spawnEffect.at(tile);
                playerQue.remove(player);
                },
                    warmupEffect.lifetime/60);
        }

        @Override
        public void updateTile()
        {
            super.updateTile();
            if (healTime < 1) return;
            if (wasRecentlyDamaged()) return;
            if (health != maxHealth) {
                charge += Time.delta;
                if (charge > 60) {
                    charge %= 60;
                    heal(maxHealth/healTime);
                    Fx.healBlockFull.at(x, y, block.size, Pal.heal, block);
                }
            }
        }
    }
}
