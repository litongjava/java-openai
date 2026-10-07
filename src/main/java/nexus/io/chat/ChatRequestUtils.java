package nexus.io.chat;

import nexus.io.openai.chat.OpenAiChatRequest;
import nexus.io.openai.responses.OpenAiResponsesRequest;
import nexus.io.tio.utils.json.JsonUtils;

/** Model parameter compatibility shared by chat transports. */
public final class ChatRequestUtils {

  private ChatRequestUtils() {
  }

  /**
   * Clears unsupported temperatures before serializing, so the field is omitted
   * rather than sent as JSON null. Other request types retain normal
   * serialization.
   */
  public static String toSkipNullJson(Object request) {
    if (request instanceof OpenAiChatRequest) {
      OpenAiChatRequest chatRequest = (OpenAiChatRequest) request;
      chatRequest.setTemperature(normalizeTemperature(chatRequest.getModel(), chatRequest.getTemperature()));

    } else if (request instanceof OpenAiResponsesRequest) {
      OpenAiResponsesRequest responsesRequest = (OpenAiResponsesRequest) request;
      responsesRequest
          .setTemperature(normalizeTemperature(responsesRequest.getModel(), responsesRequest.getTemperature()));
    }
    return JsonUtils.toSkipNullJson(request);
  }

  public static Float normalizeTemperature(String model, Float temperature) {
    return temperature != null && !supportsTemperature(model) ? null : temperature;
  }

  /**
   * Only known incompatible models are excluded; unknown models keep the caller's
   * value. GPT-5.1/5.2/5.4 base models support temperature with their default
   * reasoning effort (none), so they must not be excluded as a whole family.
   *
   * @see <a href=
   *      "https://platform.claude.com/docs/en/about-claude/model-deprecations">Claude
   *      parameter deprecations</a>
   * @see <a href=
   *      "https://developers.openai.com/api/docs/guides/latest-model?model=gpt-5.2">GPT
   *      parameter compatibility</a>
   */
  public static boolean supportsTemperature(String model) {
    if (model == null) {
      return true;
    }
    if (model.startsWith("anthropic/") || model.startsWith("openai/")) {
      model = model.substring(model.indexOf('/') + 1);
    }
    int variant = model.indexOf(':');
    if (variant >= 0) {
      model = model.substring(0, variant);
    }
    // OpenRouter uses dots in Claude version numbers; the native API uses hyphens.
    if (model.startsWith("claude-")) {
      model = model.replace('.', '-');
      return !matches(model, "claude-opus-4-7", "claude-opus-4-8", "claude-opus-5", "claude-sonnet-5", "claude-fable-5",
          "claude-mythos-5", "claude-mythos-preview") && !"claude-fable-5_1".equals(model);
    }
    return !matches(model, "o1", "o3", "o4-mini", "gpt-5-mini", "gpt-5-nano", "gpt-5-pro", "gpt-5-codex",
        "gpt-5.1-codex", "gpt-5.2-codex", "gpt-5.3-codex", "gpt-5.2-pro", "codex-mini-latest", "gpt-6-astra",
        "gpt-6.1-sol") && !model.matches("gpt-5(?:-\\d{4}-\\d{2}-\\d{2})?");
  }

  private static boolean matches(String model, String... names) {
    for (String name : names) {
      if (model.equals(name) || model.startsWith(name + "-")) {
        return true;
      }
    }
    return false;
  }
}
