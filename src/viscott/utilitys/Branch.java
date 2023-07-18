package viscott.utilitys;

import arc.graphics.Color;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Geometry;
import arc.math.geom.Rect;
import arc.math.geom.Vec2;
import arc.struct.IntSet;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Time;
import viscott.content.*;
import mindustry.content.Bullets;
import mindustry.core.World;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.gen.Unitc;
import mindustry.world.Tile;

import static mindustry.Vars.*;

/** modified copy of Lightning class, with alterations.
 * @author Otamamori
 */


public class Branch {
    private static final Rand random = new Rand();
    private static final Rect rect = new Rect();
    private static final Seq<Unitc> entities = new Seq<>();
    private static final IntSet hit = new IntSet();
    private static final int maxChain = 4;
    private static final float hitRange = 60f;
    private static boolean bhit = false;
    private static int lastSeed = 0;
    public static void create(Team team, float damage, float x, float y, float targetAngle, int length){
        createBranchInternal(null, lastSeed++, team, damage, x, y, targetAngle, length);
    }
    public static void create(Bullet bullet, float damage, float x, float y, float targetAngle, int length){
        createBranchInternal(bullet, lastSeed++, bullet.team, damage, x, y, targetAngle, length);
    }
    private static void createBranchInternal(@Nullable Bullet hitter, int seed, Team team, float damage, float x, float y, float rotation, int length){
        random.setSeed(seed);
        hit.clear();

        BulletType hitCreate = hitter == null || hitter.type.lightningType == null ? Bullets.damageLightning : hitter.type.lightningType;
        Seq<Vec2> lines = new Seq<>();
        bhit = false;

        lines.add(new Vec2(x, y));

        for(int i = 0; i < length / 2; i++){
            hitCreate.create(null, team, x, y, rotation, damage, 1f, 1f, hitter);
            lines.add(new Vec2(x + Mathf.range(15f), y + Mathf.range(15f)));

            if(lines.size > 1){
                bhit = false;
                Vec2 from = lines.get(lines.size - 2);
                Vec2 to = lines.get(lines.size - 1);
                world.raycastEach(World.toTile(from.getX()), World.toTile(from.getY()), World.toTile(to.getX()), World.toTile(to.getY()), (wx, wy) -> {

                    Tile tile = world.tile(wx, wy);
                    if(tile != null && tile.build != null && tile.solid() && tile.team() != team){ //it is blocked by all blocks, not just insulated ones; This just looks way better.
                        bhit = true;
                        lines.get(lines.size - 1).set(wx * tilesize, wy * tilesize);
                        return true;
                    }
                    return false;
                });
                if(bhit) break;
            }

            rect.setSize(hitRange).setCenter(x, y);
            entities.clear();
            if(hit.size < maxChain){
                Units.nearbyEnemies(team, rect, u -> {
                    if(!hit.contains(u.id()) && (hitter == null || u.checkTarget(hitter.type.collidesAir, hitter.type.collidesGround))){
                        entities.add(u);
                    }
                });
            }

            Unitc furthest = Geometry.findFurthest(x, y, entities);

            if(furthest != null){
                hit.add(furthest.id());
                x = furthest.x();
                y = furthest.y();
            }else{
                rotation += random.range(20f);
                x += Angles.trnsx(rotation, hitRange / 2f);
                y += Angles.trnsy(rotation, hitRange / 2f);
            }
        }

        BranchFx.smallBranch.at(x, y, rotation, lines);
        float finalRotation = rotation;
        float finalX = x;
        float finalY = y;
        Time.run(BranchFx.smallBranch.lifetime, () -> {
            BranchFx.smallBranchFade.at(finalX, finalY, finalRotation, lines);
            int n = Mathf.random(5);
            if(!headless){
                Effect.shake(6f, 5.5f, finalX, finalY);
                Sounds.mud.at(finalX, finalY, 1f + Mathf.range(0.1f), 3f);
            }
        });
    }
}
