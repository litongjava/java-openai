package com.litongjava.gemini;

import java.io.IOException;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.JsonUtils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Google Gemini 模型客户端示例
 */
@Slf4j
public class GeminiClient {

  // 接口前缀不变
  private static final String BASE_URL = EnvUtils.get("GEMINI_API_URL", "https://generativelanguage.googleapis.com/v1beta/models/");
  public static final OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

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
    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

    // 拼接 URL
    String url = BASE_URL + modelName + ":generateContent?key=" + googleApiKey;
    // 将 requestVo 转换为 JSON
    String requestJson = JsonUtils.toJson(requestVo);
    log.debug("Gemini generateContent request json: {}", requestJson);

    // 构造 HTTP 请求
    RequestBody body = RequestBody.create(requestJson, MediaType.parse("application/json"));
    Request request = new Request.Builder().url(url).post(body).build();

    // 发起调用
    try (Response response = httpClient.newCall(request).execute()) {
      String responseBody = response.body().string();
      log.debug("Gemini generateContent response json: {}", responseBody);

      if (!response.isSuccessful()) {
        throw new RuntimeException("Gemini generateContent failed, statusCode=" + response.code() + ", body=" + responseBody);
      }
      // 解析 JSON
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
    String url = BASE_URL + modelName + ":generateContent?key=" + googleApiKey;

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
    String requestJson = JsonUtils.toJson(requestVo);
    return stream(googleApiKey, modelName, requestJson, callback);

  }

  public static Call stream(String modelName, GeminiChatRequestVo requestVo, Callback callback) {
    String apiKey = EnvUtils.getStr("GEMINI_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      new RuntimeException("GEMINI_API_KEY is empty");
    }
    return stream(apiKey, modelName, requestVo, callback);
  }

  public static Call stream(String modelName, String bodyString, Callback callback) {
    String apiKey = EnvUtils.getStr("GEMINI_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      new RuntimeException("GEMINI_API_KEY is empty");
    }
    return stream(apiKey, modelName, bodyString, callback);
  }

  public static Call stream(String googleApiKey, String modelName, String bodyString, Callback callback) {
    // 拼接 URL
    String url = BASE_URL + modelName + ":streamGenerateContent?alt=sse&key=" + googleApiKey;

    // 构造 HTTP 请求
    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));
    Request request = new Request.Builder().url(url).post(body).build();

    // 异步请求
    Call call = httpClient.newCall(request);
    call.enqueue(callback);
    return call;
  }
}
