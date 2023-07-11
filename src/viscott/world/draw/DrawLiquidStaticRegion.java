package viscott.world.draw;

import arc.Core;
import arc.util.Log;
import mindustry.content.Liquids;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.draw.DrawLiquidRegion;

public class DrawLiquidStaticRegion extends DrawLiquidRegion {
    public DrawLiquidStaticRegion(Liquid drawLiquid){
        this.drawLiquid = drawLiquid;
    }

    @Override
    public void draw(Building build){
        Drawf.liquid(liquid, build.x, build.y,
                alpha,
                drawLiquid.color
        );
    }

    @Override
    public void load(Block block){
        liquid = Core.atlas.find(block.name + suffix);
        Log.info(liquid.toString(),0);
    }
}
