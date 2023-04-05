package viscott.world.block.sandbox;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.game.Gamemode;
import mindustry.graphics.Pal;
import mindustry.world.blocks.sandbox.ItemSource;

import static mindustry.Vars.state;
import static mindustry.Vars.tilesize;

public class PvItemSource extends ItemSource {
    public boolean sandboxEditOnly = false;
    public PvItemSource(String name)
    {
        super(name);
        configClear((PvLiquidSource.PvLiquidSourceBuild b) -> {
            if (b.canConfig())
                b.source = null;
        });
    }

    @Override
    protected TextureRegion[] icons(){
        return new TextureRegion[]{Core.atlas.find(name+"-bottom"), region};
    }

    public class PvItemSourceBuild extends ItemSourceBuild
    {
        @Override
        public void draw(){
            if(outputItem == null){
                Draw.rect(name+"-bottom",x,y);
                Draw.rect("cross-full", x, y);
            }else{
                Draw.color(outputItem.color);
                Fill.square(x, y, (tilesize/2f - 0.00001f)*size);
                Draw.color();
            }

            super.draw();
        }
        @Override
        public void buildConfiguration(Table table) {
            if (canConfig())
                super.buildConfiguration(table);
        }
        public boolean canConfig() {return !sandboxEditOnly || (state.rules.mode() == Gamemode.sandbox || state.rules.editor);}
    }
}
