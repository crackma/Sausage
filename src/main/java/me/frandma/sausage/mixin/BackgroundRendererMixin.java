package me.frandma.sausage.mixin;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.RenderFogEvent;
import net.minecraft.client.render.BackgroundRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
  @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
  private static void applyFog(CallbackInfoReturnable info) {
    RenderFogEvent renderFogEvent = new RenderFogEvent();
    EventManager.trigger(renderFogEvent);
    if (renderFogEvent.getReturnValue() != null) info.setReturnValue(renderFogEvent.getReturnValue());
  }
}
