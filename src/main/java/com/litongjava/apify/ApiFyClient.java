package com.litongjava.apify;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.HttpUtils;
import com.litongjava.tio.utils.hutool.StrUtil;
import com.litongjava.tio.utils.json.JsonUtils;

public class ApiFyClient {
  public static String LINKEDIN_PROFILE_SCRAPER_URL = "https://api.apify.com/v2/acts/dev_fusion~linkedin-profile-scraper/run-sync-get-dataset-items";

  public static String LINKEDIN_PROFILE_POST_SCRAPER_URL = "https://api.apify.com/v2/acts/apimaestro~linkedin-profile-posts/run-sync-get-dataset-items";

  public static ResponseVo linkedinProfileScraper(String url) {
    return linkedinProfileScraper(new ApiFyLinkedProfileReqVo(url));
  }

  public static ResponseVo linkedinProfileScraper(ApiFyLinkedProfileReqVo reqVo) {
    String payload = JsonUtils.toJson(reqVo);
    return postJson(LINKEDIN_PROFILE_SCRAPER_URL, payload);
  }

  public static ResponseVo linkedinProfilePostsScraper(String url) {
    return linkedinProfilePostsScraper(new ApiFyLinkedProfilePostReqVo(url));
  }

  public static ResponseVo linkedinProfilePostsScraper(ApiFyLinkedProfilePostReqVo reqVo) {
    String payload = JsonUtils.toJson(reqVo);
    return postJson(LINKEDIN_PROFILE_POST_SCRAPER_URL, payload);
  }

  public static ResponseVo postJson(String url, String payload) {
    String key = EnvUtils.getStr("APIFY_API_KEY");
    if (StrUtil.isBlank(key)) {
      throw new RuntimeException("APIFY_API_KEY is blank");
    }

    return HttpUtils.postJson(url, key, payload);
  }

}
