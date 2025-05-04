package me.frandma.sausage.feature;

import lombok.Getter;
import me.frandma.sausage.event.events.KeyboardKeyEvent;
import net.minecraft.client.MinecraftClient;

public abstract class Feature {

  @Getter
  protected Settings settings = new Settings();
  protected MinecraftClient mc = MinecraftClient.getInstance();
  @Getter
  protected String name;
  @Getter
  protected Category category;
  @Getter
  protected int keyBind;
  @Getter
  protected boolean toggled = false;
  @Getter
  protected boolean onlyWhenHeldDown;
  public Feature() {
  }
  protected void tick() { }
  protected void onKeybindPressed(KeyboardKeyEvent event) {
    if (event.getAction() == 1) toggle();
  }
  public final void enable() {
    toggled = true;
    onEnable();
  }
  protected void onEnable() { }
  public final void disable() {
    toggled = false;
    onDisable();
  }
  protected void onDisable() { }
  public final void toggle() {
    if (toggled){
      disable();
    } else {
      enable();
    }
  }
}
