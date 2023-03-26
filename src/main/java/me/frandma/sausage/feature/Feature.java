package me.frandma.sausage.feature;

import net.minecraft.client.MinecraftClient;

public abstract class Feature {

    protected MinecraftClient mc = MinecraftClient.getInstance();

    protected String name;

    protected int keyBinding;

    protected boolean toggled;

    public Feature() { }

    public String getName() {
        return name;
    }

    public int getKeyBinding() {
        return keyBinding;
    }

    public boolean isToggled() {
        return toggled;
    }

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
