package me.frandma.sausage.mixins;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.MouseButtonEvent;
import me.frandma.sausage.event.events.MouseCursorPosEvent;
import me.frandma.sausage.event.events.MouseLockEvent;
import me.frandma.sausage.event.events.MouseScrollEvent;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
  @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
  public void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo info) {
    if (EventManager.trigger(new MouseScrollEvent(window, horizontal, vertical))) info.cancel();
  }
  @Inject(method = "onCursorPos", at = @At("HEAD"), cancellable = true)
  private void onCursorPos(long window, double x, double y, CallbackInfo info) {
    MouseCursorPosEvent mouseCursorPosEvent = new MouseCursorPosEvent(window, x, y);
    if (EventManager.trigger(mouseCursorPosEvent)) info.cancel();
  }
  @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
  private void onMouseButton(long window, int button, int action, int mods, CallbackInfo info) {
    if (EventManager.trigger(new MouseButtonEvent(window, button, action))) info.cancel();
  }
  @Inject(method = "lockCursor", at = @At("HEAD"), cancellable = true)
  public void lockCursor(CallbackInfo info) {
    if (EventManager.trigger(new MouseLockEvent())) info.cancel();
  }
}
