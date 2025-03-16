package me.frandma.sausage.mixins;

import me.frandma.sausage.Sausage;
import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.ChangeInventorySlotEvent;
import me.frandma.sausage.feature.features.CameraClipFeature;
import me.frandma.sausage.feature.features.ScrollClickFeature;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
  @Inject(method = "setSelectedSlot", at = @At("HEAD"), cancellable = true)
  public void setSelectedSlot(int slot, CallbackInfo info) {
    if (EventManager.trigger(new ChangeInventorySlotEvent(slot))) info.cancel();
  }
}
