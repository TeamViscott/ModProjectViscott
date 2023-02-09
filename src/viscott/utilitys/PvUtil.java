package viscott.utilitys;

import mindustry.gen.Iconc;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class PvUtil {
    public static float GetRange(float speed,float DesiredRange)
    {
        return DesiredRange/(speed/8f);
    }
    public static String GetName(String added)
    {
        return "project-viscott-"+added;
    }

    public static class DoubleIntStack
    {
        public DoubleIntStack(int i1,int i2)
        {
            this.i1 = i1;
            this.i2 = i2;
        }
        public int i1 = 0;
        public int i2 = 0;
    }
}
