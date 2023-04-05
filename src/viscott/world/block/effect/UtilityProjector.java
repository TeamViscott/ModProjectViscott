package viscott.world.block.effect;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.DelayedRemovalSeq;
import arc.struct.Seq;
import arc.util.Strings;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.StatusEffect;
import mindustry.world.Block;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.meta.BlockGroup;
import viscott.content.PvStatusEffects;
import viscott.utilitys.PvUtil;
import viscott.world.statusEffects.PvStatusEffect;

import javax.swing.text.AbstractDocument;

import static mindustry.Vars.indexer;
import static mindustry.Vars.renderer;

public class UtilityProjector extends Block {
    public Seq<StatusEffect> statusEffects = new Seq<StatusEffect>();
    public float range = 60;
    Seq<Ability> abilities = new Seq<Ability>();
    TextureRegion topRegion = new TextureRegion();

    float shieldMaxHealth = 800;
    float shieldDownTime = 60;
    public UtilityProjector(String name)
    {
        super(name);
        solid = true;
        destructible = true;
        update = true;
        category = Category.effect;
        configurable = true;
        clearOnDoubleTap = true;
        group = BlockGroup.projectors;
        config(Integer.class,(UtilityProjectorBuild b,Integer i) -> {
            b.selectedEffect = i;
        });
        configClear((UtilityProjectorBuild b) -> {
            b.selectedEffect = -1;
        });
    }
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x,y,rotation,valid);
        float offset = (size-1)*4;
        Drawf.dashCircle(x*8+offset,y*8+offset,range,Pal.lighterOrange);
    }
    @Override
    public void init()
    {
        super.init();
        topRegion = Core.atlas.find(name+"-top");
    }
    public class UtilityProjectorBuild extends Building
    {
        Integer selectedEffect = -1;
        float smoothEff = 0;
        float waveEff = 0;
        float charge = 0;
        float realRange = 0;
        float shieldHealth = 0;
        float shieldDT = 0;
        float shieldEff = 0;
        float regenHalt = 0;
        boolean hit = false;

        boolean shieldUp = false;

        @Override
        public void created()
        {
            super.created();
            topRegion = Core.atlas.find(name+"-top");
        }

        @Override
        public void updateTile()
        {
            waveEff += edelta()/4f;
            realRange = range * smoothEff;
            if (selectedEffect > -1 && efficiency > 0)
            {
                charge+=edelta();
                smoothEff = Mathf.lerpDelta(smoothEff,efficiency,0.02f);
                shieldEff = Mathf.lerpDelta(shieldEff,shieldUp?1:0,0.02f);
                Groups.unit.forEach(u -> {
                    if (u.team == team && Mathf.len(u.x-x,u.y-y) <= realRange)
                        u.apply(statusEffects.get(selectedEffect),60f);
                });
                if (charge > 60) {
                    charge%=60;
                    if (statusEffects.get(selectedEffect) == PvStatusEffects.mend) {
                        indexer.eachBlock(this, realRange, b -> b.damaged() && !b.isHealSuppressed(), other -> {
                            other.heal(-PvStatusEffects.mend.damage * efficiency*60);
                            other.recentlyHealed();
                            Fx.healBlockFull.at(other.x, other.y, other.block.size, Pal.heal, other.block);
                        });
                    } else if (statusEffects.get(selectedEffect) == StatusEffects.overclock) {
                        indexer.eachBlock(this, realRange, other -> other.block.canOverdrive, other -> other.applyBoost(1.5f, 61f));
                    }
                }
                float sHPbefor = shieldHealth;
                if (shieldHealth > 0) {
                    if (statusEffects.get(selectedEffect) == PvStatusEffects.shield)
                        Groups.bullet.intersect(x - realRange, y - realRange, realRange * 2f, realRange * 2f, u -> {
                            if (u.team != team && Mathf.len(u.x - x, u.y - y) < realRange) {
                                if (shieldHealth < u.damage) {
                                    u.damage(u.damage - shieldHealth);
                                    shieldHealth = 0;
                                } else {
                                    shieldHealth -= u.damage;
                                    u.absorb();
                                }
                            }
                        });
                } else if (shieldUp)
                {
                    shieldUp = false;
                    shieldEff = 0;
                    Fx.unitShieldBreak.at(x,y);
                } else if (statusEffects.get(selectedEffect) == PvStatusEffects.shield) {
                    shieldDT = Mathf.approachDelta(shieldDT,shieldDownTime,1);
                    if (shieldDT >= shieldDownTime)
                    {
                        shieldDT = 0;
                        shieldHealth = 1;
                        shieldUp = true;
                    }
                }
                hit = sHPbefor != shieldHealth;
                if (hit) regenHalt = 60;
                if (shieldUp && !hit)
                {
                    if (regenHalt <= 0)
                        shieldHealth = Mathf.approachDelta(shieldHealth,shieldMaxHealth,1);
                    else
                        regenHalt = Mathf.approachDelta(regenHalt,0,1);
                }

            } else
            {
                smoothEff = Mathf.lerpDelta(smoothEff,0,0.02f);
            }
        }

        @Override
        public void drawSelect() {
            super.drawSelect();
            Drawf.dashCircle(x,y,realRange,Pal.lighterOrange);
        }

        @Override
        public void draw()
        {
            super.draw();
            Draw.z(Layer.blockBuilding+1);
            Draw.alpha(smoothEff);
            Draw.rect(topRegion,x,y);
            Color col = selectedEffect > -1 ? statusEffects.get(selectedEffect).color : Pal.gray;
            Draw.z(Layer.buildBeam-1);
            Draw.alpha(1);
            Lines.stroke(smoothEff,col);
            Lines.circle(x,y,range*smoothEff);
            int trail = 10;
            float length = realRange;
            for(int i = 0;i<trail;i++)
            {
                Lines.stroke(smoothEff,col.cpy().a((float)(trail-i)/trail));
                Lines.line(x,y,x+Mathf.cos((waveEff-i*0.2f)/2,Mathf.PI,length),y+Mathf.sin((waveEff-i*0.2f)/2,Mathf.PI,length));
            }
            if (selectedEffect == -1) return;
            if (statusEffects.get(selectedEffect) == PvStatusEffects.shield) drawShield();
            else shieldEff = 0;
        }

        public void drawShield(){
            if(shieldUp){
                float radius = range * shieldEff;

                Draw.z(Layer.shields);

                Draw.color(team.color, Color.white, Mathf.clamp(hit?1:0));

                if(renderer.animateShields){
                    Fill.poly(x, y, 15, radius);
                }else{
                    Lines.stroke(1.5f);
                    Draw.alpha(0.09f + Mathf.clamp(0.08f * (hit?1:0)));
                    Fill.poly(x, y, 15, radius);
                    Draw.alpha(1f);
                    Lines.poly(x, y, 15, radius);
                    Draw.reset();
                }
            }

            Draw.reset();
        }

        @Override
        public Object config()
        {
            return selectedEffect;
        }
        @Override
        public void buildConfiguration(Table table) {
            ItemSelection.buildTable(UtilityProjector.this, table, statusEffects,() -> selectedEffect > -1 ? statusEffects.get(selectedEffect) : null, icon -> configure(statusEffects.indexOf(icon)),selectionRows,selectionColumns);
        }
    }
}
