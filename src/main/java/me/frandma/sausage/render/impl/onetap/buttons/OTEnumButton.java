package me.frandma.sausage.render.impl.onetap.buttons;

import me.frandma.sausage.feature.setting.EnumSetting;
import me.frandma.sausage.render.MenuButton;
import me.frandma.sausage.render.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public class OTEnumButton<T extends Enum<T>> extends MenuButton {
  private boolean isExpanded = false;
  public OTEnumButton(EnumSetting<T> enumSetting, String text) {
    super(17, 15, 150, 12);
    renderConsumer(renderInfo -> {
      DrawContext drawContext = renderInfo.getDrawContext();
      RenderUtil.renderText(drawContext, text , renderInfo.getX() + getXOffset(), renderInfo.getY(), ColorHelper.getArgb(204, 204, 204));
      int buttonX = renderInfo.getX() + getXOffset();
      int buttonY = renderInfo.getY() + getYOffset();
      if (RenderUtil.isMouseOver(buttonX, buttonY, getOriginalWidth(), getOriginalHeight()))
        drawContext.fill(buttonX, buttonY, buttonX + getOriginalWidth(), buttonY + getOriginalHeight(), ColorHelper.getArgb(100, 100, 100));
      RenderUtil.renderText(drawContext, enumSetting.getValue().name().toLowerCase(), buttonX + 10, buttonY, ColorHelper.getArgb(204, 204, 204));
      Identifier arrowTexture = Identifier.of("sausage", isExpanded ? "textures/uparrow.png" : "textures/downarrow.png");
      int width = 5, height = 3;
      RenderUtil.drawTexture(drawContext, arrowTexture, buttonX + 140, buttonY + 4, width, height);
      setHeight(getOriginalHeight());
      setRenderHeight(getOriginalHeight() + getYOffset());
      if (!isExpanded) return;
      setHeight(0);
      for (T mode : enumSetting.getModes()) {
        setHeight(getHeight() + getOriginalHeight() + 1);
        int modeY = buttonY + getHeight();
        if (RenderUtil.isMouseOver(buttonX, modeY, getOriginalWidth(), getOriginalHeight()))
          drawContext.fill(buttonX, modeY, buttonX + getOriginalWidth(), modeY + getOriginalHeight(), ColorHelper.getArgb(100, 100, 100));
        int color = enumSetting.getValue() == mode ? ColorHelper.getArgb(255, 121, 27) : ColorHelper.getArgb(204, 204, 204);
        RenderUtil.renderText(drawContext, mode.name().toLowerCase(), buttonX + 10, modeY, color);
      }
      setHeight(getHeight() + getOriginalHeight());
      setRenderHeight(getHeight() + getYOffset());
    }).mouseClickConsumer(mouseButtonEvent -> {
      if (mouseButtonEvent.getAction() == 0) return;
      if (!isExpanded) {
        isExpanded = true;
        return;
      }
      T[] modes = enumSetting.getModes();
      int buttonX = getX();
      int buttonY = getY();
      for (int i = 0; i < modes.length; i++) {
        if (RenderUtil.isMouseOver(buttonX, buttonY + (i + 1) * getOriginalHeight(), getWidth(), getOriginalHeight()))
          enumSetting.set(modes[i]);
      }
      isExpanded = false;
    });
  }
}
