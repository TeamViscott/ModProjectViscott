package viscott.gen;

import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import viscott.content.PvUnits;
import viscott.world.statusEffects.CurseStatusEffect;

public class MwBossUnit extends UnitEntity {
    public CurseStatusEffect necroEffect;
    public Effect summonEffect = Fx.absorb;
    public float necroRange = 16;

    boolean canTransform = true;
    public UnitType transformInto = PvUnits.charlie;
    Unit transformedUnit;
    boolean hideUnit = false;

    @Override
    public void update() {
        if (!hideUnit)
            super.update();
        else if (!transformedUnit.isValid()) {
            hideUnit = false;
            set(transformedUnit.x(),transformedUnit.y());
        }

        if (canTransform)
            if (health == 1) {
                UnitType selected = transformInto;
                transformedUnit = selected.spawn(team,x,y);
                hideUnit = true;
                x = 0;
                y = 0;
                canTransform = false;
            }

    }

    @Override
    public boolean isBoss() {
        return health != maxHealth || super.isBoss();
    }
    @Override
    public void damage(float damage) {
        if (health - damage > 1)
            super.damage(damage);
        else
            health = 1;
    }
    @Override
    public int classId() {
        return 73;
    }

    @Override
    public void draw() {
        if (!hideUnit)
            super.draw();
    }
}
