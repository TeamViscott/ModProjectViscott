package viscott.world.block.effect;

import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.WeaponMount;
import mindustry.type.Weapon;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import viscott.types.BlockWeapon;
import viscott.types.BlockWeaponMount;

public class PartCore extends CoreBlock {

    public DrawBlock drawer;

    public Seq<BlockWeapon> weapons;


    public PartCore(String name) {
        super(name);
        drawer = new DrawDefault();
        weapons = new Seq<>();
    }

    @Override
    public void setStats() {
        super.setStats();
        for (BlockWeapon weapon : weapons) {
            weapon.addStats(this);
        }
    }

    @Override
    public void load() {
        super.load();
        drawer.load(this);
        for (BlockWeapon weapon : weapons) {
            weapon.load();
        }
    }

    public TextureRegion[] icons() {
        return this.drawer.finalIcons(this);
    }

    public void getRegionsToOutline(Seq<TextureRegion> out) {
        this.drawer.getRegionsToOutline(this, out);
    }

    public class PartCoreBuild extends CoreBuild {
        Seq<BlockWeaponMount> weaponMounds = new Seq<>();

        @Override
        public void created() {
            super.created();
            for (BlockWeapon weapon : weapons) {
                weaponMounds.add(new BlockWeaponMount(weapon));
            }
        }

        @Override
        public void updateTile() {
            super.updateTile();
            for (BlockWeaponMount weaponMount : weaponMounds) {
                weaponMount.weapon.update(this,weaponMount);
                if (weaponMount.shoot) {

                }
            }
        }

        @Override
        public void draw() {
            if (this.thrusterTime > 0.0F) {
                super.draw();
                return;
            }
            drawer.draw(this);
            for (BlockWeaponMount weaponMount : weaponMounds) {
                weaponMount.weapon.draw(this,weaponMount);
            }
        }

        @Override
        public float efficiency() {
            return 1.0F;
        }

        @Override
        public boolean enabled() {
            return true;
        }
    }
}
