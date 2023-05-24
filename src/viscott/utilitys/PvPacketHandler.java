package viscott.utilitys;

import arc.Events;
import arc.func.Cons;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Reflect;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Call;
import viscott.types.PvFaction;

import static mindustry.Vars.netClient;

public class PvPacketHandler {
    public static void load()
    {
        /*FACTION PACKET*/
        Events.on(EventType.PlayerJoin.class,(pJ)->{
            StringBuilder sb = new StringBuilder();
            PvFaction.all.forEach(faction -> {
                sb.append("!");
                sb.append(faction.packetLinks());
            });
            sb.replace(0,0,"");
            Call.clientPacketUnreliable(pJ.player.con,"recFactions",sb.toString());
        });

        ObjectMap<String, Seq<Cons<String>>> customPacketHandlers = Reflect.get(netClient,"customPacketHandlers");
        Seq<Cons<String>> cons = new Seq<>();
        cons.add(s -> {
            Seq<String> factionList = new Seq<>();
            String[] split1 = s.split("!");
            for(int i = 1;i<split1.length;i++)
                factionList.add(split1[i]);
            factionList.each(f->{
                String[] split2 = f.split(";");
                if (split2.length > 1) {
                    PvFaction curFaction = PvFaction.all.get(Integer.parseInt(split2[0]));
                    curFaction.clearTeams();
                    for (int i = 1; i < split2.length; i++)
                        curFaction.add(Team.get(Integer.parseInt(split2[i])));

                }
            });
        });
        customPacketHandlers.put("recFactions",cons);
    }
}
