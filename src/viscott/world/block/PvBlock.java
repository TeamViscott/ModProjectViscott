package viscott.world.block;

import arc.Core;
import arc.struct.Seq;
import arc.util.Structs;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.type.LiquidStack;
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
    @Override
    public void loadIcon(){
        super.loadIcon();
        fullIcon = Core.atlas.find(name + "-full",fullIcon);
        uiIcon = Core.atlas.find(name + "-ui",fullIcon);
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
