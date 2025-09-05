package viscott.content;

import mindustry.content.StatusEffects;
import mindustry.content.Weathers;
import mindustry.gen.Sounds;
import mindustry.type.StatusEffect;
import mindustry.type.Weather;
import mindustry.world.meta.Attribute;
import viscott.types.weathers.FleetWeather;
import viscott.types.weathers.NewSnowWeather;
import viscott.types.weathers.ThunderWeather;

public class PvWeathers {
    public static Weather
        fleet,thunder,newSnow
            ;
    public static void load()
    {
        fleet = new FleetWeather("fleet")
        {{
            localizedName = "Fleet";
            fleet.add(
                    PvUnits.amp,
                    PvUnits.branch,
                    PvUnits.centi,
                    PvUnits.puppet
            );
            fleetSize = 25;
        }};
        thunder = new ThunderWeather("thunder") {{
            localizedName = "Thunder";
        }};
        newSnow = new NewSnowWeather("new-snow") {{
            localizedName = "Snow [orange](New)[]";
            particleRegion = "particle";
            sizeMax = 13f;
            sizeMin = 2.6f;
            density = 1200f;
            attrs.set(Attribute.light, -0.15f);

            sound = Sounds.windhowl;
            soundVol = 0f;
            soundVolOscMag = 1.5f;
            soundVolOscScl = 1100f;
            soundVolMin = 0.02f;
        }};
    }
}
