package viscott.utilitys;

import arc.Events;
import mindustry.game.EventType;

public class PvWorldState {
    static public void load()
    {
        Events.on(EventType.SaveLoadEvent.class,(sL) -> {

        });
    }
}
