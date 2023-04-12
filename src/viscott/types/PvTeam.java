package viscott.types;

import arc.Events;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.TechTree;
import mindustry.game.EventType;
import mindustry.game.Team;
import viscott.content.PvPlanets;

public class PvTeam extends Team {

    public TechTree.TechNode techTree = null;
    protected TechTree.TechNode btt = null;
    public PvTeam(int id, String name, Color color)
    {
        super(id,name,color);
    }
}
