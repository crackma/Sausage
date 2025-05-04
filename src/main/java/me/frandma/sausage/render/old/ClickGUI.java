package me.frandma.sausage.render.old;

import me.frandma.sausage.Sausage;
import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.*;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.setting.EnumSetting;
import me.frandma.sausage.feature.setting.NumberSetting;
import me.frandma.sausage.feature.setting.BooleanSetting;
import me.frandma.sausage.render.RenderUtil;
import me.frandma.sausage.render.old.buttons.BooleanButton;
import me.frandma.sausage.render.old.buttons.EnumButton;
import me.frandma.sausage.render.old.buttons.GUIButton;
import me.frandma.sausage.render.old.buttons.NumberButton;
import me.frandma.sausage.render.old.elements.CategoryElement;
import me.frandma.sausage.render.old.elements.FeatureElement;
import me.frandma.sausage.render.old.elements.SettingElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

import java.util.ArrayList;
import java.util.List;

public class ClickGUI {
  private final MinecraftClient mc = MinecraftClient.getInstance();
  private final List<CategoryElement> categoryElements = new ArrayList<>();
  private boolean opened = false, moving = false;
  private double mouseX, mouseY;
  private int x = 50, y = 50;
  private final int width = 529, height = 621;
  private CategoryElement selectedCategoryElement;
  public ClickGUI(Sausage sausage) {
    EventManager.register(RenderEvent.class, this::handleRender);
    EventManager.register(MouseCursorPosEvent.class, this::handleMousePos);
    EventManager.register(MouseButtonEvent.class, this::handleMouseButton);
    EventManager.register(KeyboardCharEvent.class, this::handleKeyboardChar);
    EventManager.register(KeyboardKeyEvent.class, this::handleKeyboardKey);
    EventManager.register(MouseLockEvent.class, this::handleMouseLock);
    EventManager.register(MouseScrollEvent.class, this::handleMouseScroll);
    for (Category category : Category.values()) {
      CategoryElement categoryElement = new CategoryElement(category, new GUIButton(CategoryElement.getWidth(), CategoryElement.getHeight(), 0, 0).renderConsumer(
          renderInfo -> {
            DrawContext drawContext = renderInfo.getDrawContext();
            int x = renderInfo.getX(), y = renderInfo.getY();
            Text categoryName = Text.literal(category.getName()).setStyle(Style.EMPTY.withFont(SausageFont.TAHOMA.getIdentifier()));
            int textWidth = mc.textRenderer.getWidth(categoryName);
            int textHeight = 8;
            int middleX = (CategoryElement.getWidth() - textWidth) / 2;
            int middleY = (CategoryElement.getHeight() - textHeight) / 2;
            RenderUtil.renderText(drawContext, category.getName(), middleX + x, middleY + y, ColorHelper.getArgb(204, 204, 204));
            if (selectedCategoryElement.getCategory() != category) return 0;
            drawContext.fill(x, CategoryElement.getHeight() + y, x + CategoryElement.getWidth(), CategoryElement.getHeight() + y - 2, ColorHelper.getArgb(204, 204, 204));
            return 0;
          }));
          categoryElement.getCategoryButton().clickConsumer(mouseButtonEvent -> {
            if (mouseButtonEvent.getAction() == 1) selectedCategoryElement = categoryElement;
          });
      if (selectedCategoryElement == null) selectedCategoryElement = categoryElement;
      switch (category) {
        case ALTS:
          break;
        case CONFIG:
          break;
        default:
          sausage.getFeatureManager().getFeatureList().forEach(feature -> {
            if (feature.getCategory() != category) return;
            FeatureElement featureElement = new FeatureElement(feature, new BooleanButton(feature::isToggled, 6, 6, 13, "enabled").clickConsumer(mouseButtonEvent -> {
              if (mouseButtonEvent.getAction() == 1) feature.toggle();
            }));
            feature.getSettings().getList().forEach(setting -> {
              switch (setting.getType()) {
                case ENUM:
                  EnumSetting enumSetting = (EnumSetting) setting;
                  featureElement.addSettingElement(new SettingElement(enumSetting, new EnumButton<>(enumSetting, enumSetting.getName())));
                  break;
                case NUMBER:
                  NumberSetting numberSetting = (NumberSetting) setting;
                  featureElement.addSettingElement(new SettingElement(numberSetting, new NumberButton(numberSetting, numberSetting.getName())));
                  break;
                case BOOLEAN:
                  BooleanSetting booleanSetting = (BooleanSetting) setting;
                  featureElement.addSettingElement(new SettingElement(booleanSetting, new BooleanButton(booleanSetting::getValue, 6, 6, 13, setting.getName())
                    .clickConsumer(mouseButtonEvent -> {
                      if (mouseButtonEvent.getAction() == 1) booleanSetting.set(!booleanSetting.getValue());
                    })
                  ));
                  break;
              }
            });
            categoryElement.addFeatureElement(featureElement);
          });
      }
      categoryElements.add(categoryElement);
    }
  }
  public void renderGUI(DrawContext drawContext) {
    MatrixStack matrices = drawContext.getMatrices();
    matrices.translate(0, 0, 500);
    int scaleFactor = (int) mc.getWindow().getScaleFactor();
    if (scaleFactor != 1) matrices.scale(1.0f / scaleFactor, 1.0f / scaleFactor, 1.0f);
    Identifier clickGUITexture = Identifier.of("sausage", "textures/clickgui.png");
    drawContext.drawTexture(RenderLayer::getGuiTextured, clickGUITexture, x, y, 0, 0, width, height, width, height, -1);
    int categoryX = x + 24, categoryY = y + 7;
    for (CategoryElement categoryElement : categoryElements) {
      categoryElement.getCategoryButton().handleRender(new GUIButton.RenderInfo(drawContext, categoryX, categoryY));
      categoryX += CategoryElement.getWidth() + 4;
    }
    int firstRowX = x + 57, firstRowY = y + 54;
    int secondRowX = x + 284, secondRowY = y + 54;
    for (FeatureElement featureElement : selectedCategoryElement.getFeatureElements()) {
      boolean useFirstRow = (firstRowY <= secondRowY);
      int rowX = useFirstRow ? firstRowX : secondRowX;
      int rowY = useFirstRow ? firstRowY : secondRowY;
      int yGap = 4;
      RenderUtil.renderText(drawContext, featureElement.getFeature().getName(), rowX + 2, rowY, ColorHelper.getArgb(204, 204, 204));
      rowY += 21;
      rowY += featureElement.getToggleButton().handleRender(new GUIButton.RenderInfo(drawContext, rowX, rowY)) + yGap;
      for (SettingElement settingElement : featureElement.getSettingElements()) {
        rowY += settingElement.getGuiButton().handleRender(new GUIButton.RenderInfo(drawContext, rowX, rowY)) + yGap;
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
  private void handleRender(RenderEvent event) {
    if (!opened) return;
    DrawContext drawContext = event.getDrawContext();
    drawContext.getMatrices().push();
    renderGUI(drawContext);
    drawContext.getMatrices().pop();
  }
  private void handleMousePos(MouseCursorPosEvent event) {
    double lastMouseX = mouseX;
    double lastMouseY = mouseY;
    mouseX = event.getX();
    mouseY = event.getY();
    if (!opened) return;
    for (FeatureElement featureElement : selectedCategoryElement.getFeatureElements()) {
      for (SettingElement settingElement : featureElement.getSettingElements()) {
        settingElement.getGuiButton().handleMousePosition(event);
      }
    }
    event.cancel();
    if (moving) {
      int tempX = (int) (x + mouseX - lastMouseX);
      int tempY = (int) (y + mouseY - lastMouseY);
      if (tempX >= 0 && tempX + width <= mc.getWindow().getWidth()) x = tempX;
      if (tempY >= 0 && tempY + height <= mc.getWindow().getHeight()) y = tempY;
    }
  }
  private void handleMouseButton(MouseButtonEvent event) {
    if (!opened) return;
    event.cancel();
    int button = event.getButton();
    int action = event.getAction();
    if (moving && action == 0) moving = false;
    for (CategoryElement categoryElement : categoryElements) {
      GUIButton categoryButton = categoryElement.getCategoryButton();
      if (categoryButton.handleClick(event)) return;
    }
    for (FeatureElement featureElement : selectedCategoryElement.getFeatureElements()) {
      GUIButton toggleButton = featureElement.getToggleButton();
      if (toggleButton.handleClick(event)) return;
      for (SettingElement settingElement : featureElement.getSettingElements()) {
        GUIButton settingButton = settingElement.getGuiButton();
        if (settingButton.handleClick(event)) return;
      }
    }
    if (isMouseOver(x, y, width, height) && button == 0) moving = action == 1;
  }
  private void handleKeyboardChar(KeyboardCharEvent event) {
    if (opened) event.cancel();
  }
  private void handleKeyboardKey(KeyboardKeyEvent event) {
    if (event.getKey() == 344) event.cancel();
    if (event.getKey() == 344 && event.getAction() == 1) {
      toggle();
      event.cancel();
    }
    if (opened && event.getAction() == 1) event.cancel();
  }
  private void handleMouseLock(MouseLockEvent event) {
    if (opened) event.cancel();
  }
  private void handleMouseScroll(MouseScrollEvent event) {
    if (opened) event.cancel();
  }
  private boolean isMouseOver(double x, double y, double width, double height) {
    return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
  }
  public void toggle() {
    opened = !opened;
    if (opened) {
      mc.mouse.unlockCursor();
    } else {
      if (mc.getOverlay() == null && mc.currentScreen == null) {
        mc.mouse.lockCursor();
      }
    }
  }
}