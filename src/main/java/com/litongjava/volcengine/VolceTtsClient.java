package com.litongjava.volcengine;

import java.io.IOException;
import java.util.UUID;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.base64.Base64Utils;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VolceTtsClient {
  public static final String API_URL = "https://openspeech.bytedance.com/api/v1/tts";

  public static byte[] tts(String input) {
    String appid = EnvUtils.getStr("VOLC_APP_ID");
    String accessToken = EnvUtils.getStr("VOLC_ACCESS_TOKEN");

    return tts(input, appid, accessToken);
  }

  public static byte[] tts(String input, String appid, String accessToken) {
    VolcAudio audio = VolcAudio.builder().encoding("mp3").voice_type("zh_male_beijingxiaoye_moon_bigtts").build();
    VolcRequest request = VolcRequest.builder().reqid(UUID.randomUUID().toString())
        //
        .operation("query").text(input).build();

    VolcTtsRequest ttsRequest = VolcTtsRequest.builder()
        //
        .app(VolcApp.builder().appid(appid).cluster("volcano_tts").build())
        //
        .user(VolcUser.builder().uid("uid").build())
        //
        .audio(audio).request(request).build();

    VolcTtsResponse response = tts(accessToken, ttsRequest);
    Integer code = response.getCode();
    if (code.equals(3000)) {
      return Base64Utils.decodeToBytes(response.getData());
    } else {
      String message = response.getMessage();
      throw new RuntimeException("Failed to tts code:" + code + ",message" + message);
    }

  }

  public static VolcTtsResponse tts(String accessToken, VolcTtsRequest ttsRequest) {
    String reqBody = JsonUtils.toSkipNullJson(ttsRequest);
    ResponseVo responseVo = execute(accessToken, reqBody);
    if (responseVo.isOk()) {
      String bodyString = responseVo.getBodyString();
      return JsonUtils.parse(bodyString, VolcTtsResponse.class);

    } else {
      throw new RuntimeException(responseVo.getBodyString());
    }
  }

  private static ResponseVo execute(String accessToken, String reqBody) {
    RequestBody body = RequestBody.create(reqBody, MediaType.get("application/json; charset=utf-8"));
    Request request = new Request.Builder().url(API_URL).header("Authorization", "Bearer;" + accessToken).post(body).build();

    // Obtain an HTTP client from the pool
    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

    try (Response response = httpClient.newCall(request).execute()) {
      String responseBody = response.body().string();
      int code = response.code();
      if (response.isSuccessful()) {
        // Return success response with the response body
        return ResponseVo.ok(responseBody);
      } else {
        // Return failure response with code and response body
        return ResponseVo.fail(code, responseBody);
      }
    } catch (IOException e) {
      // Wrap and rethrow any IO exceptions
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
