package viscott.gen;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.core.World;
import mindustry.gen.MechUnit;

public class GridUnit extends MechUnit {
    public World innerWorld = new World();
    public boolean built = false;

    @Override
    public void update() {
        super.update();
    }

    @Override
    public int classId() {
        return 151;
    }

    @Override
    public void read(Reads read) {
        super.read(read);
    }

    @Override
    public void write(Writes write) {
        super.write(write);
    }
}
