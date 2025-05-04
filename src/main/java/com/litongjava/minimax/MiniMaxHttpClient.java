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

  /**
   * 传入 FishAudioTTSRequestVo 对象。
   *
   * @param vo 请求对象
   * @return MiniMaxTTSResponse 响应结果
   */
  public static MiniMaxTTSResponse speech(MiniMaxTTSRequest vo) {
    String groupId = EnvUtils.get("MINIMAX_GROUP_ID");
    String apiKey = EnvUtils.get("MINIMAX_API_KEY");
    return speech(groupId, apiKey, vo);
  }

  /**
   * 指定 API Key 调用接口。
   *
   * @param apiKey API密钥
   * @param vo     请求对象
   * @return MiniMaxTTSResponse 响应结果
   */
  public static MiniMaxTTSResponse speech(String groupId, String apiKey, MiniMaxTTSRequest vo) {
    String apiPrefixUrl = EnvUtils.get("FISHAUDIO_API_URL", TTS_URL);
    return speech(apiPrefixUrl, groupId, apiKey, vo);
  }

  /**
   * 完整的接口调用：指定 URL、API Key 和请求对象。
   *
   * @param apiPrefixUrl 接口前缀地址，如 https://api.fish.audio/v1
   * @param apiKey       API 密钥
   * @param vo           请求对象
   * @return MiniMaxTTSResponse   响应结果
   */
  public static MiniMaxTTSResponse speech(String apiPrefixUrl, String groupId, String apiKey, MiniMaxTTSRequest vo) {
    // 使用 msgpack 工具将请求对象序列化成Base64
    String json = JsonUtils.toJson(vo);
    return speechRequest(apiPrefixUrl, groupId, apiKey, json);
  }

  /**
   * 发起 HTTP 请求，返回鱼声平台 TTS 接口响应结果。
   *
   * @param apiPrefixUrl 接口前缀
   * @param apiKey       API 密钥
   * @param payload      msgpack 序列化后的请求数据
   * @return MiniMaxTTSResponse  响应结果，成功时包含音频Base64数据
   */
  public static MiniMaxTTSResponse speechRequest(String apiPrefixUrl, String groupId, String apiKey, String payload) {
    // 接口地址为 “/tts”
    String baseUrl = apiPrefixUrl + "/t2a_v2?GroupId=" + groupId;
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
}
