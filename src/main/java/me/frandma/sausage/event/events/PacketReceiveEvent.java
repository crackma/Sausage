package me.frandma.sausage.event.events;

import lombok.Getter;
import me.frandma.sausage.event.Cancellable;
import me.frandma.sausage.event.Event;
import net.minecraft.network.packet.Packet;

public class PacketReceiveEvent extends Cancellable implements Event {
  @Getter
  private final Packet<?> packet;
  public PacketReceiveEvent(Packet<?> packet) {
    this.packet = packet;
  }
}
