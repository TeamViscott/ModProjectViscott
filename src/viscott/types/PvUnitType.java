package viscott.types;

import arc.graphics.Color;
import mindustry.type.UnitType;

import java.awt.*;

public class PvUnitType extends UnitType {
    public PvUnitType(String name)
    {
        super(name);
        outlineColor = Color.valueOf("#181a1b");
    }
}
