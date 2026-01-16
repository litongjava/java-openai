package com.litongjava.jian.reader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class JinaReaderClient {
  private static final Logger log = LoggerFactory.getLogger(JinaReaderClient.class);
  
  // Reuse a single OkHttpClient instance if possible
  private static final OkHttpClient client = OkHttpClientPool.get120HttpClient();

  public static ResponseVo read(JinaReaderRequest request) {
    // 默认endpoint
    String endpoint = (request.getEndpoint() == null) ? "https://r.jina.ai/" : request.getEndpoint();
    String url = endpoint + request.getUrl();

    // Prepare the request
    Request.Builder builder = new Request.Builder().url(url);

    // 用来存储最终的响应信息
    ResponseVo responseVo = new ResponseVo();

    // Add headers
    // 1) Authorization
    if (request.getAuthorization() != null) {
      builder.header("Authorization", "Bearer " + request.getAuthorization());
    } else {
      String key = EnvUtils.getStr("JINA_API_KEY");
      if (key == null) {
        responseVo.setOk(false);
        responseVo.setBodyString("JINA_API_KEY is null");
        return responseVo;
      }
      builder.header("Authorization", "Bearer " + key);
    }
    // 2) Content-Type (usually handled by RequestBody, but we can add if needed)
    if (request.getContentType() != null) {
      builder.header("Content-Type", request.getContentType());
    }
    // 3) X-Retain-Images, X-Site, etc.
    if (request.getxRetainImages() != null) {
      builder.header("X-Retain-Images", request.getxRetainImages());
    }
    if (request.getxRespondWith() != null) {
      builder.header("X-Respond-With", request.getxRespondWith());
    }
    if (request.getxWithGeneratedAlt() != null) {
      builder.header("X-With-Generated-Alt", request.getxWithGeneratedAlt());
    }
    if (request.getxWithLinksSummary() != null) {
      builder.header("X-With-Links-Summary", request.getxWithLinksSummary());
    }
    builder.get();

    try {
      // Make the request
      Request okHttpRequest = builder.build();
      try (Response response = client.newCall(okHttpRequest).execute()) {
        int code = response.code();
        responseVo.setCode(code);
        // 2xx means success
        responseVo.setOk(code >= 200 && code < 300);

        // Read response body as string
        ResponseBody responseBody = response.body();
        String bodyString = (responseBody == null) ? "" : responseBody.string();
        responseVo.setBodyString(bodyString);

        // Collect response headers
        Map<String, String> headersMap = new HashMap<>();
        Headers headers = response.headers();
        for (String name : headers.names()) {
          headersMap.put(name, headers.get(name));
        }
        responseVo.setHeadersMap(headersMap);
      }

    } catch (IOException e) {
      log.error(e.getMessage(), e);
      responseVo.setOk(false);
      responseVo.setBodyString(e.getMessage());
    }

    return responseVo;
  }

  public static String read(String url) {
    JinaReaderRequest request = new JinaReaderRequest();
    request.setUrl(url);
    //Remove All Images
    request.setxRetainImages("none");
    ResponseVo vo = read(request);
    String bodyString = vo.getBodyString();
    if (vo.isOk()) {
      return bodyString;
    } else {
      int code = vo.getCode();
      throw new RuntimeException("code:" + code + " body:" + bodyString);
    }
  }

}
