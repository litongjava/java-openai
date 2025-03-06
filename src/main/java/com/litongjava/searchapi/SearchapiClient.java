package com.litongjava.searchapi;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.HttpUtils;
import com.litongjava.tio.utils.url.UrlUtils;

public class SearchapiClient {

  public static String SERACH_URL = "https://www.searchapi.io/api/v1/search?engine=%s&q=%s";

  public static ResponseVo search(String q) {
    String engine = "google";
    return search(engine, q);
  }

  public static ResponseVo search(String engine, String q) {
    q = UrlUtils.encode(q);
    String url = String.format(SERACH_URL, engine, q);
    String key = EnvUtils.getStr("SEARCHAPI_API_KEY");
    return HttpUtils.get(url, key);
  }
}
