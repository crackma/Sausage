package me.frandma.sausage.render.elements;

import lombok.Getter;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.render.ClickGUI;
import me.frandma.sausage.render.SausageFont;
import me.frandma.sausage.render.buttons.GUIButton;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;

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
