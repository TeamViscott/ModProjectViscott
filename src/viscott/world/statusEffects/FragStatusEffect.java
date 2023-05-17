package viscott.world.statusEffects;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.Vars;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import viscott.content.PvStatusEffects;

public class FragStatusEffect extends PvStatusEffect {

    public BulletType fragBullet = null;
    public int fragBullets = 1;

    public FragStatusEffect(String name) {
        super(name);
    }
    @Override
    public void update(Unit unit, float time){
        if (fragBullet != null && !Vars.net.client())
            Groups.bullet.forEach(b->{
                if (b.owner() == unit){
                    b.keepAlive(true);
                    b.time += Time.delta;
                    if (b.lifetime <= b.time)
                        for(int i = 0;i<fragBullets;i++)
                            fragBullet.createNet(b.team(),b.x,b.y, Mathf.random(360),1,1,1);
                }
            });
    }
}
