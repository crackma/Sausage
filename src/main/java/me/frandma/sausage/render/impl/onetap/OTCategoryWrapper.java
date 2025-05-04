package me.frandma.sausage.render.impl.onetap;

import lombok.Getter;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.render.MenuButton;
import me.frandma.sausage.render.UIComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class OTCategoryWrapper {
  private final Category category;
  private final MenuButton categoryButton;
  private final Map<Feature, List<UIComponent>> featureComponents = new HashMap<>();
  public OTCategoryWrapper(Category category, MenuButton categoryButton) {
    this.category = category;
    this.categoryButton = categoryButton;
  }
  public void addFeatureComponent(Feature feature, UIComponent component) {
    featureComponents.computeIfAbsent(feature, k -> new ArrayList<>()).add(component);
  }
}