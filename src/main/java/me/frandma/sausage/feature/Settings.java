package me.frandma.sausage.feature;

import me.frandma.sausage.feature.setting.Setting;

import java.util.ArrayList;
import java.util.List;

public class Settings {
  private final List<Setting<?>> settingList = new ArrayList<>();
  public <T> Setting<T> addSetting(Setting<T> setting) {
    settingList.add(setting);
    return setting;
  }
  public List<Setting<?>> getList() {
    return settingList;
  }
}
