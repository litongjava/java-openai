package com.litongjava.minimax;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import okhttp3.sse.EventSource.Factory;

/**
 * FishAudioClient 用于调用 Fish.audio 的 TTS 接口。
 */
public class MiniMaxHttpClient {
  public static final String TTS_URL = "https://api.minimaxi.chat/v1";

  /**
   * 直接传入文本内容构造请求（使用默认参数）。
   *
   * @param text 需要合成的文本
   * @return MiniMaxTTSResponse 响应结果（成功时包含音频Base64数据）
   */
  public static MiniMaxTTSResponse speech(String text, String voice) {
    MiniMaxTTSRequest miniMaxTTSRequest = new MiniMaxTTSRequest(text, voice);
    return speech(miniMaxTTSRequest);
  }

  public static MiniMaxTTSResponse speech(String text, String voice, String language_boost) {
    MiniMaxTTSRequest miniMaxTTSRequest = new MiniMaxTTSRequest(text, voice, language_boost);
    return speech(miniMaxTTSRequest);
  }

  /**
   * 传入 FishAudioTTSRequestVo 对象。
   *
   * @param vo 请求对象
   * @return MiniMaxTTSResponse 响应结果
   */
  public static MiniMaxTTSResponse speech(MiniMaxTTSRequest vo) {
    String apiKey = EnvUtils.get("MINIMAX_API_KEY");
    return speech(apiKey, vo);
  }

  /**
   * 指定 API Key 调用接口。
   *
   * @param apiKey API密钥
   * @param vo     请求对象
   * @return MiniMaxTTSResponse 响应结果
   */
  public static MiniMaxTTSResponse speech(String apiKey, MiniMaxTTSRequest vo) {
    String apiPrefixUrl = EnvUtils.get("MINIMAX_API_URL", TTS_URL);
    return speech(apiPrefixUrl, apiKey, vo);
  }

  /**
   * 完整的接口调用：指定 URL、API Key 和请求对象。
   *
   * @param apiPrefixUrl 接口前缀地址，如 https://api.fish.audio/v1
   * @param apiKey       API 密钥
   * @param vo           请求对象
   * @return MiniMaxTTSResponse 响应结果
   */
  public static MiniMaxTTSResponse speech(String apiPrefixUrl, String apiKey, MiniMaxTTSRequest vo) {
    String json = JsonUtils.toJson(vo);
    return speechRequest(apiPrefixUrl, apiKey, json);
  }

  /**
   * 发起 HTTP 请求，返回平台 TTS 接口响应结果。
   *
   * @param apiPrefixUrl 接口前缀
   * @param apiKey       API 密钥
   * @param payload      msgpack 序列化后的请求数据
   * @return MiniMaxTTSResponse 响应结果，成功时包含音频Base64数据
   */
  public static MiniMaxTTSResponse speechRequest(String apiPrefixUrl, String apiKey, String payload) {
    // 接口地址为 “/tts”
    String baseUrl = apiPrefixUrl + "/t2a_v2";
    Map<String, String> header = new HashMap<>();
    header.put("Authorization", "Bearer " + apiKey);
    return execute(baseUrl, header, payload);
  }

  /**
   * 发送 HTTP 请求并处理响应（流式返回音频数据）。
   *
   * @param url     完整 URL
   * @param header  请求头信息
   * @param payload 请求体
   * @return MiniMaxTTSResponse 响应结果
   */
  private static MiniMaxTTSResponse execute(String url, Map<String, String> header, String payload) {
    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body = RequestBody.create(payload, mediaType);

    // 构建请求并添加请求头
    Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
    for (Map.Entry<String, String> entry : header.entrySet()) {
      requestBuilder.addHeader(entry.getKey(), entry.getValue());
    }
    Request request = requestBuilder.build();

    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

    try (Response response = httpClient.newCall(request).execute()) {
      int code = response.code();
      String responseBody = response.body().string();
      if (response.isSuccessful()) {
        return JsonUtils.parse(responseBody, MiniMaxTTSResponse.class);
      } else {
        // 失败时返回错误码和响应体
        throw new RuntimeException("Failed to tts code:" + code + " body: " + responseBody);
      }
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static EventSource speechStream(String text, String voice, EventSourceListener listener) {
    MiniMaxTTSRequest miniMaxTTSRequest = new MiniMaxTTSRequest(text, voice);
    return speechStream(miniMaxTTSRequest, listener);
  }

  public static EventSource speechStream(String text, String voice, String language_boost,
      EventSourceListener listener) {
    MiniMaxTTSRequest miniMaxTTSRequest = new MiniMaxTTSRequest(text, voice, language_boost);
    return speechStream(miniMaxTTSRequest, listener);
  }

  public static EventSource speechStream(MiniMaxTTSRequest vo, EventSourceListener listener) {
    String apiKey = EnvUtils.get("MINIMAX_API_KEY");
    return speechStream(apiKey, vo, listener);
  }

  public static EventSource speechStream(String apiKey, MiniMaxTTSRequest vo, EventSourceListener listener) {
    String apiPrefixUrl = EnvUtils.get("MINIMAX_API_URL", TTS_URL);
    return speechStream(apiPrefixUrl, apiKey, vo, listener);
  }

  public static EventSource speechStream(String apiPrefixUrl, String apiKey, MiniMaxTTSRequest vo,
      EventSourceListener listener) {
    vo.setStream(true);
    String json = JsonUtils.toJson(vo);
    return speechStreamRequest(apiPrefixUrl, apiKey, json, listener);
  }

  /**
   * 发起 HTTP 请求，返回平台 TTS 接口响应结果。
   *
   * @param apiPrefixUrl 接口前缀
   * @param apiKey       API 密钥
   * @param payload      msgpack 序列化后的请求数据
   * @return EventSource 响应结果，成功时包含音频Base64数据
   */
  public static EventSource speechStreamRequest(String apiPrefixUrl, String apiKey, String payload,
      EventSourceListener listener) {
    // 接口地址为 “/tts”
    String baseUrl = apiPrefixUrl + "/t2a_v2";
    Map<String, String> header = new HashMap<>();
    header.put("Authorization", "Bearer " + apiKey);
    return executeStream(baseUrl, header, payload, listener);
  }

  public static EventSource executeStream(String url, Map<String, String> header, String payload,
      EventSourceListener listener) {
    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body = RequestBody.create(payload, mediaType);

    // 构建请求并添加请求头
    Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
    for (Map.Entry<String, String> entry : header.entrySet()) {
      requestBuilder.addHeader(entry.getKey(), entry.getValue());
    }
    Request request = requestBuilder.build();

    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();
    Factory createFactory = EventSources.createFactory(httpClient);
    EventSource source = createFactory.newEventSource(request, listener);
    return source;
  }
}
