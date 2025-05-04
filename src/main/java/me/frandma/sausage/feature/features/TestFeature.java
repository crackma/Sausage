package me.frandma.sausage.feature.features;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.TestEvent;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.setting.NumberSetting;
import me.frandma.sausage.feature.Setting;

public class TestFeature extends Feature {
  private final Setting<Double> clamp = settings.addSetting(new NumberSetting(this, "clamp", 4D, 1, 150D, 1));
  public TestFeature() {
    super.name = "test";
    super.category = Category.MISC;
    EventManager.register(TestEvent.class, testEvent -> {
      testEvent.setClampedViewDistance(clamp.getValue().intValue());
    });
  }
}
