package viscott.world;

import arc.Core;
import mindustry.Vars;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import viscott.types.PvFaction;

public class teamResearch extends Block {
    public PvFaction refTeam;
    public teamResearch(String name, PvFaction team)
    {
        super(name);
        localizedName = Core.bundle.get("team."+name);
        refTeam = team;
        requirements(Category.effect, BuildVisibility.sandboxOnly, ItemStack.with());
        health = 1;
        update = true;
        rebuildable = false;
    }

    @Override
    public void init()
    {
        super.init();
        description = refTeam.description;
        details = refTeam.info;
    }

    @Override
    public void loadIcon(){
        fullIcon =Core.atlas.find(name);

        uiIcon = fullIcon;
    }

    @Override
    public boolean unlocked(){
        return Vars.ui.research.lastNode == refTeam.techTree;
    }

    public class teamResearchBuild extends Building
    {
        @Override
        public void updateTile()
        {
            if (refTeam.add(team()))
                killed();
            sleep();
        }
    }
}
