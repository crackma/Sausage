package me.frandma.sausage.alt;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.ServicesKeyType;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UndashedUuid;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
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
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class MicrosoftAuth {
  private static final String CLIENT_ID = "4673b348-3efa-4f6a-bbb6-34e141cdc638";
  private static final int PORT = 9675;
  private static final Gson GSON = new Gson();
  private static final MinecraftClient mc = MinecraftClient.getInstance();
  private static final MinecraftClientAccessor mca = (MinecraftClientAccessor) MinecraftClient.getInstance();
  private static final Executor EXECUTOR = Executors.newCachedThreadPool();
  private static HttpServer server;
  private static CompletableFuture<Session> loginFuture;
  public static CompletableFuture<Session> login() {
    loginFuture = new CompletableFuture<>();
    try {
      startServer();
      openBrowser("https://login.live.com/oauth20_authorize.srf?client_id=" + CLIENT_ID
          + "&response_type=code&redirect_uri=http://127.0.0.1:" + PORT
          + "&scope=XboxLive.signin%20offline_access&prompt=select_account");
    } catch (Exception e) {
      loginFuture.completeExceptionally(e);
    }
    return loginFuture;
  }
  private static void startServer() throws IOException {
    if (server != null) return;
    server = HttpServer.create(new InetSocketAddress("127.0.0.1", PORT), 0);
    server.createContext("/", new AuthCallback());
    server.setExecutor(EXECUTOR);
    server.start();
  }
  private static void stopServer() {
    if (server == null) return;
    server.stop(0);
    server = null;
  }
  private static void openBrowser(String url) {
    try {
      if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        Desktop.getDesktop().browse(URI.create(url));
      } else {
        Runtime runtime = Runtime.getRuntime();
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
          runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
        } else if (os.contains("mac")) {
          runtime.exec("open " + url);
        } else if (os.contains("nix") || os.contains("nux")) {
          runtime.exec("xdg-open " + url);
        }
      }
    } catch (Exception e) {
      loginFuture.completeExceptionally(new RuntimeException("Failed to open browser: " + e.getMessage()));
    }
  }
  private static class AuthCallback implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
      try {
        String query = exchange.getRequestURI().getQuery();
        String response = "auth complete";
        if (query != null && query.startsWith("code=")) {
          String code = query.substring(5);
          if (query.contains("&")) {
            code = code.substring(0, code.indexOf("&"));
          }
          final String authCode = code;
          CompletableFuture.runAsync(() -> completeAuth(authCode), EXECUTOR);
        } else {
          response = "auth failed";
          loginFuture.completeExceptionally(new RuntimeException("No authorization code received"));
        }
        byte[] responseBytes = response.getBytes();
        exchange.sendResponseHeaders(200, responseBytes.length);
        exchange.getResponseBody().write(responseBytes);
        exchange.getResponseBody().close();
      } catch (Exception e) {
        loginFuture.completeExceptionally(e);
      } finally {
        stopServer();
      }
    }
  }
  private static void completeAuth(String code) {
    try {
      AuthTokenResponse tokenResponse = postFormRequest(
          "https://login.live.com/oauth20_token.srf",
          List.of(
              new BasicNameValuePair("client_id", CLIENT_ID),
              new BasicNameValuePair("code", code),
              new BasicNameValuePair("grant_type", "authorization_code"),
              new BasicNameValuePair("redirect_uri", "http://127.0.0.1:" + PORT)
          ),
          AuthTokenResponse.class
      );
      System.out.println("exchange");
      XblResponse xblResponse = postJsonRequest(
          "https://user.auth.xboxlive.com/user/authenticate",
          "{\"Properties\":{\"AuthMethod\":\"RPS\",\"SiteName\":\"user.auth.xboxlive.com\",\"RpsTicket\":\"d=" +
              tokenResponse.accessToken + "\"},\"RelyingParty\":\"http://auth.xboxlive.com\",\"TokenType\":\"JWT\"}",
          XblResponse.class
      );
      System.out.println("xbl");
      XblResponse xstsResponse = postJsonRequest(
          "https://xsts.auth.xboxlive.com/xsts/authorize",
          "{\"Properties\":{\"SandboxId\":\"RETAIL\",\"UserTokens\":[\"" +
              xblResponse.token + "\"]},\"RelyingParty\":\"rp://api.minecraftservices.com/\",\"TokenType\":\"JWT\"}",
          XblResponse.class
      );
      System.out.println("xsts");
      String uhs = xblResponse.displayClaims.xui[0].uhs;
      MinecraftResponse mcResponse = postJsonRequest(
          "https://api.minecraftservices.com/authentication/login_with_xbox",
          "{\"identityToken\":\"XBL3.0 x=" + uhs + ";" + xstsResponse.token + "\"}",
          MinecraftResponse.class
      );
      System.out.println("xbl");
      GameOwnershipResponse ownershipResponse = getRequest(
          "https://api.minecraftservices.com/entitlements/mcstore",
          mcResponse.accessToken,
          GameOwnershipResponse.class
      );
      if (!ownershipResponse.hasGameOwnership()) {
        loginFuture.completeExceptionally(new RuntimeException("This Microsoft account doesn't own Minecraft"));
        return;
      }
      System.out.println("owner");
      ProfileResponse profileResponse = getRequest(
          "https://api.minecraftservices.com/minecraft/profile",
          mcResponse.accessToken,
          ProfileResponse.class
      );
      System.out.println("profile info");
      Session session = new Session(
          profileResponse.name,
          UndashedUuid.fromStringLenient(profileResponse.id),
          mcResponse.accessToken,
          Optional.empty(),
          Optional.empty(),
          Session.AccountType.MSA
      );
      loginFuture.complete(session);
    } catch (Exception e) {
      loginFuture.completeExceptionally(e);
    }
  }
  public static void setSession(Session session) throws Exception {
    Proxy proxy = mca.getProxy();
    YggdrasilAuthenticationService authService = new YggdrasilAuthenticationService(proxy);
    MinecraftSessionService sessionService = authService.createMinecraftSessionService();
    mca.setAuthenticationService(authService);
    SignatureVerifier.create(authService.getServicesKeySet(), ServicesKeyType.PROFILE_KEY);
    mca.setSessionService(sessionService);
    PlayerSkinProvider.FileCache skinCache = ((PlayerSkinProviderAccessor) mc.getSkinProvider()).getSkinCache();
    Path skinCachePath = ((FileCacheAccessor) skinCache).getDirectory();
    mca.setSkinProvider(new PlayerSkinProvider(skinCachePath, sessionService, mc));
    mca.setSession(session);
    UserApiService apiService = authService.createUserApiService(session.getAccessToken());
    mca.setUserApiService(apiService);
    mca.setSocialInteractionsManager(new SocialInteractionsManager(mc, apiService));
    mca.setProfileKeys(ProfileKeys.create(apiService, session, mc.runDirectory.toPath()));
    mca.setAbuseReportContext(AbuseReportContext.create(ReporterEnvironment.ofIntegratedServer(), apiService));
    mca.setGameProfileFuture(CompletableFuture.supplyAsync(
        () -> mc.getSessionService().fetchProfile(session.getUuidOrNull(), true),
        Util.getIoWorkerExecutor()
    ));
  }
  private static <T> T postFormRequest(String url, List<NameValuePair> params, Class<T> responseType) throws IOException {
    try (CloseableHttpClient client = HttpClients.createDefault()) {
      HttpPost post = new HttpPost(url);
      post.setEntity(new UrlEncodedFormEntity(params));
      try (CloseableHttpResponse response = client.execute(post)) {
        String json = EntityUtils.toString(response.getEntity());
        return GSON.fromJson(json, responseType);
      }
    }
  }
  private static <T> T postJsonRequest(String url, String json, Class<T> responseType) throws IOException {
    try (CloseableHttpClient client = HttpClients.createDefault()) {
      HttpPost post = new HttpPost(url);
      post.setHeader("Content-Type", "application/json");
      post.setEntity(new StringEntity(json));
      try (CloseableHttpResponse response = client.execute(post)) {
        String responseJson = EntityUtils.toString(response.getEntity());
        return GSON.fromJson(responseJson, responseType);
      }
    }
  }
  private static <T> T getRequest(String url, String accessToken, Class<T> responseType) throws IOException {
    try (CloseableHttpClient client = HttpClients.createDefault()) {
      HttpGet get = new HttpGet(url);
      get.setHeader("Authorization", "Bearer " + accessToken);

      try (CloseableHttpResponse response = client.execute(get)) {
        String json = EntityUtils.toString(response.getEntity());
        return GSON.fromJson(json, responseType);
      }
    }
  }
  private static class AuthTokenResponse {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("refresh_token")
    private String refreshToken;
  }
  private static class XblResponse {
    @SerializedName("Token")
    private String token;

    @SerializedName("DisplayClaims")
    private DisplayClaims displayClaims;
    private static class DisplayClaims {
      private Xui[] xui;
      private static class Xui {
        private String uhs;
      }
    }
  }
  private static class MinecraftResponse {
    @SerializedName("access_token")
    private String accessToken;
  }
  private static class GameOwnershipResponse {
    private Item[] items;
    private static class Item {
      private String name;
    }
    private boolean hasGameOwnership() {
      if (items == null || items.length == 0) return false;

      boolean hasProduct = false;
      boolean hasGame = false;

      for (Item item : items) {
        if (item.name.equals("product_minecraft")) hasProduct = true;
        else if (item.name.equals("game_minecraft")) hasGame = true;
      }

      return hasProduct && hasGame;
    }
  }
  private static class ProfileResponse {
    private String id;
    private String name;
  }
}
