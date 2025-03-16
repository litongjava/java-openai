package com.litongjava.supadata;

import java.util.HashMap;
import java.util.Map;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.HttpUtils;
import com.litongjava.tio.utils.json.FastJson2Utils;

import okhttp3.Response;

public class SupadataClient {
  public static final String target = "https://api.supadata.ai/v1/youtube/transcript?videoId=%s";

  /**
   * @param videoId
   * @return
   */
  public static ResponseVo get(String videoId) {
    String targetUrl = String.format(target, videoId);
    String apiKey = EnvUtils.getStr("SUPADATA_API_KEY");
    Map<String, String> header = new HashMap<>(1);
    header.put("x-api-key", apiKey);
    try (Response response = HttpUtils.get(targetUrl, header);) {
      String string = response.body().string();
      int code = response.code();
      if (response.isSuccessful()) {
        return ResponseVo.ok(string);
      } else {
        return ResponseVo.fail(code, string);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static SubTitleResponse getSubTitle(String videoId) {
    ResponseVo responseVo = get(videoId);
    if (responseVo.isOk()) {
      return FastJson2Utils.parse(responseVo.getBodyString(), SubTitleResponse.class);
    } else {
      return null;
    }
  }
}
