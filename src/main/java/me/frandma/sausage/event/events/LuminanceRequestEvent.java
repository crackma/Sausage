package me.frandma.sausage.event.events;

import lombok.Getter;
import lombok.Setter;
import me.frandma.sausage.event.Event;

public class LuminanceRequestEvent implements Event {
  @Getter
  @Setter
  private int luminance = -1;
}
