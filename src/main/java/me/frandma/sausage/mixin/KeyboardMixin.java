package me.frandma.sausage.mixin;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.KeyboardCharEvent;
import me.frandma.sausage.event.events.KeyboardKeyEvent;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
  @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
  public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo info) {
    if (EventManager.trigger(new KeyboardKeyEvent(window, key, scancode, action, modifiers))) info.cancel();
  }
  @Inject(method = "onChar", at = @At("HEAD"), cancellable = true)
  public void onChar(long window, int codePoint, int modifiers, CallbackInfo info) {
    if (EventManager.trigger(new KeyboardCharEvent(window, codePoint, modifiers))) info.cancel();
  }
}
