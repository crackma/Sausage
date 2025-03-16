package me.frandma.sausage.feature;

import lombok.Getter;
import net.minecraft.client.MinecraftClient;

public abstract class Feature {
  @Getter
  protected Settings settings = new Settings();
  protected MinecraftClient mc = MinecraftClient.getInstance();
  @Getter
  protected String name;
  @Getter
  protected int defaultKeyBinding;
  @Getter
  protected boolean toggled;
  //TODO toggle on bind release
  @Getter
  protected boolean onlyWhenHeldDown;
  public Feature() { }
  protected void tick() { }
  public final void enable() {
    toggled = true;
  }
  public final void disable() {
    toggled = false;
  }
  public final void toggle() {
    if (toggled){
      disable();
    } else {
      enable();
    }
  }
}
