package me.frandma.sausage;

import lombok.Getter;
import me.frandma.sausage.feature.FeatureManager;
import me.frandma.sausage.render.ClickGUI;
import me.frandma.sausage.render.HudRender;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

@Getter
public class Sausage implements ModInitializer {
    @Getter
    private static Sausage instance;
    private ClickGUI clickGUI;
    private FeatureManager featureManager;
    @Override
    public void onInitialize() {
        instance = this;
        MinecraftClient mc = MinecraftClient.getInstance();
        featureManager = new FeatureManager();
        clickGUI = new ClickGUI(this);
        new HudRender(this);
    }
}
