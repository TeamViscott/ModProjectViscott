package viscott.types.weathers;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.gen.Groups;
import mindustry.gen.WeatherState;
import mindustry.type.Weather;
import viscott.utilitys.PvUtil;

import static arc.Core.camera;
import static mindustry.Vars.renderer;

public class DangerWeather extends Weather {
    TextureRegion region;
    public DangerWeather(String name)
    {
        super(name);
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
        if (state.life < 0)
            Groups.unit.copy(new Seq<>()).each(u->u.damage(u.maxHealth * state.intensity));
    }
}
