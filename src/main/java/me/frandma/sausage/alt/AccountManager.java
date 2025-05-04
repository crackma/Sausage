package me.frandma.sausage.alt;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.ServicesKeyType;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import lombok.Getter;
import me.frandma.sausage.event.EventManager;
import me.frandma.sausage.event.events.RenderEvent;
import me.frandma.sausage.mixin.FileCacheAccessor;
import me.frandma.sausage.mixin.MinecraftClientAccessor;
import me.frandma.sausage.mixin.PlayerSkinProviderAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.SocialInteractionsManager;
import net.minecraft.client.session.ProfileKeys;
import net.minecraft.client.session.Session;
import net.minecraft.client.session.report.AbuseReportContext;
import net.minecraft.client.session.report.ReporterEnvironment;
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.network.encryption.SignatureVerifier;
import net.minecraft.util.Util;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.ColorHelper;

import java.net.Proxy;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class AccountManager {
  private final MinecraftClient mc = MinecraftClient.getInstance();
  private final MinecraftClientAccessor mca = (MinecraftClientAccessor) mc;
  @Getter
  private final Account originalAccount;
  @Getter
  private final List<Account> accounts = new ArrayList<>();
  public AccountManager() {
    Session session = mc.getSession();
    originalAccount = session.getAccessToken().equals("") ? new Account(Account.Type.OFFLINE, session) : new Account(Account.Type.MICROSOFT, session);
  }
  public CompletableFuture<Account> addAccount() {
    CompletableFuture<Account> completableFuture = new CompletableFuture<>();
    MicrosoftAuth.login().thenAccept(session -> {
      Account account = new Account(Account.Type.MICROSOFT, session);
      accounts.add(account);
      completableFuture.complete(account);
    });
    return completableFuture;
  }
  public Account addAccount(String username) {
    Account account = new Account(Account.Type.OFFLINE, new Session(username, Uuids.getOfflinePlayerUuid(username), "", Optional.empty(), Optional.empty(), Session.AccountType.MOJANG));
    accounts.add(account);
    return account;
  }
  public String login(Account account) {
    try {
      MicrosoftAuth.setSession(account.getSession());
      return "Successfully logged in.";
    } catch (Exception exception) {
      exception.printStackTrace();
      return "An exception occurred while logging in.";
    }
  }
}
