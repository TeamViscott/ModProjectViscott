package viscott.types;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.entities.part.DrawPart;
import mindustry.game.Gamemode;
import mindustry.gen.Crawlc;
import mindustry.gen.MechUnit;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import viscott.content.PvFactions;
import viscott.world.draw.ChangeRegionPart;

import java.awt.*;

public class PvUnitType extends UnitType {
    public Seq<PvFaction> factions = new Seq<>();
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
        super.draw(unit);
    }


    @Override
    public void load() {
        super.load();
        textureChainCells = new TextureRegion[segments];
        for(int i = 0;i < segments;i++)
            if (Core.atlas.has(name + "-segment" + i + "-cell"))
                textureChainCells[i] = Core.atlas.find(name + "-segment" + i + "-cell");
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

    @Override
    public void drawCrawl(Crawlc crawl){
        super.drawCrawl(crawl);

        Unit unit = (Unit)crawl;
        for(int i = 0; i < segments; i++){
            if (textureChainCells[i] == null)
                continue;
            float trns = Mathf.sin(crawl.crawlTime() + i * segmentPhase, segmentScl, segmentMag);

            //at segment 0, rotation = segmentRot, but at the last segment it is rotation
            float rot = Mathf.slerp(crawl.segmentRot(), unit.rotation, i / (float)(segments - 1));
            float tx = Angles.trnsx(rot, trns), ty = Angles.trnsy(rot, trns);

            //shadow
            Draw.color(unit.team.color);


            //TODO merge outlines?

            Draw.rect(textureChainCells[i], unit.x + tx, unit.y + ty, rot - 90);
        }
    }
}
