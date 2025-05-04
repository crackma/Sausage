package me.frandma.sausage.feature.features.render;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.LuminanceRequestEvent;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.setting.NumberSetting;
import me.frandma.sausage.feature.Setting;

public class FullBrightFeature extends Feature {
  private final Setting<Double> luminance = settings.addSetting(new NumberSetting(this, "luminance", 15D, 0, 15, 1));
  public FullBrightFeature() {
    this.toggled = true;
    super.name = "full bright";
    super.category = Category.RENDER;
    EventManager.register(LuminanceRequestEvent.class, luminanceRequestEvent -> {
      if (!toggled) return;
      luminanceRequestEvent.setLuminance(luminance.getValue().intValue());
    });
  }
}
