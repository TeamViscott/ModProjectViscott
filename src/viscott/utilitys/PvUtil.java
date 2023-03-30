package viscott.utilitys;

import mindustry.gen.Iconc;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class PvUtil {
    public static float GetRange(float speed,float DesiredRange)
    {
        return (DesiredRange*8)/speed;
    }
    public static String GetName(String added)
    {
        return "project-viscott-"+added;
    }
    public static float GetDamage(float DPS, float fireRate){
        return DPS/fireRate;
    }
}
