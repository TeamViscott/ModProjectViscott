package viscott.utilitys;

import arc.Events;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.io.SaveFileReader;
import mindustry.io.SaveVersion;
import mindustry.type.StatusEffect;
import viscott.types.PvFaction;
import viscott.world.statusEffects.StatusEffectStack;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PvWorldState {
    static public void load()
    {
        SaveVersion.addCustomChunk("factions", new SaveFileReader.CustomChunk() {
            @Override
            public void write(DataOutput stream) throws IOException {
                stream.writeInt(PvFaction.all.size); // Size of faction list
                PvFaction.all.each(faction -> {
                    try {
                        stream.writeInt(faction.id); // faction id
                        stream.writeInt(faction.teamSize()); // faction team
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
                int factionSize = PvFaction.all.size;
                for(;c1 > 0;c1--)
                {
                    int factionId = stream.readInt();
                    if (factionId >= factionSize) {
                        int c2 = stream.readInt();
                        for(;c2>0;c2--) stream.readInt();
                        continue;
                    }
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

        SaveVersion.addCustomChunk("stackEffects", new SaveFileReader.CustomChunk() {
            @Override
            public void write(DataOutput stream) throws IOException {
                int count = Vars.content.statusEffects().count(se -> se instanceof StatusEffectStack);
                stream.writeInt(count);
                Vars.content.statusEffects().each(se -> {
                    if (se instanceof StatusEffectStack st){
                        try {
                            stream.writeInt(st.id);
                            stream.writeInt(st.unitCharges.size());
                            st.unitCharges.forEach((unit,c) -> {
                                try {
                                    if (unit == null) return;
                                    stream.writeInt(unit.id);
                                    stream.writeInt(c);
                                    if (st.unitTeam.containsKey(unit))
                                        stream.writeInt(st.unitTeam.get(unit).id);
                                    else
                                        stream.writeInt(-1);
                                    stream.writeFloat(st.unitTime.get(unit));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }

            @Override
            public void read(DataInput stream) throws IOException {
                int count = stream.readInt();
                for(;count > 0;count--) {
                    int statusEffId = stream.readInt();
                    int unitsize = stream.readInt();
                    StatusEffect status = Vars.content.statusEffects().find(se -> se.id == statusEffId);
                    if (status instanceof StatusEffectStack st) {
                        st.unitTeam.clear();
                        st.unitTime.clear();
                        st.unitCharges.clear();
                        for(;unitsize > 0;unitsize--) {
                            int unitId = stream.readInt();
                            int unitSt = stream.readInt();
                            int unitT = stream.readInt();
                            float unitTime = stream.readFloat();
                            Unit unit = Groups.unit.find(u->u.id == unitId);
                            if (unit == null) continue;
                            st.unitCharges.put(unit,unitSt);
                            st.unitTeam.put(unit,Team.get(unitT));
                            st.unitTime.put(unit,unitTime);
                        }
                    }
                }
            }
        });
    }
}
