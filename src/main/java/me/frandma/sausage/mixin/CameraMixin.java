package me.frandma.sausage.mixin;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.ClipToSpaceEvent;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public class CameraMixin {
  @Inject(method = "clipToSpace", at = @At("HEAD"), cancellable = true)
  private void onClipToSpace(float f, CallbackInfoReturnable<Float> info) {
    ClipToSpaceEvent clipToSpaceEvent = new ClipToSpaceEvent(f);
    EventManager.trigger(clipToSpaceEvent);
    info.setReturnValue(clipToSpaceEvent.getDistance());
  }
}
