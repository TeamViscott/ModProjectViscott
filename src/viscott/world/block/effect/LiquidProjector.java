package viscott.world.block.effect;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Align;
import mindustry.Vars;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.core.World;
import mindustry.entities.Fires;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.gen.Tex;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.type.StatusEffect;
import mindustry.world.Tile;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawMulti;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValue;
import viscott.world.block.PvBlock;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;


public class LiquidProjector extends PvBlock {
    public float range = 20*8;
    public DrawBlock drawer = new DrawDefault();
    public float activeDrain = 1;

    /// removes the liquid component if set. if unset will use liquidsAllowed to have a list of liquids that can be inserted.
    public Liquid defaultLiquid;

    public float extinguishMultiplier = 1;

    public Seq<Liquid> liquidsAllowed;

    public void init() {
        if (defaultLiquid != null) { /* Should not have liquid if defaultLiquid is chosen */
            liquidCapacity = 0;
            hasLiquids = false;
            liquidsAllowed.clear(); // clear allowed Liquids.
        }
        super.init();
        drawer.load(this);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.range,(range/8) + " Blocks");
        if (defaultLiquid == null)
            stats.add(Stat.ammo, (t) -> {
                Table table = new Table();
                table.setWidth(220f);
                table.align(Align.left);
                liquidsAllowed.each( l -> {
                    Table liq = new Table();
                    liq.setWidth(liq.getMaxWidth());
                    liq.background(Tex.whiteui);
                    liq.setColor(Pal.darkestGray);
                    liq.margin(5f);
                    liq.image(l.uiIcon).padLeft(20f);
                    liq.add(l.localizedName).left();
                    if (liquidExtinguish(l)) { // Extinguishable
                        liq.row();
                        liq.add("- auto Extinguish",Pal.gray);
                    }
                    if (liquidDefend(l)) { // Dangerous
                        liq.row();
                        liq.add("- auto Defense",Pal.gray);
                    }
                    liq.marginBottom(10f);
                    table.add(liq).pad(10).grow();
                    table.row();
                });
                t.add(table);
            });
        else
            stats.add(Stat.ammoUse, (t) -> {
                Table table = new Table();
                table.setWidth(220f);
                table.background(Tex.whiteui);
                table.setColor(Pal.darkestGray);
                table.image(defaultLiquid.uiIcon).padLeft(20f);
                table.add(defaultLiquid.localizedName).left();
                if (liquidExtinguish(defaultLiquid)) { // Extinguishable
                    table.row();
                    table.add("- auto Extinguish",Pal.gray);
                }
                if (liquidDefend(defaultLiquid)) { // Dangerous
                    table.row();
                    table.add("- auto Defense",Pal.gray);
                }
                table.row();
                table.margin(5f);
                t.add(table);
            });
    }
    private boolean liquidExtinguish(Liquid liquid) {
        return liquid.canExtinguish() && liquid.temperature < 1;
    }
    private boolean liquidDefend(Liquid liquid) {
        return (!liquid.gas && liquid.viscosity > 0.5) && liquid.effect != StatusEffects.none;
    }
    public LiquidProjector(String name) {
        super(name);
        liquidsAllowed = Vars.content.liquids().copy().retainAll(l->liquidExtinguish(l) || liquidDefend(l));
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
        float effectI = 0;
        @Override
        public boolean acceptLiquid(Building source,Liquid liquid) {
            return hasLiquids && ((liquids.currentAmount() <= 0 || liquids.current() == liquid) && liquidCapacity > liquids.currentAmount());
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
            effectI += delta();
            extinguishI += delta();
            if (hasLiquids && (liquids.current() == null || liquids.currentAmount() <= 0)) return;
            if (power != null && power.status == 0) return;
            boolean activate = false;
            Liquid current = hasLiquids ? liquids.current() : defaultLiquid;
            boolean dangerous = liquidDefend(current);
            boolean canExtinguish = liquidExtinguish(current);
            StatusEffect givenEffect = current.effect;
            if (canExtinguish) { // Activate when Fire.
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
                            if (extinguishI >= 10) {
                                float eff = (1 - current.temperature) * extinguishMultiplier * efficiency;
                                Fires.extinguish(other, eff);
                                current.vaporEffect.at(other.x,other.y,current.color);
                            }
                        }
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
                extinguishI %= 10;
                if (effectI >= 60) {
                    effectI %= 60;
                    for(int i = 0;i<60;i++) {
                        float vx = Mathf.sinDeg(i*6)*range;
                        float vy = Mathf.sinDeg(i*6+90)*range;
                        current.vaporEffect.at(x+vx,y+vy,current.color);
                    }
                    current.vaporEffect.at(x,y,current.color);
                }
                float max = power == null ? 1 : power.status;
                efficiency = Mathf.lerpDelta(efficiency,max,0.1f);
                if (hasLiquids) liquids.remove(current,efficiency/60 * max * activeDrain);
                Units.nearby(x-range,y-range,range*2,range*2,(unit) -> {
                    if (unit.team == team || range <= Mathf.len(unit.x-x,unit.y-y)) return;
                    unit.apply(givenEffect,60 * efficiency);
                });

            } else
                efficiency = Mathf.lerpDelta(efficiency,0,0.1f);
        }
    }
}
