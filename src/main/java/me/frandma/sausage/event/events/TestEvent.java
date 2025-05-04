package me.frandma.sausage.event.events;

import lombok.Getter;
import lombok.Setter;
import me.frandma.sausage.event.Cancellable;
import me.frandma.sausage.event.Event;

public class TestEvent extends Cancellable implements Event {
  @Getter
  @Setter
  private int clampedViewDistance = 16;
}
