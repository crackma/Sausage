package me.frandma.sausage.feature;

import java.util.ArrayList;
import java.util.List;

public class FeatureManager {

    private static List<Feature> features = new ArrayList<>();

    static {
        features.add(new FlyFeature());
    }

    public static List<Feature> getFeatures() {
        return features;
    }
}
