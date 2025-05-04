package me.frandma.sausage.render;

import lombok.Getter;
import lombok.Setter;
import me.frandma.sausage.event.events.KeyboardKeyEvent;
import me.frandma.sausage.event.events.MouseButtonEvent;
import me.frandma.sausage.event.events.MouseCursorPosEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public abstract class MenuPanel implements UIComponent {
  private final MinecraftClient mc = MinecraftClient.getInstance();
  @Getter
  @Setter
  private int x, y, width, height, renderWidth, renderHeight;
  public MenuPanel(int width, int height) {
    this.width = width;
    this.height = height;
  }
  public final void render(RenderInfo renderInfo) {
    MatrixStack matrices = renderInfo.getDrawContext().getMatrices();
    matrices.push();
    onRender(renderInfo);
    matrices.pop();
  }
  public final boolean mouseButton(MouseButtonEvent mouseButtonEvent) {
    if (!RenderUtil.isMouseOver(this)) return false;
    return onMouseButton(mouseButtonEvent);
  }
  public final void mousePosition(MouseCursorPosEvent mouseCursorPosEvent) {
    onMousePosition(mouseCursorPosEvent);
  }
  public final void keyboardKey(KeyboardKeyEvent keyboardKeyEvent) {
    onKeyboardKey(keyboardKeyEvent);
  }
  public abstract void onRender(RenderInfo renderInfo);
  public abstract boolean onMouseButton(MouseButtonEvent mouseButtonEvent);
  public abstract void onMousePosition(MouseCursorPosEvent mouseCursorPosEvent);
  public abstract void onKeyboardKey(KeyboardKeyEvent keyboardKeyEvent);
}