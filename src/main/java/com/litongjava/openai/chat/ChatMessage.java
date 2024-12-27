package com.litongjava.openai.chat;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * the chat request message of openai
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
  private String role;
  private Object content;

  public void setContent(String content) {
    this.content = content;
  }

  public ChatMessage content(String content) {
    this.content = content;
    return this;
  }

  public ChatMessage multiContents(List<ChatRequestMultiContent> multiContents) {
    this.content = multiContents;
    return this;
  }

  public ChatMessage role(String role) {
    this.role = role;
    return this;
  }

  public String getRole() {
    return role;
  }

  public Object getContent() {
    return content;
  }

  public String role() {
    return role;
  }

  public Object content() {
    return content;
  }

  public ChatMessage(String content) {
    this.role = "user";
    this.content = content;
  }

  public static ChatMessage buildSystem(String content) {
    return new ChatMessage(MessageRole.system, content);
  }

  public static ChatMessage buildAssistant(String content) {
    return new ChatMessage(MessageRole.assistant, content);
  }

  public static ChatMessage buildUser(String content) {
    return new ChatMessage(MessageRole.user, content);
  }

  public static ChatMessage buildFunction(String content) {
    return new ChatMessage(MessageRole.function, content);
  }

  public static ChatMessage buildTool(String content) {
    return new ChatMessage(MessageRole.tool, content);
  }

  public static ChatMessage buildDeveloper(String content) {
    return new ChatMessage(MessageRole.developer, content);
  }

}
