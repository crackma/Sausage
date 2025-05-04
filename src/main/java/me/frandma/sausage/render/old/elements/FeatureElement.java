package me.frandma.sausage.render.old.elements;

import lombok.Getter;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.render.old.buttons.GUIButton;

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
