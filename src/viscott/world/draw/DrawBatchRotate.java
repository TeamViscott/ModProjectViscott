package viscott.world.draw;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.g2d.Batch;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;

public class DrawBatchRotate extends Batch {
    public float rotation = 0;
    public float minZ = 0;
    Batch source;
    public DrawBatchRotate() {
        super();
        source = Core.batch;
    }

    @Override
    protected void draw(Texture texture, float[] spriteVertices, int offset, int count) {
        Core.batch = source;
        Draw.vert(texture,spriteVertices,offset,count);
        Core.batch = this;
    }

    public void inject() {
        source = Core.batch;
        Core.batch = this;
    }

    public void eject() {
        Core.batch = source;
    }

    @Override
    protected void setBlending(Blending blending){
        Core.batch = source;
        Draw.blend(blending);
        Core.batch = this;
    }

    @Override
    protected void setColor(float r, float g, float b, float a){
        Core.batch = source;
        Draw.color(r,g,b,a);
        Core.batch = this;
    }

    @Override
    protected void setColor(Color tint){
        Core.batch = source;
        Draw.color(tint);
        Core.batch = this;
    }
    @Override
    protected float getPackedColor(){
        Core.batch = source;
        Color c = Draw.getColor();
        float packed = c.toFloatBits();
        Core.batch = this;
        return packed;
    }
    @Override
    protected void setPackedColor(float packedColor){
        Core.batch = source;
        Draw.color(packedColor);
        Core.batch = this;
    }

    @Override
    protected void setPackedMixColor(float packedColor){
        Core.batch = source;
        Color c = new Color();
        c.abgr8888(packedColor);
        Draw.mixcol(c,c.a);
        Core.batch = this;

    }

    @Override
    protected void z(float z){
        Core.batch = source;
        Draw.z(Math.max(z,minZ));
        Core.batch = this;
    }

    @Override
    protected void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float rotation) {
        Core.batch = source;
        Draw.rect(region,x+width/2f,y+height/2f,width,height,width/2,height/2,rotation + this.rotation);
        Core.batch = this;
    }

    @Override
    protected void flush() {
        Core.batch = source;
        Draw.flush();
        Core.batch = this;
    }
}
