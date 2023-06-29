package viscott.types.logic;

import arc.Core;
import arc.func.Prov;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.input.KeyCode;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.scene.Element;
import arc.scene.event.HandCursorListener;
import arc.scene.event.InputEvent;
import arc.scene.event.InputListener;
import arc.scene.event.Touchable;
import arc.scene.ui.Label;
import arc.scene.ui.Tooltip;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.scene.ui.layout.WidgetGroup;
import arc.struct.Seq;
import arc.util.Align;
import arc.util.Reflect;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.logic.*;
import mindustry.ui.Styles;

public class PvCanvas extends LCanvas {
    public Seq<Prov<LStatement>> allStatements;

    public PvCanvas(Seq<Prov<LStatement>> statements) {
        super();
        allStatements = statements;
        resetDialog();
    }
    @Override
    public String save(){
        Seq<LStatement> st = statements.getChildren().<StatementElem>as().map(s -> s.st);
        st.each(LStatement::saveUI);

        return PvAssembler.write(st);
    }

    public void resetDialog() {
        Reflect.set(LCanvas.class,"canvas", Vars.ui.logic.canvas);
    }
    public void setDialog() {
        Reflect.set(LCanvas.class,"canvas", this);
    }

    @Override
    public void load(String asm){
        setDialog();
        jumps.clear();

        Seq<LStatement> statements = PvAssembler.pvRead(asm, false);
        statements.truncate(LExecutor.maxInstructions);
        this.statements.clearChildren();
        for(LStatement st : statements){
            add(st);
        }

        for(LStatement st : statements){
            st.setupUI();
        }

        this.statements.layout();
    }
}
