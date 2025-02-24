package viscott.utilitys;

import arc.func.*;
import arc.math.*;
import arc.math.geom.*;
import arc.math.geom.QuadTree.*;
import arc.struct.*;
import arc.util.pooling.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.game.Teams.*;
import mindustry.gen.*;
import mindustry.world.*;

import java.util.*;

/** @author EyeOfDarkness */
public class TreebUtils {
    public static Rect r = new Rect(), r2 = new Rect();
    static Vec2 v2 = new Vec2(), v3 = new Vec2(), v4 = new Vec2();

    public static Vec2 v = new Vec2();
    public static Rand rand = new Rand();

    public static <T extends QuadTreeObject> void scanQuadTree(QuadTree<T> tree, QuadTreeHandler within, Cons<T> cons){
        if(within.get(tree.bounds, true)){
            for(T t : tree.objects){
                t.hitbox(r2);
                if(within.get(r2, false)){
                    cons.get(t);
                }
            }

            if(!tree.leaf){
                scanQuadTree(tree.botLeft, within, cons);
                scanQuadTree(tree.botRight, within, cons);
                scanQuadTree(tree.topLeft, within, cons);
                scanQuadTree(tree.topRight, within, cons);
            }
        }
    }

    public static <T extends QuadTreeObject> void intersectLine(QuadTree<T> tree, float width, float x1, float y1, float x2, float y2, LineHitHandler<T> cons){
        //intersectLine(tree, width, x1, y1, x2, y2, cons, 12);
        r.set(tree.bounds).grow(width);
        if(Intersector.intersectSegmentRectangle(x1, y1, x2, y2, r)){
            //
            for(T t : tree.objects){
                //cons.get(t);
                t.hitbox(r2);
                //float size = Math.max(r2.width, r2.height);
                r2.grow(width);
                /*
                Vec2 v = Geometry.raycastRect(x1, y1, x2, y2, r2);
                if(v != null){
                    float size = Math.max(r2.width, r2.height);
                    float mx = r2.x + r2.width / 2, my = r2.y + r2.height / 2;
                    float scl = (size - width) / size;
                    v.sub(mx, my).scl(scl).add(mx, my);

                    cons.get(t, v.x, v.y);
                }
                 */
                float cx = r2.x + r2.width / 2f, cy = r2.y + r2.height / 2f;
                float cr = Math.max(r2.width, r2.height);

                Vec2 v = intersectCircle(x1, y1, x2, y2, cx, cy, cr / 2f);
                if(v != null){
                    float scl = (cr - width) / cr;
                    v.sub(cx, cy).scl(scl).add(cx, cy);

                    cons.get(t, v.x, v.y);
                }
            }

            if(!tree.leaf){
                intersectLine(tree.botLeft, width, x1, y1, x2, y2, cons);
                intersectLine(tree.botRight, width, x1, y1, x2, y2, cons);
                intersectLine(tree.topLeft, width, x1, y1, x2, y2, cons);
                intersectLine(tree.topRight, width, x1, y1, x2, y2, cons);
            }
        }
    }
    public static <T extends QuadTreeObject> void scanCone(QuadTree<T> tree, float x, float y, float rotation, float length, float spread, Cons<T> cons, boolean source, boolean accurate){
        //
        if(source){
            v2.trns(rotation - spread, length).add(x, y);
            v3.trns(rotation + spread, length).add(x, y);
        }
        //r.set(tree.bounds).grow(width);
        Rect r = tree.bounds;
        boolean valid = false;
        if(Intersector.intersectSegmentRectangle(x, y, v2.x, v2.y, r) || Intersector.intersectSegmentRectangle(x, y, v3.x, v3.y, r) || r.contains(x, y)){
            valid = true;
        }
        float lenSqr = length * length;
        if(!valid){
            for(int i = 0; i < 4; i++){
                float mx = (r.x + r.width * (i % 2)) - x;
                float my = (r.y + (i >= 2 ? r.height : 0f)) - y;

                float dst2 = Mathf.dst2(mx, my);
                if(dst2 < lenSqr && Angles.within(rotation, Angles.angle(mx, my), spread)){
                    valid = true;
                    break;
                }
            }
        }
        if(valid){
            for(T t : tree.objects){
                Rect rr = r2;
                t.hitbox(rr);

                float mx = (rr.x + rr.width / 2) - x;
                float my = (rr.y + rr.height / 2) - y;
                float size = (Math.max(rr.width, rr.height) / 2f);
                float bounds = size + length;
                float at = accurate ? Mathf.angle(Mathf.sqrt(mx * mx + my * my), size) : 0f;
                if(mx * mx + my * my < (bounds * bounds) && Angles.within(rotation, Angles.angle(mx, my), spread + at)){
                    cons.get(t);
                }
            }
            if(!tree.leaf){
                scanCone(tree.botLeft, x, y, rotation, length, spread, cons, false, accurate);
                scanCone(tree.botRight, x, y, rotation, length, spread, cons, false, accurate);
                scanCone(tree.topLeft, x, y, rotation, length, spread, cons, false, accurate);
                scanCone(tree.topRight, x, y, rotation, length, spread, cons, false, accurate);
            }
        }
    }

    /** code taken from BadWrong_ on the gamemaker subreddit */
    public static Vec2 intersectCircle(float x1, float y1, float x2, float y2, float cx, float cy, float cr){
        if(!Intersector.nearestSegmentPoint(x1, y1, x2, y2, cx, cy, v4).within(cx, cy, cr)) return null;
        
        cx = x1 - cx;
        cy = y1 - cy;

        float vx = x2 - x1,
                vy = y2 - y1,
                a = vx * vx + vy * vy,
                b = 2 * (vx * cx + vy * cy),
                c = cx * cx + cy * cy - cr * cr,
                det = b * b - 4 * a * c;

        if(a <= Mathf.FLOAT_ROUNDING_ERROR || det < 0){
            return null;
        }else if(det == 0f){
            float t = -b / (2 * a);
            float ix = x1 + t * vx;
            float iy = y1 + t * vy;

            return v4.set(ix, iy);
        }else{
            det = Mathf.sqrt(det);
            float t1 = (-b - det) / (2 * a);

            return v4.set(x1 + t1 * vx, y1 + t1 * vy);
        }
    }
    public static float angleDistSigned(float a, float b){
        a += 360f;
        a %= 360f;
        b += 360f;
        b %= 360f;
        float d = Math.abs(a - b) % 360f;
        int sign = (a - b >= 0f && a - b <= 180f) || (a - b <= -180f && a - b >= -360f) ? 1 : -1;
        return (d > 180f ? 360f - d : d) * sign;
    }

    public static class BasicPool<T> extends Pool<T>{
        Prov<T> prov;

        public BasicPool(Prov<T> f){
            prov = f;
        }

        @Override
        protected T newObject(){
            return prov.get();
        }
    }

    public static class Hit{
        Healthc entity;
        float x;
        float y;
    }

    public interface LineHitHandler<T>{
        void get(T t, float x, float y);
    }

    public interface QuadTreeHandler{
        boolean get(Rect rect, boolean tree);
    }
}
