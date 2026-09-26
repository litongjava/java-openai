package nexus.io.deepseek;

import nexus.io.chat.ChatModelResponse;
import nexus.io.openai.client.OpenAiClient;
import nexus.io.tio.utils.environment.EnvUtils;

/** Helpers for the official DeepSeek API. */
public class DeepSeekClient {
  private DeepSeekClient() {
  }

  /** Lists models actually available to the configured official API account. */
  public static ChatModelResponse getModels() {
    return getModels(EnvUtils.get("DEEPSEEK_API_KEY"));
  }

  public static ChatModelResponse getModels(String apiKey) {
    return OpenAiClient.getModels(DeepSeekConst.API_PREFIX_URL, apiKey);
  }
}
