package me.frandma.sausage.feature.features.misc;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.PacketReceiveEvent;
import me.frandma.sausage.event.events.PacketSendEvent;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class PacketLoggerFeature extends Feature {
  public PacketLoggerFeature() {
    this.toggled = false;
    super.name = "packet logger";
    super.category = Category.MISC;
    EventManager.register(PacketReceiveEvent.class, packetReceiveEvent -> {
      if (!toggled) return;
      if (!(packetReceiveEvent.getPacket() instanceof CommonPingS2CPacket packet)) return;
      mc.inGameHud.getChatHud().addMessage(Text.of(packet.getParameter() + ""));
    });
  }
}
