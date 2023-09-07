package viscott.world.block.defense;

import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.style.Drawable;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Cell;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.entities.Units;
import mindustry.gen.Groups;
import mindustry.gen.Healthc;
import mindustry.gen.Icon;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.world.blocks.ItemSelection;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import static mindustry.Vars.control;

public class FocusItemTurret extends PvItemTurret{
    public enum Modes {
        closest,
        farthest,
        mostHealth,
        leastHealth,
        random;
        static Seq<Modes> all = Seq.with(closest,farthest,mostHealth,leastHealth,random);
        static HashMap<Modes, TextureRegionDrawable> icon = new HashMap<>(){{
            put(closest,Icon.commandRally);
            put(farthest,Icon.flipX);
            put(mostHealth,Icon.players);
            put(leastHealth,Icon.playersSmall);
            put(random,Icon.info);

        }};
    }
    public FocusItemTurret(String name) {
        super(name);
        configurable = true;
        config(Modes.class,(b,m) -> {
            ((FocusItemTurretBuild)b).mode = m;
        });
    }
    class Health {
        public float hp = 0;
        public float maxHp = 0;
    }
    public class FocusItemTurretBuild extends ItemTurretBuild {
        public Modes mode = Modes.closest;
        @Override
        protected void findTarget(){
            float range = range();
            if (!Units.anyEntities(x,y,range)) return;
            Health hitP = new Health();
            switch(mode) {
                case closest:
                    if(targetAir && !targetGround){
                        target = Units.bestEnemy(team, x, y, range, e -> !e.dead() && !e.isGrounded() && unitFilter.get(e), unitSort);
                    }else{
                        target = Units.bestTarget(team, x, y, range, e -> !e.dead() && unitFilter.get(e) && (e.isGrounded() || targetAir) && (!e.isGrounded() || targetGround), b -> targetGround && buildingFilter.get(b), unitSort);
                    }
                    break;
                case farthest:
                    Groups.unit.each(u->{
                        if (u.team() == team()) return;
                        //  can attack ground and is.              can attack air and is.
                        if (!((u.isGrounded() && targetGround) || (!u.isGrounded() && targetAir))) return;
                        if (target == null) {
                            target = u;
                            return;
                        }
                        if (Mathf.len(x-u.x,y-u.y) > Mathf.len(x - target.x(), y - target.y())) {
                            target = u;
                        }
                    });
                    break;
                case mostHealth:
                    Groups.unit.each(u -> {
                        if (u.team() == team()) return;
                        if (!((u.isGrounded() && targetGround) || (!u.isGrounded() && targetAir))) return;
                        if (u.health() > hitP.hp) {
                            target = u;
                            hitP.hp = u.health();
                            hitP.maxHp = u.maxHealth();
                        }
                    });
                    break;
                case leastHealth:
                    hitP.hp = Float.MAX_VALUE;
                    Groups.unit.each(u -> {
                        if (u.team() == team()) return;
                        if (!((u.isGrounded() && targetGround) || (!u.isGrounded() && targetAir))) return;
                        if (u.health() < hitP.hp) {
                            target = u;
                            hitP.hp = u.health();
                            hitP.maxHp = u.maxHealth();
                        }
                    });
                    break;
                case random:
                    Seq<Unit> targetSeq = new Seq<>();
                    Groups.unit.each(u->{
                        if (u.team() == team()) return;
                        if (!((u.isGrounded() && targetGround) || (!u.isGrounded() && targetAir))) return;
                        targetSeq.add(u);
                    });
                    target = targetSeq.random();
                    break;
            }

            if(target == null && canHeal()){
                target = Units.findAllyTile(team, x, y, range, b -> b.damaged() && b != this);
            }
        }

        @Override
        public void buildConfiguration(Table table) {
            super.buildConfiguration(table);
            Table inner = new Table();
            for(var i : Modes.all) {
                TextureRegionDrawable draw = Modes.icon.get(i);
                if (i == mode) draw.tint(Pal.bulletYellow);
                var b = inner.button(draw,32,() -> {}).tooltip(i.name());
                b.get().changed(()->{
                    if (b.get().isChecked())
                        configure(i);
                    control.input.config.hideConfig();
                });
            }
            table.add(inner);
        }
    }
}
