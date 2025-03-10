package com.litongjava.google.search;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.Http;
import com.litongjava.tio.utils.json.JsonUtils;
import com.litongjava.tio.utils.url.UrlUtils;

public class GoogleCustomSearchClient {

  public static final String SEARCH_URL = "https://www.googleapis.com/customsearch/v1?key=%s&cx=%s&q=%s";

  public static GoogleCustomSearchResponse search(String key, String cx, String q) {
    String encode = UrlUtils.encode(q);
    String url = String.format(SEARCH_URL, key, cx, encode);
    ResponseVo responseVo = Http.get(url);
    if (responseVo.isOk()) {
      String bodyString = responseVo.getBodyString();
      return JsonUtils.parse(bodyString, GoogleCustomSearchResponse.class);
    } else {
      throw new RuntimeException("request:" + url + " " + "response:" + responseVo.getBodyString());
    }
  }

  public static GoogleCustomSearchResponse serach(String q) {
    String key = EnvUtils.getStr("GOOGLE_API_KEY");
    String ctx = EnvUtils.getStr("GOOGLE_SEARCH_CTX");
    return search(key, ctx, q);
  }
}
