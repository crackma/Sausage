package me.frandma.sausage.render.impl.onetap;

import lombok.Getter;
import lombok.Setter;
import me.frandma.sausage.Sausage;
import me.frandma.sausage.event.events.KeyboardKeyEvent;
import me.frandma.sausage.event.events.MouseButtonEvent;
import me.frandma.sausage.event.events.MouseCursorPosEvent;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.Setting;
import me.frandma.sausage.feature.setting.BooleanSetting;
import me.frandma.sausage.feature.setting.EnumSetting;
import me.frandma.sausage.feature.setting.NumberSetting;
import me.frandma.sausage.render.*;
import me.frandma.sausage.render.impl.onetap.buttons.OTBooleanButton;
import me.frandma.sausage.render.impl.onetap.buttons.OTEnumButton;
import me.frandma.sausage.render.impl.onetap.buttons.OTNumberButton;
import me.frandma.sausage.render.old.SausageFont;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OTMenuPanel extends MenuPanel implements Draggable {
  private final MinecraftClient mc = MinecraftClient.getInstance();
  private final Sausage sausage;
  private Category selectedCategory = Category.COMBAT;
  private final Map<Category, OTCategoryWrapper> categoryWrappers = new HashMap<>();
  private final int categoryWidth = 57, categoryHeight = 26;
  @Getter
  @Setter
  private boolean dragging;
  public OTMenuPanel(Sausage sausage) {
    super(529, 621);
    this.setX(50);
    this.setY(50);
    this.sausage = sausage;
    for (Category category : Category.values()) {
      MenuButton categoryButton = new MenuButton(categoryWidth, categoryHeight);
      categoryWrappers.put(category, new OTCategoryWrapper(category, categoryButton
          .renderConsumer(renderInfo -> {
            DrawContext drawContext = renderInfo.getDrawContext();
            Text categoryName = Text.literal(category.getName()).setStyle(Style.EMPTY.withFont(SausageFont.TAHOMA.getIdentifier()));
            int textWidth = mc.textRenderer.getWidth(categoryName);
            int textHeight = 8;
            int middleX = (categoryButton.getWidth() - textWidth) / 2;
            int middleY = (categoryButton.getHeight() - textHeight) / 2;
            int finalX = renderInfo.getX(), finalY = renderInfo.getY();
            RenderUtil.renderText(drawContext, category.getName(), middleX + finalX, middleY + finalY, ColorHelper.getArgb(204, 204, 204));
            if (selectedCategory != category) return;
            drawContext.fill(finalX, categoryHeight + finalY, finalX + categoryWidth, categoryHeight + finalY - 2, ColorHelper.getArgb(255, 121, 27));
          })
          .mouseClickConsumer(mouseButtonEvent -> {
            if (RenderUtil.isMouseOver(categoryButton) && mouseButtonEvent.getAction() == 1) selectedCategory = category;
          })));
    }
    for (Feature feature : sausage.getFeatureManager().getFeatureList()) {
      OTCategoryWrapper categoryWrapper = categoryWrappers.get(feature.getCategory());
      categoryWrapper.addFeatureComponent(feature, new OTBooleanButton(feature::isToggled, "enabled").mouseClickConsumer(mouseButtonEvent -> {
        if (mouseButtonEvent.getAction() == 1) feature.toggle();
      }));
      for (Setting setting : feature.getSettings().getList()) {
        switch (setting.getType()) {
          case ENUM:
            EnumSetting enumSetting = (EnumSetting) setting;
            categoryWrapper.addFeatureComponent(feature, new OTEnumButton<>(enumSetting, enumSetting.getName()));
            continue;
          case NUMBER:
            NumberSetting numberSetting = (NumberSetting) setting;
            categoryWrapper.addFeatureComponent(feature, new OTNumberButton(numberSetting, numberSetting.getName()));
            continue;
          case BOOLEAN:
            BooleanSetting booleanSetting = (BooleanSetting) setting;
            categoryWrapper.addFeatureComponent(feature, new OTBooleanButton(booleanSetting::getValue, setting.getName()).mouseClickConsumer(mouseButtonEvent -> {
              if (mouseButtonEvent.getAction() == 1) booleanSetting.set(!booleanSetting.getValue());
            }));
            continue;
        }
      }
    }
  }
  @Override
  public void onRender(RenderInfo renderInfo) {
    DrawContext drawContext = renderInfo.getDrawContext();
    RenderUtil.drawTexture(drawContext, Identifier.of("sausage", "textures/clickgui.png"), getX(), getY(), getWidth(), getHeight());
    int categoryX = getX() + 24, categoryY = getY() + 7;
    OTCategoryWrapper selectedCategoryWrapper = null;
    for (OTCategoryWrapper categoryWrapper : categoryWrappers.values()) {
      if (categoryWrapper.getCategory() == selectedCategory) selectedCategoryWrapper = categoryWrapper;
      categoryWrapper.getCategoryButton().render(new RenderInfo(drawContext, categoryX, categoryY));
      categoryX += categoryWrapper.getCategoryButton().getRenderWidth() + 4;
    }
    if (selectedCategoryWrapper == null) return;
    int firstRowX = getX() + 57, firstRowY = getY() + 54;
    int secondRowX = getX() + 284, secondRowY = getY() + 54;
    int rowX, rowY, yGap = 4;
    boolean useFirstRow;
    for (Map.Entry<Feature, List<UIComponent>> featureComponents : selectedCategoryWrapper.getFeatureComponents().entrySet()) {
      Feature feature = featureComponents.getKey();
      if (featureComponents.getKey().getCategory() != selectedCategory) continue;
      useFirstRow = (firstRowY <= secondRowY);
      rowX = useFirstRow ? firstRowX : secondRowX;
      rowY = useFirstRow ? firstRowY : secondRowY;
      RenderUtil.renderText(drawContext, feature.getName(), rowX + 2, rowY, ColorHelper.getArgb(204, 204, 204));
      rowY += 21;
      for (UIComponent featureComponent : featureComponents.getValue()) {
        featureComponent.render(new RenderInfo(drawContext, rowX, rowY));
        rowY += featureComponent.getRenderHeight() + yGap;
      }
      if (useFirstRow) {
        firstRowY = rowY;
        firstRowY += 21;
      } else {
        secondRowY = rowY;
        secondRowY += 21;
      }
    }
  }
  @Override
  public boolean onMouseButton(MouseButtonEvent mouseButtonEvent) {
//    for (UIComponent categoryComponent : categoryComponents) {
//      if (categoryComponent.mouseButton(mouseButtonEvent)) return true;
//    }
//    for (Feature feature : sausage.getFeatureManager().getFeatureList()) {
//      if (feature.getCategory() != selectedCategory) continue;
//      for (UIComponent featureComponent : featureComponents.get(feature))
//        if (featureComponent.mouseButton(mouseButtonEvent)) return true;
//    }
    return false;
  }
  @Override
  public void onMousePosition(MouseCursorPosEvent mouseCursorPosEvent) {
//    for (Feature feature : sausage.getFeatureManager().getFeatureList()) {
//      if (feature.getCategory() != selectedCategory) continue;
//      for (UIComponent featureComponent : featureComponents.get(feature))
//        featureComponent.mousePosition(mouseCursorPosEvent);
//    }
  }
  @Override
  public void onKeyboardKey(KeyboardKeyEvent keyboardKeyEvent) {
//    for (Feature feature : sausage.getFeatureManager().getFeatureList()) {
//      if (feature.getCategory() != selectedCategory) continue;
//      for (UIComponent featureComponent : featureComponents.get(feature))
//        featureComponent.keyboardKey(keyboardKeyEvent);
//    }
  }
}
