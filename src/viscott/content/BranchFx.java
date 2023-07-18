package viscott.content;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.*;
import static mindustry.Vars.renderer;

public class BranchFx {
    public static final Effect

            smallBranch = new Effect(6f, 500f, e -> {
        if (!(e.data instanceof Seq)) return;
        Seq<Vec2> lines = e.data();
        int n = Mathf.clamp(1 + (int) (e.fin() * lines.size), 1, lines.size);
        for (int i = 2; i >= 0; i--) {
            stroke(3f * (i / 2f + 1f));
            color(i == 0 ? Color.valueOf("392f32") : Color.valueOf("766e4d"));
            alpha(i == 2 ? 0.5f : 1f);

            beginLine();
            for (int j = 0; j < n; j++) {
                linePoint(lines.get(j).x, lines.get(j).y);
            }
            endLine(false);
        }

        if (renderer.lights.enabled()) {
            for (int i = 0; i < n - 1; i++) {
                Drawf.light(lines.get(i).x, lines.get(i).y, lines.get(i + 1).x, lines.get(i + 1).y, 40f, Color.valueOf("766e4d"), 0.9f);
            }
        }
    }),

    smallBranchFade = new Effect(30f, 500f, e -> {
        if (!(e.data instanceof Seq)) return;
        Seq<Vec2> lines = e.data();
        for (int i = 2; i >= 0; i--) {
            stroke(3f * (i / 2f + 1f) * e.fout());
            color(i == 0 ? Color.valueOf("392f32") : Color.valueOf("766e4d"));
            alpha((i == 2 ? 0.5f : 1f) * e.fout());

            beginLine();
            for (Vec2 p : lines) {
                linePoint(p.x, p.y);
            }
            endLine(false);
        }

        if (renderer.lights.enabled()) {
            for (int i = 0; i < lines.size - 1; i++) {
                Drawf.light(lines.get(i).x, lines.get(i).y, lines.get(i + 1).x, lines.get(i + 1).y, 40f, Color.valueOf("766e4d"), 0.9f * e.fout());
            }
        }
    }),

    branch = new Effect(12f, 1300f, e -> {
        if (!(e.data instanceof Seq)) return;
        Seq<Vec2> lines = e.data();
        int n = Mathf.clamp(1 + (int) (e.fin() * lines.size), 1, lines.size);
        for (int i = 2; i >= 0; i--) {
            stroke(4.5f * (i / 2f + 1f));
            color(i == 0 ? Color.valueOf("392f32") : Color.valueOf("766e4d"));
            alpha(i == 2 ? 0.5f : 1f);

            beginLine();
            for (int j = 0; j < n; j++) {
                linePoint(lines.get(j).x, lines.get(j).y);
            }
            endLine(false);
        }

        if (renderer.lights.enabled()) {
            for (int i = 0; i < n - 1; i++) {
                Drawf.light(lines.get(i).x, lines.get(i).y, lines.get(i + 1).x, lines.get(i + 1).y, 40f, Color.valueOf("766e4d"), 0.9f);
            }
        }
    }),

    branchFade = new Effect(80f, 1300f, e -> {
        if (!(e.data instanceof Seq)) return;
        Seq<Vec2> lines = e.data();
        for (int i = 2; i >= 0; i--) {
            stroke(4.5f * (i / 2f + 1f) * e.fout());
            color(i == 0 ? Color.valueOf("392f32") : Color.valueOf("766e4d"));
            alpha((i == 2 ? 0.5f : 1f) * e.fout());

            beginLine();
            for (Vec2 p : lines) {
                linePoint(p.x, p.y);
            }
            endLine(false);
        }

        if (renderer.lights.enabled()) {
            for (int i = 0; i < lines.size - 1; i++) {
                Drawf.light(lines.get(i).x, lines.get(i).y, lines.get(i + 1).x, lines.get(i + 1).y, 40f, Color.valueOf("766e4d"), 0.9f * e.fout());
            }
        }
    });
}

    