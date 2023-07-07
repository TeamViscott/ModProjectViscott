package viscott.gen;

import arc.Core;
import arc.func.Cons;
import arc.func.Prov;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.entities.Effect;
import mindustry.gen.EntityMapping;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import viscott.content.PvStatusEffects;
import viscott.world.statusEffects.CurseStatusEffect;
import viscott.world.statusEffects.PvStatusEffect;

import java.lang.annotation.Target;
import java.util.HashMap;

public class FrogUnit extends UnitEntity {
    public CurseStatusEffect necroEffect;
    public Effect summonEffect = Fx.absorb;
    public float necroRange = 16;

    boolean canTransform = true;
    Seq<UnitType> transformList = Seq.with(UnitTypes.reign,UnitTypes.corvus,UnitTypes.toxopid,UnitTypes.eclipse);
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
            if (health < maxHealth/2) {
                UnitType selected = transformList.random();
                if (tileOn().solid())
                    while (!selected.flying) selected = transformList.random();
                transformedUnit = selected.spawn(team,x,y);
                transformedUnit.health /= 2;
                hideUnit = true;
                x = 0;
                y = 0;
                canTransform = false;
            }

        if (necroEffect == null)
            necroEffect = (CurseStatusEffect) PvStatusEffects.memoryExchange;
        Seq<Unit> remEff = new Seq<>();
        necroEffect.deadCursed.each(u -> {
            if (Mathf.len(u.x-x,u.y-y) <= 8*necroRange) {
                Unit necUnit = u.type().spawn(team(),u.x,u.y);
                necUnit.apply(PvStatusEffects.dataLeak);
                necUnit.health/= 2;
                remEff.add(u);
                summonEffect.at(necUnit);
            }
        });
        remEff.each(u -> necroEffect.deadCursed.remove(u));
    }

    @Override
    public boolean isBoss() {
        return health != maxHealth || super.isBoss();
    }
    @Override
    public int classId() {
        return 150;
    }

    @Override
    public void draw() {
        if (!hideUnit)
            super.draw();
    }
}
