package viscott.world;

import arc.Core;
import mindustry.Vars;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Team;
import viscott.types.PvTeam;

import static mindustry.Vars.net;
import static mindustry.Vars.state;

public class teamResearch extends UnlockableContent {
    public PvTeam refTeam;
    public teamResearch(String name, PvTeam team)
    {
        super(name);
        localizedName = Core.bundle.get("team."+name+".name");
        refTeam = team;
    }

    @Override
    public void init()
    {
        super.init();
        description = refTeam.description;
        details = refTeam.info;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.team;
    }

    @Override
    public void loadIcon(){
        fullIcon =Core.atlas.find(name+"-team");

        uiIcon = fullIcon;
    }

    @Override
    public boolean unlocked(){
        return Vars.ui.research.lastNode == refTeam.techTree;
    }
}
