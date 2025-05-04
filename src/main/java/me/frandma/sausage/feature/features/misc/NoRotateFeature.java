package me.frandma.sausage.feature.features.misc;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.PacketReceiveEvent;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRotationS2CPacket;
import net.minecraft.text.Text;

public class NoRotateFeature extends Feature {
  public NoRotateFeature() {
    this.toggled = false;
    super.name = "no rotate";
    super.category = Category.MISC;
    EventManager.register(PacketReceiveEvent.class, packetReceiveEvent -> {
      if (!toggled) return;
      if (!(packetReceiveEvent.getPacket() instanceof PlayerRotationS2CPacket packet)) return;
      mc.inGameHud.getChatHud().addMessage(Text.of("dropped rotation packet"));
      packetReceiveEvent.cancel();
    });
  }
}

