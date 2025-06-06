package com.litongjava.chat;

import java.util.Iterator;
import java.util.List;

import com.litongjava.claude.ClaudeCacheControl;
import com.litongjava.claude.ClaudeChatResponseVo;
import com.litongjava.claude.ClaudeClient;
import com.litongjava.claude.ClaudeMessageContent;
import com.litongjava.consts.AiProviderName;
import com.litongjava.gemini.GeminiChatRequestVo;
import com.litongjava.gemini.GeminiChatResponseVo;
import com.litongjava.gemini.GeminiClient;
import com.litongjava.gemini.GeminiContentResponseVo;
import com.litongjava.gemini.GeminiGenerationConfigVo;
import com.litongjava.gemini.GeminiPartVo;
import com.litongjava.gemini.GeminiUsageMetadataVo;
import com.litongjava.openai.chat.ChatResponseMessage;
import com.litongjava.openai.chat.ChatResponseUsage;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.chat.OpenAiChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;
import com.litongjava.openai.consts.OpenAiConstants;
import com.litongjava.openrouter.OpenRouterConst;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.volcengine.VolcEngineConst;

public class UniChatClient {

  private static final String OPENAI_API_URL = EnvUtils.get("OPENAI_API_URL", OpenAiConstants.API_PERFIX_URL);
  private static final String VOLCENGINE_API_URL = EnvUtils.get("VOLCENGINE_API_URL", VolcEngineConst.API_PERFIX_URL);
  private static final String OPENROUTER_API_URL = EnvUtils.get("OPENROUTER_API_URL", OpenRouterConst.API_PERFIX_URL);

  public static UniChatResponse generate(UniChatRequest uniChatRequest) {
    return generate(uniChatRequest.getApiKey(), uniChatRequest);
  }

  public static UniChatResponse generate(String key, UniChatRequest uniChatRequest) {
    if (AiProviderName.GOOGLE.equals(uniChatRequest.getProvider())) {
      return useGemeni(key, uniChatRequest);
    } else if (AiProviderName.ANTHROPIC.equals(uniChatRequest.getProvider())) {
      return useClaude(key, uniChatRequest);

    } else if (AiProviderName.VOLC_ENGINE.equals(uniChatRequest.getProvider())) {
      return useVolcEngine(key, uniChatRequest);

    } else if (AiProviderName.OPENROUTER.equals(uniChatRequest.getProvider())) {
      return useOpenRouter(key, uniChatRequest);
    } else {
      return useOpenAi(key, uniChatRequest);
    }
  }

  private static UniChatResponse useVolcEngine(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(VOLCENGINE_API_URL, key, uniChatRequest);
  }

  private static UniChatResponse useOpenRouter(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(OPENROUTER_API_URL, key, uniChatRequest);
  }

  private static UniChatResponse useOpenAi(String key, UniChatRequest uniChatRequest) {
    return useOpenAi(OPENAI_API_URL, key, uniChatRequest);
  }

  public static UniChatResponse useOpenAi(String prefixUrl, String apiKey, UniChatRequest uniChatRequest) {
    List<ChatMessage> messages = uniChatRequest.getMessages();
    Iterator<ChatMessage> iterator = messages.iterator();
    while (iterator.hasNext()) {
      ChatMessage next = iterator.next();
      if (next.getRole().equals("model")) {
        next.setRole("assistant");
      }
    }
    if (uniChatRequest.isUseSystemPrompt()) {
      messages.add(0, new ChatMessage("system", uniChatRequest.getSystemPrompt()));
    }
    OpenAiChatRequestVo openAiChatRequestVo = new OpenAiChatRequestVo();
    openAiChatRequestVo.setModel(uniChatRequest.getModel());
    openAiChatRequestVo.setTemperature(uniChatRequest.getTemperature());
    openAiChatRequestVo.setChatMessages(messages);
    openAiChatRequestVo.setMax_tokens(uniChatRequest.getMax_tokens());

    OpenAiChatResponseVo chatCompletions = OpenAiClient.chatCompletions(prefixUrl, apiKey, openAiChatRequestVo);
    if (chatCompletions == null) {
      return null;
    }
    ChatResponseMessage message = chatCompletions.getChoices().get(0).getMessage();
    ChatResponseUsage usage = chatCompletions.getUsage();
    return new UniChatResponse(message, usage);
  }

  private static UniChatResponse useClaude(String key, UniChatRequest uniChatRequest) {
    List<ChatMessage> messages = uniChatRequest.getMessages();
    Iterator<ChatMessage> iterator = messages.iterator();
    while (iterator.hasNext()) {
      ChatMessage next = iterator.next();
      if (next.getRole().equals("model")) {
        //'system', 'assistant', 'user', 'function', 'tool', and 'developer'.",
        next.setRole("assistant");
      }
    }

    OpenAiChatRequestVo openAiChatRequestVo = new OpenAiChatRequestVo();
    if (uniChatRequest.isUseSystemPrompt()) {
      String systemPrompt = uniChatRequest.getSystemPrompt();
      if (AiProviderName.ANTHROPIC.equals(uniChatRequest.getProvider())) {
        ClaudeMessageContent claudeChatMessage = new ClaudeMessageContent("text", systemPrompt);
        if (uniChatRequest.isCacheSystemPrompt()) {
          claudeChatMessage.setCache_control(new ClaudeCacheControl());
        }
        openAiChatRequestVo.setSystemChatMessage(claudeChatMessage);
      }
    }

    openAiChatRequestVo.setModel(uniChatRequest.getModel());
    openAiChatRequestVo.setTemperature(uniChatRequest.getTemperature());
    openAiChatRequestVo.setChatMessages(messages, uniChatRequest.getProvider());
    openAiChatRequestVo.setMax_tokens(uniChatRequest.getMax_tokens());

    ClaudeChatResponseVo chatCompletions = ClaudeClient.chatCompletions(key, openAiChatRequestVo);
    if (chatCompletions == null) {
      return null;
    }

    String role = chatCompletions.getRole();
    ClaudeMessageContent claudeChatMessage = chatCompletions.getContent().get(0);
    ChatResponseMessage message = new ChatResponseMessage(role, claudeChatMessage.getText());
    ChatResponseUsage usage = new ChatResponseUsage(chatCompletions.getUsage());
    return new UniChatResponse(message, usage);
  }

  private static UniChatResponse useGemeni(String key, UniChatRequest uniChatRequest) {
    GeminiGenerationConfigVo geminiGenerationConfigVo = new GeminiGenerationConfigVo();
    geminiGenerationConfigVo.setTemperature(uniChatRequest.getTemperature());

    GeminiChatRequestVo geminiChatRequestVo = new GeminiChatRequestVo();
    geminiChatRequestVo.setGenerationConfig(geminiGenerationConfigVo);
    geminiChatRequestVo.setSystemPrompt(uniChatRequest.getSystemPrompt());
    geminiChatRequestVo.setChatMessages(uniChatRequest.getMessages());
    geminiChatRequestVo.setCachedContent(uniChatRequest.getCachedId());

    GeminiChatResponseVo chatResponse = GeminiClient.generate(key, uniChatRequest.getModel(), geminiChatRequestVo);
    if (chatResponse == null) {
      return null;
    }

    GeminiContentResponseVo content = chatResponse.getCandidates().get(0).getContent();
    String role = content.getRole();
    GeminiPartVo geminiPartVo = content.getParts().get(0);
    GeminiUsageMetadataVo usageMetadata = chatResponse.getUsageMetadata();
    ChatResponseUsage usage = new ChatResponseUsage(usageMetadata);
    ChatResponseMessage message = new ChatResponseMessage(role, geminiPartVo);
    return new UniChatResponse(message, usage);
  }
}
