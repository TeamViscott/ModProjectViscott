package viscott.world.block;

import mindustry.Vars;
import mindustry.game.Team;
import mindustry.world.Block;

import static mindustry.Vars.state;

public class PvBlock extends Block {

    public Team visibleTeam = null;
    public PvBlock(String name)
    {
        super(name);
        solid = true;
        destructible = true;
    }

    @Override
    public boolean isVisible(){
        return (visibleTeam == null ? true : Vars.player.team() == visibleTeam) && !isHidden() && (state.rules.editor || (!state.rules.hideBannedBlocks || !state.rules.isBanned(this)));
    }
}
