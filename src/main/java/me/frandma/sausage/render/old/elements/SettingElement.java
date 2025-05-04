package me.frandma.sausage.render.old.elements;

import lombok.Getter;
import me.frandma.sausage.feature.Setting;
import me.frandma.sausage.render.old.buttons.GUIButton;

@Getter
public class SettingElement {
  private final Setting setting;
  private final GUIButton guiButton;
  public SettingElement(Setting setting, GUIButton guiButton) {
    this.setting = setting;
    this.guiButton = guiButton;
  }
}
