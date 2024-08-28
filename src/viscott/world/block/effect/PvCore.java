package viscott.world.block.effect;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Interp;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Timer;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import viscott.types.PvFaction;

import static mindustry.Vars.player;
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
            return faction.count(f -> f.partOf(player.team())) == 0;
        return faction.size == 0 || faction.count(f -> f.partOf(player.team())) > 0;
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
        float delay = 0;
        Seq<Player> playerQue = new Seq<>();

        Player curPlayer = null;

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
            if (Vars.player == player) {
                var pos = Core.camera.position.cpy();

                Core.camera.position.set(tile);
                warmupEffect.at(tile);

                Core.camera.position.set(pos);
            }
            else
                warmupEffect.at(tile);
            playerQue.add(player);
        }

        @Override
        public void draw() {
            drawer.draw(this);
            drawTeamTop();
            if (playerQue.isEmpty()) return;
            float fin = 1 - (delay / warmupEffect.lifetime);
            float frac = Interp.smooth.apply(fin);
            Draw.color(Pal.lightOrange.cpy().lerp(Color.white,frac));
            Draw.z(Layer.effect+0.01f);
            Draw.rect(unitType.shadowRegion,x(),y(),unitType.shadowRegion.width*frac/4,unitType.shadowRegion.height*frac/4);
        }

        @Override
        public void updateTile()
        {
            super.updateTile();
            if (warmupEffect.lifetime != 0 && !playerQue.isEmpty()) {
                if (curPlayer == null) {
                    curPlayer = playerQue.get(0);
                    delay = warmupEffect.lifetime;
                }
                delay -= delta();
                float frac = Interp.circleIn.apply(1 - (delay / warmupEffect.lifetime));
                if (player == curPlayer)
                    Core.camera.position.lerp(x(),y(),frac*0.5f);
                if (delay <= 0) {
                    if (player == curPlayer)
                        Core.camera.position.set(tile);
                    playerQue.remove(curPlayer);
                    Call.playerSpawn(tile, curPlayer);
                    spawnEffect.at(tile);
                    curPlayer = null;
                }
            }
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
