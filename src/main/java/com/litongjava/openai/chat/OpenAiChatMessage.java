package com.litongjava.openai.chat;

import java.util.ArrayList;
import java.util.List;

import com.litongjava.chat.ChatFile;
import com.litongjava.chat.UniChatMessage;
import com.litongjava.claude.ClaudeMessageContent;
import com.litongjava.consts.ModelPlatformName;
import com.litongjava.tio.utils.hutool.StrUtil;

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

  public OpenAiChatMessage(UniChatMessage chatMessage) {
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

  public OpenAiChatMessage multiContents(List<ChatMessageContent> multiContents) {
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

  public OpenAiChatMessage(UniChatMessage message, String provider) {
    this.role = message.getRole();
    String content = message.getContent();
    if (ModelPlatformName.ANTHROPIC.equals(provider)) {
      if (message.getFiles() != null && message.getFiles().size() > 0) {
        List<ClaudeMessageContent> messageContents = new ArrayList<>();
        List<ChatFile> files = message.getFiles();
        for (ChatFile file : files) {
          ClaudeMessageContent e = new ClaudeMessageContent(file);
          messageContents.add(e);
        }
        if (StrUtil.isNotBlank(content)) {
          messageContents.add(new ClaudeMessageContent("text", content));
        }

        this.content = messageContents;
      } else {
        this.content = content;
      }
    } else {
      this.role = message.getRole();
      this.content = content;
    }
  }
}
