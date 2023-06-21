package viscott.types;

import arc.struct.Seq;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.units.UnitFactory;
import viscott.content.PvUnits;
import viscott.world.block.unit.PvTemplate;

public class PvUnitPlan extends UnitFactory.UnitPlan {
    public PvTemplate template = null;

    public PvUnitPlan(UnitType unitType, float time, ItemStack[] items) {
        super(unitType,time,items);
    }
    public PvUnitPlan(UnitType unitType, float time, ItemStack[] items, Block template) {
        super(unitType,time,items);
        if (template instanceof PvTemplate t)
            this.template = t;
    }
    public boolean needsTemplate() {
        return template != null;
    }
}
