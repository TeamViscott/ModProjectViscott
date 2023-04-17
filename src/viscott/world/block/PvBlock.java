package viscott.world.block;

import arc.util.Structs;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.Groups;
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
    public boolean environmentBuildable(){
        return (visibleTeam == null ? true : Vars.player.team() == visibleTeam) && (state.rules.hiddenBuildItems.isEmpty() || !Structs.contains(requirements, i -> state.rules.hiddenBuildItems.contains(i.item)));
    }
}
