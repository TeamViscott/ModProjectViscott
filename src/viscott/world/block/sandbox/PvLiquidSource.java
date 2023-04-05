package viscott.world.block.sandbox;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import mindustry.game.Gamemode;
import mindustry.graphics.Pal;
import mindustry.world.blocks.liquid.LiquidBlock;
import mindustry.world.blocks.sandbox.LiquidSource;
import viscott.utilitys.PvUtil;

import static mindustry.Vars.state;
import static mindustry.Vars.tilesize;

public class PvLiquidSource extends LiquidSource {
    public boolean sandboxEditOnly = false;
    public PvLiquidSource(String name)
    {
        super(name);
        configClear((PvLiquidSourceBuild b) -> {
            if (b.canConfig())
                b.source = null;
        });
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{Core.atlas.find(name+"-bottom"), region};
    }

    public class PvLiquidSourceBuild extends LiquidSourceBuild
    {
        @Override
        public void draw(){

            super.draw();

            Draw.rect(Core.atlas.find(name+"-bottom"), x, y);
            if(source == null){
                Draw.rect(crossRegion, x, y);
            }else{
                LiquidBlock.drawTiledFrames(size, x, y, 0f, source, 1f);
            }

            Draw.rect(block.region, x, y);
        }

        @Override
        public void buildConfiguration(Table table) {
            if (canConfig())
                super.buildConfiguration(table);
        }

        public boolean canConfig() {return !sandboxEditOnly || (state.rules.mode() == Gamemode.sandbox || state.rules.editor);}
    }
}
