package viscott.types;

import arc.Core;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.entities.part.DrawPart;
import mindustry.game.Gamemode;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import viscott.content.PvFactions;
import viscott.world.draw.ChangeRegionPart;

import java.awt.*;

public class PvUnitType extends UnitType {
    public Seq<PvFaction> factions = new Seq<>();
    DrawPart.PartParams partp = new DrawPart.PartParams();
    public PvUnitType(String name)
    {
        super(name);
        outlineColor = Color.valueOf("#181a1b");
    }

    @Override
    public void draw(Unit unit){
        partp.set(0,0,0,0,0,0,unit.x,unit.y,unit.rotation);
        partp.life = unit.health / unit.maxHealth;
        for(var part : unit.type.parts) {
            if (part instanceof ChangeRegionPart c && c.lifeEnabled)
                part.draw(partp);
        }
        super.draw(unit);
    }

    @Override
    public void loadIcon(){
        super.loadIcon();
        fullIcon = Core.atlas.find(name + "-preview",fullIcon);
        uiIcon = Core.atlas.find(name + "-ui",fullIcon);
    }

    @Override
    public boolean unlockedNow()
    {
        return Vars.state.rules.mode() == Gamemode.sandbox || Vars.state.isEditor() || Vars.net.server() || (factions.size == 0 || factions.count(f->f.partOf(Vars.player.team())) > 0) && super.unlockedNow();
    }
}
