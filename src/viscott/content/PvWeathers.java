package viscott.content;

import mindustry.type.Weather;
import viscott.types.weathers.DangerWeather;

public class PvWeathers {
    public static Weather
        danger
            ;
    public static void load()
    {
        danger = new DangerWeather("danger")
        {{

        }};
    }
}
