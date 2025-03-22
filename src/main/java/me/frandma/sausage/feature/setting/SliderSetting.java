package me.frandma.sausage.feature.setting;

import lombok.Getter;
import me.frandma.sausage.feature.Feature;

@Getter
public class SliderSetting extends Setting<Double> {
  private final double min, max, step;
  public SliderSetting(Feature feature, String name, double defaultValue, double min, double max, double step) {
    super(feature, name, defaultValue);
    this.min = min;
    this.max = max;
    this.step = step;
  }
  @Override
  public Type getType() {
    return Type.SLIDER;
  }
}