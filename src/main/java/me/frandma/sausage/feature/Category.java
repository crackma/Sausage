package me.frandma.sausage.feature;

import lombok.Getter;

public enum Category {
  COMBAT("combat"),
  HUD("hud"),
  MISC ("misc"),
  MOVEMENT("movement"),
  RENDER("render"),
  WORLD("world"),
  ALTS("alts"),
  CONFIG("config");
  @Getter
  private final String name;
  Category(String name) {
    this.name = name;
  }
}