package me.frandma.sausage.feature.features.world;

import me.frandma.sausage.event.events.KeyboardKeyEvent;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.setting.EnumSetting;
import me.frandma.sausage.feature.Setting;
import me.frandma.sausage.feature.setting.NumberSetting;
import net.minecraft.block.Blocks;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

public class GhostBlockFeature extends Feature {
  private final Setting<Double> maxDistance = settings.addSetting(new NumberSetting(this, "max distance", 5D, 1D, 10D, 1));
  private final Setting<Mode> mode = settings.addSetting(new EnumSetting(this, "mode", Mode.ONCE));
  private final Setting<Double> tickSkip = settings.addSetting(new NumberSetting(this, "tick skip", 5D, 0D, 20D, 1));
  private int skippedTicks = 0;
  public GhostBlockFeature() {
    this.toggled = false;
    super.name = "ghost block";
    super.category = Category.WORLD;
  }
  protected void tick() {
    if (mode.getValue() != Mode.TICK && mode.getValue() != Mode.HOLDING) return;
    if (skippedTicks < tickSkip.getValue()) {
      skippedTicks++;
      return;
    }
    updateBlock();
  }
  @Override
  protected void onKeybindPressed(KeyboardKeyEvent event) {
    switch (mode.getValue()) {
      case ONCE:
        if (event.getAction() != 1) return;
        updateBlock();
        toggle();
        break;
      case TICK:
        if (event.getAction() == 1) toggle();
        break;
      case HOLDING:
        toggle();
        break;
    }
  }
  @Override
  protected void onEnable() {
    if (mode.getValue() != Mode.ONCE) return;
    updateBlock();
    toggle();
  }
  private void updateBlock() {
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
    TICK,
    HOLDING
  }
}
