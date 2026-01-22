package com.litongjava.openrouter;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.http.HttpUtils;
import com.litongjava.tio.utils.json.JsonUtils;

public class OpenrouterClient {

  public OpenrouterModelResponse getModels() {
    ResponseVo responseVo = HttpUtils.get("https://openrouter.ai/api/v1/models");
    if (responseVo.isOk()) {
      return JsonUtils.parse(responseVo.getBodyString(), OpenrouterModelResponse.class);
    }
    return null;
  }
}
