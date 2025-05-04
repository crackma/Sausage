package me.frandma.sausage.feature.features.movement;

import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.setting.EnumSetting;
import me.frandma.sausage.feature.setting.NumberSetting;
import me.frandma.sausage.feature.Setting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class FlyFeature extends Feature {
  private final Setting<Mode> mode = settings.addSetting(new EnumSetting<>(this, "mode", Mode.VANILLA));
  private final Setting<Handling> handling = settings.addSetting(new EnumSetting<>(this, "handling", Handling.NORMAL));
  private final Setting<Double> speed = settings.addSetting(new NumberSetting(this, "speed", 1D, 0.1D, 10D, 0.1, 1, ""));
  private final Setting<Double> sprintSpeed = settings.addSetting(new NumberSetting(this, "sprint speed", 2D, 0.1D, 10D, 0.1D, 1, ""));
  private boolean oddTick;
  public FlyFeature() {
    this.toggled = false;
    super.name = "fly";
    super.category = Category.MOVEMENT;
  }
  protected void tick() {
    oddTick = !oddTick;
    mc.player.getInventory().contains(new ItemStack(Items.STONE));
    if (mc.player.getWorld().isSpaceEmpty(mc.player.getBoundingBox().offset(0, -0.05D, 0))) {
      Vec3d vec3d = mc.player.getPos().subtract(0, 0.0433D, 0);
      if (mc.player.getWorld().isBlockSpaceEmpty(mc.player, new Box(vec3d, vec3d)))
      mc.getNetworkHandler().getConnection().send( new PlayerMoveC2SPacket.Full( vec3d.x, vec3d.y, vec3d.z, mc.player.getYaw(), mc.player.getPitch(), false, false ) );
    }
    Vec3d vec3d = Vec3d.ZERO;
    double speed = mc.options.sprintKey.isPressed() ? this.sprintSpeed.getValue() : this.speed.getValue();
    if (mode.getValue() == Mode.VULCAN) {
      if (oddTick) speed = 0.1;
    }
    switch (handling.getValue()) {
      case Handling.NORMAL:
        if (mc.options.forwardKey.isPressed())vec3d = vec3d.add(getMovementOffset(0).multiply(speed));
        if (mc.options.rightKey.isPressed())  vec3d = vec3d.add(getMovementOffset(90).multiply(speed));
        if (mc.options.leftKey.isPressed())   vec3d = vec3d.add(getMovementOffset(-90).multiply(speed));
        if (mc.options.backKey.isPressed())   vec3d = vec3d.add(getMovementOffset(180).multiply(speed));
        if (mc.options.jumpKey.isPressed())   vec3d = vec3d.add(new Vec3d(0, 1, 0).multiply(speed));
        if (mc.options.sneakKey.isPressed())  vec3d = vec3d.add(new Vec3d(0, -1, 0).multiply(speed));
        if (vec3d.lengthSquared() > 0) vec3d = vec3d.normalize().multiply(speed);
        break;
      case Handling.ELYTRA:
        double forward = (mc.options.forwardKey.isPressed() ? 1 : 0) - (mc.options.backKey.isPressed() ? 1 : 0);
        double strafe = (mc.options.rightKey.isPressed() ? 1 : 0) - (mc.options.leftKey.isPressed() ? 1 : 0);
        double up = (mc.options.jumpKey.isPressed() ? 1 : 0) - (mc.options.sneakKey.isPressed() ? 1 : 0);
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
        double xVelocity = -sinYaw * forward * cosPitch * speed + cosYaw * -strafe * speed;
        double yVelocity = -sinPitch * forward * speed;
        double zVelocity = cosYaw * forward * cosPitch * speed + sinYaw * -strafe * speed;
        vec3d = new Vec3d(xVelocity, yVelocity, zVelocity);
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

  @Override
  protected void onDisable() {
    if (mc.world == null || mc.player == null) return;
    mc.player.getAbilities().flying = false;
  }

  public enum Mode {
    VANILLA,
    VULCAN
  }
  public enum Handling {
    NORMAL,
    ELYTRA
  }
}