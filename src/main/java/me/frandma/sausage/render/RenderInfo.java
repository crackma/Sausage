package me.frandma.sausage.render;

import lombok.Getter;
import net.minecraft.client.gui.DrawContext;

@Getter
public class RenderInfo {
  private final DrawContext drawContext;
  private final int x, y;
  public RenderInfo(DrawContext drawContext, int x, int y) {
    this.drawContext = drawContext;
    this.x = x;
    this.y = y;
  }
  public RenderInfo(DrawContext drawContext) {
    this.drawContext = drawContext;
    this.x = 0;
    this.y = 0;
  }
}
