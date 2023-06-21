package viscott.world.block.unit;

import mindustry.world.blocks.defense.Wall;

public class PvTemplate extends Wall {
    public PvTemplate(String name) {
        super(name);
    }

    @Override
    public boolean isVisible() {
        return false;
    }
    @Override
    public boolean isHidden() {
        return false;
    }

    public class PvTemplateBuild extends WallBuild {}
}
