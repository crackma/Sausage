package me.frandma.sausage.render;

import me.frandma.sausage.event.events.KeyboardKeyEvent;
import me.frandma.sausage.event.events.MouseButtonEvent;
import me.frandma.sausage.event.events.MouseCursorPosEvent;

public interface UIComponent {
  void render(RenderInfo renderInfo);
  boolean mouseButton(MouseButtonEvent mouseButtonEvent);
  void mousePosition(MouseCursorPosEvent mouseCursorPosEvent);
  void keyboardKey(KeyboardKeyEvent keyboardKeyEvent);
  int getX();
  void setX(int x);
  int getY();
  void setY(int y);
  int getWidth();
  void setWidth(int width);
  int getHeight();
  void setHeight(int height);
  int getRenderWidth();
  void setRenderWidth(int width);
  int getRenderHeight();
  void setRenderHeight(int height);
}
