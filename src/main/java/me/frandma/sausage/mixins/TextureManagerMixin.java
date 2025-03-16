package me.frandma.sausage.mixins;

import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.ReloadableTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureManager.class)
public class TextureManagerMixin {
  @Inject(method = "registerTexture", at = @At("HEAD"))
  public void registerTexture(Identifier id, ReloadableTexture texture, CallbackInfo info) {
    System.out.println(id.getNamespace() + ", " + id.getPath());
  }
}
