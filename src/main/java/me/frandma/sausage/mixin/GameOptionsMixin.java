package me.frandma.sausage.mixin;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.PerspectiveChangeEvent;
import me.frandma.sausage.event.events.TestEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Perspective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
  @Inject(method = "setPerspective", at = @At("HEAD"))
  public void setPerspective(Perspective perspective, CallbackInfo info) {
    EventManager.trigger(new PerspectiveChangeEvent(perspective));
  }
  @Inject(method = "getClampedViewDistance", at = @At("HEAD"), cancellable = true)
  public void getClampedViewDistance(CallbackInfoReturnable<Integer> info) {
    //TestEvent testEvent = new TestEvent();
    //EventManager.trigger(testEvent);
    //info.setReturnValue(testEvent.getClampedViewDistance());
  }
}
