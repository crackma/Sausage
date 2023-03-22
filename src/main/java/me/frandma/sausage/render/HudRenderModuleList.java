package me.frandma.sausage.render;

import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.FeatureManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class HudRenderModuleList implements HudRenderCallback {

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        DrawableHelper.drawTextWithShadow(matrixStack, MinecraftClient.getInstance().textRenderer, Text.of("Modules:"), 0, 0, 0xffffff);
        int y = 9;
        for (Feature module : FeatureManager.getModules()) {
            if (!module.isToggled()) continue;
            DrawableHelper.drawTextWithShadow(matrixStack, MinecraftClient.getInstance().textRenderer, Text.of("- §a" + module.getName()), 0, y, 0xffffff);
            y = y + 9;
        }
    }
}
