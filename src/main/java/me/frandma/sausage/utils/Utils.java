package me.frandma.sausage.utils;

import me.frandma.sausage.mixins.MinecraftClientInvoker;
import net.minecraft.client.MinecraftClient;

public class Utils {
  private static final MinecraftClient mc = MinecraftClient.getInstance();
  private static final MinecraftClientInvoker mcInvoker = (MinecraftClientInvoker) mc;
  public static void doAttack() {
    mc.options.attackKey.setPressed(true);
    mcInvoker.invokeDoAttack();
    mc.options.attackKey.setPressed(false);
  }
  public static void doItemUse() {
    mcInvoker.invokeDoItemUse();
  }
}
