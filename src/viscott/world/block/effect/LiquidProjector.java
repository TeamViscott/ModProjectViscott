package viscott.world.block.effect;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.core.World;
import mindustry.entities.Fires;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.type.StatusEffect;
import mindustry.world.Tile;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawMulti;
import viscott.world.block.PvBlock;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;


public class LiquidProjector extends PvBlock {
    public float range = 20*8;
    public DrawBlock drawer = new DrawDefault();
    public float activeDrain = 1;

    public Seq<Liquid> liquidsAllowed = Vars.content.liquids();

    public void init() {
        super.init();
        drawer.load(this);
    }
    public LiquidProjector(String name) {
        super(name);
        update = true;
        liquidCapacity = 10;
        hasLiquids = true;
    }
    public void drawPlace(int x,int y,int rotation,boolean valid) {
        super.drawPlace(x,y,rotation,valid);
        Lines.stroke(1);
        Drawf.dashCircle(x*8,y*8,range,valid ? Pal.placing : Pal.noplace);
    }

    public class LiquidProjectorBuild extends Building {
        float extinguishI = 0;
        @Override
        public boolean acceptLiquid(Building source,Liquid liquid) {
            return (liquids.currentAmount() <= 0 || liquids.current() == liquid) && liquidCapacity > liquids.currentAmount();
        }
        @Override
        public void draw() {
            drawer.draw(this);
        }
        @Override
        public float warmup() {
            return efficiency;
        }
        @Override
        public void drawSelect() {
            super.drawSelect();
            Lines.stroke(1);
            Drawf.dashCircle(x,y,range,Pal.placing);
        }
        @Override
        public void update() {
            if (liquids.current() == null || liquids.currentAmount() <= 0 || (power != null && power.status == 0)) {
                efficiency = Mathf.lerpDelta(efficiency,0,0.1f);
                if (extinguishI < 60)
                    extinguishI += 0.5f;
                return;
            }
            boolean activate = false;
            Liquid current = liquids.current();
            boolean dangerous = current.gas || current.viscosity > 0.5;
            boolean canExtinguish = current.canExtinguish();
            StatusEffect givenEffect = current.effect;
            if (canExtinguish) { // Activate when fire.
                int tx = World.toTile(x), ty = World.toTile(y);
                int tr = (int)(range / tilesize);
                for(int x = -tr; x <= tr; x++){
                    for(int y = -tr; y <= tr; y++){
                        Tile other = world.tile(x + tx, y + ty);
                        var fire = Fires.get(x + tx, y + ty);
                        float dst = fire == null ? 0 : dst2(fire);
                        //do not extinguish fires on other team blocks
                        if(other != null && fire != null && Fires.has(other.x, other.y) && dst <= range * range && (other.build == null || other.team() == team)){
                            activate = true;
                        }
                        if (extinguishI >= 60)
                            Fires.extinguish(other,(1-current.temperature)*efficiency);
                    }
                }
            }
            if (dangerous && !activate) { // Activate when enemy nearby.
                activate = Units.anyEntities(x-range,y-range,range*2,range*2,(unit)->{
                    if (unit.team() == team || range <= Mathf.len(unit.x-x,unit.y-y)) return false;
                    return true;
                });
            }

            if (activate) {
                if (extinguishI >= 60) {
                    extinguishI %= 60;
                    for(int i = 0;i<60;i++) {
                        float vx = Mathf.sinDeg(i*6)*range;
                        float vy = Mathf.sinDeg(i*6+90)*range;
                        current.vaporEffect.at(x+vx,y+vy,current.color);
                    }
                }
                float max = power == null ? 1 : power.status;
                extinguishI += efficiency;
                efficiency = Mathf.lerpDelta(efficiency,max,0.1f);
                liquids.remove(current,efficiency/60 * activeDrain);
                Units.nearby(x-range,y-range,range*2,range*2,(unit) -> {
                    if (range <= Mathf.len(unit.x-x,unit.y-y)) return;
                    unit.apply(givenEffect,60 * efficiency);

                });

            } else {
                efficiency = Mathf.lerpDelta(efficiency,0,0.1f);
                if (extinguishI < 60)
                    extinguishI += 0.5f;
            }
        }
    }
}
