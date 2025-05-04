package me.frandma.sausage.feature.setting;

import lombok.Getter;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.Setting;

import java.util.HashSet;
import java.util.Set;

@Getter
public class MultipleEnumSetting<T extends Enum<T>> extends Setting<Set<T>> {
  private final T[] modes;
  double x, y;
  public MultipleEnumSetting(Feature feature, String name, Set<T> defaultValue) {
    super(feature, name, defaultValue);
    this.modes = defaultValue.iterator().next().getDeclaringClass().getEnumConstants();
  }
  @Override
  public Type getType() {
    return Type.MULTIPLE_ENUM;
  }
  public void toggleValue(T value) {
    Set<T> newValue = new HashSet<>(getValue());
    if (newValue.contains(value)) {
      newValue.remove(value);
    } else {
      newValue.add(value);
    }
    set(newValue);
  }
}

