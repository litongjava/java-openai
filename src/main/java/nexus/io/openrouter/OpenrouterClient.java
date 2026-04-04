package nexus.io.openrouter;

import nexus.io.model.http.response.ResponseVo;
import nexus.io.tio.utils.http.HttpUtils;
import nexus.io.tio.utils.json.JsonUtils;

public class OpenrouterClient {

  public OpenrouterModelResponse getModels() {
    ResponseVo responseVo = HttpUtils.get("https://openrouter.ai/api/v1/models");
    if (responseVo.isOk()) {
      return JsonUtils.parse(responseVo.getBodyString(), OpenrouterModelResponse.class);
    }
    return null;
  }
}
