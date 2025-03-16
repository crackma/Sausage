package me.frandma.sausage.render;

import me.frandma.sausage.Sausage;
import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.RenderHudEvent;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.FeatureManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class HudRender {
    private FeatureManager featureManager;
    private MinecraftClient mc = MinecraftClient.getInstance();
    public HudRender(Sausage sausage) {
        featureManager = sausage.getFeatureManager();
        EventManager.register(RenderHudEvent.class, renderHudEvent -> {
            if ( mc.getDebugHud().shouldShowDebugHud() ) return;
            DrawContext drawContext = renderHudEvent.getDrawContext();
            drawContext.getMatrices().translate(0, 0, 300);
            drawContext.fill( 0, 0, 100, 100, 1000, 0xFFFFF );
            int y = 0;
            for ( Feature feature : featureManager.getFeatureList() ) {
                if ( !feature.isToggled() ) continue;
                drawContext.drawTextWithShadow( mc.textRenderer, "- §a" + feature.getName(), 0, y, 0xffffff );
                y = y + 9;
            }
        });
    }
}
