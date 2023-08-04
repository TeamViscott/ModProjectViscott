package viscott.utilitys;

import arc.Events;
import arc.func.Cons;
import arc.struct.Queue;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.core.World;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.world.Block;
import mindustry.world.Tile;
import viscott.gen.GridUnit;
import viscott.types.GridUnitType;
import viscott.types.PvFaction;

import java.io.*;

import static mindustry.Vars.netClient;
import static mindustry.Vars.world;

public class PvPacketHandler {
    public static void gridUnitLoad(GridUnit unit) {
            if (unit == null) return;
            StringBuilder sb = new StringBuilder();
            Writes writer = new Writes(new DataOutput() {
                @Override
                public void write(int b) throws IOException {
                    sb.append(";w");
                    sb.append(b);
                }

                @Override
                public void write(byte[] b) throws IOException {
                    sb.append(";w");
                    sb.append(b);
                }

                @Override
                public void write(byte[] b, int off, int len) throws IOException {
                    sb.append(";w");
                    sb.append(b);

                }

                @Override
                public void writeBoolean(boolean v) throws IOException {
                    sb.append(";x");
                    sb.append(v);

                }

                @Override
                public void writeByte(int v) throws IOException {
                    sb.append(";b");
                    sb.append(v);

                }

                @Override
                public void writeShort(int v) throws IOException {
                    sb.append(";s");
                    sb.append(v);

                }

                @Override
                public void writeChar(int v) throws IOException {
                    sb.append(";c");
                    sb.append(v);

                }

                @Override
                public void writeInt(int v) throws IOException {
                    sb.append(";i");
                    sb.append(v);

                }

                @Override
                public void writeLong(long v) throws IOException {
                    sb.append(";l");
                    sb.append(v);

                }

                @Override
                public void writeFloat(float v) throws IOException {
                    sb.append(";f");
                    sb.append(v);
                }

                @Override
                public void writeDouble(double v) throws IOException {
                    sb.append(";d");
                    sb.append(v);
                }

                @Override
                public void writeBytes(String s) throws IOException {
                    sb.append(";b");
                    sb.append(s.getBytes().toString());
                }

                @Override
                public void writeChars(String s) throws IOException {
                    sb.append(";e");
                    sb.append(s);
                }

                @Override
                public void writeUTF(String s) throws IOException {
                    sb.append(";u");
                    sb.append(s);
                }
            });
            writer.i(unit.id());
            unit.innerWorld.tiles.each((x,y) -> {
                Tile tile = unit.innerWorld.tile(x, y);
                if (tile.block() == null || tile.build == null || tile.build.tile != tile) {
                    writer.i(-1);
                    return;
                }
                writer.i(tile.block().id);
                writer.b(tile.build.rotation);
                tile.build.write(writer);
            });
            Call.clientPacketReliable("gridUnitUpdate",sb.toString().substring(1));
            Log.info(sb.toString().substring(1));
    };
    public static void load()
    {
        /*FACTION PACKET*/
        Events.on(EventType.PlayerJoin.class,(pJ)->{
            if (!Vars.net.server()) return;
            StringBuilder sb = new StringBuilder();
            PvFaction.all.each(faction -> {
                sb.append("!");
                sb.append(faction.packetLinks());
            });
            sb.replace(0,0,"");
            Call.clientPacketReliable(pJ.player.con,"recFactions",sb.toString());
        });

        Cons<String> cons1 = (s -> {
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
        netClient.addPacketHandler("recFactions", cons1);

        Cons<String> cons2 = (s -> {
            Log.info(s);
            Seq<String> ss = Seq.with(s.split(";"));
            Queue<Integer> il = new Queue<>();
            Queue<Boolean> xl = new Queue<>();
            Queue<Byte> bl = new Queue<>();
            Queue<Short> sl = new Queue<>();
            Queue<Character> cl = new Queue<>();
            Queue<Double> dl = new Queue<>();
            Queue<Float> fl = new Queue<>();
            Queue<Long> ll = new Queue<>();
            Queue<String> ul = new Queue<>();
            Queue<String> el = new Queue<>();
            ss.each(v -> {
                String value = v.substring(1);
                switch(v.charAt(0)) {
                    case 'i':
                        il.add(Integer.parseInt(value));
                        break;
                    case 'x':
                        xl.add(value == "true");
                        break;
                    case 'b':
                        bl.add(Byte.parseByte(value));
                        break;
                    case 'c':
                        cl.add(Character.valueOf(value.charAt(0)));
                        break;
                    case 's':
                        sl.add(Short.parseShort(value));
                        break;
                    case 'd':
                        dl.add(Double.parseDouble(value));
                        break;
                    case 'f':
                        fl.add(Float.parseFloat(value));
                        break;
                    case 'l':
                        ll.add(Long.parseLong(value));
                        break;
                    case 'e':
                        el.add(value);
                        break;
                    case 'u':
                        ul.add(value);
                        break;
                    default:
                        break;
                }
            });


            DataInput di = new DataInput() {
                @Override
                public void readFully(byte[] b) throws IOException {

                }

                @Override
                public void readFully(byte[] b, int off, int len) throws IOException {

                }

                @Override
                public int skipBytes(int n) throws IOException {
                    return 0;
                }

                @Override
                public boolean readBoolean() throws IOException {
                    return xl.removeFirst();
                }

                @Override
                public byte readByte() throws IOException {
                    return bl.removeFirst();
                }

                @Override
                public int readUnsignedByte() throws IOException {
                    return 0;
                }

                @Override
                public short readShort() throws IOException {
                    return sl.removeFirst();
                }

                @Override
                public int readUnsignedShort() throws IOException {
                    return 0;
                }

                @Override
                public char readChar() throws IOException {
                    return cl.removeFirst();
                }

                @Override
                public int readInt() throws IOException {
                    return il.removeFirst();
                }


                @Override
                public long readLong() throws IOException {
                    return ll.removeFirst();
                }

                @Override
                public float readFloat() throws IOException {
                    return fl.removeFirst();
                }

                @Override
                public double readDouble() throws IOException {
                    return dl.removeFirst();
                }

                @Override
                public String readLine() throws IOException {
                    return el.removeFirst();
                }

                @Override
                public String readUTF() throws IOException {
                    return ul.removeFirst();
                }
            };
            Reads reader = new Reads(di);
            int unitid = reader.i();
            Unit u = Groups.unit.find(e->e.id == unitid);
            if (u == null) return;
            GridUnit gu = (GridUnit) u;
            if (gu.gu == null) {
                gu.gu = (GridUnitType) gu.type;
                gu.build();
            }
            World w = Vars.world;
            Vars.world = gu.innerWorld;
            gu.innerWorld.tiles.each((x,y) -> {
                int bi = reader.i();
                if (bi == -1) return;
                Block b = Vars.content.block(bi);
                Building bu = b.newBuilding();
                bu.rotation = reader.b();
                world.tile(x,y).setBlock(b,gu.team,bu.rotation,()->bu);
                bu.read(reader,(byte)0);
                Groups.all.remove(bu);
                bu.setIndex__all(-1);
            });
            Vars.world = w;
        });
        netClient.addPacketHandler("gridUnitUpdate", cons2);
    }
}
