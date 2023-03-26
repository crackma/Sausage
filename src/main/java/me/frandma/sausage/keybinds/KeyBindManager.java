package me.frandma.sausage.keybinds;

import me.frandma.sausage.feature.Feature;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeyBindManager {

    public static void initFeature(Feature feature) {
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.sausage.toggle_" + feature.getName(),
                InputUtil.Type.KEYSYM,
                feature.getKeyBinding(),
                "key.category.sausage.Features"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!keyBinding.wasPressed()) return;
            feature.toggle();
        });
    }
}
