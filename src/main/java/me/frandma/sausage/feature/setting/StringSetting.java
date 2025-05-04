package me.frandma.sausage.feature.setting;

import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.Setting;

public class StringSetting extends Setting<String> {
  public StringSetting(Feature feature, String name, String defaultValue) {
    super(feature, name, defaultValue);
  }
  @Override
  public Setting.Type getType() {
    return Setting.Type.STRING;
  }
}
