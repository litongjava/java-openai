package com.litongjava.jian.rerank;

import java.io.IOException;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JinaRerankClient {

  public static final String server_url = "https://api.jina.ai/v1/rerank";

  /**
   * @param req
   * @return
   */
  public static RerankRespVo rerank(RerankReqVo req) {
    String json = JsonUtils.toJson(req);
    String respJson = rerank(json);
    if (respJson == null) {
      return null;
    }
    return JsonUtils.parse(respJson, RerankRespVo.class);

  }

  public static String rerank(String bodyString) {
    OkHttpClient client = OkHttpClientPool.getHttpClient();
    MediaType mediaType = MediaType.parse("application/json");

    RequestBody body = RequestBody.create(bodyString, mediaType);

    String apiKey = EnvUtils.get("JINA_API_KEY");
    if (apiKey == null) {
      throw new RuntimeException("JINA_API_KEY is null");
    }
    Request request = new Request.Builder().url(server_url).method("POST", body).addHeader("Authorization", "Bearer " + apiKey).build();
    try (Response response = client.newCall(request).execute()) {
      if (response.isSuccessful()) {
        return response.body().string();
      } else {
        throw new RuntimeException(response.body().string());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
