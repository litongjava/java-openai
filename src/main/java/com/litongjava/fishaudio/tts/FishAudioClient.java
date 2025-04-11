package com.litongjava.fishaudio.tts;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * FishAudioClient 用于调用 Fish.audio 的 TTS 接口。
 */
public class FishAudioClient {
  public static final String TTS_URL = "https://api.fish.audio/v1";

  /**
   * 直接传入文本内容构造请求（使用默认参数）。
   *
   * @param text 需要合成的文本
   * @return ResponseVo 响应结果（成功时包含音频二进制数据）
   */
  public static ResponseVo speech(String text) {
    FishAudioTTSRequestVo req = new FishAudioTTSRequestVo().setText(text);
    // 其他参数均采用默认值，如 chunk_length = 200, format = "mp3" 等
    return speech(req);
  }

  /**
   * 传入 FishAudioTTSRequestVo 对象。
   *
   * @param vo 请求对象
   * @return ResponseVo 响应结果
   */
  public static ResponseVo speech(FishAudioTTSRequestVo vo) {
    String apiKey = EnvUtils.get("FISHAUDIO_API_KEY");
    return speech(apiKey, vo);
  }

  /**
   * 指定 API Key 调用接口。
   *
   * @param apiKey API密钥
   * @param vo     请求对象
   * @return ResponseVo 响应结果
   */
  public static ResponseVo speech(String apiKey, FishAudioTTSRequestVo vo) {
    String apiPrefixUrl = EnvUtils.get("FISHAUDIO_API_URL", TTS_URL);
    return speech(apiPrefixUrl, apiKey, vo);
  }

  /**
   * 完整的接口调用：指定 URL、API Key 和请求对象。
   *
   * @param apiPrefixUrl 接口前缀地址，如 https://api.fish.audio/v1
   * @param apiKey       API 密钥
   * @param vo           请求对象
   * @return ResponseVo   响应结果
   */
  public static ResponseVo speech(String apiPrefixUrl, String apiKey, FishAudioTTSRequestVo vo) {
    // 使用 msgpack 工具将请求对象序列化成二进制
    byte[] payload = com.litongjava.fishaudio.tts.FishAudioMsgPackConverter.encodeFishAudioTTSRequestVo(vo);
    return speechRequest(apiPrefixUrl, apiKey, payload);
  }

  /**
   * 发起 HTTP 请求，返回鱼声平台 TTS 接口响应结果。
   *
   * @param apiPrefixUrl 接口前缀
   * @param apiKey       API 密钥
   * @param payload      msgpack 序列化后的请求数据
   * @return ResponseVo  响应结果，成功时包含音频二进制数据
   */
  public static ResponseVo speechRequest(String apiPrefixUrl, String apiKey, byte[] payload) {
    // 接口地址为 “/tts”
    String baseUrl = apiPrefixUrl + "/tts";
    Map<String, String> header = new HashMap<>();
    header.put("Authorization", "Bearer " + apiKey);
    header.put("content-type", "application/msgpack");
    // 指定 TTS 模型版本，默认 "speech-1.6"
    header.put("model", "speech-1.6");
    return execute(baseUrl, header, payload);
  }

  /**
   * 发送 HTTP 请求并处理响应（流式返回音频数据）。
   *
   * @param url     完整 URL
   * @param header  请求头信息
   * @param payload 请求体（msgpack二进制数据）
   * @return ResponseVo 响应结果
   */
  private static ResponseVo execute(String url, Map<String, String> header, byte[] payload) {
    MediaType mediaType = MediaType.parse("application/msgpack");
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
      if (response.isSuccessful()) {
        // 成功时返回音频二进制数据
        return ResponseVo.ok(response.body().bytes());
      } else {
        // 失败时返回错误码和响应体
        String responseBody = response.body().string();
        return ResponseVo.fail(code, responseBody);
      }
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
