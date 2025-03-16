package me.frandma.sausage.feature.setting;

import me.frandma.sausage.feature.Feature;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class SliderSetting extends Setting<Double> {
  private final double min;
  private final double max;
  private final double step;
  private boolean dragging = false;
  public SliderSetting(Feature feature, String name, double defaultValue, double min, double max, double step) {
    super(feature, name, defaultValue);
    this.min = min;
    this.max = max;
    this.step = step;
  }
  @Override
  public int onRender(DrawContext drawContext, final int x, final int y) {
    drawContext.drawText(mc.textRenderer, getName() + ": " + String.format("%.2f", getValue()),
        x, y + 2, 0xFFCCCCCC, true);
    int sliderX = x;
    int sliderY = y + 15;
    int sliderWidth = 100;
    int sliderHeight = 5;
    drawContext.fill(sliderX, sliderY, sliderX + sliderWidth, sliderY + sliderHeight, 0xFF333333);
    double percentage = (getValue() - min) / (max - min);
    int sliderPos = (int) (sliderX + (sliderWidth * percentage));
    drawContext.fill(sliderX, sliderY, sliderPos, sliderY + sliderHeight, 0xFF3399FF);
    drawContext.fill(sliderPos - 2, sliderY - 2, sliderPos + 2, sliderY + sliderHeight + 2, 0xFFFFFFFF);
    return 25;
  }
  @Override
  public void onClick(double mouseX, double mouseY, int button, int action) {
    int sliderX = 0;
    int sliderY = 15;
    int sliderWidth = 100;
    int sliderHeight = 5;
    if (mouseX >= sliderX && mouseX <= sliderX + sliderWidth &&
        mouseY >= sliderY - 2 && mouseY <= sliderY + sliderHeight + 2) {
      if (action == 1) {
        dragging = true;
        updateValue(mouseX, sliderX, sliderWidth);
      }
    }
    if (action == 0) {
      dragging = false;
    }
  }
  private void updateValue(double mouseX, int sliderX, int sliderWidth) {
    double percentage = (mouseX - sliderX) / sliderWidth;
    percentage = Math.max(0, Math.min(1, percentage));
    double newValue = min + (percentage * (max - min));
    if (step > 0) {
      newValue = Math.round(newValue / step) * step;
    }
    set(newValue);
  }
  @Override
  public void onKey(int key, int action) {
  }
  public void onMouseDrag(double mouseX) {
    if (dragging) {
      updateValue(mouseX, 0, 100);
    }
  }
}