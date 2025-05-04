package me.frandma.sausage.render;

import lombok.Getter;
import lombok.Setter;
import me.frandma.sausage.event.events.KeyboardKeyEvent;
import me.frandma.sausage.event.events.MouseButtonEvent;
import me.frandma.sausage.event.events.MouseCursorPosEvent;
import net.minecraft.client.util.math.MatrixStack;

import java.util.function.Consumer;

// x and y are the offsets that are added on top of RenderInfo
@SuppressWarnings("UnusedReturnValue")
public class MenuButton implements UIComponent {
  @Getter
  @Setter
  private int x = 0, y = 0, width, height, renderWidth, renderHeight;
  @Getter
  private final int originalWidth, originalHeight;
  private Consumer<RenderInfo> renderConsumer;
  private Consumer<MouseButtonEvent> mouseButtonConsumer;
  private Consumer<MouseCursorPosEvent> mouseCursorPosConsumer;
  private Consumer<KeyboardKeyEvent> keyboardKeyConsumer;
  private RenderInfo lastRenderInfo = new RenderInfo(null);
  public MenuButton(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.renderWidth = width;
    this.renderHeight = height;
    this.originalWidth = width;
    this.originalHeight = height;
  }
  public MenuButton(int width, int height) {
    this.width = width;
    this.height = height;
    this.renderWidth = width;
    this.renderHeight = height;
    this.originalWidth = width;
    this.originalHeight = height;
  }
  public MenuButton renderConsumer(Consumer<RenderInfo> renderConsumer) {
    this.renderConsumer = renderConsumer;
    return this;
  }
  public MenuButton mouseClickConsumer(Consumer<MouseButtonEvent> mouseButtonConsumer) {
    this.mouseButtonConsumer = mouseButtonConsumer;
    return this;
  }
  public MenuButton mousePositionConsumer(Consumer<MouseCursorPosEvent> mouseCursorPosConsumer) {
    this.mouseCursorPosConsumer = mouseCursorPosConsumer;
    return this;
  }
  public MenuButton keyboardKeyConsumer(Consumer<KeyboardKeyEvent> keyboardKeyConsumer) {
    this.keyboardKeyConsumer = keyboardKeyConsumer;
    return this;
  }
  @Override
  public final void render(RenderInfo renderInfo) {
    MatrixStack matrices = renderInfo.getDrawContext().getMatrices();
    matrices.push();
    renderConsumer.accept(renderInfo);
    lastRenderInfo = renderInfo;
    matrices.pop();
  }
  @Override
  public final boolean mouseButton(MouseButtonEvent mouseButtonEvent) {
    if (mouseButtonConsumer == null) return false;
    if (!RenderUtil.isMouseOver(this)) {
      if (mouseButtonEvent.getAction() == 0) mouseButtonConsumer.accept(mouseButtonEvent);
      return false;
    }
    mouseButtonConsumer.accept(mouseButtonEvent);
    return RenderUtil.isMouseOver(this);
  }
  @Override
  public final void mousePosition(MouseCursorPosEvent mouseCursorPosEvent) {
    if (mouseCursorPosConsumer == null) return;
    mouseCursorPosConsumer.accept(mouseCursorPosEvent);
  }
  @Override
  public void keyboardKey(KeyboardKeyEvent keyboardKeyEvent) {
    if (keyboardKeyConsumer == null) return;
    keyboardKeyConsumer.accept(keyboardKeyEvent);
  }
  public int getX() {
    return x + lastRenderInfo.getX();
  }
  public int getY() {
    return y + lastRenderInfo.getY();
  }
  public int getXOffset() {
    return x;
  }
  public int getYOffset() {
    return y;
  }
}
