package com.litongjava.mv;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.model.type.TioTypeReference;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.HttpUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class MotionCanvasClient {

  public static String BASE_URL = EnvUtils.getStr("VT_BASE_URL");

  public static MvApiResponse<MvBody> parseTSX(String code) {
    String url = BASE_URL + "/api/parseTSX";
    ResponseVo response = HttpUtils.postText(url, code);
    if (response.isOk()) {
      String bodyString = response.getBodyString();
      TioTypeReference<MvApiResponse<MvBody>> typeReference = new TioTypeReference<MvApiResponse<MvBody>>() {
      };
      MvApiResponse<MvBody> apiResponse = JsonUtils.parse(bodyString, typeReference);
      return apiResponse;
    }
    return null;
  }

  public static MvApiResponse<String> parseMakeScene(String code) {
    String url = BASE_URL + "/api/parse-make-scene";
    ResponseVo response = HttpUtils.postText(url, code);
    if (response.isOk()) {
      String bodyString = response.getBodyString();
      TioTypeReference<MvApiResponse<String>> typeReference = new TioTypeReference<MvApiResponse<String>>() {
      };
      MvApiResponse<String> apiResponse = JsonUtils.parse(bodyString, typeReference);
      return apiResponse;
    }
    return null;
  }

}
