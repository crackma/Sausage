package me.frandma.sausage.client;

import lombok.Getter;
import me.frandma.sausage.render.SausageFont;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SausageClient implements ClientModInitializer {
  @Getter
  private static SausageClient instance;
  private TextRenderer textRenderer;
  @Override
  public void onInitializeClient() {
    instance = this;
    getTextRenderer();
  }
  public TextRenderer getTextRenderer() {
    if (textRenderer != null) return textRenderer;
    if (MinecraftClient.getInstance().getResourceManager() != null) {
      try {
        textRenderer = getTr();
        return textRenderer;
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    } else {
      return null;
    }
  }
  private TextRenderer getTr() throws IOException {
    MinecraftClient mc = MinecraftClient.getInstance();
    List<Font.FontFilterPair> list = new ArrayList<>();
    TrueTypeFontLoader loader = new TrueTypeFontLoader(
        SausageFont.VOLTE_SEMIBOLD.getIdentifier(),
        16,
        20,
        TrueTypeFontLoader.Shift.NONE,
        ""
    );
    FontLoader.Loadable loadable = loader.build().orThrow();
    Font font = loadable.load(mc.getResourceManager());
    list.add(new Font.FontFilterPair(font, FontFilterType.FilterMap.NO_FILTER));
    FontStorage storage = new FontStorage(mc.getTextureManager(), SausageFont.VOLTE_SEMIBOLD.getIdentifier());
    storage.setFonts(list, new HashSet<>());
    return new TextRenderer(id -> storage, true);
  }
}
