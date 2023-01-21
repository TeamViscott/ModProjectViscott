package viscott.content;

import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;

public class PvStats {
    public static Stat
    maxDamage,maxDamageMultiplier,maxHealthMultiplier,maxSpeedMultiplier,maxReloadSpeedMultiplier, maxBuildSpeedMultiplier,maxCharges,maxHealing,dragMultiplier,maxDragMultiplier,newTeam
            ;
    public static void load()
    {
        dragMultiplier = new Stat("dragMultiplier",StatCat.function);
        maxDamage = new Stat("maxDamage",StatCat.function);
        maxDamageMultiplier = new Stat("maxDamageMultiplier",StatCat.function);
        maxHealthMultiplier = new Stat("MaxHealthMultiplier",StatCat.function);
        maxSpeedMultiplier = new Stat("maxSpeedMultiplier",StatCat.function);
        maxReloadSpeedMultiplier = new Stat("maxReloadMultiplier",StatCat.function);
        maxBuildSpeedMultiplier = new Stat("maxBuildSpeedMultiplier",StatCat.function);
        maxDragMultiplier = new Stat("maxDragMultiplier",StatCat.function);
        maxHealing = new Stat("maxHealing",StatCat.function);
        maxCharges = new Stat("maxCharges",StatCat.function);
        newTeam = new Stat("newTeam",StatCat.function);
    }
}
