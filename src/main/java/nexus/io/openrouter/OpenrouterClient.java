package nexus.io.openrouter;

import com.litongjava.tio.utils.http.HttpUtils;
import com.litongjava.tio.utils.json.JsonUtils;

import nexus.io.model.http.response.ResponseVo;

public class OpenrouterClient {

  public OpenrouterModelResponse getModels() {
    ResponseVo responseVo = HttpUtils.get("https://openrouter.ai/api/v1/models");
    if (responseVo.isOk()) {
      return JsonUtils.parse(responseVo.getBodyString(), OpenrouterModelResponse.class);
    }
    return null;
  }
}
