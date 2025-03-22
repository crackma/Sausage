package me.frandma.sausage.render;

import lombok.Getter;
import net.minecraft.util.Identifier;

public enum SausageFont {
  TAHOMA("tahoma");
  @Getter
  private final Identifier identifier;
  SausageFont(String path) {
    this.identifier = Identifier.of("sausage", path);
  }
}
