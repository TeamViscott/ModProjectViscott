package viscott.world.block.effect;

import arc.struct.Seq;
import mindustry.Vars;
import mindustry.world.blocks.storage.CoreBlock;
import viscott.types.PvFaction;

import static mindustry.Vars.state;

public class PvCore extends CoreBlock {
    public Seq<PvFaction> faction = new Seq<>();

    public PvCore(String name)
    {
        super(name);
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
}
