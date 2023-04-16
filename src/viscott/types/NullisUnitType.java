package viscott.types;

import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.TimedKillc;
import mindustry.gen.Unit;
import viscott.content.PvEffects;
import viscott.content.PvTeams;

public class NullisUnitType extends PvUnitType{
    public NullisUnitType(String name)
    {
        super(name);
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

    @Override
    public boolean unlockedNow()
    {
        return Vars.player.team() == PvTeams.Nullis && super.unlockedNow();
    }
}
