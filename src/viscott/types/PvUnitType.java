package viscott.types;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.entities.part.DrawPart;
import mindustry.game.Gamemode;
import mindustry.gen.MechUnit;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import viscott.content.PvFactions;
import viscott.world.draw.ChangeRegionPart;

import java.awt.*;

public class PvUnitType extends UnitType {
    public Seq<PvFaction> factions = new Seq<>();
    public int drawChain = 0;
    public float chainWeave = 0;
    public float chainOffset = 0;
    public float chainAmplitude = 0;
    private TextureRegion[] textureChain;
    private TextureRegion[] textureChainCells;
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
        if (drawChain > 0) drawChain(unit);
        super.draw(unit);
    }

    public void drawChain(Unit unit){
        if (unit instanceof MechUnit mechUnit) {
            float t = mechUnit.walkExtend(false);
            for (int i = drawChain - 1; i >= 0; i--) {
                applyColor(unit);
                Draw.rect(textureChain[i], unit.x, unit.y);
                if (textureChainCells[i] != null) { //Also draw cell if available
                    Draw.color(cellColor(unit));
                    Draw.rect(textureChainCells[i], unit.x, unit.y);
                }
                Draw.reset();
            }
        }
    }

    @Override
    public void load() {
        super.load();
        if (0 <= drawChain) return;
        textureChain = new TextureRegion[drawChain];
        for(int i = 0;i < drawChain;i++)
            textureChain[0] = Core.atlas.find(name+"-"+i);
        textureChainCells = new TextureRegion[drawChain];
        for(int i = 0;i < drawChain;i++)
            textureChainCells[0] = Core.atlas.find(name + "-" + i + "-cell");
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
