package me.frandma.sausage.feature.features.render;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.ChangeInventorySlotEvent;
import me.frandma.sausage.event.events.ClipToSpaceEvent;
import me.frandma.sausage.event.events.MouseScrollEvent;
import me.frandma.sausage.event.events.PerspectiveChangeEvent;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import org.lwjgl.glfw.GLFW;

public class CameraClipFeature extends Feature {
  private float cameraDistance = 0;
  public CameraClipFeature() {
    this.toggled = true;
    super.name = "camera clip";
    super.category = Category.RENDER;
    EventManager.register(MouseScrollEvent.class, mouseScrollEvent -> {
      if (!toggled || mc.currentScreen != null) return;
      cameraDistance -= (float) mouseScrollEvent.getVertical();
    });
    EventManager.register(ClipToSpaceEvent.class, clipToSpaceEvent -> {
      if (!toggled) return;
      clipToSpaceEvent.setDistance(clipToSpaceEvent.getDistance() + cameraDistance);
    });
    EventManager.register(PerspectiveChangeEvent.class, perspectiveChangeEvent -> {
      if (!toggled) return;
      cameraDistance = 0;
    });
    EventManager.register(ChangeInventorySlotEvent.class, changeInventorySlotEvent -> {
      if (!toggled) return;
      changeInventorySlotEvent.cancel();
    });
  }
}
