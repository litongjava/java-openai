package com.litongjava.chat;

import java.util.List;

import com.litongjava.openai.chat.MessageRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
  private String role = "user";
  private String content;
  //data:image base64 code, url:image http url
  private List<ChatFile> files;
  private ChatMessageArgs args;

  public ChatMessage(String role, String content) {
    this.role = role;
    this.content = content;
  }

  public ChatMessage(String role, String content, ChatMessageArgs args) {
    this.role = role;
    this.content = content;
    this.args = args;
  }

  public static ChatMessage buildSystem(String content) {
    return new ChatMessage(MessageRole.system, content);
  }

  public static ChatMessage buildUser(String content) {
    return new ChatMessage(MessageRole.user, content);
  }

  public static ChatMessage buildAssistant(String content) {
    return new ChatMessage(MessageRole.assistant, content);
  }

  public ChatMessage role(String role) {
    this.role = role;
    return this;
  }

  public ChatMessage content(String content) {
    this.content = content;
    return this;
  }

  public ChatMessage(String content) {
    this.role = "user";
    this.content = content;
  }

}
