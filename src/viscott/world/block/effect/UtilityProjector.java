package viscott.world.block.effect;

import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.type.Category;
import mindustry.type.StatusEffect;
import mindustry.world.Block;
import mindustry.world.blocks.ItemSelection;

import javax.swing.text.AbstractDocument;

public class UtilityProjector extends Block {
    public Seq<StatusEffect> statusEffects = new Seq<StatusEffect>();
    public float range = 60;
    Seq<Ability> abilities = new Seq<Ability>();
    public UtilityProjector(String name)
    {
        super(name);
        solid = true;
        destructible = true;
        update = true;
        category = Category.effect;
        configurable = true;
        clearOnDoubleTap = true;
        config(Integer.class,(UtilityProjectorBuild b,Integer i) -> {
            b.selectedEffect = i;
        });
        configClear((UtilityProjectorBuild b) -> {
            b.selectedEffect = -1;
        });
    }
    @Override
    public void init()
    {
        super.init();
    }
    public class UtilityProjectorBuild extends Building
    {
        Integer selectedEffect = -1;

        @Override
        public void updateTile()
        {
            if (selectedEffect > -1)
            {
                Groups.unit.forEach(u -> {
                    if (u.team == team && Mathf.len(u.x-x,u.y-y) <= range)
                        u.apply(statusEffects.get(selectedEffect),60f);
                });
            }
        }

        @Override
        public Object config()
        {
            return selectedEffect;
        }
        @Override
        public void buildConfiguration(Table table) {
            ItemSelection.buildTable(UtilityProjector.this, table, statusEffects,() -> selectedEffect > -1 ? statusEffects.get(selectedEffect) : null, icon -> configure(statusEffects.indexOf(icon)),selectionRows,selectionColumns);
        }
    }
}
