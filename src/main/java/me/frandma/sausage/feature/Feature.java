package me.frandma.sausage.feature;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public abstract class Feature {

    protected MinecraftClient mc = MinecraftClient.getInstance();

    protected String name;

    protected int keyBinding;

    protected boolean toggled;

    public Feature() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            tick();
        });
    }

    public String getName() {
        return name;
    }

    public int getKeyBinding() {
        return keyBinding;
    }

    public boolean isToggled() {
        return toggled;
    }

    protected void tick() { }

    public void enable() { }

    public void disable() { }

    public final void toggle() {
        if (toggled){
            disable();
            toggled = false;
        } else {
            enable();
            toggled = true;
        }
    }
}
