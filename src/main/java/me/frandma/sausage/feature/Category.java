package me.frandma.sausage.feature;

public enum Category {
  COMBAT("Combat"),
  MOVEMENT("Movement"),
  RENDER("Render"),
  WORLD("World"),
  HUD("HUD"),
  MISC("Misc");
  private final String name;
  Category(String name) {
    this.name = name;
  }
}