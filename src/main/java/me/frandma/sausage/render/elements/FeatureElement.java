package me.frandma.sausage.render.elements;

import lombok.Getter;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.render.buttons.GUIButton;
import me.frandma.sausage.render.buttons.ToggleButton;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FeatureElement {
  private final Feature feature;
  private final GUIButton toggleButton;
  private final List<SettingElement> settingElements = new ArrayList<>();
  public FeatureElement(Feature feature, GUIButton toggleButton) {
    this.feature = feature;
    this.toggleButton = toggleButton;
  }
  public void addSettingElement(SettingElement settingElement) {
    settingElements.add(settingElement);
  }
}
