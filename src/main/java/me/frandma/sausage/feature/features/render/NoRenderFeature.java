package me.frandma.sausage.feature.features.render;

import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.RenderFogEvent;
import me.frandma.sausage.event.events.RenderPostEffectEvent;
import me.frandma.sausage.event.events.RenderVignetteEvent;
import me.frandma.sausage.feature.Category;
import me.frandma.sausage.feature.Feature;
import me.frandma.sausage.feature.Setting;
import me.frandma.sausage.feature.setting.BooleanSetting;
import net.minecraft.client.render.Fog;

public class NoRenderFeature extends Feature {
  private final Setting<Boolean> fog = settings.addSetting(new BooleanSetting(this, "fog", true));
  private final Setting<Boolean> postEffect = settings.addSetting(new BooleanSetting(this, "post effects", true));
  private final Setting<Boolean> vignette = settings.addSetting(new BooleanSetting(this, "vignette", true));
  public NoRenderFeature() {
    this.toggled = true;
    super.name = "no render";
    super.category = Category.RENDER;
    EventManager.register(RenderFogEvent.class, renderFogEvent -> {
      if (!toggled) return;
      if (fog.getValue()) renderFogEvent.setReturnValue(Fog.DUMMY);
    });
    EventManager.register(RenderPostEffectEvent.class, renderPostEffectEvent -> {
      if (!toggled) return;
      if (postEffect.getValue()) renderPostEffectEvent.cancel();
    });
    EventManager.register(RenderVignetteEvent.class, renderVignetteEvent -> {
      if (!toggled) return;
      if (vignette.getValue()) renderVignetteEvent.cancel();
    });
  }
}
