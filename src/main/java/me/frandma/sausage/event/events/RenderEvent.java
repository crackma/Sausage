package me.frandma.sausage.event.events;

import lombok.Getter;
import me.frandma.sausage.event.Event;
import net.minecraft.client.gui.DrawContext;

public class RenderEvent implements Event {
  @Getter
  private DrawContext drawContext;
  public RenderEvent(DrawContext drawContext) {
    this.drawContext = drawContext;
  }
}
