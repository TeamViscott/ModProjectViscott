package viscott.world.block.sandbox;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import mindustry.graphics.Pal;
import mindustry.world.blocks.sandbox.ItemSource;

import static mindustry.Vars.tilesize;

public class PvItemSource extends ItemSource {
    public PvItemSource(String name)
    {
        super(name);
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
                Draw.color(Pal.gray);
                Fill.square(x, y, (tilesize/2f - 0.00001f)*size);
                Draw.color();
                Draw.rect("cross-full", x, y);
            }else{
                Draw.color(outputItem.color);
                Fill.square(x, y, (tilesize/2f - 0.00001f)*size);
                Draw.color();
            }

            super.draw();
        }
    }
}
