package me.frandma.sausage.feature.setting;

import me.frandma.sausage.feature.Feature;
import net.minecraft.client.gui.DrawContext;

public class ModeSetting<T extends Enum<T>> extends Setting<T> {
  private final T[] modes;
  private boolean expanded = false;
  private static final int OPTION_HEIGHT = 20;
  private static final int ARROW_SIZE = 8;
  private static final int TOTAL_WIDTH = 150;
  double x, y;
  public ModeSetting(Feature feature, String name, T defaultValue) {
    super(feature, name, defaultValue);
    this.modes = getValue().getDeclaringClass().getEnumConstants();
  }
  @Override
  public int onRender(DrawContext drawContext, final int x, final int y) {
    this.x = x * mc.getWindow().getScaleFactor();
    this.y = y * mc.getWindow().getScaleFactor();
    drawContext.drawTextWithShadow(
        mc.textRenderer,
        getName(),
        x + 5,
        y + 5,
        0xFFFFFF
    );
    int selectionX = x + 100;
    drawContext.fill(
        selectionX,
        y,
        selectionX + TOTAL_WIDTH,
        y + OPTION_HEIGHT,
        0xFF333333
    );
    drawContext.drawTextWithShadow(
        mc.textRenderer,
        getValue().name(),
        selectionX + 5,
        y + 6,
        0xFFFFFF
    );
    int arrowX = selectionX + TOTAL_WIDTH - ARROW_SIZE - 5;
    int arrowY = y + (OPTION_HEIGHT - ARROW_SIZE) / 2;
    drawArrow(drawContext, arrowX, arrowY, expanded);
    if (expanded) {
      for (int i = 0; i < modes.length; i++) {
        if (modes[i] == getValue()) continue;
        int optionY = y + OPTION_HEIGHT * (i + 1);
        drawContext.fill(
            selectionX,
            optionY,
            selectionX + TOTAL_WIDTH,
            optionY + OPTION_HEIGHT,
            isMouseOver(mc.mouse.getX(), mc.mouse.getY(), selectionX, optionY)
                ? 0xFF444444
                : 0xFF333333
        );
        drawContext.drawTextWithShadow(
            mc.textRenderer,
            modes[i].name(),
            selectionX + 5,
            optionY + 6,
            0xFFFFFF
        );
      }

      return y + (OPTION_HEIGHT * modes.length);
    }
    return y + OPTION_HEIGHT;
  }
  private void drawArrow(DrawContext drawContext, int x, int y, boolean up) {
    int color = 0xFFFFFFFF;
    if (up) {
      for (int i = 0; i < ARROW_SIZE; i++) {
        drawContext.fill(
            x + i,
            y + ARROW_SIZE - i,
            x + ARROW_SIZE - i,
            y + ARROW_SIZE - i + 1,
            color
        );
      }
    } else {
      for (int i = 0; i < ARROW_SIZE; i++) {
        drawContext.fill(
            x + i,
            y + i,
            x + ARROW_SIZE - i,
            y + i + 1,
            color
        );
      }
    }
  }
  private boolean isMouseOver(double mouseX, double mouseY, double x, double y) {
    return mouseX >= x && mouseX <= x + TOTAL_WIDTH &&
        mouseY >= y && mouseY <= y + OPTION_HEIGHT;
  }
  @Override
  public void onClick(double mouseX, double mouseY, int button, int action) {
    if (action != 1) return;
    double selectionX = x + 100;
    if (isMouseOver(mouseX, mouseY, selectionX, y)) {
      expanded = !expanded;
      return;
    }
    if (expanded) {
      for (int i = 0; i < modes.length; i++) {
        if (modes[i] == getValue()) continue;
        double optionY = y + OPTION_HEIGHT * (i + 1);
        if (isMouseOver(mouseX, mouseY, selectionX, optionY)) {
          set(modes[i]);
          expanded = false;
          break;
        }
      }
    }
  }
  @Override
  public void onKey(int key, int action) {

  }
}
