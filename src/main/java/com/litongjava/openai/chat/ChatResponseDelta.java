package com.litongjava.openai.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponseDelta {

  private String role;
  private String content;
  private String reasoning_content;
  private String refusal;
  private ChatFunctionCall function_call;

  // claude
  private String text;

  public ChatResponseDelta(String role, String content) {
    this.role = role;
    this.content = content;
  }
}
