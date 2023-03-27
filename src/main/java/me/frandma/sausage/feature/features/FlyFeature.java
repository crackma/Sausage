package me.frandma.sausage.feature.features;

import me.frandma.sausage.feature.Feature;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

public class FlyFeature extends Feature {

    public FlyFeature() {
        this.toggled = false;
        super.name = "Fly";
        super.keyBinding = GLFW.GLFW_KEY_Z;
    }

    protected void tick() {
        if (!this.isToggled()) return;

        //check if it should give extra speed for up and down if you are sprinting
        int speed = 1;
        if (mc.options.sprintKey.isPressed()) speed++;

        Vec3d vec3d = new Vec3d(0, 0, 0);
        if (mc.options.forwardKey.isPressed())vec3d = vec3d.add(sin(0), 0, cos(0));
        if (mc.options.rightKey.isPressed())  vec3d = vec3d.add(sin(90), 0, cos(90));
        if (mc.options.leftKey.isPressed())   vec3d = vec3d.add(sin(-90), 0, cos(-90));
        if (mc.options.backKey.isPressed())   vec3d = vec3d.add(sin(180), 0, cos(180));
        if (mc.options.jumpKey.isPressed())   vec3d = vec3d.add(0, speed, 0);
        if (mc.options.sneakKey.isPressed())  vec3d = vec3d.add(0, -speed, 0);
        mc.player.setVelocity(vec3d);
    }

    private double sin(int yawOffset) {
        int extraSpeed = 1;
        if (mc.options.sprintKey.isPressed()) extraSpeed++;

        double radians = -Math.toRadians(mc.player.getYaw() + yawOffset);
        return Math.sin(radians) * extraSpeed;
    }

    private double cos(int yawOffset) {
        int extraSpeed = 1;
        if (mc.options.sprintKey.isPressed()) extraSpeed++;

        double radians = Math.toRadians(mc.player.getYaw() + yawOffset);
        return Math.cos(radians) * extraSpeed;
    }
}
