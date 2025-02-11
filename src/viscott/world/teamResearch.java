package viscott.world;

import arc.Core;
import arc.Events;
import mindustry.Vars;
import mindustry.entities.units.BuildPlan;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import viscott.types.PvFaction;

import java.util.concurrent.atomic.AtomicReference;

import static mindustry.Vars.state;

public class teamResearch extends Block {
    public PvFaction refTeam;
    public String localName;
    public teamResearch(String name, PvFaction team)
    {
        super(name);

        localName = Core.bundle.get("team."+name);
        localizedName= localName;
        refTeam = team;
        requirements(Category.effect, BuildVisibility.sandboxOnly, ItemStack.with());
        health = 1;
        update = true;
        rebuildable = false;
    }

    @Override
    public boolean isVisible(){
        return super.isVisible() && (!refTeam.isExtraFaction || Core.settings.getBool("pv-extra-stuff"));
    }

    @Override
    public void init()
    {
        super.init();
        description = refTeam.description;
        details = refTeam.info;
        Events.run(EventType.Trigger.update,()->{
             if (Vars.state.rules.infiniteResources || Vars.state.isEditor())
                 localizedName = localName + (refTeam.partOf(Vars.player.team()) ? " [red][Remove][]" : " [green][Add][]");
             else
                 localizedName = localName;
        });
    }

    @Override
    public void loadIcon(){
        fullIcon =Core.atlas.find(name);
        uiIcon = fullIcon;
    }

    @Override
    public boolean unlocked(){
        return Vars.ui.research.lastNode == refTeam.techTree;
    }

    public class teamResearchBuild extends Building
    {
        @Override
        public void created() {
            super.add();
            if (!refTeam.add(team))
                refTeam.remove(team);
        }

        @Override
        public void update() {
            if (!Vars.net.client())
                kill();
        }

        @Override
        public void killed() {
            if (Vars.net.client()) {
                AtomicReference<BuildPlan> plan = new AtomicReference<>(null);
                Vars.player.unit().plans.each(c -> {
                    if (c.build() == this)
                        plan.set(c);
                });
                if (plan.get() != null) {
                    Vars.player.unit().plans.remove(plan.get());
                }
            }
            super.killed();
        }
    }
}
