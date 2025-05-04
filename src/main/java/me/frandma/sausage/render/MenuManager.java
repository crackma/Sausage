package me.frandma.sausage.render;

import lombok.Setter;
import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

import java.util.HashSet;
import java.util.Set;

public class MenuManager {
  private final MinecraftClient mc = MinecraftClient.getInstance();
  private boolean opened = false;
  @Setter
  private Set<MenuPanel> panels = new HashSet<>();
  public MenuManager(MenuPanel menu) {
    panels.add(menu);
    EventManager.register(RenderEvent.class, this::handleRender);
    EventManager.register(MouseCursorPosEvent.class, this::handleMousePos);
    EventManager.register(MouseButtonEvent.class, this::handleMouseButton);
    EventManager.register(KeyboardCharEvent.class, this::handleKeyboardChar);
    EventManager.register(KeyboardKeyEvent.class, this::handleKeyboardKey);
    EventManager.register(MouseLockEvent.class, this::handleMouseLock);
    EventManager.register(MouseScrollEvent.class, this::handleMouseScroll);
  }
  private void handleRender(RenderEvent event) {
    if (!opened) return;
    DrawContext drawContext = event.getDrawContext();
    drawContext.getMatrices().push();
    MatrixStack matrices = drawContext.getMatrices();
    matrices.translate(0, 0, 500);
    int scaleFactor = (int) mc.getWindow().getScaleFactor();
    if (scaleFactor != 1) matrices.scale(1.0f / scaleFactor, 1.0f / scaleFactor, 1.0f);
    panels.forEach(menuPanel -> menuPanel.render(new RenderInfo(drawContext, menuPanel.getX(), menuPanel.getY())));
    drawContext.getMatrices().pop();
  }
  private void handleMousePos(MouseCursorPosEvent event) {
    if (!opened) return;
    RenderUtil.updatePositions(event.getX(), event.getY());
    event.cancel();
    panels.forEach(menuPanel ->  {
      menuPanel.mousePosition(event);
      if (!(menuPanel instanceof Draggable draggable)) return;
      if (!draggable.isDragging()) return;
      int tempX = menuPanel.getX() + RenderUtil.mouseX - RenderUtil.lastMouseX;
      int tempY = menuPanel.getY() + RenderUtil.mouseY - RenderUtil.lastMouseY;
      if (tempX >= 0 && tempX + menuPanel.getWidth() <= mc.getWindow().getWidth()) menuPanel.setX(tempX);
      if (tempY >= 0 && tempY + menuPanel.getHeight() <= mc.getWindow().getHeight()) menuPanel.setY(tempY);
    });
  }
  private void handleMouseButton(MouseButtonEvent event) {
    if (!opened) return;
    event.cancel();
    for (MenuPanel menuPanel : panels) {
      if (menuPanel.mouseButton(event)) return;
      if (!(menuPanel instanceof Draggable draggable)) return;
      int button = event.getButton();
      int action = event.getAction();
      if (draggable.isDragging() && action == 0) draggable.setDragging(false);
      if (RenderUtil.isMouseOver(menuPanel) && button == 0) draggable.setDragging(action == 1);
    }
  }
  private void handleKeyboardChar(KeyboardCharEvent event) {
    if (opened) event.cancel();
  }
  private void handleKeyboardKey(KeyboardKeyEvent event) {
    if (event.getKey() == 344) event.cancel();
    if (event.getKey() == 344 && event.getAction() == 1) {
      toggle();
      event.cancel();
    }
    if (opened && event.getAction() == 1) event.cancel();
  }
  private void handleMouseLock(MouseLockEvent event) {
    if (opened) event.cancel();
  }
  private void handleMouseScroll(MouseScrollEvent event) {
    if (opened) event.cancel();
  }
  public void toggle() {
    opened = !opened;
    if (opened) {
      mc.mouse.unlockCursor();
    } else {
      if (mc.getOverlay() == null && mc.currentScreen == null) mc.mouse.lockCursor();
    }
  }
}
