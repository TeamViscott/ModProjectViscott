package viscott.gen;

import arc.graphics.Color;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.math.geom.Vec2;
import arc.math.geom.Vec3;
import arc.struct.FloatSeq;
import arc.struct.ObjectMap;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Structs;
import arc.util.Tmp;
import arc.util.noise.Ridged;
import arc.util.noise.Simplex;
import mindustry.ai.Astar;
import mindustry.ai.BaseRegistry;
import mindustry.content.Blocks;
import mindustry.content.Liquids;
import mindustry.game.Schematics;
import mindustry.game.Team;
import mindustry.game.Waves;
import mindustry.graphics.Pal;
import mindustry.maps.generators.BaseGenerator;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.type.Sector;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.TileGen;
import mindustry.world.Tiles;
import mindustry.world.blocks.environment.Floor;
import viscott.content.PvBlocks;

import static mindustry.Vars.*;
/* SerpuloPlanetGenerator */
public class VercilusPlanetGenerator extends PlanetGenerator {
    public static boolean alt = false;
    BaseGenerator basegen = new BaseGenerator();
    float scl = 5f;
    float waterOffset = 0.07f;
    boolean genLakes = false;

    ObjectMap<Block, Block> dec = ObjectMap.of(
            Blocks.sporeMoss, Blocks.sporeCluster,
            Blocks.moss, Blocks.sporeCluster,
            Blocks.taintedWater, Blocks.water,
            Blocks.darksandTaintedWater, Blocks.darksandWater
    );

    public float rawHeight(Vec3 position){
        position = Tmp.v33.set(position).scl(scl);
        return (Mathf.pow(Simplex.noise3d(seed, 7, 0.5f, 1f/3f, position.x, position.y, position.z), 2.3f) + waterOffset) / (1f + waterOffset);
    }

    @Override
    public float getHeight(Vec3 position) {
        return Simplex.noise3d(seed,7,0.5f,1f/3f,position.x,position.y,position.z)*2f-2f;
    }

    @Override
    public Color getColor(Vec3 position){
        if (Simplex.noise3d(seed,7,0.5f,1f/3f,position.x,position.y,position.z)*1f < 0.5f)
            return Pal.darkFlame;
        return Pal.redLight;
    }

    @Override
    public void generate(Tiles tiles, Sector sec, int seed){
        this.tiles = tiles;
        this.seed = seed + baseSeed;
        this.sector = sec;
        this.width = tiles.width;
        this.height = tiles.height;
        this.rand.setSeed(sec.id + seed + baseSeed);

        TileGen gen = new TileGen();
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                gen.reset();
                Vec3 position = sector.rect.project(x / (float)tiles.width, y / (float)tiles.height);

                genTile(position, gen);
                tiles.set(x, y, new Tile(x, y, gen.floor, gen.overlay, gen.block));
            }
        }

        generate(tiles);
    }

}
