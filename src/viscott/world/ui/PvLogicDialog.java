package viscott.world.ui;

import arc.Core;
import arc.func.Cons;
import arc.func.Prov;
import arc.graphics.Color;
import arc.scene.actions.Actions;
import arc.scene.ui.Image;
import arc.scene.ui.Label;
import arc.scene.ui.TextButton;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Align;
import arc.util.Log;
import arc.util.Nullable;
import arc.util.Time;
import mindustry.core.GameState;
import mindustry.ctype.Content;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.logic.*;
import mindustry.ui.Fonts;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import viscott.types.logic.PvCanvas;
import viscott.world.block.logic.PvLogicBlock;

import java.util.HashMap;

import static mindustry.Vars.*;
import static mindustry.logic.LCanvas.tooltip;

public class PvLogicDialog extends BaseDialog {
    public PvCanvas canvas;
    Cons<String> consumer = s -> {};
    boolean privileged;
    @Nullable LExecutor executor;

    Seq<Prov<LStatement>> statements;

    public PvLogicDialog(){
        super("Pvlogic");

        clearChildren();

        canvas = new PvCanvas(statements);
        shouldPause = true;

        addCloseListener();

        shown(this::setup);
        hidden(() -> consumer.get(canvas.save()));
        onResize(() -> {
            setup();
            canvas.rebuild();
        });

        add(canvas).grow().name("Pcanvas");

        row();

        add(buttons).growX().name("Pcanvas");
    }

    private Color typeColor(LExecutor.Var s, Color color){
        return color.set(
                !s.isobj ? Pal.place :
                s.objval == null ? Color.darkGray :
                s.objval instanceof String ? Pal.ammo :
                s.objval instanceof Content ? Pal.logicOperations :
                s.objval instanceof Building ? Pal.logicBlocks :
                s.objval instanceof Unit ? Pal.logicUnits :
                s.objval instanceof Team ? Pal.logicUnits :
                s.objval instanceof Enum<?> ? Pal.logicIo :
                Color.white
        );
    }

    private String typeName(LExecutor.Var s){
        return
                !s.isobj ? "number" :
                s.objval == null ? "null" :
                s.objval instanceof String ? "string" :
                s.objval instanceof Content ? "content" :
                s.objval instanceof Building ? "building" :
                s.objval instanceof Team ? "team" :
                s.objval instanceof Unit ? "unit" :
                s.objval instanceof Enum<?> ? "enum" :
                "unknown";
    }

    private void setup(){
        buttons.clearChildren();
        buttons.defaults().size(160f, 64f);
        buttons.button("@back", Icon.left, this::hide).name("back");

        buttons.button("@edit", Icon.edit, () -> {
            BaseDialog dialog = new BaseDialog("@editor.export");
            dialog.cont.pane(p -> {
                p.margin(10f);
                p.table(Tex.button, t -> {
                    TextButton.TextButtonStyle style = Styles.flatt;
                    t.defaults().size(280f, 60f).left();

                    t.button("@schematic.copy", Icon.copy, style, () -> {
                        dialog.hide();
                        Core.app.setClipboardText(canvas.save());
                    }).marginLeft(12f);
                    t.row();
                    t.button("@schematic.copy.import", Icon.download, style, () -> {
                        dialog.hide();
                        try{
                            canvas.allStatements = statements;
                            canvas.load(Core.app.getClipboardText().replace("\r\n", "\n"));
                        }catch(Throwable e){
                            ui.showException(e);
                        }
                    }).marginLeft(12f).disabled(b -> Core.app.getClipboardText() == null);
                });
            });

            dialog.addCloseButton();
            dialog.show();
        }).name("edit");

        if(Core.graphics.isPortrait()) buttons.row();

        buttons.button("@variables", Icon.menu, () -> {
            BaseDialog dialog = new BaseDialog("@variables");
            dialog.hidden(() -> {
                if(!wasPaused && !net.active()){
                    state.set(GameState.State.paused);
                }
            });

            dialog.shown(() -> {
                if(!wasPaused && !net.active()){
                    state.set(GameState.State.playing);
                }
            });

            dialog.cont.pane(p -> {
                p.margin(10f).marginRight(16f);
                p.table(Tex.button, t -> {
                    t.defaults().fillX().height(45f);
                    for(var s : executor.vars){
                        if(s.constant) continue;

                        Color varColor = Pal.gray;
                        float stub = 8f, mul = 0.5f, pad = 4;

                        t.add(new Image(Tex.whiteui, varColor.cpy().mul(mul))).width(stub);
                        t.stack(new Image(Tex.whiteui, varColor), new Label(" " + s.name + " ", Styles.outlineLabel){{
                            setColor(Pal.accent);
                        }}).padRight(pad);

                        t.add(new Image(Tex.whiteui, Pal.gray.cpy().mul(mul))).width(stub);
                        t.table(Tex.pane, out -> {
                            float period = 15f;
                            float[] counter = {-1f};
                            Label label = out.add("").style(Styles.outlineLabel).padLeft(4).padRight(4).width(140f).wrap().get();
                            label.update(() -> {
                                if(counter[0] < 0 || (counter[0] += Time.delta) >= period){
                                    String text = s.isobj ? LExecutor.PrintI.toString(s.objval) : Math.abs(s.numval - (long)s.numval) < 0.00001 ? (long)s.numval + "" : s.numval + "";
                                    if(!label.textEquals(text)){
                                        label.setText(text);
                                        if(counter[0] >= 0f){
                                            label.actions(Actions.color(Pal.accent), Actions.color(Color.white, 0.2f));
                                        }
                                    }
                                    counter[0] = 0f;
                                }
                            });
                            label.act(1f);
                        }).padRight(pad);

                        t.add(new Image(Tex.whiteui, typeColor(s, new Color()).mul(mul))).update(i -> i.setColor(typeColor(s, i.color).mul(mul))).width(stub);

                        t.stack(new Image(Tex.whiteui, typeColor(s, new Color())){{
                            update(() -> setColor(typeColor(s, color)));
                        }}, new Label(() -> " " + typeName(s) + " "){{
                            setStyle(Styles.outlineLabel);
                        }});

                        t.row();

                        t.add().growX().colspan(6).height(4).row();
                    }
                });
            });

            dialog.addCloseButton();
            dialog.show();
        }).name("variables").disabled(b -> executor == null || executor.vars.length == 0);

        buttons.button("@add", Icon.add, () -> {
            BaseDialog dialog = new BaseDialog("@add");
            dialog.cont.table(table -> {
                table.background(Tex.button);
                table.pane(t -> {
                    for(Prov<LStatement> prov : statements){
                        LStatement example = prov.get();
                        if(example instanceof LStatements.InvalidStatement || example.hidden() || (example.privileged() && !privileged) || (example.nonPrivileged() && privileged)) continue;

                        LCategory category = example.category();
                        Table cat = t.find(category.name);
                        if(cat == null){
                            t.table(s -> {
                                if(category.icon != null){
                                    s.image(category.icon, Pal.darkishGray).left().size(15f).padRight(10f);
                                }
                                s.add(category.localized()).color(Pal.darkishGray).left().tooltip(category.description());
                                s.image(Tex.whiteui, Pal.darkishGray).left().height(5f).growX().padLeft(10f);
                            }).growX().pad(5f).padTop(10f);

                            t.row();

                            cat = t.table(c -> {
                                c.top().left();
                            }).name(category.name).top().left().growX().fillY().get();
                            t.row();
                        }

                        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(Styles.flatt);
                        style.fontColor = category.color;
                        style.font = Fonts.outline;

                        cat.button(example.name(), style, () -> {
                            canvas.add(prov.get());
                            dialog.hide();
                        }).size(130f, 50f).self(c -> tooltip(c, "lst." + example.name())).top().left();

                        if(cat.getChildren().size % 3 == 0) cat.row();
                    }
                }).grow();
            }).fill().maxHeight(Core.graphics.getHeight() * 0.8f);
            dialog.addCloseButton();
            dialog.show();
        }).disabled(t -> canvas.statements.getChildren().size >= LExecutor.maxInstructions);
    }

    @Override
    public void hide(){
        super.hide();
        canvas.resetDialog();
    }

    public void show(Seq<Prov<LStatement>> statements, String code, LExecutor executor, boolean privileged, Cons<String> modified){
        this.statements = statements;
        this.executor = executor;
        this.privileged = privileged;
        canvas.statements.clearChildren();
        canvas.rebuild();
        //canvas.privileged = privileged;
        try{
            canvas.load(code);
        }catch(Throwable t){
            Log.err(t);
            canvas.load("");
        }
        this.consumer = result -> {
            if(!result.equals(code)){
                modified.get(result);
            }
        };

        show();
    }
}
