package me.frandma.sausage.render.buttons;

import me.frandma.sausage.feature.setting.ModeSetting;
import me.frandma.sausage.render.ClickGUI;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

import java.util.Arrays;

public class ModeButton<T extends Enum<T>> extends GUIButton {
  private final ModeSetting<T> modeSetting;
  private boolean isExpanded = false;
  private int mouseX, mouseY;
  public ModeButton(ModeSetting<T> modeSetting, String text) {
    super(150, 12, 19, 17);
    this.modeSetting = modeSetting;
    renderConsumer(renderInfo -> {
      DrawContext drawContext = renderInfo.getDrawContext();
      ClickGUI.renderText(drawContext, text , renderInfo.getX() + getXOffset(), renderInfo.getY(), ColorHelper.getArgb(204, 204, 204));
      int buttonX = renderInfo.getX() + getXOffset();
      int buttonY = renderInfo.getY() + getYButtonOffset();
      if (isMouseOver(buttonX, buttonY, getWidth(), getHeight(), mouseX, mouseY))
        drawContext.fill(buttonX, buttonY, buttonX + getWidth(), buttonY + getHeight(), ColorHelper.getArgb(100, 100, 100));
      ClickGUI.renderText(drawContext, modeSetting.getValue().name().toLowerCase(), buttonX + 10, buttonY, ColorHelper.getArgb(204, 204, 204));
      Identifier arrowTexture = Identifier.of("sausage", isExpanded ? "textures/uparrow.png" : "textures/downarrow.png");
      int width = 5, height = 3;
      drawContext.drawTexture(RenderLayer::getGuiTextured, arrowTexture, buttonX + 140, buttonY + 4, 0, 0, width, height, width, height, -1);
      resetExtraHeight();
      if (isExpanded) {
        for (T mode : modeSetting.getModes()) {
          addExtraHeight(getHeight() + 1);
          if (isMouseOver(buttonX, buttonY + getExtraHeight(), getWidth(), getHeight(), mouseX, mouseY))
            drawContext.fill(buttonX, buttonY + getExtraHeight(), buttonX + getWidth(), buttonY + getExtraHeight() + getHeight(), ColorHelper.getArgb(100, 100, 100));;
          int color = modeSetting.getValue() == mode ? ColorHelper.getArgb(255, 255, 255) : ColorHelper.getArgb(204, 204, 204);
          ClickGUI.renderText(drawContext, mode.name().toLowerCase(), buttonX + 10, buttonY + getExtraHeight(), color);
        }
      }
      return getHeight() + getYButtonOffset() + getExtraHeight();
    }).clickConsumer(mouseButtonEvent -> {
      if (mouseButtonEvent.getAction() == 0) return;
      if (!isExpanded) {
        isExpanded = true;
        return;
      }
      T[] modes = modeSetting.getModes();
      int buttonX = getLatestRenderInfo().getX() + getXOffset();
      int buttonY = getLatestRenderInfo().getY() + getYButtonOffset();
      for (int i = 0; i < modes.length; i++) {
        if (isMouseOver(buttonX, buttonY + (i + 1) * getHeight(), getWidth(), getHeight(), mouseX, mouseY)) modeSetting.set(modes[i]);
      }
      isExpanded = false;
    }).mousePositionConsumer(mouseCursorPosEvent -> {
      mouseX = mouseCursorPosEvent.getX();
      mouseY = mouseCursorPosEvent.getY();
    });
  }
  private void cycleMode() {
    T[] modes = modeSetting.getModes();
    int currentIndex = Arrays.asList(modes).indexOf(modeSetting.getValue());
    int nextIndex = (currentIndex + 1) % modes.length;
    modeSetting.set(modes[nextIndex]);
  }
}
