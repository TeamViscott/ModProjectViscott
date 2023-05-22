package viscott.world.block;

import arc.struct.Seq;
import arc.util.Structs;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.world.Block;
import viscott.types.PvFaction;

import static mindustry.Vars.state;

public class PvBlock extends Block {

    public Seq<PvFaction> faction = new Seq<>();
    public PvBlock(String name)
    {
        super(name);
        solid = true;
        destructible = true;
    }

    public boolean partOfPlayerFaction()
    {
        return faction.size == 0 || faction.count(f -> f.partOf(Vars.player.team())) > 0;
    }

    @Override
    public boolean isVisible(){
        return partOfPlayerFaction() && !isHidden() && (state.rules.editor || (!state.rules.hideBannedBlocks || !state.rules.isBanned(this)));
    }

    @Override
    public boolean isPlaceable(){
        return Vars.net.server() || (!state.rules.isBanned(this) || state.rules.editor) && supportsEnv(state.rules.env);
    }
}
