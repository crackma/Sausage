package me.frandma.sausage.feature.features;

import me.frandma.sausage.feature.Feature;
import org.lwjgl.glfw.GLFW;

public class KillAuraFeature extends Feature {
  public KillAuraFeature() {
    this.toggled = false;
    super.name = "KillAura";
    super.defaultKeyBinding = GLFW.GLFW_KEY_Y;
  }
  public void tick() {

  }
}
