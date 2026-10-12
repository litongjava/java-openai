package nexus.io.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import nexus.io.claude.AnthropicModels;
import nexus.io.claude.ClaudeCacheControl;
import nexus.io.claude.ClaudeChatResponse;
import nexus.io.claude.ClaudeConsts;
import nexus.io.claude.ClaudeMessageContent;
import nexus.io.claude.ClaudeWebSearchCitation;
import nexus.io.claude.ClaudeWebSearchError;
import nexus.io.claude.ClaudeWebSearchResult;
import nexus.io.claude.ClaudeWebSearchTool;
import nexus.io.consts.ModelPlatformName;
import nexus.io.deepseek.DeepSeekConst;
import nexus.io.deepseek.DeepSeekModels;
import nexus.io.exchangetoken.ExchangetokenConst;
import nexus.io.openai.chat.ChatResponseMessage;
import nexus.io.openai.chat.ChatResponseUsage;
import nexus.io.openai.chat.OpenAiChatMessage;
import nexus.io.openai.chat.OpenAiChatRequest;
import nexus.io.openai.consts.OpenAiConst;
import nexus.io.openai.consts.OpenAiModels;
import nexus.io.openai.responses.OpenAiResponsesOutput;
import nexus.io.openai.responses.OpenAiResponsesOutputContent;
import nexus.io.openai.responses.OpenAiResponsesRequest;
import nexus.io.openai.responses.OpenAiResponsesResponse;
import nexus.io.openai.responses.OpenAiUrlCitation;
import nexus.io.openai.responses.OpenAiWebSearchAction;
import nexus.io.openai.responses.OpenAiWebSearchTool;
import nexus.io.tio.utils.environment.EnvUtils;
import nexus.io.tio.utils.hutool.StrUtil;

/**
 * Protocol adaptation for UniChatClient's synchronous hosted-search entry
 * points.
 */
final class UniWebSearchAdapter {
  private UniWebSearchAdapter() {
  }

  static final class Prepared {
    String url;
    String model;
    boolean openai;
    Object payload;
    Map<String, String> headers = new LinkedHashMap<>();
  }

  static boolean enabled(UniChatRequest request) {
    if (request == null || !Boolean.TRUE.equals(request.getEnable_search())) {
      return false;
    }
    String platform = normalizePlatform(request.getPlatform());
    return platform == null || ModelPlatformName.AUTO.equals(platform) || supported(platform);
  }

  static Prepared prepare(String key, UniChatRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("Request is required");
    }
    if (Boolean.TRUE.equals(request.getStream())) {
      throw new IllegalArgumentException(
          "webSearch is synchronous; use a native EventSourceListener overload for streaming");
    }
    if (request.getMessages() == null || request.getMessages().isEmpty()) {
      throw new IllegalArgumentException("Web search requires messages");
    }
    if (request.getTools() != null && !request.getTools().isEmpty()) {
      throw new IllegalArgumentException(
          "Unified webSearch supplies its own tool; use native clients to combine custom tools");
    }
    boolean hasMessage = false;
    for (UniChatMessage message : request.getMessages()) {
      if (message == null)
        continue;
      String role = message.getRole();
      if (message.getToolCalls() != null || message.getToolCallId() != null
          || !(StrUtil.isBlank(role) || "user".equals(role) || "assistant".equals(role) || "model".equals(role)
              || "system".equals(role) || "developer".equals(role))) {
        throw new IllegalArgumentException(
            "Unified webSearch requires user/assistant history; use native clients for tool history");
      }
      if (!"system".equals(role) && !"developer".equals(role) && (StrUtil.isNotBlank(message.getContent())
          || (message.getFiles() != null && !message.getFiles().isEmpty()))) {
        hasMessage = true;
      }
    }
    if (!hasMessage) {
      throw new IllegalArgumentException("Web search requires a non-empty user/assistant message");
    }
    String model = StrUtil.isBlank(request.getModel()) ? null : request.getModel().trim();
    String family = modelFamily(model);
    String platform = normalizePlatform(request.getPlatform());
    if (platform == null || ModelPlatformName.AUTO.equals(platform)) {
      platform = model == null ? ModelPlatformName.OPENAI : family;
      if (platform == null) {
        throw new IllegalArgumentException("Cannot infer the web search platform; specify platform for model " + model);
      }
    }
    if (!supported(platform)) {
      throw new IllegalArgumentException("Unified web search does not support platform " + platform);
    }
    boolean openai = ModelPlatformName.OPENAI.equals(platform) || ModelPlatformName.OPENAI_RESPONSES.equals(platform);
    boolean deepseek = ModelPlatformName.DEEPSEEK.equals(platform);
    String expectedFamily = openai ? ModelPlatformName.OPENAI
        : deepseek ? ModelPlatformName.DEEPSEEK : ModelPlatformName.ANTHROPIC;
    if (family != null && !expectedFamily.equals(family)) {
      throw new IllegalArgumentException("Model " + model + " does not match search platform " + platform);
    }
    if (model == null) {
      model = openai ? OpenAiModels.GPT_5_4
          : deepseek ? DeepSeekModels.deepseek_v4_flash : AnthropicModels.CLAUDE_SONNET_4_6;
    } else if (model.startsWith(expectedFamily + "/")) {
      model = model.substring(expectedFamily.length() + 1);
    }

    UniWebSearchOptions options = request.getWebSearchOptions() == null ? new UniWebSearchOptions()
        : request.getWebSearchOptions();
    validateOptions(options, openai);
    if (request.getMax_tokens() != null && request.getMax_tokens() < (openai ? 16 : 1)) {
      throw new IllegalArgumentException("max_tokens must be at least " + (openai ? 16 : 1));
    }
    Prepared prepared = new Prepared();
    prepared.openai = openai;
    prepared.model = model;
    String prefix = request.getApiPrefixUrl();
    String envKey;
    if (openai) {
      boolean responses = ModelPlatformName.OPENAI_RESPONSES.equals(platform);
      if (StrUtil.isBlank(prefix)) {
        prefix = responses ? setting("OPENAI_RESPONSES_API_URL", setting("OPENAI_API_URL", OpenAiConst.API_PREFIX_URL))
            : setting("OPENAI_API_URL", OpenAiConst.API_PREFIX_URL);
      }
      envKey = responses ? setting("OPENAI_RESPONSES_API_KEY", EnvUtils.get("OPENAI_API_KEY"))
          : EnvUtils.get("OPENAI_API_KEY");
    } else if (deepseek) {
      if (StrUtil.isBlank(prefix)) {
        prefix = setting("DEEPSEEK_SEARCH_BASE_URL", DeepSeekConst.ANTHROPIC_API_PREFIX_URL);
      }
      envKey = EnvUtils.get("DEEPSEEK_API_KEY");
    } else {
      String urlName = "CLAUDE_API_URL";
      String defaultUrl = ClaudeConsts.API_PREFIX_URL;
      String keyName = "CLAUDE_API_KEY";
      if (ModelPlatformName.EXCHANGE_TOKEN_ANTHROPIC.equals(platform)) {
        urlName = "EXCHANGE_TOKEN_API_URL";
        defaultUrl = ExchangetokenConst.BASE_URL;
        keyName = "EXCHANGE_TOKEN_API_KEY";
      } else if (ModelPlatformName.EXCHANGE_TOKEN_US_ANTHROPIC.equals(platform)) {
        urlName = "EXCHANGE_TOKEN_US_API_URL";
        defaultUrl = ExchangetokenConst.US_BASE_URL;
        keyName = "EXCHANGE_TOKEN_API_KEY";
      }
      if (StrUtil.isBlank(prefix)) {
        prefix = setting(urlName, defaultUrl);
      }
      envKey = EnvUtils.get(keyName);
    }
    key = StrUtil.isNotBlank(key) ? key : StrUtil.isNotBlank(request.getApiKey()) ? request.getApiKey() : envKey;
    if (StrUtil.isBlank(key)) {
      throw new IllegalArgumentException("API key is required for search platform " + platform);
    }
    prefix = prefix.replaceAll("/+$", "");
    // Only normalize official DeepSeek chat prefixes; custom compatible gateways
    // keep their paths.
    if (deepseek && (DeepSeekConst.BASE_URL.equals(prefix) || DeepSeekConst.API_PREFIX_URL.equals(prefix))) {
      prefix = DeepSeekConst.ANTHROPIC_API_PREFIX_URL;
    }
    prepared.url = prefix + (openai ? "/responses" : "/messages");
    if (openai || deepseek) {
      prepared.headers.put("Authorization", "Bearer " + key);
    }
    if (!openai) {
      prepared.headers.put("x-api-key", key);
      prepared.headers.put("anthropic-version", "2023-06-01");
    }
    prepared.payload = openai ? responsesRequest(request, model, options)
        : messagesRequest(request, model, options, deepseek);
    return prepared;
  }

  private static Object responsesRequest(UniChatRequest request, String model, UniWebSearchOptions options) {
    OpenAiWebSearchTool tool = new OpenAiWebSearchTool().setUser_location(options.getUserLocation())
        .setSearch_context_size(options.getSearchContextSize()).setExternal_web_access(options.getExternalWebAccess());
    if (options.getAllowedDomains() != null || options.getBlockedDomains() != null) {
      tool.setFilters(new OpenAiWebSearchTool.Filters().setAllowed_domains(options.getAllowedDomains())
          .setBlocked_domains(options.getBlockedDomains()));
    }
    OpenAiResponsesRequest payload = UniChatClient.toOpenAiResponsesRequest(request).setModel(model)
        .setTools(Collections.singletonList(tool)).setTool_choice("required")
        .setInclude(Collections.singletonList("web_search_call.action.sources"));
    // Normalize model parameters before converting to an extensible Responses
    // payload.
    JSONObject body = JSON.parseObject(ChatRequestUtils.toSkipNullJson(payload));
    if (options.getMaxToolCalls() != null) {
      body.put("max_tool_calls", options.getMaxToolCalls());
    }
    return body;
  }

  private static OpenAiChatRequest messagesRequest(UniChatRequest request, String model, UniWebSearchOptions options,
      boolean deepseek) {
    List<ClaudeMessageContent> system = new ArrayList<>();
    system.add(new ClaudeMessageContent("text", "Use web_search to answer the user's request and cite your sources."));
    if (request.isUseSystemPrompt() && StrUtil.isNotBlank(request.getSystemPrompt())) {
      ClaudeMessageContent block = new ClaudeMessageContent("text", request.getSystemPrompt());
      if (request.isCacheSystemPrompt()) {
        block.setCache_control(new ClaudeCacheControl());
      }
      system.add(block);
    }
    List<OpenAiChatMessage> messages = new ArrayList<>();
    for (UniChatMessage message : request.getMessages()) {
      if (message == null) {
        continue;
      }
      String role = message.getRole();
      if ("system".equals(role) || "developer".equals(role)) {
        if (request.isUseSystemPrompt() && StrUtil.isNotBlank(message.getContent())) {
          system.add(new ClaudeMessageContent("text", message.getContent()));
        }
        continue;
      }
      OpenAiChatMessage copy = new OpenAiChatMessage(message, ModelPlatformName.ANTHROPIC);
      copy.setRole("model".equals(role) ? "assistant" : StrUtil.isBlank(role) ? "user" : role);
      messages.add(copy);
    }
    if (messages.isEmpty()) {
      throw new IllegalArgumentException("Web search requires a non-system message");
    }
    ClaudeWebSearchTool tool = new ClaudeWebSearchTool().setMax_uses(options.getMaxToolCalls())
        .setAllowed_domains(options.getAllowedDomains()).setBlocked_domains(options.getBlockedDomains())
        .setUser_location(options.getUserLocation());
    OpenAiChatRequest payload = new OpenAiChatRequest().setModel(model).setMessages(messages).setSystem(system)
        .setTools(Collections.singletonList(tool))
        .setMax_tokens(request.getMax_tokens() == null ? 4096 : request.getMax_tokens())
        .setTemperature(request.getTemperature()).setStream(false);
    if (request.getThinking() != null) {
      payload.setThinking(new LinkedHashMap<>(request.getThinking()));
    } else if (deepseek && request.getEnable_thinking() != null) {
      payload.setThinking(Collections.singletonMap("type", request.getEnable_thinking() ? "enabled" : "disabled"));
    }
    return payload;
  }

  private static void validateOptions(UniWebSearchOptions options, boolean openai) {
    if (options.getAllowedDomains() != null && options.getBlockedDomains() != null) {
      throw new IllegalArgumentException("Use allowedDomains or blockedDomains, not both");
    }
    if (options.getMaxToolCalls() != null && options.getMaxToolCalls() < 1) {
      throw new IllegalArgumentException("maxToolCalls must be positive");
    }
    String size = options.getSearchContextSize();
    if (size != null && !"low".equals(size) && !"medium".equals(size) && !"high".equals(size)) {
      throw new IllegalArgumentException("searchContextSize must be low, medium, or high");
    }
    if (!openai && (size != null || options.getExternalWebAccess() != null)) {
      throw new IllegalArgumentException("searchContextSize and externalWebAccess are supported only by OpenAI");
    }
  }

  private static String normalizePlatform(String platform) {
    if (StrUtil.isBlank(platform)) {
      return null;
    }
    platform = platform.trim().toLowerCase(Locale.ROOT);
    return "claude".equals(platform) ? ModelPlatformName.ANTHROPIC : platform;
  }

  private static boolean supported(String platform) {
    return ModelPlatformName.OPENAI.equals(platform) || ModelPlatformName.OPENAI_RESPONSES.equals(platform)
        || ModelPlatformName.DEEPSEEK.equals(platform) || UniChatClient.isAnthropic(platform);
  }

  private static String modelFamily(String model) {
    if (model == null) {
      return null;
    }
    String name = model.toLowerCase(Locale.ROOT);
    if (name.startsWith("openai/") || name.startsWith("gpt-") || name.matches("o[134](?:-.*)?")) {
      return ModelPlatformName.OPENAI;
    }
    if (name.startsWith("anthropic/") || name.startsWith("claude-")) {
      return ModelPlatformName.ANTHROPIC;
    }
    return name.startsWith("deepseek/") || name.startsWith("deepseek-") ? ModelPlatformName.DEEPSEEK : null;
  }

  private static String setting(String name, String fallback) {
    String value = EnvUtils.get(name);
    return StrUtil.isBlank(value) ? fallback : value;
  }

  static UniChatResponse parse(Prepared prepared, String raw) throws IOException {
    try {
      JSONObject root = JSON.parseObject(raw);
      if (root == null || !(root.get(prepared.openai ? "output" : "content") instanceof List)) {
        throw new IOException("Web search response has no " + (prepared.openai ? "output" : "content") + " array");
      }
      UniChatResponse result = new UniChatResponse();
      result.setRawData(raw);
      result.setModel(StrUtil.isBlank(root.getString("model")) ? prepared.model : root.getString("model"));
      Map<String, UniWebSearchSource> sources = new LinkedHashMap<>();
      Set<String> citations = new LinkedHashSet<>();
      List<String> errors = new ArrayList<>();
      String text;
      if (prepared.openai) {
        OpenAiResponsesResponse response = JSON.parseObject(raw, OpenAiResponsesResponse.class);
        text = response.getOutputText();
        result.setFinishReason(response.getStatus());
        if (response.getUsage() != null) {
          ChatResponseUsage usage = new ChatResponseUsage();
          usage.setPrompt_tokens(response.getUsage().getInput_tokens());
          usage.setCompletion_tokens(response.getUsage().getOutput_tokens());
          usage.setTotal_tokens(response.getUsage().getTotal_tokens());
          result.setUsage(usage);
        }
        for (OpenAiResponsesOutput output : response.getOutput()) {
          if (output == null)
            continue;
          if (output.getAction() != null && output.getAction().getSources() != null) {
            for (OpenAiWebSearchAction.Source source : output.getAction().getSources()) {
              if (source != null)
                addSource(sources, source.getUrl(), source.getTitle());
            }
          }
          if ("web_search_call".equals(output.getType()) && "failed".equals(output.getStatus())) {
            errors.add("web_search_call_failed");
          }
          if (output.getContent() == null)
            continue;
          for (OpenAiResponsesOutputContent block : output.getContent()) {
            if (block == null || block.getAnnotations() == null)
              continue;
            for (OpenAiUrlCitation citation : block.getAnnotations()) {
              if (citation != null && "url_citation".equals(citation.getType())
                  && StrUtil.isNotBlank(citation.getUrl())) {
                citations.add(citation.getUrl());
                addSource(sources, citation.getUrl(), citation.getTitle());
              }
            }
          }
        }
        if (root.getJSONObject("error") != null) {
          JSONObject error = root.getJSONObject("error");
          errors.add(error.getString("code") == null ? "response_failed" : error.getString("code"));
        }
      } else {
        ClaudeChatResponse response = JSON.parseObject(raw, ClaudeChatResponse.class);
        text = response.getOutputText();
        result.setFinishReason(response.getStop_reason());
        if (response.getUsage() != null) {
          result.setUsage(new ChatResponseUsage(response.getUsage()));
          if (response.getUsage().getServer_tool_use() != null) {
            result.setWebSearchRequests(response.getUsage().getServer_tool_use().get("web_search_requests"));
          }
        }
        for (ClaudeMessageContent block : response.getContent()) {
          if (block == null)
            continue;
          ClaudeWebSearchError error = block.webSearchError();
          if (error != null)
            errors.add(error.getError_code());
          for (ClaudeWebSearchResult source : block.webSearchResults()) {
            addSource(sources, source.getUrl(), source.getTitle());
          }
          if (block.getCitations() == null)
            continue;
          for (ClaudeWebSearchCitation citation : block.getCitations()) {
            if (citation != null && StrUtil.isNotBlank(citation.getUrl())) {
              citations.add(citation.getUrl());
              addSource(sources, citation.getUrl(), citation.getTitle());
            }
          }
        }
      }
      result.setMessage(new ChatResponseMessage("assistant", text));
      result.setCitations(new ArrayList<>(citations));
      result.setSearchSources(new ArrayList<>(sources.values()));
      result.setSearchErrors(errors);
      return result;
    } catch (RuntimeException e) {
      throw new IOException("Web search provider response is not valid for the selected protocol");
    }
  }

  private static void addSource(Map<String, UniWebSearchSource> sources, String url, String title) {
    if (StrUtil.isBlank(url))
      return;
    UniWebSearchSource existing = sources.get(url);
    if (existing == null) {
      sources.put(url, new UniWebSearchSource(url, title));
    } else if (StrUtil.isBlank(existing.getTitle()) && StrUtil.isNotBlank(title)) {
      existing.setTitle(title);
    }
  }
}
