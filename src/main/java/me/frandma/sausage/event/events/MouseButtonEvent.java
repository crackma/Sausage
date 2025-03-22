package me.frandma.sausage.event.events;

import lombok.Getter;
import me.frandma.sausage.event.Cancellable;
import me.frandma.sausage.event.Event;

@Getter
public class MouseButtonEvent extends Cancellable implements Event {
  private final long window;
  private final int button, action, mouseX, mouseY;
  public MouseButtonEvent(long window, int button, int action, int mouseX, int mouseY) {
    this.window = window;
    this.button = button;
    this.action = action;
    this.mouseX = mouseX;
    this.mouseY = mouseY;
  }
}
