package me.frandma.sausage.feature;

import me.frandma.sausage.keybinds.KeyBindManager;

import java.util.ArrayList;
import java.util.List;

public class FeatureManager {

    private static List<Feature> features = new ArrayList<>();

    public static void init() {
        Feature fly = new FlyFeature();
        features.add(fly);
        KeyBindManager.initFeature(fly);
    }

    public static List<Feature> getFeatures() {
        return features;
    }
}
