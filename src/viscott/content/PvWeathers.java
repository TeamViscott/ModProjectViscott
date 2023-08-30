package viscott.content;

import mindustry.type.Weather;
import viscott.types.weathers.FleetWeather;
import viscott.types.weathers.ThunderWeather;

public class PvWeathers {
    public static Weather
        fleet,thunder
            ;
    public static void load()
    {
        fleet = new FleetWeather("fleet")
        {{

        }};
        thunder = new ThunderWeather("thunder") {{

        }};
    }
}
