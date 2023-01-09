package viscott.types.weathers;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.gen.WeatherState;
import mindustry.type.Weather;
import viscott.utilitys.PvUtil;

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
    public void update(WeatherState state)
    {
        float x = Core.camera.position.x-Core.graphics.getWidth() / renderer.minScale(), y = Core.camera.position.y-Core.graphics.getHeight() / renderer.minScale();
        Draw.rect(region,x,y,Core.graphics.getWidth() / renderer.minScale(),Core.graphics.getHeight() / renderer.minScale(),0);
    }
}
