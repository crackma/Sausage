package me.frandma.sausage.mixin;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.PacketSendEvent;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonNetworkHandler.class)
public class ClientCommonNetworkHandlerMixin {
  @Shadow
  private ClientConnection connection;
  @Inject(method = "onPing", at = @At("HEAD"))
  public void onPing(CommonPingS2CPacket packet, CallbackInfo info) {

  }
  @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
  public void sendPacket(Packet<?> packet, CallbackInfo info) {
    info.cancel();
    PacketSendEvent packetSendEvent = new PacketSendEvent(packet);
    EventManager.trigger(packetSendEvent);
    this.connection.send(packetSendEvent.getPacket());
  }
}
