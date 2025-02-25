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
public class OpenAiChatMessage {
  private String role;
  private Object content;
  private Boolean prefix;

  public static OpenAiChatMessage buildSystem(String content) {
    return new OpenAiChatMessage(MessageRole.system, content);
  }

  public static OpenAiChatMessage buildAssistant(String content) {
    return new OpenAiChatMessage(MessageRole.assistant, content);
  }

  public static OpenAiChatMessage buildUser(String content) {
    return new OpenAiChatMessage(MessageRole.user, content);
  }

  public static OpenAiChatMessage buildFunction(String content) {
    return new OpenAiChatMessage(MessageRole.function, content);
  }

  public static OpenAiChatMessage buildTool(String content) {
    return new OpenAiChatMessage(MessageRole.tool, content);
  }

  public static OpenAiChatMessage buildDeveloper(String content) {
    return new OpenAiChatMessage(MessageRole.developer, content);
  }

  public OpenAiChatMessage(String prompt) {
    this.role = "user";
    this.content = prompt;
  }

  public OpenAiChatMessage(ChatMessage chatMessage) {
    this.role = chatMessage.getRole();
    this.content = chatMessage.getContent();
  }

  public OpenAiChatMessage(String role, String prompt) {
    this.role = role;
    this.content = prompt;
  }

  public void setContent(String prompt) {
    this.content = prompt;
  }

  public OpenAiChatMessage content(String prompt) {
    this.setContent(prompt);
    return this;
  }

  public OpenAiChatMessage multiContents(List<ChatMesageContent> multiContents) {
    this.content = multiContents;
    return this;
  }

  public OpenAiChatMessage role(String role) {
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

  public Boolean getPrefix() {
    return prefix;
  }

  public void setPrefix(Boolean prefix) {
    this.prefix = prefix;
  }
}
