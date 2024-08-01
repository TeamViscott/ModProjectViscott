package viscott.world.block.logic;

import arc.func.Cons2;
import arc.math.Mathf;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.ctype.Content;
import mindustry.gen.Building;
import mindustry.gen.Call;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.logic.LAccess;
import mindustry.logic.Senseable;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;
import viscott.content.PvStats;
import viscott.content.PvUIs;

import static mindustry.Vars.control;
import static mindustry.logic.LAccess.config;

public class PvSelector extends Block {
    public int configWidth = 4;
    public int maxConfigs = 3;

    public PvSelector(String name)
    {
        super(name);
        configurable = true;
        logicConfigurable = true;
        update = true;
        clearOnDoubleTap = true;
        group = BlockGroup.logic;
        envEnabled = Env.any;
        saveConfig = true;
        config(Integer.class, (PvSelectorBuild entity, Integer i) -> {
            if (i == -2)
                entity.icons.pop();
            else
                if (i < entity.icons.size)
                    entity.selectedIcon = i;
        });
        config(String.class, (PvSelectorBuild entity, String n) -> {
            if (entity.icons.size >= maxConfigs)
                return;
            entity.icons.add(Icon.icons.get(n));
        });
        configClear((PvSelectorBuild entity) -> {
            entity.selectedIcon = -1;
        });
    }

    @Override public void setStats() {
        super.setStats();
        stats.add(PvStats.selectableAmount,maxConfigs);
    }

    public class PvSelectorBuild extends Building
    {
        Seq<TextureRegionDrawable> icons = new Seq<>();
        int selectedIcon = -1;

        @Override
        public void control(LAccess type, double p1, double p2, double p3, double p4) {
            if (type == config && logicConfigurable) {
                configure((int)Math.round(p1));
            }
            else
                super.control(type,p1,p2,p3,p4);
        }
        @Override
        public Object senseObject(LAccess sensor)
        {
            switch(sensor) {
                case config:
                    return Senseable.noSensed;
                default:
                    return super.senseObject(sensor);
            }
        }
        @Override
        public double sense(LAccess sensor)
        {
            switch(sensor) {
                case config:
                    return selectedIcon;
                default:
                    return super.sense(sensor);
            }
        }

        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);
            if (icons.size > 0) {
                Table ics = new Table();
                ics.background(Styles.black6);
                int i = 0;
                for (int index = 0; index < icons.size;index++) {
                    TextureRegionDrawable icon = icons.get(index);
                    ImageButton button = ics.button(Tex.whiteui, Styles.clearNoneTogglei, 40f, () -> {
                        control.input.config.hideConfig();
                    }).get();
                    button.getStyle().imageUp = icon;

                    int buttonIndex = index;
                    button.changed(() -> {
                        configure(button.isChecked() ? buttonIndex : -1);
                    });
                    button.update(() -> button.setChecked(buttonIndex == selectedIcon));
                    if (++i == configWidth) {
                        ics.row();
                        i %= configWidth;
                    }
                }
                table.add(ics);
            }
            if (!privileged || Vars.state.isEditor()) {
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
                        if (selectedIcon >= icons.size - 1) configure(-1);
                        configure(-2);
                    });
                }
                table.add(adRem);
            }
        }

        public void addIcon(TextureRegionDrawable icon) {
            if (icons.size < maxConfigs) {
                configure(Icon.icons.findKey(icon,true));
            }
        }
        public boolean hasIcon(TextureRegionDrawable icon) {
            return icons.find(i -> i == icon) != null;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.i(icons.size);
            for(var icon : icons)
                write.str(Icon.icons.findKey(icon,true));
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
