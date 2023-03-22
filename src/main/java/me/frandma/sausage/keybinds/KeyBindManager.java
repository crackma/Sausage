package me.frandma.sausage.keybinds;

import me.frandma.sausage.feature.Feature;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

public class KeyBindManager {

    private MinecraftClient client = MinecraftClient.getInstance();

    public void initKeyBind(Feature feature) {
        //make the keybind
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.sausage.toggle_" + feature.getName(),
                InputUtil.Type.KEYSYM,
                feature.getKeyBinding(),
                "key.category.sausage.sausage"
        ));
        //listen for keybind? maybe
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!keyBinding.wasPressed()) return;
            feature.toggle();
            client.player.sendMessage(Text.of(feature.getName() + (feature.isToggled() ? " was enabled" : "was disabled")));
        });
    }
}
