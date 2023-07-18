package viscott.world.draw;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.util.Log;
import arc.util.Tmp;
import mindustry.content.Liquids;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.draw.DrawLiquidRegion;

import static mindustry.Vars.renderer;
import static mindustry.Vars.tilesize;

public class DrawLiquidStaticRegion extends DrawLiquidRegion {
    public float padding = 0;
    public DrawLiquidStaticRegion(Liquid drawLiquid){
        this.drawLiquid = drawLiquid;
    }


    @Override
    public void draw(Building build){
        TextureRegion region = renderer.fluidFrames[drawLiquid.gas ? 1 : 0][drawLiquid.getAnimationFrame()];
        TextureRegion toDraw = Tmp.tr1;
        int size = build.block().size;
        float leftBounds = size/2f * tilesize - padding;
        float bottomBounds = size/2f * tilesize - padding;
        Color color = Tmp.c1.set(drawLiquid.color).a(1f);

        for(int sx = 0; sx < size; sx++){
            for(int sy = 0; sy < size; sy++){
                float relx = sx - (size-1)/2f, rely = sy - (size-1)/2f;

                toDraw.set(region);

                //truncate region if at border
                float rightBorder = relx*tilesize + padding,
                        topBorder = rely*tilesize + padding;
                float squishX = rightBorder + tilesize/2f - leftBounds, squishY = topBorder + tilesize/2f - bottomBounds;
                float ox = 0f, oy = 0f;

                if(squishX >= 8 || squishY >= 8) continue;

                //cut out the parts that don't fit inside the padding
                if(squishX > 0){
                    toDraw.setWidth(toDraw.width - squishX * 4f);
                    ox = -squishX/2f;
                }

                if(squishY > 0){
                    toDraw.setY(toDraw.getY() + squishY * 4f);
                    oy = -squishY/2f;
                }

                Drawf.liquid(toDraw, build.x + rightBorder + ox, build.y + topBorder + oy, alpha, color);
            }
        }
    }

    @Override
    public void load(Block block){
    }
}
