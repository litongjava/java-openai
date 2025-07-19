package com.litongjava.chat;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class UniChatRequest {
  private String apiPrefixUrl;
  private Long groupId;
  private String groupName;
  private Long taskId;
  private String taskName;
  private boolean useSystemPrompt = true;
  private String apiKey;
  private String platform;
  private String model;
  private String systemPrompt;;
  private boolean cacheSystemPrompt;
  private List<UniChatMessage> messages;
  private Float temperature;
  private String cachedId;
  private Integer max_tokens;
  private Boolean enable_thinking;
  private UniThinkingConfig thinkingConfig;
  private String responseMimeType;
  private UniResponseSchema responseSchema;

  public UniChatRequest(List<UniChatMessage> messages) {
    this.messages = messages;
  }

  public UniChatRequest(Long groupId, String groupName) {
    this.groupId = groupId;
    this.groupName = groupName;
  }

  public UniChatRequest(List<UniChatMessage> messages, Float temperature) {
    this.messages = messages;
    this.temperature = temperature;
  }

  public UniChatRequest(String systemPrompt) {
    this.systemPrompt = systemPrompt;
  }

  public UniChatRequest(String systemPrompt, Float temperature) {
    this.systemPrompt = systemPrompt;
    this.temperature = temperature;
  }

  public UniChatRequest(String systemPrompt, List<UniChatMessage> messages, Float temperature) {
    this.systemPrompt = systemPrompt;
    this.messages = messages;
    this.temperature = temperature;
  }

  public UniChatRequest(String provider, String model, List<UniChatMessage> messages) {
    this.platform = provider;
    this.model = model;
    this.messages = messages;
  }

  public UniChatRequest(String provider, String model, List<UniChatMessage> messages, Float temperature) {
    this.platform = provider;
    this.model = model;
    this.messages = messages;
    this.temperature = temperature;
  }

  public UniChatRequest(String provider, String model, String systemPrompt, List<UniChatMessage> messages, Float temperature) {
    this.systemPrompt = systemPrompt;
    this.platform = provider;
    this.model = model;
    this.messages = messages;
    this.temperature = temperature;
  }

}
