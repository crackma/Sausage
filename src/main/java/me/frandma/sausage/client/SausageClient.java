package me.frandma.sausage.client;

import me.frandma.sausage.feature.FeatureManager;
import me.frandma.sausage.render.HudRender;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class SausageClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(new HudRender());
        FeatureManager.initFeatures();
    }
}
