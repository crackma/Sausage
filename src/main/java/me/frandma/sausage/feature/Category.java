package me.frandma.sausage.feature;

import lombok.Getter;

public enum Category {
  COMBAT("combat"),
  MOVEMENT("movement"),
  RENDER("render"),
  WORLD("world"),
  HUD("hud"),
  PLAYER  ("player"),
  ALTS("alts"),
  CONFIG("config");
  @Getter
  private final String name;
  Category(String name) {
    this.name = name;
  }
}