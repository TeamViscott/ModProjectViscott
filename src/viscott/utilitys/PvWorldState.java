package viscott.utilitys;

import arc.Events;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.io.SaveFileReader;
import mindustry.io.SaveVersion;
import viscott.types.PvFaction;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PvWorldState {
    static public void load()
    {
        SaveVersion.addCustomChunk("factions", new SaveFileReader.CustomChunk() {
            @Override
            public void write(DataOutput stream) throws IOException {
                stream.writeInt(PvFaction.all.size);
                PvFaction.all.each(faction -> {
                    try {
                        stream.writeInt(faction.id);
                        stream.writeInt(faction.teamSize());
                        faction.allLinked().each(team -> {
                            try {
                                stream.writeInt(team.id);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            public void read(DataInput stream) throws IOException {
                int c1 = stream.readInt();
                for(;c1 > 0;c1--)
                {
                    int factionId = stream.readInt();
                    PvFaction curFac = PvFaction.all.get(factionId);
                    curFac.clearTeams();
                    int c2 = stream.readInt();
                    for(;c2 > 0;c2--)
                    {
                        int teamId = stream.readInt();
                        curFac.add(Team.get(teamId));
                    }
                }
            }
        });
    }
}
