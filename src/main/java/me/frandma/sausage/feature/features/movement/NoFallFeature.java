package me.frandma.sausage.feature.features.movement;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.PacketSendEvent;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.setting.EnumSetting;
import me.frandma.sausage.feature.setting.NumberSetting;
import me.frandma.sausage.feature.Setting;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class NoFallFeature extends Feature {
  private final Setting<Mode> mode = settings.addSetting(new EnumSetting<>(this, "mode", Mode.ON_GROUND));
  private final Setting<Double> requiredVelocity = settings.addSetting(new NumberSetting(this, "required velocity", 0.6D, 0.1D, 10D, 0.1, 1, ""));
  private boolean modifyNextPacket = false;
  public NoFallFeature() {
    super.name = "nofall";
    super.category = Category.MOVEMENT;
    EventManager.register(PacketSendEvent.class, packetSendEvent -> {
      if (!toggled || !modifyNextPacket || !(packetSendEvent.getOriginalPacket() instanceof PlayerMoveC2SPacket packet)) return;
      packetSendEvent.setPacket(new PlayerMoveC2SPacket.Full( packet.x, packet.y, packet.z, packet.yaw, packet.pitch, true, packet.horizontalCollision()));
      modifyNextPacket = false;
    });
  }
  protected void tick() {
    if (mc.player.getVelocity().y > -requiredVelocity.getValue()) return;
    Vec3d velocity = mc.player.getVelocity();
    switch (mode.getValue()) {
      case ON_GROUND:
        modifyNextPacket = true;
        break;
      case GLIDE:
        modifyNextPacket = true;
        mc.player.setVelocity(velocity.x, velocity.y / 3, mc.player.getVelocity().z);
        break;
      case TEST:
        //mc.player.velocity
    }
  }
  private enum Mode {
    ON_GROUND,
    GLIDE,
    TEST
  }
}
