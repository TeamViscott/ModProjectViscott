package viscott.world.block.effect;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.world.blocks.storage.CoreBlock;
import viscott.content.PvEffects;
import viscott.content.PvStatusEffects;
import viscott.world.bullets.VoidBulletType;
import viscott.world.chips.VoidArea;

import static mindustry.Vars.*;

public class NullisCore extends CoreBlock {
    public float voidRadius = 5;

    public Team visibleTeam = null;
    public NullisCore(String name)
    {
        super(name);
    }

    @Override
    public boolean isVisible(){
        return (visibleTeam == null ? true : Vars.player.team() == visibleTeam) && !isHidden() && (state.rules.editor || (!state.rules.hideBannedBlocks || !state.rules.isBanned(this)));
    }

    @Override
    public void init()
    {
        updateClipRadius(voidRadius*8);
        super.init();
    }
    public class NullisCoreBlock extends CoreBuild implements VoidArea {
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
