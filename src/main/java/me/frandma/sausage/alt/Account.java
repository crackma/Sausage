package me.frandma.sausage.alt;

import lombok.Getter;
import net.minecraft.client.session.Session;

public class Account {
  @Getter
  private final Type type;
  @Getter
  private final Session session;
  public Account(Type type, Session session) {
    this.type = type;
    this.session = session;
  }
  public enum Type {
    MICROSOFT,
    OFFLINE
  }
}
