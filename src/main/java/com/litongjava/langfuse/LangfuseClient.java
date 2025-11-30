package com.litongjava.langfuse;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.FastJson2Utils;
import com.litongjava.tio.utils.json.JsonUtils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Java 版 Langfuse 客户端
 * - 使用 OkHttp 发起 GET /prompts/{name}
 * - 使用 Basic Auth（publicKey:secretKey）
 * - 带简单内存缓存，10 分钟过期
 */
@Slf4j
public class LangfuseClient {

  private final LangfuseConfig config;

  private static class CacheEntry {
    final PromptPayload value;
    final long loadedAtMillis;

    CacheEntry(PromptPayload value, long loadedAtMillis) {
      this.value = value;
      this.loadedAtMillis = loadedAtMillis;
    }
  }

  private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
  private final long expireMillis = 10 * 60 * 1000L; // 10 分钟

  public LangfuseClient() {
    this(LangfuseConfig.fromEnv());
  }

  public LangfuseClient(LangfuseConfig config) {
    this.config = config;
  }

  public PromptPayload getPrompt(String name) {
    return getPrompt(name, null);
  }

  public PromptPayload getPrompt(String name, String version) {
    String cacheKey = name + "|" + version + "|" + config.getLabel();
    long now = System.currentTimeMillis();

    CacheEntry cached = cache.get(cacheKey);
    if (cached != null && (now - cached.loadedAtMillis) < expireMillis) {
      return cached.value;
    }

    HttpUrl base = HttpUrl.parse(config.getBaseURL() + "/api/public/v2");
    if (base == null) {
      throw new IllegalStateException("Invalid baseURL: " + config.getBaseURL());
    }

    HttpUrl.Builder urlBuilder = base.newBuilder().addPathSegment("prompts").addPathSegment(name);

    if (version != null && !version.isEmpty()) {
      urlBuilder.addQueryParameter("version", version);
    }
    if (config.getLabel() != null && !config.getLabel().isEmpty()) {
      urlBuilder.addQueryParameter("label", config.getLabel());
    }

    HttpUrl url = urlBuilder.build();

    String basicAuth = Credentials.basic(config.getPublicKey(), config.getSecretKey());

    Request request = new Request.Builder().url(url).get().header("Authorization", basicAuth).build();

    OkHttpClient httpClient = OkHttpClientPool.get60HttpClient();
    try (Response response = httpClient.newCall(request).execute()) {
      int code = response.code();
      String bodyString = response.body() != null ? response.body().string() : "";

      if (!response.isSuccessful()) {
        log.error("Langfuse getPrompt failed, status code:{}, body:{}", code, bodyString);
        throw new RuntimeException("Langfuse getPrompt failed, status=" + code + ", body=" + bodyString);
      }

      PromptPayload payload;
      try {
        payload = FastJson2Utils.parse(bodyString, PromptPayload.class);
      } catch (Exception e) {
        log.error("Langfuse response parse error, status code:{}, body:{}", code, bodyString, e);
        throw new RuntimeException("Langfuse parse PromptPayload failed: " + e.getMessage(), e);
      }

      cache.put(cacheKey, new CacheEntry(payload, now));
      return payload;
    } catch (IOException e) {
      throw new RuntimeException("Langfuse getPrompt IO error: " + e.getMessage(), e);
    }
  }
}
