package me.frandma.sausage.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.SocialInteractionsManager;
import net.minecraft.client.session.ProfileKeys;
import net.minecraft.client.session.Session;
import net.minecraft.client.session.report.AbuseReportContext;
import net.minecraft.client.texture.PlayerSkinProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.net.Proxy;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {
  @Accessor("networkProxy")
  Proxy getProxy();
  @Mutable
  @Accessor("session")
  void setSession(Session session);
  @Mutable
  @Accessor("authenticationService")
  void setAuthenticationService(YggdrasilAuthenticationService authenticationService);
  @Mutable
  @Accessor("sessionService")
  void setSessionService(MinecraftSessionService minecraftSessionService);
  @Mutable
  @Accessor("userApiService")
  void setUserApiService(UserApiService userApiService);
  @Mutable
  @Accessor("socialInteractionsManager")
  void setSocialInteractionsManager(SocialInteractionsManager socialInteractionsManager);
  @Mutable
  @Accessor("profileKeys")
  void setProfileKeys(ProfileKeys profileKeys);
  @Mutable
  @Accessor("skinProvider")
  void setSkinProvider(PlayerSkinProvider skinProvider);
  @Mutable
  @Accessor("abuseReportContext")
  void setAbuseReportContext(AbuseReportContext abuseReportContext);
  @Mutable
  @Accessor("gameProfileFuture")
  void setGameProfileFuture(CompletableFuture<ProfileResult> gameProfileFuture);
}
