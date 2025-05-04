package com.litongjava.chat;

import java.util.List;

import com.litongjava.openai.chat.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class UniChatRequest {
  private boolean existsSystemPrompt;
  private String provider;
  private String model;
  private String systemPrompt;;
  private List<ChatMessage> messages;
  private Float temperature;

  public UniChatRequest(String systemPrompt, List<ChatMessage> messages) {
    this.systemPrompt = systemPrompt;
    this.messages = messages;
  }

  public UniChatRequest(String systemPrompt, List<ChatMessage> messages, Float temperature) {
    this.systemPrompt = systemPrompt;
    this.messages = messages;
    this.temperature = temperature;
  }
}
