package me.frandma.sausage.event.events;

import lombok.Getter;
import me.frandma.sausage.event.Cancellable;
import me.frandma.sausage.event.Event;

@Getter
public class MouseCursorPosEvent extends Cancellable implements Event {
  private final long window;
  private final int x, y;
  public MouseCursorPosEvent(long window, int x, int y) {
    this.window = window;
    this.x = x;
    this.y = y;
  }
}
