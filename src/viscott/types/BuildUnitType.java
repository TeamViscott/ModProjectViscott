package viscott.types;

import arc.graphics.g2d.Draw;
import mindustry.gen.Payloadc;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.payloads.Payload;

public class BuildUnitType extends PvUnitType{
    public BuildUnitType(String name)
    {
        super(name);
    }

    @Override
    public <T extends Unit & Payloadc> void drawPayload(T unit){
        if(unit.hasPayload()){
            Payload pay = unit.payloads().first();
            pay.set(unit.x, unit.y, unit.rotation);
            Draw.z(Layer.flyingUnit + 5);
            if (false)
            {

            }
            else
                Draw.rect(pay.content().fullIcon, unit.x, unit.y, unit.rotation);
            Draw.z(Layer.flyingUnitLow);
        }
    }
}
