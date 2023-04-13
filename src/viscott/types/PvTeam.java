package viscott.types;

import arc.Events;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.TechTree;
import mindustry.game.EventType;
import mindustry.game.Team;
import viscott.content.PvPlanets;
import viscott.world.teamResearch;

public class PvTeam extends Team {

    public TechTree.TechNode techTree = null;
    public String description = "";
    public String info = "";
    public static Seq<PvTeam> all = new Seq<>();
    public teamResearch icon;
    public PvTeam(int id, String name, Color color)
    {
        super(id,name,color);
        icon = new teamResearch(name,this);
        all.add(this);
    }
}
