package viscott.world.statusEffects;

import arc.Events;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.entities.Units;
import mindustry.game.EventType;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;

public class HomingStatusEffect extends PvStatusEffect {
    public float homingPower = 0.02f;
    public float homingDelay = 0;
    public float homingRange = 8*10;
    Seq<Bullet> homingBullets = new Seq<>();

    public HomingStatusEffect(String name) {
        super(name);
        Events.run(EventType.Trigger.update,()->{
            if (!Vars.state.isPaused()) {
                Seq<Bullet> removeBullets = new Seq<>();
                homingBullets.forEach(b -> {
                    if (b.isAdded()) {
                        if(homingPower > 0.0001f && b.time >= homingDelay) {
                            float realAimX = b.aimX < 0 ? b.x : b.aimX;
                            float realAimY = b.aimY < 0 ? b.y : b.aimY;
                            Teamc target;
                            //home in on allies if possible
                            if (b.type.heals()) {
                                target = Units.closestTarget(null, realAimX, realAimY, homingRange,
                                        e -> e.checkTarget(b.type.collidesAir, b.type.collidesGround) && e.team != b.team && !b.hasCollided(e.id),
                                        t -> b.type.collidesGround && (t.team != b.team || t.damaged()) && !b.hasCollided(t.id)
                                );
                            } else {
                                if (b.aimTile != null && b.aimTile.build != null && b.aimTile.build.team != b.team && b.type.collidesGround && !b.hasCollided(b.aimTile.build.id)) {
                                    target = b.aimTile.build;
                                } else {
                                    target = Units.closestTarget(b.team, realAimX, realAimY, homingRange,
                                            e -> e != null && e.checkTarget(b.type.collidesAir, b.type.collidesGround) && !b.hasCollided(e.id),
                                            t -> t != null && b.type.collidesGround && !b.hasCollided(t.id));
                                }
                            }
                            if (target != null) {
                                b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(target), homingPower * Time.delta * 50f));
                            }
                        }
                    } else {
                        removeBullets.add(b);
                    }
                });
                removeBullets.forEach(b -> {
                    homingBullets.remove(b);
                });
            }
        });
    }
    @Override
    public void update(Unit unit, float time){
        Groups.bullet.forEach(b->{
            if (b.owner() == unit && !homingBullets.contains(b)){
                homingBullets.add(b);
            }
        });
    }
}
