package viscott.world.block.effect;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Strings;
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
import viscott.utilitys.PvUtil;

import javax.swing.text.AbstractDocument;

public class UtilityProjector extends Block {
    public Seq<StatusEffect> statusEffects = new Seq<StatusEffect>();
    public float range = 60;
    Seq<Ability> abilities = new Seq<Ability>();
    TextureRegion topRegion = new TextureRegion();
    public UtilityProjector(String name)
    {
        super(name);
        solid = true;
        destructible = true;
        update = true;
        category = Category.effect;
        configurable = true;
        clearOnDoubleTap = true;

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

            if (selectedEffect > -1 && efficiency > 0)
            {
                smoothEff = Mathf.lerpDelta(smoothEff,efficiency,0.02f);
                Groups.unit.forEach(u -> {
                    if (u.team == team && Mathf.len(u.x-x,u.y-y) <= range * smoothEff)
                        u.apply(statusEffects.get(selectedEffect),60f);
                });
            } else
            {
                smoothEff = Mathf.lerpDelta(smoothEff,0,0.02f);
            }
        }

        @Override
        public void drawSelect() {
            super.drawSelect();
            Drawf.dashCircle(x,y,range * smoothEff,Pal.lighterOrange);
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
            float length = range * smoothEff;
            for(int i = 0;i<trail;i++)
            {
                Lines.stroke(smoothEff,col.cpy().a((float)(trail-i)/trail));
                Lines.line(x,y,x+Mathf.cos((waveEff-i*0.2f)/2,Mathf.PI,length),y+Mathf.sin((waveEff-i*0.2f)/2,Mathf.PI,length));
            }
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
