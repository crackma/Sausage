package me.frandma.sausage.feature.features;

import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.setting.ModeSetting;
import me.frandma.sausage.feature.setting.Setting;
import me.frandma.sausage.feature.setting.SliderSetting;
import net.minecraft.block.Blocks;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.lwjgl.glfw.GLFW;

public class GhostBlockFeature extends Feature {
  private Setting<Double> maxDistance = settings.addSetting(new SliderSetting(this, "max distance", 5D, 1D, 10D, 1));
  private Setting<Mode> mode = settings.addSetting(new ModeSetting(this, "mode", Mode.ONCE));
  public GhostBlockFeature() {
    this.toggled = false;
    super.name = "GhostBlock";
    super.defaultKeyBinding = GLFW.GLFW_KEY_C;
    super.onlyWhenHeldDown = true;
  }
  protected void tick() {
    Vec3d eyePosition = mc.player.getEyePos();
    Vec3d lookDirection = mc.player.getRotationVec(1.0F).multiply(maxDistance.getValue(), maxDistance.getValue(), maxDistance.getValue());
    var hitResult = mc.world.raycast(new RaycastContext(eyePosition, eyePosition.add(lookDirection.x, lookDirection.y, lookDirection.z), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, mc.player));
    if (hitResult.getType() != HitResult.Type.BLOCK) return;
    var blockPos = hitResult.getBlockPos();
    mc.world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
    mc.worldRenderer.updateBlock(mc.world, blockPos, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 0);
  }
  public enum Mode {
    ONCE,
    TICK
  }
}
