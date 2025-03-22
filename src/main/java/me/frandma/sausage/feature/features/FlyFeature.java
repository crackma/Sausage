package me.frandma.sausage.feature.features;

import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.Settings;
import me.frandma.sausage.feature.setting.ModeSetting;
import me.frandma.sausage.feature.setting.Setting;
import me.frandma.sausage.feature.setting.SliderSetting;
import me.frandma.sausage.feature.setting.ToggleSetting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.lwjgl.glfw.GLFW;

public class FlyFeature extends Feature {
  private final Setting<Mode> flyMode = settings.addSetting(new ModeSetting(this, "modeg", Mode.NORMAL));
  private final Setting<Double> speed = settings.addSetting(new SliderSetting(this, "speed", 1D, 0.5D, 10D, 0.5));
  private final Setting<Double> sprintSpeed = settings.addSetting(new SliderSetting(this, "sprint speed", 2D, 0.5D, 10D, 0.5));
  public FlyFeature() {
    this.toggled = false;
    super.name = "fly";
    super.category = Category.MOVEMENT;
    super.defaultKeyBinding = GLFW.GLFW_KEY_Z;
  }
  protected void tick() {
    mc.player.getInventory().contains(new ItemStack(Items.STONE));
    if (mc.player.getWorld().isSpaceEmpty(mc.player.getBoundingBox().offset(0, -0.0433D, 0))) {
      Vec3d vec3d = mc.player.getPos().subtract(0, 0.0433D, 0);
      if (mc.player.getWorld().isBlockSpaceEmpty(mc.player, new Box(vec3d, vec3d)))
      mc.getNetworkHandler().getConnection().send( new PlayerMoveC2SPacket.Full( vec3d.x, vec3d.y, vec3d.z, mc.player.getYaw(), mc.player.getPitch(), false, false ) );
    }
    Vec3d vec3d = Vec3d.ZERO;
    double speed = mc.options.sprintKey.isPressed() ? this.sprintSpeed.getValue() : this.speed.getValue();
    switch (flyMode.getValue()) {
      case Mode.NORMAL:
        if (mc.options.forwardKey.isPressed())vec3d = vec3d.add(getMovementOffset(0).multiply(speed));
        if (mc.options.rightKey.isPressed())  vec3d = vec3d.add(getMovementOffset(90).multiply(speed));
        if (mc.options.leftKey.isPressed())   vec3d = vec3d.add(getMovementOffset(-90).multiply(speed));
        if (mc.options.backKey.isPressed())   vec3d = vec3d.add(getMovementOffset(180).multiply(speed));
        if (mc.options.jumpKey.isPressed())   vec3d = vec3d.add(new Vec3d(0, 1, 0).multiply(speed));
        if (mc.options.sneakKey.isPressed())  vec3d = vec3d.add(new Vec3d(0, -1, 0).multiply(speed));
        if (vec3d.lengthSquared() > 0) vec3d = vec3d.normalize().multiply(speed);
        break;
      case Mode.CS:
        //sin (0) = 0; cos(0) = 1
        double forward = (mc.options.forwardKey.isPressed() ? 1 : 0) - (mc.options.backKey.isPressed() ? 1 : 0);
        double strafe = (mc.options.rightKey.isPressed() ? 1 : 0) - (mc.options.leftKey.isPressed() ? 1 : 0);
        double inputMagnitude = Math.sqrt(forward * forward + strafe * strafe);
        if (inputMagnitude > 1.0) {
          forward /= inputMagnitude;
          strafe /= inputMagnitude;
        }
        double yawRad = Math.toRadians(mc.player.getYaw());
        double pitchRad = Math.toRadians(mc.player.getPitch());
        double sinYaw = Math.sin(yawRad);
        double cosYaw = Math.cos(yawRad);
        double sinPitch = Math.sin(pitchRad);
        double cosPitch = Math.cos(pitchRad);
        double xVelocity = (-sinYaw * forward + cosYaw * -strafe) * cosPitch * speed;
        double yVelocity = -sinPitch * forward * speed; // Adjust for vertical movement
        double zVelocity = (cosYaw * forward + sinYaw * -strafe) * cosPitch * speed;
        vec3d.add(xVelocity, yVelocity, zVelocity);
        break;
    }
    mc.player.setVelocity(vec3d);
  }
  private Vec3d getMovementOffset(int yawOffset) {
    double radians = Math.toRadians(mc.player.getYaw() + yawOffset);
    double sin = -Math.sin(radians);
    double cos = Math.cos(radians);
    return new Vec3d(sin, 0, cos);
  }
  public enum Mode {
    NORMAL,
    CS
  }
}