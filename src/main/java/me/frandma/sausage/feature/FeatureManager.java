package me.frandma.sausage.feature;

import lombok.Getter;
import me.frandma.sausage.feature.features.FlyFeature;
import me.frandma.sausage.feature.features.NoFallFeature;
import me.frandma.sausage.feature.features.ScrollClickFeature;
import me.frandma.sausage.feature.features.CameraClipFeature;
import me.frandma.sausage.feature.features.GhostBlockFeature;
import me.frandma.sausage.keybind.KeyBindManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

import java.util.*;

public class FeatureManager {
  private final MinecraftClient mc = MinecraftClient.getInstance();
  @Getter
  private final List<Feature> featureList = new ArrayList<>();
  private final Map<Class<? extends Feature>, Feature> featureMap = new HashMap<>();
  public FeatureManager() {
    initFeature(new CameraClipFeature());
    initFeature(new FlyFeature());
    initFeature(new GhostBlockFeature());
    initFeature(new NoFallFeature());
    initFeature(new ScrollClickFeature());
    ClientTickEvents.START_CLIENT_TICK.register(client -> {
      if (mc.world == null) return;
      for (Feature feature : featureList) {
        if (feature.isToggled()) feature.tick();
      }
    });
  }
  private void initFeature(Feature feature) {
    featureMap.put(feature.getClass(), feature);
    featureList.add(feature);
    KeyBindManager.initFeature(feature);
  }
  public <T extends Feature> T getInstance(Class<T> featureClass) {
    return featureClass.cast(featureMap.get(featureClass));
  }
}
