package me.frandma.sausage.client;

import me.frandma.sausage.feature.FeatureManager;
import me.frandma.sausage.render.HudRenderModuleList;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class SausageClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(new HudRenderModuleList());
        FeatureManager.init();
    }
}
