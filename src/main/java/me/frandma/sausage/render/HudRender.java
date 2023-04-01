package me.frandma.sausage.render;

import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.FeatureManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class HudRender implements HudRenderCallback {

    private MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        if (MinecraftClient.getInstance().options.debugEnabled) return;
        DrawableHelper.drawTextWithShadow(matrixStack, mc.textRenderer,
                Text.of("SausageMod"),
                0, 0, 0xffffff);
        int y = 9;
        for (Feature feature : FeatureManager.getFeatures()) {
            if (!feature.isToggled()) continue;
            DrawableHelper.drawTextWithShadow(matrixStack, MinecraftClient.getInstance().textRenderer, Text.of("- §a" + feature.getName()), 0, y, 0xffffff);
            y = y + 9;
        }
    }
}
