package com.litongjava.searxng;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.http.Http;
import com.litongjava.tio.utils.json.JsonUtils;
import com.litongjava.tio.utils.url.UrlUtils;

public class SearxngSearchClient {

  public static final String SEARCH_URL = "%s?format=%s&language=%s&engines=%s&q=%s";

  public static SearxngSearchResponse search(String endpoint, String format, String language, String engines, String q, Integer pageno) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(endpoint).append("?");
    if (format != null) {
      stringBuffer.append("format=").append(format);
    }
    if (language != null) {
      stringBuffer.append("&language=").append(language);
    }

    if (engines != null) {
      engines = UrlUtils.encode(engines);
      stringBuffer.append("&engines=").append(engines);
    }
    if (q != null) {
      q = UrlUtils.encode(q);
      stringBuffer.append("&q=").append(q);
    }

    if (pageno != null) {
      stringBuffer.append("&pageno=").append(pageno);
    }

    //String url = String.format(SEARCH_URL, endpoint, format, language, engines, q);
    ResponseVo responseVo = Http.get(stringBuffer.toString());
    if (responseVo.isOk()) {
      String bodyString = responseVo.getBodyString();
      return JsonUtils.parse(bodyString, SearxngSearchResponse.class);
    } else {
      throw new RuntimeException("request:" + stringBuffer.toString() + " " + "response:" + responseVo.getBodyString());
    }
  }
}
