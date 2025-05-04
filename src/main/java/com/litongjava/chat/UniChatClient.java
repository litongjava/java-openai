package com.litongjava.chat;

import java.util.Iterator;
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

  public static String generate(UniChatRequest uniChatRequest) {
    if (AiProviderName.GOOGLE.contentEquals(uniChatRequest.getProvider())) {
      return useGemeni(uniChatRequest);
    }
    return useOpenAi(uniChatRequest);
  }

  private static String useOpenAi(UniChatRequest uniChatRequest) {
    List<ChatMessage> messages = uniChatRequest.getMessages();
    Iterator<ChatMessage> iterator = messages.iterator();
    while (iterator.hasNext()) {
      ChatMessage next = iterator.next();
      if (next.getRole().equals("model")) {
        //'system', 'assistant', 'user', 'function', 'tool', and 'developer'.",
        next.setRole("assistant");
      }
    }
    if (!uniChatRequest.isExistsSystemPrompt()) {
      messages.add(0, new ChatMessage("system", uniChatRequest.getSystemPrompt()));
    }
    OpenAiChatRequestVo openAiChatRequestVo = new OpenAiChatRequestVo();
    openAiChatRequestVo.setModel(uniChatRequest.getModel());
    openAiChatRequestVo.setTemperature(uniChatRequest.getTemperature());
    openAiChatRequestVo.setChatMessages(messages);

    OpenAiChatResponseVo chatCompletions = OpenAiClient.chatCompletions(openAiChatRequestVo);
    if (chatCompletions == null) {
      return null;
    }
    return chatCompletions.getChoices().get(0).getMessage().getContent();
  }

  private static String useGemeni(UniChatRequest uniChatRequest) {
    GeminiGenerationConfigVo geminiGenerationConfigVo = new GeminiGenerationConfigVo();
    geminiGenerationConfigVo.setTemperature(uniChatRequest.getTemperature());

    GeminiChatRequestVo geminiChatRequestVo = new GeminiChatRequestVo();
    geminiChatRequestVo.setGenerationConfig(geminiGenerationConfigVo);
    geminiChatRequestVo.setSystemPrompt(uniChatRequest.getSystemPrompt());
    geminiChatRequestVo.setChatMessages(uniChatRequest.getMessages());

    GeminiChatResponseVo chatResponse = GeminiClient.generate(uniChatRequest.getModel(), geminiChatRequestVo);
    if (chatResponse == null) {
      return null;
    }
    return chatResponse.getCandidates().get(0).getContent().getParts().get(0).getText();
  }
}
