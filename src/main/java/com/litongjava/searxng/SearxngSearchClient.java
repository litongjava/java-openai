package com.litongjava.searxng;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.http.Http;
import com.litongjava.tio.utils.json.JsonUtils;
import com.litongjava.tio.utils.url.UrlUtils;

public class SearxngSearchClient {

  public static final String SEARCH_URL = "%s?format=%s&language=%s&engines=%s&q=%s";

  public static SearxngSearchResponse search(String endpoint, String format, String language, String engines, String q) {
    String encode = UrlUtils.encode(q);
    String url = String.format(SEARCH_URL, endpoint, format, language, engines, encode);
    ResponseVo responseVo = Http.get(url);
    if (responseVo.isOk()) {
      String bodyString = responseVo.getBodyString();
      return JsonUtils.parse(bodyString, SearxngSearchResponse.class);
    } else {
      throw new RuntimeException("request:" + url + " " + "response:" + responseVo.getBodyString());
    }
  }
}
