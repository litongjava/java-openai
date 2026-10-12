package nexus.io.deepseek;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nexus.io.chat.ChatModelResponse;
import nexus.io.chat.ChatRequestUtils;
import nexus.io.claude.ClaudeChatResponse;
import nexus.io.claude.ClaudeClient;
import nexus.io.consts.ModelPlatformName;
import nexus.io.exception.GenerateException;
import nexus.io.openai.chat.OpenAiChatRequest;
import nexus.io.openai.client.OpenAiClient;
import nexus.io.tio.utils.environment.EnvUtils;
import nexus.io.tio.utils.json.JsonUtils;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

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

  public static ClaudeChatResponse webSearch(String model, String query) {
    return messages(ClaudeClient.webSearchRequest(model, query));
  }

  public static ClaudeChatResponse webSearch(String apiKey, String model, String query) {
    return messages(apiKey, ClaudeClient.webSearchRequest(model, query));
  }

  /** apiPrefixUrl is an Anthropic-compatible base, including /anthropic/v1 on DeepSeek. */
  public static ClaudeChatResponse webSearch(String apiPrefixUrl, String apiKey, String model, String query) {
    return messages(apiPrefixUrl, apiKey, ClaudeClient.webSearchRequest(model, query));
  }

  public static ClaudeChatResponse messages(OpenAiChatRequest request) {
    return messages(EnvUtils.get("DEEPSEEK_API_KEY"), request);
  }

  public static ClaudeChatResponse messages(String apiKey, OpenAiChatRequest request) {
    return messages(searchBaseUrl(), apiKey, request);
  }

  /** Preserves server search results, citations, usage, and pause_turn responses. */
  public static ClaudeChatResponse messages(String apiPrefixUrl, String apiKey, OpenAiChatRequest request) {
    if (Boolean.TRUE.equals(request.getStream())) {
      throw new IllegalArgumentException("Use the EventSourceListener overload for streaming Messages requests");
    }
    if (request.getMax_tokens() == null) {
      request.setMax_tokens(4096);
    }
    String json = ChatRequestUtils.toSkipNullJson(request);
    try (Response response = ClaudeClient.chatCompletions(apiPrefixUrl, headers(apiKey), json)) {
      String body = response.body().string();
      if (!response.isSuccessful()) {
        throw new GenerateException(ModelPlatformName.DEEPSEEK, "DeepSeek Messages failed", apiPrefixUrl,
            json, response.code(), body);
      }
      ClaudeChatResponse result = JsonUtils.parse(body, ClaudeChatResponse.class);
      result.setRawResponse(body);
      return result;
    } catch (IOException e) {
      throw new RuntimeException("DeepSeek Messages response could not be read", e);
    }
  }

  public static EventSource messages(OpenAiChatRequest request, EventSourceListener listener) {
    return messages(searchBaseUrl(), EnvUtils.get("DEEPSEEK_API_KEY"), request, listener);
  }

  /** Streams native Anthropic-format events, including server tool content blocks. */
  public static EventSource messages(String apiPrefixUrl, String apiKey, OpenAiChatRequest request,
      EventSourceListener listener) {
    if (request.getMax_tokens() == null) {
      request.setMax_tokens(4096);
    }
    request.setStream(true);
    return ClaudeClient.chatCompletions(apiPrefixUrl, headers(apiKey), ChatRequestUtils.toSkipNullJson(request), listener);
  }

  private static String searchBaseUrl() {
    return EnvUtils.get("DEEPSEEK_SEARCH_BASE_URL", DeepSeekConst.ANTHROPIC_API_PREFIX_URL);
  }

  private static Map<String, String> headers(String apiKey) {
    if (apiKey == null || apiKey.trim().isEmpty()) {
      throw new IllegalArgumentException("api key can not empty");
    }
    Map<String, String> headers = new HashMap<>();
    headers.put("x-api-key", apiKey);
    headers.put("Authorization", "Bearer " + apiKey);
    headers.put("anthropic-version", "2023-06-01");
    return headers;
  }
}
