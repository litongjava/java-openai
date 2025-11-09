package com.litongjava.utils;

import java.util.ArrayList;
import java.util.List;

import com.litongjava.gemini.GeminiCandidateVo;
import com.litongjava.gemini.GeminiPartVo;
import com.litongjava.gemini.GeminiChatResponseVo;
import com.litongjava.gemini.GeminiUsageMetadataVo;
import com.litongjava.openai.chat.ChatResponseDelta;
import com.litongjava.openai.chat.ChatResponseMessage;
import com.litongjava.openai.chat.ChatResponseUsage;
import com.litongjava.openai.chat.ChatRole;
import com.litongjava.openai.chat.Choice;
import com.litongjava.openai.chat.OpenAiChatResponse;

public class ChatResponseUtils {

  public static OpenAiChatResponse toOpenAiChatResponseVo(GeminiChatResponseVo geminiResponseVo, boolean isStream) {
    OpenAiChatResponse chatResponseVo = new OpenAiChatResponse();
    if (geminiResponseVo != null && geminiResponseVo.getCandidates() != null) {
      GeminiUsageMetadataVo usageMetadata = geminiResponseVo.getUsageMetadata();
      ChatResponseUsage chatResponseUsage = new ChatResponseUsage();
      chatResponseUsage.setPrompt_tokens(usageMetadata.getPromptTokenCount());
      chatResponseUsage.setTotal_tokens(usageMetadata.getTotalTokenCount());

      chatResponseVo.setModel(geminiResponseVo.getModelVersion());
      chatResponseVo.setUsage(chatResponseUsage);

      List<Choice> choices = new ArrayList<>();

      List<GeminiCandidateVo> candidates = geminiResponseVo.getCandidates();
      for (GeminiCandidateVo candidate : candidates) {
        Choice choice = new Choice();

        if (candidate.getContent() != null && candidate.getContent().getParts() != null) {
          List<GeminiPartVo> parts = candidate.getContent().getParts();
          for (GeminiPartVo partVo : parts) {
            String text = partVo.getText();
            if (isStream) {
              ChatResponseDelta delta = new ChatResponseDelta(ChatRole.assistant, text);
              choice.setDelta(delta);
            } else {
              ChatResponseMessage chatResponseMessage = new ChatResponseMessage(ChatRole.assistant, text);
              choice.setMessage(chatResponseMessage);
            }

          }

        }
        String finishReason = candidate.getFinishReason();
        choice.setFinish_reason(finishReason);
        Double avgLogprobs = candidate.getAvgLogprobs();
        choice.setLogprobs(avgLogprobs);
        choices.add(choice);
      }

      chatResponseVo.setChoices(choices);

    }
    return chatResponseVo;
  }
}
