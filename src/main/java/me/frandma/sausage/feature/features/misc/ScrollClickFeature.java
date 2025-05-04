package me.frandma.sausage.feature.features.misc;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.ChangeInventorySlotEvent;
import me.frandma.sausage.event.events.MouseScrollEvent;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.util.Util;
import net.minecraft.client.option.Perspective;

public class ScrollClickFeature extends Feature {
  public ScrollClickFeature() {
    super.name = "scroll click";
    super.category = Category.MISC;
    EventManager.register(MouseScrollEvent.class, mouseScrollEvent -> {
      if (!toggled) return;
      if (mc.options.getPerspective() != Perspective.FIRST_PERSON) return;
      if (mouseScrollEvent.getVertical() > 0) {
        Util.doAttack();
      } else {
        Util.doItemUse();
      }
    });
    EventManager.register(ChangeInventorySlotEvent.class, changeInventorySlotEvent -> {
      if (!toggled) return;
      changeInventorySlotEvent.cancel();
    });
  }
}
