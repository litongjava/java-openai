package com.litongjava.bailian;

import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.tio.utils.environment.EnvUtils;

public class BaiLianClient {

  public static float[] embeddingArray(String text) {
    String apiKey = EnvUtils.get("BAILIAN_API_KEY");
    return OpenAiClient.embeddingArray(BaiLianConst.API_PERFIX_URL, apiKey, BaiLianAiModels.TEXT_EMBEDDING_V4, text);
  }

  public static float[] embeddingArray(String model, String text) {
    String apiKey = EnvUtils.get("BAILIAN_API_KEY");
    return OpenAiClient.embeddingArray(BaiLianConst.API_PERFIX_URL, apiKey, model, "hi");
  }

  public static float[] embeddingArray(String apiKey, String model, String text) {
    return OpenAiClient.embeddingArray(BaiLianConst.API_PERFIX_URL, apiKey, model, "hi");
  }
}
