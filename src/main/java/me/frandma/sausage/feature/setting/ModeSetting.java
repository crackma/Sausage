package me.frandma.sausage.feature.setting;

import lombok.Getter;
import me.frandma.sausage.event.events.MouseButtonEvent;
import me.frandma.sausage.feature.Feature;
import net.minecraft.client.gui.DrawContext;

@Getter
public class ModeSetting<T extends Enum<T>> extends Setting<T> {
  private final T[] modes;
  private boolean expanded = false;
  double x, y;
  public ModeSetting(Feature feature, String name, T defaultValue) {
    super(feature, name, defaultValue);
    this.modes = getValue().getDeclaringClass().getEnumConstants();
  }
  @Override
  public Type getType() {
    return Type.MODE;
  }
}
