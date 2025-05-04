package com.litongjava.chat;

import java.util.List;

import com.litongjava.consts.AiProviderName;
import com.litongjava.gemini.GeminiChatRequestVo;
import com.litongjava.gemini.GeminiChatResponseVo;
import com.litongjava.gemini.GeminiClient;
import com.litongjava.gemini.GeminiGenerationConfigVo;
import com.litongjava.openai.chat.ChatMessage;
import com.litongjava.openai.chat.OpenAiChatRequestVo;
import com.litongjava.openai.chat.OpenAiChatResponseVo;
import com.litongjava.openai.client.OpenAiClient;

public class UniChatClient {

  public static String generate(String provider, String model, String systemPrompt, List<ChatMessage> messages, Float temperature) {
    if (AiProviderName.GOOGLE.contentEquals(provider)) {
      return useGemeni(model, systemPrompt, messages, temperature);
    }
    return useOpenAi(model, systemPrompt, messages, temperature);
  }

  private static String useOpenAi(String model, String systemPrompt, List<ChatMessage> messages, Float temperature) {
    OpenAiChatRequestVo openAiChatRequestVo = new OpenAiChatRequestVo();
    openAiChatRequestVo.setModel(model);
    openAiChatRequestVo.setTemperature(temperature);
    messages.add(new ChatMessage("system", systemPrompt));
    OpenAiChatResponseVo chatCompletions = OpenAiClient.chatCompletions(openAiChatRequestVo);
    if (chatCompletions == null) {
      return null;
    }
    return chatCompletions.getChoices().get(0).getMessage().getContent();
  }

  private static String useGemeni(String model, String systemPrompt, List<ChatMessage> messages, Float temperature) {
    GeminiGenerationConfigVo geminiGenerationConfigVo = new GeminiGenerationConfigVo();
    geminiGenerationConfigVo.setTemperature(temperature);

    GeminiChatRequestVo geminiChatRequestVo = new GeminiChatRequestVo();
    geminiChatRequestVo.setGenerationConfig(geminiGenerationConfigVo);
    geminiChatRequestVo.setSystemPrompt(systemPrompt);
    geminiChatRequestVo.setChatMessages(messages);

    GeminiChatResponseVo chatResponse = GeminiClient.generate(model, geminiChatRequestVo);
    if (chatResponse == null) {
      return null;
    }
    return chatResponse.getCandidates().get(0).getContent().getParts().get(0).getText();
  }
}
