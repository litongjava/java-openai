package com.litongjava.jian.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.model.web.WebPageContent;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class JinaSearchClient {

  // Reuse a single OkHttpClient instance if possible
  private static final OkHttpClient client = OkHttpClientPool.get120HttpClient();

  // Regex to match title, URL, description, and markdown content
  public static String regex = "\\[(\\d+)\\] Title: (.*?)\n" +
  //
      "\\[\\d+\\] URL Source: (.*?)\n" +
      //
      "\\[\\d+\\] Description: (.*?)(?=\\[\\d+\\] Markdown Content:|\n\\[\\d+\\]|$)" +
      //
      "(?:\\[\\d+\\] Markdown Content:\n(.*?))?(?=\n\\[\\d+\\] Title: |$)";

  public static Pattern sourcePattern = Pattern.compile(regex, Pattern.DOTALL);

  public static ResponseVo search(JinaSearchRequest request) {
    // 默认endpoint
    String endpoint = (request.getEndpoint() == null) ? "https://s.jina.ai/" : request.getEndpoint();

    // 默认method
    String method = (request.getMethod() == null) ? "POST" : request.getMethod();

    // 用来存储最终的响应信息
    ResponseVo responseVo = new ResponseVo();

    try {
      // Build request body if needed (for POST or PUT)
      RequestBody body = null;

      if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
        Map<String, Object> reqBody = new HashMap<>();
        if (request.getQ() != null) {
          reqBody.put("q", request.getQ());
        }
        if (request.getCount() != null) {
          reqBody.put("count", request.getCount());
        }
        String json = JsonUtils.toJson(reqBody);

        MediaType mediaType = MediaType.parse(request.getContentType() != null ? request.getContentType() : "application/json; charset=utf-8");
        body = RequestBody.create(json, mediaType);
      }

      // Prepare the request
      Request.Builder builder = new Request.Builder().url(endpoint);

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
      if (request.getXRetainImages() != null) {
        builder.header("X-Retain-Images", request.getXRetainImages());
      }
      if (request.getXSite() != null) {
        builder.header("X-Site", request.getXSite());
      }
      if (request.getXWithGeneratedAlt() != null) {
        builder.header("X-With-Generated-Alt", request.getXWithGeneratedAlt());
      }
      if (request.getXWithLinksSummary() != null) {
        builder.header("X-With-Links-Summary", request.getXWithLinksSummary());
      }

      // Set method and body
      switch (method.toUpperCase()) {
      case "POST":
        builder.post(body != null ? body : RequestBody.create("", null));
        break;
      case "PUT":
        builder.put(body != null ? body : RequestBody.create("", null));
        break;
      case "DELETE":
        builder.delete();
        break;
      case "GET":
      default:
        builder.get();
        break;
      }

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
      e.printStackTrace();
      responseVo.setOk(false);
      responseVo.setBodyString(e.getMessage());
    }

    return responseVo;
  }

  public static String search(String keywords) {
    JinaSearchRequest jinaSearchRequest = new JinaSearchRequest();
    jinaSearchRequest.setQ(keywords);
    //Remove All Images
    jinaSearchRequest.setXRetainImages("none");
    ResponseVo vo = JinaSearchClient.search(jinaSearchRequest);
    String bodyString = vo.getBodyString();
    if (vo.isOk()) {
      return bodyString;
    } else {
      int code = vo.getCode();
      throw new RuntimeException("code:" + code + " body:" + bodyString);
    }
  }

  public static List<WebPageContent> parse(String markdown) {
    List<WebPageContent> results = new ArrayList<>();

    Matcher matcher = sourcePattern.matcher(markdown);
    while (matcher.find()) {
      String title = matcher.group(2).trim();
      String url = matcher.group(3).trim();
      String description = matcher.group(4) != null ? matcher.group(4).trim() : "";
      String content = matcher.group(5) != null ? matcher.group(5).trim() : "";

      WebPageContent webPageConteont = new WebPageContent(title, url, description, content);
      results.add(webPageConteont);
    }
    return results;
  }
}
