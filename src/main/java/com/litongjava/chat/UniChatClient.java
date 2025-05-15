package com.litongjava.chat;

import java.util.Iterator;
import java.util.List;

import com.litongjava.claude.ClaudeCacheControl;
import com.litongjava.claude.ClaudeChatMessage;
import com.litongjava.claude.ClaudeChatResponseVo;
import com.litongjava.claude.ClaudeClient;
import com.litongjava.consts.AiProviderName;
import com.litongjava.gemini.GeminiChatRequestVo;
import com.litongjava.gemini.GeminiChatResponseVo;
import com.litongjava.gemini.GeminiClient;
import com.litongjava.gemini.GeminiContentResponseVo;
import com.litongjava.gemini.GeminiGenerationConfigVo;
import com.litongjava.gemini.GeminiPartVo;
import com.litongjava.gemini.GeminiUsageMetadataVo;
import com.litongjava.openai.chat.ChatMessage;
import com.litongjava.openai.chat.ChatResponseMessage;
import com.litongjava.openai.chat.ChatResponseUsage;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.chat.OpenAiChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;

public class UniChatClient {

  public static UniChatResponse generate(String key, UniChatRequest uniChatRequest) {
    if (AiProviderName.GEMINI.equals(uniChatRequest.getProvider())) {
      return useGemeni(key, uniChatRequest);
    } else if (AiProviderName.CLAUDE.equals(uniChatRequest.getProvider())) {
      return useClaude(key, uniChatRequest);
    } else {
      return useOpenAi(key, uniChatRequest);
    }
  }

  public static UniChatResponse generate(UniChatRequest uniChatRequest) {
    if (AiProviderName.GEMINI.equals(uniChatRequest.getProvider())) {
      return useGemeni(uniChatRequest);
    } else if (AiProviderName.CLAUDE.equals(uniChatRequest.getProvider())) {
      return useClaude(uniChatRequest);
    } else {
      return useOpenAi(uniChatRequest);
    }

  }

  private static UniChatResponse useOpenAi(UniChatRequest uniChatRequest) {
    List<ChatMessage> messages = uniChatRequest.getMessages();
    Iterator<ChatMessage> iterator = messages.iterator();
    while (iterator.hasNext()) {
      ChatMessage next = iterator.next();
      if (next.getRole().equals("model")) {
        next.setRole("assistant");
      }
    }
    if (uniChatRequest.isHasSystemPrompt()) {
      messages.add(0, new ChatMessage("system", uniChatRequest.getSystemPrompt()));
    }
    OpenAiChatRequestVo openAiChatRequestVo = new OpenAiChatRequestVo();
    openAiChatRequestVo.setModel(uniChatRequest.getModel());
    openAiChatRequestVo.setTemperature(uniChatRequest.getTemperature());
    openAiChatRequestVo.setChatMessages(messages);
    openAiChatRequestVo.setMax_tokens(uniChatRequest.getMax_tokens());

    OpenAiChatResponseVo chatCompletions = OpenAiClient.chatCompletions(openAiChatRequestVo);
    if (chatCompletions == null) {
      return null;
    }
    ChatResponseMessage message = chatCompletions.getChoices().get(0).getMessage();
    ChatResponseUsage usage = chatCompletions.getUsage();
    return new UniChatResponse(message, usage);
  }

  private static UniChatResponse useOpenAi(String key, UniChatRequest uniChatRequest) {
    List<ChatMessage> messages = uniChatRequest.getMessages();
    Iterator<ChatMessage> iterator = messages.iterator();
    while (iterator.hasNext()) {
      ChatMessage next = iterator.next();
      if (next.getRole().equals("model")) {
        //'system', 'assistant', 'user', 'function', 'tool', and 'developer'.",
        next.setRole("assistant");
      }
    }
    if (uniChatRequest.isHasSystemPrompt()) {
      messages.add(0, new ChatMessage("system", uniChatRequest.getSystemPrompt()));
    }
    OpenAiChatRequestVo openAiChatRequestVo = new OpenAiChatRequestVo();
    openAiChatRequestVo.setModel(uniChatRequest.getModel());
    openAiChatRequestVo.setTemperature(uniChatRequest.getTemperature());
    openAiChatRequestVo.setChatMessages(messages);
    openAiChatRequestVo.setMax_tokens(uniChatRequest.getMax_tokens());

    OpenAiChatResponseVo chatCompletions = OpenAiClient.chatCompletions(key, openAiChatRequestVo);
    if (chatCompletions == null) {
      return null;
    }
    ChatResponseMessage message = chatCompletions.getChoices().get(0).getMessage();
    ChatResponseUsage usage = chatCompletions.getUsage();
    return new UniChatResponse(message, usage);
  }

  private static UniChatResponse useClaude(UniChatRequest uniChatRequest) {
    List<ChatMessage> messages = uniChatRequest.getMessages();
    Iterator<ChatMessage> iterator = messages.iterator();

    while (iterator.hasNext()) {
      ChatMessage next = iterator.next();
      if (next.getRole().equals("model")) {
        //'assistant', 'user'
        next.setRole("assistant");
      }
    }
    OpenAiChatRequestVo openAiChatRequestVo = new OpenAiChatRequestVo();
    if (uniChatRequest.isHasSystemPrompt()) {
      String systemPrompt = uniChatRequest.getSystemPrompt();
      ClaudeChatMessage claudeChatMessage = new ClaudeChatMessage("text", systemPrompt);
      if (uniChatRequest.isCacheSystemPrompt()) {
        claudeChatMessage.setCache_control(new ClaudeCacheControl());
      }
      openAiChatRequestVo.setSystemChatMessage(claudeChatMessage);
    }

    openAiChatRequestVo.setModel(uniChatRequest.getModel());
    openAiChatRequestVo.setTemperature(uniChatRequest.getTemperature());
    openAiChatRequestVo.setChatMessages(messages);
    openAiChatRequestVo.setMax_tokens(uniChatRequest.getMax_tokens());

    ClaudeChatResponseVo chatCompletions = ClaudeClient.chatCompletions(openAiChatRequestVo);
    if (chatCompletions == null) {
      return null;
    }

    String role = chatCompletions.getRole();
    ClaudeChatMessage claudeChatMessage = chatCompletions.getContent().get(0);
    ChatResponseMessage message = new ChatResponseMessage(role, claudeChatMessage.getText());
    ChatResponseUsage usage = new ChatResponseUsage(chatCompletions.getUsage());
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
    if (uniChatRequest.isHasSystemPrompt()) {
      String systemPrompt = uniChatRequest.getSystemPrompt();
      ClaudeChatMessage claudeChatMessage = new ClaudeChatMessage("text", systemPrompt);
      if (uniChatRequest.isCacheSystemPrompt()) {
        claudeChatMessage.setCache_control(new ClaudeCacheControl());
      }
      openAiChatRequestVo.setSystemChatMessage(claudeChatMessage);
    }

    openAiChatRequestVo.setModel(uniChatRequest.getModel());
    openAiChatRequestVo.setTemperature(uniChatRequest.getTemperature());
    openAiChatRequestVo.setChatMessages(messages);
    openAiChatRequestVo.setMax_tokens(uniChatRequest.getMax_tokens());

    ClaudeChatResponseVo chatCompletions = ClaudeClient.chatCompletions(openAiChatRequestVo);
    if (chatCompletions == null) {
      return null;
    }

    String role = chatCompletions.getRole();
    ClaudeChatMessage claudeChatMessage = chatCompletions.getContent().get(0);
    ChatResponseMessage message = new ChatResponseMessage(role, claudeChatMessage.getText());
    ChatResponseUsage usage = new ChatResponseUsage(chatCompletions.getUsage());
    return new UniChatResponse(message, usage);
  }

  private static UniChatResponse useGemeni(UniChatRequest uniChatRequest) {
    GeminiGenerationConfigVo geminiGenerationConfigVo = new GeminiGenerationConfigVo();
    geminiGenerationConfigVo.setTemperature(uniChatRequest.getTemperature());

    GeminiChatRequestVo geminiChatRequestVo = new GeminiChatRequestVo();
    geminiChatRequestVo.setGenerationConfig(geminiGenerationConfigVo);
    geminiChatRequestVo.setSystemPrompt(uniChatRequest.getSystemPrompt());
    geminiChatRequestVo.setChatMessages(uniChatRequest.getMessages());
    geminiChatRequestVo.setCachedContent(uniChatRequest.getCachedId());

    GeminiChatResponseVo chatResponse = GeminiClient.generate(uniChatRequest.getModel(), geminiChatRequestVo);
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
