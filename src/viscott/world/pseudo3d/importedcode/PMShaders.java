package viscott.world.pseudo3d.importedcode;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.graphics.gl.*;
import arc.scene.ui.layout.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class PMShaders{
    public static MaterializeShader materialize;
    public static VerticalBuildShader vertBuild;
    public static BlockBuildCenterShader blockBuildCenter;
    public static TractorConeShader tractorCone;

    public static void init(){
        materialize = new MaterializeShader();
        vertBuild = new VerticalBuildShader();
        blockBuildCenter = new BlockBuildCenterShader();
        tractorCone = new TractorConeShader();
    }

    public static class MaterializeShader extends PMLoadShader{
        public float progress, offset, time;
        public int shadow;
        public Color color = new Color();
        public TextureRegion region;

        MaterializeShader(){
            super("materialize");
        }

        @Override
        public void apply(){
            setUniformf("u_progress", progress);
            setUniformf("u_offset", offset);
            setUniformf("u_time", time);
            setUniformf("u_width", region.width);
            setUniformf("u_shadow", shadow);
            setUniformf("u_color", color);
            setUniformf("u_uv", region.u, region.v);
            setUniformf("u_uv2", region.u2, region.v2);
            setUniformf("u_texsize", region.texture.width, region.texture.height);
        }
    }

    public static class VerticalBuildShader extends PMLoadShader{
        public float progress, time;
        public Color color = new Color();
        public TextureRegion region;

        public VerticalBuildShader(){
            super("vertbuild");
        }

        @Override
        public void apply(){
            setUniformf("u_time", time);
            setUniformf("u_color", color);
            setUniformf("u_progress", progress);
            setUniformf("u_uv", region.u, region.v);
            setUniformf("u_uv2", region.u2, region.v2);
            setUniformf("u_texsize", region.texture.width, region.texture.height);
        }
    }

    public static class BlockBuildCenterShader extends PMLoadShader{
        public float progress;
        public TextureRegion region;
        public float time;

        BlockBuildCenterShader(){
            super("blockbuildcenter");
        }

        @Override
        public void apply(){
            setUniformf("u_progress", progress);
            setUniformf("u_uv", region.u, region.v);
            setUniformf("u_uv2", region.u2, region.v2);
            setUniformf("u_time", time);
            setUniformf("u_texsize", region.texture.width, region.texture.height);
        }
    }

    public static class TractorConeShader extends PMLoadShader{
        public float cx, cy;
        public float time, spacing, thickness;

        TractorConeShader(){
            super("screenspace", "tractorcone");
        }

        @Override
        public void apply(){
            setUniformf("u_dp", Scl.scl(1f));
            setUniformf("u_time", time / Scl.scl(1f));
            setUniformf("u_offset",
                    Core.camera.position.x - Core.camera.width / 2,
                    Core.camera.position.y - Core.camera.height / 2);
            setUniformf("u_texsize", Core.camera.width, Core.camera.height);

            setUniformf("u_spacing", spacing / Scl.scl(1f));
            setUniformf("u_thickness", thickness / Scl.scl(1f));
            setUniformf("u_cx", cx / Scl.scl(1f));
            setUniformf("u_cy", cy / Scl.scl(1f));
        }

        public void setCenter(float cx, float cy){
            /*Vec2 c = Core.camera.project(cx, cy);
            this.cx = c.x;
            this.cy = c.y;*/
            this.cx = cx;
            this.cy = cy;
        }
    }

    static class PMLoadShader extends Shader{
        PMLoadShader(String vert, String frag){
            super(
                    files.internal("shaders/" + vert + ".vert"),
                    tree.get("shaders/" + frag + ".frag")
            );
        }

        PMLoadShader(String frag){
            this("default", frag);
        }
    }
}
