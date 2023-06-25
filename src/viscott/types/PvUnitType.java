package viscott.types;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.game.Gamemode;
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
        return Vars.state.rules.mode() == Gamemode.sandbox || Vars.state.isEditor() || Vars.net.server() || (factions.size == 0 || factions.count(f->f.partOf(Vars.player.team())) > 0) && super.unlockedNow();
    }
}
