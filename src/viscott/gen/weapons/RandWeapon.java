package viscott.gen.weapons;

import arc.math.Mathf;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.type.Weapon;

public class RandWeapon extends Weapon {
    public float bulletSpeedMin = -1;
    public float bulletSpeedMax = -1;
    public RandWeapon() {
        super();
    }
    public RandWeapon(String name){
        super(name);
    }
    @Override public void update(Unit unit, WeaponMount weaponMount) {
        if (bulletSpeedMin > 0) {
            bullet.speed = Mathf.random(bulletSpeedMin,bulletSpeedMax);
        }
        super.update(unit,weaponMount);
    }
}
