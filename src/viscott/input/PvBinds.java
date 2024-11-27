package viscott.input;

import arc.KeyBinds;
import arc.input.InputDevice;
import arc.input.KeyCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static arc.Core.keybinds;

public enum PvBinds implements KeyBinds.KeyBind {
    strafe_left(KeyCode.q,"Special"),
    strafe_right(KeyCode.e),
    special_ability(KeyCode.mouseRight)
    ;

    KeyBinds.KeybindValue k;
    String category;
    PvBinds(KeyBinds.KeybindValue key, String category) {
        k = key;
        if (category != null)
            this.category = "Project Viscott " + category;
    }
    PvBinds(KeyBinds.KeybindValue key) {
        this(key,null);
    }

    @Override
    public String category() {
        return category;
    }
    @Override
    public KeyBinds.KeybindValue defaultValue(InputDevice.DeviceType deviceType) {
        return k;
    }

    public static void load() {

        KeyBinds.KeyBind[] merged = new KeyBinds.KeyBind[keybinds.getKeybinds().length+PvBinds.values().length];
        int i = 0;

        KeyBinds.KeyBind[] k1 = keybinds.getKeybinds();
        for(var k: k1)
            merged[i++] = k;

        KeyBinds.KeyBind[] k2 = PvBinds.values();
        for(var k: k2)
            merged[i++] = k;

        keybinds.setDefaults(merged);
    }
}
