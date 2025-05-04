package me.frandma.sausage.render.impl.onetap.buttons;

import me.frandma.sausage.render.MenuButton;
import me.frandma.sausage.render.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.ColorHelper;

import java.util.function.Supplier;

public class OTBooleanButton extends MenuButton {
  public OTBooleanButton(Supplier<Boolean> toggled, String text) {
    super(0, 4, 4, 4);
    renderConsumer(renderInfo -> {
      DrawContext drawContext = renderInfo.getDrawContext();
      int x = renderInfo.getX(), y = renderInfo.getY();
      if (toggled.get()) {
        drawContext.fill(x, y + getYOffset(), x + getWidth(), y + getHeight() + getYOffset(), ColorHelper.getArgb(255, 121, 27));
      } else {
        drawContext.fill(x, y + getYOffset(), x + getWidth(), y + getHeight() + getYOffset(), ColorHelper.getArgb(69, 69, 69));
      }
      RenderUtil.renderText(drawContext, text, x + getWidth() + 13, y, ColorHelper.getArgb(204, 204, 204));
      setRenderHeight(11);
    });
  }
}