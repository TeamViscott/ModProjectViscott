package viscott.gen;

import arc.graphics.g2d.TextureRegion;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Pal;
import mindustry.world.blocks.defense.turrets.Turret;

import java.util.Random;

public class CoinUnit extends UnitEntity {

    Unit TeamReference = this;
    int nextTeam;

    public CoinUnit(){super(); TeamReference.team = team; Random r = new Random();  this.nextTeam = 10 + r.nextInt(10);}
    public void update(){
        if (this.team.id != nextTeam){TeamReference.team = this.team;} this.team = Team.get(nextTeam);}
    @Override
    public void damage(float damage){
        BasicBulletType ricoShot = new BasicBulletType(4,damage+10){{
            homingPower = 1;
            homingRange = 1600;
            lifetime = 600;
            trailLength = 20;
            trailWidth = 2;
            trailColor = backColor = lightColor = Pal.missileYellow;
        }};
        ricoShot.load();
        ricoShot.create(TeamReference, x, y, rotation);
        super.damage(damage);

    }
}
