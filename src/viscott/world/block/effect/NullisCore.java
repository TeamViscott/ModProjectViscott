package viscott.world.block.effect;

import arc.Core;
import arc.Events;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.scene.ui.layout.Scl;
import arc.struct.EnumSet;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.UnitTypes;
import mindustry.core.UI;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.Damage;
import mindustry.entities.TargetPriority;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Env;
import mindustry.world.meta.Stat;
import mindustry.world.modules.ItemModule;
import viscott.content.PvEffects;
import viscott.content.PvStatusEffects;

import static mindustry.Vars.*;
import static mindustry.Vars.state;

public class NullisCore extends CoreBlock {
    public float voidRadius = 5;
    public float voidDamage = 2;
    public NullisCore(String name)
    {
        super(name);
    }

    @Override
    public void init()
    {
        updateClipRadius(voidRadius*8);
        super.init();
    }
    public class NullisCoreBlock extends CoreBuild {
        public float pulsing = 0;
        @Override
        public void updateTile()
        {
            pulsing = Mathf.approachDelta(pulsing,1,0.01f);
            pulsing%=1;
            super.updateTile();
            Groups.unit.forEach(unit ->
                    {
                        if (unit.team == team) {
                            if (unit.stack != null && unit.stack.amount > 0)
                                transferItems(unit);
                            if (Mathf.len(x-unit.x,y-unit.y) <= 8*voidRadius) {
                                unit.buildSpeedMultiplier*= 10;
                                unit.apply(PvStatusEffects.voidShield,30);
                            }
                        } else {
                            if (Mathf.len(x-unit.x,y-unit.y) <= 8*voidRadius) {
                                unit.damage(voidDamage);

                            }
                        }
                    }
            );
        }

        public void transferItems(Unit unit)
        {
            if (items.get(unit.stack.item) < storageCapacity) {
                handleItem(this,unit.item());
                unit.stack.amount-=1;
            }
        }

        @Override
        public void draw()
        {
            float wave = Mathf.sin(pulsing*Mathf.pi);
            float revWave = Math.abs(Mathf.cos(pulsing*Mathf.pi));
            super.draw();
            Draw.z(Layer.block-1);
            Draw.color(Color.black.cpy().a(0.4f));
            Fill.circle(x,y,8*voidRadius);
            Draw.color(Color.black.cpy().a(0.5f*revWave));
            Fill.circle(x,y,8*voidRadius*wave);
            Draw.z(Layer.effect);
            Lines.stroke(1+wave,Color.white);
            Lines.circle(x,y,8*voidRadius);
            int spikes = 20;
            float voidRad = voidRadius*8;
            float prog = Mathf.sin(pulsing*2*Mathf.pi);
            for(int i = 0;i < spikes ;i++)
            {
                float acW = (prog + (float)i/(float)spikes) % 1;
                float radian = acW*Mathf.pi*2;
                float sx = x + Mathf.cos(radian)*voidRad;
                float sy = y + Mathf.sin(radian)*voidRad;
                acW = (prog + (float)(i+1)/(float)spikes) % 1;
                radian = acW*Mathf.pi*2;
                float sx2 = x + Mathf.cos(radian)*(voidRad+4);
                float sy2 = y + Mathf.sin(radian)*(voidRad+4);
                Lines.stroke(wave,Color.white);
                Lines.line(sx,sy,sx2,sy2);
            }
        }
        @Override
        public void requestSpawn(Player player)
        {
            super.requestSpawn(player);
            PvEffects.nullisDeath.get(size-1).at(player);
        }
    }
}
