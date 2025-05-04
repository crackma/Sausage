package me.frandma.sausage.feature.setting;

import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.Setting;

public class BooleanSetting extends Setting<Boolean> {
  public BooleanSetting(Feature feature, String name, boolean defaultValue) {
    super(feature, name, defaultValue);
  }
  @Override
  public Type getType() {
    return Type.BOOLEAN;
  }
}
