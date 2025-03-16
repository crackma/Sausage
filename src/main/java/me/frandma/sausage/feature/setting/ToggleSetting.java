package me.frandma.sausage.feature.setting;

import me.frandma.sausage.feature.Feature;
import net.minecraft.client.gui.DrawContext;

public class ToggleSetting extends Setting<Boolean> {

  public ToggleSetting(Feature feature, String name, boolean defaultValue) {
    super(feature, name, defaultValue);
  }

  @Override
  public int onRender(DrawContext drawContext, final int x, final int y) {
    drawContext.drawText(mc.textRenderer, getName(), x, y + 2, 0xFFCCCCCC, true);
    int toggleX = x + 100;
    int toggleY = y;
    int toggleWidth = 30;
    int toggleHeight = 15;
    drawContext.fill(toggleX, toggleY, toggleX + toggleWidth, toggleY + toggleHeight, 0xFF333333);
    if (getValue()) {
      drawContext.fill(toggleX + toggleWidth - 13, toggleY + 2, toggleX + toggleWidth - 2, toggleY + toggleHeight - 2, 0xFF00CC66);
    } else {
      drawContext.fill(toggleX + 2, toggleY + 2, toggleX + 13, toggleY + toggleHeight - 2, 0xFF666666);
    }
    return 20;
  }
  @Override
  public void onClick(double mouseX, double mouseY, int button, int action) {
    int toggleX = 100;
    int toggleY = 0;
    int toggleWidth = 30;
    int toggleHeight = 15;
    if (mouseX >= toggleX && mouseX <= toggleX + toggleWidth &&
        mouseY >= toggleY && mouseY <= toggleY + toggleHeight) {
      set(!getValue());
    }
  }
  @Override
  public void onKey(int key, int action) {
  }
}
