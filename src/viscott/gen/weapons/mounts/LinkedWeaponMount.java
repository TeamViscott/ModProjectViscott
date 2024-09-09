package viscott.gen.weapons.mounts;

import mindustry.entities.units.WeaponMount;
import mindustry.type.Weapon;

public class LinkedWeaponMount extends WeaponMount {
    public float cooldown;
    public LinkedWeaponMount(Weapon weapon) {
        super(weapon);
    }
}
