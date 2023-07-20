package viscott.content;

import mindustry.type.SectorPreset;

public class PvSectorPresets{
    public static SectorPreset xealTestmap;
    public static void load() {
        xealTestmap = new SectorPreset("echosOfResiliantReach",PvPlanets.vercilus,15) {{
            localizedName = "[green]Echos of the Resiliant Reach";
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 50;
            difficulty = 1;
            overrideLaunchDefaults = true;
            noLighting = true;
            startWaveTimeMultiplier = 2f;

        }};
    }
}
