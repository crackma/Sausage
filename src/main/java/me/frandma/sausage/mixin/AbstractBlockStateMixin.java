package me.frandma.sausage.mixin;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.LuminanceRequestEvent;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin {
  @Inject(method = "getLuminance", at = @At("RETURN"), cancellable = true)
  public void getLuminance(CallbackInfoReturnable<Integer> info) {
    LuminanceRequestEvent luminanceRequestEvent = new LuminanceRequestEvent();
    EventManager.trigger(luminanceRequestEvent);
    if (luminanceRequestEvent.getLuminance() != -1) info.setReturnValue(luminanceRequestEvent.getLuminance());
  }
}
