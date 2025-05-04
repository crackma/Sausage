package me.frandma.sausage.render;

import lombok.Getter;
import lombok.Setter;
import me.frandma.sausage.mixin.DrawContextAccessor;
import me.frandma.sausage.render.old.SausageFont;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class RenderUtil {
  @Getter @Setter
  public static int mouseX, mouseY, lastMouseX, lastMouseY;
  public static void updatePositions(int mouseX, int mouseY) {
    lastMouseX = RenderUtil.mouseX;
    lastMouseY = RenderUtil.mouseY;
    RenderUtil.mouseX = mouseX;
    RenderUtil.mouseY = mouseY;
  }
  public static void renderText(DrawContext drawContext, String text, int x, int y, int color) {
    MatrixStack matrices = drawContext.getMatrices();
    matrices.push();
    matrices.translate(x, y, 0);
    VertexConsumerProvider.Immediate immediate = ((DrawContextAccessor)drawContext).getVertexConsumers();
    MinecraftClient.getInstance().textRenderer.draw(
        Text.literal(text)
            .setStyle(Style.EMPTY.withFont(SausageFont.TAHOMA.getIdentifier())),
        0, 0,
        color,
        true,
        matrices.peek().getPositionMatrix(),
        immediate,
        TextRenderer.TextLayerType.SEE_THROUGH,
        0,
        0xF000F0
    );
    matrices.pop();
  }
  public static void drawTexture(DrawContext drawContext, Identifier texture, int x, int y, int width, int height) {
    drawContext.drawTexture(RenderLayer::getGuiTextured, texture, x, y, 0, 0, width, height, width, height, -1);
  }
  public static boolean isMouseOver(UIComponent component) {
    return isMouseOver(component.getX(), component.getY(), component.getWidth(), component.getHeight());
  }
  public static boolean isMouseOver(double x, double y, double width, double height) {
    return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
  }
}
