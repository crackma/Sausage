package me.frandma.sausage.mixin;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.RenderPostEffectEvent;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.render.FrameGraphBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PostEffectProcessor.class)
public class PostEffectProcessorMixin {
  @Inject(method = "render", at = @At("HEAD"), cancellable = true)
  public void render(FrameGraphBuilder builder, int textureWidth, int textureHeight, PostEffectProcessor.FramebufferSet framebufferSet, CallbackInfo callbackInfo) {
    if (EventManager.trigger(new RenderPostEffectEvent())) callbackInfo.cancel();
  }
}
