package me.frandma.sausage.mixins;

import net.minecraft.client.font.FontManager;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.BufferAllocator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Tessellator.class)
public interface TessellatorAccessor {
  @Accessor("allocator")
  BufferAllocator getBufferAllocator();
}
