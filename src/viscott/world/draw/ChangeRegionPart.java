package viscott.world.draw;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.entities.part.DrawPart;
import mindustry.entities.part.RegionPart;

import java.util.Iterator;

public class ChangeRegionPart extends RegionPart {
    public ChangeRegionPart(String prefix) {
        super(prefix);
    }
    public boolean lifeEnabled = false;
    public int parts = 1;
    public String unitName = "";

    @Override
    public void draw(DrawPart.PartParams params) {
        int part = Mathf.round(parts*progress.getClamp(params));
        if (part > 0) {
            float prevZ = Draw.z();
            float z = Draw.z();
            if (this.layer > 0.0F) {
                Draw.z(this.layer);
            }

            if (this.under && this.turretShading) {
                Draw.z(z - 1.0E-4F);
            }

            Draw.z(Draw.z() + this.layerOffset);
            Draw.rect(unitName + this.suffix + "-" + part,params.x,params.y,params.rotation-90);
            Draw.z(prevZ);
        }
    }

    @Override
    public void load(String name) {
        unitName = name != null ? name : this.name;
        super.load(name);
    }
}
