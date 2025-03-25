package com.litongjava.tavily;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.HttpUtils;
import com.litongjava.tio.utils.hutool.StrUtil;
import com.litongjava.tio.utils.json.JsonUtils;

public class TavilyClient {

  public static final String TAVILY_API_BASE = "https://api.tavily.com";

  /**
   * 发送请求，传入封装好的 SearchRequest 对象
   *
   * @param request 请求参数对象
   * @return 响应对象 ResponseVo
   */
  public static TavilySearchResponse search(TavilySearchRequest request) {
    String requestJson = JsonUtils.toSkipNullJson(request);
    String apiBase = EnvUtils.getStr("TAVILY_API_BASE", TAVILY_API_BASE);
    String url = apiBase + "/search";
    String token = EnvUtils.getStr("TAVILY_API_TOKEN");
    if (StrUtil.isBlank(token)) {
      throw new RuntimeException("TAVILY_API_TOKEN can not be empty");
    }
    ResponseVo responseVo = HttpUtils.postJson(url, token, requestJson);
    int code = responseVo.getCode();
    String bodyString = responseVo.getBodyString();
    if (responseVo.isOk()) {

    } else {
      throw new RuntimeException("request:" + requestJson + " response code:" + code + " response body:" + bodyString);
    }

    return JsonUtils.parse(bodyString, TavilySearchResponse.class);
  }

  /**
   * 使用默认参数封装 SearchRequest 对象，并发送请求
   *
   * @param query 查询关键字
   * @return 响应对象 ResponseVo
   */
  public static TavilySearchResponse search(String query) {
    // 使用 Builder 构建 SearchRequest 对象
    TavilySearchRequest request = TavilySearchRequest.builder().query(query).topic("general").search_depth("basic")
        //
        .chunks_perSource(3).max_results(4).include_answer(false).include_raw_content(true)
        //
        .include_images(false).include_image_descriptions(false).build();
    return search(request);
  }

  /**
   * 发送 extract 请求，传入封装好的 TavilyExtractRequest 对象
   *
   * @param request 请求参数对象
   * @return 响应对象 TavilyExtractResponse
   */
  public static TavilyExtractResponse extract(TavilyExtractRequest request) {
    String requestJson = JsonUtils.toJson(request);
    String apiBase = EnvUtils.getStr("TAVILY_API_BASE", TAVILY_API_BASE);
    String url = apiBase + "/extract";
    String token = EnvUtils.getStr("TAVILY_API_TOKEN");
    ResponseVo responseVo = HttpUtils.postJson(url, token, requestJson);
    int code = responseVo.getCode();
    String bodyString = responseVo.getBodyString();
    if (!responseVo.isOk()) {
      throw new RuntimeException("request:" + requestJson + " response code:" + code + " response body:" + bodyString);
    }
    return JsonUtils.parse(bodyString, TavilyExtractResponse.class);
  }

  /**
   * 使用默认参数封装 TavilyExtractRequest 对象，并发送 extract 请求
   *
   * @param url 待抽取内容的链接
   * @return 响应对象 TavilyExtractResponse
   */
  public static TavilyExtractResponse extract(String url) {
    TavilyExtractRequest request = TavilyExtractRequest.builder().urls(url).include_images(false)
        //
        .extract_depth("basic").build();
    return extract(request);
  }
}
