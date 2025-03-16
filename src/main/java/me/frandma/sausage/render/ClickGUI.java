package me.frandma.sausage.render;

import com.mojang.blaze3d.systems.RenderSystem;
import me.frandma.sausage.Sausage;
import me.frandma.sausage.client.SausageClient;
import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.*;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.setting.Setting;
import me.frandma.sausage.mixins.DrawContextAccessor;
import me.frandma.sausage.mixins.MinecraftClientAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.*;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.TriState;
import net.minecraft.util.math.ColorHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;


public class ClickGUI {
  private final MinecraftClient mc = MinecraftClient.getInstance();
  private boolean opened = false;
  private Feature selectedFeature = null;
  private double mouseX, mouseY, lastMouseX, lastMouseY;
  private int x = 50, y = 50;
  private final int width = 300, height = 400;
  private Category selectedCategory = Category.COMBAT;
  private boolean moving = false;
  private float scale = 1;
  public ClickGUI(Sausage sausage) {
    EventManager.register(RenderEvent.class, this::handleRender);
    EventManager.register(MouseCursorPosEvent.class, this::handleMousePos);
    EventManager.register(MouseButtonEvent.class, this::handleMouseButton);
    EventManager.register(KeyboardKeyEvent.class, this::handleKeyboard);
    EventManager.register(MouseLockEvent.class, this::handleMouseLock);
    EventManager.register(MouseScrollEvent.class, mouseScrollEvent -> {
      scale += (float) mouseScrollEvent.getVertical() > 0f ? 0.5f : -0.5f;
    });
  }
  public void renderText(DrawContext drawContext, String text, int x, int y, int color, SausageFont sausageFont, float scale) {
    MatrixStack matrices = drawContext.getMatrices();
    matrices.push();
    matrices.translate(x, y, 0);
    matrices.scale(scale, scale, 1.0f);
    SausageClient sausageClient = SausageClient.getInstance();
    if (SausageClient.getInstance() == null) return;
    VertexConsumerProvider.Immediate immediate = ((DrawContextAccessor)drawContext).getVertexConsumers();
    TextRenderer textRenderer = sausageClient.getTextRenderer();
    if (textRenderer == null) return;
    textRenderer.draw(
        Text.literal(text)
            .setStyle(Style.EMPTY.withFont(sausageFont.getIdentifier())),
        0, 0,
        color,
        false,
        matrices.peek().getPositionMatrix(),
        immediate,
        TextRenderer.TextLayerType.SEE_THROUGH,
        0,
        0xF000F0
    );
//    VertexConsumerProvider.Immediate immediate = ((DrawContextAccessor)drawContext).getVertexConsumers();
//    mc.textRenderer.draw(
//        Text.literal(text)
//            .setStyle(Style.EMPTY.withFont(sausageFont.getIdentifier())),
//        0, 0,
//        color,
//        false,
//        matrices.peek().getPositionMatrix(),
//        immediate,
//        TextRenderer.TextLayerType.SEE_THROUGH,
//        0,
//        0xF000F0
//    );
    matrices.pop();
  }
  public void renderGUI(DrawContext drawContext) {
    MatrixStack matrices = drawContext.getMatrices();
    matrices.translate(0, 0, 400);
    int scaleFactor = (int) mc.getWindow().getScaleFactor();
    if (scaleFactor != 1) matrices.scale(1.0f / scaleFactor, 1.0f / scaleFactor, 1.0f);
    drawContext.drawTextWithShadow(mc.textRenderer, "scale" + scale, 0, 0, ColorHelper.getArgb(255, 255, 255));
    drawContext.fill(x, y, x + width, y + height, ColorHelper.getArgb(179, 66, 255));
    drawContext.fill(x+1, y+1, x + width-1, y + height-1, ColorHelper.getArgb(0, 0, 0));
    drawContext.fill(x, y + 21, x + width, y + 20, ColorHelper.getArgb(179, 66, 255));
    matrices.translate(x, y, 0);
    renderText(drawContext, "Neon", 1, 1, ColorHelper.getArgb(255, 255, 255), SausageFont.VOLTE_SEMIBOLD, scale);
  }
  public void onClick(int button, int action) {
    if (isMouseOver(x, y, width, height) && button == 0) moving = action == 1;
    if (moving && action == 0) moving = false;
  }
  private void handleRender(RenderEvent event) {
    if (!opened) return;
    DrawContext drawContext = event.getDrawContext();
    drawContext.getMatrices().push();
    renderGUI(drawContext);
    drawContext.getMatrices().pop();
  }
  private void handleMousePos(MouseCursorPosEvent event) {
    lastMouseX = mouseX;
    lastMouseY = mouseY;
    mouseX = event.getX();
    mouseY = event.getY();
    if (!opened) return;
    event.cancel();
    if (moving) {
      int tempX = (int) (x + mouseX - lastMouseX);
      int tempY = (int) (y + mouseY - lastMouseY);
      if (tempX > 0 && tempX + width < mc.getWindow().getWidth()) x = tempX;
      if (tempY > 0 && tempY + height < mc.getWindow().getHeight()) y = tempY;
    }
  }
  private void handleMouseButton(MouseButtonEvent event) {
    if (!opened) return;
    onClick(event.getButton(), event.getAction());
    event.cancel();
  }
  private void handleKeyboard(KeyboardKeyEvent event) {
    if (event.getKey() == 344 && event.getAction() == 1) toggle();
    if (opened && event.getAction() == 1) event.cancel();
    if (selectedFeature == null) return;
    for (Setting<?> setting : selectedFeature.getSettings().getList()) {
      setting.onKey(event.getKey(), event.getAction());
    }
  }
  private void handleMouseLock(MouseLockEvent event) {
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