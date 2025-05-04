package me.frandma.sausage.render.impl.onetap.buttons;

import me.frandma.sausage.feature.setting.NumberSetting;
import me.frandma.sausage.render.MenuButton;
import me.frandma.sausage.render.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.ColorHelper;

public class OTNumberButton extends MenuButton {
  private final NumberSetting numberSetting;
  private boolean dragging = false;
  public OTNumberButton(NumberSetting numberSetting, String text) {
    super(17, 16, 150, 6);
    this.numberSetting = numberSetting;
    renderConsumer(renderInfo -> {
      DrawContext drawContext = renderInfo.getDrawContext();
      int sliderX = renderInfo.getX() + getXOffset();
      int sliderY = renderInfo.getY() + getYOffset();
      RenderUtil.renderText(drawContext, text, sliderX, renderInfo.getY(), ColorHelper.getArgb(204, 204, 204));
      drawContext.fill(sliderX, sliderY, sliderX + getWidth(), sliderY + getHeight(), ColorHelper.getArgb(69, 69, 69));
      double value = numberSetting.getValue();
      double min = numberSetting.getMin();
      double max = numberSetting.getMax();
      double percentage = (value - min) / (max - min);
      int handleX = sliderX + (int) (percentage * getWidth());
      drawContext.fill(sliderX, sliderY, handleX, sliderY + getHeight(), ColorHelper.getArgb(255, 121, 27));
      RenderUtil.renderText(drawContext, String.format("%." + numberSetting.getPrecision() + "f", numberSetting.getValue()) + numberSetting.getSuffix(), handleX - 4, sliderY, ColorHelper.getArgb(204, 204, 204));
      setRenderHeight(26);
    }).mouseClickConsumer(mouseButtonEvent -> {
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
    int sliderX = getX();
    int sliderWidth = getWidth();
    double percentage = (double) (mouseX - sliderX) / sliderWidth;
    percentage = Math.max(0, Math.min(1, percentage));
    double step = numberSetting.getStep();
    double min = numberSetting.getMin();
    double max = numberSetting.getMax();
    double newValue = min + percentage * (max - min);
    newValue = Math.round(newValue / step) * step;
    numberSetting.set(newValue);
  }
}