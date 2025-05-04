package me.frandma.sausage;

import lombok.Getter;
import me.frandma.sausage.alt.AccountManager;
import me.frandma.sausage.feature.FeatureManager;
import me.frandma.sausage.render.old.ClickGUI;
import me.frandma.sausage.render.MenuManager;
import me.frandma.sausage.render.impl.onetap.OTMenuPanel;
import net.fabricmc.api.ModInitializer;

@Getter
public class Sausage implements ModInitializer {
  @Getter
  private static Sausage instance;
  private AccountManager accountManager;
  private ClickGUI clickGUI;
  private MenuManager menuManager;
  private FeatureManager featureManager;
  @Override
  public void onInitialize() {
    instance = this;
    this.accountManager = new AccountManager();
    featureManager = new FeatureManager();
    menuManager = new MenuManager(new OTMenuPanel(this));
    //clickGUI = new ClickGUI(this);
  }
}
