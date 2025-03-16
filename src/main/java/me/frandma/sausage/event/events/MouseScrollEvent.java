package me.frandma.sausage.event.events;

import lombok.Getter;
import me.frandma.sausage.event.Event;

@Getter
public class MouseScrollEvent implements Event {
  private long window;
  private double horizontal, vertical;
  public MouseScrollEvent(long window, double horizontal, double vertical) {
    this.window = window;
    this.horizontal = horizontal;
    this.vertical = vertical;
  }
}
