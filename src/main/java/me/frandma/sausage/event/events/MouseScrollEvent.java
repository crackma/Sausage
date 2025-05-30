package me.frandma.sausage.event.events;

import lombok.Getter;
import me.frandma.sausage.event.Cancellable;
import me.frandma.sausage.event.Event;

@Getter
public class MouseScrollEvent extends Cancellable implements Event {
  private final long window;
  private final double horizontal, vertical;
  public MouseScrollEvent(long window, double horizontal, double vertical) {
    this.window = window;
    this.horizontal = horizontal;
    this.vertical = vertical;
  }
}
