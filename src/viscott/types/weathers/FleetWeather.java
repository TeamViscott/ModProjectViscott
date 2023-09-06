package viscott.types.weathers;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.gen.WeatherState;
import mindustry.type.UnitType;
import mindustry.type.Weather;
import viscott.utilitys.PvUtil;

import java.util.HashMap;

public class FleetWeather extends Weather {
    TextureRegion region;
    public Seq<UnitType> fleet = new Seq<>();
    public int fleetSize = 5;
    HashMap<Team,Seq<UnitType>> outerFleets;
    boolean spawnUnit = true;
    public FleetWeather(String name)
    {
        super(name);
    }

    @Override
    public WeatherState create(float intensity, float duration) {
        outerFleets.clear();
        Vars.state.teams.getActive().each(td -> {
            Seq<UnitType> curFleet = new Seq<>();
            outerFleets.put(td.team,curFleet);
            for(int i = 0;i<fleetSize;i++)
                curFleet.add(fleet.random());
        });

        return super.create(intensity,duration);
    }
    @Override
    public void load()
    {
        if (region == null)
            region = Core.atlas.find(PvUtil.GetName(name));
    }

    @Override
    public void drawOver(WeatherState state){
        Draw.color(Color.red.cpy().a(Mathf.sin(state.life/60) * 0.2f + 0.4f));
        Draw.rect();
    }
    @Override
    public void update(WeatherState state)
    {
        super.update(state);
        if (state.life < 0) {
            state.life(1);
            spawnUnit = false;
        }
    }
}
