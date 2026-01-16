package com.litongjava.image;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litongjava.consts.ModelPlatformName;
import com.litongjava.exception.GenerateException;
import com.litongjava.gitee.GiteeConst;
import com.litongjava.openai.consts.OpenAiConst;
import com.litongjava.openai.image.OpenAiImageRequest;
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

public class UniImageClient {
  private static final Logger log = LoggerFactory.getLogger(UniImageClient.class);
  
  public static boolean debug = false;
  public static final String GITEE_API_URL = EnvUtils.get("GITEE_API_URL", GiteeConst.API_PREFIX_URL);
  public static final String OPENAI_API_URL = EnvUtils.get("OPENAI_API_URL", OpenAiConst.API_PREFIX_URL);

  /**
   * 
   * @param apiKey
   * @param bodyString
   * @return
   */
  public static Response generateImage(String baseUrl, String apiKey, String bodyString) {
    Map<String, String> header = new HashMap<>();
    if (StrUtil.isBlank(apiKey)) {
      throw new RuntimeException("api key can not empty");
    }
    header.put("Authorization", "Bearer " + apiKey);
    return generateImage(baseUrl, header, bodyString);
  }

  /**
   * 
   * @param baseUrl
   * @param requestHeaders
   * @param bodyString
   * @return
   */
  public static Response generateImage(String baseUrl, Map<String, String> requestHeaders, String bodyString) {

    OkHttpClient httpClient = OkHttpClientPool.get300HttpClient();

    RequestBody body = RequestBody.create(bodyString, MediaType.parse("application/json"));

    Headers headers = Headers.of(requestHeaders);

    String url = baseUrl + "/images/generations";
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

  public static UniImageResponse generateImage(UniImageRequest request) {
    String platform = request.getPlatform();
    String apiKey = request.getApiKey();
    String baseUrl = null;
    if (ModelPlatformName.GITEE.equals(platform)) {
      baseUrl = GITEE_API_URL;
      if (apiKey == null) {
        apiKey = EnvUtils.get("GITEE_API_KEY");
      }
    } else {
      baseUrl = OPENAI_API_URL;
      if (apiKey == null) {
        apiKey = EnvUtils.get("OPENAI_API_KEY");
      }
    }

    OpenAiImageRequest openAiImageRequest = new OpenAiImageRequest(request);
    String requestBodyString = JsonUtils.toSkipNullJson(openAiImageRequest);
    UniImageResponse respVo = null;
    try (Response response = generateImage(baseUrl, apiKey, requestBodyString)) {
      int code = response.code();
      String bodyString = response.body().string();
      if (response.isSuccessful()) {
        try {
          respVo = JsonUtils.parse(bodyString, UniImageResponse.class);
          respVo.setRawResponse(bodyString);

        } catch (Exception e) {
          log.error("status code:{},response body:{}", code, bodyString);
          throw new GenerateException(ModelPlatformName.GITEE, "LLM generated failed", GITEE_API_URL, requestBodyString,
              code, bodyString);
        }
        respVo.setRawResponse(bodyString);
      } else {
        log.error("status code:{},response body:{}", code, bodyString);
        throw new GenerateException(ModelPlatformName.GITEE, "LLM generated failed", GITEE_API_URL, requestBodyString,
            code, bodyString);
      }
    } catch (IOException e1) {
      throw new RuntimeException(e1);
    }
    return respVo;
  }
}
