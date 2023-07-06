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
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import viscott.content.PvStatusEffects;
import viscott.world.statusEffects.CurseStatusEffect;
import viscott.world.statusEffects.PvStatusEffect;

import java.util.HashMap;

public class FrogUnit extends UnitEntity {
    public CurseStatusEffect necroEffect;
    public float necroRange = 8;

    @Override
    public void update() {
        super.update();
        if (necroEffect == null)
            necroEffect = (CurseStatusEffect) PvStatusEffects.memoryExchange;
        Seq<Unit> remEff = new Seq<>();
        necroEffect.deadCursed.each(u -> {
            if (Mathf.len(u.x-x,u.y-y) <= 8*necroRange) {
                Unit necUnit = u.type().spawn(team(),u.x,u.y);
                necUnit.health/= 2;
                remEff.add(u);
            }
        });
        remEff.each(u -> necroEffect.deadCursed.remove(u));

    }

    @Override
    public void draw() {
        super.draw();
    }
}
