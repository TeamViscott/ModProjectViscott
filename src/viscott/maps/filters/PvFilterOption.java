package viscott.maps.filters;

import arc.Core;
import arc.func.*;
import arc.input.KeyCode;
import arc.scene.Element;
import arc.scene.event.Touchable;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Button;
import arc.scene.ui.CheckBox;
import arc.scene.ui.Label;
import arc.scene.ui.Slider;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.Strings;
import mindustry.content.Blocks;
import mindustry.gen.Icon;
import mindustry.maps.Maps;
import mindustry.maps.filters.FilterOption;
import mindustry.maps.filters.GenerateFilter;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;

import static mindustry.Vars.*;
import static mindustry.Vars.content;

public class PvFilterOption {

    public static void init() {
        Prov<GenerateFilter>[] newFilters = new Prov[]{
                SpecificMirrorFilter::new
        };
        Prov<GenerateFilter>[] combinedFilets = new Prov[newFilters.length+Maps.allFilterTypes.length];
        int i = 0;
        for(Prov<GenerateFilter> f : Maps.allFilterTypes)
            combinedFilets[i++] = f;
        for(Prov<GenerateFilter> f : newFilters)
            combinedFilets[i++] = f;
        Maps.allFilterTypes = combinedFilets;
    }
    static class SliderOption extends FilterOption{
        final String name;
        final Floatp getter;
        final Floatc setter;
        final float min, max, step;

        boolean display = true;

        SliderOption(String name, Floatp getter, Floatc setter, float min, float max){
            this(name, getter, setter, min, max, (max - min) / 200);
        }

        SliderOption(String name, Floatp getter, Floatc setter, float min, float max, float step){
            this.name = name;
            this.getter = getter;
            this.setter = setter;
            this.min = min;
            this.max = max;
            this.step = step;
        }

        public SliderOption display(){
            display = true;
            return this;
        }

        @Override
        public void build(Table table){
            Element base;
            if(!display){
                Label l = new Label("@filter.option." + name);
                l.setWrap(true);
                l.setStyle(Styles.outlineLabel);
                base = l;

            }else{
                Table t = new Table().marginLeft(11f).marginRight(11f);
                base = t;
                t.add("@filter.option." + name).growX().wrap().style(Styles.outlineLabel);
                t.label(() -> Strings.autoFixed(getter.get(), 2)).style(Styles.outlineLabel).right().labelAlign(Align.right).padLeft(6);
            }
            base.touchable = Touchable.disabled;

            Slider slider = new Slider(min, max, step, false);
            slider.moved(setter);
            slider.setValue(getter.get());
            if(updateEditorOnChange){
                slider.changed(changed);
            }else{
                slider.released(changed);
            }

            table.stack(slider, base).colspan(2).pad(3).growX().row();
        }
    }

    static class BlockOption extends FilterOption{
        final String name;
        final Prov<Block> supplier;
        final Cons<Block> consumer;
        final Boolf<Block> filter;

        BlockOption(String name, Prov<Block> supplier, Cons<Block> consumer, Boolf<Block> filter){
            this.name = name;
            this.supplier = supplier;
            this.consumer = consumer;
            this.filter = filter;
        }

        @Override
        public void build(Table table){
            Button button = table.button(b -> b.image(supplier.get().uiIcon).update(i -> ((TextureRegionDrawable)i.getDrawable())
                    .setRegion(supplier.get() == Blocks.air ? Icon.none.getRegion() : supplier.get().uiIcon)).size(iconSmall), () -> {
                BaseDialog dialog = new BaseDialog("@filter.option." + name);
                dialog.cont.pane(t -> {
                    int i = 0;
                    for(Block block : content.blocks()){
                        if(!filter.get(block)) continue;

                        t.image(block == Blocks.air ? Icon.none.getRegion() : block.uiIcon).size(iconMed).pad(3).tooltip(block == Blocks.air ? "@none" : block.localizedName).get().clicked(() -> {
                            consumer.get(block);
                            dialog.hide();
                            changed.run();
                        });
                        if(++i % 10 == 0) t.row();
                    }
                    dialog.setFillParent(i > 100);
                }).scrollX(false);


                dialog.addCloseButton();
                dialog.show();
            }).pad(4).margin(12f).get();

            button.clicked(KeyCode.mouseMiddle, () -> {
                Core.app.setClipboardText(supplier.get().name);
                ui.showInfoFade("@copied");
            });

            button.clicked(KeyCode.mouseRight, () -> {
                if(content.block(Core.app.getClipboardText()) != null && filter.get(content.block(Core.app.getClipboardText()))){
                    consumer.get(content.block(Core.app.getClipboardText()));
                    changed.run();
                }
            });

            table.add("@filter.option." + name);
        }
    }

    static class ToggleOption extends FilterOption{
        final String name;
        final Boolp getter;
        final Boolc setter;

        ToggleOption(String name, Boolp getter, Boolc setter){
            this.name = name;
            this.getter = getter;
            this.setter = setter;
        }

        @Override
        public void build(Table table){
            table.row();
            CheckBox check = table.check("@filter.option." + name, setter).growX().padBottom(5).padTop(5).center().get();
            check.setChecked(getter.get());
            check.changed(changed);
        }
    }
}
