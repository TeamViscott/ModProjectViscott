package viscott.gen;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Timer;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.entities.Effect;
import mindustry.entities.abilities.ForceFieldAbility;
import mindustry.gen.*;
import mindustry.type.UnitType;
import mindustry.world.Tile;
import viscott.content.PvEffects;
import viscott.content.PvStatusEffects;
import viscott.content.PvUnitMapper;
import viscott.content.PvUnits;
import viscott.world.statusEffects.CurseStatusEffect;

import java.io.DataInput;

public class FrogUnit extends UnitEntity {
    public CurseStatusEffect necroEffect = (CurseStatusEffect) PvStatusEffects.memoryExchange;
    public Effect summonEffect = PvEffects.siedeSummon;
    public float necroRange = 20;

    boolean canTransform = true;
    Seq<UnitType> transformList = Seq.with(PvUnits.pericope,PvUnits.kilo);
    Unit transformedUnit;
    boolean hideUnit = false;
    int fleetSize = 24;
    Seq<UnitType> fleetUnits = Seq.with(
            PvUnits.snippet,PvUnits.fragment, PvUnits.excerpt,
            PvUnits.milli,PvUnits.deci, PvUnits.centi,
            PvUnits.chime,PvUnits.carillon,PvUnits.peal
    );
    float summonCharges = 2;
    boolean wantsToSummon = true;
    ForceFieldAbility forcePhaseTwo = new ForceFieldAbility(8*24,200/60,45000,1800){{sides = 10;}};
    public FrogUnit() {
        super();
    }
    @Override
    public void update() {
        if (!hideUnit)
            super.update();
        else if (!transformedUnit.isValid()) {
            hideUnit = false;
            set(transformedUnit.x(),transformedUnit.y());
        }

        if(summonCharges > 0) {
            if (isShooting() && !hideUnit && wantsToSummon) {
                summonFleet();
                summonCharges--;
                wantsToSummon = false;
            }
        }

        if (canTransform) {
            if (health < maxHealth / 2) {
                if (!Vars.net.client()) {
                    UnitType selected = transformList.random();
                    if (tileOn().solid()) {
                        while (!selected.flying) selected = transformList.random();
                    }
                    transformedUnit = selected.spawn(team, x, y);
                    transformedUnit.health /= 2;
                }
                hideUnit = true;
                x = 0;
                y = 0;
                canTransform = false;
                wantsToSummon = true;
                isShooting = false;
                shield = 45000;
            }
        } else {
            forcePhaseTwo.update(this);
        }

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

    public void summonFleet() {
        if(Vars.net.client()) return;
        for(int i = 0;i < fleetSize;i++)
            Timer.schedule(()-> {
                float curX = (x() + ((float)Math.random()-0.5f) * 16f * 10);
                float curY = (y() + ((float)Math.random()-0.5f) * 16f * 10);
                Tile t = Vars.world.tile(Mathf.floor(curX/8),Mathf.floor(curY/8));
                UnitType selected = fleetUnits.random();
                if (t==null || t.solid())
                    while (!selected.flying) selected = fleetUnits.random();
                Unit u = selected.spawn(team,curX,curY);
                Call.effect(summonEffect,curX,curY,0,Color.white);
                if (!Vars.headless)
                    summonEffect.at(curX,curY,0,Color.white);
                u.shield = u.maxHealth;
                u.apply(PvStatusEffects.dataLeak);
            },i/10f);
    }

    @Override
    public boolean isBoss() {
        return health != maxHealth || super.isBoss();
    }
    @Override
    public int classId() {
        return PvUnitMapper.FrogId;
    }

    @Override
    public void draw() {
        if (hideUnit) return;
        super.draw();
        if (!canTransform)
            forcePhaseTwo.draw(this);
    }
    @Override
    public void write(Writes write) {
        super.write(write);
        write.bool(canTransform);
        write.bool(wantsToSummon);
        write.b((byte)summonCharges);
    }

    @Override
    public void read(Reads reads) {
        super.read(reads);
        canTransform = reads.bool();
        wantsToSummon = reads.bool();
        summonCharges = reads.b();
    }
}
