package com.litongjava.cloudflare;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.HttpUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class CloudflareAiClient {

  private String api_base_prefix_url = EnvUtils.getStr(CloudflareAiConst.CLOUDFLARE_API_BASE_PREFIX_URL,
      CloudflareAiConst.CLOUDFLARE_API_BASE_PREFIX_URL_VALUE);

  private String acount_id;
  private String gateway_id;
  private String apiKey;
  private String api_prefix_url;

  public CloudflareAiClient() {
    this(EnvUtils.getStr(CloudflareAiConst.CLOUDFLARE_ACCOUNT_ID),
        EnvUtils.getStr(CloudflareAiConst.CLOUDFLARE_GATEWAY_ID),
        EnvUtils.getStr(CloudflareAiConst.CLOUDFLARE_API_KEY));
  }

  public CloudflareAiClient(String acount_id, String gateway_id, String apiKey) {
    this.acount_id = acount_id;
    this.gateway_id = gateway_id;
    this.apiKey = apiKey;
    // https://gateway.ai.cloudflare.com/v1/{account_id}/{gateway_id}/compat/chat/completions
    this.api_prefix_url = api_base_prefix_url + "/" + acount_id + "/" + gateway_id + "/compat";
  }

  public String getPrefixUrl() {
    return this.api_base_prefix_url;
  }

  public CloudfalreModelInfo models() {
    String url = api_base_prefix_url + "/" + "models";
    ResponseVo responseVo = HttpUtils.get(url);
    if (responseVo.isOk()) {
      return JsonUtils.parse(responseVo.getBodyString(), CloudfalreModelInfo.class);
    }
    return null;
  }
}
