package viscott.types;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.part.DrawPart;
import mindustry.game.Gamemode;
import mindustry.gen.Crawlc;
import mindustry.gen.MechUnit;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.ui.Styles;
import viscott.content.PvFactions;
import viscott.content.PvStats;
import viscott.world.draw.ChangeRegionPart;

import java.awt.*;

public class PvUnitType extends UnitType {
    public Seq<PvFaction> factions = new Seq<>();

    //chain code.
    private TextureRegion[] textureChain;
    private TextureRegion[] textureChainCells;
    DrawPart.PartParams partp = new DrawPart.PartParams();

    //Dodge stuff
    // how fast it basically should go.
    public float strafeStrength = 7;
    // the drag during the strafe.
    public float strafeDrag = 0;
    // the duration of the strafe.
    public float strafeTime = 1;
    // the strafe cooldown.
    public float strafeCooldown = 2;
    // the strafe damage taken multiplier. While strafing, damage taken is multiplied by x.
    public float strafeDamageMultiplier = 1;
    // a red tinted trail created while strafing.
    public Color strafeTint = Pal.lancerLaser.cpy().a(0.4f);
    public int strafeTrail = 0;

    public int strafeBursts = 1;
    // if above 1, enables strafe and Dodge unit params.
    public int strafeLevel = 0;

    // strafe slash, dodgeUnit's 3rd ability.
    public float strafeSlashTime = 15;
    public float strafeSlashStrength = 10;
    public float strafeSlashCooldown = 10;
    public float strafeSlashDelay = 8;
    public BulletType strafeSlashBullet;

    public boolean unlockOnDeath = false; //for boss units, to unlock their details after defeating them.

    // direct boost, dodgeUnit's 2nd ability.
    public float directBoost = 1.5f;
    public float directDecay = 60;
    public PvUnitType(String name)
    {
        super(name);
        outlineColor = Color.valueOf("#181a1b");
    }

    @Override
    public void setStats() {
        super.setStats();
        if (strafeLevel > 0) {
            stats.add(PvStats.strafeStat,(t) -> {
                strafeSideStat(t);
            });
            if (strafeLevel >= 2) {
                stats.add(PvStats.directBoost,"+"+((directBoost-1)*100) + "%");
            }
        }
    }
    public void strafeSideStat(Table table) {
        table.row();
        table.table(Styles.grayPanel,(t) -> {
            t.left().top().defaults().padRight(3.0F).left();

            t.add("Duration: ",Pal.lightishGray);
            t.add(Math.round(strafeTime/60*100)/100d+"s");
            t.row();
            t.add("Cooldown: ",Pal.lightishGray);
            t.add(Mathf.round(strafeCooldown/60*100)/100d+"s");
            if (strafeBursts > 1) {
                t.row();
                t.add("Charges: ",Pal.lightishGray);
                t.add(strafeBursts+"");
            }
        }).growX().pad(5.0F).margin(10.0F);
        table.row();
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
    public void killed(Unit u) {
        if (unlockOnDeath && Vars.state.isCampaign())
            unlock();
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
