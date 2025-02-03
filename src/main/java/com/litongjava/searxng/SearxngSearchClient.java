package com.litongjava.searxng;

import java.util.HashMap;
import java.util.Map;

import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.HttpUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.Response;

public class SearxngSearchClient {

  public static SearxngSearchResponse search(SearxngSearchParam param) {
    String baseUrl = EnvUtils.getStr("SEARXNG_API_URL");
    String endpoint = baseUrl + "/search";
    return search(endpoint, param);
  }

  public static SearxngSearchResponse search(String endpoint, SearxngSearchParam param) {
    // 使用 Map 收集所有非空参数
    Map<String, String> params = new HashMap<>();

    if (param.getFormat() != null) {
      params.put("format", param.getFormat());
    }
    if (param.getLanguage() != null) {
      params.put("language", param.getLanguage());
    }
    if (param.getEngines() != null) {
      params.put("engines", param.getEngines());
    }
    if (param.getQ() != null) {
      params.put("q", param.getQ());
    }
    if (param.getPageno() != null) {
      params.put("pageno", param.getPageno().toString());
    }
    if (param.getCategories() != null) {
      params.put("categories", param.getCategories());
    }
    if (param.getTime_range() != null) {
      params.put("time_range", param.getTime_range());
    }
    if (param.getSafesearch() != null) {
      params.put("safesearch", param.getSafesearch().toString());
    }
    if (param.getAutocomplete() != null) {
      params.put("autocomplete", param.getAutocomplete());
    }
    if (param.getLocale() != null) {
      params.put("locale", param.getLocale());
    }
    if (param.getNo_cache() != null) {
      params.put("no_cache", param.getNo_cache().toString());
    }
    if (param.getTheme() != null) {
      params.put("theme", param.getTheme());
    }

    try (Response resposne = HttpUtils.post(endpoint, null, params)) {
      String bodyString = resposne.body().string();
      if (resposne.isSuccessful()) {
        return JsonUtils.parse(bodyString, SearxngSearchResponse.class);
      } else {
        throw new RuntimeException("Request URL: " + endpoint + " Response: " + bodyString);
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
