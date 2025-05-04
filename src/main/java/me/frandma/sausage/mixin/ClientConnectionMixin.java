package me.frandma.sausage.mixin;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.PacketReceiveEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
  @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
  private static <T extends PacketListener> void handlePacket(Packet<T> packet, PacketListener listener, CallbackInfo info) {
    if (EventManager.trigger(new PacketReceiveEvent(packet))) info.cancel();
  }
}
