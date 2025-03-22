package me.frandma.sausage.render.buttons;

import me.frandma.sausage.render.ClickGUI;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.ColorHelper;

import java.util.function.Supplier;

public class ToggleButton extends GUIButton {
  public ToggleButton(Supplier<Boolean> toggled, int width, int height, int gap, String text) {
    super(width, height, 0, 4);
    renderConsumer(renderInfo -> {
      DrawContext drawContext = renderInfo.getDrawContext();
      if (toggled.get()) {
        drawContext.fill(renderInfo.getX() + getXOffset(), renderInfo.getY() + getYButtonOffset(), renderInfo.getX() + width + getXOffset(), renderInfo.getY() + height + getYButtonOffset(), ColorHelper.getArgb(204, 204, 204));
      } else {
        drawContext.drawBorder(renderInfo.getX() + getXOffset(), renderInfo.getY() + getYButtonOffset(), width, height, ColorHelper.getArgb(204, 204, 204));
      }
      ClickGUI.renderText(drawContext, text, renderInfo.getX() + width + gap, renderInfo.getY(), ColorHelper.getArgb(204, 204, 204));
      return 11;
    });
  }
}
