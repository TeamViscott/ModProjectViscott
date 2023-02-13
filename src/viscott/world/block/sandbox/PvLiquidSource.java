package viscott.world.block.sandbox;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import mindustry.graphics.Pal;
import mindustry.world.blocks.liquid.LiquidBlock;
import mindustry.world.blocks.sandbox.LiquidSource;
import viscott.utilitys.PvUtil;

import static mindustry.Vars.tilesize;

public class PvLiquidSource extends LiquidSource {
    public PvLiquidSource(String name)
    {
        super(name);
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
    }
}
