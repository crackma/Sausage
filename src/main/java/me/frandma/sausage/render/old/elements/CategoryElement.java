package me.frandma.sausage.render.old.elements;

import lombok.Getter;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.render.old.buttons.GUIButton;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CategoryElement {
  @Getter
  private static final int width = 57, height = 26;
  private final Category category;
  private final GUIButton categoryButton;
  private final List<FeatureElement> featureElements = new ArrayList<>();
  public CategoryElement(Category category, GUIButton categoryButton) {
    this.category = category;
    this.categoryButton = categoryButton;
  }
  public void addFeatureElement(FeatureElement featureElement) {
    featureElements.add(featureElement);
  }
}
