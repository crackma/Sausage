package me.frandma.sausage.mixin;

import net.minecraft.client.texture.PlayerSkinProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.nio.file.Path;

@Mixin(PlayerSkinProvider.FileCache.class)
public interface FileCacheAccessor {
  @Accessor
  Path getDirectory();
}
