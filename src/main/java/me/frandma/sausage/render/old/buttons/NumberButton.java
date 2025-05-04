package me.frandma.sausage.render.old.buttons;

import me.frandma.sausage.feature.setting.NumberSetting;
import me.frandma.sausage.render.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.ColorHelper;

public class NumberButton extends GUIButton {
  private final NumberSetting numberSetting;
  private boolean dragging = false;
  public NumberButton(NumberSetting numberSetting, String text) {
    super(150, 6, 19, 16);
    this.numberSetting = numberSetting;
    renderConsumer(renderInfo -> {
      DrawContext drawContext = renderInfo.getDrawContext();
      int sliderX = renderInfo.getX() + getXOffset();
      int sliderY = renderInfo.getY() + getYButtonOffset();
      drawContext.drawBorder(sliderX, sliderY, 150, 6, ColorHelper.getArgb(204, 204, 204));
      double value = numberSetting.getValue();
      double min = numberSetting.getMin();
      double max = numberSetting.getMax();
      double percentage = (value - min) / (max - min);
      int handleX = sliderX + (int) (percentage * (150 - 6));
      drawContext.fill(handleX, sliderY, handleX + 6, sliderY + 6, ColorHelper.getArgb(204, 204, 204));
      RenderUtil.renderText(drawContext, text + ": " + String.format("%.2f", numberSetting.getValue()), sliderX, renderInfo.getY(), ColorHelper.getArgb(204, 204, 204));
      return 22;
    }).clickConsumer(mouseButtonEvent -> {
      if (mouseButtonEvent.getAction() == 1) {
        updateSliderValue(mouseButtonEvent.getMouseX());
        dragging = true;
      }
      if (mouseButtonEvent.getAction() == 0) dragging = false;
    }).mousePositionConsumer(mouseCursorPosEvent -> {
      if (!dragging) return;
      updateSliderValue(mouseCursorPosEvent.getX());
    });
  }
  private void updateSliderValue(int mouseX) {
    int sliderX = getLatestRenderInfo().getX() + getXOffset();
    int sliderWidth = 150;
    double percentage = (double) (mouseX - 2 - sliderX) / (sliderWidth - 6);
    percentage = Math.max(0, Math.min(1, percentage));
    double step = numberSetting.getStep();
    double min = numberSetting.getMin();
    double max = numberSetting.getMax();
    double newValue = min + percentage * (max - min);
    newValue = Math.round(newValue / step) * step;
    numberSetting.set(newValue);
  }
}