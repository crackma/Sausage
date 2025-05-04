package me.frandma.sausage.event.events;

import lombok.Getter;
import lombok.Setter;
import me.frandma.sausage.event.Cancellable;
import me.frandma.sausage.event.Event;
import net.minecraft.client.render.Fog;

public class RenderFogEvent implements Event {
  @Getter
  @Setter
  private Fog returnValue;
  public RenderFogEvent() {
  }
}
