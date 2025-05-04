package me.frandma.sausage.render;

import lombok.Getter;
import lombok.Setter;

public interface Draggable {
  boolean dragging = false;
  boolean isDragging();
  void setDragging(boolean dragging);
}
