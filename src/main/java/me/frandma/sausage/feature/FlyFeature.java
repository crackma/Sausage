package me.frandma.sausage.feature;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class FlyFeature extends Feature {

    public FlyFeature() {
        super.name = "Fly";
        super.keyBinding = GLFW.GLFW_KEY_Z;
    }

    @Override
    public void enable() {
        MinecraftClient.getInstance().player.getAbilities().allowFlying = true;
        MinecraftClient.getInstance().player.getAbilities().flying = true;
    }

    @Override
    public void disable() {
        MinecraftClient.getInstance().player.getAbilities().allowFlying = true;
        MinecraftClient.getInstance().player.getAbilities().flying = true;
    }

}
