package viscott.gen;

import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;

public interface TechtreeObjective {
    public static Objectives.Objective newObjective(UnlockableContent content) {
        return new Objectives.Objective() {
            @Override
            public boolean complete() {
                return content.unlocked();
            }

            @Override
            public String display() {
                return "research : " + content.localizedName;
            }
        };
    }
}
