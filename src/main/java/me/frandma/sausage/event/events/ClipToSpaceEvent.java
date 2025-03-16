package me.frandma.sausage.event.events;

import lombok.Getter;
import lombok.Setter;
import me.frandma.sausage.event.Event;

public class ClipToSpaceEvent implements Event {
  @Getter
  @Setter
  private float distance;
  public ClipToSpaceEvent(float distance) {
    this.distance = distance;
  }
}
