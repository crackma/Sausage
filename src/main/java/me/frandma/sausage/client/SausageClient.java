package me.frandma.sausage.client;

import lombok.Getter;
import net.fabricmc.api.ClientModInitializer;

public class SausageClient implements ClientModInitializer {
  @Getter
  private static SausageClient instance;
  @Override
  public void onInitializeClient() {
    instance = this;
  }
}
