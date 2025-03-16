package me.frandma.sausage.feature.setting;

import me.frandma.sausage.feature.Feature;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public abstract class Setting<T> {
  public final MinecraftClient mc = MinecraftClient.getInstance();
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
  public String getName() {
    return name;
  }
  public T getDefaultValue() {
    return defaultValue;
  }
  public T getValue() {
    return value;
  }
  public void set(T value) {
    this.value = value;
  }
  public abstract int onRender(DrawContext drawContext, final int x, final int y);
  public abstract void onClick(double mouseX, double mouseY, int button, int action);
  public abstract void onKey(int key, int action);
}
