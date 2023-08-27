package viscott.content;

import mindustry.type.Weather;
import viscott.types.weathers.FleetWeather;

public class PvWeathers {
    public static Weather
        danger
            ;
    public static void load()
    {
        danger = new FleetWeather("danger")
        {{

        }};
    }
}
