package viscott.types;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.type.UnitType;
import viscott.content.PvFactions;

import java.awt.*;

public class PvUnitType extends UnitType {
    public Seq<PvFaction> factions = new Seq<>();
    public PvUnitType(String name)
    {
        super(name);
        outlineColor = Color.valueOf("#181a1b");
    }

    @Override
    public boolean unlockedNow()
    {
        return Vars.net.server() || factions.count(f->f.partOf(Vars.player.team())) > 0 && super.unlockedNow();
    }
}
