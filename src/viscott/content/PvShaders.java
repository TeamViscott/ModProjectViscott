package viscott.content;

import arc.Core;
import arc.graphics.gl.Shader;
import arc.scene.ui.layout.Scl;
import arc.util.Time;
import mindustry.Vars;

public class PvShaders {
    public static ModShieldShader nullisAura;
    public static void init() {
        if (!Vars.headless) {
            nullisAura = new ModShieldShader("nullisAura", "screenspace");
        }
    }

    public static class ModShieldShader extends ModShader {
        public ModShieldShader(String frag, String vert) {
            super(frag, vert);
        }

        @Override
        public void apply() {
            setUniformf("u_time", Time.time / Scl.scl(1f));
            setUniformf("u_offset",
                    Core.camera.position.x - Core.camera.width / 2,
                    Core.camera.position.y - Core.camera.height / 2);
            setUniformf("u_texsize", Core.camera.width, Core.camera.height);
            setUniformf("u_invsize", 1f / Core.camera.width, 1f / Core.camera.height);
        }
    }


    public static class ModShader extends Shader {
        public ModShader(String frag) {
            super(Vars.tree.get("shaders/screenspace.vert"),Vars.tree.get("shaders/" + frag + ".frag"));
        }
        public ModShader(String frag, String vert) {
            super(Vars.tree.get("shaders/" + vert + ".vert"), Vars.tree.get("shaders/" + frag + ".frag"));
        }
    }
}
