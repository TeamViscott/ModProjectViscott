package viscott.world.block.effect;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Structs;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.Item;
import mindustry.world.blocks.storage.CoreBlock;
import viscott.content.PvEffects;
import viscott.content.PvFactions;
import viscott.content.PvStatusEffects;
import viscott.types.PvFaction;
import viscott.world.bullets.VoidBulletType;
import viscott.world.chips.VoidArea;

import static mindustry.Vars.*;

public class NullisCore extends PvCore {
    public float voidRadius = 5;
    public NullisCore(String name)
    {
        super(name);
        faction.add(PvFactions.Nullis);
    }

    @Override
    public void init()
    {
        updateClipRadius(voidRadius*8);
        super.init();
    }
    public class NullisCoreBlock extends PvCoreBuild implements VoidArea {
        public float pulsing = 0;
        @Override
        public void updateTile()
        {
            pulsing = Mathf.approachDelta(pulsing,1,0.01f);
            pulsing%=1;
            super.updateTile();
            Groups.unit.forEach(unit ->
                    {
                        if (unit.team == team)
                            if (unit.stack != null && unit.stack.amount > 0)
                                transferItems(unit);
                    }
            );
            updateVoid(this,8*voidRadius);
        }

        @Override
        public int getMaximumAccepted(Item item){
            return storageCapacity;
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
            super.draw();
            drawVoid(this,8*voidRadius);
        }
        @Override
        public void requestSpawn(Player player)
        {
            super.requestSpawn(player);
            PvEffects.nullisDeath.get(size-1).at(player);
        }
    }
}
