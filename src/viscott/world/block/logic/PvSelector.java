package viscott.world.block.logic;

import arc.math.Mathf;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.blocks.logic.SwitchBlock;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;
import viscott.content.PvUIs;
import viscott.world.block.production.MultiCrafter;
import viscott.world.ui.PvUI;
import viscott.world.ui.SelectDialog;

import java.io.Reader;

import static mindustry.Vars.control;

public class PvSelector extends Block {
    public int configWidth = 4;
    public int maxConfigs = 3;
    public PvSelector(String name)
    {
        super(name);
        configurable = true;
        update = true;
        clearOnDoubleTap = true;
        autoResetEnabled = false;
        group = BlockGroup.logic;
        envEnabled = Env.any;
        config(Integer.class, (PvSelectorBuild entity, Integer i) -> entity.selectedIcon = i);
        configClear((PvSelectorBuild entity) -> {
            entity.selectedIcon = -1;
        });
    }
    public class PvSelectorBuild extends Building
    {
        Seq<TextureRegionDrawable> icons = new Seq<>();
        int selectedIcon = -1;

        @Override
        public Object config()
        {
            return selectedIcon;
        }

        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);
            Table ics = new Table();
            ics.background(Styles.black6);
            int i = 0;
            for(TextureRegionDrawable icon : icons)
            {
                ImageButton button = ics.button(Tex.whiteui, Styles.clearNoneTogglei, 40f, () -> {
                    control.input.config.hideConfig();
                }).get();
                button.getStyle().imageUp = icon;
                button.changed(() -> {if(button.isChecked()) configure(icons.indexOf(icon)); else configure(-1);} );
                button.update(() -> button.setChecked(selectedIcon == -1 ? false : icons.get(selectedIcon) == icon));
                if (i++ == configWidth)
                {
                    ics.row();
                    i %= configWidth;
                }
            }
            table.add(ics);
            Table adRem = new Table();
            adRem.background(Styles.black6);
            if (icons.size < maxConfigs) {
                ImageButton add = adRem.button(Tex.whiteui, Styles.clearNoneTogglei, 40, () -> {
                    control.input.config.hideConfig();
                }).get();
                add.getStyle().imageUp = Icon.add;
                add.changed(() -> {
                    PvUIs.extraUI.selectedDialog.call(this);
                });
            }
            if (icons.size > 0) {
                table.row();
                table.add("");
                table.row();

                ImageButton rem = adRem.button(Tex.whiteui, Styles.clearNoneTogglei, 40, () -> {
                    control.input.config.hideConfig();
                }).get();
                rem.getStyle().imageUp = Icon.cancel;
                rem.changed(() -> {
                    if (selectedIcon >= icons.size-1) configure(-1);
                    icons.pop();
                });
            }
            table.add(adRem);
        }

        public void addIcon(TextureRegionDrawable icon) {
            if (icons.size < maxConfigs)
                icons.add(icon);
        }
        public boolean hasIcon(TextureRegionDrawable icon) {
            return icons.find(i -> i == icon) != null;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.i(icons.size);
            for(var icon : icons)
                write.str(Icon.icons.findKey(icon,false));
            write.i(selectedIcon);
        }

        @Override
        public void read(Reads read, byte revision)
        {
            super.read(read,revision);
            int size = read.i();
            icons.clear();
            for(int i = 0;i<size;i++)
                icons.add(Icon.icons.get(read.str()));
            selectedIcon = read.i();
        }
    }
}
