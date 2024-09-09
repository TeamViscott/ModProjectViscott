package viscott.gen.weapons;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.type.Weapon;
import viscott.gen.weapons.mounts.LinkedWeaponMount;

public class LinkedWeapon extends Weapon {
    public LinkedWeapon pauseForWeapon; // The weapon This weapon will pause for to shoot
    public LinkedWeapon waitTillWeapon; // The weapon This weapon will wait till its done shooting.

    // after a pauseWeapon has shot, a timeout could be wanted.
    public float timeout = 0;

    public LinkedWeapon() {
        super();
        mountType = LinkedWeaponMount::new;
    }
    public LinkedWeapon(String name) {
        super(name);
        mountType = LinkedWeaponMount::new;
    }

    public void setLink(LinkedWeapon weapon) {
        weapon.waitTillWeapon = this;
        this.pauseForWeapon = weapon;
    }

    @Override
    public void update(Unit unit, WeaponMount mount) {
        if (!(mount instanceof LinkedWeaponMount linkedmount)) {
            super.update(unit,mount);
            return; // smth went wrong there
        }

        boolean valid = true;
        float perc = 1;
        if (pauseForWeapon != null) {
            for (WeaponMount othermount : unit.mounts) {
                if (othermount.weapon != pauseForWeapon)
                    continue;

                if (!(othermount instanceof LinkedWeaponMount linkedothermount))
                    continue; // smth went wrong there

                if (othermount.shoot == true && (othermount.reload <= 0 || linkedothermount.cooldown > 0)) // if it can, pause this shortly
                    valid = false;
                perc = 1 - (linkedothermount.cooldown / pauseForWeapon.timeout);
            }
        }

        if (waitTillWeapon != null) {
            for (WeaponMount othermount : unit.mounts) {
                if (othermount.weapon != waitTillWeapon)
                    continue;
                if (othermount.reload > 0) // while weapon is still fireing, it shouldn't fire.
                    valid = false;


            }
        }

        linkedmount.cooldown = Math.max(linkedmount.cooldown - Time.delta * unit.reloadMultiplier, 0.0f);
        if (valid) {
            super.update(unit,mount);
        } else {
            mount.reload = Math.max(mount.reload - Time.delta * unit.reloadMultiplier, 0.0F);
            mount.recoil = Mathf.approachDelta(mount.recoil, 0.0F, unit.reloadMultiplier / this.recoilTime);
            mount.smoothReload = Mathf.lerpDelta(mount.smoothReload, mount.reload / this.reload, this.smoothReloadSpeed);
        }
        mount.charge = perc;
    }

    @Override
    public void shoot(Unit unit, WeaponMount mount, float shootX, float shootY, float rotation) {
        if (mount instanceof LinkedWeaponMount linkedmount)
            linkedmount.cooldown = timeout;
        super.shoot(unit,mount,shootX,shootY,rotation);
    }
}
