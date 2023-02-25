package viscott.content;

import mindustry.world.meta.Attribute;

public class PvAttributes {
    public static Attribute
        Deposit,power
            ;
    public static void load()
    {
        Deposit = Attribute.add("deposit");
        power = Attribute.add("power");
    }
}
