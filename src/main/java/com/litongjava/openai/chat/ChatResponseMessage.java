package com.litongjava.openai.chat;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseMessage {

  private String role;
  private String content;
  private List<ToolCall> tool_calls;

  public ChatResponseMessage(String role, String content) {
    this.role = role;
    this.content = content;
  }
}
