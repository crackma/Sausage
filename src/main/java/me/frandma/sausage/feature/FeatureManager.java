package me.frandma.sausage.feature;

import me.frandma.sausage.feature.features.FlyFeature;
import me.frandma.sausage.feature.features.NofallFeature;
import me.frandma.sausage.keybinds.KeyBindManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import java.util.ArrayList;
import java.util.List;

public class FeatureManager {

    private static List<Feature> features = new ArrayList<>();

    public static void init() {
        initFeature(new FlyFeature());

        initFeature(new NofallFeature());

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            for(Feature feature : features) {
                feature.tick();
            }
        });
    }

    private static void initFeature(Feature feature) {
        features.add(feature);
        KeyBindManager.initFeature(feature);
    }

    public static List<Feature> getFeatures() {
        return features;
    }
}
