package me.frandma.sausage.render;

import lombok.Getter;
import net.minecraft.util.Identifier;

public enum SausageFont {
  TAHOMA( "tahoma"),
  VOLTE_REGULAR( "volte-regular"),
  VOLTE_MEDIUM("volte-medium"),
  VOLTE_SEMIBOLD("volte-semibold"),
  VOLTE_BOLD( "volte-bold");
  @Getter
  private final Identifier identifier;
  SausageFont(String path) {
    this.identifier = Identifier.of("sausage", path);
  }
}
