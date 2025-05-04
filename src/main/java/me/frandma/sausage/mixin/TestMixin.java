package me.frandma.sausage.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class TestMixin {
  @Shadow private Frustum frustum;
  @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Frustum;setPosition(DDD)V"))
  public void render(
      ObjectAllocator allocator,
      RenderTickCounter tickCounter,
      boolean renderBlockOutline,
      Camera camera,
      GameRenderer gameRenderer,
      Matrix4f positionMatrix,
      Matrix4f projectionMatrix,
      CallbackInfo info
  ) {
//    double cameraDistance = YourMod.getCameraDistance();
//    int renderDistance = 32;
//    double farPlane = cameraDistance + (renderDistance * 16) + 16;
//    projectionMatrix.multiply(Matrix4f.projection(
//        /* fov */, /* aspect */, /* near */, farPlane
//    ));
//    Frustum frustum = this.frustum;
//    Vec3d vec3d = camera.getPos();
//    frustum.setPosition(vec3d.x, vec3d.y, vec3d.z);
//    frustum.calculateFrustum(projectionMatrix, viewMatrix);
  }
}
