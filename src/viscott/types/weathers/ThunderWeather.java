package viscott.types.weathers;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.audio.SoundControl;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.WeatherState;
import mindustry.type.weather.RainWeather;
import mindustry.world.Tile;

public class ThunderWeather extends RainWeather {
    float thunderAlpha;
    public float thunderDamage = 20;
    public Effect thunderEffect = new MultiEffect(Fx.explosion,Fx.smokeCloud,Fx.freezing,Fx.hitLaser);
    public ThunderWeather(String name) {
        super(name);
        density = 3000;
    }
    @Override
    public void update(WeatherState state)
    {
        super.update(state);
        thunderAlpha = Mathf.lerp(thunderAlpha,1,0.1f);
        if (thunderAlpha >= 0.99 && Mathf.randomBoolean(0.02f)) {
            thunderAlpha = 0;
            if (Vars.net.client()) return; //Server exclusive code.
            int size = Vars.world.tiles.height * Vars.world.tiles.width;
            Tile t = Vars.world.tiles.geti(Mathf.random(size));
            thunderEffect.at(t);
            if (t.build != null)
                t.build.damage(thunderDamage);
            Units.nearby(t.worldx(),t.worldy(),8,8,(u)->{u.damage(thunderDamage);});
        }
    }

    @Override
    public void drawOver(WeatherState state){
        Draw.color(Color.black);
        Draw.alpha(0.1f);
        Draw.rect();
        Draw.alpha(1);
        drawRain(sizeMin, sizeMax, xspeed, yspeed, density*2, state.intensity, stroke, color);
        Draw.color(Color.white);
        Draw.alpha(1-thunderAlpha);
        Draw.rect();
    }

    @Override
    public void drawUnder(WeatherState state){
        drawSplashes(splashes, sizeMax, density*2, state.intensity, state.opacity, splashTimeScale, stroke, color, liquid);
    }
}
