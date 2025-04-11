package com.litongjava.openai.tts;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.openai.constants.OpenAiConstants;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OpenAiTTSClient {

  public static ResponseVo speech(String input) {
    OpenAiTTSRequestVo req = new OpenAiTTSRequestVo().setInput(input).setVoice(OpenAiTTSVoice.shimmer)
        //
        .setModel(OpenAiTTSModels.TTS_1);
    return speech(req);
  }

  public static ResponseVo speech(OpenAiTTSRequestVo vo) {
    // Get API key from environment variables
    String apiKey = EnvUtils.get("OPENAI_API_KEY");
    return speech(apiKey, vo);
  }

  public static ResponseVo speech(String apiKey, OpenAiTTSRequestVo vo) {
    String apiPrefixUrl = EnvUtils.get("OPENAI_API_URL", OpenAiConstants.API_PERFIX_URL);
    return speech(apiPrefixUrl, apiKey, vo);
  }

  public static ResponseVo speech(String apiPrefixUrl, String apiKey, OpenAiTTSRequestVo vo) {
    String json = JsonUtils.toSkipNullJson(vo);
    return speechRequest(apiPrefixUrl, apiKey, json);
  }

  public static ResponseVo speechRequest(String apiPrefixUrl, String apiKey, String payload) {
    String baseUrl = apiPrefixUrl + "/audio/speech";
    Map<String, String> header = new HashMap<>();
    header.put("Authorization", "Bearer " + apiKey);
    return execute(baseUrl, header, payload);
  }

  private static ResponseVo execute(String url, Map<String, String> header, String payload) {

    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body = RequestBody.create(payload, mediaType);

    // Build the HTTP request and include the header information
    Request.Builder requestBuilder = new Request.Builder().url(url).post(body);

    // Add each header from the map
    for (Map.Entry<String, String> entry : header.entrySet()) {
      requestBuilder.addHeader(entry.getKey(), entry.getValue());
    }

    Request request = requestBuilder.build();

    // Obtain an HTTP client from the pool
    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

    try (Response response = httpClient.newCall(request).execute()) {
      int code = response.code();
      if (response.isSuccessful()) {
        // Return success response with the response body
        return ResponseVo.ok(response.body().bytes());
      } else {
        // Return failure response with code and response body
        String responseBody = response.body().string();
        return ResponseVo.fail(code, responseBody);
      }
    } catch (IOException e) {
      // Wrap and rethrow any IO exceptions
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
