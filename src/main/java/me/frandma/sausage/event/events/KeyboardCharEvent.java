package me.frandma.sausage.event.events;

import lombok.Getter;
import me.frandma.sausage.event.Cancellable;
import me.frandma.sausage.event.Event;

@Getter
public class KeyboardCharEvent extends Cancellable implements Event {
  private final long window;
  private final int codePoint;
  private final int modifiers;
  public KeyboardCharEvent(long window, int codePoint, int modifiers) {
    this.window = window;
    this.codePoint = codePoint;
    this.modifiers = modifiers;
  }
}
