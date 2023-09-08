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

    int nextTeam;
    int oldTeam;
    static BasicBulletType ricoShot = new BasicBulletType(4,10){{
        homingPower = 1;
        homingRange = 1600;
        lifetime = 600;
        trailLength = 20;
        trailWidth = 2;
        trailColor = backColor = lightColor
                = Pal.missileYellow;
    }};

    public CoinUnit(){
        super();
        ricoShot.load();
        Random r = new Random();
        oldTeam = this.team.id;
        this.nextTeam = 10 + r.nextInt(10);
    }
    @Override
    public void update(){
        super.update();
        this.team = Team.get(nextTeam);
    }
    @Override
    public void damage(float damage){
        ricoShot.damage = 10 + damage;
        this.team = Team.get(oldTeam);
        ricoShot.create(this, x, y, rotation);
        super.damage(damage);
    }
}
