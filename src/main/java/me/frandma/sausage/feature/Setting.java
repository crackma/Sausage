package me.frandma.sausage.feature;

import lombok.Getter;

@Getter
public abstract class Setting<T> {
  private final String name;
  private final T defaultValue;
  private T value;
  private final Feature feature;
  public Setting(Feature feature, String name, T defaultValue) {
    this.name = name;
    this.defaultValue = defaultValue;
    this.value = defaultValue;
    this.feature = feature;
  }
  public abstract Type getType();
  public void set(T value) {
    this.value = value;
  }
  public enum Type {
    BOOLEAN,
    ENUM,
    MULTIPLE_ENUM,
    NUMBER_RANGE,
    NUMBER,
    STRING
  }
}
