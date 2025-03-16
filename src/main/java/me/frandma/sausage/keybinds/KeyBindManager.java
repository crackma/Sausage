package me.frandma.sausage.keybinds;

import me.frandma.sausage.feature.Feature;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import java.lang.reflect.Field;

public class KeyBindManager {
  public static void initFeature(Feature feature) {
    KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        feature.getName(),
        InputUtil.Type.KEYSYM,
        feature.getDefaultKeyBinding(),
        "Features"
    ));
    if (feature.isOnlyWhenHeldDown()) {
      try {
        Field pressedField = KeyBinding.class.getDeclaredField("pressed");
        pressedField.setAccessible(true);
        Field toggledField = Feature.class.getDeclaredField("toggled");
        toggledField.setAccessible(true);
          try {
            toggledField.setBoolean(feature, pressedField.getBoolean(keyBinding));
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      }
      return;
    }
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      if (keyBinding.wasPressed()) feature.toggle();
    });
  }
}
