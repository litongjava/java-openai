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
  private Long groupId;
  private String groupName;
  private Long taskId;
  private String taskName;
  private boolean hasSystemPrompt;
  private String apiKey;
  private String provider;
  private String model;
  private String systemPrompt;;
  private List<ChatMessage> messages;
  private Float temperature;
  private String cachedId;
  private Integer max_tokens;

  public UniChatRequest(List<ChatMessage> messages) {
    this.messages = messages;
  }

  public UniChatRequest(Long groupId, String groupName) {
    this.groupId = groupId;
    this.groupName = groupName;
  }

  public UniChatRequest(List<ChatMessage> messages, Float temperature) {
    this.messages = messages;
    this.temperature = temperature;
  }

  public UniChatRequest(String systemPrompt, List<ChatMessage> messages, Float temperature) {
    this.systemPrompt = systemPrompt;
    this.messages = messages;
    this.temperature = temperature;
  }

}
