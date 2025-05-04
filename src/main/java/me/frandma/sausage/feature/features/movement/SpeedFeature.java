package me.frandma.sausage.feature.features.movement;

import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.setting.EnumSetting;
import me.frandma.sausage.feature.setting.NumberSetting;
import me.frandma.sausage.feature.Setting;
import net.minecraft.util.math.Vec3d;

public class SpeedFeature extends Feature {
  private final Setting<Mode> mode = settings.addSetting(new EnumSetting<>(this, "mode", Mode.VANILLA));
  private final Setting<Double> bps = settings.addSetting(new NumberSetting(this, "bps", 1D, 0.5, 10, 0.5, 1, ""));
  public boolean oddTick;
  public SpeedFeature() {
    this.toggled = false;
    super.name = "speed";
    super.category = Category.MOVEMENT;
  }
  public void tick() {
    oddTick = !oddTick;
    switch (mode.getValue()) {
      case VANILLA:
        if (!mc.player.isOnGround()) return;
        Vec3d vec3d = Vec3d.ZERO;
        double speed = bps.getValue();
        if (mc.options.forwardKey.isPressed())vec3d = vec3d.add(getMovementOffset(0).multiply(speed));
        if (mc.options.rightKey.isPressed())  vec3d = vec3d.add(getMovementOffset(90).multiply(speed));
        if (mc.options.leftKey.isPressed())   vec3d = vec3d.add(getMovementOffset(-90).multiply(speed));
        if (mc.options.backKey.isPressed())   vec3d = vec3d.add(getMovementOffset(180).multiply(speed));
        mc.player.setVelocity(vec3d);
        break;
    }
  }
  private Vec3d getMovementOffset(int yawOffset) {
    double radians = Math.toRadians(mc.player.getYaw() + yawOffset);
    double sin = -Math.sin(radians);
    double cos = Math.cos(radians);
    return new Vec3d(sin, 0, cos);
  }
  public enum Mode {
    VANILLA
  }
}
