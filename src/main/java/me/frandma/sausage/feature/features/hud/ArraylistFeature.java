package me.frandma.sausage.feature.features.hud;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.RenderHudEvent;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.FeatureManager;
import me.frandma.sausage.feature.setting.EnumSetting;
import me.frandma.sausage.feature.setting.NumberSetting;
import me.frandma.sausage.feature.Setting;
import me.frandma.sausage.feature.setting.BooleanSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArraylistFeature extends Feature {
  private final Setting<Boolean> noSpaces = settings.addSetting(new BooleanSetting(this, "no spaces", true));
  private final Setting<Boolean> drawBackground = settings.addSetting(new BooleanSetting(this, "draw background", true));
  private final Setting<Double> scale = settings.addSetting(new NumberSetting(this, "scale", 2D, 1D, 4D, 1D, 0, "x"));
  private final Setting<Double> xOffset = settings.addSetting(new NumberSetting(this, "x offset", 4D, 0D, 50D, 1D, 0, "px"));
  private final Setting<Double> yOffset = settings.addSetting(new NumberSetting(this, "y offset", 4D, 0D, 50D, 1D, 0, "px"));
  private final Setting<Position> position = settings.addSetting(new EnumSetting<>(this, "position", Position.TOP_RIGHT));
  private final Setting<Sort> sort = settings.addSetting(new EnumSetting<>(this, "sort", Sort.LENGTH));
  public ArraylistFeature() {
    this.toggled = false;
    super.name = "array list";
    super.category = Category.HUD;
    EventManager.register(RenderHudEvent.class, this::onHudRender);
  }
  public void onHudRender(RenderHudEvent renderHudEvent) {
    if (!isToggled()) return;
    DrawContext drawContext = renderHudEvent.getDrawContext();
    MatrixStack matrices = drawContext.getMatrices();
    matrices.push();
    int scaleFactor = (int) mc.getWindow().getScaleFactor();
    if (scaleFactor != 1) matrices.scale(1.0f / scaleFactor, 1.0f / scaleFactor, 1.0f);
    double scaleValue = scale.getValue();
    int screenWidth = (int) (mc.getWindow().getWidth() / scaleValue);
    int screenHeight = (int) (mc.getWindow().getHeight() / scaleValue);
    matrices.scale((int) scaleValue, (int) scaleValue, 1.0f);
    List<Feature> features = FeatureManager.getInstance().getFeatureList();
    List<Feature> activeFeatures = features.stream()
        .filter(Feature::isToggled)
        .collect(Collectors.toList());
    if (sort.getValue() == Sort.ALPHABETICAL) {
      activeFeatures.sort(Comparator.comparing(Feature::getName));
    } else if (sort.getValue() == Sort.LENGTH) {
      Map<Feature, String> displayTextMap = new HashMap<>();
      for (Feature feature : activeFeatures) {
        String displayText = noSpaces.getValue() ? feature.getName().replace(" ", "") : feature.getName();
        displayTextMap.put(feature, displayText);
      }
      activeFeatures.sort((f1, f2) -> {
        String text1 = displayTextMap.get(f1);
        String text2 = displayTextMap.get(f2);
        int len1 = mc.textRenderer.getWidth(text1);
        int len2 = mc.textRenderer.getWidth(text2);
        return Integer.compare(len2, len1);
      });
    }
    int startX = position.getValue() == Position.TOP_LEFT || position.getValue() == Position.BOTTOM_LEFT
        ? xOffset.getValue().intValue()
        : screenWidth - xOffset.getValue().intValue();
    int startY = position.getValue() == Position.TOP_LEFT || position.getValue() == Position.TOP_RIGHT
        ? yOffset.getValue().intValue()
        : screenHeight - yOffset.getValue().intValue();
    int yIncrement = mc.textRenderer.fontHeight + 4;
    for (int i = 0; i < activeFeatures.size(); i++) {
      Feature feature = activeFeatures.get(i);
      String displayText = noSpaces.getValue() ? feature.getName().replace(" ", "") : feature.getName();
      int textWidth = mc.textRenderer.getWidth(displayText);
      int x = position.getValue() == Position.TOP_LEFT || position.getValue() == Position.BOTTOM_LEFT
          ? startX
          : startX - textWidth;
      int y = position.getValue() == Position.TOP_LEFT || position.getValue() == Position.TOP_RIGHT
          ? startY + (i * yIncrement)
          : startY - ((activeFeatures.size() - i) * yIncrement) + mc.textRenderer.fontHeight;
      if (drawBackground.getValue()) {
        drawContext.fill(
            x - 2,
            y - 2,
            x + textWidth + 2,
            y + mc.textRenderer.fontHeight + 2,
            0x80000000
        );
      }
      drawContext.drawText(
          mc.textRenderer,
          displayText,
          x,
          y,
          0xFFFFFFFF,
          true
      );
    }
    matrices.pop();
  }
  public enum Position {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT
  }
  public enum Sort {
    ALPHABETICAL,
    LENGTH,
    NONE
  }
}
