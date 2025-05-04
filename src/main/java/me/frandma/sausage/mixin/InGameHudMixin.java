package me.frandma.sausage.mixin;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.RenderVignetteEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
  @Inject(method = "renderVignetteOverlay", at = @At("HEAD"), cancellable = true)
  public void renderVignetteOverlay(DrawContext context, Entity entity, CallbackInfo info) {
    if (EventManager.trigger(new RenderVignetteEvent())) info.cancel();
  }
}
