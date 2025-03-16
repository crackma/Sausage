package me.frandma.sausage.event.events;

import lombok.Getter;
import me.frandma.sausage.event.Cancellable;
import me.frandma.sausage.event.Event;

public class ChangeInventorySlotEvent extends Cancellable implements Event {
  @Getter
  private final int slot;
  public ChangeInventorySlotEvent(int slot) {
    this.slot = slot;
  }
}
