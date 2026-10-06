package nexus.io.chat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nexus.io.aiapi.AiApiConst;
import nexus.io.bailian.BaiLianConst;
import nexus.io.cerebras.CerebrasConst;
import nexus.io.claude.ClaudeCacheControl;
import nexus.io.claude.ClaudeChatResponse;
import nexus.io.claude.ClaudeClient;
import nexus.io.claude.ClaudeConsts;
import nexus.io.claude.ClaudeMessageContent;
import nexus.io.consts.ModelPlatformName;
import nexus.io.deepseek.DeepSeekConst;
import nexus.io.exchangetoken.ExchangetokenConst;
import nexus.io.gemini.GeminiCandidate;
import nexus.io.gemini.GeminiChatRequest;
import nexus.io.gemini.GeminiChatResponse;
import nexus.io.gemini.GeminiClient;
import nexus.io.gemini.GeminiConsts;
import nexus.io.gemini.GeminiContentResponse;
import nexus.io.gemini.GeminiGenerationConfig;
import nexus.io.gemini.GeminiPart;
import nexus.io.gemini.GeminiTool;
import nexus.io.gemini.GeminiUsageMetadata;
import nexus.io.gemini.GroundingMetadata;
import nexus.io.gitee.GiteeConst;
import nexus.io.llmproxy.LlmProxyConst;
import nexus.io.minimax.MiniMaxConst;
import nexus.io.moonshot.MoonshotConst;
import nexus.io.openai.ChatProvider;
import nexus.io.openai.chat.ChatResponseMessage;
import nexus.io.openai.chat.ChatResponseUsage;
import nexus.io.openai.chat.OpenAiChatMessage;
import nexus.io.openai.chat.OpenAiChatRequest;
import nexus.io.openai.chat.OpenAiChatResponse;
import nexus.io.openai.client.OpenAiClient;
import nexus.io.openai.consts.OpenAiConst;
import nexus.io.openai.responses.OpenAiResponsesClient;
import nexus.io.openai.responses.OpenAiResponsesInput;
import nexus.io.openai.responses.OpenAiResponsesInputContent;
import nexus.io.openai.responses.OpenAiResponsesRequest;
import nexus.io.openai.responses.OpenAiResponsesResponse;
import nexus.io.openai.responses.OpenAiResponsesUsage;
import nexus.io.openrouter.OpenRouterConst;
import nexus.io.tencent.TencentConst;
import nexus.io.tio.utils.environment.EnvUtils;
import nexus.io.tio.utils.hutool.StrUtil;
import nexus.io.tio.utils.json.JsonUtils;
import nexus.io.vertexai.VertexAiConsts;
import nexus.io.volcengine.VolcEngineConst;
import nexus.io.zenmux.ZenmuxConst;
import okhttp3.OkHttpClient;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

public class UniChatClient {
  public static final String GEMINI_API_KEY = GeminiClient.GEMINI_API_KEY;
  public static final String VERTEX_AI_API_KEY = EnvUtils.get(VertexAiConsts.VERTEX_AI_API_KEY_NAME);
  public static final String VERTEX_AI_API_URL = EnvUtils.get(VertexAiConsts.VERTEX_AI_API_URL_NAME,
      VertexAiConsts.API_MODEL_BASE);
  public static final String CLAUDE_API_KEY = ClaudeClient.CLAUDE_API_KEY;

  public static final String OPENAI_API_URL = EnvUtils.get("OPENAI_API_URL", OpenAiConst.API_PREFIX_URL);
  public static final String OPENAI_API_KEY = EnvUtils.get("OPENAI_API_KEY");

  public static final String DEEPSEEK_API_URL = EnvUtils.get("DEEPSEEK_API_URL", DeepSeekConst.API_PREFIX_URL);
  public static final String DEEPSEEK_API_KEY = EnvUtils.get("DEEPSEEK_API_KEY");

  public static final String VOLCENGINE_API_URL = EnvUtils.get("VOLCENGINE_API_URL", VolcEngineConst.API_PREFIX_URL);
  public static final String VOLCENGINE_API_KEY = EnvUtils.get("VOLCENGINE_API_KEY");

  public static final String OPENAI_RESPONSES_API_URL = EnvUtils.get("OPENAI_RESPONSES_API_URL",
      OpenAiConst.API_PREFIX_URL);
  public static final String OPENAI_RESPONSES_API_KEY = EnvUtils.get("OPENAI_RESPONSES_API_KEY", OPENAI_API_KEY);

  public static final String VOLCENGINE_RESPONSES_API_URL = EnvUtils.get("VOLCENGINE_RESPONSES_API_URL",
      VolcEngineConst.API_RESPONSES_PREFIX_URL);
  public static final String VOLCENGINE_RESPONSES_API_KEY = EnvUtils.get("VOLCENGINE_RESPONSES_API_KEY",
      VOLCENGINE_API_KEY);

  public static final String OPENROUTER_API_URL = EnvUtils.get("OPENROUTER_API_URL", OpenRouterConst.API_PREFIX_URL);
  public static final String OPENROUTER_API_KEY = EnvUtils.get("OPENROUTER_API_KEY");

  public static final String ZENMUX_API_URL = EnvUtils.get("ZENMUX_API_URL", ZenmuxConst.API_PREFIX_URL);
  public static final String ZENMUX_API_KEY = EnvUtils.get("ZENMUX_API_KEY");

  public static final String BAILIAN_API_URL = EnvUtils.get("BAILIAN_API_URL",
      BaiLianConst.BAILIEN_API_OPENAI_PERFIX_URL);
  public static final String BAILIAN_API_KEY = EnvUtils.get("BAILIAN_API_KEY");

  public static final String TENCENT_API_URL = EnvUtils.get("TENCENT_API_URL", TencentConst.API_PERFIX_URL);
  public static final String TENCENT_API_KEY = EnvUtils.get("TENCENT_API_KEY");

  public static final String MOONSHOT_API_URL = EnvUtils.get("MOONSHOT_API_URL", MoonshotConst.API_PERFIX_URL);
  public static final String MOONSHOT_API_KEY = EnvUtils.get("MOONSHOT_API_KEY");

  public static final String MINIMAX_API_URL = EnvUtils.get("MINIMAX_API_URL", MiniMaxConst.API_PREFIX_URL);
  public static final String MINIMAX_API_KEY = EnvUtils.get("MINIMAX_API_KEY");

  public static final String CEREBRAS_API_URL = EnvUtils.get("CEREBRAS_API_URL", CerebrasConst.API_PREFIX_URL);
  public static final String CEREBRAS_API_KEY = EnvUtils.get("CEREBRAS_API_KEY");

  public static final String GITEE_API_URL = EnvUtils.get(GiteeConst.GITEE_API_URL_KEY, GiteeConst.API_PREFIX_URL);
  public static final String GITEE_API_KEY = EnvUtils.get(GiteeConst.GITEE_API_KEY);
  public static final String LLM_PROXY_API_URL = EnvUtils.get(LlmProxyConst.LLM_PROXY_API_URL_KEY,
      LlmProxyConst.API_PREFIX_URL);
  public static final String LLM_PROXY_API_KEY = EnvUtils.get(LlmProxyConst.LLM_PROXY_API_KEY);

  public static final String OLLAMA_API_URL = EnvUtils.get("OLLAMA_API_URL");
  public static final String OLLAMA_API_KEY = EnvUtils.get("OLLAMA_API_KEY");

  public static final String LLAMACPP_API_URL = EnvUtils.get("LLAMACPP_API_URL");
  public static final String LLAMACPP_API_KEY = EnvUtils.get("LLAMACPP_API_KEY");

  public static final String VLLM_API_URL = EnvUtils.get("VLLM_API_URL");
  public static final String VLLM_API_KEY = EnvUtils.get("VLLM_API_KEY");

  public static final String SWIFT_API_URL = EnvUtils.get("SWIFT_API_URL");
  public static final String SWIFT_API_KEY = EnvUtils.get("SWIFT_API_KEY");

  public static final String TITANIUM_API_KEY = EnvUtils.get("TITANIUM_API_KEY");
  public static final String TITANIUM_API_URL = EnvUtils.get("TITANIUM_API_URL");

  public static final String EXCHANGE_TOKEN_API_KEY = EnvUtils.get("EXCHANGE_TOKEN_API_KEY");
  public static final String EXCHANGE_TOKEN_API_URL = EnvUtils.get("EXCHANGE_TOKEN_API_URL",
      ExchangetokenConst.BASE_URL);

  public static final String EXCHANGE_TOKEN_US_API_URL = EnvUtils.get("EXCHANGE_TOKEN_US_API_URL",
      ExchangetokenConst.US_BASE_URL);

  public static final String EXCHANGE_TOKEN_GOOGLE_API_URL = EnvUtils.get("EXCHANGE_TOKEN_GOOGLE_API_URL",
      ExchangetokenConst.GOOGLE_BASE_URL);

  public static final String EXCHANGE_TOKEN_US_GOOGLE_API_URL = EnvUtils.get("EXCHANGE_TOKEN_US_GOOGLE_API_URL",
      ExchangetokenConst.US_GOOGLE_BASE_URL);

  public static final String AIAPI_API_KEY = EnvUtils.get("AIAPI_API_KEY");
  public static final String AIAPI_API_URL = EnvUtils.get("AIAPI_API_URL", AiApiConst.V1_BASE_URL);

  public static boolean isAnthropic(String platform) {
    return ModelPlatformName.ANTHROPIC.equals(platform) ||
    //
        ModelPlatformName.EXCHANGE_TOKEN_ANTHROPIC.equals(platform) ||
        //
        ModelPlatformName.EXCHANGE_TOKEN_US_ANTHROPIC.equals(platform);
  }

  public static boolean isGoogle(String platform) {
    return ModelPlatformName.GOOGLE.equals(platform) ||
    //
        ModelPlatformName.EXCHANGE_TOKEN_GOOGLE.equals(platform) ||
        //
        ModelPlatformName.EXCHANGE_TOKEN_US_GOOGLE.equals(platform);
  }

  public static ChatModelResponse getModels(String url, String key) {
    return OpenAiClient.getModels(url, key);
  }

  public static UniChatResponse generate(UniChatRequest uniChatRequest) {
    return generate(uniChatRequest.getApiKey(), uniChatRequest);
  }

  /**
   * Synchronous generation using the caller's transport for every supported
   * platform. Platform routing selects the wire protocol, credentials, request
   * and response conversion. No shared client mutation, body logging or
   * application-level retry.
   */
  public static UniChatResponse generate(OkHttpClient client, String key, UniChatRequest request)
      throws java.io.IOException {
    if (client == null || request == null) {
      throw new IllegalArgumentException("Client and request are required");
    }
    if (Boolean.TRUE.equals(request.getStream())) {
      throw new IllegalArgumentException("This transport requires synchronous generation");
    }
    String platform = request.getPlatform();
    PlatformConfig config = platformConfig(platform);
    String prefix = request.getApiPrefixUrl();
    if (StrUtil.isBlank(prefix)) {
      prefix = EnvUtils.get(config.urlName, config.defaultUrl);
    }
    if (StrUtil.isBlank(prefix)) {
      throw new IllegalArgumentException("A configured or explicit API prefix is required");
    }
    if (StrUtil.isBlank(key)) {
      key = request.getApiKey();
    }
    if (StrUtil.isBlank(key)) {
      key = EnvUtils.get(config.keyName);
      if (StrUtil.isBlank(key) && ModelPlatformName.OPENAI_RESPONSES.equals(platform)) {
        key = EnvUtils.get("OPENAI_API_KEY");
      } else if (StrUtil.isBlank(key) && ModelPlatformName.VOLC_ENGINE_RESPONSES.equals(platform)) {
        key = EnvUtils.get("VOLCENGINE_API_KEY");
      }
    }

    if (isGoogle(platform) || ModelPlatformName.VERTEX_AI.equals(platform)) {
      return useGoogle(client, prefix, key, request);
    } else if (isAnthropic(platform)) {
      return useClaude(client, prefix, key, request);
    } else if (ModelPlatformName.OPENAI_RESPONSES.equals(platform)
        || ModelPlatformName.VOLC_ENGINE_RESPONSES.equals(platform)) {
      return useOpenAiResponses(client, prefix, key, request);
    } else {
      return useOpenAi(client, prefix, key, request);
    }
  }

  private static UniChatResponse useOpenAi(OkHttpClient client, String prefix, String key, UniChatRequest request)
      throws java.io.IOException {
    OpenAiChatRequest payload = toOpenAiRequest(request);
    if (ModelPlatformName.VOLC_ENGINE.equals(request.getPlatform()) && request.getMax_tokens() == null) {
      payload.setMax_tokens(16384);
    } else if (ModelPlatformName.BAILIAN.equals(request.getPlatform())) {
      payload.setEnable_thinking(false);
    }
    String raw = post(client, prefix + "/chat/completions", authHeaders("Authorization", bearer(key)), payload);
    OpenAiChatResponse parsed = parseResponse(raw, OpenAiChatResponse.class);
    if (parsed == null || parsed.getChoices() == null || parsed.getChoices().isEmpty()
        || parsed.getChoices().get(0) == null || parsed.getChoices().get(0).getMessage() == null) {
      throw new java.io.IOException("Chat provider response has no assistant message");
    }
    return new UniChatResponse(parsed.getModel(), parsed.getChoices().get(0).getMessage(), parsed.getUsage(), raw);
  }

  private static UniChatResponse useGoogle(OkHttpClient client, String prefix, String key, UniChatRequest request)
      throws java.io.IOException {
    GeminiChatRequest payload = toGoogleRequest(request);
    String raw = post(client, prefix + "/" + request.getModel() + ":generateContent",
        authHeaders("x-goog-api-key", key), payload);
    GeminiChatResponse parsed = parseResponse(raw, GeminiChatResponse.class);
    if (parsed == null || parsed.getCandidates() == null || parsed.getCandidates().isEmpty()
        || parsed.getCandidates().get(0) == null || parsed.getCandidates().get(0).getContent() == null
        || parsed.getCandidates().get(0).getContent().getParts() == null
        || parsed.getCandidates().get(0).getContent().getParts().isEmpty()) {
      throw new java.io.IOException("Chat provider response has no candidate content");
    }
    parsed.setRawData(raw);
    return fromGoogleResponse(parsed);
  }

  private static UniChatResponse useClaude(OkHttpClient client, String prefix, String key, UniChatRequest request)
      throws java.io.IOException {
    OpenAiChatRequest payload = toClaudeRequest(request);
    if (payload.getMax_tokens() == null) {
      payload.setMax_tokens(64000);
    }
    java.util.Map<String, String> headers = authHeaders("x-api-key", key);
    headers.put("anthropic-version", "2023-06-01");
    String raw = post(client, prefix + "/messages", headers, payload);
    ClaudeChatResponse parsed = parseResponse(raw, ClaudeChatResponse.class);
    if (parsed == null || parsed.getContent() == null || parsed.getContent().isEmpty()) {
      throw new java.io.IOException("Chat provider response has no message content");
    }
    parsed.setRawResponse(raw);
    return fromClaudeResponse(request.getModel(), parsed);
  }

  private static UniChatResponse useOpenAiResponses(OkHttpClient client, String prefix, String key,
      UniChatRequest request) throws java.io.IOException {
    String raw = post(client, prefix + "/responses", authHeaders("Authorization", bearer(key)),
        toOpenAiResponsesRequest(request));
    OpenAiResponsesResponse parsed = parseResponse(raw, OpenAiResponsesResponse.class);
    if (parsed == null || parsed.getOutputText() == null) {
      throw new java.io.IOException("Chat provider response has no output text");
    }
    parsed.setRawResponse(raw);
    return fromOpenAiResponsesResponse(parsed);
  }

  private static String bearer(String key) {
    return StrUtil.isBlank(key) ? null : "Bearer " + key;
  }

  private static java.util.Map<String, String> authHeaders(String name, String key) {
    java.util.Map<String, String> headers = new java.util.HashMap<>();
    if (StrUtil.isNotBlank(key)) {
      headers.put(name, key);
    }
    return headers;
  }

  /**
   * All protocols execute on this exact client and share non-logging
   * response handling.
   */
  private static String post(OkHttpClient client, String url, java.util.Map<String, String> headers, Object payload)
      throws java.io.IOException {
    okhttp3.Request httpRequest = new okhttp3.Request.Builder().url(url).headers(okhttp3.Headers.of(headers))
        .post(
            okhttp3.RequestBody.create(JsonUtils.toSkipNullJson(payload), okhttp3.MediaType.parse("application/json")))
        .build();
    try (okhttp3.Response response = client.newCall(httpRequest).execute()) {
      if (!response.isSuccessful() || response.body() == null) {
        throw new java.io.IOException("Chat provider HTTP status " + response.code());
      }
      java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
      java.io.InputStream input = response.body().byteStream();
      byte[] chunk = new byte[4096];
      int count;
      while ((count = input.read(chunk)) != -1) {
        output.write(chunk, 0, count);
      }
      return new String(output.toByteArray(), java.nio.charset.StandardCharsets.UTF_8);
    }
  }

  private static <T> T parseResponse(String raw, Class<T> type) throws java.io.IOException {
    try {
      return JsonUtils.parse(raw, type);
    } catch (RuntimeException invalid) {
      throw new java.io.IOException("Chat provider response is not valid JSON");
    }
  }

  /**
   * Uses the request key when supplied, otherwise reads the platform's key from
   * EnvUtils.
   */
  public static UniChatResponse generate(OkHttpClient client, UniChatRequest request) throws java.io.IOException {
    return generate(client, null, request);
  }

  private static final class PlatformConfig {
    final String urlName;
    final String keyName;
    final String defaultUrl;

    PlatformConfig(String urlName, String keyName, String defaultUrl) {
      this.urlName = urlName;
      this.keyName = keyName;
      this.defaultUrl = defaultUrl;
    }
  }

  /** Mirrors all platform branches in generate(String, UniChatRequest). */
  private static PlatformConfig platformConfig(String platform) {
    if (ModelPlatformName.GOOGLE.equals(platform)) {
      return new PlatformConfig("GEMINI_API_URL", "GEMINI_API_KEY", GeminiConsts.GEMINI_API_MODEL_BASE);
    }
    if (ModelPlatformName.VERTEX_AI.equals(platform)) {
      return new PlatformConfig("VERTEX_AI_API_URL", "VERTEX_AI_API_KEY", VertexAiConsts.API_MODEL_BASE);
    }
    if (ModelPlatformName.EXCHANGE_TOKEN_GOOGLE.equals(platform)) {
      return new PlatformConfig("EXCHANGE_TOKEN_GOOGLE_API_URL", "EXCHANGE_TOKEN_API_KEY",
          ExchangetokenConst.GOOGLE_BASE_URL);
    }
    if (ModelPlatformName.EXCHANGE_TOKEN_US_GOOGLE.equals(platform)) {
      return new PlatformConfig("EXCHANGE_TOKEN_US_GOOGLE_API_URL", "EXCHANGE_TOKEN_API_KEY",
          ExchangetokenConst.US_GOOGLE_BASE_URL);
    }
    if (ModelPlatformName.ANTHROPIC.equals(platform)) {
      return new PlatformConfig("CLAUDE_API_URL", "CLAUDE_API_KEY", ClaudeConsts.API_PREFIX_URL);
    }
    if (ModelPlatformName.EXCHANGE_TOKEN_ANTHROPIC.equals(platform)) {
      return new PlatformConfig("EXCHANGE_TOKEN_API_URL", "EXCHANGE_TOKEN_API_KEY", ExchangetokenConst.BASE_URL);
    }
    if (ModelPlatformName.EXCHANGE_TOKEN_US_ANTHROPIC.equals(platform)) {
      return new PlatformConfig("EXCHANGE_TOKEN_US_API_URL", "EXCHANGE_TOKEN_API_KEY", ExchangetokenConst.US_BASE_URL);
    }
    if (ModelPlatformName.OPENAI_RESPONSES.equals(platform)) {
      return new PlatformConfig("OPENAI_RESPONSES_API_URL", "OPENAI_RESPONSES_API_KEY", OpenAiConst.API_PREFIX_URL);
    }
    if (ModelPlatformName.VOLC_ENGINE_RESPONSES.equals(platform)) {
      return new PlatformConfig("VOLCENGINE_RESPONSES_API_URL", "VOLCENGINE_RESPONSES_API_KEY",
          VolcEngineConst.API_RESPONSES_PREFIX_URL);
    }
    if (ModelPlatformName.VOLC_ENGINE.equals(platform)) {
      return new PlatformConfig("VOLCENGINE_API_URL", "VOLCENGINE_API_KEY", VolcEngineConst.API_PREFIX_URL);
    }
    if (ModelPlatformName.OPENROUTER.equals(platform)) {
      return new PlatformConfig("OPENROUTER_API_URL", "OPENROUTER_API_KEY", OpenRouterConst.API_PREFIX_URL);
    }
    if (ModelPlatformName.ZENMUX.equals(platform)) {
      return new PlatformConfig("ZENMUX_API_URL", "ZENMUX_API_KEY", ZenmuxConst.API_PREFIX_URL);
    }
    if (ModelPlatformName.BAILIAN.equals(platform)) {
      return new PlatformConfig("BAILIAN_API_URL", "BAILIAN_API_KEY", BaiLianConst.BAILIEN_API_OPENAI_PERFIX_URL);
    }
    if (ModelPlatformName.TENCENT.equals(platform)) {
      return new PlatformConfig("TENCENT_API_URL", "TENCENT_API_KEY", TencentConst.API_PERFIX_URL);
    }
    if (ModelPlatformName.MINIMAX.equals(platform)) {
      return new PlatformConfig("MINIMAX_API_URL", "MINIMAX_API_KEY", MiniMaxConst.API_PREFIX_URL);
    }
    if (ModelPlatformName.MOONSHOT.equals(platform)) {
      return new PlatformConfig("MOONSHOT_API_URL", "MOONSHOT_API_KEY", MoonshotConst.API_PERFIX_URL);
    }
    if (ModelPlatformName.CEREBRAS.equals(platform)) {
      return new PlatformConfig("CEREBRAS_API_URL", "CEREBRAS_API_KEY", CerebrasConst.API_PREFIX_URL);
    }
    if (ModelPlatformName.OLLAMA.equals(platform)) {
      return new PlatformConfig("OLLAMA_API_URL", "OLLAMA_API_KEY", null);
    }
    if (ModelPlatformName.LLAMACPP.equals(platform)) {
      return new PlatformConfig("LLAMACPP_API_URL", "LLAMACPP_API_KEY", null);
    }
    if (ModelPlatformName.VLLM.equals(platform)) {
      return new PlatformConfig("VLLM_API_URL", "VLLM_API_KEY", null);
    }
    if (ModelPlatformName.SWIFT.equals(platform)) {
      return new PlatformConfig("SWIFT_API_URL", "SWIFT_API_KEY", null);
    }
    if (ModelPlatformName.TITANIUM.equals(platform)) {
      return new PlatformConfig("TITANIUM_API_URL", "TITANIUM_API_KEY", null);
    }
    if (ModelPlatformName.GITEE.equals(platform)) {
      return new PlatformConfig("GITEE_API_URL", "GITEE_API_KEY", GiteeConst.API_PREFIX_URL);
    }
    if (ModelPlatformName.LLM_PROXY.equals(platform)) {
      return new PlatformConfig("LLM_PROXY_API_URL", "LLM_PROXY_API_KEY", LlmProxyConst.API_PREFIX_URL);
    }
    if (ModelPlatformName.EXCHANGE_TOKEN.equals(platform)) {
      return new PlatformConfig("EXCHANGE_TOKEN_API_URL", "EXCHANGE_TOKEN_API_KEY", ExchangetokenConst.BASE_URL);
    }
    if (ModelPlatformName.EXCHANGE_TOKEN_US.equals(platform)) {
      return new PlatformConfig("EXCHANGE_TOKEN_US_API_URL", "EXCHANGE_TOKEN_API_KEY", ExchangetokenConst.US_BASE_URL);
    }
    if (ModelPlatformName.AIAPI.equals(platform)) {
      return new PlatformConfig("AIAPI_API_URL", "AIAPI_API_KEY", AiApiConst.V1_BASE_URL);
    }
    if (ModelPlatformName.DEEPSEEK.equals(platform)) {
      return new PlatformConfig("DEEPSEEK_API_URL", "DEEPSEEK_API_KEY", DeepSeekConst.API_PREFIX_URL);
    }
    // Keep the existing default branch: unspecified/custom platforms use OpenAI
    // configuration.
    return new PlatformConfig("OPENAI_API_URL", "OPENAI_API_KEY", OpenAiConst.API_PREFIX_URL);
  }

  public static UniChatResponse generate(String key, UniChatRequest uniChatRequest) {

    String platform = uniChatRequest.getPlatform();

    if (ModelPlatformName.GOOGLE.equals(platform)) {
      if (key == null) {
        key = GEMINI_API_KEY;
      }
      return useGoogle(key, uniChatRequest);
    } else if (ModelPlatformName.VERTEX_AI.equals(platform)) {
      if (key == null) {
        key = VERTEX_AI_API_KEY;
      }
      return useVertexAi(key, uniChatRequest);
    } else if (ModelPlatformName.EXCHANGE_TOKEN_GOOGLE.equals(platform)) {
      if (key == null) {
        key = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangeTokenGoogle(key, uniChatRequest);

    } else if (ModelPlatformName.EXCHANGE_TOKEN_US_GOOGLE.equals(platform)) {
      if (key == null) {
        key = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangeTokenUsGoogle(key, uniChatRequest);

    } else if (ModelPlatformName.ANTHROPIC.equals(platform)) {
      if (key == null) {
        key = CLAUDE_API_KEY;
      }
      return useClaude(key, uniChatRequest);

    } else if (ModelPlatformName.OPENAI_RESPONSES.equals(platform)) {
      if (key == null) {
        key = OPENAI_RESPONSES_API_KEY;
      }
      return useOpenAiResponses(key, uniChatRequest);

    } else if (ModelPlatformName.VOLC_ENGINE_RESPONSES.equals(platform)) {
      if (key == null) {
        key = VOLCENGINE_RESPONSES_API_KEY;
      }
      return useVolcEngineResponses(key, uniChatRequest);

    } else if (ModelPlatformName.EXCHANGE_TOKEN_ANTHROPIC.equals(platform)) {
      if (key == null) {
        key = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangeTokenClaude(key, uniChatRequest);
    } else if (ModelPlatformName.EXCHANGE_TOKEN_US_ANTHROPIC.equals(platform)) {
      if (key == null) {
        key = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangeTokenUsClaude(key, uniChatRequest);
    } else if (ModelPlatformName.VOLC_ENGINE.equals(platform)) {
      if (key == null) {
        key = VOLCENGINE_API_KEY;
      }
      Integer max_tokens = uniChatRequest.getMax_tokens();
      if (max_tokens == null) {
        uniChatRequest.setMax_tokens(16384);
      }
      return useVolcEngine(key, uniChatRequest);

    } else if (ModelPlatformName.OPENROUTER.equals(platform)) {
      if (key == null) {
        key = OPENROUTER_API_KEY;
      }
      return useOpenRouter(key, uniChatRequest);

    } else if (ModelPlatformName.ZENMUX.equals(platform)) {
      if (key == null) {
        key = ZENMUX_API_KEY;
      }
      return useZenmux(key, uniChatRequest);

    } else if (ModelPlatformName.BAILIAN.equals(platform)) {
      if (key == null) {
        key = BAILIAN_API_KEY;
      }
      return useBailian(key, uniChatRequest);

    } else if (ModelPlatformName.TENCENT.equals(platform)) {
      if (key == null) {
        key = TENCENT_API_KEY;
      }
      return useTencent(key, uniChatRequest);

    } else if (ModelPlatformName.MINIMAX.equals(platform)) {
      if (key == null) {
        key = MINIMAX_API_KEY;
      }
      return useMiniMax(key, uniChatRequest);

    } else if (ModelPlatformName.MOONSHOT.equals(platform)) {
      if (key == null) {
        key = MOONSHOT_API_KEY;
      }
      return useMoonshot(key, uniChatRequest);

    } else if (ModelPlatformName.CEREBRAS.equals(platform)) {
      if (key == null) {
        key = CEREBRAS_API_KEY;
      }
      return useCerebras(key, uniChatRequest);

    } else if (ModelPlatformName.OLLAMA.equals(platform)) {
      if (key == null) {
        key = OLLAMA_API_KEY;
      }
      return useOllama(key, uniChatRequest);

    } else if (ModelPlatformName.LLAMACPP.equals(platform)) {
      if (key == null) {
        key = LLAMACPP_API_KEY;
      }
      return useLlamacpp(key, uniChatRequest);

    } else if (ModelPlatformName.VLLM.equals(platform)) {
      if (key == null) {
        key = VLLM_API_KEY;
      }
      return useVllm(key, uniChatRequest);

    } else if (ModelPlatformName.SWIFT.equals(platform)) {
      if (key == null) {
        key = SWIFT_API_KEY;
      }
      return useSwift(key, uniChatRequest);

    } else if (ModelPlatformName.TITANIUM.equals(platform)) {
      if (key == null) {
        key = TITANIUM_API_KEY;
      }
      return useTitanium(key, uniChatRequest);

    } else if (ModelPlatformName.GITEE.equals(platform)) {
      if (key == null) {
        key = GITEE_API_KEY;
      }
      return useGitee(key, uniChatRequest);

    } else if (ModelPlatformName.LLM_PROXY.equals(platform)) {
      if (key == null) {
        key = LLM_PROXY_API_KEY;
      }
      return useLlmProxy(key, uniChatRequest);

    } else if (ModelPlatformName.EXCHANGE_TOKEN.equals(platform)) {
      if (key == null) {
        key = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangetoken(key, uniChatRequest);
    } else if (ModelPlatformName.EXCHANGE_TOKEN_US.equals(platform)) {
      if (key == null) {
        key = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangetokenUs(key, uniChatRequest);

    } else if (ModelPlatformName.AIAPI.equals(platform)) {
      if (key == null) {
        key = AIAPI_API_KEY;
      }
      return useAiApi(key, uniChatRequest);

    } else if (ModelPlatformName.DEEPSEEK.equals(platform)) {
      if (key == null) {
        key = DEEPSEEK_API_KEY;
      }
      return useDeepseek(key, uniChatRequest);

    } else {
      if (key == null) {
        key = OPENAI_API_KEY;
      }
      return useOpenAi(key, uniChatRequest);
    }
  }

  public static UniChatResponse useOpenAi(String prefixUrl, String apiKey, UniChatRequest uniChatRequest) {
    OpenAiChatRequest openAiChatRequestVo = toOpenAiRequest(uniChatRequest);
    String apiPrefixUrl = uniChatRequest.getApiPrefixUrl();
    OpenAiChatResponse chatResponse = OpenAiClient.chatCompletions(apiPrefixUrl != null ? apiPrefixUrl : prefixUrl,
        apiKey, openAiChatRequestVo);
    if (chatResponse == null || chatResponse.getChoices() == null || chatResponse.getChoices().isEmpty()
        || chatResponse.getChoices().get(0) == null || chatResponse.getChoices().get(0).getMessage() == null) {
      return null;
    }
    return new UniChatResponse(chatResponse.getModel(), chatResponse.getChoices().get(0).getMessage(),
        chatResponse.getUsage(), chatResponse.getRawResponse());
  }

  /**
   * Convert without modifying the caller's messages; shared by generation and
   * streaming.
   */
  public static OpenAiChatRequest toOpenAiRequest(UniChatRequest uniChatRequest) {
    List<UniChatMessage> messages = uniChatRequest.getMessages();
    List<OpenAiChatMessage> openAiChatMesages = new ArrayList<>();

    if (uniChatRequest.isUseSystemPrompt()) {
      String systemPrompt = uniChatRequest.getSystemPrompt();
      if (StrUtil.isNotBlank(systemPrompt)) {
        openAiChatMesages.add(new OpenAiChatMessage("system", systemPrompt));
      }
    }

    if (messages != null && messages.size() > 0) {
      Iterator<UniChatMessage> iterator = messages.iterator();
      while (iterator.hasNext()) {
        UniChatMessage next = iterator.next();
        String role = next.getRole();
        if (role != null) {
          if (role.equals("model")) {
            role = "assistant";
          }
        } else {
          role = "user";
        }

        OpenAiChatMessage openAiMsg = new OpenAiChatMessage(next);
        openAiMsg.setRole(role);
        openAiChatMesages.add(openAiMsg);
      }
    }

    OpenAiChatRequest openAiChatRequestVo = new OpenAiChatRequest();
    openAiChatRequestVo.setMessages(openAiChatMesages);

    openAiChatRequestVo.setModel(uniChatRequest.getModel());
    openAiChatRequestVo.setThinking(uniChatRequest.getThinking());
    openAiChatRequestVo.setStream(uniChatRequest.getStream());
    Float temperature = uniChatRequest.getTemperature();
    if (temperature != null) {
      openAiChatRequestVo.setTemperature(temperature);
    }

    Integer max_tokens = uniChatRequest.getMax_tokens();
    if (max_tokens != null) {
      openAiChatRequestVo.setMax_tokens(max_tokens);
    }

    Boolean enable_thinking = uniChatRequest.getEnable_thinking();
    if (enable_thinking != null) {
      openAiChatRequestVo.setEnable_thinking(enable_thinking);
    }

    String responseFormat = uniChatRequest.getResponseFormat();
    if (responseFormat != null) {
      openAiChatRequestVo.setResponse_format(responseFormat);
    }

    List<String> responseModalities = uniChatRequest.getResponseModalities();
    openAiChatRequestVo.setModalities(responseModalities);

    ChatProvider provider = uniChatRequest.getProvider();
    openAiChatRequestVo.setProvider(provider);
    openAiChatRequestVo.setTools(uniChatRequest.getTools());

    return openAiChatRequestVo;
  }

  /**
   * Raw HTTP callback for OpenAI-compatible endpoints, retaining cancellation
   * through Call.
   */
  public static okhttp3.Call streamOpenAi(UniChatRequest request, okhttp3.Callback callback) {
    if (request.getApiPrefixUrl() == null || request.getApiKey() == null) {
      throw new IllegalArgumentException("Explicit API URL and key are required");
    }
    OpenAiChatRequest body = toOpenAiRequest(request);
    body.setStream(true);
    return OpenAiClient.chatCompletions(request.getApiPrefixUrl(), request.getApiKey(), body, callback);
  }

  public static UniChatResponse useClaude(String key, UniChatRequest uniChatRequest) {
    String apiPrefixUrl = uniChatRequest.getApiPrefixUrl();
    return useClaude(apiPrefixUrl, key, uniChatRequest);
  }

  public static UniChatResponse useClaude(String apiPrefixUrl, String key, UniChatRequest uniChatRequest) {
    OpenAiChatRequest openAiChatRequest = toClaudeRequest(uniChatRequest);
    ClaudeChatResponse chatResponse = null;
    if (apiPrefixUrl != null) {
      chatResponse = ClaudeClient.chatCompletions(apiPrefixUrl, key, openAiChatRequest);
    } else {
      chatResponse = ClaudeClient.chatCompletions(key, openAiChatRequest);
    }

    if (chatResponse == null) {
      return null;
    }

    return fromClaudeResponse(uniChatRequest.getModel(), chatResponse);
  }

  private static OpenAiChatRequest toClaudeRequest(UniChatRequest uniChatRequest) {
    List<UniChatMessage> messages = uniChatRequest.getMessages();
    OpenAiChatRequest openAiChatRequest = new OpenAiChatRequest();
    if (uniChatRequest.isUseSystemPrompt()) {
      String platform = uniChatRequest.getPlatform();
      if (isAnthropic(platform)) {
        String systemPrompt = uniChatRequest.getSystemPrompt();
        if (systemPrompt != null) {
          ClaudeMessageContent claudeChatMessage = new ClaudeMessageContent("text", systemPrompt);
          if (uniChatRequest.isCacheSystemPrompt()) {
            claudeChatMessage.setCache_control(new ClaudeCacheControl());
          }
          openAiChatRequest.setSystemChatMessage(claudeChatMessage);
        }
      }
    }

    String model = uniChatRequest.getModel();
    if (model != null) {
      openAiChatRequest.setModel(model);
    }

    openAiChatRequest.setTemperature(uniChatRequest.getTemperature());
    List<OpenAiChatMessage> converted = new ArrayList<>();
    if (messages != null) {
      for (UniChatMessage message : messages) {
        OpenAiChatMessage copy = new OpenAiChatMessage(message, ModelPlatformName.ANTHROPIC);
        if ("model".equals(copy.getRole())) {
          copy.setRole("assistant");
        }
        converted.add(copy);
      }
    }
    openAiChatRequest.setMessages(converted);
    openAiChatRequest.setMax_tokens(uniChatRequest.getMax_tokens());

    return openAiChatRequest;
  }

  private static UniChatResponse fromClaudeResponse(String model, ClaudeChatResponse chatResponse) {
    String role = chatResponse.getRole();
    ChatResponseMessage message = new ChatResponseMessage(role);

    List<ClaudeMessageContent> contentList = chatResponse.getContent();
    for (ClaudeMessageContent claudeMessageContent : contentList) {
      if ("text".equals(claudeMessageContent.getType())) {
        message.setContent(claudeMessageContent.getText());
      }
//      else {
//        message.setReasoning(claudeMessageContent.getText());
//      }
    }
    ChatResponseUsage usage = chatResponse.getUsage() == null ? null : new ChatResponseUsage(chatResponse.getUsage());
    return new UniChatResponse(model, message, usage, chatResponse.getRawResponse());
  }

  public static UniChatResponse useGoogle(String key, UniChatRequest uniChatRequest) {
    String apiPrefixUrl = uniChatRequest.getApiPrefixUrl();

    return useGoogle(apiPrefixUrl, key, uniChatRequest);
  }

  public static UniChatResponse useGoogle(String apiPrefixUrl, String key, UniChatRequest uniChatRequest) {
    GeminiChatRequest geminiChatRequestVo = toGoogleRequest(uniChatRequest);
    GeminiChatResponse chatResponse = null;
    if (apiPrefixUrl != null) {
      chatResponse = GeminiClient.generate(apiPrefixUrl, key, uniChatRequest.getModel(), geminiChatRequestVo);
    } else {
      chatResponse = GeminiClient.generate(key, uniChatRequest.getModel(), geminiChatRequestVo);
    }

    if (chatResponse == null) {
      return null;
    }
    return fromGoogleResponse(chatResponse);
  }

  private static GeminiChatRequest toGoogleRequest(UniChatRequest uniChatRequest) {
    GeminiChatRequest geminiChatRequestVo = new GeminiChatRequest();
    if (uniChatRequest.getMessages() != null) {
      geminiChatRequestVo.setChatMessages(uniChatRequest.getMessages());
    }
    String cachedId = uniChatRequest.getCachedId();
    // CachedContent can not be used with GenerateContent request setting
    // system_instruction, tools or tool_config.
    // Proposed fix: move those values to CachedContent from GenerateContent request
    if (cachedId != null) {
      geminiChatRequestVo.setCachedContent(cachedId);
    } else if (uniChatRequest.isUseSystemPrompt()) {
      geminiChatRequestVo.setSystemPrompt(uniChatRequest.getSystemPrompt());
    }

    GeminiGenerationConfig config = new GeminiGenerationConfig();
    config.setMaxOutputTokens(uniChatRequest.getMax_tokens());

    Float temperature = uniChatRequest.getTemperature();
    if (temperature != null) {
      config.setTemperature(temperature);
    }

    Boolean enable_thinking = uniChatRequest.getEnable_thinking();
    if (enable_thinking != null && !enable_thinking) {
      UniThinkingConfig geminiThinkingConfig = new UniThinkingConfig(0);
      config.setThinkingConfig(geminiThinkingConfig);
    }

    String responseMimeType = uniChatRequest.getResponseFormat();
    if (responseMimeType != null) {
      config.setResponseMimeType(
          "json_object".equals(responseMimeType) ? ResponseMimeType.APPLICATION_JSON : responseMimeType);
    }
    List<String> responseModalities = uniChatRequest.getResponseModalities();
    config.setResponseModalities(responseModalities);

    UniResponseSchema responseSchema = uniChatRequest.getResponseSchema();
    if (responseSchema != null) {
      config.setResponseSchema(responseSchema);
      config.setResponseMimeType(ResponseMimeType.APPLICATION_JSON);
    }

    geminiChatRequestVo.setGenerationConfig(config);

    List<GeminiTool> tools = new ArrayList<>();
    Boolean enable_search = uniChatRequest.getEnable_search();
    if (enable_search != null && enable_search) {
      GeminiTool geminiToolVo = new GeminiTool();
      geminiToolVo.enableSearch();
      tools.add(geminiToolVo);
    }

    if (tools.size() > 0) {
      geminiChatRequestVo.setTools(tools);
    }

    return geminiChatRequestVo;
  }

  private static UniChatResponse fromGoogleResponse(GeminiChatResponse chatResponse) {
    GeminiUsageMetadata usageMetadata = chatResponse.getUsageMetadata();
    ChatResponseUsage usage = usageMetadata == null ? null : new ChatResponseUsage(usageMetadata);
    String modelVersion = chatResponse.getModelVersion();

    UniChatResponse uniChatResponse = new UniChatResponse();
    uniChatResponse.setUsage(usage);
    uniChatResponse.setRawData(chatResponse.getRawData());
    uniChatResponse.setModel(modelVersion);

    GeminiCandidate geminiCandidateVo = chatResponse.getCandidates().get(0);
    GeminiContentResponse content = geminiCandidateVo.getContent();

    String role = content.getRole();
    List<GeminiPart> parts = content.getParts();

    ChatResponseMessage message = new ChatResponseMessage(role, parts);
    GroundingMetadata groundingMetadata = geminiCandidateVo.getGroundingMetadata();
    if (groundingMetadata != null) {
      message.setUniSources(new UniSources(groundingMetadata));
    }

    uniChatResponse.setMessage(message);

    return uniChatResponse;
  }

  public static EventSource stream(UniChatRequest uniChatRequest, EventSourceListener listener) {
    return stream(uniChatRequest.getApiKey(), uniChatRequest, listener);
  }

  public static EventSource stream(UniChatRequest uniChatRequest, UniChatEventListener listener) {
    String platform = uniChatRequest.getPlatform();
    listener.setPlatform(platform);
    String apiKey = uniChatRequest.getApiKey();
    return stream(apiKey, uniChatRequest, listener);
  }

  public static EventSource stream(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {

    String platform = uniChatRequest.getPlatform();
    uniChatRequest.setStream(true);

    if (ModelPlatformName.GOOGLE.equals(platform)) {
      if (key == null) {
        key = GEMINI_API_KEY;
      }
      return useGoogle(key, uniChatRequest, listener);

    } else if (ModelPlatformName.VERTEX_AI.equals(platform)) {
      if (key == null) {
        key = VERTEX_AI_API_KEY;
      }
      return useVertexAi(key, uniChatRequest, listener);

    } else if (ModelPlatformName.EXCHANGE_TOKEN_GOOGLE.equals(platform)) {
      if (key == null) {
        key = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangeTokenGoogle(key, uniChatRequest, listener);

    } else if (ModelPlatformName.EXCHANGE_TOKEN_US_GOOGLE.equals(platform)) {
      if (key == null) {
        key = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangeTokenUsGoogle(key, uniChatRequest, listener);

    } else if (ModelPlatformName.ANTHROPIC.equals(platform)) {
      if (key == null) {
        key = CLAUDE_API_KEY;
      }
      return useClaude(key, uniChatRequest, listener);

    } else if (ModelPlatformName.OPENAI_RESPONSES.equals(platform)) {
      throw new UnsupportedOperationException("OpenAI Responses streaming is not supported by UniChatClient yet");

    } else if (ModelPlatformName.VOLC_ENGINE_RESPONSES.equals(platform)) {
      throw new UnsupportedOperationException("VolcEngine Responses streaming is not supported by UniChatClient yet");

    } else if (ModelPlatformName.EXCHANGE_TOKEN_ANTHROPIC.equals(platform)) {
      if (key == null) {
        key = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangeTokenClaude(key, uniChatRequest, listener);

    } else if (ModelPlatformName.EXCHANGE_TOKEN_US_ANTHROPIC.equals(platform)) {
      if (key == null) {
        key = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangeTokenUsClaude(key, uniChatRequest, listener);
    } else if (ModelPlatformName.VOLC_ENGINE.equals(platform)) {
      if (key == null) {
        key = VOLCENGINE_API_KEY;
      }
      Integer max_tokens = uniChatRequest.getMax_tokens();
      if (max_tokens == null) {
        uniChatRequest.setMax_tokens(16384);
      }
      return useVolcEngine(key, uniChatRequest, listener);

    } else if (ModelPlatformName.OPENROUTER.equals(platform)) {
      if (key == null) {
        key = OPENROUTER_API_KEY;
      }
      return useOpenRouter(key, uniChatRequest, listener);

    } else if (ModelPlatformName.ZENMUX.equals(platform)) {
      if (key == null) {
        key = ZENMUX_API_KEY;
      }
      return useZenmux(key, uniChatRequest, listener);

    } else if (ModelPlatformName.BAILIAN.equals(platform)) {
      if (key == null) {
        key = BAILIAN_API_KEY;
      }
      return useBailian(key, uniChatRequest, listener);

    } else if (ModelPlatformName.TENCENT.equals(platform)) {
      if (key == null) {
        key = TENCENT_API_KEY;
      }
      return useTencent(key, uniChatRequest, listener);

    } else if (ModelPlatformName.MINIMAX.equals(platform)) {
      if (key == null) {
        key = MINIMAX_API_KEY;
      }
      return useMiniMax(key, uniChatRequest, listener);

    } else if (ModelPlatformName.MOONSHOT.equals(platform)) {
      if (key == null) {
        key = MOONSHOT_API_KEY;
      }
      return useMoonshot(key, uniChatRequest, listener);

    } else if (ModelPlatformName.CEREBRAS.equals(platform)) {
      if (key == null) {
        key = CEREBRAS_API_KEY;
      }
      return useCerebras(key, uniChatRequest, listener);

    } else if (ModelPlatformName.OLLAMA.equals(platform)) {
      if (key == null) {
        key = OLLAMA_API_KEY;
      }
      return useOllama(key, uniChatRequest, listener);

    } else if (ModelPlatformName.LLAMACPP.equals(platform)) {
      if (key == null) {
        key = LLAMACPP_API_KEY;
      }
      return useLlamacpp(key, uniChatRequest, listener);

    } else if (ModelPlatformName.VLLM.equals(platform)) {
      if (key == null) {
        key = VLLM_API_KEY;
      }
      return useVllm(key, uniChatRequest, listener);

    } else if (ModelPlatformName.SWIFT.equals(platform)) {
      if (key == null) {
        key = SWIFT_API_KEY;
      }
      return useSwift(key, uniChatRequest, listener);

    } else if (ModelPlatformName.TITANIUM.equals(platform)) {
      if (key == null) {
        key = TITANIUM_API_KEY;
      }
      return useTitanium(key, uniChatRequest, listener);

    } else if (ModelPlatformName.GITEE.equals(platform)) {
      if (key == null) {
        key = GITEE_API_KEY;
      }
      return useGitee(key, uniChatRequest, listener);

    } else if (ModelPlatformName.LLM_PROXY.equals(platform)) {
      if (key == null) {
        key = LLM_PROXY_API_KEY;
      }
      return useLlmProxy(key, uniChatRequest, listener);

    } else if (ModelPlatformName.EXCHANGE_TOKEN.equals(platform)) {
      if (key == null) {
        key = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangetoken(key, uniChatRequest, listener);

    } else if (ModelPlatformName.EXCHANGE_TOKEN_US.equals(platform)) {
      if (key == null) {
        key = EXCHANGE_TOKEN_API_KEY;
      }
      return useExchangetokenUs(key, uniChatRequest, listener);

    } else if (ModelPlatformName.AIAPI.equals(platform)) {
      if (key == null) {
        key = AIAPI_API_KEY;
      }
      return useAiApi(key, uniChatRequest, listener);
    } else if (ModelPlatformName.DEEPSEEK.equals(platform)) {
      if (key == null) {
        key = DEEPSEEK_API_KEY;
      }
      return useDeepseek(key, uniChatRequest, listener);
    } else {
      if (key == null) {
        key = OPENAI_API_KEY;
      }
      return useOpenAi(key, uniChatRequest, listener);
    }
  }

  public static EventSource useOpenAi(String prefixUrl, String apiKey, UniChatRequest uniChatRequest,
      EventSourceListener listener) {
    OpenAiChatRequest openaiChatRequest = toOpenAiRequest(uniChatRequest);
    String apiPrefixUrl = uniChatRequest.getApiPrefixUrl();
    EventSource eventSource = null;
    if (apiPrefixUrl != null) {
      eventSource = OpenAiClient.chatCompletions(apiPrefixUrl, apiKey, openaiChatRequest, listener);
    } else {
      eventSource = OpenAiClient.chatCompletions(prefixUrl, apiKey, openaiChatRequest, listener);
    }
    return eventSource;
  }

  public static EventSource useClaude(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    String apiPrefixUrl = uniChatRequest.getApiPrefixUrl();

    return useClaude(apiPrefixUrl, key, uniChatRequest, listener);

  }

  public static EventSource useClaude(String apiPrefixUrl, String key, UniChatRequest uniChatRequest,
      EventSourceListener listener) {
    List<UniChatMessage> messages = uniChatRequest.getMessages();
    Iterator<UniChatMessage> iterator = messages.iterator();
    while (iterator.hasNext()) {
      UniChatMessage next = iterator.next();
      if (next.getRole().equals("model")) {
        // 'system', 'assistant', 'user', 'function', 'tool', and 'developer'.",
        next.setRole("assistant");
      }
    }

    OpenAiChatRequest openaiChatRequest = new OpenAiChatRequest();
    String platform = uniChatRequest.getPlatform();
    if (uniChatRequest.isUseSystemPrompt()) {
      String systemPrompt = uniChatRequest.getSystemPrompt();
      if (isAnthropic(platform) && systemPrompt != null) {
        ClaudeMessageContent claudeChatMessage = new ClaudeMessageContent("text", systemPrompt);
        if (uniChatRequest.isCacheSystemPrompt()) {
          claudeChatMessage.setCache_control(new ClaudeCacheControl());
        }
        openaiChatRequest.setSystemChatMessage(claudeChatMessage);
      }
    }

    openaiChatRequest.setModel(uniChatRequest.getModel());
    openaiChatRequest.setTemperature(uniChatRequest.getTemperature());
    openaiChatRequest.setChatMessages(messages, platform);
    openaiChatRequest.setMax_tokens(uniChatRequest.getMax_tokens());
    openaiChatRequest.setStream(uniChatRequest.getStream());

    EventSource eventSource = null;
    if (apiPrefixUrl != null) {
      eventSource = ClaudeClient.chatCompletions(apiPrefixUrl, key, openaiChatRequest, listener);
    } else {
      eventSource = ClaudeClient.chatCompletions(key, openaiChatRequest, listener);
    }

    return eventSource;
  }

  public static EventSource useGoogle(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    String apiPrefixUrl = uniChatRequest.getApiPrefixUrl();
    return useGoogle(apiPrefixUrl, key, uniChatRequest, listener);
  }

  private static EventSource useGoogle(String apiPrefixUrl, String key, UniChatRequest uniChatRequest,
      EventSourceListener listener) {
    GeminiGenerationConfig geminiGenerationConfigVo = new GeminiGenerationConfig();
    geminiGenerationConfigVo.setTemperature(uniChatRequest.getTemperature());

    GeminiChatRequest geminiChatRequestVo = new GeminiChatRequest();
    geminiChatRequestVo.setGenerationConfig(geminiGenerationConfigVo);
    geminiChatRequestVo.setSystemPrompt(uniChatRequest.getSystemPrompt());
    geminiChatRequestVo.setChatMessages(uniChatRequest.getMessages());
    geminiChatRequestVo.setCachedContent(uniChatRequest.getCachedId());

    EventSource eventSource = null;
    if (apiPrefixUrl != null) {
      eventSource = GeminiClient.stream(apiPrefixUrl, key, uniChatRequest.getModel(), geminiChatRequestVo, listener);
    } else {
      eventSource = GeminiClient.stream(key, uniChatRequest.getModel(), geminiChatRequestVo, listener);
    }
    return eventSource;
  }

  public static EventSource useVolcEngine(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(VOLCENGINE_API_URL, key, uniChatRequest, listener);
  }

  public static EventSource useOpenRouter(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(OPENROUTER_API_URL, key, uniChatRequest, listener);
  }

  public static EventSource useZenmux(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(ZENMUX_API_URL, key, uniChatRequest, listener);
  }

  public static EventSource useOpenAi(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(OPENAI_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useOpenAi(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(OPENAI_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useOpenAiResponses(String key, UniChatRequest uniChatRequest) {
    String apiPrefixUrl = uniChatRequest.getApiPrefixUrl();
    if (apiPrefixUrl == null) {
      apiPrefixUrl = OPENAI_RESPONSES_API_URL;
    }
    return useOpenAiResponses(apiPrefixUrl, key, uniChatRequest);
  }

  public static UniChatResponse useVolcEngineResponses(String key, UniChatRequest uniChatRequest) {
    String apiPrefixUrl = uniChatRequest.getApiPrefixUrl();
    if (apiPrefixUrl == null) {
      apiPrefixUrl = VOLCENGINE_RESPONSES_API_URL;
    }
    return useOpenAiResponses(apiPrefixUrl, key, uniChatRequest);
  }

  public static UniChatResponse useOpenAiResponses(String apiPrefixUrl, String key, UniChatRequest uniChatRequest) {
    OpenAiResponsesRequest request = toOpenAiResponsesRequest(uniChatRequest);
    OpenAiResponsesResponse response = OpenAiResponsesClient.responses(apiPrefixUrl, key, request);
    if (response == null) {
      return null;
    }

    return fromOpenAiResponsesResponse(response);
  }

  private static UniChatResponse fromOpenAiResponsesResponse(OpenAiResponsesResponse response) {
    String content = response.getOutputText();
    ChatResponseMessage message = new ChatResponseMessage("assistant", content);
    ChatResponseUsage usage = toChatResponseUsage(response.getUsage());
    return new UniChatResponse(response.getModel(), message, usage, response.getRawResponse());
  }

  public static OpenAiResponsesRequest toOpenAiResponsesRequest(UniChatRequest uniChatRequest) {
    List<OpenAiResponsesInput> inputs = new ArrayList<>();
    if (uniChatRequest.isUseSystemPrompt() && StrUtil.isNotBlank(uniChatRequest.getSystemPrompt())) {
      inputs.add(new OpenAiResponsesInput("system", singleResponsesTextContent(uniChatRequest.getSystemPrompt())));
    }

    List<UniChatMessage> messages = uniChatRequest.getMessages();
    if (messages != null && messages.size() > 0) {
      for (UniChatMessage message : messages) {
        if (message == null) {
          continue;
        }
        String role = message.getRole();
        if ("model".equals(role)) {
          role = "assistant";
        }
        if (StrUtil.isBlank(role)) {
          role = "user";
        }

        List<OpenAiResponsesInputContent> contents = new ArrayList<>();
        if (StrUtil.isNotBlank(message.getContent())) {
          contents.add(OpenAiResponsesInputContent.text(message.getContent()));
        }

        List<ChatImageFile> files = message.getFiles();
        if (files != null && files.size() > 0) {
          for (ChatImageFile file : files) {
            String imageUrl = toResponsesImageUrl(file);
            if (StrUtil.isNotBlank(imageUrl)) {
              contents.add(OpenAiResponsesInputContent.imageUrl(imageUrl));
            }
          }
        }

        if (!contents.isEmpty()) {
          inputs.add(new OpenAiResponsesInput(role, contents));
        }
      }
    }

    OpenAiResponsesRequest request = new OpenAiResponsesRequest();
    request.setModel(uniChatRequest.getModel());
    request.setInput(inputs);
    request.setTemperature(uniChatRequest.getTemperature());
    request.setMax_output_tokens(uniChatRequest.getMax_tokens());
    return request;
  }

  private static List<OpenAiResponsesInputContent> singleResponsesTextContent(String text) {
    List<OpenAiResponsesInputContent> contents = new ArrayList<>(1);
    contents.add(OpenAiResponsesInputContent.text(text));
    return contents;
  }

  private static String toResponsesImageUrl(ChatImageFile file) {
    if (file == null) {
      return null;
    }
    if (StrUtil.isNotBlank(file.getUrl())) {
      return file.getUrl();
    }
    String data = file.getData();
    if (StrUtil.isBlank(data)) {
      return null;
    }
    if (data.startsWith("data:")) {
      return data;
    }
    String mimeType = file.getMimeType();
    if (StrUtil.isBlank(mimeType)) {
      mimeType = "image/png";
    }
    return "data:" + mimeType + ";base64," + data;
  }

  private static ChatResponseUsage toChatResponseUsage(OpenAiResponsesUsage usage) {
    if (usage == null) {
      return null;
    }
    ChatResponseUsage chatUsage = new ChatResponseUsage();
    chatUsage.setPrompt_tokens(usage.getInput_tokens());
    chatUsage.setCompletion_tokens(usage.getOutput_tokens());
    chatUsage.setTotal_tokens(usage.getTotal_tokens());
    return chatUsage;
  }

  public static UniChatResponse useVolcEngine(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(VOLCENGINE_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useOpenRouter(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(OPENROUTER_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useOpenRouter(String prefixUrl, String key, UniChatRequest uniChatRequest) {
    if (prefixUrl != null) {
      return useOpenAi(prefixUrl, key, uniChatRequest);
    } else {
      return useOpenAi(OPENROUTER_API_URL, key, uniChatRequest);
    }

  }

  public static UniChatResponse useZenmux(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(ZENMUX_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useBailian(String key, UniChatRequest uniChatRequest) {
    uniChatRequest.setEnable_thinking(false);
    return useOpenAi(BAILIAN_API_URL, key, uniChatRequest);
  }

  public static EventSource useBailian(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(BAILIAN_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useTencent(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(TENCENT_API_URL, key, uniChatRequest);
  }

  public static EventSource useTencent(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(TENCENT_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useMiniMax(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(MINIMAX_API_URL, key, uniChatRequest);
  }

  public static EventSource useMiniMax(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(MINIMAX_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useMoonshot(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(MOONSHOT_API_URL, key, uniChatRequest);
  }

  public static EventSource useMoonshot(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(MOONSHOT_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useCerebras(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(CEREBRAS_API_URL, key, uniChatRequest);
  }

  public static EventSource useCerebras(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(CEREBRAS_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useOllama(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(OLLAMA_API_URL, key, uniChatRequest);
  }

  public static EventSource useOllama(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(OLLAMA_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useLlamacpp(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(LLAMACPP_API_URL, key, uniChatRequest);
  }

  public static EventSource useLlamacpp(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(LLAMACPP_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useVllm(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(VLLM_API_URL, key, uniChatRequest);
  }

  public static EventSource useVllm(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(VLLM_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useSwift(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(SWIFT_API_URL, key, uniChatRequest);
  }

  public static EventSource useSwift(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(SWIFT_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useGitee(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(GITEE_API_URL, key, uniChatRequest);
  }

  public static EventSource useGitee(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(GITEE_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useLlmProxy(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(LLM_PROXY_API_URL, key, uniChatRequest);
  }

  public static EventSource useLlmProxy(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(LLM_PROXY_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useTitanium(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(TITANIUM_API_URL, key, uniChatRequest);
  }

  public static EventSource useTitanium(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(TITANIUM_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useAiApi(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(AIAPI_API_URL, key, uniChatRequest);
  }

  public static EventSource useAiApi(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(AIAPI_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useDeepseek(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(DEEPSEEK_API_URL, key, uniChatRequest);
  }

  private static EventSource useDeepseek(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(DEEPSEEK_API_URL, key, uniChatRequest, listener);
  }

  public static ChatModelResponse models(String platform) {
    return models(platform, null);

  }

  public static ChatModelResponse models(String platform, String key) {

//    if (ModelPlatformName.GOOGLE.equals(platform)) {
//      if (key == null) {
//        key = GEMINI_API_KEY;
//      }
//      return getModels(GEMINI_API_,key);
//    } else if (ModelPlatformName.EXCHANGE_TOKEN_GOOGLE.equals(platform)) {
//      if (key == null) {
//        key = EXCHANGE_TOKEN_API_KEY;
//      }
//      return useExchangeTokenGoogle(key, uniChatRequest);
//    } else if (ModelPlatformName.ANTHROPIC.equals(platform)) {
//      if (key == null) {
//        key = CLAUDE_API_KEY;
//      }
//      return useClaude(key, uniChatRequest);
//
//    } else 
    if (ModelPlatformName.VOLC_ENGINE.equals(platform)) {
      if (key == null) {
        key = VOLCENGINE_API_KEY;
      }

      return getModels(VOLCENGINE_API_URL, key);

    } else if (ModelPlatformName.OPENROUTER.equals(platform)) {
      if (key == null) {
        key = OPENROUTER_API_KEY;
      }
      return getModels(OPENROUTER_API_URL, key);

    } else if (ModelPlatformName.ZENMUX.equals(platform)) {
      if (key == null) {
        key = ZENMUX_API_KEY;
      }
      return getModels(ZENMUX_API_URL, key);

    } else if (ModelPlatformName.BAILIAN.equals(platform)) {
      if (key == null) {
        key = BAILIAN_API_KEY;
      }
      return getModels(BAILIAN_API_URL, key);

    } else if (ModelPlatformName.TENCENT.equals(platform)) {
      if (key == null) {
        key = TENCENT_API_KEY;
      }
      return getModels(TENCENT_API_URL, key);

    } else if (ModelPlatformName.MINIMAX.equals(platform)) {
      if (key == null) {
        key = MINIMAX_API_KEY;
      }
      return getModels(MINIMAX_API_URL, key);

    } else if (ModelPlatformName.MOONSHOT.equals(platform)) {
      if (key == null) {
        key = MOONSHOT_API_KEY;
      }
      return getModels(MOONSHOT_API_URL, key);

    } else if (ModelPlatformName.CEREBRAS.equals(platform)) {
      if (key == null) {
        key = CEREBRAS_API_KEY;
      }
      return getModels(CEREBRAS_API_URL, key);

    } else if (ModelPlatformName.OLLAMA.equals(platform)) {
      if (key == null) {
        key = OLLAMA_API_KEY;
      }
      return getModels(OLLAMA_API_URL, key);

    } else if (ModelPlatformName.LLAMACPP.equals(platform)) {
      if (key == null) {
        key = LLAMACPP_API_KEY;
      }
      return getModels(LLAMACPP_API_URL, key);

    } else if (ModelPlatformName.VLLM.equals(platform)) {
      if (key == null) {
        key = VLLM_API_KEY;
      }
      return getModels(VLLM_API_URL, key);

    } else if (ModelPlatformName.SWIFT.equals(platform)) {
      if (key == null) {
        key = SWIFT_API_KEY;
      }
      return getModels(SWIFT_API_URL, key);

    } else if (ModelPlatformName.TITANIUM.equals(platform)) {
      if (key == null) {
        key = TITANIUM_API_KEY;
      }
      return getModels(TITANIUM_API_URL, key);

    } else if (ModelPlatformName.GITEE.equals(platform)) {
      if (key == null) {
        key = GITEE_API_KEY;
      }
      return getModels(GITEE_API_URL, key);

    } else if (ModelPlatformName.LLM_PROXY.equals(platform)) {
      if (key == null) {
        key = LLM_PROXY_API_KEY;
      }
      return getModels(LLM_PROXY_API_URL, key);

    } else if (ModelPlatformName.EXCHANGE_TOKEN.equals(platform)) {
      if (key == null) {
        key = EXCHANGE_TOKEN_API_KEY;
      }
      return getModels(EXCHANGE_TOKEN_API_URL, key);

    } else if (ModelPlatformName.EXCHANGE_TOKEN_US.equals(platform)) {
      if (key == null) {
        key = EXCHANGE_TOKEN_API_KEY;
      }
      return getModels(EXCHANGE_TOKEN_US_API_URL, key);

    } else if (ModelPlatformName.AIAPI.equals(platform)) {
      if (key == null) {
        key = AIAPI_API_KEY;
      }
      return getModels(AIAPI_API_URL, key);

    } else if (ModelPlatformName.DEEPSEEK.equals(platform)) {
      if (key == null) {
        key = DEEPSEEK_API_KEY;
      }
      return getModels(DEEPSEEK_API_URL, key);

    } else {
      if (key == null) {
        key = OPENAI_API_KEY;
      }
      return getModels(OPENAI_API_URL, key);
    }
  }

  // useExchangetoken
  public static UniChatResponse useExchangetoken(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(EXCHANGE_TOKEN_API_URL, key, uniChatRequest);
  }

  public static EventSource useExchangetoken(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useOpenAi(EXCHANGE_TOKEN_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useExchangetokenUs(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(EXCHANGE_TOKEN_US_API_URL, key, uniChatRequest);
  }

  public static EventSource useExchangetokenUs(String key, UniChatRequest uniChatRequest,
      EventSourceListener listener) {
    return useOpenAi(EXCHANGE_TOKEN_API_URL, key, uniChatRequest, listener);
  }

  // ExchangeTokenGoogle
  public static UniChatResponse useExchangeTokenGoogle(String key, UniChatRequest uniChatRequest) {
    return useGoogle(EXCHANGE_TOKEN_GOOGLE_API_URL, key, uniChatRequest);
  }

  public static EventSource useExchangeTokenGoogle(String key, UniChatRequest uniChatRequest,
      EventSourceListener listener) {
    return useGoogle(EXCHANGE_TOKEN_GOOGLE_API_URL, key, uniChatRequest, listener);
  }

  public static EventSource useExchangeTokenUsGoogle(String key, UniChatRequest uniChatRequest,
      EventSourceListener listener) {
    return useGoogle(EXCHANGE_TOKEN_US_GOOGLE_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useExchangeTokenUsGoogle(String key, UniChatRequest uniChatRequest) {
    return useGoogle(EXCHANGE_TOKEN_US_GOOGLE_API_URL, key, uniChatRequest);
  }

  // ExchangeTokenClaude
  public static UniChatResponse useExchangeTokenClaude(String key, UniChatRequest uniChatRequest) {
    return useClaude(EXCHANGE_TOKEN_API_URL, key, uniChatRequest);
  }

  public static EventSource useExchangeTokenClaude(String key, UniChatRequest uniChatRequest,
      EventSourceListener listener) {
    return useClaude(EXCHANGE_TOKEN_API_URL, key, uniChatRequest, listener);
  }

  public static UniChatResponse useExchangeTokenUsClaude(String key, UniChatRequest uniChatRequest) {
    return useClaude(EXCHANGE_TOKEN_US_API_URL, key, uniChatRequest);
  }

  public static EventSource useExchangeTokenUsClaude(String key, UniChatRequest uniChatRequest,
      EventSourceListener listener) {
    return useClaude(EXCHANGE_TOKEN_US_API_URL, key, uniChatRequest, listener);
  }

  // VERTEX_AI
  private static UniChatResponse useVertexAi(String key, UniChatRequest uniChatRequest) {
    return useGoogle(VERTEX_AI_API_URL, key, uniChatRequest);
  }

  private static EventSource useVertexAi(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    return useGoogle(VERTEX_AI_API_URL, key, uniChatRequest, listener);
  }

}
