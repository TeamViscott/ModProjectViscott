package viscott.types.logic;

import arc.Core;
import arc.func.Prov;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.input.KeyCode;
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
import arc.util.Tmp;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.logic.*;
import mindustry.ui.Styles;

public class PvCanvas extends LCanvas {
    StatementElem dragging;
    public Seq<Prov<LStatement>> allStatements;
    float targetWidth;

    @Override
    public String save(){
        Seq<LStatement> st = statements.getChildren().<StatementElem>as().map(s -> s.st);
        st.each(LStatement::saveUI);

        return LAssembler.write(st);
    }
}
