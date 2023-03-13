package viscott.world.block.production;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.type.ItemStack;
import mindustry.world.blocks.power.VariableReactor;
import mindustry.world.consumers.Consume;
import mindustry.world.consumers.ConsumeItems;

public class ItemVariableReactor extends VariableReactor {
    public float itemDuration = 60;
    public Effect consumeEffect = Fx.none;
    public ItemVariableReactor(String name)
    {
        super(name);
    }

    public class ItemVariableReactorBuild extends VariableReactorBuild
    {
        float curItemC = 0;
        @Override
        public void updateTile(){
            if (efficiency >= 1)
            curItemC = Mathf.approachDelta(curItemC,1,1f/itemDuration * efficiency);
            heat = calculateHeat(sideHeat);

            productionEfficiency = efficiency;
            warmup = Mathf.lerpDelta(warmup, productionEfficiency > 0 ? 1f : 0f, warmupSpeed);

            if(curItemC >= 1)
            {
                for(Consume c : consumers)
                    if (c instanceof ConsumeItems ci)
                        for (ItemStack is : ci.items)
                            items.remove(is);
                consumeEffect.at(this.x,this.y,0);
                curItemC-=1;
            }
            if(instability >= 1f){
                kill();
            }

            totalProgress += productionEfficiency * Time.delta;

            if(Mathf.chanceDelta(effectChance * warmup)){
                effect.at(x, y, effectColor);
            }
        }

        @Override
        public void updateEfficiencyMultiplier(){
            //at this stage efficiency = how much coolant is provided

            //target efficiency value
            float target = Mathf.clamp(heat / maxHeat);

            //fraction of coolant provided (from what is needed)
            float efficiencyMet = Mathf.clamp(Mathf.zero(target) ? 1f : efficiency / target);
            boolean met = efficiencyMet >= 0.99999f;

            //if all requirements are met, instability moves toward 0 at 50% of speed
            //if requirements are not meant, instability approaches 1 at a speed scaled by how much efficiency is *not* met
            instability = Mathf.approachDelta(instability, met ? 0f : 1f, met ? 0.5f : unstableSpeed * (1f - efficiencyMet));

            //now scale efficiency by target, so it consumes less depending on heat
            efficiency *= target;
        }
    }
}
