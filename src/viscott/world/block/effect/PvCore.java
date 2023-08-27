package viscott.world.block.effect;

import arc.Core;
import arc.graphics.g2d.Draw;
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
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import viscott.types.PvFaction;

import static mindustry.Vars.state;

public class PvCore extends CoreBlock {
    public Seq<PvFaction> faction = new Seq<>();
    public float healTime = -1;
    public float minHealCharge = -1;
    public float healPowerDrain = 0;
    public Effect warmupEffect = Fx.none;
    public Effect spawnEffect = Fx.none;
    public boolean blackListFactions = false;

    public DrawBlock drawer = new DrawMulti(
            new DrawDefault()
    );

    public PvCore(String name)
    {
        super(name);
        update = true;
        noUpdateDisabled = false;
    }

    @Override
    public void init() {
        super.init();
        drawer.load(this);
    }

    @Override
    public void loadIcon(){
        super.loadIcon();
        fullIcon = Core.atlas.find(name + "-full",fullIcon);
        uiIcon = Core.atlas.find(name + "-ui",fullIcon);
    }

    public boolean partOfPlayerFaction()
    {
        if (blackListFactions)
            return faction.count(f -> f.partOf(Vars.player.team())) == 0;
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
            if(warmupEffect.lifetime == 0) {
                Call.playerSpawn(tile,player);
                spawnEffect.at(tile);
                return;
            }
            if(playerQue.contains(player)) return;
            if (!Vars.net.client())
                warmupEffect.at(tile);
            playerQue.add(player);
            Timer.schedule(()->{
                Call.playerSpawn(tile, player);
                if (!Vars.net.client())
                    spawnEffect.at(tile);
                playerQue.remove(player);
                },
                    warmupEffect.lifetime/60);
        }

        @Override
        public void draw() {
            drawer.draw(this);
            drawTeamTop();
        }

        @Override
        public void updateTile()
        {
            super.updateTile();
            if (healTime < 1) return;
            if (wasRecentlyDamaged()) return;
            if (minHealCharge > power.graph.getBatteryStored() || healPowerDrain > power.graph.getBatteryStored()) return;
            if (health != maxHealth) {
                charge += Time.delta;
                if (charge > 60) {
                    charge %= 60;
                    heal(maxHealth/healTime);
                    if (healPowerDrain != 0)
                        power.graph.useBatteries(healPowerDrain);
                    Fx.healBlockFull.at(x, y, block.size, Pal.heal, block);
                }
            }
        }
    }
}
