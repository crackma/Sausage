package me.frandma.sausage.render.buttons;

import me.frandma.sausage.feature.setting.SliderSetting;
import me.frandma.sausage.render.ClickGUI;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.ColorHelper;

public class SliderButton extends GUIButton {
  private final SliderSetting sliderSetting;
  private boolean dragging = false;
  public SliderButton(SliderSetting sliderSetting, String text) {
    super(150, 6, 19, 16);
    this.sliderSetting = sliderSetting;
    renderConsumer(renderInfo -> {
      DrawContext drawContext = renderInfo.getDrawContext();
      int sliderX = renderInfo.getX() + getXOffset();
      int sliderY = renderInfo.getY() + getYButtonOffset();
      drawContext.drawBorder(sliderX, sliderY, 150, 6, ColorHelper.getArgb(204, 204, 204));
      double percentage = (sliderSetting.getValue() - sliderSetting.getMin()) / (sliderSetting.getMax() - sliderSetting.getMin());
      int handleX = sliderX + (int) (percentage * (150 - 6));
      drawContext.fill(handleX, sliderY, handleX + 6, sliderY + 6, ColorHelper.getArgb(204, 204, 204));
      ClickGUI.renderText(drawContext, text + ": " + String.format("%.2f", sliderSetting.getValue()), sliderX, renderInfo.getY(), ColorHelper.getArgb(204, 204, 204));
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
    double newValue = sliderSetting.getMin() + percentage * (sliderSetting.getMax() - sliderSetting.getMin());
    newValue = Math.round(newValue / sliderSetting.getStep()) * sliderSetting.getStep();
    sliderSetting.set(newValue);
  }
}