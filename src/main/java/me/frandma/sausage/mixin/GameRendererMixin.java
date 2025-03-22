package me.frandma.sausage.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.RenderHudEvent;
import me.frandma.sausage.event.events.RenderEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
  @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;draw()V", ordinal = 0))
  public void hudRender(RenderTickCounter tickCounter, boolean tick, CallbackInfo info, @Local DrawContext drawContext) {
    EventManager.trigger(new RenderHudEvent(drawContext));
  }
  @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;draw()V", ordinal = 1))
  public void render(RenderTickCounter tickCounter, boolean tick, CallbackInfo info, @Local DrawContext drawContext) {
    EventManager.trigger(new RenderEvent(drawContext));
  }
}
