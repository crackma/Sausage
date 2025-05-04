package me.frandma.sausage.event.events;

import lombok.Getter;
import me.frandma.sausage.event.Event;
import net.minecraft.client.gui.DrawContext;

public class RenderInventoryEvent implements Event {
  @Getter
  private final DrawContext drawContext;
  public RenderInventoryEvent(DrawContext drawContext) {
    this.drawContext = drawContext;
  }
}