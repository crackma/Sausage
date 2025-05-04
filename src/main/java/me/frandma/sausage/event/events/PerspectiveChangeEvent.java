package me.frandma.sausage.event.events;

import lombok.Getter;
import me.frandma.sausage.event.Cancellable;
import me.frandma.sausage.event.Event;
import net.minecraft.client.option.Perspective;

public class PerspectiveChangeEvent extends Cancellable implements Event {
  @Getter
  private final Perspective perspective;
  public PerspectiveChangeEvent(Perspective perspective) {
    this.perspective = perspective;
  }
}
