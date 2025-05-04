package me.frandma.sausage.feature;

import lombok.Getter;
import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.KeyboardKeyEvent;
import me.frandma.sausage.feature.features.TestFeature;
import me.frandma.sausage.feature.features.combat.KillAuraFeature;
import me.frandma.sausage.feature.features.hud.ArraylistFeature;
import me.frandma.sausage.feature.features.misc.NoRotateFeature;
import me.frandma.sausage.feature.features.render.FullBrightFeature;
import me.frandma.sausage.feature.features.render.NoRenderFeature;
import me.frandma.sausage.feature.features.world.GhostBlockFeature;
import me.frandma.sausage.feature.features.misc.PacketLoggerFeature;
import me.frandma.sausage.feature.features.misc.ScrollClickFeature;
import me.frandma.sausage.feature.features.movement.FlyFeature;
import me.frandma.sausage.feature.features.movement.NoFallFeature;
import me.frandma.sausage.feature.features.movement.SpeedFeature;
import me.frandma.sausage.feature.features.render.CameraClipFeature;
import me.frandma.sausage.keybind.KeyBindManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

import java.util.*;

public class FeatureManager {
  @Getter
  private static FeatureManager instance;
  private final MinecraftClient mc = MinecraftClient.getInstance();
  @Getter
  private final List<Feature> featureList = new ArrayList<>();
  private final Map<Class<? extends Feature>, Feature> featureMap = new HashMap<>();
  public FeatureManager() {
    instance = this;
    initFeature(new KillAuraFeature());
    initFeature(new ArraylistFeature());
    initFeature(new NoRotateFeature());
    initFeature(new PacketLoggerFeature());
    initFeature(new ScrollClickFeature());
    initFeature(new FlyFeature());
    initFeature(new NoFallFeature());
    initFeature(new SpeedFeature());
    initFeature(new CameraClipFeature());
    initFeature(new FullBrightFeature());
    initFeature(new NoRenderFeature());
    initFeature(new GhostBlockFeature());
    initFeature(new TestFeature());
    ClientTickEvents.START_CLIENT_TICK.register(client -> {
      if (mc.world == null) return;
      featureList.forEach(feature -> {
        if (feature.isToggled()) feature.tick();
      });
    });
    EventManager.register(KeyboardKeyEvent.class, keyboardKeyEvent -> {
      if (mc.world == null) return;
      featureList.forEach(feature -> {
        if (keyboardKeyEvent.getKey() == feature.getKeyBind()) feature.onKeybindPressed(keyboardKeyEvent);
      });
    });
  }
  private void initFeature(Feature feature) {
    featureMap.put(feature.getClass(), feature);
    featureList.add(feature);
  }
  public <T extends Feature> T getInstance(Class<T> featureClass) {
    return featureClass.cast(featureMap.get(featureClass));
  }
}
