package viscott.world.statusEffects;

import arc.Events;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.units.StatusEntry;
import mindustry.game.EventType;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Unit;

public class FragStatusEffect extends PvStatusEffect {

    public BulletType fragBullet = null;
    public int fragBullets = 1;
    public float fragRandomSpread = 360;
    Seq<Bullet> fragTaggedBullets = new Seq<>();

    public FragStatusEffect(String name) {
        super(name);
        Events.run(EventType.Trigger.update,()->{
            if (!Vars.state.isPaused()) {
                Seq<Bullet> removeBullets = new Seq<>();
                fragTaggedBullets.each(b -> {
                    if (!b.isAdded()) {
                        for (int i = 0; i < fragBullets; i++)
                            fragBullet.createNet(b.team(), b.x, b.y, Mathf.random(fragRandomSpread) - fragRandomSpread / 2 + b.rotation(), 1, 1, 1);
                        removeBullets.add(b);
                    }
                });
                removeBullets.each(b -> {
                    fragTaggedBullets.remove(b);
                });
            }
        });
    }
    @Override
    public void update(Unit unit, StatusEntry status){
        if (fragBullet != null && !Vars.net.client())
            Groups.bullet.intersect(unit.x, unit.y, 32, 32, b->{
                if (b.owner() == unit && !fragTaggedBullets.contains(b)){
                    fragTaggedBullets.add(b);
                }
            });
    }
}
