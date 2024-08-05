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
import viscott.world.block.drill.Grinder;

import java.util.HashMap;

public class FleetWeather extends Weather {
    TextureRegion region;
    public Seq<UnitType> fleet = new Seq<>();
    public int fleetSize = 5;

    public class UPosition {
        UnitType unitType;
        float x,y,rotation;

        float h = 2;

        public UPosition(UnitType unitType) {
            this.unitType = unitType;
            x = 0;
            y = 0;
            rotation = 0;
        }
        public float alpha() {
            return Math.abs(h - 1);
        }
    }
    HashMap<Team,Seq<UPosition>> outerFleets;
    boolean spawnUnit = true;
    public FleetWeather(String name)
    {
        super(name);
        outerFleets = new HashMap<>();
    }

    @Override
    public WeatherState create(float intensity, float duration) {
        spawnUnit = true;
        outerFleets.clear();
        Vars.state.teams.getActive().each(td -> {
            Seq<UPosition> curFleet = new Seq<>();
            outerFleets.put(td.team,curFleet);
            for(int i = 0;i<fleetSize;i++)
                curFleet.add(newRandomFleet());
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
        //Draw.color(Color.red.cpy().a(Mathf.sin(state.life/60) * 0.2f + 0.4f));
        //Draw.rect();
        Draw.color(0,0,0,0.1f);

        for (var team : outerFleets.keySet()) {
            var curFleet = outerFleets.get(team);
            for (var upos : curFleet) {
                Draw.rect(upos.unitType.fullIcon,upos.x,upos.y,upos.rotation);
            }
        }
    }
    @Override
    public void update(WeatherState state)
    {
        super.update(state);
        if (state.life < 0) {
            state.life(1);
            spawnUnit = false;
        }
        if (spawnUnit) {
            for(var team : outerFleets.keySet()) {
                var curFleet = outerFleets.get(team);
                if (curFleet.size > fleetSize)
                    curFleet.add(newRandomFleet());
            }
        }


    }

    public UPosition newRandomFleet() {
        var pos = new UPosition(fleet.random());
        pos.x = Mathf.random( Vars.world.width() * 8);
        pos.y = Mathf.random( Vars.world.height() * 8);
        pos.rotation = Mathf.random(0,359);
        return pos;
    }
}
