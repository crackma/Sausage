package me.frandma.sausage.feature.features;

import me.frandma.sausage.feature.Feature;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.lwjgl.glfw.GLFW;

public class NoFallFeature extends Feature {
  public NoFallFeature() {
    super.name = "NoFall";
    super.defaultKeyBinding = GLFW.GLFW_KEY_5;
  }
  protected void tick() {
    if (mc.player.fallDistance > 2) mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true, false));
  }
}
