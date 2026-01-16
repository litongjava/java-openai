package com.litongjava.claude;

import java.util.List;

import com.litongjava.openai.chat.ChatResponseDelta;

public class ClaudeChatResponse {
  private String id;
  private String type;
  private String role;
  private String model;
  private String stop_reason;
  private String stop_sequence;
  private ClaudeChatUsage usage;
  private List<ClaudeMessageContent> content;
  private ChatResponseDelta delta;
  private String rawResponse;

  public ClaudeChatResponse() {
    super();
  }

  public ClaudeChatResponse(String id, String type, String role, String model, String stop_reason, String stop_sequence,
      ClaudeChatUsage usage, List<ClaudeMessageContent> content, ChatResponseDelta delta, String rawResponse) {
    super();
    this.id = id;
    this.type = type;
    this.role = role;
    this.model = model;
    this.stop_reason = stop_reason;
    this.stop_sequence = stop_sequence;
    this.usage = usage;
    this.content = content;
    this.delta = delta;
    this.rawResponse = rawResponse;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getStop_reason() {
    return stop_reason;
  }

  public void setStop_reason(String stop_reason) {
    this.stop_reason = stop_reason;
  }

  public String getStop_sequence() {
    return stop_sequence;
  }

  public void setStop_sequence(String stop_sequence) {
    this.stop_sequence = stop_sequence;
  }

  public ClaudeChatUsage getUsage() {
    return usage;
  }

  public void setUsage(ClaudeChatUsage usage) {
    this.usage = usage;
  }

  public List<ClaudeMessageContent> getContent() {
    return content;
  }

  public void setContent(List<ClaudeMessageContent> content) {
    this.content = content;
  }

  public ChatResponseDelta getDelta() {
    return delta;
  }

  public void setDelta(ChatResponseDelta delta) {
    this.delta = delta;
  }

  public String getRawResponse() {
    return rawResponse;
  }

  public void setRawResponse(String rawResponse) {
    this.rawResponse = rawResponse;
  }

}
