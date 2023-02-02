package viscott.content.shootpatterns;

import mindustry.entities.part.DrawPart;
import mindustry.entities.pattern.ShootAlternate;

public class AlternateShootPatternTurret extends ShootAlternate {
    public Integer barrelIndex = 0;
    public DrawPart.PartProgress selectedBarrel1 = p -> (barrelIndex+1)%barrels;
    public DrawPart.PartProgress selectedBarrel2 = p -> barrelIndex%barrels;
    AlternateShootPatternTurret()
    {
        super();
    }
    public AlternateShootPatternTurret(int margin)
    {
        super(margin);
    }
    @Override
    public void shoot(int totalShots, BulletHandler handler){
        barrelIndex = totalShots;
        super.shoot(totalShots,handler);
    }
}
