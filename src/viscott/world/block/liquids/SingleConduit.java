package viscott.world.block.liquids;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.scene.ui.layout.Table;
import arc.util.Tmp;
import mindustry.content.Liquids;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.logic.LAccess;
import mindustry.type.Liquid;
import mindustry.world.blocks.liquid.Conduit;
import viscott.utilitys.PvUtil;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class SingleConduit extends Conduit {
    public float lightRange = 0;
    public float brightness = 0;
    public Liquid liquid = Liquids.water;
    public SingleConduit(String name)
    {
        super(name);
    }

    @Override
    public void init()
    {
        super.init();
        lightRadius = lightRange*3f;
        emitLight = true;
    }
    public class SingleConduitBuild extends ConduitBuild
    {
        public float smoothTime = 1f;

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            noSleep();
            return (SingleConduit.this.liquid == liquid || liquids.currentAmount() < 0.2f)
                    && (tile == null || (source.relativeTo(tile.x, tile.y) + 2) % 4 != rotation);
        }

        @Override
        public void draw(){
            super.draw();
            Draw.blend(Blending.additive);
            Draw.color(Tmp.c1.set(liquid.color), efficiency * 0.3f);
            Draw.rect(Core.atlas.find(name+"-" + blending +"-top"),x,y,rotation*90);
            Draw.color();
            Draw.blend();
        }

        @Override
        public void updateTile(){
            smoothTime = Mathf.lerpDelta(smoothTime, timeScale, 0.1f);
            super.updateTile();
        }

        @Override
        public void drawLight(){
            brightness = liquids.get(liquid) / liquidCapacity;
            Drawf.light(x, y, lightRadius * Math.min(smoothTime, 2f), Tmp.c1.set(liquid.color), brightness * efficiency);
        }
    }
}
