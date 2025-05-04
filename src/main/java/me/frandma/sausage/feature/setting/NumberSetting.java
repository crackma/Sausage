package me.frandma.sausage.feature.setting;

import lombok.Getter;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.Setting;

@Getter
public class NumberSetting extends Setting<Double> {
  private final double min, max, step;
  private final int precision;
  private final String suffix;
  public NumberSetting(Feature feature, String name, Double defaultValue, double min, double max, double step, int precision, String suffix) {
    super(feature, name, defaultValue);
    this.min = min;
    this.max = max;
    this.step = step;
    this.precision = precision;
    this.suffix = suffix;
  }
  public NumberSetting(Feature feature, String name, Double defaultValue, double min, double max, double step) {
    super(feature, name, defaultValue);
    this.min = min;
    this.max = max;
    this.step = step;
    this.precision = 0;
    this.suffix = "";
  }
  @Override
  public Type getType() {
    return Type.NUMBER;
  }
}