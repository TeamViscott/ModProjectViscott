package viscott.types;

import arc.util.Nullable;
import mindustry.audio.SoundLoop;
import mindustry.gen.Bullet;
import mindustry.gen.Teamc;

public class BlockWeaponMount {
    public final BlockWeapon weapon;
    public float reload;
    public float rotation;
    public float recoil;
    @Nullable
    public float[] recoils;
    public float targetRotation;
    public float heat;
    public float warmup;
    public boolean charging;
    public float charge;
    public float smoothReload;
    public float aimX;
    public float aimY;
    public boolean shoot = false;
    public boolean rotate = false;
    public int cost;
    public int totalShots;
    public int barrelCounter;
    @Nullable
    public Bullet bullet;
    @Nullable
    public SoundLoop sound;
    @Nullable
    public Teamc target;
    public float retarget = 0.0F;

    public BlockWeaponMount(BlockWeapon weapon) {
        this.weapon = weapon;
        this.rotation = weapon.baseRotation;
    }
}
