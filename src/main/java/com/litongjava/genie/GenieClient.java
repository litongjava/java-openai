package com.litongjava.genie;

import java.io.IOException;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GenieClient {

  // private final String apiKey;
  private final String baseUrl;

  public GenieClient() {
    this(EnvUtils.getStr("GENIE_API_KEY"), EnvUtils.getStr("GENIE_BASE_URL"));
  }

  public GenieClient(String apiKey, String baseUrl) {
    // this.apiKey = apiKey;
    this.baseUrl = baseUrl;
  }

  public ResponseVo tts(GenieTTSRequest requestVo) {
    String url = baseUrl + "/tts";
    String payload = JsonUtils.toJson(requestVo);

    OkHttpClient client = OkHttpClientPool.get60HttpClient();
    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body = RequestBody.create(payload, mediaType);
    Request request = new Request.Builder().url(url).post(body).build();

    try (Response response = client.newCall(request).execute()) {
      int code = response.code();
      if (response.isSuccessful()) {
        byte[] bytes = response.body().bytes();
        return new ResponseVo(true, code, bytes);
      } else {
        String string = response.body().string();
        return new ResponseVo(false, code, string);
      }

    } catch (IOException e) {
      throw new RuntimeException("Failed to request:" + url, e);
    }

  }
}
