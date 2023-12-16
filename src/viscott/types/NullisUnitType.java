package viscott.types;

import arc.graphics.Color;
import mindustry.Vars;
import mindustry.entities.part.DrawPart;
import mindustry.game.Team;
import mindustry.gen.Unit;
import viscott.content.PvEffects;
import viscott.content.PvFactions;
import viscott.world.draw.ChangeRegionPart;

public class NullisUnitType extends PvUnitType{
    public NullisUnitType(String name)
    {
        super(name);
        healColor = Color.black;
        factions.add(PvFactions.Nullis);
    }
    @Override
    public Unit create(Team team){
        deathEffect();
        return super.create(team);
    }

    public void deathEffect()
    {
        int ind = (int)((hitSize-1)/8);
        deathExplosionEffect = PvEffects.nullisDeath.get(Math.min(ind,PvEffects.nullisDeath.size-1));
    }
}
