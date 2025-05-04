package me.frandma.sausage.event.events;

import lombok.Getter;
import lombok.Setter;
import me.frandma.sausage.event.Event;
import net.minecraft.network.packet.Packet;

public class PacketSendEvent implements Event {
  @Getter
  private final Packet<?> originalPacket;
  @Getter
  @Setter
  private Packet<?> packet;
  public PacketSendEvent(Packet<?> originalPacket) {
    this.originalPacket = originalPacket;
    this.packet = originalPacket;
  }
}
