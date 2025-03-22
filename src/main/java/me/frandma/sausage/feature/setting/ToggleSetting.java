package me.frandma.sausage.feature.setting;

import me.frandma.sausage.feature.Feature;

public class ToggleSetting extends Setting<Boolean> {
  public ToggleSetting(Feature feature, String name, boolean defaultValue) {
    super(feature, name, defaultValue);
  }
  @Override
  public Type getType() {
    return Type.TOGGLE;
  }
}
