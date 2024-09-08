package viscott.gen.weapons;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.type.Weapon;

public class LinkedWeapon extends Weapon {
    public LinkedWeapon pauseForWeapon;
    public LinkedWeapon waitTillWeapon;

    // after a pauseWeapon has shot, a timeout could be wanted.
    public float timeout = 0;

    public LinkedWeapon(String name) {
        super(name);
    }

    public void setLink(LinkedWeapon weapon) {
        weapon.waitTillWeapon = this;
        this.pauseForWeapon = weapon;
    }

    @Override
    public void update(Unit unit, WeaponMount mount) {
        boolean valid = true;
        if (pauseForWeapon != null) {
            for (WeaponMount othermount : unit.mounts) {
                if (othermount.weapon != pauseForWeapon)
                    continue;

                if (othermount.shoot == true && othermount.reload <= 0) // if it can, pause this shortly
                    valid = false;
            }
        }

        if (waitTillWeapon != null) {
            for (WeaponMount othermount : unit.mounts) {
                if (othermount.weapon != waitTillWeapon)
                    continue;

                if (othermount.reload > 0) //
                    valid = false;
            }
        }

        if (valid) {
            super.update(unit,mount);
        } else {
            mount.reload = Math.max(mount.reload - Time.delta * unit.reloadMultiplier, 0.0F);
            mount.recoil = Mathf.approachDelta(mount.recoil, 0.0F, unit.reloadMultiplier / this.recoilTime);
            mount.smoothReload = Mathf.lerpDelta(mount.smoothReload, mount.reload / this.reload, this.smoothReloadSpeed);
            mount.charge = mount.charging && this.shoot.firstShotDelay > 0.0F ? Mathf.approachDelta(mount.charge, 1.0F, 1.0F / this.shoot.firstShotDelay) : 0.0F;
        }
    }
}
