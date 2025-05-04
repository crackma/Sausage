package me.frandma.sausage.feature.features.combat;

import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import org.lwjgl.glfw.GLFW;

public class KillAuraFeature extends Feature {
  public KillAuraFeature() {
    this.toggled = false;
    super.name = "kill aura";
    super.category = Category.COMBAT;
  }
  public void tick() {

  }
}
