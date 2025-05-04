package me.frandma.sausage.feature.setting;

import lombok.Getter;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.Setting;

@Getter
public class EnumSetting<T extends Enum<T>> extends Setting<T> {
  private final T[] modes;
  double x, y;
  public EnumSetting(Feature feature, String name, T defaultValue) {
    super(feature, name, defaultValue);
    this.modes = getValue().getDeclaringClass().getEnumConstants();
  }
  @Override
  public Type getType() {
    return Type.ENUM;
  }
}
