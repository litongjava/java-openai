package com.litongjava.chat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.litongjava.bailian.BaiLianConst;
import com.litongjava.cerebras.CerebrasConst;
import com.litongjava.claude.ClaudeCacheControl;
import com.litongjava.claude.ClaudeChatResponseVo;
import com.litongjava.claude.ClaudeClient;
import com.litongjava.claude.ClaudeMessageContent;
import com.litongjava.consts.ModelPlatformName;
import com.litongjava.gemini.GeminiCandidateVo;
import com.litongjava.gemini.GeminiChatRequestVo;
import com.litongjava.gemini.GeminiChatResponseVo;
import com.litongjava.gemini.GeminiClient;
import com.litongjava.gemini.GeminiContentResponseVo;
import com.litongjava.gemini.GeminiGenerationConfig;
import com.litongjava.gemini.GeminiPartVo;
import com.litongjava.gemini.GeminiToolVo;
import com.litongjava.gemini.GeminiUsageMetadataVo;
import com.litongjava.gemini.GroundingMetadata;
import com.litongjava.minimax.MiniMaxConst;
import com.litongjava.moonshot.MoonshotConst;
import com.litongjava.openai.ChatProvider;
import com.litongjava.openai.chat.ChatMessageContent;
import com.litongjava.openai.chat.ChatRequestImage;
import com.litongjava.openai.chat.ChatResponseMessage;
import com.litongjava.openai.chat.ChatResponseUsage;
import com.litongjava.openai.chat.Choice;
import com.litongjava.openai.chat.OpenAiChatMessage;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.chat.OpenAiChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.openai.consts.OpenAiConstants;
import com.litongjava.openrouter.OpenRouterConst;
import com.litongjava.tencent.TencentConst;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.hutool.StrUtil;
import com.litongjava.volcengine.VolcEngineConst;
import com.litongjava.zenmux.ZenmuxConst;

import lombok.extern.slf4j.Slf4j;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

@Slf4j
public class UniChatClient {

  public static final String GEMINI_API_KEY = GeminiClient.GEMINI_API_KEY;
  public static final String CLAUDE_API_KEY = ClaudeClient.CLAUDE_API_KEY;

  public static final String OPENAI_API_URL = EnvUtils.get("OPENAI_API_URL", OpenAiConstants.API_PERFIX_URL);
  public static final String OPENAI_API_KEY = EnvUtils.get("OPENAI_API_KEY");

  public static final String VOLCENGINE_API_URL = EnvUtils.get("VOLCENGINE_API_URL", VolcEngineConst.API_PREFIX_URL);
  public static final String VOLCENGINE_API_KEY = EnvUtils.get("VOLCENGINE_API_KEY");

  public static final String OPENROUTER_API_URL = EnvUtils.get("OPENROUTER_API_URL", OpenRouterConst.API_PREFIX_URL);
  public static final String OPENROUTER_API_KEY = EnvUtils.get("OPENROUTER_API_KEY");

  public static final String ZENMUX_API_URL = EnvUtils.get("ZENMUX_API_URL", ZenmuxConst.API_PREFIX_URL);
  public static final String ZENMUX_API_KEY = EnvUtils.get("ZENMUX_API_KEY");

  public static final String BAILIAN_API_URL = EnvUtils.get("BAILIAN_API_URL", BaiLianConst.API_PERFIX_URL);
  public static final String BAILIAN_API_KEY = EnvUtils.get("BAILIAN_API_KEY");

  public static final String TENCENT_API_URL = EnvUtils.get("TENCENT_API_URL", TencentConst.API_PERFIX_URL);
  public static final String TENCENT_API_KEY = EnvUtils.get("TENCENT_API_KEY");

  public static final String MOONSHOT_API_URL = EnvUtils.get("MOONSHOT_API_URL", MoonshotConst.API_PERFIX_URL);
  public static final String MOONSHOT_API_KEY = EnvUtils.get("MOONSHOT_API_KEY");

  public static final String MINIMAX_API_URL = EnvUtils.get("MINIMAX_API_URL", MiniMaxConst.API_PREFIX_URL);
  public static final String MINIMAX_API_KEY = EnvUtils.get("MINIMAX_API_KEY");

  public static final String CEREBRAS_API_URL = EnvUtils.get("CEREBRAS_API_URL", CerebrasConst.API_PREFIX_URL);
  public static final String CEREBRAS_API_KEY = EnvUtils.get("CEREBRAS_API_KEY");

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

  public static UniChatResponse generate(UniChatRequest uniChatRequest) {
    return generate(uniChatRequest.getApiKey(), uniChatRequest);
  }

  public static UniChatResponse generate(String key, UniChatRequest uniChatRequest) {

    String platform = uniChatRequest.getPlatform();

    if (ModelPlatformName.GOOGLE.equals(platform)) {
      if (key == null) {
        key = GEMINI_API_KEY;
      }
      return useGemeni(key, uniChatRequest);

    } else if (ModelPlatformName.ANTHROPIC.equals(platform)) {
      if (key == null) {
        key = CLAUDE_API_KEY;
      }
      return useClaude(key, uniChatRequest);

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

    } else {
      if (key == null) {
        key = OPENAI_API_KEY;
      }
      return useOpenAi(key, uniChatRequest);
    }
  }

  public static UniChatResponse useOpenAi(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(OPENAI_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useVolcEngine(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(VOLCENGINE_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useOpenRouter(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(OPENROUTER_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useZenmux(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(ZENMUX_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useBailian(String key, UniChatRequest uniChatRequest) {
    uniChatRequest.setEnable_thinking(false);
    return useOpenAi(BAILIAN_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useTencent(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(TENCENT_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useMiniMax(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(MINIMAX_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useMoonshot(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(MOONSHOT_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useCerebras(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(CEREBRAS_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useOllama(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(OLLAMA_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useLlamacpp(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(LLAMACPP_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useVllm(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(VLLM_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useSwift(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(SWIFT_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useTitanium(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(TITANIUM_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useOpenAi(String prefixUrl, String apiKey, UniChatRequest uniChatRequest) {
    List<UniChatMessage> messages = uniChatRequest.getMessages();
    List<OpenAiChatMessage> openAiChatMesages = new ArrayList<>();
    if (messages != null && messages.size() > 0) {
      Iterator<UniChatMessage> iterator = messages.iterator();
      while (iterator.hasNext()) {
        UniChatMessage next = iterator.next();
        String role = next.getRole();
        if (next.getRole().equals("model")) {
          role = "assistant";
        }
        String content = next.getContent();
        if (content != null) {
          openAiChatMesages.add(new OpenAiChatMessage(role, content));
        }
        List<ChatImageFile> files = next.getFiles();
        // files
        if (files != null && files.size() > 0) {
          List<ChatMessageContent> multiContents = new ArrayList<>();
          for (ChatImageFile file : files) {
            String data = file.getData();
            ChatRequestImage chatRequestImage = new ChatRequestImage();
            chatRequestImage.setDetail("auto");
            chatRequestImage.setUrl(data);
            ChatMessageContent image = new ChatMessageContent(chatRequestImage);
            multiContents.add(image);

          }
          OpenAiChatMessage openAiFileMesage = new OpenAiChatMessage();
          openAiFileMesage.role(role);
          openAiFileMesage.multiContents(multiContents);
          openAiChatMesages.add(openAiFileMesage);
        }
      }
    }

    if (uniChatRequest.isUseSystemPrompt()) {
      String systemPrompt = uniChatRequest.getSystemPrompt();
      if (StrUtil.isNotBlank(systemPrompt)) {
        openAiChatMesages.add(0, new OpenAiChatMessage("system", systemPrompt));
      }
    }
    OpenAiChatRequestVo openAiChatRequestVo = new OpenAiChatRequestVo();
    openAiChatRequestVo.setMessages(openAiChatMesages);

    openAiChatRequestVo.setModel(uniChatRequest.getModel());
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

    openAiChatRequestVo.setModalities(uniChatRequest.getResponseModalities());

    ChatProvider provider = uniChatRequest.getProvider();
    openAiChatRequestVo.setProvider(provider);

    String apiPrefixUrl = uniChatRequest.getApiPrefixUrl();

    OpenAiChatResponseVo chatResponse = null;
    if (apiPrefixUrl != null) {
      chatResponse = OpenAiClient.chatCompletions(apiPrefixUrl, apiKey, openAiChatRequestVo);
    } else {
      chatResponse = OpenAiClient.chatCompletions(prefixUrl, apiKey, openAiChatRequestVo);
    }

    if (chatResponse == null) {
      return null;
    }
    ChatResponseUsage usage = chatResponse.getUsage();
    String model = chatResponse.getModel();

    List<Choice> choices = chatResponse.getChoices();
    String rawResponse = chatResponse.getRawResponse();
    if (choices == null) {
      log.error("raw response:{}", rawResponse);
      return null;
    }
    Choice choice = choices.get(0);
    if (choice == null) {
      log.error("raw response:{}", rawResponse);
      return null;
    }
    ChatResponseMessage message = choice.getMessage();
    if (message == null) {
      log.error("raw response:{}", rawResponse);
      return null;
    }

    return new UniChatResponse(model, message, usage, rawResponse);
  }

  public static UniChatResponse useClaude(String key, UniChatRequest uniChatRequest) {
    String apiPrefixUrl = uniChatRequest.getApiPrefixUrl();
    List<UniChatMessage> messages = uniChatRequest.getMessages();
    Iterator<UniChatMessage> iterator = messages.iterator();
    while (iterator.hasNext()) {
      UniChatMessage next = iterator.next();
      if (next.getRole().equals("model")) {
        // 'system', 'assistant', 'user', 'function', 'tool', and 'developer'.",
        next.setRole("assistant");
      }
    }

    OpenAiChatRequestVo openAiChatRequestVo = new OpenAiChatRequestVo();
    if (uniChatRequest.isUseSystemPrompt()) {
      String systemPrompt = uniChatRequest.getSystemPrompt();
      if (ModelPlatformName.ANTHROPIC.equals(uniChatRequest.getPlatform())) {
        ClaudeMessageContent claudeChatMessage = new ClaudeMessageContent("text", systemPrompt);
        if (uniChatRequest.isCacheSystemPrompt()) {
          claudeChatMessage.setCache_control(new ClaudeCacheControl());
        }
        openAiChatRequestVo.setSystemChatMessage(claudeChatMessage);
      }
    }

    String model = uniChatRequest.getModel();
    if (model != null) {
      openAiChatRequestVo.setModel(model);
    }

    openAiChatRequestVo.setTemperature(uniChatRequest.getTemperature());
    openAiChatRequestVo.setChatMessages(messages, uniChatRequest.getPlatform());
    openAiChatRequestVo.setMax_tokens(uniChatRequest.getMax_tokens());

    ClaudeChatResponseVo chatResponse = null;
    if (apiPrefixUrl != null) {
      chatResponse = ClaudeClient.chatCompletions(apiPrefixUrl, key, openAiChatRequestVo);
    } else {
      chatResponse = ClaudeClient.chatCompletions(key, openAiChatRequestVo);
    }

    if (chatResponse == null) {
      return null;
    }

    String role = chatResponse.getRole();
    ClaudeMessageContent claudeChatMessage = chatResponse.getContent().get(0);
    ChatResponseMessage message = new ChatResponseMessage(role, claudeChatMessage.getText());
    ChatResponseUsage usage = new ChatResponseUsage(chatResponse.getUsage());
    return new UniChatResponse(model, message, usage, chatResponse.getRawResponse());
  }

  public static UniChatResponse useGemeni(String key, UniChatRequest uniChatRequest) {
    String apiPrefixUrl = uniChatRequest.getApiPrefixUrl();

    GeminiChatRequestVo geminiChatRequestVo = new GeminiChatRequestVo();
    geminiChatRequestVo.setChatMessages(uniChatRequest.getMessages());
    String cachedId = uniChatRequest.getCachedId();
    // CachedContent can not be used with GenerateContent request setting
    // system_instruction, tools or tool_config.
    // Proposed fix: move those values to CachedContent from GenerateContent request
    if (cachedId != null) {
      geminiChatRequestVo.setCachedContent(cachedId);
    } else {
      geminiChatRequestVo.setSystemPrompt(uniChatRequest.getSystemPrompt());
    }

    GeminiGenerationConfig config = new GeminiGenerationConfig();

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
      config.setResponseMimeType(responseMimeType);
    }
    List<String> responseModalities = uniChatRequest.getResponseModalities();
    config.setResponseModalities(responseModalities);

    UniResponseSchema responseSchema = uniChatRequest.getResponseSchema();
    if (responseSchema != null) {
      config.setResponseSchema(responseSchema);
      config.setResponseMimeType(ResponseMimeType.APPLICATION_JSON);
    }

    geminiChatRequestVo.setGenerationConfig(config);

    List<GeminiToolVo> tools = new ArrayList<>();
    Boolean enable_search = uniChatRequest.getEnable_search();
    if (enable_search != null && enable_search) {
      GeminiToolVo geminiToolVo = new GeminiToolVo();
      geminiToolVo.enableSearch();
      tools.add(geminiToolVo);
    }

    if (tools.size() > 0) {
      geminiChatRequestVo.setTools(tools);
    }

    GeminiChatResponseVo chatResponse = null;
    if (apiPrefixUrl != null) {
      chatResponse = GeminiClient.generate(apiPrefixUrl, key, uniChatRequest.getModel(), geminiChatRequestVo);
    } else {
      chatResponse = GeminiClient.generate(key, uniChatRequest.getModel(), geminiChatRequestVo);
    }

    if (chatResponse == null) {
      return null;
    }
    GeminiUsageMetadataVo usageMetadata = chatResponse.getUsageMetadata();
    ChatResponseUsage usage = new ChatResponseUsage(usageMetadata);
    String modelVersion = chatResponse.getModelVersion();

    UniChatResponse uniChatResponse = new UniChatResponse();
    uniChatResponse.setUsage(usage).setRawResponse(chatResponse.getRawResponse()).setModel(modelVersion);

    GeminiCandidateVo geminiCandidateVo = chatResponse.getCandidates().get(0);
    GeminiContentResponseVo content = geminiCandidateVo.getContent();

    String role = content.getRole();
    List<GeminiPartVo> parts = content.getParts();

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

  public static EventSource stream(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    if (ModelPlatformName.GOOGLE.equals(uniChatRequest.getPlatform())) {
      return useGemeni(key, uniChatRequest, listener);
    } else if (ModelPlatformName.ANTHROPIC.equals(uniChatRequest.getPlatform())) {
      return useClaude(key, uniChatRequest, listener);

    } else if (ModelPlatformName.VOLC_ENGINE.equals(uniChatRequest.getPlatform())) {
      return useVolcEngine(key, uniChatRequest, listener);

    } else if (ModelPlatformName.OPENROUTER.equals(uniChatRequest.getPlatform())) {
      return useOpenRouter(key, uniChatRequest, listener);
    } else {
      return useOpenAi(key, uniChatRequest, listener);
    }
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

  public static EventSource useOpenAi(String prefixUrl, String apiKey, UniChatRequest uniChatRequest,
      EventSourceListener listener) {
    List<UniChatMessage> messages = uniChatRequest.getMessages();
    Iterator<UniChatMessage> iterator = messages.iterator();
    while (iterator.hasNext()) {
      UniChatMessage next = iterator.next();
      if (next.getRole().equals("model")) {
        next.setRole("assistant");
      }
    }
    if (uniChatRequest.isUseSystemPrompt()) {
      messages.add(0, new UniChatMessage("system", uniChatRequest.getSystemPrompt()));
    }
    OpenAiChatRequestVo openAiChatRequestVo = new OpenAiChatRequestVo();
    openAiChatRequestVo.setModel(uniChatRequest.getModel());
    openAiChatRequestVo.setTemperature(uniChatRequest.getTemperature());
    openAiChatRequestVo.setChatMessages(messages);
    openAiChatRequestVo.setMax_tokens(uniChatRequest.getMax_tokens());
    String apiPrefixUrl = uniChatRequest.getApiPrefixUrl();
    EventSource eventSource = null;
    if (apiPrefixUrl != null) {
      eventSource = OpenAiClient.chatCompletions(apiPrefixUrl, apiKey, openAiChatRequestVo, listener);
    } else {
      eventSource = OpenAiClient.chatCompletions(prefixUrl, apiKey, openAiChatRequestVo, listener);
    }
    return eventSource;
  }

  public static EventSource useClaude(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    List<UniChatMessage> messages = uniChatRequest.getMessages();
    Iterator<UniChatMessage> iterator = messages.iterator();
    while (iterator.hasNext()) {
      UniChatMessage next = iterator.next();
      if (next.getRole().equals("model")) {
        // 'system', 'assistant', 'user', 'function', 'tool', and 'developer'.",
        next.setRole("assistant");
      }
    }

    OpenAiChatRequestVo openAiChatRequestVo = new OpenAiChatRequestVo();
    if (uniChatRequest.isUseSystemPrompt()) {
      String systemPrompt = uniChatRequest.getSystemPrompt();
      if (ModelPlatformName.ANTHROPIC.equals(uniChatRequest.getPlatform())) {
        ClaudeMessageContent claudeChatMessage = new ClaudeMessageContent("text", systemPrompt);
        if (uniChatRequest.isCacheSystemPrompt()) {
          claudeChatMessage.setCache_control(new ClaudeCacheControl());
        }
        openAiChatRequestVo.setSystemChatMessage(claudeChatMessage);
      }
    }

    openAiChatRequestVo.setModel(uniChatRequest.getModel());
    openAiChatRequestVo.setTemperature(uniChatRequest.getTemperature());
    openAiChatRequestVo.setChatMessages(messages, uniChatRequest.getPlatform());
    openAiChatRequestVo.setMax_tokens(uniChatRequest.getMax_tokens());

    String apiPrefixUrl = uniChatRequest.getApiPrefixUrl();
    EventSource eventSource = null;
    if (apiPrefixUrl == null) {
      eventSource = ClaudeClient.chatCompletions(apiPrefixUrl, key, openAiChatRequestVo, listener);
    } else {
      eventSource = ClaudeClient.chatCompletions(key, openAiChatRequestVo, listener);
    }

    return eventSource;

  }

  public static EventSource useGemeni(String key, UniChatRequest uniChatRequest, EventSourceListener listener) {
    GeminiGenerationConfig geminiGenerationConfigVo = new GeminiGenerationConfig();
    geminiGenerationConfigVo.setTemperature(uniChatRequest.getTemperature());

    GeminiChatRequestVo geminiChatRequestVo = new GeminiChatRequestVo();
    geminiChatRequestVo.setGenerationConfig(geminiGenerationConfigVo);
    geminiChatRequestVo.setSystemPrompt(uniChatRequest.getSystemPrompt());
    geminiChatRequestVo.setChatMessages(uniChatRequest.getMessages());
    geminiChatRequestVo.setCachedContent(uniChatRequest.getCachedId());

    String apiPrefixUrl = uniChatRequest.getApiPrefixUrl();
    EventSource eventSource = null;
    if (apiPrefixUrl != null) {
      eventSource = GeminiClient.stream(apiPrefixUrl, key, uniChatRequest.getModel(), geminiChatRequestVo, listener);
    } else {
      eventSource = GeminiClient.stream(key, uniChatRequest.getModel(), geminiChatRequestVo, listener);
    }
    return eventSource;

  }

}
