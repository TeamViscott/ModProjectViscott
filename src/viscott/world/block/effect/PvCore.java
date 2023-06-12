package viscott.world.block.effect;

import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.graphics.Pal;
import mindustry.world.blocks.storage.CoreBlock;
import viscott.types.PvFaction;

import static mindustry.Vars.state;

public class PvCore extends CoreBlock {
    public Seq<PvFaction> faction = new Seq<>();
    public float healTime = -1;
    public float healTimeout = 0;

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
        @Override
        public void updateTile()
        {
            super.updateTile();
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
