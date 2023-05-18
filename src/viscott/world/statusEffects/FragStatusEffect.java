package viscott.world.statusEffects;

import arc.Events;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import viscott.content.PvStatusEffects;

public class FragStatusEffect extends PvStatusEffect {

    public BulletType fragBullet = null;
    public int fragBullets = 1;
    public float fragRandomSpread = 360;
    Seq<Bullet> fragTaggedBullets = new Seq<>();

    public FragStatusEffect(String name) {
        super(name);
        Events.run(EventType.Trigger.update,()->{
            if (!Vars.state.isPaused())
                fragTaggedBullets.forEach(b -> {
                    if (!b.isAdded())
                    {
                        for(int i = 0;i<fragBullets;i++)
                            fragBullet.createNet(b.team(),b.x,b.y, Mathf.random(fragRandomSpread)-fragRandomSpread/2 + b.rotation(),1,1,1);
                        fragTaggedBullets.remove(b);
                    }
                });
        });
    }
    @Override
    public void update(Unit unit, float time){
        if (fragBullet != null && !Vars.net.client())
            Groups.bullet.forEach(b->{
                if (b.owner() == unit && !fragTaggedBullets.contains(b)){
                    fragTaggedBullets.add(b);
                }
            });
    }
}
