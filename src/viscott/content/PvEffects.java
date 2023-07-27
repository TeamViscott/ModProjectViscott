package viscott.content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import viscott.world.pseudo3d.importedcode.DrawPseudo3d;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;

public class PvEffects {
    public static Effect
        slowEnergeticEffect, particleDeath1,particleDeath2,particleDeath3,
            railFrag,waveBulletFalerica,waveBulletJavelin,sumayaShoot, sumayaImpact,

            quadRushCraft,cascadeCraft,bulkCraft,surgeSpawn,siedeSummon,chargeUpHecta,branch
            ;
    public static Seq<Effect> nullisDeath = new Seq<>();
    public static void load()
    {
        slowEnergeticEffect = new Effect(32,e -> {
            color(Pal.sap);
            alpha(e.fout());
            stroke(e.fout()*4);
            circle(e.x,e.y,e.fin()*8.3f*8);
        });
        particleDeath1 = new Effect(100, e -> {
            color(Pal.sap);
            alpha(e.fout());
            stroke(e.fout()*4);
            circle(e.x,e.y,e.fin()*8);
        });
        particleDeath2 = new Effect(100, e -> {
            color(Pal.sap);
            alpha(e.fout());
            stroke(e.fout()*8);
            circle(e.x,e.y,e.fin()*16);
            circle(e.x,e.y,e.fin()*8);
        });
        particleDeath3 = new Effect(100, e -> {
            color(Pal.sap);
            alpha(e.fout());
            stroke(e.fout()*8);
            circle(e.x,e.y,e.fin()*24);
            circle(e.x,e.y,e.fin()*16);
            circle(e.x,e.y,e.fin()*8);
        });
        railFrag = new Effect(32f, e -> {
            color(Pal.sap);

            for(int i : Mathf.signs){
                Drawf.tri(e.x, e.y, 20f * e.fout(), 30f, e.rotation + 90 + 90f * i);
            }

            Drawf.light(e.x, e.y, 60f * e.fout(), Pal.orangeSpark, 0.5f);
        });
        waveBulletFalerica = new Effect(25f,e -> {
            color(Pal.lancerLaser);
            float waves = 2.5f;
            float x1 = e.x + Mathf.sin(e.rotation/180*Mathf.pi) * Mathf.sin(e.fin()*waves *Mathf.pi) * 8;
            float y1 = e.y - Mathf.cos(e.rotation/180*Mathf.pi) * Mathf.sin(e.fin()*waves *Mathf.pi) * 8;
            float x2 = e.x - Mathf.sin(e.rotation/180*Mathf.pi) * Mathf.sin(e.fin()*waves *Mathf.pi) * 8;
            float y2 = e.y + Mathf.cos(e.rotation/180*Mathf.pi) * Mathf.sin(e.fin()*waves *Mathf.pi) * 8;
            float fin = (e.time+1)/e.lifetime;
            float xn = e.x - Mathf.cos(e.rotation/180*Mathf.pi) * 16;
            float yn = e.y - Mathf.sin(e.rotation/180*Mathf.pi) * 16;
            float xn1 = xn + Mathf.sin(e.rotation/180*Mathf.pi) * Mathf.sin(fin*waves *Mathf.pi) * 8;
            float yn1 = yn - Mathf.cos(e.rotation/180*Mathf.pi) * Mathf.sin(fin*waves *Mathf.pi) * 8;
            float xn2 = xn - Mathf.sin(e.rotation/180*Mathf.pi) * Mathf.sin(fin*waves *Mathf.pi) * 8;
            float yn2 = yn + Mathf.cos(e.rotation/180*Mathf.pi) * Mathf.sin(fin*waves *Mathf.pi) * 8;
            // Fill.circle(x1,y1,e.fout()*2);
            // Fill.circle(x2,y2,e.fout()*2);
            stroke(e.fout()*2);
            line(x1, y1, xn1, yn1);
            line(x2, y2, xn2, yn2);
            // Lifetime = max lifetime || time = time alive
        });
        waveBulletJavelin = new Effect(35f,e -> {
            color(Pal.lancerLaser);
            float waves = 3f;
            float x1 = e.x + Mathf.sin(e.rotation/180*Mathf.pi) * Mathf.sin(e.fin()*waves *Mathf.pi) * 8;
            float y1 = e.y - Mathf.cos(e.rotation/180*Mathf.pi) * Mathf.sin(e.fin()*waves *Mathf.pi) * 8;
            float x2 = e.x - Mathf.sin(e.rotation/180*Mathf.pi) * Mathf.sin(e.fin()*waves *Mathf.pi) * 8;
            float y2 = e.y + Mathf.cos(e.rotation/180*Mathf.pi) * Mathf.sin(e.fin()*waves *Mathf.pi) * 8;
            float fin = (e.time+1)/e.lifetime;
            float xn = e.x - Mathf.cos(e.rotation/180*Mathf.pi) * 22;
            float yn = e.y - Mathf.sin(e.rotation/180*Mathf.pi) * 22;
            float xn1 = xn + Mathf.sin(e.rotation/180*Mathf.pi) * Mathf.sin(fin*waves *Mathf.pi) * 8;
            float yn1 = yn - Mathf.cos(e.rotation/180*Mathf.pi) * Mathf.sin(fin*waves *Mathf.pi) * 8;
            float xn2 = xn - Mathf.sin(e.rotation/180*Mathf.pi) * Mathf.sin(fin*waves *Mathf.pi) * 8;
            float yn2 = yn + Mathf.cos(e.rotation/180*Mathf.pi) * Mathf.sin(fin*waves *Mathf.pi) * 8;
            // Fill.circle(x1,y1,e.fout()*2);
            // Fill.circle(x2,y2,e.fout()*2);
            stroke(e.fout()*2);
            line(x1, y1, xn1, yn1);
            line(x2, y2, xn2, yn2);
            // Lifetime = max lifetime || time = time alive
        });
        quadRushCraft = new Effect(60,e->{
            Color col = Team.sharded.color;
            DrawPseudo3d.tube(e.x,e.y,5*e.fin(),e.fout()*50,col,Color.clear);
            Lines.stroke(e.fout()*4,col);
            Lines.circle(e.x,e.y,15*e.fin());
        });
        Interp sumayaShootInterp = new arc.math.Interp.ExpIn(2.5f, 10f);
        sumayaShoot = new Effect(180,e->{
            Color col = Color.valueOf("baf9ff");
            Color colVar = col.a(sumayaShootInterp.apply(1));
            DrawPseudo3d.tube(e.x,e.y,8,e.fout()*50,colVar,Color.clear);
            Lines.stroke(e.fout()*4,col);
            Lines.circle(e.x,e.y,80*e.fout());

        });
        sumayaImpact = new Effect(90,e->{
            Color col = Color.valueOf("baf9ff");
            DrawPseudo3d.tube(e.x,e.y,8,e.fout()*50,col,Color.clear);
            Lines.stroke(e.fout()*4,col);
            Lines.circle(e.x,e.y,80*e.fin());
        });
        Interp cascadeColAlphaInterp = new arc.math.Interp.ExpIn(2.5f, 10f);
        cascadeCraft = new Effect(120,e->{
            float waves = 3;
            float sin = Math.abs(Mathf.sin(e.fin()*waves *Mathf.pi));
            Color col = Team.green.color.cpy();
            col.a = cascadeColAlphaInterp.apply(1);
            DrawPseudo3d.ring(e.x,e.y,5 * sin,7 * sin,9 * sin,e.fin()*8,col,col);
            DrawPseudo3d.tube(e.x,e.y,2*e.fout(),e.fin()*50,col,Color.clear);
            Lines.stroke(e.fout()*4,col);
            Lines.circle(e.x,e.y,15*e.fin());
        });
        Interp bulkInterp = new arc.math.Interp.ExpIn(2.5f, 10f);
        bulkCraft = new Effect(120,e->{
            float waves = 10;
            float sin = Math.abs(Mathf.sin(e.fin()*waves *Mathf.pi));
            Color col = Team.crux.color.cpy();
            col.a = bulkInterp.apply(1);
            DrawPseudo3d.ring(e.x,e.y,5 * sin,7 * sin,9 * sin,e.fin()*8,col,col);
            DrawPseudo3d.tube(e.x,e.y,2*e.fout(),e.fin()*50,col,Color.clear);
            Lines.stroke(e.fout()*4,col);
            Lines.circle(e.x,e.y,15*e.fin());
        });
        for(int i = 1;i<=9;i++)
            nullisDeath.add(newNullisDeath(i));
        surgeSpawn = new Effect(120,e-> {
            Color col = Team.green.color;
            float finout = (Math.abs(e.fout() - 0.5f) - 0.5f) * -2;
            Lines.stroke(finout*4,col);
            Lines.square(e.x,e.y,e.fout()*12+4,e.fout()*720);
            Lines.stroke(finout*2,col);
            Lines.square(e.x,e.y,e.fout()*18+6,e.fout()*360);
            Draw.color(col);
            Fill.circle(e.x,e.y,e.fin()*8);
        });
        siedeSummon = new Effect(120,e-> {
            float waves = 1;
            float size = 24;
            float wave = Math.abs(Mathf.sin(e.fin()*waves*Mathf.pi));
            Draw.z(Layer.effect);
            Draw.color(Color.gray);
            Fill.circle(e.x,e.y,wave*size+1);
            Draw.color(Team.green.color);
            Fill.circle(e.x,e.y,wave*size);
            Draw.reset();
        });
        chargeUpHecta = new Effect(100,e-> {
            Draw.z(Layer.effect);
            Draw.color(Pal.redderDust);
            Lines.stroke(e.fin()*8);
            Lines.circle(e.x,e.y,e.fout()*8*5);
        });
    }

    public static Effect newNullisDeath(int blockSize)
    {
        return new Effect(60f,e -> {
            float waves = 1;
            float size = blockSize*4;
            float wave = Math.abs(Mathf.sin(e.fin()*waves*Mathf.pi));
            Draw.z(Layer.effect);
            Draw.color(Color.white);
            Fill.circle(e.x,e.y,wave*size+1);
            Draw.z(Layer.effect+5);
            Draw.color(Color.black);
            Fill.circle(e.x,e.y,wave*size);
            Draw.reset();
        });
    }
}

