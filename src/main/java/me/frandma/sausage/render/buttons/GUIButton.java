package me.frandma.sausage.render.buttons;

import lombok.Getter;
import lombok.Setter;
import me.frandma.sausage.event.events.MouseButtonEvent;
import me.frandma.sausage.event.events.MouseCursorPosEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

import java.util.function.Consumer;
import java.util.function.Function;

public class GUIButton {
  @Getter
  private final int width, height, xOffset, yButtonOffset;
  @Getter
  private int extraHeight;
  @Getter
  private RenderInfo latestRenderInfo;
  private Function<RenderInfo, Integer> renderConsumer;
  private Consumer<MouseButtonEvent> mouseButtonConsumer;
  private Consumer<MouseCursorPosEvent> mouseCursorPosConsumer;
  public GUIButton(int width, int height, int xOffset, int yButtonOffset) {
    this.width = width;
    this.height = height;
    this.xOffset = xOffset;
    this.yButtonOffset = yButtonOffset;
  }
  public GUIButton renderConsumer(Function<RenderInfo, Integer> renderConsumer) {
    this.renderConsumer = renderConsumer;
    return this;
  }
  public GUIButton clickConsumer(Consumer<MouseButtonEvent> mouseButtonConsumer) {
    this.mouseButtonConsumer = mouseButtonConsumer;
    return this;
  }
  public GUIButton mousePositionConsumer(Consumer<MouseCursorPosEvent> mouseCursorPosConsumer) {
    this.mouseCursorPosConsumer = mouseCursorPosConsumer;
    return this;
  }
  public int handleRender(RenderInfo renderInfo) {
    MatrixStack matrices = renderInfo.getDrawContext().getMatrices();
    matrices.push();
    int result = renderConsumer != null ? renderConsumer.apply(renderInfo) : 0;
    latestRenderInfo = renderInfo;
    matrices.pop();
    return result;
  }
  public boolean handleClick(MouseButtonEvent mouseButtonEvent) {
    if (mouseButtonConsumer == null || latestRenderInfo == null) return false;
    if (!isMouseOver(mouseButtonEvent.getMouseX(), mouseButtonEvent.getMouseY())) {
      if (mouseButtonEvent.getAction() == 0) mouseButtonConsumer.accept(mouseButtonEvent);
      return false;
    }
    mouseButtonConsumer.accept(mouseButtonEvent);
    return true;
  }
  public void handleMousePosition(MouseCursorPosEvent mouseCursorPosEvent) {
    if (mouseCursorPosConsumer == null) return;
    mouseCursorPosConsumer.accept(mouseCursorPosEvent);
  }
  public void resetExtraHeight() {
    extraHeight = 0;
  }
  public void addExtraHeight(int height) {
    extraHeight += height;
  }
  protected boolean isMouseOver(int mouseX, int mouseY) {
    return isMouseOver(latestRenderInfo.x + xOffset, latestRenderInfo.y + yButtonOffset, width, height + extraHeight, mouseX, mouseY);
  }
  protected boolean isMouseOver(double x, double y, double width, double height, int mouseX, int mouseY) {
    return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
  }
  @Getter
  public static class RenderInfo {
    private final DrawContext drawContext;
    private final int x, y;
    public RenderInfo(DrawContext drawContext, int x, int y) {
      this.drawContext = drawContext;
      this.x = x;
      this.y = y;
    }
  }
}
