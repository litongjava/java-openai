package com.litongjava.chat;

import java.util.List;

import com.litongjava.openai.chat.MessageRole;

public class UniChatMessage {
  private String role = "user";
  private String content;
  // data:image base64 code, url:image http url
  private List<ChatImageFile> files;
  private ChatMessageArgs args;
  private List<String> attachments;

  public UniChatMessage(String role, String content) {
    this.role = role;
    this.content = content;
  }

  public UniChatMessage(String role, List<ChatImageFile> files) {
    this.role = role;
    this.files = files;
  }

  public UniChatMessage(String role, String content, ChatMessageArgs args) {
    this.role = role;
    this.content = content;
    this.args = args;
  }

  public static UniChatMessage buildSystem(String content) {
    return new UniChatMessage(MessageRole.system, content);
  }

  public static UniChatMessage buildUser(String content) {
    return new UniChatMessage(MessageRole.user, content);
  }

  public static UniChatMessage buildUser(List<ChatImageFile> files) {
    return new UniChatMessage(MessageRole.user, files);
  }

  public static UniChatMessage buildAssistant(String content) {
    return new UniChatMessage(MessageRole.assistant, content);
  }

  public UniChatMessage role(String role) {
    this.role = role;
    return this;
  }

  public UniChatMessage content(String content) {
    this.content = content;
    return this;
  }

  public UniChatMessage(String content) {
    this.role = "user";
    this.content = content;
  }

  public UniChatMessage() {
    super();
    // TODO Auto-generated constructor stub
  }

  public UniChatMessage(String role, String content, List<ChatImageFile> files, ChatMessageArgs args,
      List<String> attachments) {
    super();
    this.role = role;
    this.content = content;
    this.files = files;
    this.args = args;
    this.attachments = attachments;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<ChatImageFile> getFiles() {
    return files;
  }

  public void setFiles(List<ChatImageFile> files) {
    this.files = files;
  }

  public ChatMessageArgs getArgs() {
    return args;
  }

  public void setArgs(ChatMessageArgs args) {
    this.args = args;
  }

  public List<String> getAttachments() {
    return attachments;
  }

  public void setAttachments(List<String> attachments) {
    this.attachments = attachments;
  }

}
