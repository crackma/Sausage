package me.frandma.sausage.mixin;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.PerspectiveChangeEvent;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Perspective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
  @Inject(method = "setPerspective", at = @At("HEAD"))
  public void setPerspective(Perspective perspective, CallbackInfo info) {
    EventManager.trigger(new PerspectiveChangeEvent(perspective));
  }
}
