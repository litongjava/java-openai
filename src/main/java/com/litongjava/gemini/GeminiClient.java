package com.litongjava.gemini;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.litongjava.consts.ModelPlatformName;
import com.litongjava.exception.GenerateException;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.hutool.StrUtil;
import com.litongjava.tio.utils.json.Json;
import com.litongjava.tio.utils.json.JsonUtils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

/**
 * Google Gemini 模型客户端示例
 */
@Slf4j
public class GeminiClient {

  public static boolean debug;
  public static final OkHttpClient httpClient = OkHttpClientPool.get1000HttpClient();
  public static final String GEMINI_API_URL = EnvUtils.get("GEMINI_API_URL", GeminiConsts.GEMINI_API_MODEL_BASE);
  public static final String GEMINI_API_KEY = EnvUtils.get("GEMINI_API_KEY");

  /**
   * 单次生成内容 (同步请求)
   * POST https://generativelanguage.googleapis.com/v1beta/models/{model_name}:generateContent?key={GOOGLE_API_KEY}
   *
   * @param googleApiKey - 你的 Google API Key
   * @param modelName    - 要使用的模型名称，如 "gemini-1.5-flash"
   * @param requestVo    - 请求体实体
   * @return GeminiResponseVo - 响应实体
   */
  public static GeminiChatResponseVo generate(String googleApiKey, String modelName, GeminiChatRequestVo requestVo) {
    return generate(GEMINI_API_URL, googleApiKey, modelName, requestVo);
  }

  public static GeminiChatResponseVo generate(String baseUrl, String googleApiKey, String modelName, GeminiChatRequestVo requestVo) {
    if(StrUtil.isBlank(googleApiKey)) {
      throw new RuntimeException("api key can not be empty");
    }
    // 拼接 URL
    String urlPerfix = baseUrl + modelName + ":generateContent?key=";
    String url = urlPerfix + googleApiKey;
    // 将 requestVo 转换为 JSON
    String requestJson = Json.getSkipNullJson().toJson(requestVo);
    if (debug) {
      System.out.println(requestJson);
    }

    // 构造 HTTP 请求
    RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
    Request request = new Request.Builder().url(url).post(body).build();

    // 发起调用
    try (Response response = httpClient.newCall(request).execute()) {
      String responseBody = response.body().string();

      if (!response.isSuccessful()) {
        if (requestJson.length() > 1024) {
          requestJson = requestJson.substring(0, 1024);
        }
        throw new GenerateException(ModelPlatformName.GOOGLE, "Gemini generateContent failed", urlPerfix, requestJson, response.code(), responseBody);
      }
      return JsonUtils.parse(responseBody, GeminiChatResponseVo.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static GeminiChatResponseVo generate(String modelName, GeminiChatRequestVo requestVo) {
    String key = EnvUtils.getStr("GEMINI_API_KEY");
    if (key == null || key.isEmpty()) {
      new RuntimeException("GEMINI_API_KEY is empty");
    }
    return generate(key, modelName, requestVo);
  }

  public static Response generate(String modelName, String bodyString) {
    String apiKey = EnvUtils.getStr("GEMINI_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      new RuntimeException("GEMINI_API_KEY is empty");
    }
    return generate(apiKey, modelName, bodyString);
  }

  public static Response generate(String googleApiKey, String modelName, String bodyString) {
    // 拼接 URL
    String url = GEMINI_API_URL + modelName + ":generateContent?key=" + googleApiKey;

    // 构造 HTTP 请求
    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));
    Request request = new Request.Builder().url(url).post(body).build();

    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();
    // 发起调用
    try {
      return httpClient.newCall(request).execute();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * @param model
   * @param role
   * @param prompt
   * @return
   */
  public static String chatWithModel(String model, String role, String prompt) {
    String apiKey = EnvUtils.getStr("GEMINI_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      new RuntimeException("GEMINI_API_KEY is empty");
    }
    return chatWithModel(apiKey, model, role, prompt);
  }

  /**
   * 
   * @param apiKey
   * @param model
   * @param role
   * @param prompt
   * @return
   */
  public static String chatWithModel(String apiKey, String model, String role, String prompt) {
    // 1. 构造请求体
    GeminiPartVo part = new GeminiPartVo(prompt);
    GeminiContentVo content = new GeminiContentVo(role, Collections.singletonList(part));
    GeminiChatRequestVo reqVo = new GeminiChatRequestVo(Collections.singletonList(content));
    // 2.发送请求
    GeminiChatResponseVo chatResponse = GeminiClient.generate(apiKey, model, reqVo);
    // 3.返回结果
    return chatResponse.getCandidates().get(0).getContent().getParts().get(0).getText();
  }

  /**
   * 流式生成内容 (SSE，异步请求)
   * POST https://generativelanguage.googleapis.com/v1beta/models/{model_name}:streamGenerateContent?alt=sse&key={GOOGLE_API_KEY}
   * 注：请求体还是 JSON，但响应会通过 SSE 分段返回.
   *
   * @param googleApiKey - 你的 Google API Key
   * @param modelName    - 要使用的模型名称
   * @param requestVo    - 请求体
   * @param callback     - 用于处理 SSE 流式回调
   * @return OkHttp Call 对象，可根据需求执行取消等操作
   */
  public static Call stream(String googleApiKey, String modelName, GeminiChatRequestVo requestVo, Callback callback) {
    String requestJson = Json.getSkipNullJson().toJson(requestVo);
    return stream(googleApiKey, modelName, requestJson, callback);

  }

  public static EventSource stream(String googleApiKey, String modelName, GeminiChatRequestVo requestVo, EventSourceListener listener) {
    String requestJson = Json.getSkipNullJson().toJson(requestVo);
    return stream(googleApiKey, modelName, requestJson, listener);
  }

  public static EventSource stream(String apiPrefixUrl, String googleApiKey, String modelName, GeminiChatRequestVo requestVo, EventSourceListener listener) {
    String requestJson = Json.getSkipNullJson().toJson(requestVo);
    return stream(apiPrefixUrl, googleApiKey, modelName, requestJson, listener);
  }

  public static Call stream(String modelName, GeminiChatRequestVo requestVo, Callback callback) {
    String apiKey = EnvUtils.getStr("GEMINI_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      new RuntimeException("GEMINI_API_KEY is empty");
    }
    return stream(apiKey, modelName, requestVo, callback);
  }

  public static EventSource stream(String modelName, GeminiChatRequestVo requestVo, EventSourceListener listener) {
    String apiKey = EnvUtils.getStr("GEMINI_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      new RuntimeException("GEMINI_API_KEY is empty");
    }
    return stream(apiKey, modelName, requestVo, listener);
  }

  public static Call stream(String modelName, String bodyString, Callback callback) {
    String apiKey = EnvUtils.getStr("GEMINI_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      new RuntimeException("GEMINI_API_KEY is empty");
    }
    return stream(apiKey, modelName, bodyString, callback);
  }

  public static EventSource stream(String modelName, String bodyString, EventSourceListener listener) {
    String apiKey = EnvUtils.getStr("GEMINI_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      new RuntimeException("GEMINI_API_KEY is empty");
    }
    return stream(apiKey, modelName, bodyString, listener);
  }

  public static Call stream(String googleApiKey, String modelName, String bodyString, Callback callback) {
    // 拼接 URL
    String url = GEMINI_API_URL + modelName + ":streamGenerateContent?alt=sse&key=" + googleApiKey;
    if (debug) {
      log.info("{} {}", url, bodyString);
    }
    // 构造 HTTP 请求
    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));
    Request request = new Request.Builder().url(url).post(body).build();

    // 异步请求
    Call call = httpClient.newCall(request);
    call.enqueue(callback);
    return call;
  }

  public static EventSource stream(String googleApiKey, String modelName, String bodyString, EventSourceListener listener) {
    // 拼接 URL
    return stream(GEMINI_API_URL, googleApiKey, modelName, bodyString, listener);
  }

  public static EventSource stream(String apiPrefixUrl, String googleApiKey, String modelName, String bodyString, EventSourceListener listener) {
    // 拼接 URL
    String url = apiPrefixUrl + modelName + ":streamGenerateContent?alt=sse&key=" + googleApiKey;
    if (debug) {
      log.info("{} {}", url, bodyString);
    }
    // 构造 HTTP 请求
    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));
    Request request = new Request.Builder().url(url).post(body).build();

    // 异步请求
    EventSource source = EventSources.createFactory(httpClient).newEventSource(request, listener);
    return source;
  }

  /**
   * 上传文件到 Google Gemini API
   * @param googleApiKey - 你的 Google API Key
   */
  public static String uploadFile(String googleApiKey, byte[] bytes) {

    String url = GeminiConsts.GEMINI_API_SERVER + "/upload/v1beta/files?key=" + googleApiKey;

    RequestBody requestBody = RequestBody.create(bytes);

    // 定义自定义 RequestBody，先写入 JSON，然后写入文件的二进制数据
    // 构建请求
    Request request = new Request.Builder().url(url).post(requestBody).addHeader("x-goog-api-key", "googleApiKey")
        //
        .build();

    // 发送请求并处理响应
    try (Response response = httpClient.newCall(request).execute()) {
      String responseBody = response.body().string();
      if (!response.isSuccessful()) {
        throw new RuntimeException("File upload failed, statusCode=" + response.code() + ", body=" + responseBody);
      }

      return responseBody;
    } catch (IOException e) {
      throw new RuntimeException("File upload failed: " + e.getMessage(), e);
    }
  }

  public static String parseYoutubeSubtitle(String model, String url, String userPrompt) {
    GeminiFileDataVo geminiFileDataVo = new GeminiFileDataVo("video/*", url);
    List<GeminiPartVo> parts = new ArrayList<>();
    parts.add(new GeminiPartVo(userPrompt));
    parts.add(new GeminiPartVo(geminiFileDataVo));
    List<GeminiContentVo> contents = new ArrayList<>();
    GeminiContentVo content = new GeminiContentVo("user", parts);
    contents.add(content);

    GeminiChatRequestVo geminiChatRequestVo = new GeminiChatRequestVo();
    geminiChatRequestVo.setContents(contents);

    GeminiChatResponseVo reponseVo = GeminiClient.generate(model, geminiChatRequestVo);
    String text = reponseVo.getCandidates().get(0).getContent().getParts().get(0).getText();
    return text;
  }

  /**
   * 重载方法，从环境变量中获取 API Key
   *
   * @param displayName - 文件的显示名称
   * @param file        - 要上传的文件
   * @param mimeType    - 文件的 MIME 类型
   * @return String - 上传后文件的 URI
   */
  public static FileUploadResponseVo uploadFile(byte[] bytes) {
    String apiKey = EnvUtils.getStr("GEMINI_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      throw new RuntimeException("GEMINI_API_KEY is empty");
    }
    String responseBody = uploadFile(apiKey, bytes);
    // 解析响应 JSON
    return JsonUtils.parse(responseBody, FileUploadResponseVo.class);
  }

  /**
   * Creates a new cached content entry.
   * POST https://generativelanguage.googleapis.com/v1beta/cachedContents?key={GOOGLE_API_KEY}
   *
   * @param googleApiKey - Your Google API Key
   * @param requestVo    - The request body for creating the cache
   * @return GeminiCacheVo - The created cache metadata
   */
  public static GeminiCacheVo createCache(String googleApiKey, GeminiCreateCacheRequestVo requestVo) {
    String url = GeminiConsts.GEMINI_API_BASE + "cachedContents?key=" + googleApiKey;
    String requestJson = Json.getSkipNullJson().toJson(requestVo);

    RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
    Request request = new Request.Builder().url(url).post(body).build();

    try (Response response = httpClient.newCall(request).execute()) {
      String responseBody = response.body().string();
      if (!response.isSuccessful()) {
        String truncatedRequestJson = requestJson.length() > 1024 ? requestJson.substring(0, 1024) + "..." : requestJson;
        throw new RuntimeException("Gemini createCache failed, request url=" + url + " request body=" + truncatedRequestJson + " statusCode=" + response.code() + ", body=" + responseBody);
      }
      return JsonUtils.parse(responseBody, GeminiCacheVo.class);
    } catch (IOException e) {
      throw new RuntimeException("Gemini createCache failed: " + e.getMessage(), e);
    }
  }

  /**
  * Creates a new cached content entry using API key from environment.
  *
  * @param requestVo - The request body for creating the cache
  * @return GeminiCacheVo - The created cache metadata
  */
  public static GeminiCacheVo createCache(GeminiCreateCacheRequestVo requestVo) {
    String apiKey = EnvUtils.getStr("GEMINI_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      throw new RuntimeException("GEMINI_API_KEY is empty");
    }
    return createCache(apiKey, requestVo);
  }

  /**
   * Gets metadata for a specific cached content entry.
   * GET https://generativelanguage.googleapis.com/v1beta/{cache_name}?key={GOOGLE_API_KEY}
   *
   * @param googleApiKey - Your Google API Key
   * @param cacheName    - The full name of the cache (e.g., "cachedContents/12345")
   * @return GeminiCacheVo - The cache metadata
   */
  public static GeminiCacheVo getCache(String googleApiKey, String cacheName) {
    String url = GeminiConsts.GEMINI_API_BASE + cacheName + "?key=" + googleApiKey;

    Request request = new Request.Builder().url(url).get().build(); // Use GET method

    try (Response response = httpClient.newCall(request).execute()) {
      String responseBody = response.body().string();
      if (!response.isSuccessful()) {
        throw new RuntimeException("Gemini getCache failed, request url=" + url + " statusCode=" + response.code() + ", body=" + responseBody);
      }
      return JsonUtils.parse(responseBody, GeminiCacheVo.class);
    } catch (IOException e) {
      throw new RuntimeException("Gemini getCache failed: " + e.getMessage(), e);
    }
  }

  /**
  * Gets metadata for a specific cached content entry using API key from environment.
  *
  * @param cacheName    - The full name of the cache (e.g., "cachedContents/12345")
  * @return GeminiCacheVo - The cache metadata
  */
  public static GeminiCacheVo getCache(String cacheName) {
    String apiKey = EnvUtils.getStr("GEMINI_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      throw new RuntimeException("GEMINI_API_KEY is empty");
    }
    return getCache(apiKey, cacheName);
  }

  /**
   * Lists metadata for all cached content entries.
   * GET https://generativelanguage.googleapis.com/v1beta/cachedContents?key={GOOGLE_API_KEY}
   *
   * @param googleApiKey - Your Google API Key
   * @return GeminiListCachesResponseVo - A list of cache metadata
   */
  public static GeminiListCachesResponseVo listCaches(String googleApiKey) {
    String url = GeminiConsts.GEMINI_API_BASE + "cachedContents?key=" + googleApiKey;

    Request request = new Request.Builder().url(url).get().build();

    try (Response response = httpClient.newCall(request).execute()) {
      String responseBody = response.body().string();
      if (!response.isSuccessful()) {
        throw new RuntimeException("Gemini listCaches failed, request url=" + url + " statusCode=" + response.code() + ", body=" + responseBody);
      }
      return JsonUtils.parse(responseBody, GeminiListCachesResponseVo.class);
    } catch (IOException e) {
      throw new RuntimeException("Gemini listCaches failed: " + e.getMessage(), e);
    }
  }

  /**
  * Lists metadata for all cached content entries using API key from environment.
  *
  * @return GeminiListCachesResponseVo - A list of cache metadata
  */
  public static GeminiListCachesResponseVo listCaches() {
    String apiKey = EnvUtils.getStr("GEMINI_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      throw new RuntimeException("GEMINI_API_KEY is empty");
    }
    return listCaches(apiKey);
  }

  /**
   * Updates a cached content entry (only ttl or expireTime can be updated).
   * PATCH https://generativelanguage.googleapis.com/v1beta/{cache_name}?key={GOOGLE_API_KEY}
   *
   * @param googleApiKey - Your Google API Key
   * @param cacheName    - The full name of the cache (e.g., "cachedContents/12345")
   * @param updateRequestVo - The request body containing the fields to update (ttl or expireTime)
   * @return GeminiCacheVo - The updated cache metadata
   */
  public static GeminiCacheVo updateCache(String googleApiKey, String cacheName, GeminiUpdateCacheRequestVo updateRequestVo) {
    String url = GeminiConsts.GEMINI_API_BASE + cacheName + "?key=" + googleApiKey;
    String requestJson = Json.getSkipNullJson().toJson(updateRequestVo);

    RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
    Request request = new Request.Builder().url(url).patch(body).build(); // Use PATCH method

    try (Response response = httpClient.newCall(request).execute()) {
      String responseBody = response.body().string();
      if (!response.isSuccessful()) {
        String truncatedRequestJson = requestJson.length() > 1024 ? requestJson.substring(0, 1024) + "..." : requestJson;
        throw new RuntimeException("Gemini updateCache failed, request url=" + url + " request body=" + truncatedRequestJson + " statusCode=" + response.code() + ", body=" + responseBody);
      }
      return JsonUtils.parse(responseBody, GeminiCacheVo.class);
    } catch (IOException e) {
      throw new RuntimeException("Gemini updateCache failed: " + e.getMessage(), e);
    }
  }

  /**
  * Updates a cached content entry using API key from environment.
  *
  * @param cacheName    - The full name of the cache (e.g., "cachedContents/12345")
  * @param updateRequestVo - The request body containing the fields to update (ttl or expireTime)
  * @return GeminiCacheVo - The updated cache metadata
  */
  public static GeminiCacheVo updateCache(String cacheName, GeminiUpdateCacheRequestVo updateRequestVo) {
    String apiKey = EnvUtils.getStr("GEMINI_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      throw new RuntimeException("GEMINI_API_KEY is empty");
    }
    return updateCache(apiKey, cacheName, updateRequestVo);
  }

  /**
   * Deletes a cached content entry.
   * DELETE https://generativelanguage.googleapis.com/v1beta/{cache_name}?key={GOOGLE_API_KEY}
   *
   * @param googleApiKey - Your Google API Key
   * @param cacheName    - The full name of the cache (e.g., "cachedContents/12345")
   */
  public static void deleteCache(String googleApiKey, String cacheName) {
    String url = GeminiConsts.GEMINI_API_BASE + cacheName + "?key=" + googleApiKey;

    Request request = new Request.Builder().url(url).delete().build(); // Use DELETE method

    try (Response response = httpClient.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        String responseBody = response.body() != null ? response.body().string() : "N/A";
        throw new RuntimeException("Gemini deleteCache failed, request url=" + url + " statusCode=" + response.code() + ", body=" + responseBody);
      }
      // Successful delete usually returns 200 OK with an empty body or a simple status.
      // No specific JSON parsing needed based on the curl example.
    } catch (IOException e) {
      throw new RuntimeException("Gemini deleteCache failed: " + e.getMessage(), e);
    }
  }

  /**
  * Deletes a cached content entry using API key from environment.
  *
  * @param cacheName    - The full name of the cache (e.g., "cachedContents/12345")
  */
  public static void deleteCache(String cacheName) {
    String apiKey = EnvUtils.getStr("GEMINI_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      throw new RuntimeException("GEMINI_API_KEY is empty");
    }
    deleteCache(apiKey, cacheName);
  }
}
