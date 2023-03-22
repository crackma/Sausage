package me.frandma.sausage.feature;

public abstract class Feature {

    protected String name;

    protected int keyBinding;

    private boolean toggled;

    public Feature() {
        this.toggled = false;
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

    public void enable() {

    }

    public void disable() {

    }

    public final void toggle() {
        if (toggled){
            enable();
        } else {
            disable();
        }
    }
}
