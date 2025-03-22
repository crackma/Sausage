package me.frandma.sausage.client;

import lombok.Getter;
import me.frandma.sausage.render.SausageFont;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.*;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SausageClient implements ClientModInitializer {
  @Getter
  private static SausageClient instance;
  @Override
  public void onInitializeClient() {
    instance = this;
  }
}
