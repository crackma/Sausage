package me.frandma.sausage.util;

import me.frandma.sausage.mixin.MinecraftClientInvoker;
import net.minecraft.client.MinecraftClient;

public class Util {
  private static final MinecraftClient mc = MinecraftClient.getInstance();
  private static final MinecraftClientInvoker mcInvoker = (MinecraftClientInvoker) mc;
  public static void doAttack() {
    if (mc.interactionManager == null) return;
    mc.options.attackKey.setPressed(true);
    mcInvoker.invokeDoAttack();
    mc.options.attackKey.setPressed(false);
  }
  public static void doItemUse() {
    if (mc.interactionManager == null) return;
    mcInvoker.invokeDoItemUse();
  }
}
