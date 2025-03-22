package me.frandma.sausage.feature.features;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.ChangeInventorySlotEvent;
import me.frandma.sausage.event.events.MouseScrollEvent;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.util.Utils;
import org.lwjgl.glfw.GLFW;

public class ScrollClickFeature extends Feature {
  public ScrollClickFeature() {
    super.name = "scroll click";
    super.category = Category.PLAYER;
    super.defaultKeyBinding = GLFW.GLFW_KEY_F7;
    EventManager.register(MouseScrollEvent.class, mouseScrollEvent -> {
      if (!toggled) return;
      if (mouseScrollEvent.getVertical() > 0) {
        Utils.doAttack();
      } else {
        Utils.doItemUse();
      }
    });
    EventManager.register(ChangeInventorySlotEvent.class, changeInventorySlotEvent -> {
      if (!toggled) return;
      changeInventorySlotEvent.cancel();
    });
  }
}
