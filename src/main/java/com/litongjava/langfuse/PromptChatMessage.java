package com.litongjava.langfuse;

public class PromptChatMessage {

  private String type;
  private String role;
  private String content;

  public PromptChatMessage() {
  }

  public PromptChatMessage(String type, String role, String content) {
    this.type = type;
    this.role = role;
    this.content = content;
  }

  public String getType() {
    return type;
  }

  public String getRole() {
    return role;
  }

  public String getContent() {
    return content;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
