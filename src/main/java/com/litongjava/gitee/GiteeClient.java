package com.litongjava.gitee;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.litongjava.consts.ModelPlatformName;
import com.litongjava.exception.GenerateException;
import com.litongjava.gitee.image.GiteeImageRequest;
import com.litongjava.gitee.image.GiteeImageResponse;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.hutool.StrUtil;
import com.litongjava.tio.utils.json.JsonUtils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public class GiteeClient {

  public static boolean debug = false;
  public static final String GITEE_API_URL = EnvUtils.get("GITEE_API_URL", GiteeConst.API_PREFIX_URL);

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
    return generateImage(GITEE_API_URL, header, bodyString);
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

  public static GiteeImageResponse generateImage(GiteeImageRequest request) {
    String apiKey = EnvUtils.get("GITEE_API_KEY");
    String requestBodyString = JsonUtils.toSkipNullJson(request);
    GiteeImageResponse respVo = null;
    try (Response response = generate(apiKey, requestBodyString)) {
      int code = response.code();
      String bodyString = response.body().string();
      if (response.isSuccessful()) {
        try {
          respVo = JsonUtils.parse(bodyString, GiteeImageResponse.class);
          respVo.setRawResponse(bodyString);

        } catch (Exception e) {
          log.error("status code:{},response body:{}", code, bodyString);
          throw new GenerateException(ModelPlatformName.GITEE, "LLM generated failed", GITEE_API_URL,
              requestBodyString, code, bodyString);
        }
        respVo.setRawResponse(bodyString);
      } else {
        log.error("status code:{},response body:{}", code, bodyString);
        throw new GenerateException(ModelPlatformName.GITEE, "LLM generated failed", GITEE_API_URL,
            requestBodyString, code, bodyString);
      }
    } catch (IOException e1) {
      throw new RuntimeException(e1);
    }
    return respVo;
  }
}
