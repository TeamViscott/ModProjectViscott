package viscott.input;

import arc.input.KeyBind;
import arc.input.KeyCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PvBinds {
    public static KeyBind strafe_left, strafe_right, special_ability
    ;

    static KeyBind addKeybind(String name,KeyBind.KeybindValue key) {
        return KeyBind.add(name,key);
    }
    static KeyBind addKeybind(String name,KeyBind.KeybindValue key, String category) {
        return KeyBind.add(name,key,category);
    }
    public static void load() {
        strafe_left = addKeybind("Strafe Left",KeyCode.q,"Special");
        strafe_right = addKeybind("Strafe Right",KeyCode.e);
        special_ability = addKeybind("Special Ability",KeyCode.mouseRight);
    }
}
