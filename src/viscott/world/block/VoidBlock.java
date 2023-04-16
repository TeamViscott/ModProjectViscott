package viscott.world.block;

import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.Building;
import viscott.content.PvTeams;
import viscott.types.PvTeam;
import viscott.world.chips.VoidArea;

import static mindustry.Vars.state;

public class VoidBlock extends PvBlock{
    public float voidRadius = 1;
    public VoidBlock(String name)
    {
        super(name);
        update = true;
    }

    @Override
    public void init()
    {
        updateClipRadius(voidRadius*8);
        super.init();
    }

    public class VoidBuilding extends Building implements VoidArea
    {
        @Override
        public void updateTile()
        {
            super.updateTile();
            updateVoid(this,8*voidRadius);
        }

        @Override
        public void draw()
        {
            super.draw();
            drawVoid(this,8*voidRadius);
        }
    }
}
