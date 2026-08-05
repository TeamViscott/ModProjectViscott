package viscott.gen;

import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Teamc;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Pal;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.meta.Stat;

import java.util.Random;

public class CoinUnit extends UnitEntity {

    Team nextTeam;
    Team oldTeam;
    static BasicBulletType ricoShot = new BasicBulletType(4,10){{
        homingPower = 20;
        homingRange = 1600;
        lifetime = 600;
        trailLength = 20;
        trailWidth = 2;
        trailColor = backColor = lightColor
                = Pal.missileYellow;
    }};

    public CoinUnit(){
        //to do increase cap of team()
        super();
        ricoShot.load();
        oldTeam = team();
        nextTeam = Team.get(10 + Mathf.random(10));
        //to do increase cap of nextteam & reduce cap of team()
        team(nextTeam);
    }

    //TODO now the coins switch teams, but instantly break due to unit cap.
    // simple solution: increase unit cap when needed, and reduce it afterwards

    @Override
    public void update(){
        super.update();
    }
    @Override
    public void damage(float damage){
        ricoShot.damage = 10 + damage;
        //to do decrease cap of nextteam & increase cap of oldTeam
        team(oldTeam);
        Bullet bullet = ricoShot.create(this, team(), this.x, this.y, 0f);
        super.damage(damage);
        ricoShot.updateHoming(bullet);
        //to do decrease cap of oldteam

    }
}
