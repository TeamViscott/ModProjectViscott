package viscott.world.block.defense;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.gen.Groups;
import mindustry.graphics.Pal;
import viscott.content.PvEffects;


public class VoidWall extends PvWall{
    public float regenRate = 20;
    public VoidWall(String name) {
        super(name);
        update = true;
    }

    public class VoidWallBuild extends PvWallBuild {
        public boolean inVoid;
        public float time = 0;
        float lx = x,
              ly = y;

        @Override
        public void update() {
            float hpPercent = (health / maxHealth)*-1+1;
            lx = x + Mathf.random(-2*hpPercent,2*hpPercent);
            ly = y + Mathf.random(-2*hpPercent,2*hpPercent);
            if (inVoid && health < maxHealth && !isHealSuppressed()) {
                time += Time.delta;
                inVoid = false;
                heal(regenRate / 60);
                if (time > 60) {
                    time-=60;
                    Fx.healBlockFull.create(x,y,block.size, Color.white,block);
                }
            } else {
                time = 0;
            }
            Groups.bullet.intersect(x,y,8*2,8*2,b -> {
                if (b.team != team && b.type instanceof ArtilleryBulletType a) {
                    damage(b.damage);
                    PvEffects.nullisDeath.get(0).at(b);
                    b.absorb();
                }
            });
        }

        @Override
        public void draw() {
            Draw.rect(block.name,lx,ly);
            super.draw();
            Draw.rect(block.name+"-shake",lx,ly);
        }
    }
}
