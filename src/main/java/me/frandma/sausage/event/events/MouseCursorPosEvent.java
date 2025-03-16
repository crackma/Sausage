package me.frandma.sausage.event.events;

import lombok.Getter;
import me.frandma.sausage.event.Cancellable;
import me.frandma.sausage.event.Event;

@Getter
public class MouseCursorPosEvent extends Cancellable implements Event {
  private long window;
  private double x;
  private double y;
  public MouseCursorPosEvent(long window, double x, double y) {
    this.window = window;
    this.x = x;
    this.y = y;
  }
}
