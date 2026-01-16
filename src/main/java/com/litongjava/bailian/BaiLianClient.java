package com.litongjava.bailian;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litongjava.bailian.image.MultiModalRequest;
import com.litongjava.bailian.image.MultiModalResponse;
import com.litongjava.consts.ModelPlatformName;
import com.litongjava.exception.GenerateException;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.hutool.StrUtil;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BaiLianClient {
  private static final Logger log = LoggerFactory.getLogger(BaiLianClient.class);
  public static boolean debug = false;
  public static final String BAILIAN_API_URL = EnvUtils.get("BAILIAN_API_URL", BaiLianConst.BAILIAN_PERFIX_URL);

  public static float[] embeddingArray(String text) {
    String apiKey = EnvUtils.get("BAILIAN_API_KEY");
    return OpenAiClient.embeddingArray(BaiLianConst.API_PERFIX_URL, apiKey, BaiLianAiModels.TEXT_EMBEDDING_V4, text);
  }

  public static float[] embeddingArray(String model, String text) {
    String apiKey = EnvUtils.get("BAILIAN_API_KEY");
    return OpenAiClient.embeddingArray(BaiLianConst.API_PERFIX_URL, apiKey, model, "hi");
  }

  public static float[] embeddingArray(String apiKey, String model, String text) {
    return OpenAiClient.embeddingArray(BaiLianConst.API_PERFIX_URL, apiKey, model, "hi");
  }

  /**
   * 
   * @param apiKey
   * @param bodyString
   * @return
   */
  public static Response generate(String apiKey, String bodyString) {
    Map<String, String> header = new HashMap<>();
    if (StrUtil.isBlank(apiKey)) {
      throw new RuntimeException("api key can not empty");
    }
    header.put("Authorization", "Bearer " + apiKey);
    return generate(header, bodyString);
  }

  /**
   * 
   * @param header
   * @param bodyString
   * @return
   */
  public static Response generate(Map<String, String> header, String bodyString) {
    return generate(BAILIAN_API_URL, header, bodyString);
  }

  /**
   * 
   * @param uri
   * @param requestHeaders
   * @param bodyString
   * @return
   */
  public static Response generate(String uri, Map<String, String> requestHeaders, String bodyString) {

    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));

    Headers headers = Headers.of(requestHeaders);

    String url = uri + "/services/aigc/multimodal-generation/generation";
    Request request = new Request.Builder() //
        .url(url) //
        .method("POST", body).headers(headers) //
        .build();
    try {
      return httpClient.newCall(request).execute();
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static MultiModalResponse generateImage(MultiModalRequest request) {
    String apiKey = EnvUtils.get("BAILIAN_API_KEY");
    String requestBodyString = JsonUtils.toSkipNullJson(request);
    MultiModalResponse respVo = null;
    try (Response response = generate(apiKey, requestBodyString)) {
      int code = response.code();
      String bodyString = response.body().string();
      if (response.isSuccessful()) {
        try {
          respVo = JsonUtils.parse(bodyString, MultiModalResponse.class);
          respVo.setRawResponse(bodyString);

        } catch (Exception e) {
          log.error("status code:{},response body:{}", code, bodyString);
          throw new GenerateException(ModelPlatformName.BAILIAN, "LLM generated failed", BAILIAN_API_URL,
              requestBodyString, code, bodyString);
        }
        respVo.setRawResponse(bodyString);
      } else {
        log.error("status code:{},response body:{}", code, bodyString);
        throw new GenerateException(ModelPlatformName.BAILIAN, "LLM generated failed", BAILIAN_API_URL,
            requestBodyString, code, bodyString);
      }
    } catch (IOException e1) {
      throw new RuntimeException(e1);
    }
    return respVo;
  }
}
