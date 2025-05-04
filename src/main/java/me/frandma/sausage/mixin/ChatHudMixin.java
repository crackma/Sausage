package me.frandma.sausage.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatHudMixin {
  @Definition(id = "visibleMessages", field = "Lnet/minecraft/client/gui/hud/ChatHud;visibleMessages:Ljava/util/List;")
  @Definition(id = "size", method = "Ljava/util/List;size()I")
  @Expression("this.visibleMessages.size() > 100")
  @ModifyExpressionValue(method = "addVisibleMessage", at = @At("MIXINEXTRAS:EXPRESSION"))
  public boolean addVisibleMessage(boolean original) {
    return false;
  }
  @Definition(id = "messages", field = "Lnet/minecraft/client/gui/hud/ChatHud;messages:Ljava/util/List;")
  @Definition(id = "size", method = "Ljava/util/List;size()I")
  @Expression("this.messages.size() > 100")
  @ModifyExpressionValue(method = "addMessage(Lnet/minecraft/client/gui/hud/ChatHudLine;)V", at = @At("MIXINEXTRAS:EXPRESSION"))
  public boolean addMessage(boolean original) {
    return false;
  }
  @Inject(method = "clear", at = @At("HEAD"), cancellable = true)
  private void clear(boolean clearHistory, CallbackInfo info) {
    info.cancel();
  }
  @Inject(method = "restoreChatState", at = @At("HEAD"), cancellable = true)
  public void restoreChatState(ChatHud.ChatState state, CallbackInfo info) {
    info.cancel();
  }
}
