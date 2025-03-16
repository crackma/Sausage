package me.frandma.sausage.event.events;

import lombok.Getter;
import me.frandma.sausage.event.Cancellable;
import me.frandma.sausage.event.Event;

@Getter
public class KeyboardKeyEvent extends Cancellable implements Event {
  private final long window;
  private final int key;
  private final int scancode;
  private final int action;
  private final int modifiers;
  public KeyboardKeyEvent(long window, int key, int scancode, int action, int modifiers) {
    this.window = window;
    this.key = key;
    this.scancode = scancode;
    this.action = action;
    this.modifiers = modifiers;
  }
}
