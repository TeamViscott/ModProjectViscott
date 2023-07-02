package viscott.types;

import arc.Events;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.TechTree;
import mindustry.game.EventType;
import mindustry.game.Team;
import viscott.world.teamResearch;

public class PvFaction {

    Seq<Team> linkedTeams = new Seq<>();
    public TechTree.TechNode techTree = null;
    public String description = "";
    public String info = "";
    public String name = "";
    public Color color;
    public int id = 0;
    static int idGiver = 0;
    public static Seq<PvFaction> all = new Seq<>();
    public teamResearch icon;
    public PvFaction(String name, Color color)
    {
        id = idGiver++;
        this.color = color;
        this.name = name;
        icon = new teamResearch(name,this);
        all.add(this);
        Events.on(EventType.WorldLoadBeginEvent.class,(w)->{
            linkedTeams.clear();
        });
    }
    public int teamSize()
    {
        return linkedTeams.size;
    }
    public Seq<Team> allLinked()
    {
        return linkedTeams;
    }
    public String packetLinks()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        linkedTeams.each(lTeam -> {
            sb.append(";");
            sb.append(lTeam.id);
        });
        return sb.toString();
    }
    public boolean partOf(Team team)
    {
        return linkedTeams.contains(team);
    }
    public boolean remove(Team team)
    {
        if (linkedTeams.contains(team)) {
            linkedTeams.remove(team);
            return true;
        }
        return false; // Didn't contain the Team, so it couldn't remove it.
    }
    public boolean add(Team team)
    {
        if (!linkedTeams.contains(team)){
            linkedTeams.add(team);
            return true;
        }
        return false; //already contains team. no duplicates
    }
    public void clearTeams()
    {
        linkedTeams.clear();
    }
}
