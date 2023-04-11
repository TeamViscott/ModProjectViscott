package viscott.content.shootpatterns;

import arc.func.Cons;
import mindustry.entities.part.DrawPart;
import mindustry.entities.pattern.ShootAlternate;

public class AlternateShootPatternTurret extends ShootAlternate {
    public Integer lastBarrelIndex = 0;
    public Integer barrels = 0;
    public interface IntCons<T>
    {
        int Run(T t);
    }
    public IntCons<Integer> barrelPos = (i) -> (lastBarrelIndex+i % barrels) == 0 ? 1 : 0;
    AlternateShootPatternTurret()
    {
        super();
    }
    public AlternateShootPatternTurret(int margin,int barrels)
    {
        super(margin);
        this.barrels = barrels;
    }
    @Override
    public void shoot(int totalShots, BulletHandler handler){
        lastBarrelIndex = totalShots/shots;
        super.shoot(totalShots,handler);
    }
}
