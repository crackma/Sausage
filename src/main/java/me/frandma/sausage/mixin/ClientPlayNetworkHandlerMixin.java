package me.frandma.sausage.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
  @Redirect(
      method = "setPosition",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/entity/Entity;setYaw(F)V"
      )
  )
  private static void redirectSetYaw(Entity entity, float yaw) {
    if (entity == MinecraftClient.getInstance().player) return;
    entity.setYaw(yaw);
  }
  @Redirect(
      method = "setPosition",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/entity/Entity;setPitch(F)V"
      )
  )
  private static void redirectSetPitch(Entity entity, float pitch) {
    if (entity == MinecraftClient.getInstance().player) return;
    entity.setPitch(pitch);
  }
}
