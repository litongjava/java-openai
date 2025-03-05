package com.litongjava.apify;

import java.io.IOException;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.OkHttpClientPool;
import com.litongjava.tio.utils.hutool.StrUtil;
import com.litongjava.tio.utils.json.JsonUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiFyClient {
  public static String LINKEDIN_PROFILE_SCRAPER_URL = "https://api.apify.com/v2/acts/dev_fusion~linkedin-profile-scraper/run-sync-get-dataset-items";

  public static ResponseVo linkedinProfileScraper(String url) {
    return linkedinProfileScraper(new ApiFyLinkedProfileReqVo(url));
  }

  public static ResponseVo linkedinProfileScraper(ApiFyLinkedProfileReqVo reqVo) {
    String key = EnvUtils.getStr("APIFY_API_KEY");
    if (StrUtil.isBlank(key)) {
      throw new RuntimeException("APIFY_API_KEY is blank");
    }
    OkHttpClient client = OkHttpClientPool.get60HttpClient();
    String payload = JsonUtils.toJson(reqVo);
    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body = RequestBody.create(payload, mediaType);
    Request request = new Request.Builder()
        //
        .url(LINKEDIN_PROFILE_SCRAPER_URL).post(body)
        //
        .addHeader("Authorization", "Bearer " + key).build();
    try (Response response = client.newCall(request).execute()) {
      int code = response.code();
      String string = response.body().string();
      return new ResponseVo(true, code, string);
    } catch (IOException e) {
      throw new RuntimeException("Failed to request:" + LINKEDIN_PROFILE_SCRAPER_URL, e);
    }
  }

}
