package viscott.utilitys;

import mindustry.gen.Iconc;

public class PvUtil {
    public static float GetRange(float speed,float DesiredRange)
    {
        return DesiredRange/(speed/8f);
    }
    public static String GetName(String added)
    {
        return "project-viscott-"+added;
    }
}
