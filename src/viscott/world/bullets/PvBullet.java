package viscott.world.bullets;

import arc.math.Mathf;
import arc.util.Time;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.core.World;
import mindustry.gen.Bullet;
import mindustry.gen.Flyingc;
import mindustry.gen.Hitboxc;
import mindustry.gen.Teamc;

public class PvBullet extends Bullet {

    @Override
    public void update() {
        if (!Vars.net.client() || this.isLocal()) {
            float px = this.x;
            float py = this.y;
            this.move(this.vel.x * Time.delta, this.vel.y * Time.delta);
            if (Mathf.equal(px, this.x)) {
                this.vel.x = 0.0F;
            }

            if (Mathf.equal(py, this.y)) {
                this.vel.y = 0.0F;
            }

            this.vel.scl(Math.max(1.0F - this.drag * Time.delta, 0.0F));
        }

        if (this.mover != null) {
            this.mover.move(this);
        }

        this.type.update(this);
        if (this.type.collidesTiles && this.type.collides && this.type.collidesGround) {
            float speed = Mathf.sqrt(Mathf.pow(this.vel.x,2)+Mathf.pow(this.vel.y,2));
            float itterations = Mathf.floor(speed / 8);
            for(int i = 0;i < itterations;i++)
                this.tileRaycast(World.toTile(this.lastX+(this.vel.x*(i/itterations))), World.toTile(this.lastY+(this.vel.y*(i/itterations))), this.tileX(), this.tileY());
        }

        if (this.type.removeAfterPierce && this.type.pierceCap != -1 && this.collided.size >= this.type.pierceCap) {
            this.hit = true;
            this.remove();
        }

        if (this.keepAlive) {
            this.time -= Time.delta;
            this.keepAlive = false;
        }

        this.time = Math.min(this.time + Time.delta, this.lifetime);
        if (this.time >= this.lifetime) {
            this.remove();
        }

    }
    public static PvBullet pvCreate() {
        return (PvBullet) Pools.obtain(PvBullet.class, PvBullet::new);
    }
}
