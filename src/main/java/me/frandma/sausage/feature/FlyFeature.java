package me.frandma.sausage.feature;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class FlyFeature extends Feature {

    public FlyFeature() {
        this.toggled = false;
        super.name = "Fly";
        super.keyBinding = GLFW.GLFW_KEY_Z;

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (!this.isToggled()) return;
            Vec3d vec3d = new Vec3d(0, 0, 0);
            double radians = Math.toRadians(mc.player.getYaw());
            if (mc.options.forwardKey.isPressed())vec3d = vec3d.add(sin(0), 0, cos(0));
            if (mc.options.rightKey.isPressed())  vec3d = vec3d.add(sin(90), 0, cos(90));
            if (mc.options.leftKey.isPressed())   vec3d = vec3d.add(sin(-90), 0, cos(-90));
            if (mc.options.backKey.isPressed())   vec3d = vec3d.add(sin(180), 0, cos(180));
            if (mc.options.jumpKey.isPressed())   vec3d = vec3d.add(0, 1, 0);
            if (mc.options.sneakKey.isPressed())  vec3d = vec3d.add(0, -1, 0);
            if (mc.options.sprintKey.isPressed()) vec3d = vec3d.add(0, 0, 1);
            mc.player.setVelocity(vec3d);
        });
    }

    private double sin(int yawOffset) {
        double radians = -Math.toRadians(mc.player.getYaw() + yawOffset);
        return Math.sin(radians);
    }

    private double cos(int yawOffset) {
        double radians = Math.toRadians(mc.player.getYaw() + yawOffset);
        return Math.cos(radians);
    }
}
